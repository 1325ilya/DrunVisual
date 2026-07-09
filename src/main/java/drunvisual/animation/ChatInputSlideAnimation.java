package drunvisual.animation;

public class ChatInputSlideAnimation {
    private static final float START_OFFSET = 14.0f;
    private float startValue;
    private float targetValue;
    private long startTimeMs;
    private float value = Float.NaN;
    private long durationMs = 160;
    private final EasingFunction easing = Easing.h;

    public void a() {
        if (Float.isNaN(this.value) || this.startTimeMs == 0) {
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

    public void a(long j) {
        this.durationMs = Math.max(1L, j);
        if (Float.isNaN(this.value)) {
            this.value = START_OFFSET;
            this.startValue = START_OFFSET;
            this.targetValue = 0.0f;
            this.startTimeMs = System.currentTimeMillis();
            return;
        }
        if (this.value != this.targetValue) {
            this.startValue = this.value;
            this.targetValue = 0.0f;
            this.startTimeMs = System.currentTimeMillis();
        }
    }

    public float b() {
        if (Float.isNaN(this.value)) {
            return 0.0f;
        }
        return this.value;
    }

    public boolean c() {
        return b() > 0.0f;
    }

    public void d() {
        this.value = Float.NaN;
        this.startTimeMs = 0L;
    }
}
