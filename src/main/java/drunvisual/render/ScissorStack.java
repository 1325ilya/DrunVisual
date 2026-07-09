package drunvisual.render;

import java.util.Stack;
import net.minecraft.client.util.math.MatrixStack;

public class ScissorStack {
    private final Renderer2D a;
    private final Stack<ScissorBounds> b = new Stack<>();

    private static class ScissorBounds {
        private final float a;
        private final float b;
        private final float c;
        private final float d;
        private final float e;

        public ScissorBounds(float f, float f2, float f3, float f4, float f5) {
            this.a = f;
            this.b = f2;
            this.c = f3;
            this.d = f4;
            this.e = f5;
        }

        public float a() {
            return this.a;
        }

        public float b() {
            return this.b;
        }

        public float c() {
            return this.c;
        }

        public float d() {
            return this.d;
        }

        public float e() {
            return this.e;
        }
    }

    public ScissorStack(Renderer2D renderer2D) {
        this.a = renderer2D;
    }

    public void a(float f, float f2, float f3, float f4, MatrixStack MatrixStackVar) {
        a(f, f2, f3, f4, 0.0f, MatrixStackVar);
    }

    public void a(float f, float f2, float f3, float f4, float f5, MatrixStack MatrixStackVar) {
        if (this.b.isEmpty()) {
            this.b.push(new ScissorBounds(f, f2, f3, f4, f5));
            this.a.a(f, f2, f3, f4, f5, MatrixStackVar);
            return;
        }
        ScissorBounds scissorBoundsPeek = this.b.peek();
        float fMax = Math.max(scissorBoundsPeek.a(), f);
        float fMax2 = Math.max(scissorBoundsPeek.b(), f2);
        float fMin = Math.min(scissorBoundsPeek.a() + scissorBoundsPeek.c(), f + f3) - fMax;
        float fMin2 = Math.min(scissorBoundsPeek.b() + scissorBoundsPeek.d(), f2 + f4) - fMax2;
        if (fMin <= 0.0f || fMin2 <= 0.0f) {
            this.b.push(new ScissorBounds(scissorBoundsPeek.a(), scissorBoundsPeek.b(), 0.0f, 0.0f, f5));
            return;
        }
        this.a.a(MatrixStackVar);
        this.a.a(fMax, fMax2, fMin, fMin2, f5, MatrixStackVar);
        this.b.push(new ScissorBounds(fMax, fMax2, fMin, fMin2, f5));
    }

    public void a(MatrixStack MatrixStackVar) {
        if (this.b.isEmpty()) {
            return;
        }
        this.b.pop();
        this.a.a(MatrixStackVar);
        if (this.b.isEmpty()) {
            return;
        }
        ScissorBounds scissorBoundsPeek = this.b.peek();
        this.a.a(scissorBoundsPeek.a(), scissorBoundsPeek.b(), scissorBoundsPeek.c(), scissorBoundsPeek.d(), scissorBoundsPeek.e(), MatrixStackVar);
    }
}
