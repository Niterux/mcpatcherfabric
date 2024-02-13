package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pclewis.mcpatcher.mod.TextureUtils;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.CompassSprite;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Mixin(CompassSprite.class)
public class CompassSpriteMixin {
	@Shadow
	private int[] image = new int[TileSize.int_numPixels];

	@Redirect(method = "<init>(Lnet/minecraft/client/Minecraft;)V",
		at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;read(Ljava/net/URL;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage testMixin(URL e) throws IOException {
		return TextureUtils.getResourceAsBufferedImage("/gui/items.png");
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/Minecraft;)V",
		at = {@At(value = "CONSTANT", ordinal = 1, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 3, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 4, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 5, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 6, args = "intValue=16")})
	private int fixSpriteSize(int sixteen) {
		return TileSize.int_size;
	}

	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=256"))
	private int fixLoopLength(int twoFiftySix) {
		return TileSize.int_numPixels;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=-4"))
	private int fixCompassCrossMin(int negativeFour) {
		return TileSize.int_compassCrossMin;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=4"))
	private int fixCompassCrossMax(int four) {
		return TileSize.int_compassCrossMax;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = @At(value = "CONSTANT", args = "doubleValue=7.5"))
	private double fixCompassCenterMin(double sevenPointFive) {
		return TileSize.double_compassCenterMin;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = @At(value = "CONSTANT", args = "doubleValue=8.5"))
	private double fixCompassCenterMax(double eightPointFive) {
		return TileSize.double_compassCenterMax;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = {@At(value = "CONSTANT", ordinal = 0, args = "intValue=16"), @At(value = "CONSTANT", ordinal = 2, args = "intValue=16")})
	private int fixPositioning(int sixteen) {
		return TileSize.int_size;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = @At(value = "CONSTANT", ordinal = 1, args = "intValue=16"))
	private int fixLoopLength_2(int sixteen) {
		return TileSize.int_compassNeedleMax;
	}

	@ModifyExpressionValue(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=-8"))
	private int fixLoopStart(int negativeEight) {
		return TileSize.int_compassNeedleMin;
	}
}
