package org.teamvoided.nullium.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.village.VillagerProfession;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.teamvoided.nullium.module.NulliumGameRules.*;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {

    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void onLandedUpon(World world, BlockState state, BlockPos pos, Entity entity, float fallDistance, CallbackInfo ci) {
        if (!world.isClient) {
            var farmerRule = !getBoolRule(world, FRAMERS_TRAMPL);
            var featherFallingRule = !getBoolRule(world, FEATHER_FALLING_TRAMPLE);
            if ((farmerRule || featherFallingRule) && entity instanceof LivingEntity livingEntity) {
                if (farmerRule && livingEntity instanceof VillagerEntity villager && villager.getVillagerData().getProfession() == VillagerProfession.FARMER) {
                    ci.cancel();
                }
                if (featherFallingRule && livingEntity instanceof PlayerEntity player) {
                    var item = player.getInventory().armor.getFirst();
                    var ff = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getHolderOrThrow(Enchantments.FEATHER_FALLING);
                    if (!item.isEmpty() && EnchantmentHelper.method_8225(ff, item) > 0) {
                        ci.cancel();
                    }
                }
            }
        }
    }
}
