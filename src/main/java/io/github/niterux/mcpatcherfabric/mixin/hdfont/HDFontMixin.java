package io.github.niterux.mcpatcherfabric.mixin.hdfont;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.awt.image.BufferedImage;

@Mixin(TextRenderer.class)
public class HDFontMixin {
	@WrapOperation(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V", at = @At(value = "INVOKE", target = "Ljava/awt/image/BufferedImage;getWidth()I"))
	private int myHandlerMethod(BufferedImage instance, Operation<Integer> original, @Share("fontImageWidth")LocalIntRef fontImageWidth) {
		fontImageWidth.set(original.call(instance));
		return fontImageWidth.get();
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		at = @At(value = "CONSTANT", args = "intValue=7", ordinal = 0))
	private int fixCharacterWidthCalculator(int seven, @Share("fontImageWidth") LocalIntRef fontImageWidth) {
		return fontImageWidth.get() / ((seven + 1) * 2) - 1;
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		at = {@At(value = "CONSTANT", ordinal = 0, args = "intValue=8"), @At(value = "CONSTANT", ordinal = 1, args = "intValue=8"), @At(value = "CONSTANT", ordinal = 2, args = "intValue=8")})
	private int fixCharacterWidthCalculator_1(int eight, @Share("fontImageWidth") LocalIntRef fontImageWidth) {
		return fontImageWidth.get() / (eight * 2);
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=2"))
	private int fixCharacterWidthCalculator_2(int two, @Share("fontImageWidth") LocalIntRef fontImageWidth) {
		return fontImageWidth.get() / (two * 32);
	}

	@ModifyExpressionValue(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		at = @At(value = "CONSTANT", ordinal = 1, args = "intValue=2"))
	private int fixCharacterWidthCalculator_3(int two, @Local(ordinal = 5) int loop, @Share("fontImageWidth") LocalIntRef fontImageWidth) {
		return (128 * loop + 256) / fontImageWidth.get() - loop;
	}

	@ModifyExpressionValue(method = "splitAndGetHeight(Ljava/lang/String;I)I",
		at = @At(value = "CONSTANT", ordinal = 0, args = "intValue=8"))
	private int fixCharacterRenderPosition(int eight, @Local(ordinal = 1) int loop) {
		return loop / (eight * 2);
	}
}
