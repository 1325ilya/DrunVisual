package drunvisual.gui.config;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.config.CloudConfigRepository;
import drunvisual.gui.config.ConfigActionMenu;
import drunvisual.config.ConfigEntry;
import drunvisual.config.ConfigManager;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.widgets.ScrollBar;
import drunvisual.gui.widgets.SearchBox;
import drunvisual.media.chat.ChatMessages;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.theme.Theme;

public class ConfigListPanel {
    private static final float c = 6.0f;
    private static final float d = -5.0f;
    private static final float e = 4.0f;
    private static final float f = 2.0f;
    private final ScrollBar i;
    private final PanelFadeOverlay j;
    private final SearchBox k;
    private final ConfigActionMenu l;
    private final ConfigNameDialog m;
    private final ConfigBoundsDialog n;
    private final ConfigTextDialog o;
    private Consumer<ConfigEntry> r;
    private Consumer<ConfigEntry> s;
    private RenameCallback t;
    private Consumer<ConfigEntry> u;
    private float v;
    private float w;
    private float x;
    private float y;
    private ConfigEntry z;
    public static int a;
    public static boolean b;
    private final List<ConfigEntry> g = new ArrayList();
    private final List<ConfigCard> h = new ArrayList();
    private String p = "";
    private List<ConfigCard> q = new ArrayList();

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$drunvisual$ConfigActionMenu$Action = new int[ConfigActionMenu.Action.values().length];

        static {
            try {
                $SwitchMap$drunvisual$ConfigActionMenu$Action[ConfigActionMenu.Action.SAVE_TO.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$drunvisual$ConfigActionMenu$Action[ConfigActionMenu.Action.SHARE.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$drunvisual$ConfigActionMenu$Action[ConfigActionMenu.Action.RENAME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$drunvisual$ConfigActionMenu$Action[ConfigActionMenu.Action.DELETE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    @FunctionalInterface
    public interface RenameCallback {
        void onRenamed(ConfigEntry configEntry, String str, String str2);

        static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public ConfigListPanel(SearchBox searchBox) {
        this.k = searchBox;
        this.k.a(this::d);
        this.i = new ScrollBar(f, 20.0f);
        this.i.b(10.0f);
        this.i.a(Theme.b);
        this.i.b(Theme.d);
        this.j = new PanelFadeOverlay(25, 5.0f, 9.0f);
        this.l = new ConfigActionMenu();
        this.m = new ConfigNameDialog();
        this.n = new ConfigBoundsDialog();
        this.o = new ConfigTextDialog();
        i();
    }

    private void i() {
        this.l.a((ConfigActionMenu.Action action) -> a(action));
        this.l.a(() -> {
            this.z = null;
        });
        this.m.a(str -> {
            ConfigManager configManagerA = ConfigManager.a();
            if (configManagerA.i()) {
                configManagerA.c(str, () -> {
                    ChatMessages.a((Object) "Конфиг импортирован!");
                    j();
                }, errStr -> {
                    ChatMessages.a((Object) a(errStr));
                });
            } else {
                ChatMessages.a((Object) "Конфиги ещё загружаются...");
            }
        });
        this.n.a((num, num2) -> {
            ConfigManager configManagerA = ConfigManager.a();
            if (!configManagerA.i()) {
                ChatMessages.a((Object) "Конфиги ещё загружаются...");
                return;
            }
            ConfigEntry configEntryD = this.n.d();
            if (configEntryD != null) {
                configManagerA.a(configEntryD.a(), num.intValue(), (Integer) null, num2, str2 -> {
                    this.o.a(str2, configEntryD);
                }, str3 -> {
                    ChatMessages.a((Object) b(str3));
                });
            }
        });
    }

    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    private String a(String str) {
        if (str == null) {
            return "Неизвестная ошибка";
        }
        switch (str) {
            case "KEY_NOT_FOUND":
                return "Ключ не найден. Проверьте правильность ввода.";
            case "KEY_EXPIRED":
                return "Срок действия ключа истёк.";
            case "KEY_EXHAUSTED":
                return "Ключ уже использован максимальное количество раз.";
            case "key is required":
                return "Введите ключ для активации.";
            case "Internal error":
                return "Внутренняя ошибка сервера. Попробуйте позже.";
            case "Not connected":
                return "Нет подключения к серверу.";
            default:
                return "Ошибка активации: " + str;
        }
    }

    private String b(String str) {
        if (str == null) {
            return "Неизвестная ошибка";
        }
        byte b2 = -1;
        switch (str.hashCode()) {
            case -2026653947:
                break;
            case -1926714738:
                break;
            case -1557786393:
                break;
            case -1038596745:
                break;
            case -532953636:
                break;
            case -347113380:
                break;
            case -288711231:
                break;
            case 782165529:
                break;
            case 1266212642:
                break;
            case 1333715397:
                break;
        }
        switch (b2) {
            case EventPriority.MEDIUM /* 0 */:
                return "Достигнут лимит ключей (максимум 20). Удалите неиспользуемые ключи.";
            case ConfigState.a /* 1 */:
                return "Невозможно создать столько ключей. Уменьшите количество или удалите старые ключи.";
            case 2:
                return "Конфиг не найден. Сначала сохраните конфиг.";
            case 3:
                return "Не указано имя конфига.";
            case 4:
                return "Данные конфига отсутствуют. Сначала сохраните конфиг.";
            case 5:
                return "Внутренняя ошибка сервера. Попробуйте позже.";
            case 6:
                return "Нет подключения к серверу.";
            case 7:
                return "Система ещё не инициализирована. Подождите.";
            case 8:
            case 9:
                return "Сервер не вернул ключ. Попробуйте ещё раз.";
            default:
                return "Ошибка создания ключа: " + str;
        }
    }

    private void j() {
        ConfigManager configManagerA = ConfigManager.a();
        if (configManagerA.i()) {
            b();
            CloudConfigRepository cloudConfigRepositoryJ = configManagerA.j();
            if (cloudConfigRepositoryJ != null) {
                String strG = configManagerA.g();
                for (CloudConfigRepository.RemoteConfigRecord remoteConfigRecord : cloudConfigRepositoryJ.f()) {
                    ConfigEntry configEntry = new ConfigEntry(remoteConfigRecord.b, remoteConfigRecord.c, LocalDateTime.ofInstant(Instant.ofEpochMilli(remoteConfigRecord.g), ZoneId.systemDefault()));
                    configEntry.a(remoteConfigRecord.b.equals(strG));
                    a(configEntry);
                }
            }
        }
    }

    private void a(ConfigActionMenu.Action action) {
        if (this.z != null) {
            boolean zE = this.z.e();
            switch (AnonymousClass1.$SwitchMap$drunvisual$ConfigActionMenu$Action[action.ordinal()]) {
                case ConfigState.a /* 1 */:
                    if (this.z.f()) {
                        d(this.z);
                    }
                    break;
                case 2:
                    this.n.a(this.z);
                    break;
                case 3:
                    if (!zE) {
                        for (ConfigCard configCard : this.h) {
                            if (configCard.a() == this.z) {
                                configCard.b();
                                break;
                            }
                        }
                        break;
                    }
                    break;
                case 4:
                    if (!zE) {
                        b(this.z);
                        break;
                    }
                    break;
            }
        }
    }

    public void a(ConfigEntry configEntry) {
        ConfigCard configCard = new ConfigCard(configEntry);
        if (configEntry.f()) {
            configCard.c(this::d);
            this.g.add(configEntry);
            this.h.add(configCard);
        } else {
            configCard.a((configEntry2, fArr) -> {
                this.z = configEntry2;
                this.l.a(fArr[0].floatValue(), fArr[1].floatValue(), fArr[2].floatValue(), configEntry2);
            });
            configCard.b(this::c);
            configCard.b((str, str2) -> {
                if (this.g.stream().anyMatch(configEntry3 -> {
                    return Bool.from((configEntry3 == configEntry || !configEntry3.a().equalsIgnoreCase(str2)) ? 0 : 1);
                })) {
                    configEntry.a(str);
                } else if (this.t != null) {
                    this.t.onRenamed(configEntry, str, str2);
                }
            });
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= this.g.size()) {
                    break;
                }
                if (this.g.get(i2).f()) {
                    i = i2;
                    break;
                } else {
                    i = (i2 - (-2)) - 1;
                    i2++;
                }
            }
            this.g.add(i, configEntry);
            this.h.add(i, configCard);
        }
        k();
    }

    private void c(ConfigEntry configEntry) {
        if (configEntry.f()) {
            return;
        }
        Iterator<ConfigEntry> it = this.g.iterator();
        while (it.hasNext()) {
            it.next().a(false);
        }
        configEntry.a(true);
        if (this.r != null) {
            this.r.accept(configEntry);
        }
    }

    public void b(ConfigEntry configEntry) {
        int iIndexOf = this.g.indexOf(configEntry);
        if (iIndexOf >= 0) {
            this.g.remove(iIndexOf);
            this.h.remove(iIndexOf);
            k();
            float f2 = (this.y - d) - e;
            float fL = l();
            if (fL > f2) {
                this.i.b(fL, f2);
            } else {
                this.i.e();
            }
            if (this.s != null) {
                this.s.accept(configEntry);
            }
        }
    }

    private void d(ConfigEntry configEntry) {
        if (configEntry.f()) {
            ConfigManager configManagerA = ConfigManager.a();
            if (configManagerA.i()) {
                configManagerA.a(configEntry.g(), configState -> {
                    configManagerA.a(configState);
                    configManagerA.e();
                    ChatMessages.a((Object) ("Премиум конфиг \"" + configEntry.a() + "\" применён к активному конфигу."));
                }, str -> {
                    ChatMessages.a((Object) c(str));
                });
            } else {
                ChatMessages.a((Object) "Сервис конфигов ещё не инициализирован.");
            }
        }
    }

    private String c(String str) {
        if (str == null) {
            return "Не удалось применить премиум конфиг.";
        }
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1557786393:
                break;
            case -1110739040:
                break;
            case -916893481:
                break;
            case -532953636:
                break;
            case 280577614:
                break;
            case 1023286998:
                break;
        }
        switch (b2) {
            case EventPriority.MEDIUM /* 0 */:
                return "Нет соединения с сервером. Проверьте интернет-подключение.";
            case ConfigState.a /* 1 */:
                return "Сервис конфигов ещё не инициализирован. Попробуйте позже.";
            case 2:
                return "Премиум конфиг не найден.";
            case 3:
                return "Вы не приобрели этот конфиг.";
            case 4:
                return "Не удалось применить конфиг. Попробуйте позже.";
            case 5:
                return "Сервер не отвечает. Попробуйте позже.";
            default:
                return (str.contains("timeout") || str.contains("Timeout")) ? "Превышено время ожидания. Проверьте интернет-подключение." : "Не удалось применить премиум конфиг: " + str;
        }
    }

    public List<ConfigEntry> a() {
        return this.g;
    }

    public void b() {
        this.g.clear();
        this.h.clear();
        this.q.clear();
        this.i.e();
    }

    public void c() {
        for (int size = this.g.size() - 1; size >= 0; size--) {
            if (this.g.get(size).f()) {
                this.g.remove(size);
                this.h.remove(size);
            } else {
            }
        }
        k();
    }

    public void a(Consumer<ConfigEntry> consumer) {
        this.r = consumer;
    }

    public void b(Consumer<ConfigEntry> consumer) {
        this.s = consumer;
    }

    public void a(RenameCallback renameCallback) {
        this.t = renameCallback;
    }

    public void c(Consumer<ConfigEntry> consumer) {
        this.u = consumer;
    }

    private void d(String str) {
        this.p = str.toLowerCase().trim();
        k();
        this.i.e();
    }

    private void k() {
        if (this.p.isEmpty()) {
            this.q = new ArrayList(this.h);
        } else {
            this.q = (List) this.h.stream().filter(configCard -> {
                String owner = configCard.a().b();
                return Bool.from((configCard.a().a().toLowerCase().contains(this.p) || (owner != null && owner.toLowerCase().contains(this.p))) ? 1 : 0);
            }).collect(Collectors.toList());
        }
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, int i, int i2, float f6) {
        this.v = f2;
        this.w = f3;
        this.x = f4;
        this.y = f5;
        this.i.a();
        if (this.q.isEmpty()) {
            a(MatrixStackVar, renderer2D, f2, f3, f4, f5);
            return;
        }
        float f7 = f3 + d;
        float f8 = (f5 - d) - e;
        float fL = l();
        boolean z = fL > f8;
        if (!z) {
            this.i.e();
        }
        renderer2D.b().a(f2, (f3 + 4) - c, f4, (f5 - (4 * 2)) + c, MatrixStackVar);
        float fB = f7 - this.i.b();
        for (ConfigCard configCard : this.h) {
            float f9 = fB + e;
            if (f9 + 36.5f >= f7 && f9 <= f7 + f8) {
                configCard.a(MatrixStackVar, renderer2D, f2 + 1.0f, f9, f4 - f, i, i2, f6);
            }
            fB += 42.5f;
        }
        renderer2D.b().a(MatrixStackVar);
        if (z) {
            this.i.a(MatrixStackVar, renderer2D, f2 + f4 + 18.0f, f7, f8, fL, f8, i, i2, false);
        } else if (b) {
        }
        this.j.a(MatrixStackVar, renderer2D, f2, f3 + 1.5f, f4, f5, f6);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i, int i2) {
        this.l.a(MatrixStackVar, renderer2D, i, i2);
        this.m.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
        this.n.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
        this.o.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5) {
        FontRenderer fontRenderer = FontManager.b[14];
        String strEmpty = !this.g.isEmpty() ? "Ничего не найдено" : "У тебя пока-что нету конфигов :(";
        fontRenderer.a(strEmpty, f2 + ((f4 - fontRenderer.a(strEmpty)) / f), (f3 + (f5 / f)) - (fontRenderer.b(strEmpty) / f), Theme.b, MatrixStackVar);
    }

    private float l() {
        if (this.q.isEmpty()) {
            return 0.0f;
        }
        return (this.q.size() * 42.5f) - c;
    }

    public boolean a(float f2, float f3, float f4, float f5, int i, int i2) {
        if (this.o.b()) {
            return this.o.a(this.x + 100.0f, this.y + 100.0f, i, i2);
        }
        if (this.m.c()) {
            return this.m.a(this.x + 100.0f, this.y + 100.0f, i, i2);
        }
        if (this.n.b()) {
            return this.n.a(this.x + 100.0f, this.y + 100.0f, i, i2);
        }
        if (this.l.b() && this.l.a(i, i2)) {
            return true;
        }
        float f6 = f3 + d;
        float f7 = (f5 - d) - e;
        float fL = l();
        if (fL > f7) {
            if (this.i.a(f2 + f4 + 18.0f, f6, f7, fL, f7, i, i2)) {
                return true;
            }
        }
        if (GuiInput.a(f2, f3, f4, f5, i, i2)) {
            float fB = f6 - this.i.b();
            Iterator<ConfigCard> it = this.h.iterator();
            while (it.hasNext()) {
                if (it.next().a(f2 + 1.0f, fB + e, f4 - f, i, i2)) {
                    return true;
                }
                fB += 42.5f;
            }
        }
        return false;
    }

    public void a(int i, int i2) {
        this.i.d();
        if (this.o.b()) {
            this.o.a(i, i2);
        }
    }

    public void a(int i, int i2, double d2, double d3) {
        if (this.o.b()) {
            this.o.a(i, i2, d2, d3);
        } else if (this.i.c()) {
            float f2 = (this.y - d) - e;
            this.i.a(i2, l(), f2);
        }
    }

    public void a(float f2, int i, int i2) {
        if (this.o.b()) {
            this.o.a(f2, i, i2);
            return;
        }
        if (this.l.b()) {
            return;
        }
        if (this.m.c() || this.n.b() || m() || !GuiInput.a(this.v, this.w, this.x, this.y, i, i2)) {
            return;
        }
        this.i.a(f2, l(), (this.y - d) - e);
    }

    public boolean a(int i, int i2, int i3) {
        for (ConfigCard configCard : this.h) {
            if (configCard.d()) {
                return configCard.a(i, i2, i3);
            }
        }
        if (this.o.b()) {
            return this.o.a(i, i2, i3);
        }
        if (this.m.c()) {
            return this.m.a(i, i2, i3);
        }
        if (this.n.b()) {
            return this.n.a(i, i2, i3);
        }
        return false;
    }

    public boolean a(char c2, int i) {
        for (ConfigCard configCard : this.h) {
            if (configCard.d()) {
                return configCard.a(c2, i);
            }
        }
        return !this.o.b() ? !this.m.c() ? !this.n.b() ? false : this.n.a(c2, i) : this.m.a(c2, i) : this.o.a(c2, i);
    }

    public boolean d() {
        return Bool.from((this.m.e() || this.n.e() || this.o.d() || m()) ? 1 : 0);
    }

    public boolean e() {
        return Bool.from((this.l.b() || this.m.c() || this.n.b() || this.o.b()) ? 1 : 0);
    }

    private boolean m() {
        Iterator<ConfigCard> it = this.h.iterator();
        while (it.hasNext()) {
            if (it.next().d()) {
                return true;
            }
        }
        return false;
    }

    public boolean f() {
        return this.g.isEmpty();
    }

    public void g() {
        this.m.a();
    }

    public void h() {
        if (this.l.b()) {
            this.l.a();
        }
        if (this.m.c()) {
            this.m.b();
        }
        if (this.n.b()) {
            this.n.a();
        } else {
        }
        if (this.o.b()) {
            this.o.a();
        }
        for (ConfigCard configCard : this.h) {
            if (configCard.d()) {
                configCard.a(false);
            } else if (b) {
            }
        }
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
