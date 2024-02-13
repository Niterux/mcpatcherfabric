package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.pclewis.mcpatcher.mod.TileSize;
import net.minecraft.client.render.HeldItemRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(HeldItemRenderer.class)
public class HeldItemRendererMixin {
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = {@At(value = "CONSTANT", args = "intValue=16", ordinal = 1), @At(value = "CONSTANT", args = "intValue=16", ordinal = 3), @At(value = "CONSTANT", args = "intValue=16", ordinal = 5), @At(value = "CONSTANT", args = "intValue=16", ordinal = 7)}, slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/entity/living/LivingEntity;getIconSprite(Lnet/minecraft/item/ItemStack;)I")))
	private int fixVertexInts(int original){
		return TileSize.int_size;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "CONSTANT", args = "floatValue=256.0f"))
	private float fixVertexFloatSize16(float original){
		return TileSize.float_size16;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "CONSTANT", args = "floatValue=15.99f"))
	private float fixVertexFloatSize0_01(float original){
		return TileSize.float_sizeMinus0_01;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "CONSTANT", args = "intValue=16"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;normal(FFF)V", ordinal = 2)))
	private int fixPixelLoop(int original){
		return TileSize.int_size;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "CONSTANT", args = "floatValue=16.0f"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;normal(FFF)V", ordinal = 2)))
	private float fixPixelLoopFloatSize(float original){
		return TileSize.float_size;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "CONSTANT", args = "floatValue=0.001953125f"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;normal(FFF)V", ordinal = 2)))
	private float fixPixelLoopTexNudge(float original){
		return TileSize.float_texNudge;
	}
	@ModifyExpressionValue(method = "render(Lnet/minecraft/entity/living/LivingEntity;Lnet/minecraft/item/ItemStack;)V", at = @At(value = "CONSTANT", args = "floatValue=0.0625f"), slice = @Slice(from = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/vertex/BufferBuilder;normal(FFF)V", ordinal = 2)))
	private float fixPixelLoopReciprocal(float original){
		return TileSize.float_reciprocal;
	}
}
