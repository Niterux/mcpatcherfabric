package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.llamalad7.mixinextras.sugar.Local;
import com.pclewis.mcpatcher.mod.TextureUtils;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Mixin(TextRenderer.class)
public class TextRendererMixin {
	@Redirect(method = "<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;read(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage useCachedFontImage(InputStream variable, GameOptions options, String fontPath) throws IOException {
		return TextureUtils.getResourceAsBufferedImage(fontPath);
	}

}


