package drunvisual.animation;

public class HotbarSelectionAnimation {
    private static final int UNSET_SLOT = -1;
    private static final int HOTBAR_LEFT_OFFSET = 91;
    private static final int HOTBAR_SLOT_WIDTH = 20;
    private static final int SELECTION_TEXTURE_OFFSET = 1;
    private float startValue;
    private float targetValue;
    private long startTimeMs;
    private float value = Float.NaN;
    private long durationMs = 160;
    private int selectedSlot = UNSET_SLOT;
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

    public void a(int i, int i2, long j) {
        this.durationMs = Math.max(1L, j);
        float f = ((i2 - HOTBAR_LEFT_OFFSET) - 1) + (i * HOTBAR_SLOT_WIDTH);
        if (this.selectedSlot == UNSET_SLOT || Float.isNaN(this.value)) {
            this.selectedSlot = i;
            this.value = f;
            this.startValue = f;
            this.targetValue = f;
            this.startTimeMs = 0L;
            return;
        }
        if (i == this.selectedSlot) {
            this.targetValue = f;
            if (this.startTimeMs == 0) {
                this.value = f;
                return;
            }
            return;
        }
        this.startValue = this.value;
        this.targetValue = f;
        this.startTimeMs = System.currentTimeMillis();
        this.selectedSlot = i;
    }

    public float b() {
        if (Float.isNaN(this.value)) {
            return 0.0f;
        }
        return this.value;
    }

    public boolean c() {
        return this.startTimeMs != 0;
    }

    public void d() {
        this.value = Float.NaN;
        this.startTimeMs = 0L;
        this.selectedSlot = UNSET_SLOT;
    }
}
