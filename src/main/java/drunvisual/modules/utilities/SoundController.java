package drunvisual.modules.utilities;

import java.util.HashMap;
import java.util.Map;
import meteordevelopment.orbit.EventHandler;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.events.SoundPlayEvent;

@ModuleInfo(a = "Sound Controller", b = "Контроль громкости определённых звуков", c = ModuleCategory.UTILITIES)
public class SoundController extends ClientModule {
    private final SettingGroup a = new SettingGroup("Фейерверк");
    private final BooleanSetting b = new BooleanSetting("Включить", false);
    private final SliderSetting e = new SliderSetting("Громкость", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.b.a());
    });
    private final SettingGroup f = new SettingGroup("Пузырёк опыта");
    private final BooleanSetting g = new BooleanSetting("Бросок", false);
    private final SliderSetting h = new SliderSetting("Громкость броска", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.g.a());
    });
    private final BooleanSetting i = new BooleanSetting("Разбивание", false);
    private final SliderSetting j = new SliderSetting("Громкость разбивания", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.i.a());
    });
    private final BooleanSetting k = new BooleanSetting("Подбор опыта", false);
    private final SliderSetting l = new SliderSetting("Громкость подбора", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.k.a());
    });
    private final SettingGroup m = new SettingGroup("Оружие");
    private final BooleanSetting n = new BooleanSetting("Трезубец", false);
    private final SliderSetting o = new SliderSetting("Громкость трезубца", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.n.a());
    });
    private final BooleanSetting p = new BooleanSetting("Удочка", false);
    private final SliderSetting q = new SliderSetting("Громкость удочки", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.p.a());
    });
    private final SettingGroup r = new SettingGroup("Атака");
    private final BooleanSetting s = new BooleanSetting("Звук удара", false);
    private final SliderSetting t = new SliderSetting("Громкость удара", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.s.a());
    });
    private final BooleanSetting u = new BooleanSetting("Звук крита", false);
    private final SliderSetting v = new SliderSetting("Громкость крита", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.u.a());
    });
    private final SettingGroup w = new SettingGroup("Прочее");
    private final BooleanSetting x = new BooleanSetting("Сплеш", false);
    private final SliderSetting y = new SliderSetting("Громкость сплеша", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.x.a());
    });
    private final BooleanSetting z = new BooleanSetting("Крит FunTime", false);
    private final SliderSetting A = new SliderSetting("Громкость FunTime", 50.0f, 0.0f, 100.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.z.a());
    });
    private final Map<String, SoundVolumeBinding> B = new HashMap();

    private static class SoundVolumeBinding {
        private final BooleanSetting a;
        private final SliderSetting b;

        public SoundVolumeBinding(BooleanSetting booleanSetting, SliderSetting sliderSetting) {
            this.a = booleanSetting;
            this.b = sliderSetting;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public SoundController() {
        this.B.put("minecraft:entity.firework_rocket.launch", new SoundVolumeBinding(this.b, this.e));
        this.B.put("minecraft:entity.experience_bottle.throw", new SoundVolumeBinding(this.g, this.h));
        this.B.put("minecraft:entity.splash_potion.break", new SoundVolumeBinding(this.i, this.j));
        this.B.put("minecraft:entity.experience_orb.pickup", new SoundVolumeBinding(this.k, this.l));
        this.B.put("minecraft:item.trident.return", new SoundVolumeBinding(this.n, this.o));
        this.B.put("minecraft:item.trident.hit_ground", new SoundVolumeBinding(this.n, this.o));
        this.B.put("minecraft:block.beacon.deactivate", new SoundVolumeBinding(this.n, this.o));
        this.B.put("minecraft:entity.fishing_bobber.retrieve", new SoundVolumeBinding(this.p, this.q));
        this.B.put("minecraft:entity.player.attack.sweep", new SoundVolumeBinding(this.s, this.t));
        this.B.put("minecraft:entity.player.attack.strong", new SoundVolumeBinding(this.s, this.t));
        this.B.put("minecraft:entity.player.attack.weak", new SoundVolumeBinding(this.s, this.t));
        this.B.put("minecraft:entity.generic.splash", new SoundVolumeBinding(this.x, this.y));
        this.B.put("minecraft:entity.player.splash", new SoundVolumeBinding(this.x, this.y));
        this.B.put("minecraft:entity.player.attack.crit", new SoundVolumeBinding(this.u, this.v));
        this.B.put("minecraft:entity.player.levelup", new SoundVolumeBinding(this.z, this.A));
    }

    @EventHandler
    public void a(SoundPlayEvent soundPlayEvent) {
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
