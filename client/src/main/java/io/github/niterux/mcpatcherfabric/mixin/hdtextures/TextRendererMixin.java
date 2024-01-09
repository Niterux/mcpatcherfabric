package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.pclewis.mcpatcher.mod.TextureUtils;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.TextRenderer;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Debug(export = true)
@Mixin(TextRenderer.class)
public class TextRendererMixin {
	@Redirect(method = "Lnet/minecraft/client/render/TextRenderer;<init>(Lnet/minecraft/client/options/GameOptions;Ljava/lang/String;Lnet/minecraft/client/render/texture/TextureManager;)V",
		at = @At(value = "INVOKE", target = "Ljavax/imageio/ImageIO;read(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;"))
	private BufferedImage testMixin(InputStream variable, GameOptions options, String fontPath) throws IOException {
		System.out.println(variable);
        return TextureUtils.getResourceAsBufferedImage(fontPath);
    }

}


