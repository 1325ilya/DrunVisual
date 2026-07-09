package drunvisual.modules.visuals;

import java.awt.Color;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.LivingEntity;
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
import drunvisual.events.AttackEntityEvent;
import drunvisual.events.TotemPopEvent;
import drunvisual.events.WorldRenderStartEvent;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.util.ColorUtils;

@ModuleInfo(a = "Particles", b = "Создаёт частицы при выполнении условий", c = ModuleCategory.VISUALS)
public class Particles extends ClientModule {
    private long H;
    private Vec3d I;
    private LivingEntity K;
    public static int a;
    public static boolean b;
    private final ModeSetting e = new ModeSetting("Условия", new String[]{"При ударе", "При сносе тотема"}, new int[]{0});
    private final BooleanSetting f = new BooleanSetting("Индивидуальные настройки", false);
    private final SettingGroup g = new SettingGroup("Общие").a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final ModeSetting h = new ModeSetting("Тип частиц", new String[]{"Сердце", "Искра", "Снежинка", "Сияние", "Звезда", "Доллар"}, "Сердце").a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final ModeSetting i = new ModeSetting("Режим физики", new String[]{"Реалистичная", "Без коллизий", "Без физики", "Притяжение"}, "Реалистичная").a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final SliderSetting j = new SliderSetting("Количество", 4.0f, 1.0f, 20.0f, 1.0f).a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final SliderSetting k = new SliderSetting("Размер", 0.3f, 0.1f, 0.75f, 0.05f).a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final SliderSetting l = new SliderSetting("Время жизни", 20.0f, 10.0f, 50.0f, 5.0f).a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final SliderSetting m = new SliderSetting("Сила разлёта", 0.3f, 0.15f, 0.35f, 0.01f).a(() -> {
        int i;
        if (this.f.a()) {
            i = 0;
        } else {
            i = 1;
        }
        return Boolean.valueOf(Bool.from(i));
    });
    private final BooleanSetting n = new BooleanSetting("Цвет клиента", Bool.from(45027861)).a(() -> {
        return Boolean.valueOf(Bool.from(this.f.a() ? 0 : 1));
    });
    private final ColorSetting o = new ColorSetting("Конечный цвет", Color.WHITE).a(() -> {
        return Boolean.valueOf(Bool.from((this.n.a() || this.f.a()) ? 0 : 1));
    });
    private final SettingGroup p = new SettingGroup("Удар").a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting q = new ModeSetting("Тип частиц", new String[]{"Сердце", "Искра", "Снежинка", "Сияние", "Звезда", "Доллар"}, "Сердце").a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting r = new ModeSetting("Режим физики", new String[]{"Реалистичная", "Без коллизий", "Без физики", "Притяжение"}, "Реалистичная").a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting s = new SliderSetting("Количество", 4.0f, 1.0f, 20.0f, 1.0f).a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting t = new SliderSetting("Размер", 0.3f, 0.1f, 0.75f, 0.05f).a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting u = new SliderSetting("Время жизни", 20.0f, 10.0f, 50.0f, 5.0f).a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting v = new SliderSetting("Сила разлёта", 0.3f, 0.15f, 0.35f, 0.01f).a(() -> {
        return (this.f.a() && this.e.b("При ударе")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting w = new BooleanSetting("Цвет клиента", true).a(() -> {
        if (this.f.a()) {
            if (this.e.b("При ударе")) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    });
    private final ColorSetting x = new ColorSetting("Конечный цвет", Color.WHITE).a(() -> {
        return (this.f.a() && this.e.b("При ударе") && !this.w.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SettingGroup y = new SettingGroup("Снос тотема").a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting z = new ModeSetting("Тип частиц", new String[]{"Сердце", "Искра", "Снежинка", "Сияние", "Звезда", "Доллар"}, "Сердце").a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting A = new ModeSetting("Режим физики", new String[]{"Реалистичная", "Без коллизий", "Без физики", "Притяжение"}, "Реалистичная").a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting B = new SliderSetting("Количество", 4.0f, 1.0f, 20.0f, 1.0f).a(() -> {
        if (this.f.a()) {
            if (this.e.b("При сносе тотема")) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    });
    private final SliderSetting C = new SliderSetting("Размер", 0.3f, 0.1f, 0.75f, 0.05f).a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting D = new SliderSetting("Время жизни", 20.0f, 10.0f, 50.0f, 5.0f).a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting E = new SliderSetting("Сила разлёта", 0.3f, 0.15f, 0.35f, 0.01f).a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting F = new BooleanSetting("Цвет клиента", Bool.from(1175694691)).a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ColorSetting G = new ColorSetting("Конечный цвет", Color.WHITE).a(() -> {
        return (this.f.a() && this.e.b("При сносе тотема") && !this.F.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private boolean J = false;

    @EventHandler
    public void a(AttackEntityEvent attackEntityEvent) {
        int iB;
        if (this.e.b("При ударе") && attackEntityEvent.a() != c.player && (attackEntityEvent.a() instanceof LivingEntity)) {
            LivingEntity LivingEntityVarA = (LivingEntity) attackEntityEvent.a();
            Vec3d Vec3dVar = new Vec3d(LivingEntityVarA.getX(), LivingEntityVarA.getY() + ((double) (LivingEntityVarA.getEyeHeight(LivingEntityVarA.getPose()) / 2.0f)), LivingEntityVarA.getZ());
            if (this.f.a()) {
                iB = this.s.b() * 3;
            } else {
                iB = this.j.b() * 3;
            }
            int i = iB;
            for (int i2 = 0; i2 < i; i2++) {
                a(Vec3dVar, 0.02d, f("attack").getRGB(), "attack");
            }
        }
    }

    @EventHandler
    public void a(TotemPopEvent totemPopEvent) {
        if (this.e.b("При сносе тотема")) {
            this.K = totemPopEvent.a();
            this.I = new Vec3d(this.K.getX(), this.K.getY() + ((double) this.K.getEyeHeight(this.K.getPose())), this.K.getZ());
            this.H = System.currentTimeMillis();
            this.J = true;
        }
    }

    @EventHandler
    public void a(WorldRenderStartEvent worldRenderStartEvent) {
        Vec3d Vec3dVar;
        if (this.e.b("При сносе тотема") && this.J) {
            if (System.currentTimeMillis() - this.H > 1500) {
                this.J = false;
                this.K = null;
                return;
            }
            if (this.K != null && this.K.isAlive()) {
                this.I = new Vec3d(this.K.getX(), this.K.getY() + (((double) this.K.getHeight()) / 2.0d), this.K.getZ());
            }
            for (int i = 0; i < 2; i++) {
                do {
                    Vec3dVar = new Vec3d(ThreadLocalRandom.current().nextDouble(-1.0d, 1.0d), ThreadLocalRandom.current().nextDouble(0.1d, 1.0d), ThreadLocalRandom.current().nextDouble(-1.0d, 1.0d));
                } while (Vec3dVar.lengthSquared() > 1.0d);
                HudServiceRegistry.PARTICLES.a(this.I, Vec3dVar.normalize().multiply(0.4d), !this.f.a() ? this.l.b() : this.D.b(), !this.f.a() ? this.k.a() : this.C.a(), g("totem"), !ThreadLocalRandom.current().nextBoolean() ? 327424 : 16776983, !this.f.a() ? this.i.d() : this.A.d());
            }
        }
    }

    private void a(Vec3d Vec3dVar, double d, int i, String str) {
        int iA = ColorUtils.a(i);
        int iB = !this.f.a() ? this.j.b() : a(str);
        int iB2 = !this.f.a() ? this.l.b() : c(str);
        int i2 = (iB2 & (-11)) - ((iB2 ^ (-1)) & 10);
        int i3 = (iB2 | 10) + (iB2 & 10);
        float fA = !this.f.a() ? this.k.a() : b(str);
        float f = fA - 0.05f;
        float f2 = fA + 0.05f;
        double dA = !this.f.a() ? this.m.a() : d(str);
        String strD = !this.f.a() ? this.i.d() : e(str);
        for (int i4 = 0; i4 < iB; i4++) {
            int iNextInt = ThreadLocalRandom.current().nextInt(i2, i3);
            float fNextFloat = f + (ThreadLocalRandom.current().nextFloat() * (f2 - f));
            Vec3d Vec3dVar2 = new Vec3d(ThreadLocalRandom.current().nextDouble(-d, d), ThreadLocalRandom.current().nextDouble(0.0d, d), ThreadLocalRandom.current().nextDouble(-d, d));
            if (Vec3dVar2.lengthSquared() > 0.0d) {
                Vec3dVar2 = Vec3dVar2.normalize().multiply(ThreadLocalRandom.current().nextDouble(0.005d, dA));
            }
            HudServiceRegistry.PARTICLES.a(Vec3dVar, Vec3dVar2, iNextInt, fNextFloat, g(str), iA, strD);
        }
    }

    private int a(String str) {
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1407259064:
                if (str.equals("attack")) {
                    b2 = 0;
                }
                break;
            case 110549953:
                if (str.equals("totem")) {
                    b2 = 1;
                }
                break;
        }
        switch (b2) {
            case 0:
                return this.s.b();
            case 1:
                return this.B.b();
            default:
                return this.j.b();
        }
    }

    private float b(String str) {
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1407259064:
                if (str.equals("attack")) {
                    b2 = 0;
                }
                break;
            case 110549953:
                if (str.equals("totem")) {
                    b2 = 1;
                }
                break;
        }
        switch (b2) {
            case 0:
                return this.t.a();
            case 1:
                return this.C.a();
            default:
                return this.k.a();
        }
    }

    private int c(String str) {
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1407259064:
                if (str.equals("attack")) {
                    b2 = 0;
                }
                break;
            case 110549953:
                if (str.equals("totem")) {
                    b2 = 1;
                }
                break;
        }
        switch (b2) {
            case 0:
                return this.u.b();
            case 1:
                return this.D.b();
            default:
                return this.l.b();
        }
    }

    private double d(String str) {
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1407259064:
                if (str.equals("attack")) {
                    b2 = 0;
                }
                break;
            case 110549953:
                if (str.equals("totem")) {
                    b2 = 1;
                }
                break;
        }
        switch (b2) {
            case 0:
                return this.v.a();
            case 1:
                return this.E.a();
            default:
                return this.m.a();
        }
    }

    private String e(String str) {
        byte b2 = -1;
        switch (str.hashCode()) {
            case -1407259064:
                if (str.equals("attack")) {
                    b2 = 0;
                }
                break;
            case 110549953:
                if (str.equals("totem")) {
                    b2 = 1;
                }
                break;
        }
        switch (b2) {
            case 0:
                return this.r.d();
            case 1:
                return this.A.d();
            default:
                return this.i.d();
        }
    }

    private Color f(String str) {
        Color colorN;
        if (this.f.a()) {
            byte b2 = -1;
            switch (str.hashCode()) {
                case -1407259064:
                    if (str.equals("attack")) {
                        b2 = 0;
                    }
                    break;
                case 110549953:
                    if (str.equals("totem")) {
                        b2 = 1;
                    }
                    break;
            }
            switch (b2) {
                case 0:
                    return !this.w.a() ? this.x.a() : ModuleRegistry.CLIENT_COLOR.n();
                case 1:
                    if (this.F.a()) {
                        colorN = ModuleRegistry.CLIENT_COLOR.n();
                    } else {
                        colorN = this.G.a();
                    }
                    return colorN;
            }
        }
        return !this.n.a() ? this.o.a() : ModuleRegistry.CLIENT_COLOR.n();
    }

    private Identifier g(String str) {
        String strD;
        if (this.f.a()) {
            byte b2 = -1;
            switch (str.hashCode()) {
                case -1407259064:
                    if (str.equals("attack")) {
                        b2 = 0;
                    }
                    break;
                case 110549953:
                    if (str.equals("totem")) {
                        b2 = 1;
                    }
                    break;
            }
            switch (b2) {
                case 0:
                    strD = this.q.d();
                    break;
                case 1:
                    strD = this.z.d();
                    break;
                default:
                    strD = this.h.d();
                    break;
            }
        } else {
            strD = this.h.d();
        }
        String str2 = strD;
        byte b3 = -1;
        switch (str2.hashCode()) {
            case -2018961832:
                if (str2.equals("Снежинка")) {
                    b3 = 0;
                }
                break;
            case 860345114:
                if (str2.equals("Доллар")) {
                    b3 = 1;
                }
                break;
            case 934967833:
                if (str2.equals("Звезда")) {
                    b3 = 2;
                }
                break;
            case 1001367009:
                if (str2.equals("Искра")) {
                    b3 = 3;
                }
                break;
            case 1224355287:
                if (str2.equals("Сердце")) {
                    b3 = 4;
                }
                break;
            case 1227580930:
                if (str2.equals("Сияние")) {
                    b3 = 5;
                }
                break;
        }
        switch (b3) {
            case 0:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/snowflake.png");
            case 1:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/dollar.png");
            case 2:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/star.png");
            case 3:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/sparkle.png");
            case 4:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/heart.png");
            case 5:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/glow.png");
            default:
                return Identifier.of(ru.drunvisual.DrunVisual.CLIENT_NAME, "textures/particle/heart.png");
        }
    }

    @Override
    public void f() {
        super.f();
        this.J = false;
        this.K = null;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
