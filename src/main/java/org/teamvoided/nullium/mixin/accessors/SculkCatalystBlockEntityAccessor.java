package org.teamvoided.nullium.mixin.accessors;

import net.minecraft.block.entity.SculkCatalystBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(SculkCatalystBlockEntity.class)
public interface SculkCatalystBlockEntityAccessor {

    @Accessor("catalystListener")
    SculkCatalystBlockEntity.CatalystListener nullium_catalystListener();
}
