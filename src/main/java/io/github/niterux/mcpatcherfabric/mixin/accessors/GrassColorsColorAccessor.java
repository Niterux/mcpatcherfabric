package io.github.niterux.mcpatcherfabric.mixin.accessors;

import net.minecraft.client.world.color.GrassColors;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(GrassColors.class)
public interface GrassColorsColorAccessor {
	@Accessor("colors")
    static int[] getColors() {
		throw new AssertionError();
	}
}
