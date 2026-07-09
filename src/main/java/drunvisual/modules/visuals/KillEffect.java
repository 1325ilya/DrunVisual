package drunvisual.modules.visuals;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import drunvisual.events.AttackEntityEvent;
import drunvisual.events.ClientTickEvent;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SliderSetting;
import ru.drunvisual.DrunVisual;

@ModuleInfo(a = "KillEffect", b = "Создаёт визуальный эффект на месте смерти недавно атакованной цели.", c = ModuleCategory.VISUALS)
public class KillEffect extends ClientModule {
    private static final long TRACK_TIMEOUT_MS = 5000L;
    private final BooleanSetting onlyPlayers = new BooleanSetting("Только игроки", true);
    private final BooleanSetting useClientColor = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting color = new ColorSetting("Цвет", new Color(255, 95, 95)).a(() -> {
        return Boolean.valueOf(!this.useClientColor.get());
    });
    private final ModeSetting texture = new ModeSetting("Текстура", new String[]{"Sparkle", "Glow", "Star", "Heart"}, "Star");
    private final SliderSetting count = new SliderSetting("Количество", 18.0f, 6.0f, 40.0f, 1.0f);
    private final SliderSetting size = new SliderSetting("Размер", 0.35f, 0.15f, 0.85f, 0.05f);
    private final SliderSetting life = new SliderSetting("Время жизни", 26.0f, 10.0f, 60.0f, 2.0f);
    private final SliderSetting speed = new SliderSetting("Сила разлёта", 0.22f, 0.08f, 0.45f, 0.01f);
    private final Map<UUID, TrackedTarget> trackedTargets = new HashMap();

    @EventHandler
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        if (c.player == null || c.world == null) {
            return;
        }
        Entity entity = attackEntityEvent.a();
        if (!(entity instanceof LivingEntity) || entity == c.player) {
            return;
        }
        if (this.onlyPlayers.get() && !(entity instanceof PlayerEntity)) {
            return;
        }
        LivingEntity target = (LivingEntity) entity;
        this.trackedTargets.put(target.getUuid(), new TrackedTarget(target));
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            this.trackedTargets.clear();
            return;
        }
        long now = System.currentTimeMillis();
        Iterator<Map.Entry<UUID, TrackedTarget>> iterator = this.trackedTargets.entrySet().iterator();
        while (iterator.hasNext()) {
            TrackedTarget trackedTarget = (TrackedTarget) ((Map.Entry) iterator.next()).getValue();
            LivingEntity entity = trackedTarget.entity;
            if (entity == null) {
                iterator.remove();
                continue;
            }
            trackedTarget.lastPos = entity.getPos();
            if (!trackedTarget.spawned && (!entity.isAlive() || entity.isRemoved())) {
                spawnEffect(trackedTarget.lastPos.add(0.0d, 0.9d, 0.0d));
                trackedTarget.spawned = true;
                iterator.remove();
                continue;
            }
            if (now - trackedTarget.trackedAtMs > TRACK_TIMEOUT_MS) {
                iterator.remove();
            }
        }
    }

    private void spawnEffect(Vec3d center) {
        Identifier textureId = textureId();
        int rgb = activeColor().getRGB();
        int lifetime = this.life.roundedInt();
        float particleSize = this.size.get();
        double velocityScale = this.speed.get();
        for (int i = 0; i < this.count.roundedInt(); i++) {
            Vec3d velocity = new Vec3d(ThreadLocalRandom.current().nextDouble(-1.0d, 1.0d), ThreadLocalRandom.current().nextDouble(0.15d, 1.15d), ThreadLocalRandom.current().nextDouble(-1.0d, 1.0d));
            if (velocity.lengthSquared() <= 0.0001d) {
                velocity = new Vec3d(0.0d, 0.4d, 0.0d);
            }
            velocity = velocity.normalize().multiply(ThreadLocalRandom.current().nextDouble(0.04d, velocityScale));
            HudServiceRegistry.PARTICLES.a(center, velocity, lifetime, particleSize + ((ThreadLocalRandom.current().nextFloat() - 0.5f) * 0.08f), textureId, rgb, "Без коллизий");
        }
    }

    private Identifier textureId() {
        String selected = this.texture.d();
        if ("Sparkle".equals(selected)) {
            return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/sparkle.png");
        }
        if ("Glow".equals(selected)) {
            return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/glow.png");
        }
        if ("Heart".equals(selected)) {
            return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/heart.png");
        }
        return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/star.png");
    }

    private Color activeColor() {
        return this.useClientColor.get() ? ModuleRegistry.CLIENT_COLOR.n() : this.color.getColor();
    }

    @Override
    public void f() {
        super.f();
        this.trackedTargets.clear();
    }

    private static final class TrackedTarget {
        private final LivingEntity entity;
        private final long trackedAtMs;
        private Vec3d lastPos;
        private boolean spawned;

        private TrackedTarget(LivingEntity livingEntity) {
            this.entity = livingEntity;
            this.trackedAtMs = System.currentTimeMillis();
            this.lastPos = livingEntity.getPos();
        }
    }
}
