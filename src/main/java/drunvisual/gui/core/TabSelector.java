package drunvisual.gui.core;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.core.GuiLayerRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.core.Bool;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class TabSelector implements ClickGuiOverlay {
    private final TabHost c;
    private static final float d = 19.0f;
    private static final float e = 35.5f;
    private static final float f = 6.0f;
    private static final Color g = Theme.N;
    private final Map<Integer, AnimationState> h = new HashMap();
    private final Map<Integer, AnimationState> i = new HashMap();
    private final Map<Integer, Boolean> j = new HashMap();
    private final Map<Integer, Boolean> k = new HashMap();
    private final Map<Integer, Float> l = new HashMap();
    private final Map<Integer, Float> m = new HashMap();
    private String[] n;
    public static int a;
    public static boolean b;

    public TabSelector(TabHost tabHost) {
        this.c = tabHost;
        a();
    }

    private void a() {
        int i;
        String[] strArrC = this.c.c();
        this.n = strArrC;
        int iD = this.c.d();
        FontRenderer fontRenderer = FontManager.b[14];
        for (int i2 = 0; i2 < strArrC.length; i2++) {
            this.h.put(Integer.valueOf(i2), new AnimationState());
            this.i.put(Integer.valueOf(i2), new AnimationState());
            this.j.put(Integer.valueOf(i2), Boolean.valueOf(false));
            if (i2 != iD) {
                i = 0;
            } else {
                i = 1;
            }
            int i3 = i;
            this.k.put(Integer.valueOf(i2), Boolean.valueOf(Bool.from(i3)));
            this.h.get(Integer.valueOf(i2)).d(i3 == 0 ? 0.0d : 1.0d);
            this.l.put(Integer.valueOf(i2), Float.valueOf(fontRenderer.a(strArrC[i2])));
        }
    }

    private void b() {
        String[] strArrC = this.c.c();
        if (this.n == null || strArrC.length != this.n.length) {
            a();
            return;
        }
        for (int i = 0; i < strArrC.length; i++) {
            if (!strArrC[i].equals(this.n[i])) {
                a();
                return;
            }
        }
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i, int i2) {
        b();
        float fD = DrunVisualClickGuiScreen.d();
        FontRenderer fontRenderer = FontManager.b[14];
        String[] strArrC = this.c.c();
        int iD = this.c.d();
        if (strArrC.length != 0) {
            float fA = fontRenderer.a("/");
            float fFloatValue = f2 + d;
            float f4 = f3 + d;
            float fFloatValue2 = 0.0f;
            for (int i3 = 0; i3 < strArrC.length; i3++) {
                fFloatValue2 += this.l.get(Integer.valueOf(i3)).floatValue();
                int length = strArrC.length;
                if (i3 < (2 * (length & (-2))) - (length ^ 1)) {
                    fFloatValue2 += 12.0f + fA;
                }
            }
            GuiLayerRegistry.a().a(GuiLayerRegistry.Layer.TABS, fFloatValue, f4, fFloatValue2, 7.0f);
            boolean z = !GuiInput.a(GuiLayerRegistry.Layer.TABS, (double) i, (double) i2) || GuiInteractionState.a().b();
            boolean z2 = false;
            int i4 = 0;
            while (i4 < strArrC.length) {
                this.m.put(Integer.valueOf(i4), Float.valueOf(fFloatValue));
                int i5 = i4 != iD ? 0 : 1;
                int i6 = (z || !GuiInput.a(fFloatValue, f4, this.l.get(Integer.valueOf(i4)).floatValue(), 7.0f, (double) i, (double) i2)) ? 0 : 1;
                if (i6 != 0 && strArrC.length > 1 && i5 == 0) {
                    z2 = true;
                }
                if (z && this.j.get(Integer.valueOf(i4)).booleanValue()) {
                    this.i.get(Integer.valueOf(i4)).a(0.0d, 0.15d, Easing.h);
                    this.j.put(Integer.valueOf(i4), Boolean.valueOf(false));
                } else if (!z && Bool.from(i6) != this.j.get(Integer.valueOf(i4)).booleanValue()) {
                    this.i.get(Integer.valueOf(i4)).a(i6 == 0 ? 0.0d : 1.0d, 0.15d, Easing.h);
                    this.j.put(Integer.valueOf(i4), Boolean.valueOf(Bool.from(i6)));
                }
                if (Bool.from(i5) != this.k.get(Integer.valueOf(i4)).booleanValue()) {
                    this.h.get(Integer.valueOf(i4)).a(i5 == 0 ? 0.0d : 1.0d, 0.2d, Easing.h);
                    this.k.put(Integer.valueOf(i4), Boolean.valueOf(Bool.from(i5)));
                }
                this.h.get(Integer.valueOf(i4)).a();
                this.i.get(Integer.valueOf(i4)).a();
                float fJ = (float) this.h.get(Integer.valueOf(i4)).j();
                fontRenderer.a(strArrC[i4], fFloatValue, f4, strArrC.length != 1 ? ColorUtils.a(ColorUtils.a(Theme.b, g, ((float) this.i.get(Integer.valueOf(i4)).j()) * (1.0f - fJ)), Theme.aa, fJ) : Theme.aa, MatrixStackVar);
                fFloatValue += this.l.get(Integer.valueOf(i4)).floatValue();
                int length2 = strArrC.length;
                if (i4 < (2 * (length2 & (-2))) - (length2 ^ 1)) {
                    float f5 = fFloatValue + f;
                    fontRenderer.a("/", f5, f4, Theme.b, MatrixStackVar);
                    fFloatValue = f5 + fA + f;
                }
                i4++;
            }
            if (z2) {
                if (z) {
                } else {
                    GuiInput.g();
                }
            }
            renderer2D.a(((int) f2) + d, (int) (f3 + e), fD - 38.0f, 0.5f, Theme.a(Theme.aa, 25), MatrixStackVar);
        }
    }

    @Override
    public void a(float f2, float f3, int i, int i2) {
        if (!GuiInput.a(GuiLayerRegistry.Layer.TABS, i, i2) || GuiInteractionState.a().b()) {
            return;
        }
        String[] strArrC = this.c.c();
        if (strArrC.length > 1) {
            float f4 = f3 + d;
            for (int i3 = 0; i3 < strArrC.length; i3++) {
                Float f5 = this.m.get(Integer.valueOf(i3));
                Float f6 = this.l.get(Integer.valueOf(i3));
                if (f5 != null && f6 != null) {
                    if (GuiInput.a(f5.floatValue(), f4, f6.floatValue(), 7.0f, i, i2)) {
                        this.c.a(i3);
                        return;
                    } else if (b) {
                    }
                }
            }
        }
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
