package ru.drunvisual.mixin;

import net.minecraft.text.Text;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.modules.visuals.Animations;
import drunvisual.animation.ChatInputSlideAnimation;
import drunvisual.events.ChatSendEvent;
import drunvisual.events.EventBusService;
import drunvisual.gui.core.GuiInput;
import drunvisual.hud.core.HudElementManager;
import drunvisual.module.ModuleRegistry;

@Mixin({ChatScreen.class})
public abstract class ChatScreenMixin extends Screen {

    @Shadow
    protected TextFieldWidget chatField;

    @Unique
    private boolean matrixPushed;

    @Unique
    private int originalChatFieldY;

    protected ChatScreenMixin(Text TextVar) {
        super(TextVar);
        this.matrixPushed = false;
        this.originalChatFieldY = 0;
    }

    @Unique
    private boolean isChatAnimationEnabled() {
        return ModuleRegistry.ANIMATIONS != null && ModuleRegistry.ANIMATIONS.k() && ModuleRegistry.ANIMATIONS.e.a();
    }

    @Inject(method = {"sendMessage"}, at = {@At("HEAD")}, cancellable = true)
    private void onSendMessage(String str, boolean z, CallbackInfo callbackInfo) {
        ChatSendEvent chatSendEvent = new ChatSendEvent(str);
        EventBusService.EVENT_BUS.post(chatSendEvent);
        if (chatSendEvent.c()) {
            callbackInfo.cancel();
        }
    }

    @Inject(method = {"init"}, at = {@At("TAIL")})
    private void onInit(CallbackInfo callbackInfo) {
        HudElementManager.a().a(true);
        if (isChatAnimationEnabled()) {
            ChatInputSlideAnimation chatInputSlideAnimationO = Animations.o();
            chatInputSlideAnimationO.d();
            chatInputSlideAnimationO.a((long) ModuleRegistry.ANIMATIONS.f.k().floatValue());
            this.originalChatFieldY = this.chatField.getY();
        }
    }

    @Inject(method = {"removed"}, at = {@At("HEAD")})
    private void onRemoved(CallbackInfo callbackInfo) {
        HudElementManager.a().a(false);
        if (HudElementManager.a().h() != null) {
            HudElementManager.a().h().i().c();
            HudElementManager.a().h().g();
        }
        GuiInput.j();
        Animations.o().d();
    }

    @Inject(method = {"mouseClicked"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseClicked(double d, double d2, int i, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (HudElementManager.a().a(d, d2, i)) {
            callbackInfoReturnable.setReturnValue(true);
        }
    }

    public boolean mouseReleased(double d, double d2, int i) {
        if (HudElementManager.a().b(d, d2, i)) {
            return true;
        }
        return super.mouseReleased(d, d2, i);
    }

    public boolean mouseDragged(double d, double d2, int i, double d3, double d4) {
        HudElementManager.a().a(d, d2, d3, d4);
        if (HudElementManager.a().b(d, d2)) {
            return true;
        }
        return super.mouseDragged(d, d2, i, d3, d4);
    }

    @Inject(method = {"mouseScrolled"}, at = {@At("HEAD")}, cancellable = true)
    private void onMouseScrolled(double d, double d2, double d3, double d4, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (HudElementManager.a().a(d, d2, d4)) {
            callbackInfoReturnable.setReturnValue(true);
        } else if (isChatAnimationEnabled() && Animations.o().c()) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"keyPressed"}, at = {@At("HEAD")}, cancellable = true)
    private void onKeyPressed(int i, int i2, int i3, CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (HudElementManager.a().a(i, i3)) {
            callbackInfoReturnable.setReturnValue(true);
        } else if (isChatAnimationEnabled() && Animations.o().c() && i != 256) {
            callbackInfoReturnable.setReturnValue(false);
        }
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void onRenderHead(DrawContext DrawContextVar, int i, int i2, float f, CallbackInfo callbackInfo) {
        GuiInput.h();
        if (!isChatAnimationEnabled()) {
            this.matrixPushed = false;
            return;
        }
        ChatInputSlideAnimation chatInputSlideAnimationO = Animations.o();
        chatInputSlideAnimationO.a();
        float fB = chatInputSlideAnimationO.b();
        if (fB <= 0.0f) {
            this.matrixPushed = false;
            return;
        }
        this.chatField.setY(this.originalChatFieldY + ((int) fB));
        DrawContextVar.getMatrices().push();
        DrawContextVar.getMatrices().translate(0.0f, fB, 0.0f);
        this.matrixPushed = true;
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void onRender(DrawContext DrawContextVar, int i, int i2, float f, CallbackInfo callbackInfo) {
        if (this.matrixPushed) {
            DrawContextVar.getMatrices().pop();
            this.matrixPushed = false;
        }
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.isWindowFocused()) {
            HudElementManager.a().a(i, i2);
            if (HudElementManager.a().h() != null) {
                HudElementManager.a().h().a((((double) i) * MinecraftClientVarGetInstance.getWindow().getScaleFactor()) / 2.0d, (((double) i2) * MinecraftClientVarGetInstance.getWindow().getScaleFactor()) / 2.0d);
            }
        } else {
            HudElementManager.a().a(i, i2);
            if (HudElementManager.a().h() != null) {
                HudElementManager.a().h().g();
            }
        }
        GuiInput.i();
    }
}
