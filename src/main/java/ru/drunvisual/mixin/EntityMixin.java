package ru.drunvisual.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.utilities.FreeLook;
import drunvisual.module.ModuleRegistry;

@Mixin({Entity.class})
public abstract class EntityMixin {

    @Shadow
    private boolean glowing;

    @Inject(method = {"changeLookDirection"}, at = {@At("HEAD")}, cancellable = true)
    public void changeLookDirection(double d, double d2, CallbackInfo callbackInfo) {
        if (FreeLook.active && (((Object)this) instanceof ClientPlayerEntity)) {
            FreeLook.cameraYaw += (float) (d * 0.15d);
            FreeLook.cameraPitch = MathHelper.clamp(FreeLook.cameraPitch + ((float) (d2 * 0.15d)), -90.0f, 90.0f);
            callbackInfo.cancel();
        }
    }

    @Shadow
    public abstract boolean isGlowing();

    @Inject(method = {"isGlowing"}, at = {@At("HEAD")}, cancellable = true)
    private void onIsGlowing(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (ModuleRegistry.RENDER_TWEAKS.v()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }
}
