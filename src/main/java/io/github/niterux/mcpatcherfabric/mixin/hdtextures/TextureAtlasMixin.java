package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.TextureAtlas;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextureAtlas.class)
public class TextureAtlasMixin {
	@SuppressWarnings("unused")
	@Shadow
	public byte[] buffer = new byte[TileSize.int_numBytes];
}
