package drunvisual.gui.widgets;

import java.util.function.Consumer;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.TextInput;
import drunvisual.core.Bool;
import drunvisual.render.Renderer2D;

public class CoordinateInputPanel {
    private static final float c = 23.0f;
    private static final float d = 5.0f;
    private static final float e = 7.5f;
    private final TextInput f = new TextInput(TextInput.InputType.COORDINATE, "0", "X");
    private final TextInput g = new TextInput(TextInput.InputType.COORDINATE, "0", "Y");
    private final TextInput h = new TextInput(TextInput.InputType.COORDINATE, "0", "Z");
    private Consumer<int[]> i;
    public static int a;
    public static boolean b;

    public CoordinateInputPanel() {
        this.f.b(true);
        this.g.b(true);
        this.h.b(true);
        this.f.a(e);
        this.g.a(e);
        this.h.a(e);
        this.f.a(10);
        this.g.a(10);
        this.h.a(10);
        this.f.e(true);
        this.g.e(true);
        this.h.e(true);
        this.f.setClearOnFocus(true);
        this.g.setClearOnFocus(true);
        this.h.setClearOnFocus(true);
        this.f.a(str -> {
            f();
        });
        this.g.a(str2 -> {
            f();
        });
        this.h.a(str3 -> {
            f();
        });
    }

    public void a(int i, int i2, int i3) {
        this.f.setRawValue(String.valueOf(i));
        this.g.setRawValue(String.valueOf(i2));
        this.h.setRawValue(String.valueOf(i3));
    }

    public int a() {
        return parseCoord(this.f.a());
    }

    public int b() {
        return parseCoord(this.g.a());
    }

    public int c() {
        return parseCoord(this.h.a());
    }

    private static int parseCoord(String s) {
        if (s == null) {
            return 0;
        }
        String trimmed = s.trim();
        if (trimmed.isEmpty() || trimmed.equals("-") || trimmed.equals("+")) {
            return 0;
        }
        try {
            return Integer.parseInt(trimmed);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    public void a(Consumer<int[]> consumer) {
        this.i = consumer;
    }

    private void f() {
        if (this.i != null) {
            this.i.accept(new int[]{a(), b(), c()});
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, int i, int i2, float f4) {
        float f5 = (f3 - 10.0f) / 3.0f;
        float f6 = f + f5 + d;
        float f7 = f + ((f5 + d) * 2.0f);
        this.f.a(MatrixStackVar, renderer2D, f, f2, f5, c, i, i2, f4);
        this.g.a(MatrixStackVar, renderer2D, f6, f2, f5, c, i, i2, f4);
        this.h.a(MatrixStackVar, renderer2D, f7, f2, f5, c, i, i2, f4);
    }

    public boolean a(int i, int i2) {
        return this.f.a(i, i2) || this.g.a(i, i2) || this.h.a(i, i2);
    }

    public boolean c(int i, int i2, int i3) {
        boolean hitX = this.f.a(i, i2, i3);
        boolean hitY = this.g.a(i, i2, i3);
        boolean hitZ = this.h.a(i, i2, i3);
        return hitX || hitY || hitZ;
    }

    public boolean b(int i, int i2, int i3) {
        return !this.f.d() ? !this.g.d() ? !this.h.d() ? false : this.h.b(i, i2, i3) : this.g.b(i, i2, i3) : this.f.b(i, i2, i3);
    }

    public boolean a(char c2, int i) {
        return !this.f.d() ? !this.g.d() ? !this.h.d() ? false : this.h.a(c2, i) : this.g.a(c2, i) : this.f.a(c2, i);
    }

    public boolean d() {
        int i;
        if (this.f.d() || this.g.d() || this.h.d()) {
            i = 1;
        } else {
            i = 0;
        }
        return Bool.from(i);
    }

    public static float e() {
        return c;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
