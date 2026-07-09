package drunvisual.hud.elements;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleRegistry;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.hud.core.HudElement;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.icons.IconTextureRegistry;

public class HotkeysHudElement extends HudElement {
    static final Color o = new Color(13, 13, 17);
    static final Color p = new Color(17, 17, 23);
    private static final Color q = new Color(255, 255, 255);
    private static final Color r = new Color(223, 223, 243);
    private static final Color s = new Color(20, 20, 28);
    private static final Color t = new Color(16, 16, 21);
    private static final Color u = new Color(17, 17, 23);
    private static final Color v = new Color(23, 23, 30);
    private static final Color w = new Color(18, 18, 24);
    private static final Color x = new Color(115, 83, 255);
    private static final Color y = new Color(70, 46, 174);
    private static final float z = 8.5f;
    private static final float A = 8.0f;
    private static final float B = 24.0f;
    private static final float C = 16.0f;
    private static final float D = 5.0f;
    private static final float E = 12.0f;
    private static final float F = 2.0f;
    private static final float G = 12.0f;
    private static final float H = 6.0f;
    private static final float I = 6.5f;
    private static final float J = 0.5f;
    private static final float K = 82.0f;
    private static final double L = 0.2d;
    private static final long M = 1000;
    private final Map<ClientModule, ActiveHotkeyEntry> N;
    private final List<HotkeyPlaceholder> O;
    private final AnimationState P;
    private final AnimationState Q;
    private final AnimationState R;
    private long S;
    private int T;
    private boolean U;
    private boolean V;
    private boolean W;
    public static boolean n;

    private static class ActiveHotkeyEntry {
        final ClientModule a;
        String b;
        String c;
        final AnimationState d = new AnimationState();
        final AnimationState e = new AnimationState();
        boolean f = false;
        public static int g;
        public static boolean h;

        ActiveHotkeyEntry(ClientModule clientModule, String str, String str2) {
            this.a = clientModule;
            this.b = str;
            this.c = str2;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class HotkeyPlaceholder {
        final String a;
        final String b;
        public static int c;
        public static boolean d;

        HotkeyPlaceholder(String str, String str2) {
            this.a = str;
            this.b = str2;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class HotkeySnapshot {
        final ClientModule a;
        final String b;
        final String c;
        public static int d;
        public static boolean e;

        HotkeySnapshot(ClientModule clientModule, String str, String str2) {
            this.a = clientModule;
            this.b = str;
            this.c = str2;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public HotkeysHudElement(float f, float f2) {
        super(f, f2);
        this.N = new LinkedHashMap();
        this.O = new ArrayList();
        this.P = new AnimationState();
        this.Q = new AnimationState();
        this.R = new AnimationState();
        this.S = 0L;
        this.T = 0;
        this.U = false;
        this.V = false;
        this.W = false;
        this.R.d(0.0d);
        u();
        a();
    }

    private void u() {
        this.O.add(new HotkeyPlaceholder("Trails", "  G  "));
        this.O.add(new HotkeyPlaceholder("Fast Swap", "  O  "));
        this.O.add(new HotkeyPlaceholder("PVP Safe", "  B  "));
    }

    private boolean v() {
        return Bool.from(((this.a.currentScreen instanceof ChatScreen) && x().isEmpty()) ? 1 : 0);
    }

    private void w() {
        if (this.W || ModuleRegistry.HOTKEYS_HUD == null) {
            return;
        }
        f().a(ModuleRegistry.HOTKEYS_HUD);
        this.W = true;
    }

    @Override
    protected void a() {
        float f;
        w();
        boolean zV = v();
        List<HotkeySnapshot> listX = x();
        float fG = g();
        float f2 = z * fG;
        float f3 = B * fG;
        float f4 = D * fG;
        float f5 = 12.0f * fG;
        float f6 = F * fG;
        float f7 = I * fG;
        float f8 = J * fG;
        float f9 = K * fG;
        int iRound = Math.round(15.0f * fG);
        int iRound2 = Math.round(14.0f * fG);
        FontRenderer fontRenderer = FontManager.a[Math.max(10, Math.min(48, iRound))];
        FontRenderer fontRenderer2 = FontManager.a[Math.max(10, Math.min(48, iRound2))];
        if (zV) {
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.S >= M) {
                this.T = ((this.T - (-2)) - 1) % this.O.size();
                this.S = jCurrentTimeMillis;
            }
        }
        HashSet hashSet = new HashSet();
        Iterator<HotkeySnapshot> it = listX.iterator();
        while (it.hasNext()) {
            hashSet.add(it.next().a);
        }
        if (n) {
            throw new ExceptionInInitializerError();
        }
        for (ActiveHotkeyEntry activeHotkeyEntry : this.N.values()) {
            if (!hashSet.contains(activeHotkeyEntry.a) && !activeHotkeyEntry.f) {
                activeHotkeyEntry.f = true;
                activeHotkeyEntry.d.a(0.0d, L, Easing.h);
            }
        }
        int i = 0;
        for (HotkeySnapshot hotkeySnapshot : listX) {
            ActiveHotkeyEntry activeHotkeyEntry2 = this.N.get(hotkeySnapshot.a);
            if (activeHotkeyEntry2 == null) {
                ActiveHotkeyEntry activeHotkeyEntry3 = new ActiveHotkeyEntry(hotkeySnapshot.a, hotkeySnapshot.b, hotkeySnapshot.c);
                activeHotkeyEntry3.d.d(0.0d);
                activeHotkeyEntry3.d.a(1.0d, L, Easing.h);
                activeHotkeyEntry3.e.d(i);
                this.N.put(hotkeySnapshot.a, activeHotkeyEntry3);
            } else {
                activeHotkeyEntry2.b = hotkeySnapshot.b;
                activeHotkeyEntry2.c = hotkeySnapshot.c;
                activeHotkeyEntry2.f = false;
                if (activeHotkeyEntry2.d.i() < 1.0d) {
                    activeHotkeyEntry2.d.a(1.0d, L, Easing.h);
                }
                if (Math.abs(activeHotkeyEntry2.e.i() - ((double) i)) > 0.01d) {
                    activeHotkeyEntry2.e.a(i, L, Easing.h);
                }
            }
            i++;
        }
        this.N.entrySet().removeIf(entry -> {
            return (!((ActiveHotkeyEntry) entry.getValue()).f || ((ActiveHotkeyEntry) entry.getValue()).d.j() >= 0.01d) ? false : true;
        });
        for (ActiveHotkeyEntry activeHotkeyEntry4 : this.N.values()) {
            activeHotkeyEntry4.d.a();
            activeHotkeyEntry4.e.a();
        }
        boolean z2 = this.V;
        this.V = false;
        int i2 = 0;
        if (zV) {
            this.V = true;
        } else {
            Iterator<ActiveHotkeyEntry> it2 = this.N.values().iterator();
            while (it2.hasNext()) {
                if (!it2.next().f) {
                    this.V = true;
                    i2++;
                }
            }
        }
        if (this.V && !z2) {
            this.R.a(1.0d, L, Easing.h);
        } else if (!this.V && z2) {
            this.R.a(0.0d, L, Easing.h);
        } else if (this.V && this.R.i() < 1.0d) {
            this.R.a(1.0d, L, Easing.h);
        }
        this.R.a();
        float fA = 0.0f;
        if (zV) {
            HotkeyPlaceholder hotkeyPlaceholder = this.O.get(this.T);
            fA = fontRenderer.a(hotkeyPlaceholder.a) + (8.0f * fG) + fontRenderer2.a(hotkeyPlaceholder.b) + (f7 * F) + (f8 * F);
        } else {
            for (ActiveHotkeyEntry activeHotkeyEntry5 : this.N.values()) {
                if (!activeHotkeyEntry5.f) {
                    fA = Math.max(fA, fontRenderer.a(activeHotkeyEntry5.b) + (8.0f * fG) + fontRenderer2.a(activeHotkeyEntry5.c) + (f7 * F) + (f8 * F));
                }
            }
        }
        float fMax = (f2 * F) + Math.max(f9, fA);
        float f10 = 1.0f * fG;
        if (i2 == 0) {
            f = 0.0f;
        } else {
            f = (i2 - 1) * (f5 + f6);
        }
        float f12 = f3 + f10 + f4 + f + f4 + (F * fG) + (13.0f * fG);
        if (this.U) {
            if (Math.abs(this.Q.i() - ((double) fMax)) > 0.5d) {
                this.Q.a(fMax, L, Easing.h);
            }
            if (Math.abs(this.P.i() - ((double) f12)) > 0.5d) {
                this.P.a(f12, L, Easing.h);
            }
        } else {
            this.Q.d(fMax);
            this.P.d(f12);
            this.U = true;
        }
        this.Q.a();
        this.P.a();
        this.d = (float) this.Q.j();
        this.e = (float) this.P.j();
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2) {
        float f3;
        if (this.a.player != null) {
            a();
            float fJ = (float) this.R.j();
            if (fJ >= 0.01f) {
                boolean zV = v();
                float fG = g();
                float f4 = z * fG;
                float f5 = 8.0f * fG;
                float f6 = B * fG;
                float f7 = C * fG;
                float f8 = D * fG;
                float f9 = 12.0f * fG;
                float f10 = F * fG;
                int iRound = Math.round(C * fG);
                int iRound2 = Math.round(15.0f * fG);
                int iRound3 = Math.round(14.0f * fG);
                boolean z2 = fJ < 0.99f;
                if (z2) {
                    renderer2D.c(this.b, this.c, this.d, this.e, MatrixStackVar);
                }
                float f11 = !z2 ? fJ : 1.0f;
                FontRenderer fontRenderer = FontManager.b[Math.max(10, Math.min(48, iRound))];
                FontRenderer fontRenderer2 = FontManager.a[Math.max(10, Math.min(48, iRound2))];
                FontRenderer fontRenderer3 = FontManager.a[Math.max(10, Math.min(48, iRound3))];
                Color colorA = a(o, f11);
                Color colorA2 = a(p, f11);
                renderer2D.a(this.b, this.c, this.d, this.e, f5, colorA, colorA, colorA2, colorA2, MatrixStackVar);
                float f12 = this.b + f4;
                float f13 = this.c + (f6 / F);
                Identifier keyboardIcon = IconTextureRegistry.get(IconTextureRegistry.KEYBOARD);
                float iconSize = 12.0f * fG;
                renderer2D.a(keyboardIcon, (f12 + ((f7 - iconSize) / F)) - 4.0f, (f13 - (iconSize / 2.0f)) + J, iconSize, iconSize, a(x, f11), MatrixStackVar);
                fontRenderer.a("Hot Keys", (f12 + f7) - (F * fG), (f13 - (fontRenderer.b("Hot Keys") / 4.0f)) + (1.0f * fG), a(q, f11), MatrixStackVar);
                float f14 = this.c + f6;
                renderer2D.a(this.b, f14, this.d, 1.0f * fG, 0.0f, a(s, f11), MatrixStackVar);
                renderer2D.b().a(this.b, f14 + (1.0f * fG), this.d, (this.e - f6) - (1.0f * fG), 0.0f, MatrixStackVar);
                float f15 = f14 + (1.0f * fG) + f8;
                if (zV) {
                    a(MatrixStackVar, renderer2D, fontRenderer2, fontRenderer3, f12, f15, this.O.get(this.T), f11, fG);
                } else {
                    for (ActiveHotkeyEntry activeHotkeyEntry : this.N.values()) {
                        float fJ2 = (float) activeHotkeyEntry.d.j();
                        if (z2) {
                            f3 = fJ2;
                        } else {
                            f3 = fJ2 * fJ;
                        }
                        float f16 = f3;
                        if (f16 >= 0.01f) {
                            a(MatrixStackVar, renderer2D, fontRenderer2, fontRenderer3, f12, f15 + (((float) activeHotkeyEntry.e.j()) * (f9 + f10)), activeHotkeyEntry, f16, fG);
                        }
                    }
                }
                renderer2D.b().a(MatrixStackVar);
                if (z2) {
                    renderer2D.a(fJ, MatrixStackVar);
                }
            }
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, HotkeyPlaceholder hotkeyPlaceholder, float f3, float f4) {
        float f5 = z * f4;
        float f6 = I * f4;
        float f7 = J * f4;
        float f8 = 12.0f * f4;
        float f9 = H * f4;
        float f10 = f2 + ((12.0f * f4) / F);
        Color colorA = a(r, f3);
        fontRenderer.a(hotkeyPlaceholder.a, f, f10 - (fontRenderer.b(hotkeyPlaceholder.a) / 4.0f), colorA, MatrixStackVar);
        float fA = ((fontRenderer2.a(hotkeyPlaceholder.b) + (f6 * F)) + (f7 * F)) - (F * f4);
        float f11 = ((this.b + this.d) - f5) - fA;
        float f12 = (f10 - (f8 / F)) - f7;
        float f13 = f8 + (f7 * F);
        Color colorA2 = a(v, f3);
        Color colorA3 = a(w, f3);
        Color colorA4 = a(t, f3);
        Color colorA5 = a(u, f3);
        renderer2D.a(f11, f12, fA, f13, f9, colorA2, colorA2, colorA3, colorA3, MatrixStackVar);
        renderer2D.a(f11 + f7, f12 + f7, fA - (f7 * F), f8, f9 - f7, colorA4, colorA4, colorA5, colorA5, MatrixStackVar);
        fontRenderer2.a(hotkeyPlaceholder.b, f11 + ((fA - fontRenderer2.a(hotkeyPlaceholder.b)) / F), f10 - (fontRenderer2.b(hotkeyPlaceholder.b) / 4.0f), colorA, MatrixStackVar);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, FontRenderer fontRenderer, FontRenderer fontRenderer2, float f, float f2, ActiveHotkeyEntry activeHotkeyEntry, float f3, float f4) {
        float f5 = z * f4;
        float f6 = I * f4;
        float f7 = J * f4;
        float f8 = 12.0f * f4;
        float f9 = H * f4;
        float f10 = f2 + ((12.0f * f4) / F);
        Color colorA = a(r, f3);
        fontRenderer.a(activeHotkeyEntry.b, f, f10 - (fontRenderer.b(activeHotkeyEntry.b) / 4.0f), colorA, MatrixStackVar);
        float fA = ((fontRenderer2.a(activeHotkeyEntry.c) + (f6 * F)) + (f7 * F)) - (F * f4);
        float f11 = ((this.b + this.d) - f5) - fA;
        float f12 = (f10 - (f8 / F)) - f7;
        float f13 = f8 + (f7 * F);
        Color colorA2 = a(v, f3);
        Color colorA3 = a(w, f3);
        Color colorA4 = a(t, f3);
        Color colorA5 = a(u, f3);
        renderer2D.a(f11, f12, fA, f13, f9, colorA2, colorA2, colorA3, colorA3, MatrixStackVar);
        renderer2D.a(f11 + f7, f12 + f7, fA - (f7 * F), f8, f9 - f7, colorA4, colorA4, colorA5, colorA5, MatrixStackVar);
        fontRenderer2.a(activeHotkeyEntry.c, f11 + ((fA - fontRenderer2.a(activeHotkeyEntry.c)) / F), f10 - (fontRenderer2.b(activeHotkeyEntry.c) / 4.0f), colorA, MatrixStackVar);
    }

    private Color a(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.max(0, Math.min(255, (int) (color.getAlpha() * f))));
    }

    private List<HotkeySnapshot> x() {
        ArrayList arrayList = new ArrayList();
        for (ClientModule clientModule : ModuleRegistry.all()) {
            if (clientModule.k() && clientModule.j() != 0) {
                arrayList.add(new HotkeySnapshot(clientModule, clientModule.g(), "  " + b(clientModule.j()) + "  "));
            }
        }
        arrayList.sort(Comparator.comparing((HotkeySnapshot hotkeySnapshot) -> hotkeySnapshot.b));
        return arrayList;
    }

    private String b(int i) {
        if (i == 0) {
            return "None";
        }
        if (i < 8) {
            switch (i) {
                case EventPriority.MEDIUM /* 0 */:
                    return "LMB";
                case ConfigState.a /* 1 */:
                    return "RMB";
                case 2:
                    return "MMB";
                default:
                    return "M" + i;
            }
        }
        switch (i) {
            case 32:
                return "Space";
            case 39:
                return "'";
            case 44:
                return ",";
            case 45:
                return "-";
            case 46:
                return ".";
            case 47:
                return "/";
            case 59:
                return ";";
            case 61:
                return "=";
            case 91:
                return "[";
            case 92:
                return "\\";
            case 93:
                return "]";
            case 96:
                return "`";
            case 256:
                return "Esc";
            case 257:
                return "Enter";
            case 258:
                return "Tab";
            case 259:
                return "Back";
            case 260:
                return "Ins";
            case 261:
                return "Del";
            case 262:
                return "Right";
            case 263:
                return "Left";
            case 264:
                return "Down";
            case 265:
                return "Up";
            case 266:
                return "PgUp";
            case 267:
                return "PgDn";
            case 268:
                return "Home";
            case 269:
                return "End";
            case 280:
                return "Caps";
            case 281:
                return "Scroll";
            case 282:
                return "Num";
            case 283:
                return "Print";
            case 284:
                return "Pause";
            case 340:
                return "LShift";
            case 341:
                return "LCtrl";
            case 342:
                return "LAlt";
            case 343:
                return "LWin";
            case 344:
                return "RShift";
            case 345:
                return "RCtrl";
            case 346:
                return "RAlt";
            case 347:
                return "RWin";
            case 348:
                return "Menu";
            default:
                if (i >= 290 && i <= 301) {
                    return "F" + ((i - 290) + 1);
                }
                if (i >= 302 && i <= 314) {
                    return "F" + ((i - 302) + 13);
                }
                if (i >= 320 && i <= 329) {
                    return "Num" + (i - 320);
                }
                switch (i) {
                    case 330:
                        return "Num.";
                    case 331:
                        return "Num/";
                    case 332:
                        return "Num*";
                    case 333:
                        return "Num-";
                    case 334:
                        return "Num+";
                    case 335:
                        return "NumEnter";
                    case 336:
                        return "Num=";
                    default:
                        return (i < 48 || i > 57) ? (i < 65 || i > 90) ? "Key" + i : String.valueOf((char) i) : String.valueOf((char) i);
                }
        }
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
