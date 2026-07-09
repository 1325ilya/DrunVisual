package ru.drunvisual.mixin;

import net.minecraft.util.math.Vec3d;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.modules.visuals.TimeChanger;
import drunvisual.modules.visuals.WorldCustomizer;
import drunvisual.module.ModuleRegistry;

@Mixin({ClientWorld.class})
public abstract class ClientWorldMixin {
    @Inject(method = {"getSkyColor"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetSkyColor(Vec3d Vec3dVar, float f, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        WorldCustomizer worldCustomizer = ModuleRegistry.WORLD_CUSTOMIZER;
        if (worldCustomizer != null && worldCustomizer.r()) {
            callbackInfoReturnable.setReturnValue(Integer.valueOf(worldCustomizer.t()));
            return;
        }
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.n()) {
            return;
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, (float) ((1.0d - (Math.cos((((double) ((ClientWorld)(Object)this).getSkyAngle(f)) * 3.141592653589793d) * 2.0d) * 2.0d)) + 0.5d)));
        callbackInfoReturnable.setReturnValue(Integer.valueOf(ColorHelper.fromFloats(1.0f, 0.7529412f * fMax, 0.84705883f * fMax, 1.0f * fMax)));
    }

    @Inject(method = {"getCloudsColor"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetCloudsColor(float f, CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (ModuleRegistry.RENDER_TWEAKS.n()) {
            float fMax = (Math.max(0.0f, Math.min(1.0f, (float) ((Math.cos(((double) ((ClientWorld)(Object)this).getSkyAngle(f)) * 3.141592653589793d * 2.0d) * 2.0d) + 0.5d))) * 0.9f) + 0.1f;
            callbackInfoReturnable.setReturnValue(Integer.valueOf(ColorHelper.fromFloats(1.0f, fMax, fMax, (fMax * 0.85f) + 0.15f)));
        }
    }

    @Inject(method = {"getLightningTicksLeft"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetLightningTicks(CallbackInfoReturnable<Integer> callbackInfoReturnable) {
        if (ModuleRegistry.RENDER_TWEAKS.n()) {
            callbackInfoReturnable.setReturnValue(0);
        }
    }

    @Inject(method = {"getSkyBrightness"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetSkyBrightness(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        TimeChanger timeChanger = ModuleRegistry.TIME_CHANGER;
        if (timeChanger == null || !timeChanger.k()) {
            return;
        }
        callbackInfoReturnable.setReturnValue(Float.valueOf(((1.0f - Math.max(0.0f, Math.min(1.0f, 1.0f - ((float) ((Math.cos((((double) ((ClientWorld)(Object)this).getDimension().getSkyAngle(timeChanger.n())) * 3.141592653589793d) * 2.0d) * 2.0d) + 0.2d))))) * 0.8f) + 0.2f));
    }

    @Inject(method = {"getStarBrightness"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetStarBrightness(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        TimeChanger timeChanger = ModuleRegistry.TIME_CHANGER;
        if (timeChanger == null || !timeChanger.k()) {
            return;
        }
        float fMax = Math.max(0.0f, Math.min(1.0f, 1.0f - ((float) ((Math.cos((((double) ((ClientWorld)(Object)this).getDimension().getSkyAngle(timeChanger.n())) * 3.141592653589793d) * 2.0d) * 2.0d) + 0.25d))));
        callbackInfoReturnable.setReturnValue(Float.valueOf(fMax * fMax * 0.5f));
    }
}
