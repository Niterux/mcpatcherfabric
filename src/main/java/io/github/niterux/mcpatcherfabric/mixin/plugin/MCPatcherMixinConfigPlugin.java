package io.github.niterux.mcpatcherfabric.mixin.plugin;

import com.pclewis.mcpatcher.MCPatcherUtils;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import java.util.List;
import java.util.Set;

public class MCPatcherMixinConfigPlugin implements IMixinConfigPlugin {
	private boolean enableMixins = true;
	@Override
	public void onLoad(String mixinPackage) {
		String[] packageNames = mixinPackage.split("\\.");
		String modName = packageNames[packageNames.length - 1];
		String properName = MCPatcherUtils.MODS.get(modName);
		enableMixins = MCPatcherUtils.getModEnabled(properName);
	}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		return enableMixins;
	}

	@Override
	public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

	}

	@Override
	public List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

	}
}
