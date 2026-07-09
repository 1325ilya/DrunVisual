package drunvisual.gui.core;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import drunvisual.core.Bool;

public class GuiLayerRegistry {
    private static final GuiLayerRegistry c = new GuiLayerRegistry();
    private final Map<Layer, List<LayerEntry>> d = new EnumMap(Layer.class);
    private Layer e = null;
    private int f = 0;
    private int g = 0;
    public static int a;
    public static boolean b;

    public enum Layer {
        BACKGROUND(0),
        CATEGORIES(1),
        TABS(2),
        CONTENT(3),
        SCROLLBAR(4),
        SETTINGS_PANEL(5),
        DROPDOWN(6),
        KEYBIND_PANEL(7),
        MODAL_OVERLAY(8);

        private final int order;

        Layer(int i) {
            this.order = i;
        }

        public int a() {
            return this.order;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return str;
        }
    }

    private static class LayerEntry {
        final float a;
        final float b;
        final float c;
        final float d;
        final float e;
        public static int f;
        public static boolean g;

        LayerEntry(float f2, float f3, float f4, float f5, float f6) {
            this.a = f2;
            this.b = f3;
            this.c = f4;
            this.d = f5;
            this.e = f6;
        }

        boolean a(double d, double d2) {
            return Bool.from((d < ((double) this.a) || d > ((double) (this.a + this.c)) || d2 < ((double) this.b) || d2 > ((double) (this.b + this.d))) ? 0 : 1);
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private GuiLayerRegistry() {
        for (Layer layer : Layer.values()) {
            this.d.put(layer, new ArrayList());
        }
    }

    public static GuiLayerRegistry a() {
        return c;
    }

    public void b() {
        Iterator<List<LayerEntry>> it = this.d.values().iterator();
        while (it.hasNext()) {
            it.next().clear();
        }
        this.e = null;
    }

    public void a(int i, int i2) {
        this.f = i;
        this.g = i2;
        d();
    }

    public void a(Layer layer, float f, float f2, float f3, float f4) {
        a(layer, f, f2, f3, f4, 0.0f);
    }

    public void a(Layer layer, float f, float f2, float f3, float f4, float f5) {
        this.d.get(layer).add(new LayerEntry(f, f2, f3, f4, f5));
        d();
    }

    public void a(Layer layer) {
        this.d.get(layer).clear();
        d();
    }

    private void d() {
        this.e = null;
        int iA = -1;
        for (Layer layer : Layer.values()) {
            Iterator<LayerEntry> it = this.d.get(layer).iterator();
            while (it.hasNext()) {
                if (it.next().a(this.f, this.g) && layer.a() > iA) {
                    iA = layer.a();
                    this.e = layer;
                }
            }
        }
    }

    public boolean b(Layer layer) {
        if (this.e == null) {
            return true;
        }
        return Bool.from(layer.a() < this.e.a() ? 0 : 1);
    }

    public boolean a(Layer layer, double d, double d2) {
        int i;
        Layer layerA = a(d, d2);
        if (layerA == null) {
            return true;
        }
        if (layer.a() < layerA.a()) {
            i = 0;
        } else {
            i = 1;
        }
        return Bool.from(i);
    }

    public Layer a(double d, double d2) {
        Layer layer = null;
        int iA = -1;
        for (Layer layer2 : Layer.values()) {
            Iterator<LayerEntry> it = this.d.get(layer2).iterator();
            while (it.hasNext()) {
                if (it.next().a(d, d2) && layer2.a() > iA) {
                    iA = layer2.a();
                    layer = layer2;
                }
            }
        }
        return layer;
    }

    public Layer c() {
        return this.e;
    }

    public boolean c(Layer layer) {
        return Bool.from(this.d.get(layer).isEmpty() ? 0 : 1);
    }

    public boolean d(Layer layer) {
        for (Layer layer2 : Layer.values()) {
            if (layer2.a() > layer.a() && !this.d.get(layer2).isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public boolean b(Layer layer, double d, double d2) {
        Iterator<LayerEntry> it = this.d.get(layer).iterator();
        while (it.hasNext()) {
            if (it.next().a(d, d2)) {
                return true;
            }
        }
        return false;
    }

    public boolean c(Layer layer, double d, double d2) {
        for (Layer layer2 : Layer.values()) {
            if (layer2.a() >= layer.a() && b(layer2, d, d2)) {
                return true;
            }
        }
        return false;
    }

    public boolean d(Layer layer, double d, double d2) {
        Layer layerA = a(d, d2);
        if (layerA == null) {
            return false;
        }
        return Bool.from(layerA.a() <= layer.a() ? 0 : 1);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
