package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.pclewis.mcpatcher.mod.TextureUtils;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.texture.ClockSprite;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Slice;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Mixin(ClockSprite.class)
public class ClockSpriteMixin {
	@ModifyExpressionValue(method = "*", at = @At(value = "CONSTANT", args = "intValue=256"))
	private int fixImageAndLoopSize(int original) {
		return TileSize.int_numPixels;
	}

	@ModifyArg(method = "<init>(Lnet/minecraft/client/Minecraft;)V", at = @At(value = "INVOKE", target = "Ljava/lang/Class;getResource(Ljava/lang/String;)Ljava/net/URL;"), index = 0)
	private String mcpatcherGetResourceString(String path, @Share("resourcePath") LocalRef<String> resourcePath) {
		resourcePath.set(path);
		return "";
	}

	@WrapOperation(method = "<init>(Lnet/minecraft/client/Minecraft;)V", at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;read(Ljava/net/URL;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage mcpatcherCacheImageRead(URL e, Operation<BufferedImage> original, @Share("resourcePath") LocalRef<String> resourcePath) throws IOException {
		return TextureUtils.getResourceAsBufferedImage(resourcePath.get());
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/Minecraft;)V",
		at = {@At(value = "CONSTANT", ordinal = 1, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 3, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 4, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 5, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 6, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 7, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 8, args = "intValue=16"),
			@At(value = "CONSTANT", ordinal = 9, args = "intValue=16")})
	private int fixSpriteSize(int sixteen) {
		return TileSize.int_size;
	}

	@ModifyExpressionValue(method = "tick()V", at = {@At(value = "CONSTANT", args = "intValue=16")}, slice = @Slice(from = @At(value = "CONSTANT", ordinal = 1, args = "intValue=16"), to = @At(value = "CONSTANT", ordinal = 3, args = "intValue=16")))
	private int fixSpriteUpdateSizeInt(int original) {
		return TileSize.int_size;
	}

	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "doubleValue=16.0"))
	private double fixSpriteUpdateSizeDouble(double original) {
		return TileSize.double_size;
	}

	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "intValue=15"))
	private int tileSizeMinusOneInt(int original) {
		return TileSize.int_sizeMinus1;
	}

	@ModifyExpressionValue(method = "tick()V", at = @At(value = "CONSTANT", args = "doubleValue=15.0"))
	private double tileSizeMinusOneDouble(double original) {
		return TileSize.double_sizeMinus1;
	}
}
