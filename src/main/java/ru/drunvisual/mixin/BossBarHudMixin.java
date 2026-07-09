package ru.drunvisual.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.BossBarHud;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.module.ModuleRegistry;

@Mixin({BossBarHud.class})
public class BossBarHudMixin {
    @Inject(method = {"render"}, at = {@At("HEAD")}, cancellable = true)
    private void onRender(DrawContext DrawContextVar, CallbackInfo callbackInfo) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.s()) {
            return;
        }
        callbackInfo.cancel();
    }
}
