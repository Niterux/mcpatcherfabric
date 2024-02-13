package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.LavaSideSprite;
import net.minecraft.client.render.texture.LavaSprite;
import net.minecraft.client.render.texture.WaterSideSprite;
import net.minecraft.client.render.texture.WaterSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {LavaSprite.class, LavaSideSprite.class, WaterSprite.class, WaterSideSprite.class})
public class LiquidSpriteMixin {
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=16"))
	private int tileSize(int original){
		return TileSize.int_size;
	}
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=15"))
	private int tileSizeMinusOne(int original){
		return TileSize.int_sizeMinus1;
	}
	@ModifyExpressionValue(method = "*", at = @At(value = "CONSTANT", args = "intValue=256"))
	private int fixImageAndLoopSize(int original){
		return TileSize.int_numPixels;
	}
}
