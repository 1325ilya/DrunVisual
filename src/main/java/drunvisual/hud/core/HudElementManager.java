package drunvisual.hud.core;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.lwjgl.glfw.GLFW;
import drunvisual.hud.snap.HudSnapGuide;
import drunvisual.module.ModuleRegistry;
import ru.drunvisual.DrunVisual;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.hud.elements.CooldownsHudElement;
import drunvisual.hud.elements.HotkeysHudElement;
import drunvisual.hud.elements.InventoryHudElement;
import drunvisual.hud.elements.PotionsHudElement;
import drunvisual.hud.elements.SaturationHudElement;
import drunvisual.hud.elements.TargetHudElement;
import drunvisual.hud.notifications.HudNotificationCenter;
import drunvisual.hud.snap.HudSnapCalculator;
import drunvisual.hud.snap.HudSnapResult;
import drunvisual.render.Renderer2D;
import drunvisual.render.ScreenScale;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;

public class HudElementManager {
    private static final int r = 50;
    private static final HudElementManager a = new HudElementManager();
    private static final Color m = new Color(100, 180, 255, 180);
    private static final Color n = new Color(EventPriority.HIGHEST, 100, 255, 180);
    private static final Color o = new Color(255, 150, 80, 180);
    private final MinecraftClient b = MinecraftClient.getInstance();
    private final List<HudElement> c = new ArrayList();
    private boolean i = false;
    private HudElement j = null;
    private int k = 0;
    private List<HudSnapGuide> l = new ArrayList();
    private final Deque<HudElementPositionSnapshot> p = new LinkedList();
    private final Deque<HudElementPositionSnapshot> q = new LinkedList();
    private float s = -1.0f;
    private float t = -1.0f;
    private boolean u = false;
    private HudNotificationCenter d = new HudNotificationCenter();
    private PotionsHudElement e = new PotionsHudElement(10.0f, 10.0f);
    private HotkeysHudElement f = new HotkeysHudElement(10.0f, 60.0f);
    private CooldownsHudElement g = new CooldownsHudElement(10.0f, 110.0f);
    private TargetHudElement h = new TargetHudElement(10.0f, 160.0f);
    private SaturationHudElement saturationHud = new SaturationHudElement(10.0f, 210.0f);
    private InventoryHudElement inventoryHud = new InventoryHudElement(10.0f, 250.0f);

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$drunvisual$HudSnapGuide$Anchor = new int[HudSnapGuide.Anchor.values().length];

        static {
            try {
                $SwitchMap$drunvisual$HudSnapGuide$Anchor[HudSnapGuide.Anchor.SCREEN_CENTER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$drunvisual$HudSnapGuide$Anchor[HudSnapGuide.Anchor.ELEMENT_EDGE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$drunvisual$HudSnapGuide$Anchor[HudSnapGuide.Anchor.ELEMENT_CENTER.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private HudElementManager() {
        c(this.e);
        c(this.f);
        c(this.g);
        c(this.h);
        c(this.saturationHud);
        c(this.inventoryHud);
    }

    public static HudElementManager a() {
        return a;
    }

    public void b() {
    }

    public void c() {
    }

    private void c(HudElement hudElement) {
        if (hudElement == null || this.c.contains(hudElement)) {
            return;
        }
        this.c.add(hudElement);
        r();
    }

    private void d(HudElement hudElement) {
        this.c.remove(hudElement);
    }

    private void r() {
        this.c.sort(Comparator.comparingInt((v0) -> {
            return v0.p();
        }));
    }

    private List<HudElement> s() {
        ArrayList<HudElement> arrayList = new ArrayList<>(this.c);
        arrayList.sort((HudElement hudElement, HudElement hudElement2) -> Integer.compare(hudElement2.p(), hudElement.p()));
        return arrayList;
    }

    private boolean e(HudElement hudElement) {
        return hudElement == this.e ? ModuleRegistry.POTIONS_HUD.k() : hudElement == this.f ? ModuleRegistry.HOTKEYS_HUD.k() : hudElement == this.g ? ModuleRegistry.COOLDOWNS_HUD.k() : hudElement == this.h ? ModuleRegistry.TARGET_HUD.k() : hudElement == this.saturationHud ? ModuleRegistry.SATURATION_HUD.k() : hudElement == this.inventoryHud ? ModuleRegistry.INVENTORY_HUD.k() : hudElement.c();
    }

    private List<HudElement> t() {
        return (List) this.c.stream().filter(this::e).collect(Collectors.toList());
    }

    private double scaleMouseX(double d) {
        return (d * this.b.getWindow().getScaleFactor()) / 2.0d;
    }

    private double scaleMouseY(double d) {
        return (d * this.b.getWindow().getScaleFactor()) / 2.0d;
    }

    public void a(MatrixStack MatrixStackVar) {
        Renderer2D render;
        if (this.b.world == null || this.b.player == null || (render = DrunVisual.getInstance().getRender()) == null) {
            return;
        }
        ScreenScale.a(2.0d);
        float fGetFramebufferWidth = (float) (((double) this.b.getWindow().getFramebufferWidth()) / 2.0d);
        float fGetFramebufferHeight = (float) (((double) this.b.getWindow().getFramebufferHeight()) / 2.0d);
        if (this.s > 0.0f && this.t > 0.0f && (Math.abs(fGetFramebufferWidth - this.s) > 1.0f || Math.abs(fGetFramebufferHeight - this.t) > 1.0f)) {
            a(fGetFramebufferWidth, fGetFramebufferHeight);
        }
        this.s = fGetFramebufferWidth;
        this.t = fGetFramebufferHeight;
        if (this.d != null && ModuleRegistry.WATERMARK.k()) {
            this.d.a(MatrixStackVar, render);
        }
        for (HudElement hudElement : this.c) {
            if (e(hudElement)) {
                hudElement.e();
                hudElement.a(MatrixStackVar, render, fGetFramebufferWidth, fGetFramebufferHeight);
                hudElement.a(render, MatrixStackVar);
            }
        }
        if (this.j != null && !this.l.isEmpty()) {
            a(MatrixStackVar, render);
        }
        if (this.j != null && !this.u) {
            a(MatrixStackVar, render, fGetFramebufferWidth, fGetFramebufferHeight);
        }
        int iGetX = (int) (((this.b.mouse.getX() * this.b.getWindow().getScaleFactor()) / 2.0d) / this.b.getWindow().getScaleFactor());
        int iGetY = (int) (((this.b.mouse.getY() * this.b.getWindow().getScaleFactor()) / 2.0d) / this.b.getWindow().getScaleFactor());
        int iGetScaleFactor = (int) ((((double) iGetX) * this.b.getWindow().getScaleFactor()) / 2.0d);
        int iMethod_44952 = (int) ((((double) iGetY) * this.b.getWindow().getScaleFactor()) / 2.0d);
        for (HudElement hudElement2 : this.c) {
            if (e(hudElement2)) {
                hudElement2.a(MatrixStackVar, render, iGetScaleFactor, iMethod_44952);
            }
        }
        ScreenScale.a();
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D) {
        Color color;
        for (HudSnapGuide hudSnapGuide : this.l) {
            switch (AnonymousClass1.$SwitchMap$drunvisual$HudSnapGuide$Anchor[hudSnapGuide.b().ordinal()]) {
                case ConfigState.a /* 1 */:
                    color = n;
                    break;
                case 2:
                case 3:
                    color = o;
                    break;
                default:
                    color = m;
                    break;
            }
            Color color2 = color;
            if (hudSnapGuide.a() == HudSnapGuide.Orientation.VERTICAL) {
                renderer2D.a(hudSnapGuide.c() - 0.5f, hudSnapGuide.d(), 1.0f, hudSnapGuide.e() - hudSnapGuide.d(), 0.0f, color2, MatrixStackVar);
            } else {
                renderer2D.a(hudSnapGuide.d(), hudSnapGuide.c() - 0.5f, hudSnapGuide.e() - hudSnapGuide.d(), 1.0f, 0.0f, color2, MatrixStackVar);
            }
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        FontRenderer altFont = FontManager.a[24];
        altFont.a("Удерживайте ALT для свободного перемещения", (f / 2.0f) - (altFont.a("Удерживайте ALT для свободного перемещения") / 2.0f), f2 - 65.0f, new Color(255, 255, 255, EventPriority.HIGHEST), MatrixStackVar);
    }

    private void a(float f, float f2) {
        Iterator<HudElement> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a(f, f2);
        }
    }

    public void d() {
    }

    private void f(HudElement hudElement) {
        this.p.push(new HudElementPositionSnapshot(hudElement));
        if (this.p.size() > r) {
            this.p.removeLast();
        }
        this.q.clear();
    }

    public void e() {
    }

    public void f() {
    }

    public boolean a(int i, int i2) {
        return false;
    }

    public void g() {
        long jGetHandle = this.b.getWindow().getHandle();
        this.u = Bool.from((GLFW.glfwGetKey(jGetHandle, 342) == 1 || GLFW.glfwGetKey(jGetHandle, 346) == 1) ? 1 : 0);
    }

    public HudNotificationCenter h() {
        return this.d;
    }

    public PotionsHudElement i() {
        return this.e;
    }

    public HotkeysHudElement j() {
        return this.f;
    }

    public CooldownsHudElement k() {
        return this.g;
    }

    public TargetHudElement l() {
        return this.h;
    }

    public void b(MatrixStack MatrixStackVar) {
    }

    public void a(double d, double d2) {
        if (this.i) {
            g();
            if (!this.b.isWindowFocused()) {
                for (HudElement hudElement : this.c) {
                    if (e(hudElement)) {
                        hudElement.a(0.0d, 0.0d, false);
                    }
                }
                return;
            }
            double dGetScaleFactor = (d * this.b.getWindow().getScaleFactor()) / 2.0d;
            double dMethod_44952 = (d2 * this.b.getWindow().getScaleFactor()) / 2.0d;
            if (this.j != null) {
                Iterator<HudElement> it = this.c.iterator();
                while (it.hasNext()) {
                    HudElement next = it.next();
                    if (e(next)) {
                        next.a(dGetScaleFactor, dMethod_44952, Bool.from(next == this.j ? 1 : 0));
                    }
                }
                GuiInput.g();
                return;
            }
            boolean z = false;
            Iterator<HudElement> it2 = this.c.iterator();
            while (true) {
                if (!it2.hasNext()) {
                    break;
                }
                HudElement next2 = it2.next();
                if (e(next2) && next2.f().c() && next2.f().c((int) dGetScaleFactor, (int) dMethod_44952)) {
                    z = true;
                    break;
                }
            }
            if (z) {
                for (HudElement hudElement2 : this.c) {
                    if (e(hudElement2)) {
                        hudElement2.a(dGetScaleFactor, dMethod_44952, false);
                    }
                }
                return;
            }
            HudElement hudElement3 = null;
            Iterator<HudElement> it3 = s().iterator();
            while (true) {
                if (!it3.hasNext()) {
                    break;
                }
                HudElement next3 = it3.next();
                if (e(next3) && next3.a(dGetScaleFactor, dMethod_44952)) {
                    hudElement3 = next3;
                    break;
                }
            }
            Iterator<HudElement> it4 = this.c.iterator();
            while (it4.hasNext()) {
                HudElement next4 = it4.next();
                if (e(next4)) {
                    next4.a(dGetScaleFactor, dMethod_44952, Bool.from(next4 == hudElement3 ? 1 : 0));
                }
            }
            if (hudElement3 != null) {
                GuiInput.g();
            }
        }
    }

    public boolean a(double d, double d2, int i) {
        if (!this.i || !this.b.isWindowFocused()) {
            return false;
        }
        double dScaleMouseX = scaleMouseX(d);
        double dScaleMouseY = scaleMouseY(d2);
        if (this.d != null && ModuleRegistry.WATERMARK.k() && this.d.a(dScaleMouseX, dScaleMouseY, i)) {
            return true;
        }
        for (HudElement hudElement : s()) {
            if (e(hudElement) && hudElement.a(dScaleMouseX, dScaleMouseY, i)) {
                if (i != 0 || !hudElement.i()) {
                    return true;
                }
                this.j = hudElement;
                f(hudElement);
                this.l.clear();
                return true;
            }
        }
        return false;
    }

    public boolean b(double d, double d2, int i) {
        if (!this.i) {
            return false;
        }
        double dScaleMouseX = scaleMouseX(d);
        double dScaleMouseY = scaleMouseY(d2);
        boolean z = false;
        if (this.j != null) {
            this.j.b(dScaleMouseX, dScaleMouseY, i);
            this.j = null;
            this.l.clear();
            z = true;
        }
        if (this.d != null && ModuleRegistry.WATERMARK.k()) {
            this.d.b(dScaleMouseX, dScaleMouseY, i);
        }
        return z;
    }

    public boolean a(double d, double d2, double d3) {
        double dGetScaleFactor = (d * this.b.getWindow().getScaleFactor()) / 2.0d;
        double dMethod_44952 = (d2 * this.b.getWindow().getScaleFactor()) / 2.0d;
        if (this.j == null) {
            for (HudElement hudElement : this.c) {
                if (e(hudElement) && hudElement.f().e() && hudElement.a(dGetScaleFactor, dMethod_44952, d3)) {
                    return true;
                }
            }
        }
        return (this.d != null && ModuleRegistry.WATERMARK.k() && this.d.a(dGetScaleFactor, dMethod_44952, d3)) ? true : false;
    }

    public void a(double d, double d2, double d3, double d4) {
        double dGetScaleFactor = (d * this.b.getWindow().getScaleFactor()) / 2.0d;
        double dMethod_44952 = (d2 * this.b.getWindow().getScaleFactor()) / 2.0d;
        if (this.j == null) {
            for (HudElement hudElement : this.c) {
                if (e(hudElement) && hudElement.f().e()) {
                    hudElement.a(dGetScaleFactor, dMethod_44952, d3, d4);
                }
            }
        }
        if (this.d != null && ModuleRegistry.WATERMARK.k()) {
            this.d.a(dGetScaleFactor, dMethod_44952, d3, d4);
        }
        if (this.j != null) {
            g();
            float fGetFramebufferWidth = (float) (((double) this.b.getWindow().getFramebufferWidth()) / 2.0d);
            float fGetFramebufferHeight = (float) (((double) this.b.getWindow().getFramebufferHeight()) / 2.0d);
            HudSnapResult hudSnapResultA = HudSnapCalculator.a(this.j, ((float) dGetScaleFactor) - this.j.r(), ((float) dMethod_44952) - this.j.s(), fGetFramebufferWidth, fGetFramebufferHeight, t(), this.u);
            this.j.a(hudSnapResultA, fGetFramebufferWidth, fGetFramebufferHeight);
            this.l = hudSnapResultA.c();
        }
    }

    public boolean b(double d, double d2) {
        if (this.j == null) {
            return false;
        }
        double dGetScaleFactor = (d * this.b.getWindow().getScaleFactor()) / 2.0d;
        double dMethod_44952 = (d2 * this.b.getWindow().getScaleFactor()) / 2.0d;
        float fGetFramebufferWidth = (float) (((double) this.b.getWindow().getFramebufferWidth()) / 2.0d);
        float fGetFramebufferHeight = (float) (((double) this.b.getWindow().getFramebufferHeight()) / 2.0d);
        g();
        HudSnapResult hudSnapResultA = HudSnapCalculator.a(this.j, ((float) dGetScaleFactor) - this.j.r(), ((float) dMethod_44952) - this.j.s(), fGetFramebufferWidth, fGetFramebufferHeight, t(), this.u);
        this.j.a(hudSnapResultA, fGetFramebufferWidth, fGetFramebufferHeight);
        this.l = hudSnapResultA.c();
        return true;
    }

    public boolean m() {
        return this.i;
    }

    public void a(boolean z) {
        if (this.i == z) {
            return;
        }
        this.i = z;
        if (z) {
            return;
        }
        double dScaleMouseX = scaleMouseX(this.b.mouse.getX());
        double dScaleMouseY = scaleMouseY(this.b.mouse.getY());
        if (this.j != null) {
            this.j.b(dScaleMouseX, dScaleMouseY, 0);
            this.j = null;
        }
        this.l.clear();
        Iterator<HudElement> it = this.c.iterator();
        while (it.hasNext()) {
            it.next().a(0.0d, 0.0d, false);
        }
        if (this.d != null) {
            this.d.g();
        }
    }

    public void n() {
        a(Bool.from(!this.i ? 1 : 0));
    }

    public List<HudElement> o() {
        return this.c;
    }

    public void a(HudElement hudElement) {
        c(hudElement);
    }

    public void b(HudElement hudElement) {
        this.c.remove(hudElement);
    }

    public boolean p() {
        return Bool.from(this.j != null ? 1 : 0);
    }

    public boolean q() {
        return this.u;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
