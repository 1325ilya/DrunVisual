package ru.drunvisual.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.gui.core.DrunVisualClickGuiScreen;

@Mixin(value = {Mouse.class}, priority = 1100)
public class ClickGuiMouseMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Inject(method = {"onMouseButton"}, at = {@At("HEAD")}, cancellable = true)
    private void drunvisual$handleClickGuiRightClick(long j, int i, int i2, int i3, CallbackInfo callbackInfo) {
        if (j == this.client.getWindow().getHandle() && i == 1 && i2 == 1) {
            Screen ScreenVar = this.client.currentScreen;
            if (ScreenVar instanceof DrunVisualClickGuiScreen) {
                ((DrunVisualClickGuiScreen) ScreenVar).dispatchPointerClick((this.client.mouse.getX() * ((double) this.client.getWindow().getScaledWidth())) / ((double) this.client.getWindow().getWidth()), (this.client.mouse.getY() * ((double) this.client.getWindow().getScaledHeight())) / ((double) this.client.getWindow().getHeight()), i);
                callbackInfo.cancel();
            }
        }
    }
}
