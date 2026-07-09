package drunvisual.gui.settings;

import java.awt.Color;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.settings.KeySetting;
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

public class KeyBindSettingWidget implements SettingWidget {
    public static final float a = 20.0f;
    private static final float d = 8.0f;
    private static final Color e = Theme.C;
    private static final Color f = Theme.D;
    private final String g;
    private int h;
    private boolean i;
    private final KeySetting j;
    private final AnimationState k;
    private final AnimationState l;
    private final AnimationState m;
    private final MarqueeText n;
    private boolean o;
    private boolean p;
    private float q;
    private float r;
    private float s;
    private float t;
    public static int b;
    public static boolean c;

    public KeyBindSettingWidget(KeySetting keySetting) {
        this.k = new AnimationState();
        this.l = new AnimationState();
        this.m = new AnimationState();
        this.n = new MarqueeText();
        this.o = false;
        this.p = true;
        this.j = keySetting;
        this.g = keySetting.f();
        this.h = keySetting.a();
        this.i = false;
    }

    public KeyBindSettingWidget(String str, int i) {
        this.k = new AnimationState();
        this.l = new AnimationState();
        this.m = new AnimationState();
        this.n = new MarqueeText();
        this.o = false;
        this.p = true;
        this.j = null;
        this.g = str;
        this.h = i;
        this.i = false;
    }

    @Override
    public String a() {
        return this.g;
    }

    @Override
    public float b() {
        return 20.0f;
    }

    public int c() {
        return this.h;
    }

    public void a(int i) {
        this.h = i;
        if (this.j != null) {
            this.j.a(i);
        }
    }

    public void e() {
        this.i = false;
        this.l.d(0.0d);
        this.m.d(1.0d);
        this.p = true;
        this.k.d(0.0d);
        this.o = false;
    }

    @Override
    public boolean a_() {
        return this.i;
    }

    private String f() {
        if (this.i) {
            return "|";
        }
        return this.h > 0 ? b(this.h) : "-";
    }

    private String b(int i) {
        switch (i) {
            case 32:
                return "SPACE";
            case 33:
            case 34:
            case 35:
            case 36:
            case 37:
            case 38:
            case 40:
            case 41:
            case 42:
            case 43:
            case 58:
            case 60:
            case 62:
            case 63:
            case 64:
            case 94:
            case 95:
            case 97:
            case 98:
            case 99:
            case EventPriority.HIGH /* 100 */:
            case 101:
            case 102:
            case 103:
            case 104:
            case 105:
            case 106:
            case 107:
            case 108:
            case 109:
            case 110:
            case 111:
            case 112:
            case 113:
            case 114:
            case 115:
            case 116:
            case 117:
            case 118:
            case 119:
            case 120:
            case 121:
            case 122:
            case 123:
            case 124:
            case 125:
            case 126:
            case 127:
            case 128:
            case 129:
            case 130:
            case 131:
            case 132:
            case 133:
            case 134:
            case 135:
            case 136:
            case 137:
            case 138:
            case 139:
            case 140:
            case 141:
            case 142:
            case 143:
            case 144:
            case 145:
            case 146:
            case 147:
            case 148:
            case 149:
            case 150:
            case 151:
            case 152:
            case 153:
            case 154:
            case 155:
            case 156:
            case 157:
            case 158:
            case 159:
            case 160:
            case 161:
            case 162:
            case 163:
            case 164:
            case 165:
            case 166:
            case 167:
            case 168:
            case 169:
            case 170:
            case 171:
            case 172:
            case 173:
            case 174:
            case 175:
            case 176:
            case 177:
            case 178:
            case 179:
            case 180:
            case 181:
            case 182:
            case 183:
            case 184:
            case 185:
            case 186:
            case 187:
            case 188:
            case 189:
            case 190:
            case 191:
            case 192:
            case 193:
            case 194:
            case 195:
            case 196:
            case 197:
            case 198:
            case 199:
            case EventPriority.HIGHEST /* 200 */:
            case 201:
            case 202:
            case 203:
            case 204:
            case 205:
            case 206:
            case 207:
            case 208:
            case 209:
            case 210:
            case 211:
            case 212:
            case 213:
            case 214:
            case 215:
            case 216:
            case 217:
            case 218:
            case 219:
            case 220:
            case 221:
            case 222:
            case 223:
            case 224:
            case 225:
            case 226:
            case 227:
            case 228:
            case 229:
            case 230:
            case 231:
            case 232:
            case 233:
            case 234:
            case 235:
            case 236:
            case 237:
            case 238:
            case 239:
            case 240:
            case 241:
            case 242:
            case 243:
            case 244:
            case 245:
            case 246:
            case 247:
            case 248:
            case 249:
            case 250:
            case 251:
            case 252:
            case 253:
            case 254:
            case 255:
            case 270:
            case 271:
            case 272:
            case 273:
            case 274:
            case 275:
            case 276:
            case 277:
            case 278:
            case 279:
            case 285:
            case 286:
            case 287:
            case 288:
            case 289:
            case 302:
            case 303:
            case 304:
            case 305:
            case 306:
            case 307:
            case 308:
            case 309:
            case 310:
            case 311:
            case 312:
            case 313:
            case 314:
            case 315:
            case 316:
            case 317:
            case 318:
            case 319:
            case 336:
            case 337:
            case 338:
            case 339:
            default:
                return "KEY" + i;
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
            case 48:
                return "0";
            case 49:
                return "1";
            case 50:
                return "2";
            case 51:
                return "3";
            case 52:
                return "4";
            case 53:
                return "5";
            case 54:
                return "6";
            case 55:
                return "7";
            case 56:
                return "8";
            case 57:
                return "9";
            case 59:
                return ";";
            case 61:
                return "=";
            case 65:
                return "A";
            case 66:
                return "B";
            case 67:
                return "C";
            case 68:
                return "D";
            case 69:
                return "E";
            case 70:
                return "F";
            case 71:
                return "G";
            case 72:
                return "H";
            case 73:
                return "I";
            case 74:
                return "J";
            case 75:
                return "K";
            case 76:
                return "L";
            case 77:
                return "M";
            case 78:
                return "N";
            case 79:
                return "O";
            case 80:
                return "P";
            case 81:
                return "Q";
            case 82:
                return "R";
            case 83:
                return "S";
            case 84:
                return "T";
            case 85:
                return "U";
            case 86:
                return "V";
            case 87:
                return "W";
            case 88:
                return "X";
            case 89:
                return "Y";
            case 90:
                return "Z";
            case 91:
                return "[";
            case 92:
                return "\\";
            case 93:
                return "]";
            case 96:
                return "`";
            case 256:
                return "ESC";
            case 257:
                return "ENTER";
            case 258:
                return "TAB";
            case 259:
                return "BACK";
            case 260:
                return "INS";
            case 261:
                return "DEL";
            case 262:
                return "RIGHT";
            case 263:
                return "LEFT";
            case 264:
                return "DOWN";
            case 265:
                return "UP";
            case 266:
                return "PGUP";
            case 267:
                return "PGDN";
            case 268:
                return "HOME";
            case 269:
                return "END";
            case 280:
                return "CAPS";
            case 281:
                return "SCROLL";
            case 282:
                return "NUM";
            case 283:
                return "PRINT";
            case 284:
                return "PAUSE";
            case 290:
                return "F1";
            case 291:
                return "F2";
            case 292:
                return "F3";
            case 293:
                return "F4";
            case 294:
                return "F5";
            case 295:
                return "F6";
            case 296:
                return "F7";
            case 297:
                return "F8";
            case 298:
                return "F9";
            case 299:
                return "F10";
            case 300:
                return "F11";
            case 301:
                return "F12";
            case 320:
                return "NUM0";
            case 321:
                return "NUM1";
            case 322:
                return "NUM2";
            case 323:
                return "NUM3";
            case 324:
                return "NUM4";
            case 325:
                return "NUM5";
            case 326:
                return "NUM6";
            case 327:
                return "NUM7";
            case 328:
                return "NUM8";
            case 329:
                return "NUM9";
            case 330:
                return "NUM.";
            case 331:
                return "NUM/";
            case 332:
                return "NUM*";
            case 333:
                return "NUM-";
            case 334:
                return "NUM+";
            case 335:
                return "NUMENTER";
            case 340:
                return "LSHIFT";
            case 341:
                return "LCTRL";
            case 342:
                return "LALT";
            case 343:
                return "LWIN";
            case 344:
                return "RSHIFT";
            case 345:
                return "RCTRL";
            case 346:
                return "RALT";
            case 347:
                return "RWIN";
            case 348:
                return "MENU";
        }
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, int i, int i2, float f5, float f6) {
        int i3;
        FontRenderer fontRenderer = FontManager.a[14];
        float f7 = 20.0f * f6;
        float f8 = 8.0f * f6;
        this.n.a(GuiInput.a(f2, f3, f4, f7, i, i2));
        this.l.a();
        this.k.a();
        if (this.i) {
            this.m.a();
            if (this.m.d()) {
                this.p = Bool.from(this.p ? 0 : 1);
                this.m.a(!this.p ? 0.3d : 1.0d, 0.5d, Easing.i);
            }
        } else {
            this.m.d(1.0d);
            this.p = true;
        }
        float fJ = (float) this.l.j();
        float fJ2 = (float) this.m.j();
        int i4 = (int) (255.0f * f5);
        String strF = f();
        float fA = fontRenderer.a(strF) * f6;
        float fB = fontRenderer.b(strF) * f6;
        float fMax = Math.max(20.0f * f6, fA + ((5.0f * f6) * 2.0f)) - 2.5f;
        float f9 = 14.0f * f6;
        float f10 = ((f2 + f4) - f8) - fMax;
        float f11 = (f10 - (f2 + f8)) - (4.0f * f6);
        float fB2 = (f3 + (f7 / 2.0f)) - ((fontRenderer.b(this.g) * f6) / 4.0f);
        this.n.a(MatrixStackVar, renderer2D, fontRenderer, this.g, f2 + f8, fB2, f11, f6, Theme.a, f5);
        float fB3 = ((fB2 + ((fontRenderer.b(this.g) * f6) / 2.0f)) - (f9 / 2.0f)) - (5.0f * f6);
        this.q = f10;
        this.r = fB3;
        this.s = fMax;
        this.t = f9;
        float f12 = 4.0f * f6;
        Color colorA = ColorUtils.a(Theme.m, e, fJ);
        Color colorA2 = ColorUtils.a(Theme.r, f, fJ);
        Color colorA3 = Theme.a(colorA, i4);
        Color colorA4 = Theme.a(colorA2, i4);
        renderer2D.a(f10 - (0.5f * f6), fB3 - (0.5f * f6), fMax + f6, f9 + f6, f12, colorA3, colorA3, colorA4, colorA4, MatrixStackVar);
        Color colorA5 = ColorUtils.a(Theme.e, Theme.y, fJ);
        Color colorA6 = ColorUtils.a(Theme.f, Theme.z, fJ);
        Color colorA7 = Theme.a(colorA5, i4);
        Color colorA8 = Theme.a(colorA6, i4);
        renderer2D.a(f10, fB3, fMax, f9, f12, colorA7, colorA7, colorA8, colorA8, MatrixStackVar);
        float f13 = f10 + (fMax / 2.0f);
        float f14 = fB3 + (f9 / 2.0f);
        float fRound = Math.round(f13 - (fA / 2.0f));
        float fRound2 = Math.round(f14 - (fB / 2.0f));
        if (this.i) {
            i3 = (int) (255.0f * f5 * fJ2);
        } else {
            i3 = i4;
        }
        Color colorA9 = Theme.a(Theme.a, i3);
        MatrixStackVar.push();
        MatrixStackVar.translate(fRound, fRound2, 0.0f);
        MatrixStackVar.scale(f6, f6, 1.0f);
        MatrixStackVar.translate(-fRound, -fRound2, 0.0f);
        fontRenderer.a(strF, fRound, fRound2 + 4.0f, colorA9, MatrixStackVar);
        MatrixStackVar.pop();
        boolean zA = GuiInput.a(f10, fB3, fMax, f9, i, i2);
        if (zA != this.o) {
            this.k.a(!zA ? 0.0d : 1.0d, 0.15d, Easing.h);
            this.o = zA;
        }
        if (zA || this.i) {
            GuiInput.g();
        }
    }

    @Override
    public boolean a(float f2, float f3, float f4, int i, int i2) {
        FontRenderer fontRenderer = FontManager.a[14];
        float fMax = Math.max(20.0f, fontRenderer.a(f()) + (5.0f * 2.0f));
        float f5 = ((f2 + f4) - 8.0f) - fMax;
        float fB = fontRenderer.b(this.g);
        if (!GuiInput.a(f5, ((((f3 + 10.0f) - (fB / 4.0f)) + (fB / 2.0f)) - (14.0f / 2.0f)) - 5.0f, fMax, 14.0f, i, i2)) {
            if (this.i) {
                this.i = false;
                this.l.a(0.0d, 0.2d, Easing.h);
            }
            return false;
        }
        this.i = Bool.from(this.i ? 0 : 1);
        this.l.a(!this.i ? 0.0d : 1.0d, 0.2d, Easing.h);
        if (this.i) {
            this.m.d(1.0d);
            this.p = false;
            this.m.a(0.3d, 0.5d, Easing.i);
        } else {
        }
        return true;
    }

    @Override
    public boolean a(int i, int i2, int i3) {
        if (!this.i) {
            return false;
        }
        this.i = false;
        this.l.a(0.0d, 0.2d, Easing.h);
        if (i == 256 || i == 261 || i == 259) {
            this.h = -1;
        } else {
            this.h = i;
        }
        if (this.j != null) {
            this.j.a(this.h);
        }
        return true;
    }

    @Override
    public boolean d() {
        int i;
        if (this.j == null || this.j.m()) {
            i = 1;
        } else {
            i = 0;
        }
        return Bool.from(i);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
