package ru.drunvisual.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.client.render.RenderTickCounter;
import org.joml.Matrix4f;
import org.joml.Matrix4fStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.visuals.AspectRatio;
import drunvisual.render.CustomHandShaderCapture;
import drunvisual.events.EventBusService;
import drunvisual.events.WorldRenderEndEvent;
import drunvisual.render.world.WorldToScreen;
import drunvisual.modules.utilities.Zoom;
import drunvisual.module.ModuleRegistry;

@Mixin({GameRenderer.class})
public abstract class GameRendererMixin {

    @Shadow
    private float zoom;

    @Shadow
    private float zoomX;

    @Shadow
    private float zoomY;

    @Shadow
    public abstract float getFarPlaneDistance();

    @Inject(method = {"renderWorld"}, at = {@At("HEAD")})
    private void onRenderWorldStart(RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
    }

    @Inject(method = {"renderHand"}, at = {@At("RETURN")})
    private void onRenderHandEnd(Camera CameraVar, float f, Matrix4f matrix4f, CallbackInfo callbackInfo) {
        CustomHandShaderCapture.endCapture();
    }

    @Inject(method = {"renderWorld"}, at = {@At("RETURN")})
    private void onRenderWorldEnd(RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        CustomHandShaderCapture.endCapture();
        EventBusService.EVENT_BUS.post(new WorldRenderEndEvent(new MatrixStack(), RenderTickCounterVar.getTickDelta(true), ((GameRenderer)(Object)this).getCamera()));
    }

    @Inject(method = {"tiltViewWhenHurt"}, at = {@At("HEAD")}, cancellable = true)
    private void onBobViewWhenHurt(MatrixStack MatrixStackVar, float f, CallbackInfo callbackInfo) {
        if (ModuleRegistry.RENDER_TWEAKS.o()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"showFloatingItem"}, at = {@At("HEAD")}, cancellable = true)
    private void onShowFloatingItem(CallbackInfo callbackInfo) {
        if (ModuleRegistry.RENDER_TWEAKS.q()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"renderWorld"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/render/GameRenderer;renderHand:Z", opcode = 180, ordinal = 0)})
    public void renderWorld(RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        if (CustomHandShaderCapture.guiOpen()) {
            CustomHandShaderCapture.resetForGui();
            return;
        }
        Camera CameraVarGetCamera = MinecraftClient.getInstance().gameRenderer.getCamera();
        MatrixStack MatrixStackVar = new MatrixStack();
        Matrix4fStack modelViewStack = RenderSystem.getModelViewStack();
        modelViewStack.pushMatrix();
        modelViewStack.mul(MatrixStackVar.peek().getPositionMatrix());
        MatrixStackVar.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CameraVarGetCamera.getPitch()));
        MatrixStackVar.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(CameraVarGetCamera.getYaw() + 180.0f));
        WorldToScreen.a.set(RenderSystem.getProjectionMatrix());
        WorldToScreen.b.set(new Matrix4f(RenderSystem.getModelViewMatrix()));
        WorldToScreen.e.set(MatrixStackVar.peek().getPositionMatrix());
        modelViewStack.popMatrix();
        CustomHandShaderCapture.beginCapture();
    }

    @Inject(method = {"getBasicProjectionMatrix"}, at = {@At("HEAD")}, cancellable = true)
    public void getBasicProjectionMatrix(float f, CallbackInfoReturnable<Matrix4f> callbackInfoReturnable) {
        if (Zoom.a) {
            f = (float) Zoom.b;
        }
        MatrixStack MatrixStackVar = new MatrixStack();
        MatrixStackVar.peek().getPositionMatrix().identity();
        if (this.zoom != 1.0f) {
            MatrixStackVar.translate(this.zoomX, -this.zoomY, 0.0d);
            MatrixStackVar.scale(this.zoom, this.zoom, 1.0f);
        }
        float fGetFramebufferWidth = MinecraftClient.getInstance().getWindow().getFramebufferWidth() / MinecraftClient.getInstance().getWindow().getFramebufferHeight();
        AspectRatio aspectRatio = ModuleRegistry.ASPECT_RATIO;
        if (aspectRatio.k()) {
            fGetFramebufferWidth = aspectRatio.n();
        }
        callbackInfoReturnable.setReturnValue(MatrixStackVar.peek().getPositionMatrix().perspective(f * 0.017453292f, fGetFramebufferWidth, 0.05f, getFarPlaneDistance()));
    }
}
