package drunvisual.gui.modules;

import java.util.List;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.core.GuiLayerRegistry;
import drunvisual.client.MinecraftContext;
import drunvisual.module.ClientModule;
import drunvisual.gui.core.ClickGuiOverlay;
import drunvisual.gui.core.GuiEntry;
import drunvisual.gui.core.GuiEntrySource;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.gui.widgets.ScrollBar;
import drunvisual.render.Renderer2D;

public class ModuleGrid implements ClickGuiOverlay {
    private final GuiEntrySource a;
    private static final float b = 19.0f;
    private static final float c = 48.0f;
    private static final float d = 26.0f;
    private static final float e = 6.5f;
    private static final float f = 8.0f;
    private static final float g = 2.0f;
    private float k;
    private float l;
    private float m;
    private float n;
    private int o;
    private int p;
    private boolean q = false;
    private final ScrollBar h = new ScrollBar(6.0f, 25.0f);
    private final ModuleCardList i = new ModuleCardList();
    private final ModuleRowRenderer j = new ModuleRowRenderer();

    public ModuleGrid(GuiEntrySource guiEntrySource) {
        this.a = guiEntrySource;
    }

    public List<ModuleCard> a() {
        return this.i.a();
    }

    private float k() {
        int iF = this.a.f();
        float fD = DrunVisualClickGuiScreen.d() - 38.0f;
        return iF == 1 ? fD - g : ((fD - e) / g) - 1.0f;
    }

    private float c(int i, int i2) {
        return (((int) Math.ceil(((double) i) / ((double) i2))) * 34.0f) - 8.0f;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i, int i2) {
        this.k = f2;
        this.l = f3;
        this.o = i;
        this.p = i2;
        this.h.a();
        List<? extends GuiEntry> listE = this.a.e();
        if (listE == null || listE.isEmpty()) {
            return;
        }
        int iF = this.a.f();
        float fK = k();
        float f4 = f3 + c;
        float fE = (DrunVisualClickGuiScreen.e() - c) - b;
        float fCeil = (((int) Math.ceil(((double) listE.size()) / ((double) iF))) * 34.0f) - 8.0f;
        if (fCeil < 0.0f) {
            fCeil = 0.0f;
        }
        float fMax = Math.max(0.0f, fCeil - fE);
        float fB = this.h.b();
        if (fB > fMax) {
            this.h.e(fMax);
            fB = fMax;
        }
        float f5 = f4 - fB;
        float f6 = f2 + b;
        float f7 = f4 + fE;
        GuiLayerRegistry.a().a(GuiLayerRegistry.Layer.CONTENT, f2, f4, DrunVisualClickGuiScreen.d(), fE);
        renderer2D.b().a(f2, f4, DrunVisualClickGuiScreen.d(), fE, MatrixStackVar);
        for (int i3 = 0; i3 < listE.size(); i3++) {
            GuiEntry guiEntry = listE.get(i3);
            int i4 = i3 % iF;
            float f8 = f6 + (i4 * (fK + e)) + 1.0f;
            float f9 = f5 + ((i3 / iF) * 34.0f) + 1.0f;
            if (f9 + d >= f4 && f9 <= f7) {
                this.j.a(MatrixStackVar, renderer2D, guiEntry, f8, f9, fK, d, i, i2, false, i4 != 0, false, false);
            }
        }
        renderer2D.b().a(MatrixStackVar);
        if (fCeil > fE + 1.0f) {
            this.h.a(MatrixStackVar, renderer2D, (f2 + DrunVisualClickGuiScreen.d()) - 6.0f, f4, fE, fCeil, fE, i, i2, false);
        }
        this.i.b(MatrixStackVar, renderer2D, this.k, this.l, i, i2, this.m, this.n);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i, int i2) {
        this.i.b(MatrixStackVar, renderer2D, this.k, this.l, i, i2, this.m, this.n);
    }

    public void b() {
        this.i.b();
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D) {}

    @Override
    public void a(float f2, float f3, int i, int i2) {
        this.b(f2, f3, i, i2);
    }

    @Override
    public void b(float f2, float f3, int i, int i2) {
        int clickBtn = 0;
        try {
            Object currentScreen = net.minecraft.client.MinecraftClient.getInstance().currentScreen;
            if (currentScreen instanceof drunvisual.gui.core.DrunVisualClickGuiScreen) {
                clickBtn = ((drunvisual.gui.core.DrunVisualClickGuiScreen)currentScreen).clickBtn;
            }
        } catch (Throwable th) {}
        boolean isRightClick = clickBtn == 1;
        boolean hasOpenPanel = this.i.hasOpenPanelAt(i, i2);
        if (hasOpenPanel) {
            if (isRightClick) {
                this.i.f();
            } else {
                this.i.c(i, i2);
            }
            return;
        }
        float f4 = f3 + c;
        float fE = DrunVisualClickGuiScreen.e() - c - 5.0f;
        if (i2 >= f4 && i2 <= f4 + fE) {
            List<? extends GuiEntry> listE = this.a.e();
            if (listE != null && !listE.isEmpty()) {
                int iF = this.a.f();
                if (iF <= 0) iF = 2;
                float fK = k();
                float f6 = f2 + 19.0f;
                float f5 = f4 - this.h.b();
                for (int i3 = 0; i3 < listE.size(); i3++) {
                    GuiEntry guiEntry = listE.get(i3);
                    int i4 = i3 % iF;
                    int i5 = i3 / iF;
                    float f8 = f6 + i4 * (fK + 6.5f) + 1.0f;
                    float f9 = f5 + i5 * 34.0f + 1.0f;
                    if (GuiInput.a(f8, f9, fK, d, i, i2)) {
                        float f10 = f8 + fK - ModuleRowRenderer.b() - ModuleRowRenderer.a();
                        float f11 = f9 + 13.0f - ModuleRowRenderer.b() / 2.0f;
                        float f12 = f10 - ModuleRowRenderer.d() - ModuleRowRenderer.c();
                        float f13 = f9 + 13.0f - ModuleRowRenderer.c() / 2.0f;
                        if (guiEntry.d() && guiEntry.g() && GuiInput.a(f12, f13, ModuleRowRenderer.c(), ModuleRowRenderer.c(), i, i2)) {
                            if (!isRightClick) {
                                this.i.a(guiEntry, i3, i4 != 0, f8, f9, fK, d, f12, f13);
                            }
                            return;
                        }
                        if (!isRightClick) {
                            guiEntry.a(!guiEntry.b());
                            return;
                        }
                        if (guiEntry.d() && !guiEntry.g()) {
                            this.i.a(guiEntry, i3, i4 != 0, f8, f9, fK, d, i, i2);
                        } else if (guiEntry.e() != null && !guiEntry.e().m().isEmpty()) {
                            this.i.a(guiEntry, i3, i4 != 0, f9, d);
                        }
                        return;
                    }
                }
            }
        }
    }

    public void c(float f2, float f3, int i, int i2) {
        this.h.d();
        this.i.e(i, i2);
    }

    public void a(float f2, float f3, int i, int i2, double d2, double d3) {
        this.i.a(i, i2, d2, d3);
        if (this.h.c()) {
            List<? extends GuiEntry> listE = this.a.e();
            int iF = this.a.f();
            float fCeil = this.c(listE.size(), iF);
            float fE = DrunVisualClickGuiScreen.e() - 48.0f - 18.0f;
            this.h.a(i2, fCeil, fE);
        }
    }

    public void a(float f2) {
        try {
            if (this.i.hasOpenPanelAt(this.o, this.p) && this.i.a(f2, this.o, this.p)) {
                return;
            }
        } catch (Throwable th) {}
        try {
            if (this.h.c()) {
                return;
            }
        } catch (Throwable th) {}
        List<? extends GuiEntry> listE = this.a.e();
        if (listE != null && !listE.isEmpty()) {
            int iF = this.a.f();
            if (iF <= 0) iF = 2;
            float fCeil = this.c(listE.size(), iF);
            float fE = DrunVisualClickGuiScreen.e() - 48.0f - 19.0f;
            this.h.a(f2, fCeil, fE);
        }
    }

    public void a(float f2, int i, int i2) {
        this.o = i;
        this.p = i2;
        this.a(f2);
    }

    public boolean a(int i, int i2, int i3) {
        return this.i.a(i, i2, i3);
    }

    public boolean a(char c2, int i) {
        return this.i.a(c2, i);
    }

    public boolean c() {
        return this.i.l();
    }

    public boolean p() {
        return this.i.l();
    }

    public void d() {
        this.h.e();
        this.i.i();
    }

    public void e() {
        this.i.i();
    }

    public void f() {
        this.i.j();
    }

    public void g() {
        this.i.g();
    }

    public void h() {
        this.i.h();
    }

    public void i() {
        this.i.k();
    }

    public boolean j() {
        return this.i.c();
    }

    public boolean a(int i, int i2) {
        return this.i.b(i, i2);
    }

    public ModuleCard findCardByBounds(int i, int i2) {
        return this.i.a(i, i2);
    }

    public boolean hasOpenSettingsAt(int i, int i2) {
        return this.i.hasOpenPanelAt(i, i2);
    }

    public void a(ClientModule clientModule) {
        this.i.a(clientModule);
    }
}
