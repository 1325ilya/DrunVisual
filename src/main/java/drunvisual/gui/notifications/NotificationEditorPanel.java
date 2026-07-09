package drunvisual.gui.notifications;

import java.awt.Color;
import java.util.function.Consumer;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.ColorPickerPopup;
import drunvisual.hud.notifications.Notification;
import drunvisual.markers.MapMarker;
import drunvisual.markers.MarkerManager;
import drunvisual.gui.widgets.TextInput;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.gui.widgets.CoordinateInputPanel;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class NotificationEditorPanel {
    private static final float c = 11.5f;
    private static final float d = 0.5f;
    private static final float e = 8.0f;
    private static final float f = 8.0f;
    private static final float g = 8.5f;
    private static final float h = 5.0f;
    private static final float i = 10.0f;
    private static final float j = 13.5f;
    private static final float k = 4.0f;
    private static final float l = 22.0f;
    private static final float m = 9.5f;
    private static final float n = 19.5f;
    private static final float o = 6.5f;
    private static final Color p = Theme.L;
    private static final Color q = Theme.M;
    private Notification r;
    private final CoordinateInputPanel t;
    private final NotificationIconPicker u;
    private float y;
    private float z;
    private Consumer<Notification> A;
    private Consumer<Notification> B;
    public static int a;
    public static boolean b;
    private final AnimationState v = new AnimationState();
    private boolean w = false;
    private boolean x = false;
    private final TextInput s = new TextInput(TextInput.InputType.TEXT, "", "Название");

    public NotificationEditorPanel() {
        this.s.a(24);
        this.s.a(this::a);
        this.s.a(7.5f);
        this.s.e(false);
        this.t = new CoordinateInputPanel();
        this.t.a(this::a);
        this.u = new NotificationIconPicker();
        this.u.a(this::a);
    }

    public void a(Notification notification) {
        boolean sameNotification = this.r == notification;
        this.r = notification;
        if (notification != null) {
            this.s.b(notification.a());
            if (!sameNotification) {
                this.t.a(notification.b(), notification.c(), notification.d());
            }
            this.u.a(notification.f());
            this.u.a(notification.e());
        }
        if (!sameNotification && this.x) {
            ColorPickerPopup.a().b();
            this.x = false;
        }
    }

    public Notification a() {
        return this.r;
    }

    public void a(Consumer<Notification> consumer) {
        this.A = consumer;
    }

    public void b(Consumer<Notification> consumer) {
        this.B = consumer;
    }

    private void a(String str) {
        if (this.r == null || str.isEmpty() || this.r.j()) {
            return;
        }
        String oldName = this.r.a();
        MapMarker mm = MarkerManager.a(this.r.b(), this.r.c(), this.r.d());
        this.r.a(str);
        if (mm != null && oldName != null && oldName.equals(mm.a())) {
            mm.a(str);
        }
        f();
    }

    private void a(int[] iArr) {
        if (this.r == null || this.r.j()) {
            return;
        }
        MapMarker mm = MarkerManager.a(this.r.b(), this.r.c(), this.r.d());
        this.r.a(iArr[0]);
        this.r.b(iArr[1]);
        this.r.c(iArr[2]);
        if (mm != null) {
            mm.a(iArr[0]);
            mm.b(iArr[1]);
            mm.c(iArr[2]);
        }
        f();
    }

    private void a(Notification.Icon icon) {
        if (this.r == null || this.r.j()) {
            return;
        }
        this.r.a(icon);
        MapMarker mm = MarkerManager.a(this.r.b(), this.r.c(), this.r.d());
        if (mm != null) {
            mm.a(mapMarkerIconOf(icon));
        }
        f();
    }

    private void a(Color color) {
        if (this.r != null) {
            this.r.a(color);
            this.u.a(color);
            MapMarker mm = MarkerManager.a(this.r.b(), this.r.c(), this.r.d());
            if (mm != null) {
                mm.a(color);
            }
            f();
        }
    }

    private static MapMarker.Icon mapMarkerIconOf(Notification.Icon icon) {
        if (icon == null) {
            return MapMarker.Icon.EVENT;
        }
        try {
            return MapMarker.Icon.valueOf(icon.name());
        } catch (IllegalArgumentException ex) {
            return MapMarker.Icon.EVENT;
        }
    }

    public boolean b() {
        return (this.r == null || !this.r.j()) ? false : true;
    }

    private void f() {
        if (this.B == null || this.r == null) {
            return;
        }
        this.B.accept(this.r);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i2, int i3, float f6) {
        if (this.r != null) {
            this.v.a();
            int i4 = (int) (255.0f * f6);
            Color colorA = Theme.a(Theme.g, i4);
            Color colorA2 = Theme.a(Theme.n, i4);
            renderer2D.a(f2 - d, f3 - d, f4 + 1.0f, f5 + 1.0f, c, colorA, colorA, colorA2, colorA2, MatrixStackVar);
            Color colorA3 = Theme.a(Theme.e, i4);
            Color colorA4 = Theme.a(Theme.f, i4);
            renderer2D.a(f2, f3, f4, f5, c, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
            FontRenderer fontRenderer = FontManager.a[15];
            FontRenderer fontRenderer2 = FontManager.a[14];
            float f7 = f2 + 8.0f;
            float f8 = f4 - 16.0f;
            float f9 = f3 + 8.0f;
            fontRenderer.a("Настройки меток", f7, f9, Theme.a(Theme.a, i4), MatrixStackVar);
            a(MatrixStackVar, renderer2D, ((f2 + f4) - 8.0f) - j, f9 - 2.0f, i2, i3, f6);
            float f10 = f9 + 24.0f;
            Color colorA5 = Theme.a(Theme.b, i4);
            float f11 = f10 + 12.0f;
            fontRenderer2.a("Название метки", f7, f11 - 11.0f, colorA5, MatrixStackVar);
            a(MatrixStackVar, renderer2D, f7, f11, f8, i2, i3, f6);
            float f12 = f11 + 22.0f + 14.0f;
            fontRenderer2.a("Координаты", f7, f12 - 11.0f, colorA5, MatrixStackVar);
            this.t.a(MatrixStackVar, renderer2D, f7, f12, f8, i2, i3, f6);
            float fE = f12 + CoordinateInputPanel.e() + 14.0f;
            fontRenderer2.a("Иконка", f7, fE - 11.0f, colorA5, MatrixStackVar);
            this.u.a(MatrixStackVar, renderer2D, f7, fE, f8, i2, i3, f6);
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, float f4) {
        int i4 = (int) (255.0f * f4);
        boolean zB = GuiInteractionState.a().b();
        int i5 = (zB || !GuiInput.a(f2, f3, j, j, (double) i2, (double) i3)) ? 0 : 1;
        if (zB && this.w) {
            this.v.a(0.0d, 0.15d, Easing.h);
            this.w = false;
        } else if (!zB && Bool.from(i5) != this.w) {
            this.v.a(i5 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.w = Bool.from(i5);
        }
        float fJ = (float) this.v.j();
        Color colorB = Theme.b(Theme.q, f4);
        Color colorB2 = Theme.b(Theme.n, f4);
        renderer2D.a(f2 - d, f3 - d, 14.5f, 14.5f, k, colorB, colorB, colorB2, colorB2, MatrixStackVar);
        Color colorA = ColorUtils.a(Theme.f, p, fJ);
        Color colorA2 = ColorUtils.a(Theme.e, q, fJ);
        Color colorA3 = Theme.a(colorA, i4);
        Color colorA4 = Theme.a(colorA2, i4);
        renderer2D.a(f2, f3, j, j, k, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        FontManager.e[15].a(IconGlyphs.K, f2 + ((j - 8.0f) / 2.0f), (f3 + ((j - 8.0f) / 2.0f)) - d, Theme.a(ColorUtils.a(Theme.b, Theme.Z, fJ), i4), MatrixStackVar);
        if (i5 == 0 || zB) {
            return;
        }
        GuiInput.g();
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5) {
        float f6 = (f4 - n) - 8.0f;
        this.s.a(MatrixStackVar, renderer2D, f2, f3, f6, l, i2, i3, f5);
        this.y = f2 + f6 + 8.0f;
        this.z = f3 + 1.25f;
        Color colorA = Theme.a(this.r.e(), (int) (255.0f * f5));
        Color colorC = Theme.c(colorA, 150);
        Color colorC2 = Theme.c(colorA, 100);
        renderer2D.a(this.y - d, this.z - d, 20.5f, 20.5f, 7.0f, colorA, colorA, colorC2, colorC2, MatrixStackVar);
        renderer2D.a(this.y, this.z, n, n, o, colorA, colorA, colorC, colorC, MatrixStackVar);
        if (GuiInteractionState.a().b()) {
        } else if (GuiInput.a(this.y, this.z, n, n, i2, i3)) {
            GuiInput.g();
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i2, int i3, float f2) {
        ColorPickerPopup.a().a(MatrixStackVar, renderer2D, i2, i3, f2);
    }

    public boolean a(float f2, float f3, float f4, float f5, int i2, int i3) {
        if (this.r == null) {
            return false;
        }
        if (this.x && ColorPickerPopup.a().a(i2, i3)) {
            return true;
        }
        if (GuiInput.a(((f2 + f4) - 8.0f) - j, (f3 + 8.0f) - 2.0f, j, j, i2, i3)) {
            if (this.A != null) {
                this.A.accept(this.r);
            }
            return true;
        }
        if (GuiInput.a(this.y, this.z, n, n, i2, i3)) {
            if (this.x) {
                c();
            } else {
                g();
            }
            return true;
        }
        if (b()) {
            return false;
        }
        float f6 = f2 + 8.0f;
        float labelGap = 12.0f;
        float fieldGap = 14.0f;
        float titleArea = 24.0f;
        float inputH = 22.0f;
        float coordH = CoordinateInputPanel.e();
        float baseY = f3 + 8.0f + titleArea + labelGap;
        float iconY = baseY + inputH + fieldGap + coordH + fieldGap;
        boolean hitName = this.s.a(i2, i3, 0);
        boolean hitCoord = this.t.c(i2, i3, 0);
        boolean hitIcon = this.u.a(f6, iconY, i2, i3);
        if (hitName || hitCoord || hitIcon) {
            return true;
        }
        this.s.a(false);
        return false;
    }

    public boolean a(int i2, int i3) {
        if (!this.x || !ColorPickerPopup.a().c(i2, i3)) {
            return false;
        }
        c();
        return true;
    }

    private void g() {
        this.x = true;
        ColorPickerPopup.a().a((int) (this.y + 9.75f + 45.0f), (int) ((this.z + n) - 12.0f), ColorPickerPopup.Edge.RIGHT, "Кастомный цвет", this.r.e(), 1.0f, this::a);
    }

    public void c() {
        if (this.x) {
            ColorPickerPopup.a().b();
            this.x = false;
        }
    }

    public void b(int i2, int i3) {
        if (this.x) {
            ColorPickerPopup.a().b(i2, i3);
        }
    }

    public void a(int i2, int i3, double d2, double d3) {
        if (this.x) {
            ColorPickerPopup.a().a(i2, i3, d2, d3);
        }
    }

    public boolean a(int i2, int i3, int i4) {
        return !this.s.d() ? !this.t.d() ? false : this.t.b(i2, i3, i4) : this.s.b(i2, i3, i4);
    }

    public boolean a(char c2, int i2) {
        return !this.s.d() ? !this.t.d() ? false : this.t.a(c2, i2) : this.s.a(c2, i2);
    }

    public boolean d() {
        return Bool.from((this.s.d() || this.t.d()) ? 1 : 0);
    }

    public boolean e() {
        return (this.x && ColorPickerPopup.a().c()) ? true : false;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
