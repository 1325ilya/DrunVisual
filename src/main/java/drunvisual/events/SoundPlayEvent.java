package drunvisual.events;

import net.minecraft.client.sound.SoundInstance;

public class SoundPlayEvent extends CancellableDrunVisualEvent {
    private final SoundInstance sound;
    private float volume;
    private float pitch;

    public SoundPlayEvent(SoundInstance SoundInstanceVar) {
        this.sound = SoundInstanceVar;
        try {
            this.volume = SoundInstanceVar.getVolume();
            this.pitch = SoundInstanceVar.getPitch();
        } catch (NullPointerException e) {
            this.volume = 1.0f;
            this.pitch = 1.0f;
        }
    }

    public SoundInstance sound() {
        return this.sound;
    }

    public float volume() {
        return this.volume;
    }

    public void setVolume(float f) {
        this.volume = f;
    }

    public float pitch() {
        return this.pitch;
    }

    public void setPitch(float f) {
        this.pitch = f;
    }

    public SoundInstance d() {
        return this.sound;
    }

    public float e() {
        return this.volume;
    }

    public void a(float f) {
        this.volume = f;
    }

    public float f() {
        return this.pitch;
    }

    public void b(float f) {
        this.pitch = f;
    }
}
