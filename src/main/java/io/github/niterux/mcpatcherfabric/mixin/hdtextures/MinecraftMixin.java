package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.pclewis.mcpatcher.mod.TextureUtils;
import io.github.niterux.mcpatcherfabric.hdtextures.ResizeTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.TextureAtlasSprite;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixin {
	@Shadow
	public TextureManager textureManager;

	@Inject(method = "init()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;textureManager:Lnet/minecraft/client/render/texture/TextureManager;", ordinal = 0))
	public void beforeTextureManager(CallbackInfo info) {
		TextureUtils.setMinecraft((Minecraft) (Object) this);
	}

	@Inject(method = "init()V", at = @At(value = "FIELD", target = "Lnet/minecraft/client/Minecraft;textRenderer:Lnet/minecraft/client/render/TextRenderer;", ordinal = 0))
	private void afterTextureManager(CallbackInfo ci) {
		TextureUtils.setTileSize();
		((ResizeTile) this.textureManager).mcpatcherfabric$setTileSize((Minecraft) (Object) this);
	}

	@WrapOperation(method = "init()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureManager;addSprite(Lnet/minecraft/client/render/texture/TextureAtlasSprite;)V"))
	private void cancelAddSprite(TextureManager instance, TextureAtlasSprite textureAtlasSprite, Operation<Void> original) {
	}
}
