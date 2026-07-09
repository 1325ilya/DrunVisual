package ru.drunvisual.mixin;

import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.option.Perspective;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.modules.visuals.Animations;
import drunvisual.modules.utilities.FreeLook;
import drunvisual.animation.PerspectiveDistanceAnimation;
import drunvisual.module.ModuleRegistry;

@Mixin({Camera.class})
public abstract class CameraMixin {
    @Shadow
    protected abstract void setRotation(float f, float f2);

    @Inject(method = {"update"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;setRotation(FF)V", ordinal = 0, shift = At.Shift.AFTER)})
    public void onUpdate(BlockView BlockViewVar, Entity EntityVar, boolean z, boolean z2, float f, CallbackInfo callbackInfo) {
        if (FreeLook.active && (EntityVar instanceof ClientPlayerEntity)) {
            setRotation(FreeLook.cameraYaw, FreeLook.cameraPitch);
        }
    }

    @Unique
    private boolean isPerspectiveAnimationEnabled() {
        return ModuleRegistry.ANIMATIONS != null && ModuleRegistry.ANIMATIONS.k() && ModuleRegistry.ANIMATIONS.i.a();
    }

    @Inject(method = {"update"}, at = {@At("HEAD")})
    private void onUpdateHead(BlockView BlockViewVar, Entity EntityVar, boolean z, boolean z2, float f, CallbackInfo callbackInfo) {
        if (isPerspectiveAnimationEnabled()) {
            PerspectiveDistanceAnimation perspectiveDistanceAnimationQ = Animations.q();
            perspectiveDistanceAnimationQ.a(z, (long) (ModuleRegistry.ANIMATIONS.j.k().floatValue() * 1.5f));
            perspectiveDistanceAnimationQ.a();
        }
    }

    @ModifyArg(method = {"update"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/Camera;clipToSpace(F)F"), index = EventPriority.MEDIUM)
    private float modifyCameraDistance(float f) {
        return (!isPerspectiveAnimationEnabled() || MinecraftClient.getInstance().options.getPerspective() == Perspective.FIRST_PERSON) ? f : Animations.q().b();
    }
}
