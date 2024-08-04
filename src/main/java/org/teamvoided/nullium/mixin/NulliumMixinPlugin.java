package org.teamvoided.nullium.mixin;

import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;
import org.teamvoided.nullium.config.NulConfigManager;
import org.teamvoided.nullium.config.data.MainData;

public class NulliumMixinPlugin implements IMixinConfigPlugin {

	public NulliumMixinPlugin() {
		NulConfigManager.loadMain();
	}

	@Override
	public void onLoad(String mixinPackage) {}

	@Override
	public String getRefMapperConfig() {
		return null;
	}

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		MainData cfg = NulConfigManager.getMain().data();
		return switch (mixinClassName) {
			case "org.teamvoided.nullium.mixin.AnvilScreenHandlerMixin" -> cfg.enableBlacksmith();
			case "org.teamvoided.nullium.mixin.PlaceBlockGoalMixin",
                 "org.teamvoided.nullium.mixin.EndermanEntityMixinE" -> cfg.enableHolderman();
            case "org.teamvoided.nullium.mixin.GlowingFoodComponentsMixin" -> cfg.enableGlowBerriesGlow();
			case "org.teamvoided.nullium.mixin.CopperBulbBlockMixin" -> cfg.enableCopperBulbRevert();
			default -> true;
		};
	}

	@Override
	public void acceptTargets(java.util.Set<String> myTargets, java.util.Set<String> otherTargets) {}

	@Override
	public java.util.List<String> getMixins() {
		return null;
	}

	@Override
	public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

	@Override
	public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}

