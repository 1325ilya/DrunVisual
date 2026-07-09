package ru.drunvisual.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import drunvisual.events.EventBusService;
import drunvisual.events.KeyInputEvent;
import drunvisual.events.MouseButtonEvent;
import drunvisual.events.MouseScrollEvent;

@Mixin({Mouse.class})
public class MouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = {"onMouseButton"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseButton(long j, int i, int i2, int i3, CallbackInfo callbackInfo) {
        if (j == this.client.getWindow().getHandle()) {
            MouseButtonEvent mouseButtonEvent = new MouseButtonEvent(i, i2, i3, (this.client.mouse.getX() * ((double) this.client.getWindow().getScaledWidth())) / ((double) this.client.getWindow().getWidth()), (this.client.mouse.getY() * ((double) this.client.getWindow().getScaledHeight())) / ((double) this.client.getWindow().getHeight()));
            EventBusService.EVENT_BUS.post(mouseButtonEvent);
            if (mouseButtonEvent.c()) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = {"onMouseScroll"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseScroll(long j, double d, double d2, CallbackInfo callbackInfo) {
        if (j == this.client.getWindow().getHandle()) {
            MouseScrollEvent mouseScrollEvent = new MouseScrollEvent(d, d2);
            EventBusService.EVENT_BUS.post(mouseScrollEvent);
            if (mouseScrollEvent.c()) {
                callbackInfo.cancel();
            }
        }
    }

    @Inject(method = {"onMouseButton"}, at = {@At("TAIL")}, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void onMouseButton1(long j, int i, int i2, int i3, CallbackInfo callbackInfo) {
        if (i > 1) {
            EventBusService.EVENT_BUS.post(new KeyInputEvent(1450 + i, 0, i2, i3));
        }
    }
}
