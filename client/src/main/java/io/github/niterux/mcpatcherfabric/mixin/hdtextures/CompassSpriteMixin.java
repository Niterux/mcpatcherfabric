package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.pclewis.mcpatcher.mod.TextureUtils;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.CompassSprite;
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
	private int[] image;

	@Inject(method = "<init>(Lnet/minecraft/client/Minecraft;)V", at = @At(value = "TAIL"))
	private void fieldFix(CallbackInfo info) {
		image = new int[TileSize.int_numPixels];
	}

	@Redirect(method = "<init>(Lnet/minecraft/client/Minecraft;)V",
		at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;read(Ljava/net/URL;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage testMixin(URL e) throws IOException {
		return TextureUtils.getResourceAsBufferedImage("/gui/items.png");
	}

	@ModifyConstant(method = "<init>(Lnet/minecraft/client/Minecraft;)V",
		constant = {@Constant(intValue = 16, ordinal = 1),
			@Constant(intValue = 16, ordinal = 3),
			@Constant(intValue = 16, ordinal = 4),
			@Constant(intValue = 16, ordinal = 5),
			@Constant(intValue = 16, ordinal = 6)})
	private int fixSpriteSize(int sixteen) {
		return TileSize.int_size;
	}

	@ModifyConstant(method = "tick()V", constant = @Constant(intValue = 256))
	private int fixLoopLength(int twoFiftySix) {
		return TileSize.int_numPixels;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = @Constant(intValue = -4, ordinal = 0))
	private int fixCompassCrossMin(int negativeFour) {
		return TileSize.int_compassCrossMin;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = @Constant(intValue = 4, ordinal = 0))
	private int fixCompassCrossMax(int four) {
		return TileSize.int_compassCrossMax;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = @Constant(doubleValue = 7.5))
	private double fixCompassCenterMin(double sevenPointFive) {
		return TileSize.double_compassCenterMin;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = @Constant(doubleValue = 8.5))
	private double fixCompassCenterMax(double eightPointFive) {
		return TileSize.double_compassCenterMax;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = {@Constant(intValue = 16, ordinal = 0), @Constant(intValue = 16, ordinal = 2)})
	private int fixPositioning(int eightPointFive) {
		return TileSize.int_size;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = @Constant(intValue = 16, ordinal = 1))
	private int fixLoopLength_2(int sixteen) {
		return TileSize.int_compassNeedleMax;
	}

	@ModifyConstant(method = "tick()V",
		slice = @Slice(from = @At(value = "INVOKE", target = "Ljava/lang/Math;cos(D)D")),
		constant = @Constant(intValue = -8, ordinal = 0))
	private int fixLoopStart(int negativeEight) {
		return TileSize.int_compassNeedleMin;
	}
}
