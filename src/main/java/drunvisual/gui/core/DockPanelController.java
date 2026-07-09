package drunvisual.gui.core;

import java.awt.Color;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.audio.SoundPlayer;
import drunvisual.render.Renderer2D;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.theme.Theme;

public class DockPanelController implements ClickGuiOverlay {
    private static final float CORNER = 9.5f;
    private static final float ICON_S = 16.0f;
    private static final float CELL_S = 24.0f;
    private static final float PAD = 8.0f;
    private static final float GAP = 8.0f;
    private final DrunVisualClickGuiScreen screen;
    private boolean open;
    private boolean dragging;
    private final AnimationState[] hoverAnims;
    private static final float PH = 40.0f;
    private Dock dock = Dock.BOTTOM;
    private int hoveredTab = -1;

    public enum Dock {
        BOTTOM,
        LEFT,
        RIGHT
    }

    DockPanelController(DrunVisualClickGuiScreen pulseClickGuiScreen) {
        this.screen = pulseClickGuiScreen;
        ClickGuiTabType[] clickGuiTabTypeArrValues = ClickGuiTabType.values();
        this.hoverAnims = new AnimationState[clickGuiTabTypeArrValues.length];
        for (int i = 0; i < clickGuiTabTypeArrValues.length; i++) {
            this.hoverAnims[i] = new AnimationState();
        }
    }

    public Dock a() {
        return this.dock;
    }

    public void a(Dock dock) {
        if (dock != null) {
            this.dock = dock;
        }
    }

    public boolean b() {
        return this.open;
    }

    public boolean c() {
        return this.dragging;
    }

    public boolean d() {
        return this.open || this.dragging;
    }

    public void e() {
        this.open = false;
        this.dragging = false;
    }

    public void b(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        a(MatrixStackVar, renderer2D, f, f2, i, i2);
    }

    private int tabCount() {
        return ClickGuiTabType.values().length;
    }

    private float panelW() {
        return 16.0f + (CELL_S * tabCount()) + (8.0f * (tabCount() - 1));
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, int i, int i2) {
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        float fPanelW = panelW();
        float f3 = f + ((fD - fPanelW) / 2.0f);
        float f4 = f2 + fE + 5.0f;
        renderer2D.a(f3 - 0.5f, f4 - 0.5f, fPanelW + 1.0f, 41.0f, 9.5f, Theme.w, Theme.w, Theme.w, Theme.w, MatrixStackVar);
        renderer2D.a(f3, f4, fPanelW, PH, 9.5f, Theme.e, Theme.e, Theme.f, Theme.f, MatrixStackVar);
        ClickGuiTabType[] clickGuiTabTypeArrValues = ClickGuiTabType.values();
        ClickGuiTabType clickGuiTabTypeK = this.screen.k();
        this.hoveredTab = -1;
        for (int i3 = 0; i3 < clickGuiTabTypeArrValues.length; i3++) {
            float f5 = f3 + 8.0f + (i3 * (CELL_S + 8.0f));
            float f6 = f4 + 8.0f;
            if (i >= f5 - 4.0f && i <= f5 + CELL_S + 4.0f && i2 >= f6 - 4.0f && i2 <= f6 + CELL_S + 4.0f) {
                this.hoveredTab = i3;
            }
        }
        int i4 = 0;
        while (i4 < clickGuiTabTypeArrValues.length) {
            ClickGuiTabType clickGuiTabType = clickGuiTabTypeArrValues[i4];
            float f7 = f3 + 8.0f + (i4 * (CELL_S + 8.0f));
            float f8 = f4 + 8.0f;
            boolean z = clickGuiTabType == clickGuiTabTypeK;
            this.hoverAnims[i4].a(this.hoveredTab == i4 ? 1.0d : 0.0d, 0.12d, Easing.n);
            this.hoverAnims[i4].a();
            float fJ = (float) this.hoverAnims[i4].j();
            if (z) {
                renderer2D.a(f7 - 3.0f, f8 - 3.0f, CELL_S + 6.0f, CELL_S + 6.0f, 7.0f, Theme.a(Theme.I, 150), Theme.a(Theme.I, 150), Theme.a(Theme.I, 150), Theme.a(Theme.I, 150), MatrixStackVar);
            } else {
                Color bg = Theme.a(Theme.e, 80);
                renderer2D.a(f7 - 3.0f, f8 - 3.0f, CELL_S + 6.0f, CELL_S + 6.0f, 7.0f, bg, bg, bg, bg, MatrixStackVar);
                if (fJ > 0.01f) {
                    renderer2D.a(f7 - 3.0f, f8 - 3.0f, CELL_S + 6.0f, CELL_S + 6.0f, 7.0f, Theme.a(Theme.I, (int) (fJ * 45.0f)), Theme.a(Theme.I, (int) (fJ * 45.0f)), Theme.a(Theme.I, (int) (fJ * 45.0f)), Theme.a(Theme.I, (int) (fJ * 45.0f)), MatrixStackVar);
                }
            }
            float iconOffset = (CELL_S - ICON_S) / 2.0f;
            renderer2D.a(IconTextureRegistry.get(clickGuiTabType.a()), f7 + iconOffset, f8 + iconOffset, ICON_S, ICON_S, 0.0f, 0.0f, 0.0f, 1.0f, 1.0f, z ? new Color(255, 255, 255, 255) : new Color(185, 195, 212, 155 + ((int) (fJ * 100.0f))), MatrixStackVar);
            i4++;
        }
    }

    @Override
    public void a(float f, float f2, int i, int i2) {
        this.open = true;
        this.dragging = true;
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        float fPanelW = f + ((fD - panelW()) / 2.0f);
        float f3 = f2 + fE + 5.0f;
        ClickGuiTabType[] clickGuiTabTypeArrValues = ClickGuiTabType.values();
        for (int i3 = 0; i3 < clickGuiTabTypeArrValues.length; i3++) {
            float f4 = fPanelW + 8.0f + (i3 * (CELL_S + 8.0f));
            float f5 = f3 + 8.0f;
            if (i >= f4 - 4.0f && i <= f4 + CELL_S + 4.0f && i2 >= f5 - 4.0f && i2 <= f5 + CELL_S + 4.0f) {
                this.screen.a(clickGuiTabTypeArrValues[i3]);
                SoundPlayer.play("click", 0.5f);
                return;
            }
        }
    }

    @Override
    public void b(float f, float f2, int i, int i2) {
        this.dragging = false;
    }

    public void c(float f, float f2, int i, int i2) {
        this.dragging = false;
    }

    public void a(float f, float f2, int i, int i2, double d, double d2) {
    }
}
