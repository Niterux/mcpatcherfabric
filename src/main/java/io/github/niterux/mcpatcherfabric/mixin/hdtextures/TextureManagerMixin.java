package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.pclewis.mcpatcher.mod.TextureUtils;
import com.pclewis.mcpatcher.mod.TileSize;
import io.github.niterux.mcpatcherfabric.hdtextures.ResizeTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.TextureAtlas;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.List;

@Mixin(TextureManager.class)
public abstract class TextureManagerMixin implements ResizeTile {
	@Shadow
	private ByteBuffer buffer;

	@Shadow
	public abstract void reload();

	@Shadow
	private List<TextureAtlas> sprites;

	@Override
	public void mcpatcherfabric$setTileSize(Minecraft minecraft) {
		this.buffer = MemoryTracker.createByteBuffer(TileSize.int_glBufferSize);
		this.reload();
		TextureUtils.refreshTextureFX(this.sprites);
	}

	@Inject(method = "<init>(Lnet/minecraft/client/resource/pack/TexturePacks;Lnet/minecraft/client/options/GameOptions;)V", at = @At(value = "TAIL"))
	private void fieldFix(CallbackInfo info) {
		buffer = MemoryTracker.createByteBuffer(TileSize.int_glBufferSize);
	}

	@ModifyArg(method = "getColors(Ljava/lang/String;)[I", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/pack/TexturePack;getResource(Ljava/lang/String;)Ljava/io/InputStream;"), index = 0)
	private String mcpatcherGetResourceStringColor(String path, @Share("resourcePath") LocalRef<String> resourcePath) {
		resourcePath.set(path);
		return "";
	}

	@WrapOperation(method = "getColors(Ljava/lang/String;)[I", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureManager;readImage(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage mcpatcherFixImageReadColor(TextureManager instance, InputStream inputStream, Operation<BufferedImage> original, @Share("resourcePath") LocalRef<String> resourcePath) throws IOException {
		return TextureUtils.getResourceAsBufferedImage(resourcePath.get());
	}

	@ModifyArg(method = "load(Ljava/lang/String;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/pack/TexturePack;getResource(Ljava/lang/String;)Ljava/io/InputStream;"), index = 0)
	private String mcpatcherGetResourceStringLoad(String path, @Share("resourcePath") LocalRef<String> resourcePath) {
		resourcePath.set(path);
		return "";
	}

	@WrapOperation(method = "load(Ljava/lang/String;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureManager;readImage(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage mcpatcherFixImageReadLoad(TextureManager instance, InputStream inputStream, Operation<BufferedImage> original, @Share("resourcePath") LocalRef<String> resourcePath) throws IOException {
		return TextureUtils.getResourceAsBufferedImage(resourcePath.get());
	}

	@ModifyArg(method = "load(Ljava/lang/String;)I", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureManager;bind(Ljava/awt/image/BufferedImage;I)V"), slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/client/render/texture/TextureManager;image:Ljava/awt/image/BufferedImage;", ordinal = 0), to = @At(value = "FIELD", target = "Lnet/minecraft/client/render/texture/TextureManager;image:Ljava/awt/image/BufferedImage;", ordinal = 1)), index = 0)
	private BufferedImage mcpatcherFixImageReadGeneral(BufferedImage image, @Share("resourcePath") LocalRef<String> resourcePath) throws IOException {
		return TextureUtils.getResourceAsBufferedImage(resourcePath.get());
	}

	@ModifyArg(method = "reload()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resource/pack/TexturePack;getResource(Ljava/lang/String;)Ljava/io/InputStream;"), index = 0)
	private String mcpatcherGetResourceStringReload(String path, @Share("resourcePath") LocalRef<String> resourcePath) {
		resourcePath.set(path);
		return "";
	}

	@WrapOperation(method = "reload()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureManager;readImage(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage mcpatcherFixImageReadReload(TextureManager instance, InputStream inputStream, Operation<BufferedImage> original, @Share("resourcePath") LocalRef<String> resourcePath) throws IOException {
		return TextureUtils.getResourceAsBufferedImage(resourcePath.get());
	}

	@Inject(method = {"bind(Ljava/awt/image/BufferedImage;I)V", "bind([IIII)V"}, at = @At(value = "INVOKE", target = "java/nio/ByteBuffer.clear ()Ljava/nio/Buffer;", shift = At.Shift.AFTER, ordinal = 0))
	private void resizeTextureBufferBind(CallbackInfo ci, @Local(ordinal = 0) byte[] byArray) {
		if (this.buffer.capacity() != byArray.length) this.buffer = MemoryTracker.createByteBuffer(byArray.length);
	}

	@Inject(method = "tick()V", at = @At(value = "INVOKE", target = "java/nio/ByteBuffer.clear ()Ljava/nio/Buffer;", shift = At.Shift.AFTER, ordinal = 0))
	private void resizeTextureBufferTick(CallbackInfo ci, @Local(ordinal = 0) TextureAtlas textureAtlas) {
		if (this.buffer.capacity() != textureAtlas.buffer.length)
			this.buffer = MemoryTracker.createByteBuffer(textureAtlas.buffer.length);
	}

	@WrapOperation(method = "addSprite(Lnet/minecraft/client/render/texture/TextureAtlas;)V", at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z"))
	private boolean mcpatcherRegisterSprite(List<TextureAtlas> instance, Object sprite, Operation<Boolean> original) {
		TextureUtils.registerTextureFX(instance, (TextureAtlas) sprite);
		return true;
	}

	@WrapOperation(method = "addSprite(Lnet/minecraft/client/render/texture/TextureAtlas;)V", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/texture/TextureAtlas;tick()V"))
	private void mcpatcherRemoveSpriteTick(TextureAtlas instance, Operation<Void> original) {
	}

	@ModifyExpressionValue(method = "tick()V", at = {@At(value = "CONSTANT", args = "intValue=16", ordinal = 1), @At(value = "CONSTANT", args = "intValue=16", ordinal = 2), @At(value = "CONSTANT", args = "intValue=16", ordinal = 4), @At(value = "CONSTANT", args = "intValue=16", ordinal = 5), @At(value = "CONSTANT", args = "intValue=16", ordinal = 6), @At(value = "CONSTANT", args = "intValue=16", ordinal = 7), @At(value = "CONSTANT", args = "intValue=16", ordinal = 12), @At(value = "CONSTANT", args = "intValue=16", ordinal = 13)})
	private int useProperTileSize(int original) {
		return TileSize.int_size;
	}
}
