package dev.tauri.jsgcore.loader.model;

import dev.tauri.jsgcore.loader.FolderLoader;
import dev.tauri.jsgcore.utils.Logging;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModelLoader {

	private final String modId;
	private final Class modMainClass;
	public final String modelsPath;
	private final Map<ResourceLocation, OBJModel> LOADED_MODELS = new HashMap<>();

	public ModelLoader(String modId, Class modMainClass){
		this.modId = modId;
		this.modMainClass = modMainClass;
		this.modelsPath = "assets/" + modId + "/models/tesr";
	}
	
	public OBJModel getModel(ResourceLocation resourceLocation) {
		return LOADED_MODELS.get(resourceLocation);
	}

	public void loadModels() {
		try {
			LOADED_MODELS.clear();

			List<String> modelPaths = FolderLoader.getAllFiles(modMainClass, modelsPath, ".obj");

			long start = System.currentTimeMillis();

			Logging.info("Started loading models...");
			for (String modelPath : modelPaths) {
				Logging.info("Loading model: " + modelPath);
				String modelResourcePath = modelPath.replaceFirst("assets/" + modId + "/", "");
				LOADED_MODELS.put(new ResourceLocation(modId, modelResourcePath), OBJLoader.loadModel(modelPath));
			}

			Logging.info("Loaded " + modelPaths.size() + " models in " + (System.currentTimeMillis() - start) + " ms");
		}
		catch (Exception e){
			Logging.error("Error while loading models for mod " + modMainClass.toString());
			e.printStackTrace();
		}
	}
	
	public ResourceLocation getModelResource(String model) {
		return new ResourceLocation(modId, "models/tesr/" + model);
	}
}
