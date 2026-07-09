package ru.drunvisual.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.events.EventBusService;
import drunvisual.events.ItemUseFinishEvent;

@Mixin({ItemStack.class})
public class ItemStackMixin {
    @Inject(method = {"finishUsing"}, at = {@At("HEAD")})
    private void onItemUseFinish(World WorldVar, LivingEntity LivingEntityVar, CallbackInfoReturnable<ItemStack> callbackInfoReturnable) {
        ItemUseFinishEvent itemUseFinishEvent = new ItemUseFinishEvent((ItemStack)(Object)this, WorldVar, LivingEntityVar);
        EventBusService.EVENT_BUS.post(itemUseFinishEvent);
        if (itemUseFinishEvent.c()) {
            callbackInfoReturnable.cancel();
        }
    }
}
