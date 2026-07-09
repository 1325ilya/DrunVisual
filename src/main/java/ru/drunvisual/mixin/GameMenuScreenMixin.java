package ru.drunvisual.mixin;

import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.screen.MessageScreen;
import net.minecraft.client.realms.gui.screen.RealmsMainScreen;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.network.ServerInfo;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.player.CombatState;
import drunvisual.modules.utilities.PvpSafe;
import drunvisual.module.ModuleRegistry;

@Mixin({GameMenuScreen.class})
public abstract class GameMenuScreenMixin extends Screen {

    @Shadow
    @Nullable
    private ButtonWidget exitButton;

    protected GameMenuScreenMixin(Text TextVar) {
        super(TextVar);
    }

    @Inject(method = {"initWidgets"}, at = {@At("TAIL")})
    private void onInitWidgets(CallbackInfo callbackInfo) {
        if (this.exitButton != null) {
            Text TextVarGetMessage = this.exitButton.getMessage();
            int iGetX = this.exitButton.getX();
            int iGetY = this.exitButton.getY();
            int iGetWidth = this.exitButton.getWidth();
            int iGetHeight = this.exitButton.getHeight();
            remove(this.exitButton);
            this.exitButton = ButtonWidget.builder(TextVarGetMessage, ButtonWidgetVar -> {
                ButtonWidgetVar.active = false;
                PvpSafe pvpSafe = ModuleRegistry.PVP_SAFE;
                if (pvpSafe != null && pvpSafe.k() && CombatState.a().a()) {
                    this.client.setScreen(new ConfirmScreen(z -> {
                        if (z) {
                            performDisconnect();
                        } else {
                            this.client.setScreen(this);
                        }
                    }, Text.literal("Leave the server while PVP Safe is active?"), Text.empty()));
                } else {
                    performDisconnect();
                }
            }).dimensions(iGetX, iGetY, iGetWidth, iGetHeight).build();
            addDrawableChild(this.exitButton);
        }
    }

    private void performDisconnect() {
        boolean zIsInSingleplayer = this.client.isInSingleplayer();
        ServerInfo ServerInfoVarGetCurrentServerEntry = this.client.getCurrentServerEntry();
        if (this.client.world != null) {
            this.client.world.disconnect();
        }
        if (zIsInSingleplayer) {
            this.client.disconnect(new MessageScreen(Text.translatable("menu.savingLevel")));
        } else {
            this.client.disconnect();
        }
        TitleScreen TitleScreenVar = new TitleScreen();
        if (zIsInSingleplayer) {
            this.client.setScreen(TitleScreenVar);
        } else if (ServerInfoVarGetCurrentServerEntry == null || !ServerInfoVarGetCurrentServerEntry.isRealm()) {
            this.client.setScreen(new MultiplayerScreen(TitleScreenVar));
        } else {
            this.client.setScreen(new RealmsMainScreen(TitleScreenVar));
        }
    }
}
