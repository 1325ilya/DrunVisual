package drunvisual.modules.utilities;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import org.joml.Matrix4f;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.HudRenderPostEvent;
import drunvisual.events.WorldRenderEvent;
import drunvisual.media.chat.ChatMessages;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.render.Renderer2D;
import drunvisual.render.font.FontManager;
import drunvisual.render.font.FontRenderer;
import drunvisual.render.world.WorldRenderUtils;
import drunvisual.render.world.WorldToScreen;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.SliderSetting;
import ru.drunvisual.DrunVisual;

@ModuleInfo(a = "PearlTracker", b = "Отслеживает чужие эндер-пёрлы и точку предполагаемого приземления.", c = ModuleCategory.UTILITIES)
public class PearlTracker extends ClientModule {
    private static final int MAX_STEPS = 120;
    private static final long TRACK_GRACE_MS = 1600L;
    private final BooleanSetting ignoreSelf = new BooleanSetting("Игнорировать себя", true);
    private final BooleanSetting chat = new BooleanSetting("Чат", true);
    private final BooleanSetting labels = new BooleanSetting("Метки", true);
    private final BooleanSetting useClientColor = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting color = new ColorSetting("Цвет", new Color(90, 180, 255)).a(() -> {
        return Boolean.valueOf(!this.useClientColor.get());
    });
    private final SliderSetting maxDistance = new SliderSetting("Дистанция", 96.0f, 24.0f, 160.0f, 4.0f);
    private final SliderSetting lineWidth = new SliderSetting("Толщина линии", 2.0f, 1.0f, 5.0f, 0.25f);
    private final Map<Integer, TrackedPearl> trackedPearls = new HashMap();

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            this.trackedPearls.clear();
            return;
        }
        long now = System.currentTimeMillis();
        Set<Integer> seen = new HashSet();
        double maxDistanceSq = this.maxDistance.get() * this.maxDistance.get();
        for (Entity entity : c.world.getEntities()) {
            if (!(entity instanceof EnderPearlEntity)) {
                continue;
            }
            EnderPearlEntity pearl = (EnderPearlEntity) entity;
            Entity owner = pearl.getOwner();
            if (this.ignoreSelf.get() && owner == c.player) {
                continue;
            }
            if (pearl.squaredDistanceTo(c.player) > maxDistanceSq) {
                continue;
            }
            seen.add(Integer.valueOf(pearl.getId()));
            TrackedPearl trackedPearl = this.trackedPearls.get(Integer.valueOf(pearl.getId()));
            if (trackedPearl == null) {
                trackedPearl = new TrackedPearl(pearl.getId(), ownerName(owner), owner == c.player);
                this.trackedPearls.put(Integer.valueOf(pearl.getId()), trackedPearl);
                if (this.chat.get()) {
                    ChatMessages.a((Object) ("PearlTracker: " + trackedPearl.ownerName + " кинул пёрл."));
                }
            }
            updateTrackedPearl(trackedPearl, pearl, now);
        }
        this.trackedPearls.values().removeIf(trackedPearl2 -> {
            if (seen.contains(Integer.valueOf(trackedPearl2.id))) {
                return false;
            }
            return now - trackedPearl2.lastSeenMs > TRACK_GRACE_MS;
        });
    }

    @EventHandler
    public void onWorldRender(WorldRenderEvent worldRenderEvent) {
        if (c.player == null || c.world == null || this.trackedPearls.isEmpty()) {
            return;
        }
        Color lineColor = activeColor();
        for (TrackedPearl trackedPearl : this.trackedPearls.values()) {
            if (trackedPearl.path.size() < 2 || trackedPearl.impactPos == null) {
                continue;
            }
            renderPath(worldRenderEvent.a(), trackedPearl.path, lineColor);
            WorldRenderUtils.a(worldRenderEvent.a(), trackedPearl.impactPos, 0.45f, 28, lineColor.getRGB(), 0.05f);
        }
    }

    @EventHandler
    public void onHudRender(HudRenderPostEvent hudRenderPostEvent) {
        if (!this.labels.get() || c.player == null || c.world == null || this.trackedPearls.isEmpty()) {
            return;
        }
        Renderer2D renderer = DrunVisual.getInstance().getRender();
        if (renderer == null) {
            return;
        }
        FontRenderer font = FontManager.MEDIUM[10];
        MatrixStack matrices = hudRenderPostEvent.a();
        Color labelColor = activeColor();
        for (TrackedPearl trackedPearl : this.trackedPearls.values()) {
            if (trackedPearl.impactPos == null) {
                continue;
            }
            Vec3d screenPos = WorldToScreen.a(trackedPearl.impactPos.add(0.0d, 1.0d, 0.0d));
            if (screenPos == null || !WorldToScreen.b(screenPos)) {
                continue;
            }
            String text = trackedPearl.ownerName + " • " + formatEta(trackedPearl.etaTicks);
            float width = font.a(text) + 10.0f;
            float x = (float) screenPos.x - (width / 2.0f);
            float y = ((float) screenPos.y) - 10.0f;
            renderer.a(x, y, width, font.b(text) + 6.0f, 5.0f, new Color(8, 10, 14, 205), new Color(8, 10, 14, 205), new Color(6, 8, 12, 225), new Color(6, 8, 12, 225), matrices);
            font.a(text, x + 5.0f, y + 3.0f, labelColor, matrices);
        }
    }

    private void updateTrackedPearl(TrackedPearl trackedPearl, EnderPearlEntity pearl, long now) {
        trackedPearl.lastSeenMs = now;
        trackedPearl.currentPos = pearl.getPos();
        trackedPearl.path.clear();
        trackedPearl.path.add(pearl.getPos());
        Vec3d position = pearl.getPos();
        Vec3d velocity = pearl.getVelocity();
        trackedPearl.impactPos = null;
        trackedPearl.etaTicks = 0;
        for (int i = 0; i < MAX_STEPS; i++) {
            Vec3d next = position.add(velocity);
            HitCandidate hitCandidate = findHit(position, next, pearl);
            trackedPearl.path.add(hitCandidate.pos);
            if (hitCandidate.hit) {
                trackedPearl.impactPos = hitCandidate.pos;
                trackedPearl.etaTicks = i + 1;
                return;
            }
            if (next.y < ((double) c.world.getBottomY()) - 16.0d) {
                break;
            }
            position = next;
            velocity = velocity.multiply(0.99d).subtract(0.0d, 0.03d, 0.0d);
        }
        trackedPearl.impactPos = trackedPearl.path.get(trackedPearl.path.size() - 1);
        trackedPearl.etaTicks = trackedPearl.path.size();
    }

    private HitCandidate findHit(Vec3d from, Vec3d to, EnderPearlEntity pearl) {
        BlockHitResult blockHit = c.world.raycast(new RaycastContext(from, to, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, pearl));
        Vec3d bestPos = to;
        boolean hit = false;
        if (blockHit.getType() != HitResult.Type.MISS) {
            bestPos = blockHit.getPos();
            hit = true;
        }
        EntityHitResult entityHit = findEntityHit(from, bestPos, pearl);
        if (entityHit != null) {
            return new HitCandidate(entityHit.getPos(), true);
        }
        return new HitCandidate(bestPos, hit);
    }

    private EntityHitResult findEntityHit(Vec3d from, Vec3d to, EnderPearlEntity pearl) {
        Entity hitEntity = null;
        Vec3d hitPos = null;
        double bestDistance = Double.MAX_VALUE;
        for (Entity entity : c.world.getOtherEntities(pearl, new Box(from, to).expand(1.0d))) {
            if (!(entity instanceof LivingEntity) || !entity.isAlive() || entity.isSpectator()) {
                continue;
            }
            Optional<Vec3d> raycast = entity.getBoundingBox().expand(0.25d).raycast(from, to);
            if (raycast.isEmpty()) {
                continue;
            }
            double distance = from.squaredDistanceTo((Vec3d) raycast.get());
            if (distance < bestDistance) {
                bestDistance = distance;
                hitEntity = entity;
                hitPos = (Vec3d) raycast.get();
            }
        }
        return hitEntity == null ? null : new EntityHitResult(hitEntity, hitPos);
    }

    private void renderPath(MatrixStack matrices, List<Vec3d> path, Color color) {
        Vec3d cameraPos = c.gameRenderer.getCamera().getPos();
        matrices.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.lineWidth(this.lineWidth.get());
        Matrix4f matrix = matrices.peek().getPositionMatrix();
        BufferBuilder buffer = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        for (int i = 0; i < path.size(); i++) {
            Vec3d relative = ((Vec3d) path.get(i)).subtract(cameraPos);
            float alpha = Math.max(0.25f, 1.0f - (i / ((float) path.size())));
            buffer.vertex(matrix, (float) relative.x, (float) relative.y, (float) relative.z).color(red, green, blue, alpha);
        }
        BufferRenderer.drawWithGlobalProgram(buffer.end());
        RenderSystem.lineWidth(1.0f);
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        matrices.pop();
    }

    private Color activeColor() {
        return this.useClientColor.get() ? ModuleRegistry.CLIENT_COLOR.n() : this.color.getColor();
    }

    private String ownerName(Entity entity) {
        return entity == null ? "Неизвестный" : entity.getName().getString();
    }

    private String formatEta(int ticks) {
        return String.format("%.1fs", Double.valueOf(ticks / 20.0d));
    }

    @Override
    public void f() {
        super.f();
        this.trackedPearls.clear();
    }

    private static final class TrackedPearl {
        private final int id;
        private final String ownerName;
        private final boolean selfOwned;
        private final List<Vec3d> path = new ArrayList();
        private Vec3d currentPos;
        private Vec3d impactPos;
        private int etaTicks;
        private long lastSeenMs;

        private TrackedPearl(int i, String str, boolean z) {
            this.id = i;
            this.ownerName = str;
            this.selfOwned = z;
        }
    }

    private static final class HitCandidate {
        private final Vec3d pos;
        private final boolean hit;

        private HitCandidate(Vec3d vec3d, boolean z) {
            this.pos = vec3d;
            this.hit = z;
        }
    }
}
