package ru.drunvisual.mixin;

import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import drunvisual.events.EventBusService;
import drunvisual.events.SoundPlayEvent;
import drunvisual.util.sound.VolumeScaledSoundInstance;

@Mixin({SoundSystem.class})
public class SoundSystemMixin {
    @ModifyVariable(method = {"play(Lnet/minecraft/client/sound/SoundInstance;)V"}, at = @At("HEAD"), argsOnly = true)
    private SoundInstance modifySound(SoundInstance SoundInstanceVar) {
        if (SoundInstanceVar == null || (SoundInstanceVar instanceof VolumeScaledSoundInstance)) {
            return SoundInstanceVar;
        }
        SoundPlayEvent soundPlayEvent = new SoundPlayEvent(SoundInstanceVar);
        EventBusService.EVENT_BUS.post(soundPlayEvent);
        if (soundPlayEvent.c()) {
            return null;
        }
        return soundPlayEvent.e() != 1.0f ? new VolumeScaledSoundInstance(SoundInstanceVar, soundPlayEvent.e()) : SoundInstanceVar;
    }
}
