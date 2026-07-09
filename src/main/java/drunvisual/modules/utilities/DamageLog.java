package drunvisual.modules.utilities;

import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.mob.CreeperEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
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

@ModuleInfo(a = "DamageLog", b = "Показывает недавний входящий урон и предполагаемый источник.", c = ModuleCategory.UTILITIES)
public class DamageLog extends ClientModule {
    private static final Color PANEL_TOP = new Color(10, 12, 16, 220);
    private static final Color PANEL_BOTTOM = new Color(6, 8, 12, 235);
    private static final Color TEXT = new Color(255, 255, 255, 255);
    private static final Color MUTED = new Color(180, 188, 205, 255);
    private final BooleanSetting chat = new BooleanSetting("Чат", true);
    private final BooleanSetting overlay = new BooleanSetting("Оверлей", true);
    private final SliderSetting historySize = new SliderSetting("История", 5.0f, 2.0f, 8.0f, 1.0f);
    private final SliderSetting margin = new SliderSetting("Отступ", 12.0f, 4.0f, 40.0f, 1.0f);
    private final Deque<DamageEntry> entries = new ArrayDeque();
    private float lastTotal = -1.0f;
    private long lastDamageMs = -1L;

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            this.lastTotal = -1.0f;
            this.entries.clear();
            this.lastDamageMs = -1L;
            return;
        }
        ClientPlayerEntity player = c.player;
        float currentTotal = player.getHealth() + player.getAbsorptionAmount();
        if (this.lastTotal < 0.0f) {
            this.lastTotal = currentTotal;
            return;
        }
        float delta = this.lastTotal - currentTotal;
        if (delta > 0.05f) {
            String source = detectSource(player);
            long now = System.currentTimeMillis();
            DamageEntry entry = new DamageEntry(source, delta, now, this.lastDamageMs <= 0L ? -1L : now - this.lastDamageMs);
            pushEntry(entry);
            if (this.chat.get()) {
                ChatMessages.a((Object) ("Damage Log: -" + formatOneDecimal(delta) + " HP, источник: " + source + "."));
            }
            this.lastDamageMs = now;
        }
        this.lastTotal = currentTotal;
    }

    @EventHandler
    public void onHudRender(HudRenderPostEvent hudRenderPostEvent) {
        if (!this.overlay.get() || this.entries.isEmpty()) {
            return;
        }
        Renderer2D renderer = DrunVisual.getInstance().getRender();
        if (renderer == null) {
            return;
        }
        MatrixStack matrices = hudRenderPostEvent.a();
        FontRenderer titleFont = FontManager.MEDIUM[12];
        FontRenderer rowFont = FontManager.MEDIUM[10];
        float screenHeight = c.getWindow().getHeight() / 4.0f;
        float x = this.margin.get();
        List<String> lines = buildLines();
        float width = titleFont.a("DamageLog");
        for (String line : lines) {
            width = Math.max(width, rowFont.a(line));
        }
        width += 14.0f;
        float lineHeight = rowFont.b("A");
        float titleHeight = titleFont.b("DamageLog");
        float height = 12.0f + titleHeight + (lines.size() * (lineHeight + 3.0f));
        float y = screenHeight - this.margin.get() - height;
        renderer.a(x, y, width, height, 7.0f, PANEL_TOP, PANEL_TOP, PANEL_BOTTOM, PANEL_BOTTOM, matrices);
        titleFont.a("DamageLog", x + 7.0f, y + 6.0f, TEXT, matrices);
        float textY = y + 10.0f + titleHeight;
        for (String line2 : lines) {
            rowFont.a(line2, x + 7.0f, textY, MUTED, matrices);
            textY += lineHeight + 3.0f;
        }
    }

    private List<String> buildLines() {
        List<String> lines = new ArrayList();
        Iterator<DamageEntry> iterator = this.entries.iterator();
        while (iterator.hasNext()) {
            DamageEntry entry = iterator.next();
            StringBuilder line = new StringBuilder();
            line.append("-").append(formatOneDecimal(entry.amount)).append(" HP");
            line.append(" • ").append(entry.source);
            line.append(" • ").append(formatAge(entry.timeMs));
            if (entry.sincePreviousMs > 0L) {
                line.append(" • +").append(formatSeconds(entry.sincePreviousMs));
            }
            lines.add(line.toString());
        }
        return lines;
    }

    private String detectSource(ClientPlayerEntity player) {
        Entity attacker = player.getAttacker();
        if (attacker instanceof PlayerEntity) {
            return "игрок " + attacker.getName().getString();
        }
        if (attacker != null) {
            return attacker.getName().getString();
        }
        if (player.fallDistance > 3.0f) {
            return "падение";
        }
        if (player.isOnFire() || player.isInLava()) {
            return "огонь";
        }
        if (player.isSubmergedInWater() && player.getAir() <= 0) {
            return "удушье";
        }
        if (hasExplosionThreat(player)) {
            return "взрыв";
        }
        return "неизвестно";
    }

    private boolean hasExplosionThreat(ClientPlayerEntity player) {
        Box box = player.getBoundingBox().expand(6.0d);
        for (Entity entity : c.world.getOtherEntities(player, box)) {
            if ((entity instanceof TntEntity) || (entity instanceof CreeperEntity) || (entity instanceof EndCrystalEntity)) {
                return true;
            }
        }
        return false;
    }

    private void pushEntry(DamageEntry entry) {
        this.entries.addFirst(entry);
        while (this.entries.size() > this.historySize.roundedInt()) {
            this.entries.removeLast();
        }
    }

    private String formatOneDecimal(float value) {
        return String.format("%.1f", Float.valueOf(value));
    }

    private String formatAge(long timeMs) {
        return formatSeconds(System.currentTimeMillis() - timeMs) + " назад";
    }

    private String formatSeconds(long ms) {
        return String.format("%.1fs", Double.valueOf(ms / 1000.0d));
    }

    @Override
    public void f() {
        super.f();
        this.entries.clear();
        this.lastTotal = -1.0f;
        this.lastDamageMs = -1L;
    }

    private static final class DamageEntry {
        private final String source;
        private final float amount;
        private final long timeMs;
        private final long sincePreviousMs;

        private DamageEntry(String str, float f, long j, long j2) {
            this.source = str;
            this.amount = f;
            this.timeMs = j;
            this.sincePreviousMs = j2;
        }
    }
}
