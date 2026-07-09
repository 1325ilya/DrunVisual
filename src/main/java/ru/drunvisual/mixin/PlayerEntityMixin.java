package ru.drunvisual.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.events.CriticalHitEvent;
import drunvisual.events.EventBusService;

@Mixin({PlayerEntity.class})
public class PlayerEntityMixin {
    @Inject(method = {"attack"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;addCritParticles(Lnet/minecraft/entity/Entity;)V")})
    private void onCriticalHit(Entity EntityVar, CallbackInfo callbackInfo) {
        EventBusService.EVENT_BUS.post(new CriticalHitEvent(EntityVar));
    }
}
