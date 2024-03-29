package dev.tauri.jsgcore.render.activation;

import dev.tauri.jsgcore.render.ChevronEnum;

public class StargateActivation extends Activation<ChevronEnum> {

	public StargateActivation(ChevronEnum textureKey, long stateChange, boolean dim) {
		super(textureKey, stateChange, dim);
	}

	@Override
	protected float getMaxStage() {
		return 10;
	}
	
	@Override
	protected float getTickMultiplier() {
		return 3;
	}
}
