package drunvisual.hud.core;

import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import drunvisual.config.LocalConfigManager;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.gui.modules.ModuleSettingsPanel;
import drunvisual.hud.snap.HudSnapResult;
import drunvisual.render.Renderer2D;

public abstract class HudElement {
    private static final Logger LOG = LogManager.getLogger("drunvisual/config");
    protected float b;
    protected float c;
    protected float d;
    protected float e;
    protected boolean f;
    protected float g;
    protected float h;
    protected boolean j;
    protected boolean k;
    protected int l;
    private HudSnapResult snapState;
    protected final MinecraftClient a = MinecraftClient.getInstance();
    protected boolean i = true;
    private final AnimationState hoverAnimation = new AnimationState();
    private final HudElementPlacement placement = new HudElementPlacement();
    private float lastScreenWidth = -1.0f;
    private float lastScreenHeight = -1.0f;
    private final ModuleSettingsPanel settingsPanel = new ModuleSettingsPanel();

    public HudElement(float f, float f2) {
        this.b = f;
        this.c = f2;
        this.hoverAnimation.d(0.0d);
    }

    public abstract void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2);

    protected abstract void a();

    public void b() {
        a();
    }

    public boolean c() {
        return true;
    }

    public boolean a(double d, double d2, int i) {
        if (this.f) {
            return true;
        }
        int i2 = (int) d;
        int i3 = (int) d2;
        if (this.settingsPanel.c()) {
            if (this.settingsPanel.a(i2, i3)) {
                return true;
            }
            if (!this.settingsPanel.c(i2, i3)) {
                this.settingsPanel.a();
                return false;
            }
        }
        if (i != 0 || !a(d, d2)) {
            return false;
        }
        if (this.settingsPanel.c()) {
            this.settingsPanel.a();
        }
        this.f = true;
        this.g = ((float) d) - this.b;
        this.h = ((float) d2) - this.c;
        return true;
    }

    public void b(double d, double d2, int i) {
        if (i == 0) {
            if (this.f) {
                LOG.info("[DrunVisual] HUD moved -> nx={}, ny={}", Float.valueOf(this.placement.a()), Float.valueOf(this.placement.b()));
                LocalConfigManager.get().flushSaveNow("hud-move");
            }
            this.f = false;
            this.snapState = null;
        }
        this.settingsPanel.b((int) d, (int) d2);
    }

    public void a(HudSnapResult hudSnapResult, float f, float f2) {
        if (!this.f || hudSnapResult == null) {
            return;
        }
        this.b = hudSnapResult.a();
        this.c = hudSnapResult.b();
        this.snapState = hudSnapResult;
        this.placement.a(this.b, this.c, this.d, this.e, f, f2);
        this.lastScreenWidth = f;
        this.lastScreenHeight = f2;
    }

    public void a(double d, double d2, float f, float f2) {
        if (this.f) {
            float f3 = ((float) d) - this.g;
            float f4 = ((float) d2) - this.h;
            this.b = clamp(f3, 5.0f, (f - this.d) - 5.0f);
            this.c = clamp(f4, 5.0f, (f2 - this.e) - 5.0f);
            this.placement.a(this.b, this.c, this.d, this.e, f, f2);
            this.lastScreenWidth = f;
            this.lastScreenHeight = f2;
        }
    }

    public void a(float f, float f2) {
        if (this.lastScreenWidth <= 0.0f || this.lastScreenHeight <= 0.0f) {
            this.b = clamp(this.b, 5.0f, (f - this.d) - 5.0f);
            this.c = clamp(this.c, 5.0f, (f2 - this.e) - 5.0f);
        } else {
            this.b = clamp(this.placement.a(this.d, f), 5.0f, (f - this.d) - 5.0f);
            this.c = clamp(this.placement.b(this.e, f2), 5.0f, (f2 - this.e) - 5.0f);
        }
        this.lastScreenWidth = f;
        this.lastScreenHeight = f2;
    }

    public void b(float f, float f2) {
        this.placement.a(this.b, this.c, this.d, this.e, f, f2);
        this.lastScreenWidth = f;
        this.lastScreenHeight = f2;
    }

    public void a(double d, double d2, boolean z) {
        this.j = a(d, d2);
        this.k = z;
        boolean z2 = (this.j && z) || this.f;
        if (z2 && this.hoverAnimation.i() < 1.0d) {
            this.hoverAnimation.a(1.0d, 0.15d, Easing.h);
        } else {
            if (z2 || this.hoverAnimation.i() <= 0.0d) {
                return;
            }
            this.hoverAnimation.a(0.0d, 0.2d, Easing.h);
        }
    }

    public void d() {
        this.j = false;
        this.k = false;
        this.f = false;
        this.settingsPanel.b();
        if (this.hoverAnimation.j() > 0.0d || this.hoverAnimation.i() > 0.0d) {
            this.hoverAnimation.a(0.0d, 0.15d, Easing.h);
        }
    }

    public void e() {
        this.hoverAnimation.a();
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, int i, int i2) {
        this.settingsPanel.b(this.b, this.c, this.d, this.e);
        this.settingsPanel.a(MatrixStackVar, renderer2D, i, i2);
    }

    public void a(double d, double d2, double d3, double d4) {
        this.settingsPanel.a((int) d, (int) d2, d3, d4);
    }

    public boolean a(double d, double d2, double d3) {
        return this.settingsPanel.a((float) d3, (int) d, (int) d2);
    }

    public ModuleSettingsPanel f() {
        return this.settingsPanel;
    }

    public float g() {
        return this.settingsPanel.i();
    }

    public float h() {
        return (float) this.hoverAnimation.j();
    }

    public void a(Renderer2D renderer2D, MatrixStack MatrixStackVar) {
        float fH = h();
        if (fH < 0.01f) {
            return;
        }
        renderer2D.a(this.b, this.c, this.d, this.e, 13.0f * g(), new Color(130, 105, 255, (int) (25.0f * fH)), MatrixStackVar);
    }

    public boolean a(double d, double d2) {
        return d >= ((double) this.b) && d <= ((double) (this.b + this.d)) && d2 >= ((double) this.c) && d2 <= ((double) (this.c + this.e));
    }

    public boolean i() {
        return this.f;
    }

    public boolean j() {
        return this.i;
    }

    public void a(boolean z) {
        this.i = z;
    }

    public boolean k() {
        return this.j;
    }

    public float l() {
        return this.b;
    }

    public void a(float f) {
        this.b = f;
    }

    public float m() {
        return this.c;
    }

    public void b(float f) {
        this.c = f;
    }

    public float n() {
        return this.d;
    }

    public float o() {
        return this.e;
    }

    public int p() {
        return this.l;
    }

    public void a(int i) {
        this.l = i;
    }

    public HudSnapResult q() {
        return this.snapState;
    }

    public float r() {
        return this.g;
    }

    public float s() {
        return this.h;
    }

    public HudElementPlacement t() {
        return this.placement;
    }

    private static float clamp(float f, float f2, float f3) {
        return f3 < f2 ? f2 : Math.max(f2, Math.min(f, f3));
    }
}
