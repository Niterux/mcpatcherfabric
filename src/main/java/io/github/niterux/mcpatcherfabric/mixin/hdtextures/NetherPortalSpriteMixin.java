package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.NetherPortalSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NetherPortalSprite.class)
public class NetherPortalSpriteMixin {
	@Shadow
	private byte[][] images = new byte[32][TileSize.int_numBytes];
	@ModifyExpressionValue(method = "<init>()V", at = @At(value = "CONSTANT", args = "intValue=16"))
	private int fixTextureSizeInt(int original){
		return TileSize.int_size;
	}
	@ModifyExpressionValue(method = "<init>()V", at = @At(value = "CONSTANT", args = "floatValue=16.0f"))
	private float fixTextureSizeFloat(float original){
		return TileSize.float_size;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=256"))
	private int fixUpdateLoop(int original){
		return TileSize.int_numPixels;
	}
}
