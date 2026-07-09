package drunvisual.gui.modules;

import java.awt.Color;
import java.util.Iterator;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.core.GuiLayerRegistry;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.gui.settings.TokenSettingWidget;
import drunvisual.module.ClientModule;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ItemToggleSetting;
import drunvisual.settings.KeySetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.Setting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.settings.TokenSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiEntry;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.gui.settings.BooleanSettingWidget;
import drunvisual.gui.settings.ColorSettingWidget;
import drunvisual.gui.settings.ItemToggleSettingWidget;
import drunvisual.gui.settings.KeyBindSettingWidget;
import drunvisual.gui.settings.ModeSettingWidget;
import drunvisual.gui.settings.SettingGroupWidget;
import drunvisual.gui.settings.SettingWidget;
import drunvisual.gui.settings.SettingWidgetList;
import drunvisual.gui.settings.SliderSettingWidget;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;

public class ModuleCard {
    public static final float a = 150.0f;
    public static final float b = 30.0f;
    public static final float c = 100.0f;
    public static final float d = 30.0f;
    public static final float e = 9.5f;
    public static final float f = 8.0f;
    public static final float g = 8.0f;
    public static final float h = 7.0f;
    private static final int k = 25;
    private static final float l = 5.0f;
    private final GuiEntry m;
    private final ClientModule n;
    private final int o;
    private final boolean p;
    private final VerticalAlign q;
    private final boolean r;
    private float s;
    private float t;
    private final AnimationState u;
    private boolean v;
    private float w;
    private float x;
    private boolean y;
    private float z;
    private float A;
    private boolean B;
    private final SettingWidgetList C;
    private final PanelFadeOverlay D;
    private final AnimationState E;
    private boolean F;
    private KeyBindSettingWidget G;
    private float H;
    private int I;
    private int J;
    public static int i;
    public static boolean j;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$drunvisual$settings$TokenSetting$TokenType = new int[TokenSetting.TokenType.values().length];

        static {
            try {
                $SwitchMap$drunvisual$settings$TokenSetting$TokenType[TokenSetting.TokenType.COMMAND.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$drunvisual$settings$TokenSetting$TokenType[TokenSetting.TokenType.PLAYER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$drunvisual$settings$TokenSetting$TokenType[TokenSetting.TokenType.NUMBER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$drunvisual$settings$TokenSetting$TokenType[TokenSetting.TokenType.PRICE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public enum VerticalAlign {
        TOP,
        CENTER,
        BOTTOM;

        public static int d;
        public static boolean e;

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    public ModuleCard(GuiEntry guiEntry, ClientModule clientModule, int i2, boolean z, float f2, float f3, VerticalAlign verticalAlign) {
        this(guiEntry, clientModule, i2, z, f2, f3, verticalAlign, false);
    }

    private ModuleCard(GuiEntry guiEntry, ClientModule clientModule, int i2, boolean z, float f2, float f3, VerticalAlign verticalAlign, boolean z2) {
        this.u = new AnimationState();
        this.v = false;
        this.y = false;
        this.B = false;
        this.E = new AnimationState();
        this.F = true;
        this.H = 0.0f;
        this.I = 0;
        this.J = 0;
        this.m = guiEntry;
        this.n = clientModule;
        this.o = i2;
        this.p = z;
        this.s = f2;
        this.t = f3;
        this.q = verticalAlign;
        this.r = z2;
        this.C = new SettingWidgetList();
        this.C.b(7.0f);
        this.C.a(100.0f);
        z();
        y();
        this.D = new PanelFadeOverlay(k, l, 9.5f);
        this.u.d(0.0d);
        this.u.a(1.0d, 0.13d, Easing.h);
        this.E.d(1.0d);
    }

    public static ModuleCard a(GuiEntry guiEntry, ClientModule clientModule, int i2, boolean z, float f2, float f3) {
        return new ModuleCard(guiEntry, clientModule, i2, z, f2, f3, VerticalAlign.CENTER, true);
    }

    public void a(float f2, float f3, boolean z) {
        this.y = true;
        this.z = f2;
        this.A = f3;
        this.B = z;
    }

    private void y() {
        int iF;
        if (this.m != null) {
            iF = this.m.f();
        } else {
            iF = -1;
        }
        this.G = new KeyBindSettingWidget("Клавиша активации", iF);
    }

    private void z() {
        if (this.n != null) {
            Iterator<Setting<?>> it = this.n.m().iterator();
            while (it.hasNext()) {
                SettingWidget settingWidgetA = a(it.next());
                if (settingWidgetA != null) {
                    this.C.a(settingWidgetA);
                }
            }
        }
    }

    private SettingWidget a(Setting<?> setting) {
        if (setting instanceof BooleanSetting) {
            return new BooleanSettingWidget((BooleanSetting) setting);
        }
        if (setting instanceof SliderSetting) {
            return new SliderSettingWidget((SliderSetting) setting);
        }
        if (setting instanceof ModeSetting) {
            return new ModeSettingWidget((ModeSetting) setting);
        }
        if (setting instanceof KeySetting) {
            return new KeyBindSettingWidget((KeySetting) setting);
        }
        if (setting instanceof ColorSetting) {
            return new ColorSettingWidget((ColorSetting) setting);
        }
        if (setting instanceof TokenSetting) {
            TokenSetting tokenSetting = (TokenSetting) setting;
            return new TokenSettingWidget(tokenSetting, a(tokenSetting.b()));
        }
        if (setting instanceof ItemToggleSetting) {
            return new ItemToggleSettingWidget((ItemToggleSetting) setting);
        }
        return !(setting instanceof SettingGroup) ? null : new SettingGroupWidget((SettingGroup) setting);
    }

    private TokenSettingWidget.ValueType a(TokenSetting.TokenType tokenType) {
        switch (AnonymousClass1.$SwitchMap$drunvisual$settings$TokenSetting$TokenType[tokenType.ordinal()]) {
            case ConfigState.a /* 1 */:
                return TokenSettingWidget.ValueType.COMMAND;
            case 2:
                return TokenSettingWidget.ValueType.PLAYER;
            case 3:
                return TokenSettingWidget.ValueType.INT;
            case 4:
                return TokenSettingWidget.ValueType.PRICE;
            default:
                return TokenSettingWidget.ValueType.COMMAND;
        }
    }

    public float a() {
        if (this.r) {
            return 30.0f;
        }
        return Math.max(30.0f, Math.min(this.C.m(), 100.0f));
    }

    public void a(float f2) {
        this.s = f2;
    }

    public void b() {
        if (this.v) {
            return;
        }
        this.v = true;
        this.C.g();
        this.u.a(0.0d, 0.13d, Easing.g);
    }

    public void c() {
        this.v = true;
        this.u.d(0.0d);
        this.C.g();
        if (!this.r || this.G == null) {
            return;
        }
        this.G.e();
    }

    public void d() {
        this.v = false;
        this.u.d(1.0d);
        this.C.g();
    }

    public boolean e() {
        return (this.v && this.u.d() && this.u.j() < 0.01d) ? true : false;
    }

    public boolean f() {
        return this.v;
    }

    public boolean g() {
        return this.r;
    }

    public boolean a(float f2, float f3) {
        if (this.y) {
            return false;
        }
        float f4 = this.s + (this.t / 2.0f);
        return Bool.from((f4 < f2 || f4 > f2 + f3) ? 1 : 0);
    }

    private float b(float f2) {
        float fA = a();
        switch (this.q.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                return 8.0f + (f2 / 2.0f);
            case 2:
                return (fA - 8.0f) - (f2 / 2.0f);
            default:
                return fA / 2.0f;
        }
    }

    public static float a(float f2, float f3, VerticalAlign verticalAlign, float f4, float f5) {
        float f6;
        float f7 = f2 + (f3 / 2.0f);
        switch (verticalAlign.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                f6 = 8.0f + (f4 / 2.0f);
                break;
            case 2:
                f6 = (f5 - 8.0f) - (f4 / 2.0f);
                break;
            default:
                f6 = f5 / 2.0f;
                break;
        }
        return f7 - f6;
    }

    public static boolean a(float f2, float f3, float f4, float f5) {
        return Bool.from((f2 + f3 <= f4 || f2 >= f4 + f5) ? 0 : 1);
    }

    private GuiLayerRegistry.Layer A() {
        return !this.r ? GuiLayerRegistry.Layer.SETTINGS_PANEL : GuiLayerRegistry.Layer.KEYBIND_PANEL;
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, float f4, float f5) {
        a(MatrixStackVar, renderer2D, f2, f3, i2, i3, f4, f5, false);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, float f4, float f5, boolean z) {
        this.u.a();
        this.E.a();
        float fJ = (float) this.u.j();
        if (fJ >= 0.01f) {
            this.H = fJ;
            this.I = i2;
            this.J = i3;
            int i4 = !z ? i2 : -1;
            int i5 = !z ? i3 : -1;
            float fA = a();
            float fD = DrunVisualClickGuiScreen.d();
            float fMax = Math.max(0.0f, Math.min(1.0f, fJ));
            int i6 = (int) (255.0f * fMax);
            if (this.y) {
                a(MatrixStackVar, renderer2D, f2, f3, i4, i5, fA, fMax, i6);
            } else {
                a(MatrixStackVar, renderer2D, f2, f3, i4, i5, fA, fD, fMax, i6);
            }
            if (this.v || fMax <= 0.5f) {
                return;
            }
            GuiLayerRegistry.a().a(A(), this.w, this.x, 150.0f, fA);
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i2, int i3) {
        if (this.r) {
            return;
        }
        float fJ = (float) this.u.j();
        if (fJ >= 0.1f) {
            float fMax = Math.max(0.0f, Math.min(1.0f, fJ));
            this.C.a(MatrixStackVar, renderer2D, i2, i3, fMax, fMax);
        }
    }

    public void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i2, int i3) {
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, float f4, float f5, int i4) {
        this.w = this.z;
        this.x = this.A;
        float f6 = 150.0f * f5;
        float f7 = f4 * f5;
        float f8 = (this.w + 75.0f) - (f6 / 2.0f);
        float f9 = (this.x + (f4 / 2.0f)) - (f7 / 2.0f);
        Color colorA = Theme.a(Theme.q, i4);
        Color colorA2 = Theme.a(Theme.r, i4);
        renderer2D.a(f8 - (0.5f * f5), f9 - (0.5f * f5), f6 + f5, f7 + f5, 9.5f * f5, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        Color colorA3 = Theme.a(Theme.e, i4);
        Color colorA4 = Theme.a(Theme.f, i4);
        renderer2D.a(f8, f9, f6, f7, 9.5f * f5, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
        if (this.r && f5 > 0.1f && this.G != null) {
            this.G.a(MatrixStackVar, renderer2D, f8 + (3.0f * f5), f9 + ((f7 - (20.0f * f5)) / 2.0f), f6 - (6.0f * f5), i2, i3, f5, f5);
            return;
        }
        if (!this.r && f5 > 0.1f) {
            float f10 = f9 + (7.0f * f5);
            float f11 = ((f7 - 7.0f) - 6.0f) * f5;
            if (f6 <= 0.0f || f11 <= 0.0f) {
                return;
            }
            renderer2D.b().a(f8, f10, f6, f11, 9.5f * f5, MatrixStackVar);
            this.C.a(MatrixStackVar, renderer2D, f8, f9, f6, f7, i2, i3, f5, f5);
            int i5 = (!this.C.d(f7) || this.C.e(f9 + f7)) ? 0 : 1;
            if (Bool.from(i5) != this.F) {
                this.E.a(i5 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
                this.F = Bool.from(i5);
            }
            float fJ = (float) this.E.j();
            if (fJ > 0.01f) {
                this.D.a(MatrixStackVar, renderer2D, f8, f9, f6, f7, f5 * fJ);
            }
            renderer2D.b().a(MatrixStackVar);
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i2, int i3, float f4, float f5, float f6, int i4) {
        float f7 = this.s + (this.t / 2.0f);
        IconTextureRegistry.TextureInfo info = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
        float fB = info != null ? info.b() / 2.0f : 0.0f;
        float fC = info != null ? info.c() / 2.0f : 0.0f;
        this.x = f7 - b(fC);
        if (this.p) {
            this.w = ((f2 - 20.0f) - fB) - 150.0f;
        } else {
            this.w = f2 + f5 + 20.0f + fB;
        }
        float f8 = 150.0f * f6;
        float f9 = f4 * f6;
        float f10 = fB * f6;
        float f11 = fC * f6;
        float f12 = (this.w + 75.0f) - (f8 / 2.0f);
        float f13 = (this.x + (f4 / 2.0f)) - (f9 / 2.0f);
        float f14 = f7 - (f11 / 2.0f);
        float f15 = !this.p ? f12 - f10 : f12 + f8;
        Color colorA = Theme.a(Theme.q, i4);
        Color colorA2 = Theme.a(Theme.r, i4);
        renderer2D.a(f12 - (0.5f * f6), f13 - (0.5f * f6), f8 + f6, f9 + f6, 9.5f * f6, colorA, colorA, colorA2, colorA2, MatrixStackVar);
        Color colorA3 = Theme.a(Theme.e, i4);
        Color colorA4 = Theme.a(Theme.f, i4);
        renderer2D.a(f12, f13, f8, f9, 9.5f * f6, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        if (info != null) {
            Identifier IdentifierVarA = info.a();
            Color colorA5 = Theme.a(Theme.aa, i4);
            if (this.p) {
                renderer2D.a(IdentifierVarA, f15, f14, f10, f11, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, colorA5, MatrixStackVar);
            } else {
                renderer2D.a(IdentifierVarA, f15, f14, f10, f11, 0.0f, 1.0f, 0.0f, -1.0f, 1.0f, colorA5, MatrixStackVar);
            }
        }
        if (this.r && f6 > 0.1f && this.G != null) {
            this.G.a(MatrixStackVar, renderer2D, f12 + (3.0f * f6), f13 + ((f9 - (20.0f * f6)) / 2.0f), f8 - (6.0f * f6), i2, i3, f6, f6);
            return;
        }
        if (this.r || f6 <= 0.1f) {
            return;
        }
        float f16 = f13 + (7.0f * f6);
        float f17 = ((f9 - 7.0f) - 6.0f) * f6;
        if (f8 <= 0.0f || f17 <= 0.0f) {
            return;
        }
        renderer2D.b().a(f12, f16, f8, f17, 9.5f * f6, MatrixStackVar);
        this.C.a(MatrixStackVar, renderer2D, f12, f13, f8, f9, i2, i3, f6, f6);
        int i6 = (!this.C.d(f9) || this.C.e(f13 + f9)) ? 0 : 1;
        if (Bool.from(i6) != this.F) {
            this.E.a(i6 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.F = Bool.from(i6);
        }
        float fJ = (float) this.E.j();
        if (fJ > 0.01f) {
            this.D.a(MatrixStackVar, renderer2D, f12, f13, f8, f9, f6 * fJ);
        }
        renderer2D.b().a(MatrixStackVar);
    }

    public boolean a(int i2, int i3) {
        if (this.v || this.u.j() < 0.5d) {
            return false;
        }
        boolean zF = f(i2, i3);
        if (!d(i2, i3)) {
            if (!zF) {
                if (!this.C.d()) {
                    return false;
                }
                this.C.g();
                return true;
            }
        }
        if (!this.r || this.G == null) {
            return this.C.a(this.w, this.x, 150.0f, a(), i2, i3);
        }
        float fA = a();
        float fMax = (float) Math.max(0.0d, Math.min(1.0d, this.u.j()));
        float f2 = 150.0f * fMax;
        float f3 = fA * fMax;
        float f4 = this.w + 75.0f;
        float f5 = this.x + (fA / 2.0f);
        float f6 = f4 - (f2 / 2.0f);
        float f7 = f5 - (f3 / 2.0f);
        return this.G.a(f6 + (3.0f * fMax), f7 + ((f3 - (20.0f * fMax)) / 2.0f), f2 - (6.0f * fMax), i2, i3);
    }

    public boolean b(int i2, int i3) {
        if (this.v || this.u.j() < 0.5d) {
            return false;
        }
        boolean zF = f(i2, i3);
        if (!d(i2, i3) && !zF) {
            if (!this.C.d()) {
                return false;
            }
            this.C.g();
            return true;
        }
        if (this.C.d() && !this.C.b(i2, i3)) {
            this.C.g();
            return true;
        }
        return this.C.b(this.w, this.x, 150.0f, a(), i2, i3);
    }

    public void c(int i2, int i3) {
        if (!this.r || this.G == null) {
            this.C.c(i2, i3);
        } else {
            this.G.a(i2, i3);
        }
    }

    public void a(int i2, int i3, double d2, double d3) {
        if (this.r) {
            return;
        }
        this.C.a(a(), i2, i3, d2, d3);
    }

    public void a(float f2, int i2, int i3) {
        if (this.r) {
            if (859982073 < i) {
                return;
            }
            return;
        }
        if (d(i2, i3)) {
            this.C.a(f2, a());
            return;
        }
    }

    public boolean a(int i2, int i3, int i4) {
        if (!this.r || this.G == null) {
            return this.C.a(i2, i3, i4);
        }
        boolean zA = this.G.a(i2, i3, i4);
        if (zA && this.m != null) {
            this.m.a(this.G.c());
        }
        return zA;
    }

    public boolean a(char c2, int i2) {
        if (this.r) {
            return false;
        }
        return this.C.a(c2, i2);
    }

    public boolean h() {
        return (this.v || this.u.j() < 0.5d) ? false : (this.r && this.G != null) ? this.G.a_() : this.C.l();
    }

    public boolean d(int i2, int i3) {
        float f2;
        if (this.v || this.u.j() < 0.5d) {
            return false;
        }
        if (this.y) {
            return GuiInput.a(this.w, this.x, 150.0f, a(), i2, i3);
        }
        IconTextureRegistry.TextureInfo arrowInfo = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
        float fB = arrowInfo != null ? arrowInfo.b() / 2.0f : 0.0f;
        float f3 = 150.0f + fB;
        if (this.p) {
            f2 = this.w;
        } else {
            f2 = this.w - fB;
        }
        return GuiInput.a(f2, this.x, f3, a(), i2, i3);
    }

    public boolean e(int i2, int i3) {
        return (this.v || this.u.j() < 0.5d) ? false : GuiInput.a(this.w, this.x, 150.0f, a(), i2, i3);
    }

    public boolean a(ModuleCard moduleCard) {
        if (this.p != moduleCard.p) {
            return false;
        }
        return Bool.from((this.x + a() <= moduleCard.x || this.x >= moduleCard.x + moduleCard.a()) ? 0 : 1);
    }

    public float i() {
        return !this.y ? this.x : this.A;
    }

    public float j() {
        return !this.y ? this.w : this.z;
    }

    public float k() {
        return a();
    }

    public GuiEntry l() {
        return this.m;
    }

    public int m() {
        return this.o;
    }

    public boolean n() {
        return this.p;
    }

    public VerticalAlign o() {
        return this.q;
    }

    public float p() {
        return this.s;
    }

    public SettingWidgetList q() {
        return this.C;
    }

    public boolean r() {
        return this.y;
    }

    public KeyBindSettingWidget s() {
        return this.G;
    }

    public int t() {
        if (this.G != null) {
            return this.G.c();
        }
        return -1;
    }

    public void a(int i2) {
        if (this.G != null) {
            this.G.a(i2);
        }
    }

    public boolean u() {
        return !this.r ? this.C.h() : false;
    }

    public void v() {
        if (this.r) {
            return;
        }
        this.C.i();
    }

    public float[] w() {
        if (this.r) {
            return null;
        }
        return this.C.j();
    }

    public boolean x() {
        return this.C.d();
    }

    public boolean f(int i2, int i3) {
        return !this.r ? this.C.a(i2, i3) : false;
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
