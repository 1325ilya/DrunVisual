package ru.drunvisual.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.FrameGraphBuilder;
import net.minecraft.client.util.ObjectAllocator;
import net.minecraft.client.render.Fog;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.events.EventBusService;
import drunvisual.events.WorldRenderEvent;
import drunvisual.events.WorldRenderStartEvent;
import drunvisual.module.ModuleRegistry;

@Mixin({WorldRenderer.class})
public abstract class WorldRendererMixin {
    @Inject(method = {"renderWeather"}, at = {@At("HEAD")}, cancellable = true)
    private void onRenderWeather(FrameGraphBuilder FrameGraphBuilderVar, Vec3d Vec3dVar, float f, Fog FogVar, CallbackInfo callbackInfo) {
        if (ModuleRegistry.RENDER_TWEAKS.n()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void preRender(ObjectAllocator ObjectAllocatorVar, RenderTickCounter RenderTickCounterVar, boolean z, Camera CameraVar, GameRenderer GameRendererVar, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo callbackInfo) {
        EventBusService.EVENT_BUS.post(new WorldRenderStartEvent(RenderTickCounterVar.getTickDelta(true)));
    }

    @Inject(method = {"render"}, at = {@At("RETURN")})
    private void postRenderEvent(ObjectAllocator ObjectAllocatorVar, RenderTickCounter RenderTickCounterVar, boolean z, Camera CameraVar, GameRenderer GameRendererVar, Matrix4f matrix4f, Matrix4f matrix4f2, CallbackInfo callbackInfo) {
        restoreRenderState();
        MatrixStack MatrixStackVar = new MatrixStack();
        MatrixStackVar.peek().getPositionMatrix().set(matrix4f);
        EventBusService.EVENT_BUS.post(new WorldRenderEvent(MatrixStackVar, RenderTickCounterVar.getTickDelta(true)));
        restoreRenderState();
    }

    private void restoreRenderState() {
        RenderSystem.enableDepthTest();
        RenderSystem.depthFunc(515);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
    }
}
