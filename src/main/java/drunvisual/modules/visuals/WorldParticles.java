package drunvisual.modules.visuals;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.Identifier;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.events.WorldRenderStartEvent;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.util.ColorUtils;

@ModuleInfo(a = "World Particles", b = "Падающие частицы в мире", c = ModuleCategory.VISUALS)
public class WorldParticles extends ClientModule {
    private final ModeSetting e = new ModeSetting("Тип частиц", new String[]{"Сердце", "Искра", "Снежинка", "Сияние", "Звезда", "Доллар"}, "Снежинка");
    private final ModeSetting f = new ModeSetting("Режим физики", new String[]{"Реалистичная", "Без коллизий", "Без физики"}, "Без физики");
    private final SettingGroup g = new SettingGroup("Спавн");
    private final SliderSetting h = new SliderSetting("Частота спавна", 5.0f, 1.0f, 10.0f, 1.0f);
    private final SliderSetting i = new SliderSetting("Количество за спавн", 5.0f, 1.0f, 15.0f, 1.0f);
    private final SliderSetting j = new SliderSetting("Радиус спавна", 30.0f, 5.0f, 50.0f, 1.0f);
    private final SliderSetting k = new SliderSetting("Высота спавна", 10.0f, 5.0f, 30.0f, 1.0f);
    private final SettingGroup l = new SettingGroup("Частицы");
    private final SliderSetting m = new SliderSetting("Размер", 0.25f, 0.1f, 1.0f, 0.05f);
    private final SliderSetting n = new SliderSetting("Время жизни", 100.0f, 30.0f, 300.0f, 10.0f);
    private final SliderSetting o = new SliderSetting("Сила притяжения", 0.02f, 0.0f, 0.1f, 0.01f);
    private final BooleanSetting p = new BooleanSetting("Горизонтальное движение", true);
    private final SliderSetting q = new SliderSetting("Скорость движения", 0.05f, 0.0f, 0.2f, 0.01f).a(() -> {
        return Boolean.valueOf(this.p.a());
    });
    private final SettingGroup r = new SettingGroup("Цвет");
    private final BooleanSetting s = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting t = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return Boolean.valueOf(Bool.from(this.s.a() ? 0 : 1));
    });
    private long u = 0;
    public static int a;
    public static boolean b;

    @EventHandler
    public void a(WorldRenderStartEvent worldRenderStartEvent) {
        if (c.player == null || c.world == null) {
            return;
        }
        long jCurrentTimeMillis = System.currentTimeMillis();
        if (jCurrentTimeMillis - this.u >= 1000.0d / ((double) this.h.a())) {
            n();
            this.u = jCurrentTimeMillis;
        }
    }

    private void n() {
        Vec3d Vec3dVarGetPos = c.player.getPos();
        int iB = this.i.b();
        for (int i = 0; i < iB; i++) {
            HudServiceRegistry.PARTICLES.a(new Vec3d(Vec3dVarGetPos.x + ThreadLocalRandom.current().nextDouble(-this.j.a(), this.j.a()), Vec3dVarGetPos.y + (!this.f.b("Без физики") ? this.k.a() : ThreadLocalRandom.current().nextDouble(0.0d, this.k.a())), Vec3dVarGetPos.z + ThreadLocalRandom.current().nextDouble(-this.j.a(), this.j.a())), !this.p.a() ? Vec3d.ZERO : new Vec3d(ThreadLocalRandom.current().nextDouble(-this.q.a(), this.q.a()), 0.0d, ThreadLocalRandom.current().nextDouble(-this.q.a(), this.q.a())), this.n.b(), this.m.a() + ((ThreadLocalRandom.current().nextFloat() * 0.1f) - 0.05f), p(), ColorUtils.a(o().getRGB()), this.o.a(), this.f.d());
        }
    }

    private Color o() {
        return !this.s.a() ? this.t.a() : ModuleRegistry.CLIENT_COLOR.n();
    }

    private Identifier p() {
        String strD = this.e.d();
        byte b2 = -1;
        switch (strD.hashCode()) {
            case -2018961832:
                if (strD.equals("Снежинка")) {
                    b2 = 2;
                }
                break;
            case 860345114:
                if (strD.equals("Доллар")) {
                    b2 = 5;
                }
                break;
            case 934967833:
                if (strD.equals("Звезда")) {
                    b2 = 4;
                }
                break;
            case 1001367009:
                if (strD.equals("Искра")) {
                    b2 = 1;
                }
                break;
            case 1224355287:
                if (strD.equals("Сердце")) {
                    b2 = 0;
                }
                break;
            case 1227580930:
                if (strD.equals("Сияние")) {
                    b2 = 3;
                }
                break;
        }
        switch (b2) {
            case 0:
                return Identifier.of("drunvisual", "textures/particle/heart.png");
            case 1:
                return Identifier.of("drunvisual", "textures/particle/sparkle.png");
            case 2:
                return Identifier.of("drunvisual", "textures/particle/snowflake.png");
            case 3:
                return Identifier.of("drunvisual", "textures/particle/glow.png");
            case 4:
                return Identifier.of("drunvisual", "textures/particle/star.png");
            case 5:
                return Identifier.of("drunvisual", "textures/particle/dollar.png");
            default:
                return Identifier.of("drunvisual", "textures/particle/snowflake.png");
        }
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
