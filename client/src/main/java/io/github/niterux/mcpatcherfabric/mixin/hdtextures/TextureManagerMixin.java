package io.github.niterux.mcpatcherfabric.mixin.hdtextures;

import com.pclewis.mcpatcher.mod.TextureUtils;
import io.github.niterux.mcpatcherfabric.hdtextures.setTileInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.render.texture.TextureManager;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import com.mojang.blaze3d.platform.MemoryTracker;
import com.pclewis.mcpatcher.mod.TileSize;

import java.nio.ByteBuffer;
import java.util.List;

@Mixin(TextureManager.class)
public abstract class TextureManagerMixin implements setTileInterface {
	@Shadow
	private ByteBuffer buffer;

	@Shadow
	public abstract void reload();

	@Shadow
	private List sprites;

	@Override
	public void setTileSize(Minecraft minecraft) {
		this.buffer = MemoryTracker.createByteBuffer(TileSize.int_glBufferSize);
		this.reload();
		TextureUtils.refreshTextureFX(this.sprites);
	}

}
