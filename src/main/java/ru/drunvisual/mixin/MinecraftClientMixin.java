package ru.drunvisual.mixin;

import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.RunArgs;
import net.minecraft.client.world.ClientWorld;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import drunvisual.events.AttackEntityEvent;
import drunvisual.events.EventBusService;
import drunvisual.render.RenderFrameTimer;
import drunvisual.events.WorldChangeEvent;
import drunvisual.client.MinecraftContext;
import ru.drunvisual.DrunVisual;

@Mixin({MinecraftClient.class})
public class MinecraftClientMixin implements MinecraftContext {
    @Inject(method = {"joinWorld"}, at = {@At("RETURN")})
    private void onWorldChange(ClientWorld ClientWorldVar, DownloadingTerrainScreen.WorldEntryReason class_9678Var, CallbackInfo callbackInfo) {
        EventBusService.EVENT_BUS.post(new WorldChangeEvent());
    }

    @Inject(method = {"<init>"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/render/debug/DebugRenderer;<init>(Lnet/minecraft/client/MinecraftClient;)V")})
    public void init(RunArgs RunArgsVar, CallbackInfo callbackInfo) {
        DrunVisual.getInstance().init();
    }

    @Inject(method = {"render"}, at = {@At(value = "FIELD", target = "Lnet/minecraft/client/MinecraftClient;fpsCounter:I", opcode = 181, ordinal = 0)})
    private void render(boolean z, CallbackInfo callbackInfo) {
        try {
            Class.forName("ru.drunvisual.DrunVisual").getMethod("runtimeBrandingTick", new Class[0]).invoke(null, new Object[0]);
        } catch (Throwable th) {
        }
        RenderFrameTimer.b();
    }

    @Inject(method = {"isMultiplayerEnabled"}, at = {@At("HEAD")}, cancellable = true)
    public void allowsMultiplayer(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(true);
    }

    @Inject(method = {"getChatRestriction"}, at = {@At("HEAD")}, cancellable = true)
    public void allowsChat(CallbackInfoReturnable<MinecraftClient.ChatRestriction> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(MinecraftClient.ChatRestriction.ENABLED);
    }

    @Inject(method = {"isRealmsEnabled"}, at = {@At("HEAD")}, cancellable = true)
    public void allowsRealms(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        callbackInfoReturnable.setReturnValue(false);
    }

    @Inject(method = {"doAttack"}, at = {@At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerInteractionManager;attackEntity(Lnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/entity/Entity;)V")})
    private void onAttack(CallbackInfoReturnable<Boolean> callbackInfoReturnable) {
        if (c.targetedEntity != null) {
            EventBusService.EVENT_BUS.post(new AttackEntityEvent(c.targetedEntity));
        }
    }
}
