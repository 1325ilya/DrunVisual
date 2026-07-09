package ru.drunvisual.mixin;

import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.module.ModuleRegistry;

@Mixin({World.class})
public class WorldMixin {
    @Inject(method = {"isRaining"}, at = {@At("HEAD")}, cancellable = true)
    private void onIsRaining(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.n()) {
            return;
        }
        callbackInfoReturnable.setReturnValue(false);
    }

    @Inject(method = {"isThundering"}, at = {@At("HEAD")}, cancellable = true)
    private void onIsThundering(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.n()) {
            return;
        }
        callbackInfoReturnable.setReturnValue(false);
    }

    @Inject(method = {"hasRain"}, at = {@At("HEAD")}, cancellable = true)
    private void onHasRain(BlockPos BlockPosVar, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.n()) {
            return;
        }
        callbackInfoReturnable.setReturnValue(false);
    }

    @Inject(method = {"getRainGradient"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetRainGradient(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.n()) {
            return;
        }
        callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
    }

    @Inject(method = {"getThunderGradient"}, at = {@At("HEAD")}, cancellable = true)
    private void onGetThunderGradient(float f, CallbackInfoReturnable<Float> callbackInfoReturnable) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.n()) {
            return;
        }
        callbackInfoReturnable.setReturnValue(Float.valueOf(0.0f));
    }
}
