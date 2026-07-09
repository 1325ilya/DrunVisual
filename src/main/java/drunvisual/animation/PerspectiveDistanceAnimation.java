package drunvisual.animation;

public class PerspectiveDistanceAnimation {
    private static final float FIRST_PERSON_DISTANCE = 1.0f;
    private static final float THIRD_PERSON_DISTANCE = 4.0f;
    private long startTimeMs;
    private boolean thirdPerson;
    private float value = THIRD_PERSON_DISTANCE;
    private float startValue = THIRD_PERSON_DISTANCE;
    private float targetValue = THIRD_PERSON_DISTANCE;
    private long durationMs = 300;
    private final EasingFunction easing = Easing.C;

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

    public void a(boolean z, long j) {
        this.durationMs = Math.max(1L, j);
        if (z && !this.thirdPerson) {
            this.startValue = FIRST_PERSON_DISTANCE;
            this.targetValue = THIRD_PERSON_DISTANCE;
            this.value = FIRST_PERSON_DISTANCE;
            this.startTimeMs = System.currentTimeMillis();
        } else if (!z && this.thirdPerson) {
            this.startValue = this.value;
            this.targetValue = FIRST_PERSON_DISTANCE;
            this.startTimeMs = System.currentTimeMillis();
        }
        this.thirdPerson = z;
    }

    public float b() {
        return this.value;
    }

    public boolean c() {
        return this.startTimeMs != 0;
    }

    public void d() {
        this.value = THIRD_PERSON_DISTANCE;
        this.startValue = THIRD_PERSON_DISTANCE;
        this.targetValue = THIRD_PERSON_DISTANCE;
        this.startTimeMs = 0L;
        this.thirdPerson = false;
    }
}
