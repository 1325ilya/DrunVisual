package drunvisual.events;

import net.minecraft.client.util.math.MatrixStack;

public class WorldRenderEvent extends DrunVisualEvent {
    private final MatrixStack matrices;
    private final float tickDelta;

    public WorldRenderEvent(MatrixStack MatrixStackVar, float f) {
        this.matrices = MatrixStackVar;
        this.tickDelta = f;
    }

    public MatrixStack matrices() {
        return this.matrices;
    }

    public float tickDelta() {
        return this.tickDelta;
    }

    public MatrixStack a() {
        return this.matrices;
    }

    public float b() {
        return this.tickDelta;
    }
}
