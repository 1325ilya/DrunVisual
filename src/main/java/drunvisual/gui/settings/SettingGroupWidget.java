package drunvisual.gui.settings;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.settings.SettingGroup;
import drunvisual.core.Bool;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;

public class SettingGroupWidget implements SettingWidget {
    public static final float a = 18.0f;
    private static final float d = 8.0f;
    private static final float e = 6.0f;
    private static final float f = 1.0f;
    private final String g;
    private final SettingGroup h;
    public static int b;

    public SettingGroupWidget(SettingGroup settingGroup) {
        this.h = settingGroup;
        this.g = settingGroup.f();
    }

    public SettingGroupWidget(String str) {
        this.h = null;
        this.g = str;
    }

    @Override
    public String a() {
        return this.g;
    }

    @Override
    public float b() {
        return 18.0f;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i, int i2, float f5, float f6) {
        FontRenderer fontRenderer = FontManager.a[16];
        float f7 = 18.0f * f6;
        float f8 = 8.0f * f6;
        float f9 = e * f6;
        float f10 = f * f6;
        int i3 = (int) (255.0f * f5);
        float fA = fontRenderer.a(this.g) * f6;
        float f11 = (f2 + (f4 / 2.0f)) - (fA / 2.0f);
        float fB = (f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.g) * f6) / 4.0f);
        Color colorA = Theme.a(Theme.b, i3 / 1.5f);
        MatrixStackVar.push();
        MatrixStackVar.translate(f11, fB, 0.0f);
        MatrixStackVar.scale(f6, f6, f);
        MatrixStackVar.translate(-f11, -fB, 0.0f);
        fontRenderer.a(this.g, f11, fB, colorA, MatrixStackVar);
        MatrixStackVar.pop();
        float f12 = (f3 + (f7 / 2.0f)) - (f10 / 2.0f);
        Color colorA2 = Theme.a(new Color(20, 20, 28), i3);
        float f13 = f2 + f8;
        float f14 = f11 - f9;
        if (f14 > f13) {
            renderer2D.a(f13, f12, f14 - f13, f10, colorA2, MatrixStackVar);
        }
        float f15 = f11 + fA + f9;
        float f16 = (f2 + f4) - f8;
        if (f16 > f15) {
            renderer2D.a(f15, f12, f16 - f15, f10, colorA2, MatrixStackVar);
        }
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i, int i2) {
        return false;
    }

    @Override
    public boolean d() {
        return Bool.from((this.h == null || this.h.m()) ? 1 : 0);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
