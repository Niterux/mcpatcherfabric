package io.github.niterux.mcpatcherfabric.mixin.bettergrass;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import net.minecraft.block.GrassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.world.WorldView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
	@Unique
	private static int[][] grassMatrix;

	@Inject(method = "<init>(I)V", at = @At("TAIL"))
	private void initializeGrassMatrix(int id, CallbackInfo ci) {
		if (grassMatrix == null) {
			grassMatrix = new int[4][2];
			grassMatrix[0][1] = -1;
			grassMatrix[1][1] = 1;
			grassMatrix[2][0] = -1;
			grassMatrix[3][0] = 1;
		}
	}

	@ModifyReturnValue(method = "getSprite(Lnet/minecraft/world/WorldView;IIII)I", at = @At("RETURN"))
	private int addBetterGrassLogic(int original, WorldView world, int x, int y, int z, int face) {
		if (original == 3 || original == 68) {
			face -= 2;
			if (original == 3) {
				if (world.getBlock(x + grassMatrix[face][0], y - 1, z + grassMatrix[face][1]) != 2) {
					return 3;
				}
				return 0;
			}

			Material material = world.getMaterial(x + grassMatrix[face][0], y, z + grassMatrix[face][1]);
			if (material != Material.SNOW_LAYER && material != Material.SNOW) {
				return 68;
			}
			return 66;
		} else {
			return original;
		}

	}
}
