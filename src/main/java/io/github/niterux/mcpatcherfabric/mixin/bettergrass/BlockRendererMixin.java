package io.github.niterux.mcpatcherfabric.mixin.bettergrass;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalIntRef;
import com.mojang.blaze3d.vertex.BufferBuilder;
import net.minecraft.block.Block;
import net.minecraft.client.render.BlockRenderer;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRenderer.class)
public class BlockRendererMixin {
	@Shadow
	private WorldView world;
	@Shadow
	private int sprite;

	@Inject(method = "tessellateWithMaxAmbientOcclusion(Lnet/minecraft/block/Block;IIIFFF)Z", at = @At(value = "FIELD", target = "Lnet/minecraft/client/render/BlockRenderer;renderFaceAlways:Z", ordinal = 0))
	private void enableGrassColoringAO(Block block, int x, int y, int z, float r, float g, float b, CallbackInfoReturnable<Boolean> cir, @Local(ordinal = 6) LocalIntRef var15, @Local(ordinal = 7) LocalIntRef var16, @Local(ordinal = 8) LocalIntRef var17, @Local(ordinal = 9) LocalIntRef var18) {
		if (block.sprite == 3 || this.sprite >= 0) {
			if (block.getSprite(this.world, x, y, z, 2) == 0) {
				var15.set(1);
			}
			if (block.getSprite(this.world, x, y, z, 3) == 0) {
				var16.set(1);
			}
			if (block.getSprite(this.world, x, y, z, 4) == 0) {
				var17.set(1);
			}
			if (block.getSprite(this.world, x, y, z, 5) == 0) {
				var18.set(1);
			}
		}
	}

	@WrapOperation(method = "tessellateWithoutAmbientOcclusion(Lnet/minecraft/block/Block;IIIFFF)Z", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSprite(Lnet/minecraft/world/WorldView;IIII)I"), slice = @Slice(from = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getSprite(Lnet/minecraft/world/WorldView;IIII)I", ordinal = 2)))
	private int enableGrassColoringNonAO(Block instance, WorldView world, int x, int y, int z, int face, Operation<Integer> original, @Local(ordinal = 0) BufferBuilder bufferBuilderLocalRef, @Local(ordinal = 20) float lightLevel, @Local(ordinal = 7) float red, @Local(ordinal = 8) float green, @Local(ordinal = 9) float blue) {
		int sprite = original.call(instance, world, x, y, z, face);
		if (sprite == 0)
			bufferBuilderLocalRef.color(red * lightLevel, green * lightLevel, blue * lightLevel);
		return sprite;
	}
}
