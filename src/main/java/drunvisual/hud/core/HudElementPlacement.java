package drunvisual.hud.core;

public class HudElementPlacement {
    private float normalizedCenterX;
    private float normalizedCenterY;

    public void update(float f, float f2, float f3, float f4, float f5, float f6) {
        this.normalizedCenterX = clamp01((f + (f3 / 2.0f)) / f5);
        this.normalizedCenterY = clamp01((f2 + (f4 / 2.0f)) / f6);
    }

    public float resolveX(float f, float f2) {
        return (this.normalizedCenterX * f2) - (f / 2.0f);
    }

    public float resolveY(float f, float f2) {
        return (this.normalizedCenterY * f2) - (f / 2.0f);
    }

    public float normalizedCenterX() {
        return this.normalizedCenterX;
    }

    public float normalizedCenterY() {
        return this.normalizedCenterY;
    }

    public void setNormalizedCenterX(float f) {
        this.normalizedCenterX = clamp01(f);
    }

    public void setNormalizedCenterY(float f) {
        this.normalizedCenterY = clamp01(f);
    }

    public void a(float f, float f2, float f3, float f4, float f5, float f6) {
        update(f, f2, f3, f4, f5, f6);
    }

    public float a(float f, float f2) {
        return resolveX(f, f2);
    }

    public float b(float f, float f2) {
        return resolveY(f, f2);
    }

    public float a() {
        return normalizedCenterX();
    }

    public float b() {
        return normalizedCenterY();
    }

    public void a(float f) {
        setNormalizedCenterX(f);
    }

    public void b(float f) {
        setNormalizedCenterY(f);
    }

    private static float clamp01(float f) {
        return Math.max(0.0f, Math.min(1.0f, f));
    }
}
