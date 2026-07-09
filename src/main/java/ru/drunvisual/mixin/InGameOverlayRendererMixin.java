package ru.drunvisual.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.gui.hud.InGameOverlayRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.render.CustomHandShaderCapture;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.module.ModuleRegistry;

@Mixin({InGameOverlayRenderer.class})
public class InGameOverlayRendererMixin {
    @Inject(method = {"renderFireOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private static void onRenderFireOverlay(MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, CallbackInfo callbackInfo) {
        if (CustomHandShaderCapture.isActive()) {
            if (VertexConsumerProviderVar instanceof VertexConsumerProvider.Immediate) {
                ((VertexConsumerProvider.Immediate) VertexConsumerProviderVar).draw();
            }
            CustomHandShaderCapture.endCapture();
        }
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.p()) {
            return;
        }
        callbackInfo.cancel();
    }

    @Inject(method = {"renderUnderwaterOverlay"}, at = {@At("HEAD")}, cancellable = true)
    private static void onRenderUnderwaterOverlay(MinecraftClient MinecraftClientVar, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, CallbackInfo callbackInfo) {
        if (CustomHandShaderCapture.isActive()) {
            if (VertexConsumerProviderVar instanceof VertexConsumerProvider.Immediate) {
                ((VertexConsumerProvider.Immediate) VertexConsumerProviderVar).draw();
            }
            CustomHandShaderCapture.endCapture();
        }
        if (ModuleRegistry.NO_FLUID == null || !ModuleRegistry.NO_FLUID.k()) {
            return;
        }
        callbackInfo.cancel();
    }
}
