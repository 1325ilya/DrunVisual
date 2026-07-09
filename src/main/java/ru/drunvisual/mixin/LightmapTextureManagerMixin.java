package ru.drunvisual.mixin;

import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.module.ModuleRegistry;

@Mixin({LightmapTextureManager.class})
public class LightmapTextureManagerMixin {
    @Inject(method = {"getDarknessFactor"}, at = {@At("HEAD")}, cancellable = true)
    private void getDarknessFactor(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (ModuleRegistry.FULL_BRIGHT.k()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
        }
    }

    @Inject(method = {"getBrightness(FI)F"}, at = {@At("HEAD")}, cancellable = true)
    private static void onGetBrightness(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (ModuleRegistry.FULL_BRIGHT.k()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(1.0f));
        }
    }

    @Inject(method = {"getBrightness(Lnet/minecraft/world/dimension/DimensionType;I)F"}, at = {@At("HEAD")}, cancellable = true)
    private static void onGetBrightnessAmbient(CallbackInfoReturnable<Float> callbackInfoReturnable) {
        if (ModuleRegistry.FULL_BRIGHT.k()) {
            callbackInfoReturnable.setReturnValue(Float.valueOf(1.0f));
        }
    }
}
