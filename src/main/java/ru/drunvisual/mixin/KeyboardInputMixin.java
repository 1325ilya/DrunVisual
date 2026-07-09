package ru.drunvisual.mixin;

import net.minecraft.util.PlayerInput;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.input.Input;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.events.EventBusService;
import drunvisual.events.MovementInputEvent;

@Mixin({KeyboardInput.class})
public class KeyboardInputMixin extends Input {

    @Shadow
    @Final
    private GameOptions settings;

    @Inject(method = {"tick"}, at = {@At("HEAD")}, cancellable = true)
    private void onTick(CallbackInfo callbackInfo) {
        this.playerInput = new PlayerInput(this.settings.forwardKey.isPressed(), this.settings.backKey.isPressed(), this.settings.leftKey.isPressed(), this.settings.rightKey.isPressed(), this.settings.jumpKey.isPressed(), this.settings.sneakKey.isPressed(), this.settings.sprintKey.isPressed());
        this.movementForward = getMovementMultiplier(this.playerInput.forward(), this.playerInput.backward());
        this.movementSideways = getMovementMultiplier(this.playerInput.left(), this.playerInput.right());
        MovementInputEvent movementInputEvent = new MovementInputEvent(this.movementForward, this.movementSideways, this.playerInput.jump(), this.playerInput.sneak(), 0.30000001192092896d);
        EventBusService.EVENT_BUS.post(movementInputEvent);
        if (movementInputEvent.c()) {
            this.movementForward = 0.0f;
            this.movementSideways = 0.0f;
            this.playerInput = new PlayerInput(false, false, false, false, false, false, this.playerInput.sprint());
        } else {
            this.movementForward = movementInputEvent.d();
            this.movementSideways = movementInputEvent.e();
            this.playerInput = new PlayerInput(movementInputEvent.d() > 0.0f, movementInputEvent.d() < 0.0f, movementInputEvent.e() > 0.0f, movementInputEvent.e() < 0.0f, movementInputEvent.f(), movementInputEvent.g(), this.playerInput.sprint());
        }
        callbackInfo.cancel();
    }

    @Unique
    private static float getMovementMultiplier(boolean z, boolean z2) {
        if (z == z2) {
            return 0.0f;
        }
        return z ? 1.0f : -1.0f;
    }
}
