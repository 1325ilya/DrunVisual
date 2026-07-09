package drunvisual.gui.settings;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.WeakHashMap;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.PlayerListEntry;
import drunvisual.settings.TokenSetting;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;
import drunvisual.util.MarqueeText;

public class TokenSettingWidget implements SettingWidget {
    private static final Set<TokenSettingWidget> d = Collections.newSetFromMap(new WeakHashMap());
    public static final float a = 20.0f;
    private static final float e = 8.0f;
    private static final float f = 40.0f;
    private static final float g = 100.0f;
    private static final float h = 14.0f;
    private static final float i = 4.0f;
    private static final float j = 5.0f;
    private static final int k = 16;
    private final String l;
    private final ValueType m;
    private String n;
    private String o;
    private final TokenSetting p;
    private boolean q;
    private int r;
    private int s;
    private int t;
    private final AnimationState u;
    private final AnimationState v;
    private final AnimationState w;
    private final AnimationState x;
    private final MarqueeText y;
    private boolean z;
    private boolean A;
    private float B;
    private float C;
    private float D;
    private float E;
    private float F;
    private String G;
    public static int b;
    public static boolean c;

    public enum ValueType {
        COMMAND,
        PRICE,
        PLAYER,
        INT;

        public static int e;
        public static boolean f;

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    public TokenSettingWidget(TokenSetting tokenSetting, ValueType valueType) {
        this.n = "";
        this.o = "";
        this.q = false;
        this.r = 0;
        this.s = -1;
        this.t = -1;
        this.u = new AnimationState();
        this.v = new AnimationState();
        this.w = new AnimationState();
        this.x = new AnimationState();
        this.y = new MarqueeText();
        this.z = false;
        this.A = true;
        this.E = 1.0f;
        this.G = "";
        this.p = tokenSetting;
        this.l = tokenSetting.f();
        this.m = valueType;
        this.o = tokenSetting.c();
        this.w.d(1.0d);
        this.F = f;
        this.x.d(40.0d);
        d.add(this);
        a(tokenSetting.a());
    }

    public TokenSettingWidget(String str, ValueType valueType) {
        this.n = "";
        this.o = "";
        this.q = false;
        this.r = 0;
        this.s = -1;
        this.t = -1;
        this.u = new AnimationState();
        this.v = new AnimationState();
        this.w = new AnimationState();
        this.x = new AnimationState();
        this.y = new MarqueeText();
        this.z = false;
        this.A = true;
        this.E = 1.0f;
        this.G = "";
        this.p = null;
        this.l = str;
        this.m = valueType;
        this.w.d(1.0d);
        this.F = f;
        this.x.d(40.0d);
        d.add(this);
    }

    public TokenSettingWidget(String str, ValueType valueType, String str2) {
        this(str, valueType);
        this.o = str2;
    }

    public TokenSettingWidget(String str, ValueType valueType, String str2, String str3) {
        this(str, valueType, str3);
        a(str2);
    }

    @Override
    public String a() {
        return this.l;
    }

    @Override
    public float b() {
        return 20.0f;
    }

    public ValueType c() {
        return this.m;
    }

    public String e() {
        return this.n;
    }

    public String f() {
        switch (this.m.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                return "/" + this.n;
            case ConfigState.a /* 1 */:
                return "$" + this.n;
            default:
                return this.n;
        }
    }

    public String g() {
        return this.m != ValueType.PRICE ? this.n : this.n.replace(",", "");
    }

    public int h() {
        if (this.m == ValueType.INT) {
            try {
                return Integer.parseInt(this.n);
            } catch (NumberFormatException e2) {
                return 0;
            }
        }
        if (this.m != ValueType.PRICE) {
            return 0;
        }
        try {

return Integer.parseInt(this.n.replace(",", ""));
        } catch (NumberFormatException e3) {
            return 0;
        }
    }

    public void a(String str) {
        if (str == null) {
            str = "";
        }
        StringBuilder sb = new StringBuilder();
        for (char c2 : str.toCharArray()) {
            if (b(c2, sb.length())) {
                sb.append(c2);
            }
        }
        this.n = sb.toString();
        if (this.m == ValueType.PRICE) {
            this.n = b(this.n.replace(",", ""));
        }
        this.r = Math.min(this.r, this.n.length());
        H();
        t();
        q();
    }

    private void q() {
        if (this.p != null) {
            this.p.a(this.n);
        }
    }

    public boolean i() {
        return this.q;
    }

    @Override
    public boolean a_() {
        return this.q;
    }

    private String r() {
        switch (this.m.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                return "/";
            case ConfigState.a /* 1 */:
                return "$";
            default:
                return "";
        }
    }

    private String s() {
        return r() + this.n;
    }

    private boolean b(char c2, int i2) {
        switch (this.m.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                return ((c2 < 'a' || c2 > 'z') && (c2 < 'A' || c2 > 'Z') && ((c2 < '0' || c2 > '9') && c2 != ' ')) ? false : true;
            case ConfigState.a /* 1 */:
                return (c2 < '0' || c2 > '9') ? false : true;
            case 2:
                return ((c2 < 'a' || c2 > 'z') && (c2 < 'A' || c2 > 'Z') && ((c2 < '0' || c2 > '9') && c2 != '_')) ? false : true;
            case 3:
                if ((c2 < '0' || c2 > '9') && !(c2 == '-' && i2 == 0 && !this.n.contains("-"))) {
                    return false;
                }
                return true;
            default:
                return true;
        }
    }

    private boolean a(char c2) {
        return b(c2, this.r);
    }

    private String b(String str) {
        if (str.isEmpty()) {
            return "";
        }
        try {
            long j2 = Long.parseLong(str);
            StringBuilder sb = new StringBuilder();
            String strValueOf = String.valueOf(j2);
            int i2 = 0;
            for (int length = strValueOf.length() - 1; length >= 0; length--) {
                if (i2 > 0) {
                    if (i2 % 3 == 0) {

                        sb.insert(0, ",");
                    } else if (c) {
                    }
                }
                sb.insert(0, strValueOf.charAt(length));
                i2++;
            }
            return sb.toString();
        } catch (NumberFormatException e2) {
            return str;
        }
    }

    private void t() {
        String str;
        FontRenderer fontRenderer = FontManager.a[14];
        String strS = s();
        if (strS.isEmpty()) {
            str = r() + this.o;
        } else {
            str = strS;
        }
        float fMax = Math.max(f, Math.min(100.0f, fontRenderer.a(str) + 10.0f));
        if (Math.abs(fMax - this.F) > 1.0f) {
            this.x.a(fMax, 0.15d, Easing.h);
        }
    }

    private void u() {
        this.G = "";
        if (this.m != ValueType.PLAYER || this.n.isEmpty()) {
            return;
        }
        List<String> listV = v();
        String lowerCase = this.n.toLowerCase(Locale.ROOT);
        for (String str : listV) {
            if (str.toLowerCase(Locale.ROOT).startsWith(lowerCase) && !str.equalsIgnoreCase(this.n)) {
                this.G = str.substring(this.n.length());
                return;
            }
        }
    }

    private List<String> v() {
        ArrayList arrayList = new ArrayList();
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        if (MinecraftClientVarGetInstance.getNetworkHandler() != null) {
            for (PlayerListEntry PlayerListEntryVar : MinecraftClientVarGetInstance.getNetworkHandler().getPlayerList()) {
                if (PlayerListEntryVar.getProfile() != null && PlayerListEntryVar.getProfile().getName() != null) {
                    arrayList.add(PlayerListEntryVar.getProfile().getName());
                }
            }
        }
        return arrayList;
    }

    private void w() {
        if (this.G.isEmpty()) {
            return;
        }
        this.n += this.G;
        this.r = this.n.length();
        this.G = "";
        H();
        t();
    }

    private static void a(TokenSettingWidget tokenSettingWidget) {
        for (TokenSettingWidget tokenSettingWidget2 : d) {
            if (tokenSettingWidget2 != tokenSettingWidget && tokenSettingWidget2.q) {
                tokenSettingWidget2.q = false;
                tokenSettingWidget2.u.a(0.0d, 0.2d, Easing.h);
                tokenSettingWidget2.G = "";
            }
        }
    }

    public static boolean m() {
        Iterator<TokenSettingWidget> it = d.iterator();
        while (it.hasNext()) {
            if (it.next().q) {
                return true;
            }
        }
        return false;
    }

    public static TokenSettingWidget n() {
        for (TokenSettingWidget tokenSettingWidget : d) {
            if (tokenSettingWidget.q) {
                return tokenSettingWidget;
            }
        }
        return null;
    }

    public static void o() {
        for (TokenSettingWidget tokenSettingWidget : d) {
            if (tokenSettingWidget.q) {
                tokenSettingWidget.q = false;
                tokenSettingWidget.u.d(0.0d);
                tokenSettingWidget.G = "";
                tokenSettingWidget.H();
            }
        }
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i2, int i3, float f5, float f6) {
        FontRenderer fontRenderer = FontManager.a[14];
        float f7 = 20.0f * f6;
        float f8 = 8.0f * f6;
        this.E = f6;
        this.y.a(GuiInput.a(f2, f3, f4, f7, i2, i3));
        this.u.a();
        this.v.a();
        this.x.a();
        this.F = (float) this.x.j();
        if (this.q) {
            this.w.a();
            if (this.w.d()) {
                this.A = Bool.from(this.A ? 0 : 1);
                this.w.a(!this.A ? 0.3d : 1.0d, 0.5d, Easing.i);
            }
        } else {
            this.w.d(1.0d);
            this.A = true;
        }
        int i4 = (int) (255.0f * f5);
        float f9 = this.F * f6;
        float f10 = h * f6;
        float f11 = ((f2 + f4) - f8) - f9;
        float f12 = (f3 + (f7 / 2.0f)) - (f10 / 2.0f);
        this.y.a(MatrixStackVar, renderer2D, fontRenderer, this.l, f2 + f8, ((f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.l) * f6) / i)) - 1.0f, (f11 - (f2 + f8)) - (i * f6), f6, Theme.a, f5);
        this.B = f11;
        this.C = f12;
        this.D = f9;
        boolean zA = GuiInput.a(f11, f12, f9, f10, i2, i3);
        if (zA != this.z) {
            this.v.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.z = zA;
        }
        float fJ = (float) this.u.j();
        float fJ2 = (float) this.v.j();
        Color colorA = ColorUtils.a(Theme.m, Theme.y, fJ);
        Color colorA2 = ColorUtils.a(Theme.r, Theme.z, fJ);
        Color colorA3 = Theme.a(colorA, i4);
        Color colorA4 = Theme.a(colorA2, i4);
        renderer2D.a(f11 - (0.5f * f6), f12 - (0.5f * f6), f9 + f6, f10 + f6, i * f6, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        int i5 = (int) (fJ2 * 3.0f);
        Color colorA5 = Theme.a(Theme.b(Theme.e, i5), i4);
        Color colorA6 = Theme.a(Theme.b(Theme.f, i5), i4);
        renderer2D.a(f11, f12, f9, f10, i * f6, colorA5, colorA5, colorA6, colorA6, MatrixStackVar);
        float f13 = j * f6;
        float f14 = f11 + f13;
        renderer2D.b().a(f14 - 1.0f, f12, (f9 - (f13 * 2.0f)) + 2.0f, f10, MatrixStackVar);
        String strR = r();
        String strS = s();
        float fB = (f12 + (f10 / 2.0f)) - ((fontRenderer.b("A") * f6) / i);
        if (!this.n.isEmpty() || this.q) {
            if (G()) {
                int iMin = Math.min(this.s, this.t);
                int iMax = Math.max(this.s, this.t);
                float fA = fontRenderer.a(strR) * f6;
                float fA2 = f14 + fA + (fontRenderer.a(this.n, iMin) * f6);
                renderer2D.a(fA2, f12 + (2.0f * f6), ((f14 + fA) + (fontRenderer.a(this.n, iMax) * f6)) - fA2, f10 - (i * f6), 2.0f * f6, new Color(Theme.A.getRed(), Theme.A.getGreen(), Theme.A.getBlue(), (int) (Theme.A.getAlpha() * f5)), MatrixStackVar);
            }
            Color colorA7 = Theme.a(Theme.a, i4);
            MatrixStackVar.push();
            MatrixStackVar.translate(f14, fB, 0.0f);
            MatrixStackVar.scale(f6, f6, 1.0f);
            MatrixStackVar.translate(-f14, -fB, 0.0f);
            fontRenderer.a(strS, f14, fB, colorA7, MatrixStackVar);
            MatrixStackVar.pop();
            if (this.m == ValueType.PLAYER && !this.G.isEmpty() && this.q) {
                float fA3 = f14 + (fontRenderer.a(strS) * f6);
                Color colorA8 = Theme.a(Theme.d, i4);
                MatrixStackVar.push();
                MatrixStackVar.translate(fA3, fB, 0.0f);
                MatrixStackVar.scale(f6, f6, 1.0f);
                MatrixStackVar.translate(-fA3, -fB, 0.0f);
                fontRenderer.a(this.G, fA3, fB, colorA8, MatrixStackVar);
                MatrixStackVar.pop();
            }
            if (this.q) {
                if (G()) {
                } else {
                    float fJ3 = (float) this.w.j();
                    if (fJ3 > 0.3f) {
                        renderer2D.a(f14 + (fontRenderer.a(strR) * f6) + (fontRenderer.a(this.n, this.r) * f6), f12 + (3.0f * f6), 1.0f * f6, f10 - (6.0f * f6), Theme.a(Theme.a, (int) (255.0f * f5 * fJ3)), MatrixStackVar);
                    }
                }
            }
        } else {
            String str = strR + this.o;
            if (!str.isEmpty()) {
                Color colorA9 = Theme.a(Theme.b, i4);
                MatrixStackVar.push();
                MatrixStackVar.translate(f14, fB, 0.0f);
                MatrixStackVar.scale(f6, f6, 1.0f);
                MatrixStackVar.translate(-f14, -fB, 0.0f);
                fontRenderer.a(str, f14, fB, colorA9, MatrixStackVar);
                MatrixStackVar.pop();
            }
        }
        if (this.n.isEmpty() && this.q && !G()) {
            float fJ4 = (float) this.w.j();
            float fA4 = fontRenderer.a(strR) * f6;
            Color colorA10 = Theme.a(Theme.a, i4);
            MatrixStackVar.push();
            MatrixStackVar.translate(f14, fB, 0.0f);
            MatrixStackVar.scale(f6, f6, 1.0f);
            MatrixStackVar.translate(-f14, -fB, 0.0f);
            fontRenderer.a(strR, f14, fB, colorA10, MatrixStackVar);
            MatrixStackVar.pop();
            renderer2D.a(f14 + fA4, f12 + (3.0f * f6), 1.0f * f6, f10 - (6.0f * f6), Theme.a(Theme.a, (int) (255.0f * f5 * fJ4)), MatrixStackVar);
        }
        renderer2D.b().a(MatrixStackVar);
        if (zA || this.q) {
            GuiInput.g();
        }
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i2, int i3) {
        float f5 = this.F;
        float f6 = ((f2 + f4) - 8.0f) - f5;
        if (!GuiInput.a(f6, (f3 + 10.0f) - (h / 2.0f), f5, h, i2, i3)) {
            if (this.q) {
                this.q = false;
                this.u.a(0.0d, 0.2d, Easing.h);
                this.G = "";
            }
            return false;
        }
        a(this);
        if (!this.q) {
            this.q = true;
            this.u.a(1.0d, 0.2d, Easing.h);
            this.w.d(1.0d);
            this.A = false;
            this.w.a(0.3d, 0.5d, Easing.i);
        }
        FontRenderer fontRenderer = FontManager.a[14];
        this.r = fontRenderer.a(this.n, i2 - ((f6 + j) + fontRenderer.a(r())));
        H();
        u();
        return true;
    }

    @Override
    public boolean a(int i2, int i3, int i4) {
        if (!this.q) {
            return false;
        }
        int i5 = ((i4 ^ (-1)) | 2) - (i4 ^ (-1)) == 0 ? 0 : 1;
        int i6 = ((i4 ^ (-1)) | 1) - (i4 ^ (-1)) == 0 ? 0 : 1;
        if (i5 != 0) {
            switch (i2) {
                case 65:
                    C();
                    break;
                case 67:
                    D();
                    break;
                case 86:
                    E();
                    break;
                case 88:
                    F();
                    break;
            }
            return true;
        }
        switch (i2) {
            case 256:
                this.q = false;
                this.u.a(0.0d, 0.2d, Easing.h);
                this.G = "";
                H();
                break;
            case 257:
                this.q = false;
                this.u.a(0.0d, 0.2d, Easing.h);
                this.G = "";
                H();
                break;
            case 258:
                if (this.m == ValueType.PLAYER) {
                    w();
                }
                break;
            case 259:
                y();
                break;
            case 261:
                z();
                break;
            case 262:
                b(Bool.from(i6), Bool.from(i5));
                break;
            case 263:
                a(Bool.from(i6), Bool.from(i5));
                break;
            case 268:
                a(Bool.from(i6));
                break;
            case 269:
                b(Bool.from(i6));
                break;
        }
        return true;
    }

    @Override
    public boolean a(char c2, int i2) {
        if (this.q && !Character.isISOControl(c2)) {
            if (!a(c2)) {
                return true;
            }
            if (this.n.length() >= k && !G()) {
                return true;
            }
            if (G()) {
                I();
            }
            if (this.n.length() < k) {
                this.n = this.n.substring(0, this.r) + c2 + this.n.substring(this.r);
                int i3 = this.r;
                this.r = (i3 | 1) + (i3 & 1);
                if (this.m == ValueType.PRICE) {
                    int iX = x();
                    this.n = b(this.n.replace(",", ""));
                    this.r = a(iX);
                }
                J();
                u();
                t();
                q();
            }
            return true;
        }
        return false;
    }

    private int x() {
        return this.n.substring(0, this.r).replace(",", "").length();
    }

    private int a(int i2) {
        String strReplace = this.n.replace(",", "");
        if (i2 > strReplace.length()) {
            i2 = strReplace.length();
        }
        int i3 = 0;
        int i4 = 0;
        for (int i5 = 0; i5 < this.n.length() && i3 < i2; i5++) {
            if (this.n.charAt(i5) != ',') {
                i3++;
            }
            int i6 = i5;
            i4 = (i6 ^ 1) + (2 * (i6 & 1));
        }
        return i4;
    }

    private void y() {
        if (G()) {
            I();
        } else if (this.r > 0) {
            this.n = this.n.substring(0, this.r - 1) + this.n.substring(this.r);
            int i2 = this.r;
            this.r = (i2 ^ 1) - (2 * ((i2 ^ (-1)) & 1));
            if (this.m == ValueType.PRICE) {
                int iX = x();
                this.n = b(this.n.replace(",", ""));
                this.r = a(iX);
            }
        }
        J();
        u();
        t();
        q();
    }

    private void z() {
        if (G()) {
            I();
        } else if (this.r < this.n.length()) {
            this.n = this.n.substring(0, this.r) + this.n.substring((this.r - (-2)) - 1);
            if (this.m == ValueType.PRICE) {
                int iX = x();
                this.n = b(this.n.replace(",", ""));
                this.r = a(iX);
            }
        }
        J();
        u();
        t();
        q();
    }

    private void a(boolean z, boolean z2) {
        int iA;
        if (z2) {
            iA = A();
        } else {
            int i2 = this.r;
            iA = Math.max(0, (i2 ^ 1) - (2 * ((i2 ^ (-1)) & 1)));
        }
        if (z) {
            if (!G()) {
                this.s = this.r;
            }
            this.t = iA;
        } else {
            if (G()) {
                iA = Math.min(this.s, this.t);
            }
            H();
        }
        this.r = iA;
        J();
    }

    private void b(boolean z, boolean z2) {
        int iB;
        if (z2) {
            iB = B();
        } else {
            int length = this.n.length();
            int i2 = this.r;
            iB = Math.min(length, (2 * (i2 | 1)) - (i2 ^ 1));
        }
        if (z) {
            if (!G()) {
                this.s = this.r;
            }
            this.t = iB;
        } else {
            if (G()) {
                iB = Math.max(this.s, this.t);
            }
            H();
        }
        this.r = iB;
        J();
    }

    private void a(boolean z) {
        if (z) {
            if (!G()) {
                this.s = this.r;
            }
            this.t = 0;
        } else {
            H();
        }
        this.r = 0;
        J();
    }

    private void b(boolean z) {
        if (z) {
            if (!G()) {
                this.s = this.r;
            }
            this.t = this.n.length();
        } else {
            H();
        }
        this.r = this.n.length();
        J();
    }

    private int A() {
        if (this.r == 0) {
            return 0;
        }
        int i2 = this.r - 1;
        while (i2 > 0 && Character.isWhitespace(this.n.charAt(i2))) {
            i2--;
        }
        while (i2 > 0) {
            int i3 = i2;
            if (Character.isWhitespace(this.n.charAt((2 * (i3 & (-2))) - (i3 ^ 1)))) {
                break;
            }
            i2--;
        }
        return i2;
    }

    private int B() {
        if (this.r >= this.n.length()) {
            return this.n.length();
        }
        int i2 = this.r;
        while (i2 < this.n.length() && !Character.isWhitespace(this.n.charAt(i2))) {
            i2++;
        }
        while (i2 < this.n.length() && Character.isWhitespace(this.n.charAt(i2))) {
            i2++;
        }
        return i2;
    }

    private void C() {
        this.s = 0;
        this.t = this.n.length();
        this.r = this.n.length();
    }

    private void D() {
        if (G()) {
            MinecraftClient.getInstance().keyboard.setClipboard(this.n.substring(Math.min(this.s, this.t), Math.max(this.s, this.t)));
        }
    }

    private void E() {
        String strGetClipboard = MinecraftClient.getInstance().keyboard.getClipboard();
        if (strGetClipboard == null || strGetClipboard.isEmpty()) {
            return;
        }
        String strReplaceAll = strGetClipboard.replaceAll("[\\r\\n\\t]", "");
        StringBuilder sb = new StringBuilder();
        for (int i2 = 0; i2 < strReplaceAll.length(); i2++) {
            char cCharAt = strReplaceAll.charAt(i2);
            int i3 = this.r;
            int length = sb.length();
            if (b(cCharAt, (i3 & (length ^ (-1))) + (length & (i3 ^ (-1))) + (2 * (i3 & length)))) {
                sb.append(cCharAt);
            }
        }
        String string = sb.toString();
        if (G()) {
            I();
        }
        int length2 = k + (this.n.length() ^ (-1)) + 1;
        if (string.length() > length2) {
            string = string.substring(0, length2);
        }
        if (!string.isEmpty()) {
            this.n = this.n.substring(0, this.r) + string + this.n.substring(this.r);
            int i4 = this.r;
            int length3 = string.length();
            this.r = (i4 ^ length3) + (2 * (i4 & length3));
            if (this.m == ValueType.PRICE) {
                int iX = x();
                this.n = b(this.n.replace(",", ""));
                this.r = a(iX);
            }
        }
        J();
        u();
        t();
        q();
    }

    private void F() {
        if (G()) {
            D();
            I();
        }
    }

    private boolean G() {
        return (this.s == -1 || this.t == -1 || this.s == this.t) ? false : true;
    }

    private void H() {
        this.s = -1;
        this.t = -1;
    }

    private void I() {
        if (G()) {
            int iMin = Math.min(this.s, this.t);
            this.n = this.n.substring(0, iMin) + this.n.substring(Math.max(this.s, this.t));
            this.r = iMin;
            H();
            if (this.m == ValueType.PRICE) {
                int iX = x();
                this.n = b(this.n.replace(",", ""));
                this.r = a(iX);
            } else if (c) {
            }
            u();
            t();
            q();
        }
    }

    private void J() {
        this.w.d(1.0d);
        this.A = false;
        this.w.a(0.3d, 0.5d, Easing.i);
    }

    public boolean p() {
        if (this.m == ValueType.PLAYER) {
            return Bool.from(this.n.length() < 3 ? 0 : 1);
        }
        return true;
    }

    @Override
    public boolean d() {
        return Bool.from((this.p == null || this.p.m()) ? 1 : 0);
    }

    public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
        return null;
    }
}
