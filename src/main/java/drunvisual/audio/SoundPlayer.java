package drunvisual.audio;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Locale;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineEvent;

public final class SoundPlayer {
    private static final String SOUND_DIR = "assets/drunvisual/sounds/";
    private static final Random RANDOM = new Random();
    private static final String[] HIT_SOUNDS = {"hit1", "hit2", "hit3"};
    private static final String[] MOAN_SOUNDS = {"moan1", "moan2", "moan3", "moan4"};

    private SoundPlayer() {
    }

    public static void play(String str, float f) {
        if (str == null || str.isBlank()) {
            playRandomHit(f);
        } else {
            playResource("assets/drunvisual/sounds/" + str.toLowerCase(Locale.ROOT) + ".wav", f);
        }
    }

    public static void playRandomHit(float f) {
        playResource("assets/drunvisual/sounds/" + HIT_SOUNDS[RANDOM.nextInt(HIT_SOUNDS.length)] + ".wav", f);
    }

    public static void playRandomMoan(float f) {
        playResource("assets/drunvisual/sounds/" + MOAN_SOUNDS[RANDOM.nextInt(MOAN_SOUNDS.length)] + ".wav", f);
    }

    private static void playResource(String str, float f) {
        Thread thread = new Thread(() -> {
            try {
                InputStream resourceAsStream = SoundPlayer.class.getClassLoader().getResourceAsStream(str);
                if (resourceAsStream == null) {
                    if (resourceAsStream != null) {
                        resourceAsStream.close();
                        return;
                    }
                    return;
                }
                try {
                    BufferedInputStream bufferedInputStream = new BufferedInputStream(resourceAsStream);
                    try {
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(bufferedInputStream);
                        try {
                            Clip clip = AudioSystem.getClip();
                            clip.open(audioInputStream);
                            applyVolume(clip, f);
                            clip.start();
                            clip.addLineListener(lineEvent -> {
                                if (lineEvent.getType() == LineEvent.Type.STOP) {
                                    clip.close();
                                }
                            });
                            if (audioInputStream != null) {
                                audioInputStream.close();
                            }
                            bufferedInputStream.close();
                            if (resourceAsStream != null) {
                                resourceAsStream.close();
                            }
                        } catch (Throwable th) {
                            if (audioInputStream != null) {
                                try {
                                    audioInputStream.close();
                                } catch (Throwable th2) {
                                    th.addSuppressed(th2);
                                }
                            }
                            throw th;
                        }
                    } catch (Throwable th3) {
                        try {
                            bufferedInputStream.close();
                        } catch (Throwable th4) {
                            th3.addSuppressed(th4);
                        }
                        throw th3;
                    }
                } finally {
                }
            } catch (Exception e) {
            }
        }, "DrunVisual Sound Player");
        thread.setDaemon(true);
        thread.start();
    }

    private static void applyVolume(Clip clip, float f) {
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl control = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            control.setValue(Math.max(control.getMinimum(), Math.min((float) (20.0d * Math.log10(Math.max(0.01f, f))), control.getMaximum())));
        }
    }
}
