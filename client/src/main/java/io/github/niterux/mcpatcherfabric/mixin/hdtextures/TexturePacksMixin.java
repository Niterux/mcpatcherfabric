package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import io.github.niterux.mcpatcherfabric.hdtextures.setTileInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.pack.TexturePack;
import net.minecraft.client.resource.pack.TexturePacks;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import com.pclewis.mcpatcher.mod.TextureUtils;
@Mixin(TexturePacks.class)
public class TexturePacksMixin {
	@Shadow
	Minecraft minecraft;
	@Inject(method = "Lnet/minecraft/client/resource/pack/TexturePacks;select(Lnet/minecraft/client/resource/pack/TexturePack;)Z", at = @At(value = "TAIL"))
	private void testMixin(TexturePack par1, CallbackInfoReturnable<Boolean> cir) {
		TextureUtils.setTileSize();
		Minecraft minecraft = this.minecraft;
		((setTileInterface)this.minecraft.textureManager).setTileSize(minecraft);
		TextureUtils.setFontRenderer(this.minecraft);
	}
}
