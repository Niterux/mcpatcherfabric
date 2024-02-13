package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.LavaSideSprite;
import net.minecraft.client.render.texture.WaterSideSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(value = {LavaSideSprite.class, WaterSideSprite.class})
public class LiquidSideSpriteMixin {
	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=255", ordinal = 0))
	private int pixelsMinusOne(int original) {
		return TileSize.int_numPixelsMinus1;
	}
}
