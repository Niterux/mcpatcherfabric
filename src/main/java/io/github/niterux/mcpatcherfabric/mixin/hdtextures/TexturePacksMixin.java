package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.pclewis.mcpatcher.mod.TextureUtils;
import io.github.niterux.mcpatcherfabric.hdtextures.ResizeTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resource.pack.TexturePack;
import net.minecraft.client.resource.pack.TexturePacks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TexturePacks.class)
public class TexturePacksMixin {
	@Shadow
	private Minecraft minecraft;

	@Inject(method = "select(Lnet/minecraft/client/resource/pack/TexturePack;)Z", at = @At(value = "TAIL"))
	private void setTextureSize(TexturePack par1, CallbackInfoReturnable<Boolean> cir) {
		TextureUtils.setTileSize();
		((ResizeTile) this.minecraft.textureManager).mcpatcherfabric$setTileSize(this.minecraft);
		TextureUtils.setFontRenderer(this.minecraft);
	}
}
