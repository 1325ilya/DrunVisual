package drunvisual.gui.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import org.json.JSONArray;
import org.json.JSONObject;
import drunvisual.auth.AccountServiceClient;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.widgets.ScrollBar;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;

public class EventsPanel {
    private static final float a = 6.0f;
    private static final float b = 3.0f;
    private static final float c = 3.0f;
    private static final float d = 2.0f;
    private final PanelFadeOverlay l;
    private float o;
    private float p;
    private float q;
    private float r;
    private Consumer<EventInfo> s;
    private final List<EventInfo> e = new ArrayList();
    private final List<EventInfo> f = new ArrayList();
    private final List<EventInfo> g = new ArrayList();
    private final List<EventCard> h = new ArrayList();
    private final List<EventCard> i = new ArrayList();
    private final List<EventCard> j = new ArrayList();
    private int m = 0;
    private String n = "";
    private final ScrollBar k = new ScrollBar(d, 20.0f);

    public EventsPanel() {
        this.k.b(10.0f);
        this.k.a(Theme.b);
        this.k.b(Theme.d);
        this.l = new PanelFadeOverlay(25, 5.0f, 9.0f);
    }

    public void a(Consumer<EventInfo> consumer) {
        this.s = consumer;
    }

    public void a(EventInfo eventInfo) {
        this.e.add(eventInfo);
        EventCard eventCard = new EventCard(eventInfo);
        eventCard.a(this::d);
        this.h.add(eventCard);
    }

    public void b(EventInfo eventInfo) {
        this.f.add(eventInfo);
        EventCard eventCard = new EventCard(eventInfo);
        eventCard.a(this::d);
        this.i.add(eventCard);
    }

    public void c(EventInfo eventInfo) {
        this.g.add(eventInfo);
        EventCard eventCard = new EventCard(eventInfo);
        eventCard.a(this::d);
        this.j.add(eventCard);
    }

    public void a() {
        this.e.clear();
        this.f.clear();
        this.g.clear();
        this.h.clear();
        this.i.clear();
        this.j.clear();
        this.k.e();
    }

    public void b() {
        a(this.e, this.h);
        a(this.f, this.i);
        a(this.g, this.j);
    }

    private void a(List<EventInfo> list, List<EventCard> list2) {
        if (list.size() > 1) {
            ArrayList<int[]> arrayList = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(new int[]{i, list.get(i).f()});
            }
            arrayList.sort((iArr, iArr2) -> {
                return Integer.compare(iArr[1], iArr2[1]);
            });
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            for (int[] iArr3 : arrayList) {
                arrayList2.add(list.get(iArr3[0]));
                arrayList3.add(list2.get(iArr3[0]));
            }
            list.clear();
            list.addAll(arrayList2);
            list2.clear();
            list2.addAll(arrayList3);
        }
    }

    public void a(int i, AccountServiceClient accountServiceClient) {
    }

    private void a(List<EventInfo> list, List<EventCard> list2, JSONArray jSONArray, int i) {
        int size = list.size();
        for (int i2 = (size ^ 1) - (2 * ((size ^ (-1)) & 1)); i2 >= 0; i2--) {
            EventInfo eventInfo = list.get(i2);
            int iA = a(jSONArray, eventInfo.b());
            if (iA > 0) {
                int i3 = (2 * (iA & (i ^ (-1)))) - (iA ^ i);
                if (i3 <= 0) {
                    list.remove(i2);
                    list2.remove(i2);
                } else {
                    eventInfo.a(i3);
                }
            }
        }
    }

    private int a(JSONArray jSONArray, int i) {
        for (int i2 = 0; i2 < jSONArray.length(); i2++) {
            try {
                JSONObject jSONObject = jSONArray.getJSONObject(i2);
                if (Integer.parseInt(jSONObject.getString("server").replaceAll("[^0-9]", "")) == i) {

                    return jSONObject.getInt("timeSeconds");
                }
            } catch (Exception e) {
            }
            return -1;
        }
        return -1;
    }

    private void d(EventInfo eventInfo) {
        if (this.s != null) {
            this.s.accept(eventInfo);
        }
    }

    public void a(int i) {
        if (this.m != i) {
            this.m = i;
            this.k.e();
        }
    }

    public void a(String str) {
        this.n = str.toLowerCase().trim();
        this.k.e();
    }

    private List<EventCard> e() {
        List<EventCard> list;
        switch (this.m) {
            case EventPriority.MEDIUM /* 0 */:
                list = this.h;
                break;
            case ConfigState.a /* 1 */:
                list = this.i;
                break;
            case 2:
                list = this.j;
                break;
            default:
                list = this.h;
                break;
        }
        List<EventCard> list2 = list;
        return this.n.isEmpty() ? new ArrayList(list2) : (List) list2.stream().filter(eventCard -> {
            EventInfo eventInfoA = eventCard.a();
            return Bool.from((eventInfoA.a().toLowerCase().contains(this.n) || String.valueOf(eventInfoA.b()).contains(this.n) || (eventInfoA.i() != null && eventInfoA.i().toLowerCase().contains(this.n)) || ((eventInfoA.d() != null && eventInfoA.d().a().toLowerCase().contains(this.n)) || (eventInfoA.e() != null && eventInfoA.e().a().toLowerCase().contains(this.n)))) ? 1 : 0);
        }).collect(Collectors.toList());
    }

    public boolean c() {
        switch (this.m) {
            case EventPriority.MEDIUM /* 0 */:
                return this.e.isEmpty();
            case ConfigState.a /* 1 */:
                return this.f.isEmpty();
            case 2:
                return this.g.isEmpty();
            default:
                return true;
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4, int i, int i2, float f5) {
        this.o = f;
        this.p = f2;
        this.q = f3;
        this.r = f4;
        this.k.a();
        List<EventCard> listE = e();
        if (listE.isEmpty()) {
            a(MatrixStackVar, renderer2D, f, f2, f3, f4);
            return;
        }
        float f6 = f2 + 3.0f;
        float f7 = (f4 - 3.0f) - 3.0f;
        float fA = a(listE);
        boolean z = fA > f7;
        if (!z) {
            this.k.e();
        }
        renderer2D.b().a(f, f2 + (4 / d), f3, f4 - (4 * 2), MatrixStackVar);
        float fB = f6 - this.k.b();
        float f8 = f + 1.0f;
        float f9 = f3 - d;
        for (EventCard eventCard : listE) {
            if (fB + 36.5f >= f6 && fB <= f6 + f7) {
                eventCard.a(MatrixStackVar, renderer2D, f8, fB, f9, i, i2, f5);
            }
            fB += 42.5f;
        }
        renderer2D.b().a(MatrixStackVar);
        if (z) {
            this.k.a(MatrixStackVar, renderer2D, f + f3 + 18.0f, f6, f7, fA, f7, i, i2, false);
        }
        this.l.a(MatrixStackVar, renderer2D, f, f2 + 1.5f, f3, f4, f5);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f, float f2, float f3, float f4) {
        String str;
        String str2;
        FontRenderer fontRenderer = FontManager.b[24];
        FontRenderer fontRenderer2 = FontManager.a[15];
        if (c()) {
            str = "На сервере пока-что нет ивентов :(";
            str2 = "Подожди немного, скоро начнутся";
        } else {
            str = "Ничего не найдено";
            str2 = "Попробуй изменить запрос";
        }
        float fA = fontRenderer.a(str);
        float fA2 = fontRenderer2.a(str2);
        float f5 = f + ((f3 - fA) / d);
        float f6 = (f2 + (f4 / d)) - 20.0f;
        float f7 = f + ((f3 - fA2) / d);
        fontRenderer.a(str, f5, f6, Theme.a, MatrixStackVar);
        fontRenderer2.a(str2, f7, f6 + 15.0f, Theme.b, MatrixStackVar);
    }

    private float a(List<EventCard> list) {
        if (list.isEmpty()) {
            return 0.0f;
        }
        return (list.size() * 42.5f) - a;
    }

    public boolean a(float f, float f2, float f3, float f4, int i, int i2) {
        List<EventCard> listE = e();
        if (listE.isEmpty()) {
            return false;
        }
        float f5 = f2 + 3.0f;
        float f6 = (f4 - 3.0f) - 3.0f;
        float fA = a(listE);
        if (fA > f6) {
            if (this.k.a(f + f3 + 18.0f, f5, f6, fA, f6, i, i2)) {
                return true;
            }
        }
        if (GuiInput.a(f, f5, f3, f6, i, i2)) {
            float fB = f5 - this.k.b();
            float f7 = f + 1.0f;
            float f8 = f3 - d;
            Iterator<EventCard> it = listE.iterator();
            while (it.hasNext()) {
                if (it.next().a(f7, fB, f8, i, i2)) {
                    return true;
                }
                fB += 42.5f;
            }
        }
        return false;
    }

    public void a(int i, int i2) {
        this.k.d();
    }

    public void a(int i, int i2, double d2, double d3) {
        if (this.k.c()) {
            List<EventCard> listE = e();
            float f = (this.r - 3.0f) - 3.0f;
            this.k.a(i2, a(listE), f);
        }
    }

    public void a(float f, int i, int i2) {
        if (GuiInput.a(this.o, this.p, this.q, this.r, i, i2)) {
            List<EventCard> listE = e();
            this.k.a(f, a(listE), (this.r - 3.0f) - 3.0f);
        }
    }

    public void d() {
        this.e.clear();
        this.f.clear();
        this.g.clear();
        this.h.clear();
        this.i.clear();
        this.j.clear();
        this.k.e();
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
