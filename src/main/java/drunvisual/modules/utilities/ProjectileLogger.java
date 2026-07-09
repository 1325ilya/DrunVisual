package drunvisual.modules.utilities;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.HudRenderPostEvent;
import drunvisual.media.chat.ChatMessages;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SliderSetting;
import ru.drunvisual.DrunVisual;

@ModuleInfo(a = "ProjectileLogger", b = "Логирует важные прожектайлы рядом с игроком.", c = ModuleCategory.UTILITIES)
public class ProjectileLogger extends ClientModule {
    private static final Color PANEL_TOP = new Color(10, 12, 16, 220);
    private static final Color PANEL_BOTTOM = new Color(6, 8, 12, 235);
    private static final Color TITLE = new Color(255, 255, 255, 255);
    private static final Color TEXT = new Color(195, 204, 220, 255);
    private final BooleanSetting pearls = new BooleanSetting("Пёрлы", true);
    private final BooleanSetting arrows = new BooleanSetting("Стрелы", true);
    private final BooleanSetting tridents = new BooleanSetting("Трезубцы", true);
    private final BooleanSetting potions = new BooleanSetting("Зелья", true);
    private final BooleanSetting ignoreSelf = new BooleanSetting("Игнорировать себя", true);
    private final BooleanSetting chat = new BooleanSetting("Чат", true);
    private final BooleanSetting overlay = new BooleanSetting("Оверлей", true);
    private final SliderSetting radius = new SliderSetting("Радиус", 64.0f, 16.0f, 128.0f, 4.0f);
    private final SliderSetting historySize = new SliderSetting("История", 6.0f, 3.0f, 10.0f, 1.0f);
    private final SliderSetting margin = new SliderSetting("Отступ", 12.0f, 4.0f, 40.0f, 1.0f);
    private final Map<Integer, TrackedProjectile> trackedProjectiles = new HashMap();
    private final Deque<LogEntry> history = new ArrayDeque();

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            this.trackedProjectiles.clear();
            this.history.clear();
            return;
        }
        Set<Integer> seen = new HashSet();
        double radiusSq = this.radius.get() * this.radius.get();
        for (Entity entity : c.world.getEntities()) {
            String kind = projectileKind(entity);
            if (kind == null || entity.squaredDistanceTo(c.player) > radiusSq) {
                continue;
            }
            Entity owner = entity instanceof ProjectileEntity ? ((ProjectileEntity) entity).getOwner() : null;
            if (this.ignoreSelf.get() && owner == c.player) {
                continue;
            }
            seen.add(Integer.valueOf(entity.getId()));
            TrackedProjectile trackedProjectile = this.trackedProjectiles.get(Integer.valueOf(entity.getId()));
            if (trackedProjectile == null) {
                trackedProjectile = new TrackedProjectile(entity.getId(), kind, ownerName(owner), entity.getPos());
                this.trackedProjectiles.put(Integer.valueOf(entity.getId()), trackedProjectile);
                log(kind + ": " + trackedProjectile.ownerName + " • " + shortPos(entity.getPos()) + " • старт");
            }
            trackedProjectile.lastPos = entity.getPos();
            trackedProjectile.lastSeenTick = c.player.age;
        }
        Iterator<Map.Entry<Integer, TrackedProjectile>> iterator = this.trackedProjectiles.entrySet().iterator();
        while (iterator.hasNext()) {
            TrackedProjectile trackedProjectile2 = (TrackedProjectile) ((Map.Entry) iterator.next()).getValue();
            if (seen.contains(Integer.valueOf(trackedProjectile2.id))) {
                continue;
            }
            log(trackedProjectile2.kind + ": " + trackedProjectile2.ownerName + " • " + shortPos(trackedProjectile2.lastPos) + " • " + resolveOutcome(trackedProjectile2.lastPos));
            iterator.remove();
        }
    }

    @EventHandler
    public void onHudRender(HudRenderPostEvent hudRenderPostEvent) {
        if (!this.overlay.get() || this.history.isEmpty()) {
            return;
        }
        Renderer2D renderer = DrunVisual.getInstance().getRender();
        if (renderer == null) {
            return;
        }
        FontRenderer titleFont = FontManager.MEDIUM[12];
        FontRenderer rowFont = FontManager.MEDIUM[10];
        MatrixStack matrices = hudRenderPostEvent.a();
        float x = this.margin.get();
        float y = this.margin.get();
        List<String> lines = buildLines();
        float width = titleFont.a("ProjectileLogger");
        for (String line : lines) {
            width = Math.max(width, rowFont.a(line));
        }
        width += 14.0f;
        float lineHeight = rowFont.b("A");
        float height = 12.0f + titleFont.b("ProjectileLogger") + (lines.size() * (lineHeight + 3.0f));
        renderer.a(x, y, width, height, 7.0f, PANEL_TOP, PANEL_TOP, PANEL_BOTTOM, PANEL_BOTTOM, matrices);
        titleFont.a("ProjectileLogger", x + 7.0f, y + 6.0f, TITLE, matrices);
        float textY = y + 10.0f + titleFont.b("ProjectileLogger");
        for (String line2 : lines) {
            rowFont.a(line2, x + 7.0f, textY, TEXT, matrices);
            textY += lineHeight + 3.0f;
        }
    }

    private List<String> buildLines() {
        List<String> lines = new ArrayList();
        Iterator<LogEntry> iterator = this.history.iterator();
        while (iterator.hasNext()) {
            LogEntry entry = iterator.next();
            lines.add(entry.text + " • " + formatAge(entry.timeMs));
        }
        return lines;
    }

    private void log(String text) {
        this.history.addFirst(new LogEntry(text, System.currentTimeMillis()));
        while (this.history.size() > this.historySize.roundedInt()) {
            this.history.removeLast();
        }
        if (this.chat.get()) {
            ChatMessages.a((Object) ("Projectile Logger: " + text));
        }
    }

    private String projectileKind(Entity entity) {
        if ((entity instanceof EnderPearlEntity) && this.pearls.get()) {
            return "Пёрл";
        }
        if ((entity instanceof TridentEntity) && this.tridents.get()) {
            return "Трезубец";
        }
        if ((entity instanceof PotionEntity) && this.potions.get()) {
            return "Зелье";
        }
        if ((entity instanceof PersistentProjectileEntity) && this.arrows.get()) {
            return "Стрела";
        }
        return null;
    }

    private String ownerName(Entity entity) {
        return entity == null ? "Неизвестный" : entity.getName().getString();
    }

    private String resolveOutcome(Vec3d pos) {
        if (pos == null) {
            return "исчез";
        }
        if (c.player != null && c.player.getPos().distanceTo(pos) < 2.2d) {
            return "похоже попал";
        }
        Box box = new Box(pos, pos).expand(2.0d);
        for (Entity entity : c.world.getOtherEntities(c.player, box)) {
            if ((entity instanceof LivingEntity) && entity.isAlive()) {
                return "похоже попал";
            }
        }
        return "не попал";
    }

    private String shortPos(Vec3d pos) {
        if (pos == null) {
            return "?";
        }
        return ((int) Math.round(pos.x)) + " " + ((int) Math.round(pos.y)) + " " + ((int) Math.round(pos.z));
    }

    private String formatAge(long timeMs) {
        return String.format("%.1fs", Double.valueOf((System.currentTimeMillis() - timeMs) / 1000.0d));
    }

    @Override
    public void f() {
        super.f();
        this.trackedProjectiles.clear();
        this.history.clear();
    }

    private static final class TrackedProjectile {
        private final int id;
        private final String kind;
        private final String ownerName;
        private final Vec3d startPos;
        private Vec3d lastPos;
        private int lastSeenTick;

        private TrackedProjectile(int i, String str, String str2, Vec3d vec3d) {
            this.id = i;
            this.kind = str;
            this.ownerName = str2;
            this.startPos = vec3d;
            this.lastPos = vec3d;
        }
    }

    private static final class LogEntry {
        private final String text;
        private final long timeMs;

        private LogEntry(String str, long j) {
            this.text = str;
            this.timeMs = j;
        }
    }
}
