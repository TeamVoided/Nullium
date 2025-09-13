package org.teamvoided.nullium.mixin.block;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.teamvoided.nullium.init.NulGameRules.*;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {

    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (!world.isClient && entity instanceof LivingEntity livingEntity) {
            if (!getBoolRule(world, FRAMERS_TRAMPLE) && livingEntity instanceof VillagerEntity villager && villager.getVillagerData().getProfession() == VillagerProfession.FARMER) {
                ci.cancel();
            }
            if (!getBoolRule(world, FEATHER_FALLING_TRAMPLE)) {
                var item = livingEntity.getEquippedStack(EquipmentSlot.FEET);
                var ff = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getHolder(Enchantments.FEATHER_FALLING);
                if (ff.isEmpty()) return;
                if (!item.isEmpty() && EnchantmentHelper.getLevel(ff.get(), item) > 0) {
                    ci.cancel();
                }
            }
        }
    }
}
