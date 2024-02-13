package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.TextureAtlasSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TextureAtlasSprite.class)
public class TextureAtlasSpriteMixin {
	@SuppressWarnings("unused")
	@Shadow
	public byte[] buffer = new byte[TileSize.int_numBytes];
}
