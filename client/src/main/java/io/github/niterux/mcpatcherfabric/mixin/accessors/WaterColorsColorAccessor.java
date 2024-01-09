package io.github.niterux.mcpatcherfabric.mixin.accessors;

import net.minecraft.client.world.color.WaterColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WaterColors.class)
public interface WaterColorsColorAccessor {
	@Accessor("colors")
	public static int[] getColors() {
		throw new AssertionError();
	}
}
