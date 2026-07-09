package drunvisual.gui.settings;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.widgets.ColorPickerPopup;
import drunvisual.settings.ItemToggleSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;
import drunvisual.util.MarqueeText;

public class ItemToggleSettingWidget implements SettingWidget {
    public static final float a = 20.0f;
    private static final float d = 8.0f;
    private static final float e = 18.0f;
    private static final float f = 10.0f;
    private static final float g = 5.0f;
    private static final float h = 14.0f;
    private static final float i = 6.0f;
    private static final float j = 11.0f;
    private static final float k = 4.0f;
    private final String l;
    private final Item m;
    private boolean n;
    private Color o;
    private final Color p;
    private final ItemToggleSetting q;
    private final AnimationState r;
    private final AnimationState s;
    private final MarqueeText t;
    private boolean u;
    private boolean v;
    private float w;
    private float x;
    private float y;
    private boolean z;
    public static int b;
    public static boolean c;

    public ItemToggleSettingWidget(ItemToggleSetting itemToggleSetting) {
        this.r = new AnimationState();
        this.s = new AnimationState();
        this.t = new MarqueeText();
        this.u = false;
        this.v = false;
        this.y = 1.0f;
        this.z = false;
        this.q = itemToggleSetting;
        this.l = itemToggleSetting.f();
        this.m = itemToggleSetting.c();
        this.n = itemToggleSetting.a();
        this.o = itemToggleSetting.d();
        this.p = itemToggleSetting.d();
        this.v = this.n;
        this.r.d(!this.n ? 0.0d : 1.0d);
    }

    public ItemToggleSettingWidget(String str, Item ItemVar, boolean z, Color color) {
        this.r = new AnimationState();
        this.s = new AnimationState();
        this.t = new MarqueeText();
        this.u = false;
        this.v = false;
        this.y = 1.0f;
        this.z = false;
        this.q = null;
        this.l = str;
        this.m = ItemVar;
        this.n = z;
        this.o = color;
        this.p = color;
        this.v = z;
        this.r.d(!z ? 0.0d : 1.0d);
    }

    @Override
    public String a() {
        return this.l;
    }

    @Override
    public float b() {
        return 20.0f;
    }

    public Item c() {
        return this.m;
    }

    public boolean e() {
        return this.n;
    }

    public void a(boolean z) {
        this.n = z;
        if (this.q != null) {
            this.q.a(z);
        }
    }

    public void f() {
        this.n = Bool.from(this.n ? 0 : 1);
        if (this.q != null) {
            this.q.a(this.n);
        }
    }

    public Color g() {
        return this.o;
    }

    public void a(Color color) {
        this.o = color;
        if (this.q != null) {
            this.q.a(color);
        }
    }

    public Color h() {
        return this.p;
    }

    public float i() {
        return Color.RGBtoHSB(this.o.getRed(), this.o.getGreen(), this.o.getBlue(), (float[]) null)[0];
    }

    public float m() {
        return Color.RGBtoHSB(this.o.getRed(), this.o.getGreen(), this.o.getBlue(), (float[]) null)[1];
    }

    public float n() {
        return Color.RGBtoHSB(this.o.getRed(), this.o.getGreen(), this.o.getBlue(), (float[]) null)[2];
    }

    public void a(float f2, float f3, float f4) {
        this.o = Color.getHSBColor(f2, f3, f4);
    }

    public void o() {
        this.o = this.p;
    }

    public boolean p() {
        if (this.z) {
            if (ColorPickerPopup.a().c()) {
                return true;
            }
        }
        return false;
    }

    public boolean q() {
        if (this.z) {
            if (ColorPickerPopup.a().c()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5, float f6) {
        FontRenderer fontRenderer = FontManager.a[14];
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        float f7 = 20.0f * f6;
        float f8 = 8.0f * f6;
        this.y = f6;
        boolean zA = GuiInput.a(f2, f3, f4, f7, i2, i3);
        this.t.a(zA);
        if (zA != this.u) {
            this.s.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.u = zA;
        }
        if (this.n != this.v) {
            this.r.a(!this.n ? 0.0d : 1.0d, 0.2d, Easing.h);
            this.v = this.n;
        }
        this.s.a();
        this.r.a();
        float fJ = (float) this.r.j();
        int i4 = (int) (255.0f * f5);
        float f9 = 16.0f * f6;
        float f10 = (f2 + f8) - 3.0f;
        float f11 = (f3 + (f7 / 2.0f)) - (f9 / 2.0f);
        if (f5 > 0.01f) {
            a(MatrixStackVar, new ItemStack(this.m), f10, f11, f9, f5, MinecraftClientVarGetInstance);
        }
        float f12 = h * f6;
        float f13 = ((f2 + f4) - f8) - f12;
        float f14 = (f3 + (f7 / 2.0f)) - (f12 / 2.0f);
        float f15 = 18.0f * f6;
        float f16 = f * f6;
        float f17 = (f13 - f15) - (i * f6);
        float f18 = (f3 + (f7 / 2.0f)) - (f16 / 2.0f);
        float f19 = ((f2 + f8) + f9) - (1.0f * f6);
        this.t.a(MatrixStackVar, renderer2D, fontRenderer, this.l, f19, (f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.l) * f6) / k), (f17 - f19) - (k * f6), f6, Theme.a, f5);
        this.w = f13;
        this.x = f14;
        a(MatrixStackVar, renderer2D, f17, f18, fJ, f5, f6);
        renderer2D.a(f13, f14, f12, f12, i * f6, Theme.a(Theme.V, i4), MatrixStackVar);
        float f20 = j * f6;
        renderer2D.a(f13 + ((f12 - f20) / 2.0f), f14 + ((f12 - f20) / 2.0f), f20, f20, k * f6, Theme.a(this.o, i4), MatrixStackVar);
        boolean zA2 = GuiInput.a(f17, f18, f15, f16, i2, i3);
        boolean zA3 = GuiInput.a(f13, f14, f12, f12, i2, i3);
        if (zA2 || zA3) {
            GuiInput.g();
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, float f6) {
        int i2 = (int) (255.0f * f5);
        float f7 = 18.0f * f6;
        float f8 = f * f6;
        float f9 = g * f6;
        float f10 = 9.0f * f6;
        float f11 = f2 + (0.5f * f6) + (((f7 - f10) - f6) * f4);
        float f12 = f3 + (0.5f * f6);
        if (f4 > 0.01f) {
            renderer2D.a(f2 + f6, f3 - (3.0f * f6), 8.0f * f6, Theme.a(Theme.E, (int) (255.0f * f4 * 0.5f * f5)), MatrixStackVar);
        }
        Color colorA = ColorUtils.a(Theme.F, Theme.C, f4);
        Color colorA2 = ColorUtils.a(Theme.G, Theme.D, f4);
        Color colorA3 = Theme.a(colorA, i2);
        Color colorA4 = Theme.a(colorA2, i2);
        renderer2D.a(f2 - (0.5f * f6), f3 - (0.5f * f6), f7 + f6, f8 + f6, f9, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        Color colorA5 = ColorUtils.a(Theme.f, Theme.y, f4);
        Color colorA6 = ColorUtils.a(Theme.H, Theme.z, f4);
        Color colorA7 = Theme.a(colorA5, i2);
        Color color = new Color(colorA6.getRed(), colorA6.getGreen(), colorA6.getBlue(), Math.min(255, (int) (colorA6.getAlpha() + ((255 - colorA6.getAlpha()) * f4 * f5))));
        renderer2D.a(f2, f3, f7, f8, f9, colorA7, colorA7, color, color, MatrixStackVar);
        renderer2D.a(f11, f12, f10, f10, f10, Theme.a(ColorUtils.a(Theme.b, Theme.a, f4), i2), MatrixStackVar);
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        float f5 = ((f2 + f4) - 8.0f) - h;
        float f6 = (f3 + f) - (h / 2.0f);
        if (GuiInput.a((f5 - 18.0f) - i, (f3 + f) - (f / 2.0f), 18.0f, f, i2, i3)) {
            f();
            return true;
        }
        if (!GuiInput.a(f5, f6, h, h, i2, i3)) {
            return false;
        }
        if (p()) {
            r();
        } else {
            u();
        }
        return true;
    }

    private void u() {
        float f2 = h * this.y;
        int i2 = (int) (this.w + (f2 / 2.0f));
        int i3 = (int) (this.x + f2 + (g * this.y));
        this.z = true;
        ColorPickerPopup.a().a(i2, i3, ColorPickerPopup.Edge.BOTTOM, this.l, this.o, this.y, this::b);
    }

    public void r() {
        this.z = false;
        ColorPickerPopup.a().b();
    }

    private void b(Color color) {
        a(color);
    }

    @Override
    public void a(int i2, int i3) {
    }

    @Override
    public void a(int i2, int i3, double d2, double d3) {
    }

    @Override
    public boolean l() {
        return Bool.from((this.z && ColorPickerPopup.a().c()) ? 1 : 0);
    }

    public void s() {
        if (this.z) {
            r();
        }
    }

    public boolean b(int i2, int i3) {
        return this.z ? ColorPickerPopup.a().c(i2, i3) : false;
    }

    public float[] t() {
        if (this.z) {
            return ColorPickerPopup.a().f();
        }
        return null;
    }

    private void a(MatrixStack MatrixStackVar, ItemStack ItemStackVar, float f2, float f3, float f4, float f5, MinecraftClient MinecraftClientVar) {
        if (MinecraftClientVar.player == null || ItemStackVar.isEmpty() || f5 < 0.01f) {
            return;
        }
        DrawContext DrawContextVar = new DrawContext(MinecraftClientVar, MinecraftClientVar.getBufferBuilders().getEntityVertexConsumers());
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        float f6 = f4 / 16.0f;
        MatrixStackVarGetMatrices.push();
        MatrixStackVarGetMatrices.translate(f2, f3, 0.0f);
        MatrixStackVarGetMatrices.scale(f6, f6, 1.0f);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, f5);
        DrawContextVar.drawItem(ItemStackVar, 0, 0);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        MatrixStackVarGetMatrices.pop();
    }

    @Override
    public boolean d() {
        return Bool.from((this.q == null || this.q.m()) ? 1 : 0);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
