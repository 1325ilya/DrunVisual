package drunvisual.gui.widgets;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.config.ConfigState;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;

public class TextInput {
    private static final Set<TextInput> ACTIVE_INPUTS = new HashSet();
    private final InputType inputType;
    private String value;
    private String placeholder;
    private int maxLength;
    private float x;
    private float y;
    private float width;
    private float height;
    private boolean focused;
    private boolean visible;
    private boolean editable;
    private boolean centered;
    private boolean clearOnFocus;
    private boolean valueSelected;
    private Consumer<String> changeListener;
    private Color background;
    private Color foreground;

    public enum InputType {
        TEXT,
        INT,
        COORDINATE,
        PLAYER,
        KEY,
        FILENAME
    }

    public TextInput(InputType inputType) {
        this(inputType, "", "");
    }

    public TextInput(InputType inputType, String str) {
        this(inputType, "", str);
    }

    public TextInput(InputType inputType, String str, String str2) {
        this.maxLength = 256;
        this.width = 100.0f;
        this.height = 16.0f;
        this.visible = true;
        this.editable = true;
        this.background = Theme.e;
        this.foreground = Theme.a;
        this.inputType = inputType == null ? InputType.TEXT : inputType;
        this.value = clean(str);
        this.placeholder = str2 == null ? "" : str2;
    }

    public void a(float f, float f2) {
        this.x = f;
        this.y = f2;
    }

    public void b(float f, float f2) {
        this.width = Math.max(1.0f, f);
        this.height = Math.max(1.0f, f2);
    }

    public void a(float f) {
        this.width = Math.max(1.0f, f);
    }

    public void a(float f, float f2, float f3, float f4) {
        this.x = f;
        this.y = f2;
        this.width = Math.max(1.0f, f3);
        this.height = Math.max(1.0f, f4);
    }

    public void b(float f) {
        this.height = Math.max(1.0f, f);
    }

    public void a(Color color, Color color2) {
        if (color != null) {
            this.background = color;
        }
        if (color2 != null) {
            this.foreground = color2;
        }
    }

    public void a(boolean z) {
        boolean wasFocused = this.focused;
        this.focused = z;
        if (z) {
            ACTIVE_INPUTS.add(this);
            if (!wasFocused && this.clearOnFocus && !this.value.isEmpty()) {
                this.valueSelected = true;
            }
        } else {
            ACTIVE_INPUTS.remove(this);
            this.valueSelected = false;
        }
    }

    public void setClearOnFocus(boolean v) {
        this.clearOnFocus = v;
    }

    public void a(int i) {
        this.maxLength = Math.max(0, i);
        if (this.value.length() > this.maxLength) {
            this.value = this.value.substring(0, this.maxLength);
        }
    }

    public void a(String str) {
        setValue(str);
    }

    public void setRawValue(String str) {
        String strClean = clean(str);
        if (strClean.length() > this.maxLength) {
            strClean = strClean.substring(0, this.maxLength);
        }
        this.value = strClean;
    }

    public void b(boolean z) {
        this.visible = z;
    }

    public void a(Consumer<String> consumer) {
        this.changeListener = consumer;
    }

    public String a() {
        return this.value;
    }

    public String b() {
        return this.placeholder;
    }

    public void b(String str) {
        this.placeholder = str == null ? "" : str;
    }

    public int c() {
        return this.maxLength;
    }

    public void b(int i) {
        a(i);
    }

    public boolean d() {
        return this.focused;
    }

    public void c(boolean z) {
        this.editable = z;
    }

    public void d(boolean z) {
        a(z);
    }

    public boolean e() {
        return this.editable;
    }

    public void e(boolean z) {
        this.centered = z;
    }

    public static boolean f() {
        return !ACTIVE_INPUTS.isEmpty();
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, int i, int i2, float f5) {
        a(f, f2, f3, f4);
        if (!this.visible) {
            return;
        }
        int alpha = (int) (255.0f * f5);
        Color bgTop = Theme.a(this.background, alpha);
        Color bgBottom = Theme.a(Theme.f, alpha);
        Color outerTop = Theme.a(Theme.q, alpha);
        Color outerBottom = Theme.a(Theme.n, alpha);
        renderer2D.a(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, 6.5f, outerTop, outerTop, outerBottom, outerBottom, MatrixStackVar);
        renderer2D.a(f, f2, f3, f4, 6.5f, bgTop, bgTop, bgBottom, bgBottom, MatrixStackVar);
        FontRenderer fontRenderer = FontManager.a[14];
        String display = this.value.isEmpty() ? this.placeholder : this.value;
        Color textColor = this.value.isEmpty() ? Theme.a(Theme.b, alpha) : Theme.a(this.foreground, alpha);
        float textX = this.centered ? f + ((f3 - fontRenderer.a(display)) / 2.0f) : f + 5.0f;
        float textY = (f2 + (f4 / 2.0f)) - (fontRenderer.b("Ag") / 4.0f);
        renderer2D.b().a(f, f2, f3, f4, 6.5f, MatrixStackVar);
        fontRenderer.a(display, textX, textY, textColor, MatrixStackVar);
        if (this.focused && this.editable) {
            float caretX = textX + fontRenderer.a(this.value);
            if (caretX < f + f3 - 2.0f) {
                renderer2D.a(caretX, f2 + 4.0f, 0.5f, f4 - 8.0f, Theme.a(this.foreground, alpha), MatrixStackVar);
            }
        }
        renderer2D.b().a(MatrixStackVar);
    }

    public boolean a(int i, int i2) {
        return this.visible && contains(i, i2);
    }

    public boolean a(int i, int i2, int i3) {
        boolean zA = a(i, i2);
        if (i3 == 0) {
            a(zA);
        }
        return zA;
    }

    public boolean a(char c, int i) {
        if (!this.focused || !this.editable || Character.isISOControl(c)) {
            return false;
        }
        if (this.valueSelected) {
            this.value = "";
            this.valueSelected = false;
        }
        setValue(this.value + c);
        return true;
    }

    public boolean b(int keyCode, int scanCode, int modifiers) {
        if (!this.focused || !this.editable) {
            return false;
        }
        if (keyCode == 259) {
            if (this.valueSelected) {
                this.value = "";
                this.valueSelected = false;
                if (this.changeListener != null) {
                    this.changeListener.accept(this.value);
                }
            } else if (!this.value.isEmpty()) {
                this.value = this.value.substring(0, this.value.length() - 1);
                if (this.changeListener != null) {
                    this.changeListener.accept(this.value);
                }
            }
        } else if (this.valueSelected) {
            this.valueSelected = false;
        }
        return true;
    }

    public float g() {
        return this.x;
    }

    public float h() {
        return this.y;
    }

    public float i() {
        return this.width;
    }

    public float j() {
        return this.height;
    }

    static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return str;
    }

    private void setValue(String str) {
        String strClean = clean(str);
        if (strClean.length() > this.maxLength) {
            strClean = strClean.substring(0, this.maxLength);
        }
        this.value = strClean;
        if (this.changeListener != null) {
            this.changeListener.accept(this.value);
        }
    }

    private String clean(String str) {
        if (str == null) {
            return "";
        }
        switch (this.inputType.ordinal()) {
            case ConfigState.a /* 1 */:
                return str.replaceAll("[^0-9-]", "");
            case 2:
                return str.replaceAll("[^0-9+\\-., ]", "");
            default:
                return str;
        }
    }

    private boolean contains(int i, int i2) {
        return ((float) i) >= this.x && ((float) i2) >= this.y && ((float) i) <= this.x + this.width && ((float) i2) <= this.y + this.height;
    }
}
