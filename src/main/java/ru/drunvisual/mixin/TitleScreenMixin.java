package ru.drunvisual.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.drunvisual.DrunVisual;

@Mixin({TitleScreen.class})
public class TitleScreenMixin {
    @Inject(method = {"init"}, at = {@At("TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        DrunVisual.checkAndShowUpdate((TitleScreen)(Object)this);
    }
}
