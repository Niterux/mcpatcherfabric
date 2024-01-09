package io.github.niterux.mcpatcherfabric.hdtextures;


import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.texture.TextureManager;

public interface reInitializeInterface {
	void mcpatcherfabric$reInitialize(GameOptions options, String fontPath, TextureManager textureManager);
}
