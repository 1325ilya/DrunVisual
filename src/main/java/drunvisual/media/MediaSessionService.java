package drunvisual.media;

import com.wmedia.AudioLevels;
import com.wmedia.MediaInfo;
import com.wmedia.MediaProvider;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import drunvisual.core.Bool;
import drunvisual.events.ClientTickEvent;
import drunvisual.hud.core.HudService;
import drunvisual.hud.core.HudServiceInfo;

@HudServiceInfo(enabledByDefault = true)
public class MediaSessionService extends HudService {
    private static final long c = 400;
    private static final long d = 16;
    private static final AtomicReference<float[]> j;
    private static volatile boolean k;
    private static volatile boolean l;
    private static final int m = 6;
    private static final long t = 50;
    private static final long u = 16;
    public static int a;
    public static boolean b;
    private static Thread h = null;
    private static final AtomicReference<MediaSnapshot> i = new AtomicReference<>(null);
    private static final AtomicBoolean f = new AtomicBoolean(false);
    private static final AtomicBoolean g = new AtomicBoolean(false);
    private final MediaPlaybackState e = new MediaPlaybackState();
    private final float[] n = new float[m];
    private final float[] o = new float[m];
    private float p = 0.0f;
    private int q = 0;
    private long r = 0;
    private long s = 0;

    private static class MediaSnapshot {
        final String a;
        final String b;
        final boolean c;
        final float d;
        final float e;
        final byte[] f;
        final long g = System.currentTimeMillis();
        public static int h;
        public static boolean i;

        MediaSnapshot(String str, String str2, boolean z, float f, float f2, byte[] bArr) {
            this.a = str;
            this.b = str2;
            this.c = z;
            this.d = f;
            this.e = f2;
            this.f = bArr;
        }

        public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
            return null;
        }
    }

    @Override
    public void a() {
        super.a();
        j();
    }

    private static synchronized void j() {
        if ((h == null || !h.isAlive()) && !g.get()) {
            f.set(true);
            h = new Thread(MediaSessionService::k, "WMedia-Polling");
            h.setDaemon(true);
            h.setPriority(2);
            h.start();
            System.out.println("Started WMedia polling thread");
        }
    }

    private static void k() {
        long r8 = 0L;
        long j2 = 0;
        while (f.get() && !g.get()) {
            try {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - r8 >= c) {
                    r8 = jCurrentTimeMillis;
                    l();
                }
                if (jCurrentTimeMillis - j2 >= 16) {
                    j2 = jCurrentTimeMillis;
                    m();
                }
                Thread.sleep(8L);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } catch (Throwable th) {
                System.err.println("Polling error: " + th.getMessage());
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e2) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        System.out.println("Polling thread stopped");
    }

    private static void l() {
        String title;
        if (!q()) {
            i.set(null);
            return;
        }
        try {
            Optional<MediaInfo> currentMedia = MediaProvider.getCurrentMedia();
            if (currentMedia.isPresent()) {
                MediaInfo mediaInfo = currentMedia.get();
                if (mediaInfo.getTitle().isEmpty()) {

                    title = "No media";
                } else {
                    title = mediaInfo.getTitle();
                }
                i.set(new MediaSnapshot(title, mediaInfo.getArtist(), mediaInfo.isPlaying(), mediaInfo.getPositionMs(), mediaInfo.getDurationMs(), mediaInfo.getAlbumArt()));
            } else {

i.set(new MediaSnapshot("No media", "", false, 0.0f, 0.0f, null));
            }
        } catch (Throwable th) {
            k = false;
            i.set(null);
        }
    }

    private static void m() {
        if (!q()) {
            float[] fArr = new float[3];
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            j.set(fArr);
            return;
        }
        try {
            Optional<AudioLevels> audioLevels = MediaProvider.getAudioLevels();
            if (audioLevels.isPresent()) {
                AudioLevels audioLevels2 = audioLevels.get();
                j.set(new float[]{audioLevels2.getMasterPeak(), audioLevels2.getLeftPeak(), audioLevels2.getRightPeak()});
            } else {
                float[] fArr2 = new float[3];
                fArr2[0] = 0.0f;
                fArr2[1] = 0.0f;
                fArr2[2] = 0.0f;
                j.set(fArr2);
            }
        } catch (Throwable th) {
            float[] fArr3 = new float[3];
            fArr3[0] = 0.0f;
            fArr3[1] = 0.0f;
            fArr3[0 | 2] = 0.0f;
            j.set(fArr3);
        }
    }

    @EventHandler
    private void a(ClientTickEvent clientTickEvent) {
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.r >= t) {
            this.r = jCurrentTimeMillis;
            n();
        }
        if (jCurrentTimeMillis - this.s >= 16) {
            this.s = jCurrentTimeMillis;
            o();
        }
    }

    private void n() {
        MediaSnapshot mediaSnapshot = i.get();
        if (mediaSnapshot != null) {
            this.e.a(mediaSnapshot.a, mediaSnapshot.b, mediaSnapshot.c, mediaSnapshot.d, mediaSnapshot.e, mediaSnapshot.f);
        } else {
            this.e.a("No media", "", false, 0.0f, 0.0f, null);
        }
        this.e.m();
    }

    private void o() {
        this.q = (this.q - (-2)) - 1;
        float[] fArr = j.get();
        if (fArr == null || (fArr[0] == 0.0f && fArr[1] == 0.0f && fArr[2] == 0.0f)) {
            p();
            return;
        }
        float f2 = fArr[0];
        float f3 = fArr[1];
        float f4 = fArr[2];
        float f5 = f2 > this.p + 0.15f ? (f2 - this.p) * 2.0f : 0.0f;
        this.p = (f2 * 0.7f) + (this.p * 0.3f);
        float fMin = Math.min(f2 * 1.4f, 1.0f);
        float fMin2 = Math.min(f3 * 1.4f, 1.0f);
        float fMin3 = Math.min(f4 * 1.4f, 1.0f);
        float f6 = this.q;
        float fSin = (float) ((Math.sin(((double) f6) * 0.5d) * 0.08d) + 0.92d);
        float fSin2 = (float) ((Math.sin((((double) f6) * 0.6d) + 1.0d) * 0.08d) + 0.92d);
        float fSin3 = (float) ((Math.sin((((double) f6) * 0.7d) + 2.0d) * 0.08d) + 0.92d);
        float[] fArr2 = {((fMin2 * 0.6f) + (f5 * 0.3f)) * fSin, ((fMin2 * 0.85f) + (fMin * 0.3f) + (f5 * 0.5f)) * fSin2, ((fMin2 * 0.5f) + (fMin * 0.7f) + (f5 * 0.7f)) * fSin3, ((fMin3 * 0.5f) + (fMin * 0.7f) + (f5 * 0.7f)) * fSin, ((fMin3 * 0.85f) + (fMin * 0.3f) + (f5 * 0.5f)) * fSin3, ((fMin3 * 0.6f) + (f5 * 0.3f)) * fSin2};
        for (int i2 = 0; i2 < m; i2++) {
            float fMin4 = Math.min(fArr2[i2], 1.0f);
            if (fMin4 <= this.n[i2]) {
                this.n[i2] = (this.n[i2] * 0.6f) + (fMin4 * 0.4f);
            } else {
                this.n[i2] = (this.n[i2] * 0.1f) + (fMin4 * 0.9f);
            }
            if (this.n[i2] <= this.o[i2]) {
                this.o[i2] = this.o[i2] * 0.92f;
            } else {
                this.o[i2] = this.n[i2];
            }
            this.e.H()[i2] = Math.min((this.n[i2] * 0.7f) + (this.o[i2] * 0.3f), 1.0f);
        }
    }

    private void p() {
        for (int i2 = 0; i2 < m; i2++) {
            this.n[i2] = this.n[i2] * 0.8f;
            this.o[i2] = this.o[i2] * 0.85f;
            this.e.H()[i2] = (this.n[i2] * 0.7f) + (this.o[i2] * 0.3f);
        }
    }

    public void d() {
        int i2 = this.e.c() ? 0 : 1;
        this.e.a(Bool.from(i2));
        if (i2 != 0) {
            this.e.j();
        } else {
            this.e.i();
        }
        if (g()) {
            new Thread(() -> {
                try {
                    MediaProvider.playPause();
                } catch (Throwable th) {
                }
            }, "WMedia-PlayPause").start();
        }
    }

    public void e() {
        if (g()) {
            new Thread(() -> {
                try {
                    MediaProvider.next();
                } catch (Throwable th) {
                }
            }, "WMedia-Next").start();
        }
    }

    public void f() {
        if (g()) {
            new Thread(() -> {
                try {
                    MediaProvider.previous();
                } catch (Throwable th) {
                }
            }, "WMedia-Previous").start();
        }
    }

    public void a(float f2) {
        this.e.a(f2 * this.e.s());
    }

    public void b(float f2) {
        this.e.b(f2 * this.e.s());
    }

    public void c(float f2) {
        float fS = f2 * this.e.s();
        this.e.b(fS);
        this.e.e();
        if (!g() || this.e.s() <= 0.0f) {
            return;
        }
        long j2 = (long) fS;
        new Thread(() -> {
            try {
                MediaProvider.seek(j2);
            } catch (Throwable th) {
            }
        }, "WMedia-Seek").start();
    }

    private static boolean q() {
        if (g.get()) {
            return false;
        }
        if (!l) {
            l = true;
            try {
                k = MediaProvider.isAvailable();

                System.out.println("WMedia library available: " + k);
            } catch (Throwable th) {

                System.out.println("WMedia library error: " + th.getMessage());
                k = false;
            }
        }
        return k;
    }

    public static boolean g() {
        return Bool.from((!k || g.get()) ? 0 : 1);
    }

    public static synchronized void h() {
        if (g.getAndSet(true)) {
            return;
        }
        System.out.println("Shutting down WMedia...");
        f.set(false);
        if (h != null) {
            h.interrupt();
            try {
                h.join(1000L);
            } catch (InterruptedException e) {
            }
            h = null;
        }
        i.set(null);
        float[] fArr = new float[3];
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[0 | 2] = 0.0f;
        j.set(fArr);
        if (k) {
            try {
                MediaProvider.shutdown();

                System.out.println("MediaProvider.shutdown() called");
            } catch (Throwable th) {

                System.err.println("Error during shutdown: " + th.getMessage());
            }
        }
        k = false;

        System.out.println("WMedia shutdown complete");
    }

    @Generated
    public MediaPlaybackState i() {
        return this.e;
    }

    public static String b(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }

    static {
        float[] fArr = new float[3];
        fArr[0] = 0.0f;
        fArr[1] = 0.0f;
        fArr[2] = 0.0f;
        j = new AtomicReference<>(fArr);
        k = false;
        l = false;
    }
}
