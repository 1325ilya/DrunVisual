package ru.drunvisual.mixin;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectUtil;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.visuals.CustomHand;
import drunvisual.events.EntityJumpEvent;
import drunvisual.events.EventBusService;
import drunvisual.events.PlayerDeathEvent;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.module.ModuleRegistry;

@Mixin({LivingEntity.class})
public class LivingEntityMixin {
    @Overwrite
    private int getHandSwingDuration() {
        LivingEntity LivingEntityVar = (LivingEntity)(Object)this;
        if (StatusEffectUtil.hasHaste(LivingEntityVar)) {
            return 6 - (1 + StatusEffectUtil.getHasteAmplifier(LivingEntityVar));
        }
        CustomHand customHand = ModuleRegistry.CUSTOM_HAND;
        if (customHand != null && customHand.k()) {
            int iB = customHand.animationSpeed.b();
            return LivingEntityVar.hasStatusEffect(StatusEffects.MINING_FATIGUE) ? iB + ((1 + LivingEntityVar.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) * 2) : iB;
        }
        if (LivingEntityVar.hasStatusEffect(StatusEffects.MINING_FATIGUE)) {
            return 6 + ((1 + LivingEntityVar.getStatusEffect(StatusEffects.MINING_FATIGUE).getAmplifier()) * 2);
        }
        return 6;
    }

    @Inject(method = {"jump"}, at = {@At("HEAD")})
    private void onJump(CallbackInfo callbackInfo) {
        EventBusService.EVENT_BUS.post(new EntityJumpEvent((LivingEntity)(Object)this));
    }

    @Inject(method = {"handleStatus"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;getDeathSound()Lnet/minecraft/sound/SoundEvent;")})
    public void handleStatus(byte b, CallbackInfo callbackInfo) {
        LivingEntity LivingEntitySelf = (LivingEntity)(Object)this;
        if (LivingEntitySelf instanceof ClientPlayerEntity) {
            ClientPlayerEntity ClientPlayerEntityVar2 = (ClientPlayerEntity) LivingEntitySelf;
            EventBusService.EVENT_BUS.post(new PlayerDeathEvent(ClientPlayerEntityVar2, ClientPlayerEntityVar2.getX(), ClientPlayerEntityVar2.getY(), ClientPlayerEntityVar2.getZ()));
        }
    }

    @Inject(method = {"hasStatusEffect"}, at = {@At("HEAD")}, cancellable = true)
    private void onHasStatusEffect(RegistryEntry<StatusEffect> RegistryEntryVar, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        RenderTweaks renderTweaks;
        if ((((Object)this) instanceof ClientPlayerEntity) && (renderTweaks = ModuleRegistry.RENDER_TWEAKS) != null && RegistryEntryVar == StatusEffects.WITHER && renderTweaks.t()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
