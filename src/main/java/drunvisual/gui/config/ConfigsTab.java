package drunvisual.gui.config;

import java.awt.Color;
import java.lang.reflect.Method;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.gui.config.ConfigListPanel;
import drunvisual.client.MinecraftContext;
import drunvisual.config.LocalConfigManager;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigEntry;
import drunvisual.core.Bool;
import drunvisual.gui.core.ClickGuiTab;
import drunvisual.gui.core.ClickGuiTabType;
import drunvisual.gui.core.GuiInput;
import drunvisual.gui.core.GuiInteractionState;
import drunvisual.gui.core.PanelFadeOverlay;
import drunvisual.gui.core.DrunVisualClickGuiScreen;
import drunvisual.gui.core.TabHost;
import drunvisual.gui.core.TabSelector;
import drunvisual.gui.widgets.SearchBox;
import drunvisual.media.chat.ChatMessages;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.icons.IconGlyphs;
import drunvisual.render.icons.IconTextureRegistry;
import drunvisual.theme.Theme;
import drunvisual.util.ColorUtils;

public class ConfigsTab implements TabHost, ClickGuiTab {
    private static final String[] a = {"Конфигурации"};
    private static final float b = 19.0f;
    private static final float c = 48.0f;
    private static final float d = 117.5f;
    private static final float e = 15.0f;
    private static final float f = 8.0f;
    private static final float g = 8.0f;
    private int i;
    private int j;
    private int h = 0;
    private final AnimationState o = new AnimationState();
    private final AnimationState p = new AnimationState();
    private final AnimationState q = new AnimationState();
    private final AnimationState backdrop = new AnimationState();
    private boolean r = false;
    private boolean s = false;
    private boolean t = false;
    private final TabSelector k = new TabSelector(this);
    private final SearchBox l = new SearchBox(d, e);
    private final ConfigListPanel m = new ConfigListPanel(this.l);
    private final PanelFadeOverlay n = new PanelFadeOverlay(25, 10.0f, 7.5f);

    public ConfigsTab() {
        j();
    }

    private void h() {
        try {
            LocalConfigManager.get();
            Method declaredMethod = ConfigListPanel.class.getDeclaredMethod("j", new Class[0]);
            declaredMethod.setAccessible(true);
            declaredMethod.invoke(this.m, new Object[0]);
        } catch (Throwable th) {
        }
        try {
            LocalConfigManager localConfigManager = LocalConfigManager.get();
            for (ConfigEntry configEntry : localConfigManager.toConfigEntries()) {
                configEntry.a(configEntry.a().equals(localConfigManager.activeProfile()));
                this.m.a(configEntry);
            }
        } catch (Throwable th2) {
        }
    }

    public void e() {
        h();
    }

    private void i() {
    }

    private void j() {
        this.m.a(configEntry -> {
            LocalConfigManager.get().loadProfile(configEntry.a(), () -> {
                for (ConfigEntry configEntry2 : this.m.a()) {
                    configEntry2.a(configEntry2.a().equals(configEntry.a()));
                }
            }, str -> {
                ChatMessages.a("§cОшибка загрузки: " + str);
            });
        });
        this.m.b(configEntry2 -> {
            LocalConfigManager.get().deleteProfile(configEntry2.a(), () -> {
                this.e();
            }, str -> {
                ChatMessages.a("§cОшибка удаления: " + str);
            });
        });
        this.m.a(proxy_$3(this));
        this.m.c(configEntry3 -> {
            ChatMessages.a(this.d(configEntry3.a()));
        });
        h();
    }

    private static ConfigListPanel.RenameCallback proxy_$3(ConfigsTab configsTab) {
        return (configEntry, str, str2) -> {
            LocalConfigManager.get().renameProfile(configEntry.a(), str, () -> {
                configEntry.a(str);
            }, errStr -> {
                ChatMessages.a("§cОшибка переименования: " + errStr);
            });
        };
    }

    @Override
    public String[] c() {
        return a;
    }

    @Override
    public int d() {
        return this.h;
    }

    @Override
    public void a(int i) {
        if (i < 0 || i >= a.length || this.h == i) {
            return;
        }
        this.h = i;
    }

    @Override
    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, int i, int i2) {
        this.i = i;
        this.j = i2;
        this.k.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
        this.o.a();
        this.p.a();
        this.q.a();
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        float f4 = ((f2 + fD) - b) - d;
        float f5 = (f3 + b) - 3.0f;
        float f6 = (f4 - 8.0f) - 8.0f;
        float f7 = ((f3 + b) + 3.5f) - 3.0f;
        int i3 = (GuiInteractionState.a().b() || this.m.e()) ? 1 : 0;
        int i4 = (i3 == 0 && GuiInput.a((f6 - 16.0f) - 16.0f, f7, 8.0f, 8.0f, (double) i, (double) i2)) ? 1 : 0;
        int i5 = (i3 == 0 && GuiInput.a((f6 - 8.0f) - 8.0f, f7, 8.0f, 8.0f, (double) i, (double) i2)) ? 1 : 0;
        int i6 = (i3 == 0 && GuiInput.a(f6, f7, 8.0f, 8.0f, (double) i, (double) i2)) ? 1 : 0;
        a(this.o, Bool.from(i4), this.r, Bool.from(i3));
        this.r = Bool.from((i3 != 0 || i4 == 0) ? 0 : 1);
        a(this.p, Bool.from(i5), this.s, Bool.from(i3));
        this.s = Bool.from((i3 != 0 || i5 == 0) ? 0 : 1);
        a(this.q, Bool.from(i6), this.t, Bool.from(i3));
        this.t = Bool.from((i3 != 0 || i6 == 0) ? 0 : 1);
        a(MatrixStackVar, renderer2D, (f6 - 16.0f) - 16.0f, f7, 8.0f, (float) this.o.j(), "add");
        a(MatrixStackVar, renderer2D, (f6 - 8.0f) - 8.0f, f7, 8.0f, (float) this.p.j(), IconTextureRegistry.SAVE);
        a(MatrixStackVar, renderer2D, f6, f7, 8.0f, (float) this.q.j(), IconTextureRegistry.KEY);
        this.l.a(MatrixStackVar, renderer2D, f4, f5, d, e, i, i2);
        this.m.a(MatrixStackVar, renderer2D, f2 + b, f3 + c, fD - 38.0f, ((fE - c) - 9.5f) - 8.0f, i, i2, 1.0f);
        this.n.a(MatrixStackVar, renderer2D, f2, f3, i, i2);
        this.backdrop.a();
        boolean dialogOpen = this.m.e();
        if (dialogOpen && this.backdrop.i() < 1.0d) {
            this.backdrop.a(1.0d, 0.25d, Easing.h);
        } else if (!dialogOpen && this.backdrop.i() > 0.0d) {
            this.backdrop.a(0.0d, 0.18d, Easing.h);
        }
        float backdropAlpha = (float) this.backdrop.j();
        if (backdropAlpha > 0.01f) {
            float sw = MinecraftContext.d.getWidth();
            float sh = MinecraftContext.d.getHeight();
            renderer2D.a(0.0f, 0.0f, sw, sh, 0.0f, new Color(0, 0, 0, (int) (160.0f * backdropAlpha)), MatrixStackVar);
        }
        this.m.a(MatrixStackVar, renderer2D, MinecraftContext.d.getWidth() / 2.0f, MinecraftContext.d.getHeight() / 2.0f, i, i2);
        if (i4 == 0 && i5 == 0 && i6 == 0) {
            return;
        }
        GuiInput.g();
    }

    private void a(AnimationState animationState, boolean z, boolean z2, boolean z3) {
        if (z3 && z2) {
            animationState.a(0.0d, 0.15d, Easing.h);
        } else {
            if (z3 || z == z2) {
                return;
            }
            animationState.a(z ? 1.0d : 0.0d, 0.15d, Easing.h);
        }
    }

    private void a(MatrixStack MatrixStackVar, Renderer2D renderer2D, float f2, float f3, float f4, float f5, String str) {
        String str2;
        Color colorA = ColorUtils.a(Theme.b, Theme.d, f5);
        switch (str) {
            case "add":
                str2 = IconGlyphs.C;
                break;
            case "save":
                str2 = IconGlyphs.G;
                break;
            case "key":
                str2 = IconGlyphs.s;
                break;
            default:
                return;
        }
        FontManager.e[14].a(str2, f2, f3, colorA, MatrixStackVar);
    }

    public void a(MatrixStack MatrixStackVar, Renderer2D renderer2D) {
        this.m.a(MatrixStackVar, renderer2D, MinecraftContext.d.getWidth() / 2.0f, MinecraftContext.d.getHeight() / 2.0f, this.i, this.j);
    }

    @Override
    public void a(float f2, float f3, int i, int i2) {
        if (this.m.e()) {
            this.m.a(f2 + b, f3 + c, DrunVisualClickGuiScreen.d() - 38.0f, ((DrunVisualClickGuiScreen.e() - c) - 9.5f) - 8.0f, i, i2);
            return;
        }
        if (this.l.a(i, i2)) {
            return;
        }
        this.k.a(f2, f3, i, i2);
        float fD = DrunVisualClickGuiScreen.d();
        float fE = DrunVisualClickGuiScreen.e();
        float f4 = ((((f2 + fD) - b) - d) - 8.0f) - 8.0f;
        float f5 = ((f3 + b) + 3.5f) - 3.0f;
        if (GuiInput.a((f4 - 16.0f) - 16.0f, f5, 8.0f, 8.0f, i, i2)) {
            m();
            return;
        }
        if (GuiInput.a((f4 - 8.0f) - 8.0f, f5, 8.0f, 8.0f, i, i2)) {
            l();
            return;
        }
        if (GuiInput.a(f4, f5, 8.0f, 8.0f, i, i2)) {
            k();
            return;
        }
        this.m.a(f2 + b, f3 + c, fD - 38.0f, ((fE - c) - 9.5f) - 8.0f, i, i2);
    }

    private void k() {
        h();
    }

    private void l() {
        this.m.h();
    }

    private void m() {
        this.m.g();
    }

    @Override
    public void b(float f2, float f3, int i, int i2) {
        a(f2, f3, i, i2);
    }

    @Override
    public void c(float f2, float f3, int i, int i2) {
        this.m.a(i, i2);
    }

    @Override
    public void a(float f2, float f3, int i, int i2, double d2, double d3) {
        this.m.a(i, i2, d2, d3);
    }

    @Override
    public void a(float f2) {
        this.m.a(f2, this.i, this.j);
    }

    @Override
    public boolean a(int i, int i2, int i3) {
        return this.l.c() ? this.l.a(i, i2, i3) : this.m.a(i, i2, i3);
    }

    @Override
    public boolean b() {
        return Bool.from((this.l.c() || this.m.d()) ? 1 : 0);
    }

    public boolean a(char c2, int i) {
        return this.l.c() ? this.l.a(c2, i) : this.m.a(c2, i);
    }

    public boolean f() {
        return this.l.c();
    }

    public boolean g() {
        return this.m.e();
    }

    @Override
    public ClickGuiTabType a() {
        return ClickGuiTabType.CONFIGS;
    }

    private String a(String str) {
        if (str == null) {
            return "Не удалось переключить конфиг.";
        }
        switch (str) {
            case "Not connected":
                return "Нет соединения с сервером. Проверьте интернет-подключение.";
            case "Not initialized":
                return "Сервис конфигов ещё не инициализирован. Попробуйте позже.";
            case "CONFIG_NOT_FOUND":
                return "Конфиг не найден. Возможно, он был удалён.";
            case "No response":
                return "Сервер не отвечает. Попробуйте позже.";
            default:
                return (str.contains("timeout") || str.contains("Timeout")) ? "Превышено время ожидания. Проверьте интернет-подключение." : "Не удалось переключить конфиг: " + str;
        }
    }

    private String b(String str) {
        if (str == null) {
            return "Не удалось удалить конфиг.";
        }
        switch (str) {
            case "Not connected":
                return "Нет соединения с сервером. Проверьте интернет-подключение.";
            case "Not initialized":
                return "Сервис конфигов ещё не инициализирован. Попробуйте позже.";
            case "Cannot delete active config":
                return "Нельзя удалить активный конфиг. Сначала переключитесь на другой.";
            case "CONFIG_NOT_FOUND":
                return "Конфиг не найден. Возможно, он уже был удалён.";
            case "No response":
                return "Сервер не отвечает. Попробуйте позже.";
            default:
                return (str.contains("timeout") || str.contains("Timeout")) ? "Превышено время ожидания. Проверьте интернет-подключение." : "Не удалось удалить конфиг: " + str;
        }
    }

    private String c(String str) {
        if (str == null) {
            return "Не удалось переименовать конфиг.";
        }
        switch (str) {
            case "Not connected":
                return "Нет соединения с сервером. Проверьте интернет-подключение.";
            case "Not initialized":
                return "Сервис конфигов ещё не инициализирован. Попробуйте позже.";
            case "Cannot rename active config":
                return "Нельзя переименовать активный конфиг. Сначала переключитесь на другой.";
            case "NAME_TAKEN":
            case "Config already exists":
                return "Конфиг с таким именем уже существует.";
            case "CONFIG_NOT_FOUND":
                return "Конфиг не найден. Возможно, он был удалён.";
            case "INVALID_NAME":
                return "Недопустимое имя конфига. Используйте только буквы, цифры и пробелы.";
            case "No response":
                return "Сервер не отвечает. Попробуйте позже.";
            default:
                return (str.contains("timeout") || str.contains("Timeout")) ? "Превышено время ожидания. Проверьте интернет-подключение." : "Не удалось переименовать конфиг: " + str;
        }
    }

    private String d(String str) {
        if (str == null) {
            return "Не удалось создать конфиг.";
        }
        switch (str) {
            case "Not connected":
                return "Нет соединения с сервером. Проверьте интернет-подключение.";
            case "Not initialized":
                return "Сервис конфигов ещё не инициализирован. Попробуйте позже.";
            case "Config already exists":
                return "Конфиг с таким именем уже существует.";
            case "CONFLICT":
                return "Конфликт версий. Попробуйте ещё раз.";
            case "LIMIT_REACHED":
                return "Достигнут лимит конфигов. Удалите ненужные конфиги.";
            case "INVALID_NAME":
                return "Недопустимое имя конфига. Используйте только буквы, цифры и пробелы.";
            case "No response":
                return "Сервер не отвечает. Попробуйте позже.";
            default:
                return (str.contains("timeout") || str.contains("Timeout")) ? "Превышено время ожидания. Проверьте интернет-подключение." : "Не удалось создать конфиг: " + str;
        }
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
