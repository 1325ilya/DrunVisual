package ru.drunvisual.mixin.accessor;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(targets = {"net/minecraft/entity/player/ItemCooldownManager$Entry"})
public interface ItemCooldownEntryAccessor {
    @Accessor("startTick")
    int drunvisual$getStartTick();

    @Accessor("endTick")
    int drunvisual$getEndTick();
}
