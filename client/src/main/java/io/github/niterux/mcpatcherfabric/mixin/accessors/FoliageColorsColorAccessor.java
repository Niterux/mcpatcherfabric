package io.github.niterux.mcpatcherfabric.mixin.accessors;

import net.minecraft.client.world.color.FoliageColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(FoliageColors.class)
public interface FoliageColorsColorAccessor {
	@Accessor("colors")
	public static int[] getColors() {
		throw new AssertionError();
	}
}
