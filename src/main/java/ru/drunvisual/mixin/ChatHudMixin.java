package ru.drunvisual.mixin;

import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.OrderedText;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.animation.ChatMessageSlideAnimation;
import drunvisual.module.ModuleRegistry;

@Mixin({ChatHud.class})
public abstract class ChatHudMixin {

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private List<ChatHudLine.Visible> visibleMessages;

    @Shadow
    private int scrolledLines;

    @Unique
    private Map<ChatHudLine.Visible, ChatMessageSlideAnimation> messageAnimators;

    @Unique
    private int currentRenderIndex;

    @Shadow
    public abstract int getWidth();

    @Unique
    private Map<ChatHudLine.Visible, ChatMessageSlideAnimation> getAnimators() {
        if (this.messageAnimators == null) {
            this.messageAnimators = new IdentityHashMap();
        }
        return this.messageAnimators;
    }

    @Unique
    private boolean isChatAnimationEnabled() {
        return ModuleRegistry.ANIMATIONS != null && ModuleRegistry.ANIMATIONS.k() && ModuleRegistry.ANIMATIONS.e.a();
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void onRenderHead(DrawContext DrawContextVar, int i, int i2, int i3, boolean z, CallbackInfo callbackInfo) {
        if (isChatAnimationEnabled()) {
            Map<ChatHudLine.Visible, ChatMessageSlideAnimation> animators = getAnimators();
            animators.values().forEach((v0) -> {
                v0.a();
            });
            animators.entrySet().removeIf(entry -> {
                return ((ChatMessageSlideAnimation) entry.getValue()).d();
            });
            this.currentRenderIndex = 0;
        }
    }

    @Redirect(method = {"render"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/OrderedText;III)I"))
    private int redirectDrawTextWithShadow(DrawContext DrawContextVar, TextRenderer TextRendererVar, OrderedText OrderedTextVar, int i, int i2, int i3) {
        float fB = 0.0f;
        if (isChatAnimationEnabled()) {
            int i4 = this.currentRenderIndex + this.scrolledLines;
            if (i4 >= 0 && i4 < this.visibleMessages.size()) {
                ChatMessageSlideAnimation chatMessageSlideAnimation = getAnimators().get(this.visibleMessages.get(i4));
                if (chatMessageSlideAnimation != null) {
                    fB = chatMessageSlideAnimation.b();
                }
            }
            this.currentRenderIndex++;
        }
        return DrawContextVar.drawTextWithShadow(TextRendererVar, OrderedTextVar, i + ((int) fB), i2, i3);
    }

    @Inject(method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"}, at = {@At("TAIL")})
    private void onAddMessage(CallbackInfo callbackInfo) {
        if (!isChatAnimationEnabled() || this.visibleMessages.isEmpty()) {
            return;
        }
        int iGetWidth = getWidth();
        long jFloatValue = (long) ModuleRegistry.ANIMATIONS.f.k().floatValue();
        ChatHudLine.Visible class_7590Var = this.visibleMessages.get(0);
        Map<ChatHudLine.Visible, ChatMessageSlideAnimation> animators = getAnimators();
        if (animators.containsKey(class_7590Var)) {
            return;
        }
        animators.put(class_7590Var, new ChatMessageSlideAnimation(-iGetWidth, 0.0f, jFloatValue));
    }

    @Inject(method = {"clear"}, at = {@At("HEAD")})
    private void onClear(boolean z, CallbackInfo callbackInfo) {
        if (this.messageAnimators != null) {
            this.messageAnimators.clear();
        }
    }
}
