package drunvisual.modules.utilities;

import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.KeySetting;
import drunvisual.settings.SliderSetting;
import drunvisual.core.Bool;
import drunvisual.events.KeyInputEvent;
import drunvisual.events.MouseScrollEvent;
import drunvisual.events.WorldRenderEndEvent;

@ModuleInfo(a = "Zoom", b = "Плавный зум с регулировкой колесиком мыши", c = ModuleCategory.UTILITIES)
public class Zoom extends ClientModule {
    private final KeySetting g = new KeySetting("Кнопка зума", 67);
    private final SliderSetting h = new SliderSetting("Стартовый FOV", 50.0f, 2.0f, 120.0f, 1.0f);
    private double i = 70.0d;
    private double j = 70.0d;
    private double k = 70.0d;
    private double l = 0.5d;
    private boolean m = false;
    private boolean n = false;
    private int o = 0;
    public static int e;
    public static boolean f;
    public static volatile double b = 70.0d;
    public static volatile boolean a = false;

    @Override
    public void e() {
        super.e();
        if (c.options != null) {
            this.i = ((Integer) c.options.getFov().getValue()).intValue();
            this.j = this.i;
            this.k = this.i;
            this.l = ((Double) c.options.getMouseSensitivity().getValue()).doubleValue();
        }
    }

    @Override
    public void f() {
        p();
        super.f();
    }

    private void n() {
        if (c.options == null || c.player == null || c.world == null) {
            return;
        }
        if (!this.n) {
            this.i = ((Integer) c.options.getFov().getValue()).intValue();
            this.l = ((Double) c.options.getMouseSensitivity().getValue()).doubleValue();
            this.n = true;
        }
        this.j = Math.min(this.i, Math.max(2.0d, this.h.a()));
        this.k = ((Integer) c.options.getFov().getValue()).intValue();
        this.o = c.player.getInventory().selectedSlot;
        this.m = true;
    }

    private void o() {
        if (c.options != null) {
            this.m = false;
            this.j = this.i;
            c.options.getMouseSensitivity().setValue(Double.valueOf(this.l));
            c.options.smoothCameraEnabled = false;
        }
    }

    private void p() {
        if (c.options != null) {
            c.options.getMouseSensitivity().setValue(Double.valueOf(this.l));
            c.options.smoothCameraEnabled = false;
        }
        this.m = false;
        this.n = false;
        a = false;
        this.j = this.i;
        this.k = this.i;
    }

    @EventHandler
    public void a(WorldRenderEndEvent worldRenderEndEvent) {
        if (c.options != null) {
            if (this.m && c.currentScreen != null) {
                o();
                return;
            }
            if (!this.m && b(this.k, this.j, 0.2d)) {
                double dIntValue = ((Integer) c.options.getFov().getValue()).intValue();
                this.i = dIntValue;
                this.j = dIntValue;
                this.k = dIntValue;
                this.l = ((Double) c.options.getMouseSensitivity().getValue()).doubleValue();
                this.n = false;
            }
            if (b(this.k, this.j, 0.1d)) {
                this.k = this.j;
            } else {
                this.k += (this.j - this.k) * (24.0d / Math.max(1.0d, q()));
                if (b(this.k, this.j, 0.2d)) {
                    this.k = this.j;
                }
            }
            a = this.n;
            b = this.k;
            if (this.m) {
                if (c.player != null) {
                    c.player.getInventory().selectedSlot = this.o;
                }
                c.options.getMouseSensitivity().setValue(Double.valueOf(Math.min(this.l, Math.max(this.l * (this.i > 2.0d ? this.j / this.i : 1.0d), 0.0d))));
            }
        }
    }

    @EventHandler
    public void a(KeyInputEvent keyInputEvent) {
        if (k()) {
            if (this.g.b() && keyInputEvent.a() == this.g.a() && c.currentScreen == null) {
                if (keyInputEvent.c() == 1) {
                    n();
                } else if (keyInputEvent.c() == 0) {
                    o();
                }
            }
        }
    }

    @EventHandler
    public void a(MouseScrollEvent mouseScrollEvent) {
        if (k() && this.m && c.currentScreen == null) {
            mouseScrollEvent.a(true);
            double d = (-mouseScrollEvent.e()) * 20.0d;
            if (this.i > 2.0d) {
                d *= (this.j - 2.0d) / (this.i - 2.0d);
            }
            this.j = a(this.j + d, 2.0d, this.i);
        }
    }

    private int q() {
        String str = c.fpsDebugString;
        if (str == null || str.isEmpty()) {
            return 60;
        }
        String[] strArrSplit = str.split(" ");
        if (strArrSplit.length == 0) {
            return 60;
        }
        String strSubstring = strArrSplit[0];
        int iLastIndexOf = strSubstring.lastIndexOf(47);
        if (iLastIndexOf != -1) {
            strSubstring = strSubstring.substring(0, iLastIndexOf);
        }
        try {
            return Integer.parseInt(strSubstring);
        } catch (NumberFormatException e2) {
            return 60;
        }
    }

    private double a(double d, double d2, double d3) {
        return d >= d2 ? d <= d3 ? d : d3 : d2;
    }

    private boolean b(double d, double d2, double d3) {
        return Bool.from(Math.abs(d - d2) > d3 ? 0 : 1);
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
