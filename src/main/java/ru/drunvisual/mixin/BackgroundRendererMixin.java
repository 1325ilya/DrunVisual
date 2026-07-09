package ru.drunvisual.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.block.enums.CameraSubmersionType;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.render.FogShape;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Fog;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.module.ModuleRegistry;

@Mixin({BackgroundRenderer.class})
public class BackgroundRendererMixin {
    @Inject(method = {"getFogColor"}, at = {@At("RETURN")}, cancellable = true)
    private static void customizeFogColor(Camera CameraVar, float f, ClientWorld ClientWorldVar, int i, float f2, CallbackInfoReturnable<Vector4f> callbackInfoReturnable) {
        if (ModuleRegistry.WORLD_CUSTOMIZER.n()) {
            int iS = ModuleRegistry.WORLD_CUSTOMIZER.s();
            callbackInfoReturnable.setReturnValue(new Vector4f(((iS >> 16) & 255) / 255.0f, ((iS >> 8) & 255) / 255.0f, (iS & 255) / 255.0f, 1.0f));
        }
    }

    @Inject(method = {"applyFog"}, at = {@At("HEAD")}, cancellable = true)
    private static void onApplyFog(Camera CameraVar, BackgroundRenderer.FogType class_4596Var, Vector4f vector4f, float f, boolean z, float f2, CallbackInfoReturnable<Fog> callbackInfoReturnable) {
        CameraSubmersionType CameraSubmersionTypeVarGetSubmersionType;
        if (ModuleRegistry.NO_FLUID.k() && ((CameraSubmersionTypeVarGetSubmersionType = CameraVar.getSubmersionType()) == CameraSubmersionType.WATER || CameraSubmersionTypeVarGetSubmersionType == CameraSubmersionType.LAVA)) {
            callbackInfoReturnable.setReturnValue(Fog.DUMMY);
        }
        if (ModuleRegistry.WORLD_CUSTOMIZER.o()) {
            callbackInfoReturnable.setReturnValue(new Fog(ModuleRegistry.WORLD_CUSTOMIZER.p(), ModuleRegistry.WORLD_CUSTOMIZER.q(), FogShape.SPHERE, vector4f.x, vector4f.y, vector4f.z, vector4f.w));
        }
    }
}
