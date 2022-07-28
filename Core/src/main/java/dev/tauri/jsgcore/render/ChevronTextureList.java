package dev.tauri.jsgcore.render;

import dev.tauri.jsgcore.loader.texture.TextureLoader;
import dev.tauri.jsgcore.render.activation.Activation;
import dev.tauri.jsgcore.render.activation.StargateActivation;
import dev.tauri.jsgcore.utils.BiomeOverlayEnum;
import io.netty.buffer.ByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import java.util.*;

public class ChevronTextureList {

  // Saved
  private List<ChevronEnum> activeChevrons = new ArrayList<>(9);

  // Not saved
  private Map<ChevronEnum, Integer> CHEVRON_STATE_MAP = new HashMap<>(9);
  private List<Activation<ChevronEnum>> activationList = new ArrayList<>();

  private final Map<BiomeOverlayEnum, Map<Integer, ResourceLocation>> CHEVRON_RESOURCE_MAP = new HashMap<>();

  public ChevronTextureList(String chevronTextureBase, TextureLoader textureLoader) {
    for (BiomeOverlayEnum biomeOverlay : BiomeOverlayEnum.values()) {
      Map<Integer, ResourceLocation> map = new HashMap<>();

      for (int i = 0; i <= 10; i++) {
        map.put(i, textureLoader.getTextureResource(chevronTextureBase + i + biomeOverlay.suffix + ".jpg"));
      }

      CHEVRON_RESOURCE_MAP.put(biomeOverlay, map);
    }
  }

  public ChevronTextureList(String chevronTextureBase, int activeChevrons, boolean isFinalActive, TextureLoader textureLoader) {
    this(chevronTextureBase, textureLoader);

    if (isFinalActive) activeChevrons--;

    for (int i = 0; i < activeChevrons; i++)
      this.activeChevrons.add(ChevronEnum.valueOf(i));

    if (isFinalActive) this.activeChevrons.add(ChevronEnum.getFinal());
  }

  public void initClient() {
    for (ChevronEnum chevron : ChevronEnum.values()) {
      CHEVRON_STATE_MAP.put(chevron, activeChevrons.contains(chevron) ? 10 : 0);
    }
  }

  public ChevronEnum getCurrentChevron() {
    if (activeChevrons.size() > 0) return activeChevrons.get(activeChevrons.size() - 1);
    return ChevronEnum.C1;
  }

  public ChevronEnum getNextChevron() {
    if (activeChevrons.size() > 0) return getCurrentChevron().getNext();
    return ChevronEnum.C1;
  }

  public void activateNextChevron(long totalWorldTime) {
    ChevronEnum next = getNextChevron();

    activationList.add(new StargateActivation(next, totalWorldTime, false));
    activeChevrons.add(next);
  }

  public void activateNextChevron(long totalWorldTime, int chevron) {
    if(chevron < 10){
      activateNextChevron(totalWorldTime);
      return;
    }
    ChevronEnum chev = ChevronEnum.valueOf(chevron-10);

    activationList.add(new StargateActivation(chev, totalWorldTime, false));
    activeChevrons.add(chev);
  }

  public void activateFinalChevron(long totalWorldTime) {
    activationList.add(new StargateActivation(ChevronEnum.getFinal(), totalWorldTime, false));
    activeChevrons.add(ChevronEnum.getFinal());
  }

  public void deactivateFinalChevron(long totalWorldTime) {
    activationList.add(new StargateActivation(ChevronEnum.getFinal(), totalWorldTime, true));
    activeChevrons.remove(ChevronEnum.getFinal());
  }

  public void clearChevrons(long totalWorldTime) {
    for (ChevronEnum chevron : activeChevrons) {
      activationList.add(new StargateActivation(chevron, totalWorldTime, true));
    }

    activeChevrons.clear();
  }

  public void lightUpChevrons(long totalWorldTime, int incomingAddressSize) {
    for (ChevronEnum chevron : Arrays.asList(ChevronEnum.C7, ChevronEnum.C8)) {
      if (activeChevrons.contains(chevron) && chevron.index >= incomingAddressSize - 1) {
        activationList.add(new StargateActivation(chevron, totalWorldTime, true));
      }
    }

    activeChevrons.clear();

    while (activeChevrons.size() < incomingAddressSize - 1) {
      activateNextChevron(totalWorldTime);
    }

    activateFinalChevron(totalWorldTime);
  }

  public void iterate(Level world, double partialTicks) {
    Activation.iterate(activationList, world.getGameTime(), partialTicks, (index, stage) -> {
      CHEVRON_STATE_MAP.put(index, Math.round(stage));
    });
  }

  public ResourceLocation get(BiomeOverlayEnum overlayEnum, ChevronEnum chevron) {
    return CHEVRON_RESOURCE_MAP.get(overlayEnum).get(CHEVRON_STATE_MAP.get(chevron));
  }

  public void toBytes(ByteBuf buf) {
    buf.writeInt(activeChevrons.size());

    for (ChevronEnum chevron : activeChevrons) {
      buf.writeInt(chevron.index);
    }
  }

  public void fromBytes(ByteBuf buf) {
    int size = buf.readInt();
    activeChevrons.clear();

    for (int i = 0; i < size; i++) {
      activeChevrons.add(ChevronEnum.valueOf(buf.readInt()));
    }
  }
}
