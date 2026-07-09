package drunvisual.events;

import net.minecraft.client.util.math.MatrixStack;

public class HudRenderPostEvent extends DrunVisualEvent {
    private final MatrixStack matrices;
    private final int width;
    private final int height;
    private final float tickDelta;

    public HudRenderPostEvent(MatrixStack MatrixStackVar, int i, int i2, float f) {
        this.matrices = MatrixStackVar;
        this.width = i;
        this.height = i2;
        this.tickDelta = f;
    }

    public MatrixStack matrices() {
        return this.matrices;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public float tickDelta() {
        return this.tickDelta;
    }

    public MatrixStack a() {
        return this.matrices;
    }

    public int b() {
        return this.width;
    }

    public int c() {
        return this.height;
    }

    public float d() {
        return this.tickDelta;
    }
}
