package ru.drunvisual.mixin;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.DropItemEvent;
import drunvisual.events.EventBusService;

@Mixin({ClientPlayerEntity.class})
public class ClientPlayerEntityMixin {
    @Inject(method = {"tick"}, at = {@At("HEAD")})
    private void onTick(CallbackInfo callbackInfo) {
        EventBusService.EVENT_BUS.post(new ClientTickEvent());
    }

    @Inject(method = {"dropSelectedItem"}, at = {@At("HEAD")}, cancellable = true)
    private void onDropSelectedItem(boolean z, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        DropItemEvent dropItemEvent = new DropItemEvent(((ClientPlayerEntity)(Object)this).getInventory().selectedSlot, z);
        EventBusService.EVENT_BUS.post(dropItemEvent);
        if (dropItemEvent.c()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
