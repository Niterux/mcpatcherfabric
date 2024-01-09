package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import io.github.niterux.mcpatcherfabric.hdtextures.reInitializeInterface;
import io.github.niterux.mcpatcherfabric.mixinplugins.ReInitializable;
import io.github.niterux.mcpatcherfabric.mixinplugins.ReInitializer;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;

@ReInitializable
@Mixin(value = TextRenderer.class, priority = 9999)
public abstract class TextRendererMixinCursed implements reInitializeInterface {
	@ReInitializer
	public void mcpatcherfabric$reInitialize(GameOptions options, String fontPath, TextureManager textureManager) {
	}
}


