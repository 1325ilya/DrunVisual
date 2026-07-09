package drunvisual.gui.modules;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.module.ClientModule;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.KeySetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.Setting;
import drunvisual.settings.SliderSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.settings.BooleanSettingWidget;
import drunvisual.gui.settings.ColorSettingWidget;
import drunvisual.gui.settings.KeyBindSettingWidget;
import drunvisual.gui.settings.ModeSettingWidget;
import drunvisual.gui.settings.SettingWidget;
import drunvisual.gui.settings.SettingWidgetList;
import drunvisual.gui.settings.SliderSettingWidget;
import drunvisual.render.Renderer2D;
import drunvisual.theme.Theme;

public class ModuleSettingsPanel {
    public static final float a = 140.0f;
    public static final float b = 38.0f;
    public static final float c = 108.0f;
    public static final float d = 9.5f;
    public static final float e = 7.0f;
    private float l;
    private float m;
    private float n;
    private float o;
    private float p;
    private float q;
    private static final float s = 8.0f;
    private SliderSettingWidget u;
    public static int f;
    public static boolean g;
    private final AnimationState h = new AnimationState();
    private boolean i = false;
    private boolean j = false;
    private float k = 140.0f;
    private boolean r = true;
    private boolean v = false;
    private final SettingWidgetList t = new SettingWidgetList();

    public ModuleSettingsPanel() {
        this.t.b(7.0f);
        this.t.a(108.0f);
        this.u = new SliderSettingWidget("Масштаб", 1.0f, 1.0f, 2.0f, 0.1f);
        this.u.a(true);
        this.t.a(this.u);
        this.h.d(0.0d);
    }

    public void a(ClientModule clientModule) {
        SettingWidget settingWidgetA;
        if (this.v || clientModule == null) {
            return;
        }
        this.t.a();
        this.u = null;
        for (Setting<?> setting : clientModule.m()) {
            if (setting.m() && (settingWidgetA = a(setting)) != null) {
                this.t.a(settingWidgetA);
                if (this.u == null && (settingWidgetA instanceof SliderSettingWidget) && setting.f().equals("Масштаб")) {
                    this.u = (SliderSettingWidget) settingWidgetA;
                    this.u.a(true);
                }
            }
        }
        if (this.u == null) {
            this.u = new SliderSettingWidget("Масштаб", 1.0f, 1.0f, 2.0f, 0.1f);
            this.u.a(true);
            this.t.a(0, this.u);
        }
        this.v = true;
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
        if (setting instanceof ColorSetting) {
            return new ColorSettingWidget((ColorSetting) setting);
        }
        if (setting instanceof KeySetting) {
            return new KeyBindSettingWidget((KeySetting) setting);
        }
        return null;
    }

    @Deprecated
    public void a(SliderSetting sliderSetting) {
        if (this.v) {
            return;
        }
        this.t.b(this.u);
        this.u = new SliderSettingWidget(sliderSetting);
        this.u.a(true);
        this.t.a(0, this.u);
    }

    public void a(float f2, float f3, float f4, float f5) {
        if (this.i && !this.j) {
            a();
            return;
        }
        this.n = f2;
        this.o = f3;
        this.p = f4;
        this.q = f5;
        float panelH = h();
        this.r = Bool.from(f3 - 8.0f < panelH ? 0 : 1);
        this.l = Math.round((f2 + (f4 / 2.0f)) - (this.k / 2.0f));
        if (this.r) {
            this.m = Math.round((f3 - panelH) - 8.0f);
        } else {
            this.m = Math.round(f3 + f5 + 8.0f);
        }
        this.i = true;
        this.j = false;
        this.h.a(1.0d, 0.15d, Easing.F);
    }

    public void a() {
        if (this.i) {
            this.j = true;
            this.t.g();
            this.h.a(0.0d, 0.09d, Easing.g);
        }
    }

    public void b() {
        this.i = false;
        this.j = false;
        this.h.d(0.0d);
        this.t.g();
    }

    public boolean c() {
        return this.i;
    }

    public boolean d() {
        return this.j;
    }

    public boolean e() {
        return (!this.i || this.j || this.h.j() <= 0.5d) ? false : true;
    }

    public boolean f() {
        return (this.j && this.h.d() && this.h.j() < 0.01d) ? true : false;
    }

    public void g() {
        this.h.a();
        if (f()) {
            this.i = false;
            this.j = false;
        }
    }

    public float h() {
        return Math.max(38.0f, Math.min(this.t.m(), 108.0f));
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i, int i2) {
        float f2;
        float f3;
        if (this.i) {
            g();
            float fJ = (float) this.h.j();
            if (fJ >= 0.01f) {
                float fH = h();
                this.l = Math.round((this.n + (this.p / 2.0f)) - (this.k / 2.0f));
                if (this.r) {
                    this.m = Math.round((this.o - fH) - 8.0f);
                } else {
                    this.m = Math.round(this.o + this.q + 8.0f);
                }
                float fMax = Math.max(0.0f, Math.min(1.0f, fJ));
                int i3 = (int) (255.0f * fMax);
                float f4 = this.l + (this.k / 2.0f);
                float f5 = this.m + (fH / 2.0f);
                float f6 = this.k * fMax;
                float f7 = fH * fMax;
                if (f6 < 1.0f || f7 < 1.0f) {
                    return;
                }
                float f8 = f4 - (f6 / 2.0f);
                float f9 = f5 - (f7 / 2.0f);
                Color colorA = Theme.a(Theme.q, i3);
                Color colorA2 = Theme.a(Theme.r, i3);
                renderer2D.a(f8 - (0.5f * fMax), f9 - (0.5f * fMax), f6 + fMax, f7 + fMax, 9.5f * fMax, colorA, colorA, colorA2, colorA2, MatrixStackVar);
                Color colorA3 = Theme.a(Theme.e, i3);
                Color colorA4 = Theme.a(Theme.f, i3);
                renderer2D.a(f8, f9, f6, f7, 9.5f * fMax, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
                IconTextureRegistry.TextureInfo info = IconTextureRegistry.getInfo(IconTextureRegistry.ARROW_V);
                if (info != null) {
                    Identifier IdentifierVarA = info.a();
                    RenderSystem.setShaderTexture(0, IdentifierVarA);
                    float fB = (info.b() / 2.0f) * fMax;
                    float fC = (info.c() / 2.0f) * fMax;
                    float f10 = f8 + (f6 / 2.0f);
                    if (this.r) {
                        f2 = f9 + f7 + (fB / 2.0f) + 1.0f;
                        f3 = 90.0f;
                    } else {
                        f2 = (f9 - (fB / 2.0f)) - 1.0f;
                        f3 = -90.0f;
                    }
                    Color colorA5 = Theme.a(Theme.aa, i3);
                    MatrixStackVar.push();
                    MatrixStackVar.translate(f10, f2, 0.0f);
                    MatrixStackVar.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(f3));
                    MatrixStackVar.translate(-f10, -f2, 0.0f);
                    renderer2D.a(IdentifierVarA, (f10 - (fB / 2.0f)) - 0.5f, f2 - (fC / 2.0f), fB, fC, colorA5, MatrixStackVar);
                    MatrixStackVar.pop();
                }
                if (fMax <= 0.1f) {
                    return;
                }
                float f11 = f9 + (7.0f * fMax);
                float f12 = (f7 - (7.0f * fMax)) - (6.0f * fMax);
                if (f12 < 1.0f || f6 < 1.0f) {
                    return;
                }
                renderer2D.b().a(f8, f11, f6, f12, 9.5f * fMax, MatrixStackVar);
                this.t.a(MatrixStackVar, renderer2D, f8, f9, f6, f7, !this.j ? i : -1, !this.j ? i2 : -1, fMax, fMax);
                renderer2D.b().a(MatrixStackVar);
            }
        }
    }

    public boolean a(int i, int i2) {
        return (!this.i || this.j || this.h.j() < 0.5d) ? false : c(i, i2) ? this.t.a(this.l, this.m, this.k, h(), i, i2) : false;
    }

    public void b(int i, int i2) {
        if (this.i) {
            this.t.c(i, i2);
        }
    }

    public void a(int i, int i2, double d2, double d3) {
        if (!this.i || this.j) {
            return;
        }
        this.t.a(h(), i, i2, d2, d3);
    }

    public boolean a(float f2, int i, int i2) {
        if (!this.i || this.j) {
            return false;
        }
        if (c(i, i2)) {
            this.t.a(f2, h());
            return true;
        }
        return false;
    }

    public boolean c(int i, int i2) {
        return (!this.i || this.h.j() < 0.5d) ? false : GuiInput.a(this.l, this.m, this.k, h(), i, i2);
    }

    public void b(float f2, float f3, float f4, float f5) {
        this.n = f2;
        this.o = f3;
        this.p = f4;
        this.q = f5;
    }

    public float i() {
        return this.u.e();
    }

    @Deprecated
    public void a(float f2) {
        this.u.a(Math.max(1.0f, Math.min(2.0f, f2)));
    }

    public void a(SettingWidget settingWidget) {
        this.t.a(settingWidget);
    }

    public SettingWidgetList j() {
        return this.t;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
