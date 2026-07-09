package drunvisual.animation;

public class ChatMessageSlideAnimation {
    private float value;
    private float startValue;
    private float targetValue;
    private long durationMs;
    private final EasingFunction easing = Easing.h;
    private long startTimeMs = System.currentTimeMillis();

    public ChatMessageSlideAnimation(float f, float f2, long j) {
        this.value = f;
        this.startValue = f;
        this.targetValue = f2;
        this.durationMs = Math.max(1L, j);
    }

    public void a() {
        if (this.startTimeMs == 0) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis() - this.startTimeMs;
        if (jCurrentTimeMillis >= this.durationMs) {
            this.value = this.targetValue;
            this.startTimeMs = 0L;
        } else {
            this.value = (float) (((double) this.startValue) + (((double) (this.targetValue - this.startValue)) * this.easing.ease((double) jCurrentTimeMillis / (double) this.durationMs)));
        }
    }

    public float b() {
        return this.value;
    }

    public boolean c() {
        return this.startTimeMs != 0;
    }

    public boolean d() {
        return this.startTimeMs == 0 && this.value == this.targetValue;
    }
}
