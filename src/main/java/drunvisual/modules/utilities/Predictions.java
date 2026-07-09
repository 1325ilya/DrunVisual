package drunvisual.modules.utilities;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.Box;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.component.type.ChargedProjectilesComponent;
import net.minecraft.component.DataComponentTypes;
import org.joml.Matrix4f;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.config.ConfigState;
import drunvisual.events.WorldRenderEvent;

@ModuleInfo(a = "Predictions", b = "Predicts projectile trajectories.", c = ModuleCategory.UTILITIES)
public class Predictions extends ClientModule {
    private static final int MAX_STEPS = 220;
    private static final int MARKER_SEGMENTS = 48;
    private final ColorSetting gradientColor;
    private final BooleanSetting fadeOut;
    private final SliderSetting fadeStart;
    private final SettingGroup markerGroup;
    private final BooleanSetting impactMarker;
    private final SliderSetting markerSize;
    private final BooleanSetting markerPulse;
    private final SliderSetting markerPulseSpeed;
    private final ColorSetting hitColor;
    private Vec3d impactPoint;
    private boolean hitEntity;
    private final List<Vec3d> path = new ArrayList();
    private final SettingGroup projectileGroup = new SettingGroup("Projectiles");
    private final BooleanSetting arrows = new BooleanSetting("Arrows", true);
    private final BooleanSetting enderPearls = new BooleanSetting("Ender Pearls", true);
    private final BooleanSetting tridents = new BooleanSetting("Tridents", true);
    private final BooleanSetting potions = new BooleanSetting("Potions", true);
    private final SettingGroup lineGroup = new SettingGroup("Line");
    private final SliderSetting lineWidth = new SliderSetting("Line Width", 2.0f, 1.0f, 6.0f, 0.25f);
    private final BooleanSetting useClientColor = new BooleanSetting("Use Client Color", true);
    private final ColorSetting lineColor = new ColorSetting("Line Color", new Color(120, 80, 255)).a(() -> {
        return Boolean.valueOf(!this.useClientColor.a());
    });
    private final BooleanSetting gradient = new BooleanSetting("Gradient", true);

    private enum ProjectileType {
        ENDER_PEARL(1.5d, 0.99d, 0.03d),
        TRIDENT(2.5d, 0.99d, 0.05d),
        ARROW(3.0d, 0.99d, 0.05d),
        POTION(0.5d, 0.99d, 0.05d);

        private final double baseSpeed;
        private final double drag;
        private final double gravity;

        ProjectileType(double d, double d2, double d3) {
            this.baseSpeed = d;
            this.drag = d2;
            this.gravity = d3;
        }

        private static ProjectileType from(ItemStack ItemStackVar) {
            if (ItemStackVar == null || ItemStackVar.isEmpty()) {
                return null;
            }
            Item ItemVarGetItem = ItemStackVar.getItem();
            if (ItemVarGetItem == Items.ENDER_PEARL) {
                return ENDER_PEARL;
            }
            if (ItemVarGetItem == Items.TRIDENT) {
                return TRIDENT;
            }
            if (ItemVarGetItem == Items.BOW || ItemVarGetItem == Items.CROSSBOW) {
                return ARROW;
            }
            if (ItemVarGetItem == Items.SPLASH_POTION || ItemVarGetItem == Items.LINGERING_POTION) {
                return POTION;
            }
            return null;
        }
    }

    public Predictions() {
        ColorSetting colorSetting = new ColorSetting("Gradient Color", new Color(80, 180, 255));
        BooleanSetting booleanSetting = this.gradient;
        Objects.requireNonNull(booleanSetting);
        this.gradientColor = colorSetting.a(booleanSetting::a);
        this.fadeOut = new BooleanSetting("Fade Out", true);
        SliderSetting sliderSetting = new SliderSetting("Fade Start", 0.65f, 0.1f, 0.95f, 0.05f);
        BooleanSetting booleanSetting2 = this.fadeOut;
        Objects.requireNonNull(booleanSetting2);
        this.fadeStart = sliderSetting.a(booleanSetting2::a);
        this.markerGroup = new SettingGroup("Impact Marker");
        this.impactMarker = new BooleanSetting("Impact Marker", true);
        SliderSetting sliderSetting2 = new SliderSetting("Marker Size", 0.35f, 0.1f, 1.2f, 0.05f);
        BooleanSetting booleanSetting3 = this.impactMarker;
        Objects.requireNonNull(booleanSetting3);
        this.markerSize = sliderSetting2.a(booleanSetting3::a);
        BooleanSetting booleanSetting4 = new BooleanSetting("DrunVisual", true);
        BooleanSetting booleanSetting5 = this.impactMarker;
        Objects.requireNonNull(booleanSetting5);
        this.markerPulse = booleanSetting4.a(booleanSetting5::a);
        this.markerPulseSpeed = new SliderSetting("DrunVisual Speed", 1.0f, 0.2f, 3.0f, 0.1f).a(() -> {
            return Boolean.valueOf(this.impactMarker.a() && this.markerPulse.a());
        });
        ColorSetting colorSetting2 = new ColorSetting("Hit Color", new Color(255, 85, 110));
        BooleanSetting booleanSetting6 = this.impactMarker;
        Objects.requireNonNull(booleanSetting6);
        this.hitColor = colorSetting2.a(booleanSetting6::a);
    }

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        if (c.player == null || c.world == null || c.gameRenderer == null) {
            clearPrediction();
            return;
        }
        ItemStack ItemStackVarCurrentProjectileStack = currentProjectileStack();
        ProjectileType projectileTypeFrom = ProjectileType.from(ItemStackVarCurrentProjectileStack);
        if (projectileTypeFrom == null || !isEnabled(projectileTypeFrom) || !isReady(ItemStackVarCurrentProjectileStack, projectileTypeFrom)) {
            clearPrediction();
            return;
        }
        simulate(worldRenderEvent.b(), ItemStackVarCurrentProjectileStack, projectileTypeFrom);
        if (this.path.size() > 1) {
            render(worldRenderEvent);
        }
    }

    private ItemStack currentProjectileStack() {
        ItemStack ItemStackVarGetMainHandStack = c.player.getMainHandStack();
        if (ProjectileType.from(ItemStackVarGetMainHandStack) != null) {
            return ItemStackVarGetMainHandStack;
        }
        ItemStack ItemStackVarGetOffHandStack = c.player.getOffHandStack();
        return ProjectileType.from(ItemStackVarGetOffHandStack) != null ? ItemStackVarGetOffHandStack : ItemStack.EMPTY;
    }

    private boolean isEnabled(ProjectileType projectileType) throws MatchException {
        switch (projectileType.ordinal()) {
            case EventPriority.MEDIUM /* 0 */:
                return this.enderPearls.a();
            case ConfigState.a /* 1 */:
                return this.tridents.a();
            case 2:
                return this.arrows.a();
            case 3:
                return this.potions.a();
            default:
                throw new MatchException((String) null, (Throwable) null);
        }
    }

    private boolean isReady(ItemStack ItemStackVar, ProjectileType projectileType) {
        Item ItemVarGetItem = ItemStackVar.getItem();
        if (ItemVarGetItem == Items.BOW || ItemVarGetItem == Items.TRIDENT) {
            return c.player.isUsingItem() && c.player.getItemUseTimeLeft() > 0;
        }
        if (ItemVarGetItem != Items.CROSSBOW) {
            return projectileType == ProjectileType.ENDER_PEARL || projectileType == ProjectileType.POTION;
        }
        ChargedProjectilesComponent ChargedProjectilesComponentVar = (ChargedProjectilesComponent) ItemStackVar.get(DataComponentTypes.CHARGED_PROJECTILES);
        return (ChargedProjectilesComponentVar == null || ChargedProjectilesComponentVar.isEmpty()) ? false : true;
    }

    private void simulate(float f, ItemStack ItemStackVar, ProjectileType projectileType) {
        this.path.clear();
        this.impactPoint = null;
        this.hitEntity = false;
        Vec3d Vec3dVarGetCameraPosVec = c.player.getCameraPosVec(f);
        Vec3d Vec3dVarInitialVelocity = initialVelocity(ItemStackVar, projectileType);
        this.path.add(Vec3dVarGetCameraPosVec);
        for (int i = 0; i < MAX_STEPS; i++) {
            Vec3d Vec3dVarAdd = Vec3dVarGetCameraPosVec.add(Vec3dVarInitialVelocity);
            BlockHitResult BlockHitResultVarRaycast = c.world.raycast(new RaycastContext(Vec3dVarGetCameraPosVec, Vec3dVarAdd, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, c.player));
            boolean z = false;
            if (BlockHitResultVarRaycast.getType() != HitResult.Type.MISS) {
                Vec3dVarAdd = BlockHitResultVarRaycast.getPos();
                z = true;
            }
            EntityHitResult EntityHitResultVarFindEntityHit = findEntityHit(Vec3dVarGetCameraPosVec, Vec3dVarAdd);
            if (EntityHitResultVarFindEntityHit != null) {
                Vec3dVarAdd = EntityHitResultVarFindEntityHit.getPos();
                this.hitEntity = true;
                z = true;
            }
            this.path.add(Vec3dVarAdd);
            if (z) {
                this.impactPoint = Vec3dVarAdd;
                return;
            } else {
                if (Vec3dVarAdd.y < ((double) c.world.getBottomY()) - 16.0d) {
                    return;
                }
                Vec3dVarGetCameraPosVec = Vec3dVarAdd;
                Vec3dVarInitialVelocity = Vec3dVarInitialVelocity.multiply(projectileType.drag).subtract(0.0d, projectileType.gravity, 0.0d);
            }
        }
    }

    private Vec3d initialVelocity(ItemStack ItemStackVar, ProjectileType projectileType) {
        Vec3d Vec3dVarLookDirection = lookDirection();
        double dMin = projectileType.baseSpeed;
        Item ItemVarGetItem = ItemStackVar.getItem();
        if (ItemVarGetItem == Items.BOW) {
            int iGetMaxUseTime = ItemStackVar.getMaxUseTime(c.player) - c.player.getItemUseTimeLeft();
            dMin = 3.0d * ((double) Math.min(1.0f, Math.max(0.05f, ((((iGetMaxUseTime / 20.0f) * iGetMaxUseTime) / 20.0f) + ((iGetMaxUseTime / 20.0f) * 2.0f)) / 3.0f)));
        } else if (ItemVarGetItem == Items.TRIDENT) {
            dMin = 2.5d * ((double) Math.min(1.0f, Math.max(0.1f, (ItemStackVar.getMaxUseTime(c.player) - c.player.getItemUseTimeLeft()) / 10.0f)));
        }
        return Vec3dVarLookDirection.multiply(dMin).add(c.player.getVelocity().multiply(0.35d));
    }

    private Vec3d lookDirection() {
        float fGetYaw = c.player.getYaw();
        float fGetPitch = c.player.getPitch();
        float fCos = (float) Math.cos(((double) (-fGetPitch)) * 0.017453292519943295d);
        return new Vec3d(((float) Math.sin((((double) (-fGetYaw)) * 0.017453292519943295d) - 3.141592653589793d)) * fCos, (float) Math.sin(((double) (-fGetPitch)) * 0.017453292519943295d), ((float) Math.cos((((double) (-fGetYaw)) * 0.017453292519943295d) - 3.141592653589793d)) * fCos).normalize();
    }

    private EntityHitResult findEntityHit(Vec3d Vec3dVar, Vec3d Vec3dVar2) {
        Entity ClientPlayerEntityVar = null;
        Vec3d Vec3dVar3 = null;
        double d = Double.MAX_VALUE;
        for (Entity ClientPlayerEntityVar2 : c.world.getOtherEntities(c.player, new Box(Vec3dVar, Vec3dVar2).expand(1.0d))) {
            if (ClientPlayerEntityVar2 != c.player && !ClientPlayerEntityVar2.isSpectator() && ClientPlayerEntityVar2.isAlive() && (ClientPlayerEntityVar2 instanceof LivingEntity)) {
                Optional optionalRaycast = ClientPlayerEntityVar2.getBoundingBox().expand(0.3d).raycast(Vec3dVar, Vec3dVar2);
                if (optionalRaycast.isPresent()) {
                    double dSquaredDistanceTo = Vec3dVar.squaredDistanceTo((Vec3d) optionalRaycast.get());
                    if (dSquaredDistanceTo < d) {
                        d = dSquaredDistanceTo;
                        ClientPlayerEntityVar = ClientPlayerEntityVar2;
                        Vec3dVar3 = (Vec3d) optionalRaycast.get();
                    }
                }
            }
        }
        if (ClientPlayerEntityVar == null) {
            return null;
        }
        return new EntityHitResult(ClientPlayerEntityVar, Vec3dVar3);
    }

    private void render(WorldRenderEvent worldRenderEvent) {
        MatrixStack MatrixStackVarA = worldRenderEvent.a();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        MatrixStackVarA.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.disableDepthTest();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVarA.peek().getPositionMatrix();
        RenderSystem.lineWidth(this.lineWidth.a());
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < this.path.size(); i++) {
            Vec3d Vec3dVar = this.path.get(i);
            float size = this.path.size() <= 1 ? 0.0f : i / (this.path.size() - 1);
            vertex(BufferBuilderVarBegin, matrix4fGetPositionMatrix, Vec3dVar.subtract(Vec3dVarGetPos), pathColor(size), alpha(size));
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.lineWidth(1.0f);
        if (this.impactMarker.a() && this.impactPoint != null) {
            renderImpactMarker(matrix4fGetPositionMatrix, Vec3dVarGetPos);
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableDepthTest();
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        MatrixStackVarA.pop();
    }

    private void renderImpactMarker(Matrix4f matrix4f, Vec3d Vec3dVar) {
        Color colorA = this.hitEntity ? this.hitColor.a() : pathColor(1.0f);
        float fA = this.markerSize.a() * (this.markerPulse.a() ? 1.0f + (0.18f * ((float) Math.sin(System.currentTimeMillis() * 0.006d * ((double) this.markerPulseSpeed.a())))) : 1.0f);
        Vec3d Vec3dVarSubtract = this.impactPoint.subtract(Vec3dVar);
        RenderSystem.lineWidth(2.0f);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < MARKER_SEGMENTS; i++) {
            double d = (6.283185307179586d * ((double) i)) / 48.0d;
            double d2 = (6.283185307179586d * ((double) (i + 1))) / 48.0d;
            vertex(BufferBuilderVarBegin, matrix4f, Vec3dVarSubtract.add(Math.cos(d) * ((double) fA), 0.01d, Math.sin(d) * ((double) fA)), colorA, 0.9f);
            vertex(BufferBuilderVarBegin, matrix4f, Vec3dVarSubtract.add(Math.cos(d2) * ((double) fA), 0.01d, Math.sin(d2) * ((double) fA)), colorA, 0.9f);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.lineWidth(1.0f);
    }

    private Color pathColor(float f) {
        Color colorN = this.useClientColor.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.lineColor.a();
        if (!this.gradient.a()) {
            return colorN;
        }
        Color colorA = this.gradientColor.a();
        return new Color(lerp(colorN.getRed(), colorA.getRed(), f), lerp(colorN.getGreen(), colorA.getGreen(), f), lerp(colorN.getBlue(), colorA.getBlue(), f));
    }

    private float alpha(float f) {
        if (!this.fadeOut.a() || f <= this.fadeStart.a()) {
            return 1.0f;
        }
        return Math.max(0.0f, 1.0f - ((f - this.fadeStart.a()) / (1.0f - this.fadeStart.a())));
    }

    private void clearPrediction() {
        this.path.clear();
        this.impactPoint = null;
        this.hitEntity = false;
    }

    private static int lerp(int i, int i2, float f) {
        return Math.round(i + ((i2 - i) * Math.max(0.0f, Math.min(1.0f, f))));
    }

    private static void vertex(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Vec3d Vec3dVar, Color color, float f) {
        BufferBuilderVar.vertex(matrix4f, (float) Vec3dVar.x, (float) Vec3dVar.y, (float) Vec3dVar.z).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, f);
    }
}
