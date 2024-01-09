package io.github.niterux.mcpatcherfabric.mixin.hdfont;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;

import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Debug(export = true)
@Mixin(TextRenderer.class)
public class HDFontMixin {
	private int FontImageWidth;
	@ModifyVariable(
		method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
	at = @At(
			value = "INVOKE_ASSIGN",
			target = "Ljava/awt/image/BufferedImage;getWidth()I"
	))
	private int myHandlerMethod(int variable) { //the error is fake don't believe the ide's LIES
		System.out.println(variable);
		return this.FontImageWidth = variable;
	}
	@ModifyConstant(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		constant = @Constant(intValue = 7, ordinal = 0))
	private int fixWidthModifier(int seven){
		return FontImageWidth / 16 - 1;
	}
	@ModifyConstant(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		constant = @Constant(intValue = 8, ordinal = 0))
	private int fixWidthModifier_1(int eight){
		return FontImageWidth / 16;
	}
	@ModifyConstant(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		constant = @Constant(intValue = 8, ordinal = 1))
	private int fixWidthModifier_2(int eight){
		return FontImageWidth / 16;
	}

    @ModifyConstant(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		constant = @Constant(intValue = 8, ordinal = 2))
	private int fixWidthModifier_3(int eight){
		return FontImageWidth / 16;
	}

    @ModifyConstant(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		constant = @Constant(intValue = 2, ordinal = 0))
	private int fixWidthModifier_4(int two){
		return FontImageWidth / 64;
	}

    @ModifyConstant(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		constant = @Constant(intValue = 2, ordinal = 1))
	private int fixWidthModifier_5(int eight, @Local(ordinal = 2) int loop){
		return (128 * loop + 256) / FontImageWidth - loop;
	}

    @ModifyConstant(method = "Lnet/minecraft/client/render/TextRenderer;splitAndGetHeight(Ljava/lang/String;I)I",
		constant = @Constant(intValue = 8, ordinal = 0))
	private int fixWidthModifier_6(int eight, @Local(ordinal = 1) int loop){
		return loop / 16;
	}
}
