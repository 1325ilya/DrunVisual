package ru.drunvisual.mixin;

import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.Particle;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.module.ModuleRegistry;

@Mixin({ParticleManager.class})
public class ParticleManagerMixin {
    @Inject(method = {"addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;"}, at = {@At("HEAD")}, cancellable = true)
    private void onAddParticle(ParticleEffect ParticleEffectVar, double d, double d2, double d3, double d4, double d5, double d6, CallbackInfoReturnable<Particle> callbackInfoReturnable) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.u()) {
            return;
        }
        if (ParticleEffectVar.getType() == ParticleTypes.BUBBLE || ParticleEffectVar.getType() == ParticleTypes.BUBBLE_COLUMN_UP || ParticleEffectVar.getType() == ParticleTypes.BUBBLE_POP || ParticleEffectVar.getType() == ParticleTypes.CURRENT_DOWN || ParticleEffectVar.getType() == ParticleTypes.UNDERWATER) {
            callbackInfoReturnable.setReturnValue((Particle) null);
        }
    }
}
