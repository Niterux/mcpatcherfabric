package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.FireSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(FireSprite.class)
public class FireSpriteMixin {
	@ModifyExpressionValue(method = "<init>(I)V", at = @At(value = "CONSTANT", args = "intValue=320"))
	private int fixImageSize(int original){
		return TileSize.int_flameArraySize;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=256"))
	private int fixLoopLength(int original){
		return TileSize.int_numPixels;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=16"))
	private int useTileSize(int original){
		return TileSize.int_size;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=20"))
	private int useFlameHeight(int original){
		return TileSize.int_flameHeight;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=19"))
	private int useFlameHeightMinus1(int original){
		return TileSize.int_flameHeightMinus1;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "floatValue=1.06f"))
	private float useFlameNudge(float original){
		return TileSize.float_flameNudge;
	}
}
