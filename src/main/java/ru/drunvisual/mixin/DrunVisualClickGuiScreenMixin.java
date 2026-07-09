package ru.drunvisual.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.gui.core.ClickGuiInteractionHelper;
import drunvisual.gui.core.DrunVisualClickGuiScreen;

@Mixin({DrunVisualClickGuiScreen.class})
public class DrunVisualClickGuiScreenMixin {
    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    private void drunvisual$rightClickOpensSettings(double d, double d2, int i, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (i != 1) {
            return;
        }
        ClickGuiInteractionHelper.handleRightClick(d, d2);
        callbackInfoReturnable.setReturnValue(true);
        callbackInfoReturnable.cancel();
    }
}
