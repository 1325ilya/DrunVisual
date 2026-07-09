package drunvisual.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Optional;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.ModeSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import ru.drunvisual.DrunVisual;
import drunvisual.animation.AnimationState;
import drunvisual.animation.Easing;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.events.EntityJumpEvent;
import drunvisual.events.WorldRenderEvent;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.render.shader.DrunVisualShaderProgram;
import drunvisual.render.shader.ShaderLibrary;
import drunvisual.util.ColorUtils;

@ModuleInfo(a = "Jump Circles", b = "Отображает круги при прыжке игрока", c = ModuleCategory.VISUALS)
public class JumpCircles extends ClientModule {
    private static final Identifier JUMP_TEXTURE = Identifier.of(DrunVisual.CLIENT_NAME, "textures/jump.png");
    private final ModeSetting a = new ModeSetting("Режим прыжка", new String[]{"Только круг", "Только частицы", "Круг + частицы", "Блоковая волна"}, "Только круг");
    private final BooleanSetting b = new BooleanSetting("Показывать от первого лица", true);
    private final SettingGroup e = new SettingGroup("Круги").a(() -> {
        return Boolean.valueOf(this.a.b("Круг + частицы"));
    });
    private final SliderSetting f = new SliderSetting("Радиус", 1.0f, 0.5f, 2.0f, 0.1f).a(() -> {
        return (this.a.b("Только круг") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting g = new SliderSetting("Скорость", 2.0f, 0.5f, 5.0f, 0.1f).a(() -> {
        return (this.a.b("Только круг") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting h = new BooleanSetting("Цвет клиента", true).a(() -> {
        return (this.a.b("Только круг") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ColorSetting i = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return ((this.a.b("Только круг") || this.a.b("Круг + частицы")) && !this.h.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SettingGroup j = new SettingGroup("Частицы").a(() -> {
        return Boolean.valueOf(this.a.b("Круг + частицы"));
    });
    private final ModeSetting k = new ModeSetting("Тип частиц", new String[]{"Сердце", "Искра", "Снежинка", "Сияние", "Звезда", "Доллар"}, "Сердце").a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting l = new ModeSetting("Режим физики", new String[]{"Реалистичная", "Без коллизий", "Без физики", "Притяжение"}, "Реалистичная").a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting m = new SliderSetting("Количество частиц", 4.0f, 1.0f, 10.0f, 1.0f).a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting n = new SliderSetting("Размер частиц", 0.3f, 0.1f, 0.75f, 0.05f).a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting o = new SliderSetting("Время жизни", 20.0f, 10.0f, 50.0f, 5.0f).a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting p = new SliderSetting("Сила разлёта", 0.3f, 0.15f, 0.35f, 0.01f).a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting q = new BooleanSetting("Цвет клиента", true).a(() -> {
        return (this.a.b("Только частицы") || this.a.b("Круг + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ColorSetting r = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return ((this.a.b("Только частицы") || this.a.b("Круг + частицы")) && !this.q.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SettingGroup s = new SettingGroup("Блоковая волна").a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final SliderSetting t = new SliderSetting("Радиус волны", 10.0f, 6.0f, 35.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final SliderSetting u = new SliderSetting("Скорость волны", 8.0f, 1.0f, 25.0f, 0.5f).a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final SliderSetting v = new SliderSetting("Толщина кольца", 1.5f, 0.5f, 3.0f, 0.1f).a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final BooleanSetting w = new BooleanSetting("Обводка", true).a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final SliderSetting x = new SliderSetting("Толщина линий", 2.0f, 1.0f, 5.0f, 0.5f).a(() -> {
        return Boolean.valueOf(Bool.from((this.a.b("Блоковая волна") && this.w.a()) ? 1 : 0));
    });
    private final BooleanSetting y = new BooleanSetting("Заливка", true).a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final ModeSetting z = new ModeSetting("Тип заливки", new String[]{"Обычная", "Шейдер"}, "Обычная").a(() -> {
        return Boolean.valueOf(Bool.from((this.a.b("Блоковая волна") && this.y.a()) ? 1 : 0));
    });
    private final SliderSetting A = new SliderSetting("Прозрачность заливки", 0.3f, 0.05f, 0.8f, 0.05f).a(() -> {
        return (this.a.b("Блоковая волна") && this.y.a() && this.z.b("Обычная")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting B = new ModeSetting("Шейдер", new String[]{"Небула", "Звёзды", "Паутина", "Плазма"}, "Небула").a(() -> {
        return (this.a.b("Блоковая волна") && this.y.a() && this.z.b("Шейдер")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting C = new SliderSetting("Скорость шейдера", 1.0f, 0.1f, 3.0f, 0.1f).a(() -> {
        return (this.a.b("Блоковая волна") && this.y.a() && this.z.b("Шейдер")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting D = new SliderSetting("Прозрачность шейдера", 1.0f, 0.1f, 1.0f, 0.05f).a(() -> {
        return (this.a.b("Блоковая волна") && this.y.a() && this.z.b("Шейдер")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting E = new BooleanSetting("Цвет клиента", true).a(() -> {
        return Boolean.valueOf(this.a.b("Блоковая волна"));
    });
    private final ColorSetting F = new ColorSetting("Кастомный цвет", Color.CYAN).a(() -> {
        return Boolean.valueOf(Bool.from((!this.a.b("Блоковая волна") || this.E.a()) ? 0 : 1));
    });
    private final Identifier G = Identifier.of(DrunVisual.CLIENT_NAME, "textures/jump.png");
    private final Queue<PendingCircle> H = new LinkedBlockingQueue();
    private final Queue<CircleInstance> I = new LinkedBlockingQueue();
    private final Queue<CircleInstance> J = new LinkedBlockingQueue();

    private class CircleInstance {
        final Vec3d a;
        final AnimationState b = new AnimationState();
        final AnimationState c = new AnimationState();

        CircleInstance(JumpCircles jumpCircles, Vec3d Vec3dVar, float f, float f2) {
            this.a = Vec3dVar;
            this.b.d(0.0d);
            this.b.a(f, f2, Easing.f);
            this.c.d(1.0d);
            this.c.a(0.0d, f2, Easing.s);
        }

        boolean a() {
            this.b.a();
            this.c.a();
            return Bool.from((this.b.b() || this.c.b()) ? 1 : 0);
        }

        boolean b() {
            return Bool.from((this.b.d() && this.c.d()) ? 1 : 0);
        }

        float c() {
            return (float) this.b.j();
        }

        float d() {
            return (float) this.c.j();
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class PendingCircle {
        final Vec3d a;
        final long b;

        PendingCircle(Vec3d Vec3dVar, long j) {
            this.a = Vec3dVar;
            this.b = j;
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    @EventHandler
    public void a(EntityJumpEvent entityJumpEvent) {
        if (!handleJumpEvent(entityJumpEvent) && entityJumpEvent.a() == c.player) {
            if (!c.options.getPerspective().isFirstPerson() || this.b.k().booleanValue()) {
                String strD = this.a.d();
                if (strD.equals("Только круг") || strD.equals("Круг + частицы")) {
                    addCircle(c.player.getPos().add(0.0d, 0.009999999776482582d, 0.0d));
                }
                if (strD.equals("Только частицы") || strD.equals("Круг + частицы")) {
                    Vec3d Vec3dVar = new Vec3d(c.player.getX(), c.player.getY() + 0.1d, c.player.getZ());
                    int iB = this.m.b() * 2;
                    for (int i = 0; i < iB; i++) {
                        a(Vec3dVar, 0.15d, o().getRGB());
                    }
                }
                if (strD.equals("Блоковая волна")) {
                    float fA = this.t.a();
                    this.I.add(new CircleInstance(this, c.player.getPos(), fA, fA / this.u.a()));
                }
            }
        }
    }

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        b(worldRenderEvent);
        renderJumpTexture(worldRenderEvent);
    }

    private boolean handleJumpEvent(EntityJumpEvent entityJumpEvent) {
        if (c == null || c.player == null || c.options == null || entityJumpEvent == null || entityJumpEvent.a() != c.player) {
            return true;
        }
        if (c.options.getPerspective().isFirstPerson() && !this.b.a()) {
            return true;
        }
        Vec3d Vec3dVarGetPos = c.player.getPos();
        if (this.a.a(0) || this.a.a(2)) {
            addCircle(Vec3dVarGetPos.add(0.0d, 0.009999999776482582d, 0.0d));
        }
        if (this.a.a(1) || this.a.a(2)) {
            spawnJumpParticles(Vec3dVarGetPos.add(0.0d, 0.1d, 0.0d));
        }
        if (!this.a.a(3)) {
            return true;
        }
        addBlockWave(Vec3dVarGetPos);
        return true;
    }

    private void addCircle(Vec3d Vec3dVar) {
        float fA = this.f.a();
        this.J.add(new CircleInstance(this, Vec3dVar, fA, fA / Math.max(0.05f, this.g.a())));
    }

    private void spawnJumpParticles(Vec3d Vec3dVar) {
        int iA = ColorUtils.a(o().getRGB());
        int iMax = Math.max(1, this.m.b());
        int iMax2 = Math.max(1, this.o.b());
        int iMax3 = Math.max(1, iMax2 / 5);
        float fMax = Math.max(0.01f, this.n.a());
        double dMax = Math.max(0.005d, this.p.a());
        Identifier IdentifierVarParticleTexture = particleTexture();
        String strD = this.l.d();
        for (int i = 0; i < iMax; i++) {
            Vec3d Vec3dVar2 = new Vec3d(ThreadLocalRandom.current().nextDouble(-1.0d, 1.0d), ThreadLocalRandom.current().nextDouble(0.15d, 1.0d), ThreadLocalRandom.current().nextDouble(-1.0d, 1.0d));
            if (Vec3dVar2.lengthSquared() > 0.0d) {
                Vec3dVar2 = Vec3dVar2.normalize().multiply(ThreadLocalRandom.current().nextDouble(0.005d, dMax));
            }
            HudServiceRegistry.PARTICLES.a(Vec3dVar, Vec3dVar2, Math.max(1, iMax2 + ThreadLocalRandom.current().nextInt(-iMax3, iMax3 + 1)), Math.max(0.01f, fMax + ((ThreadLocalRandom.current().nextFloat() - 0.5f) * 0.08f)), IdentifierVarParticleTexture, iA, strD);
        }
    }

    private Identifier particleTexture() {
        switch (this.k.k().intValue()) {
            case ConfigState.a /* 1 */:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/sparkle.png");
            case 2:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/snowflake.png");
            case 3:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/glow.png");
            case 4:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/star.png");
            case 5:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/dollar.png");
            default:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/heart.png");
        }
    }

    private void addBlockWave(Vec3d Vec3dVar) {
        float fA = this.t.a();
        this.I.add(new CircleInstance(this, Vec3dVar, fA, fA / Math.max(0.05f, this.u.a())));
    }

    private void renderJumpTexture(WorldRenderEvent worldRenderEvent) {
        if (c.world == null || this.J.isEmpty()) {
            return;
        }
        Matrix4f matrix4fGetPositionMatrix = worldRenderEvent.a().peek().getPositionMatrix();
        Vec3d Vec3dVarGetPos = c.getEntityRenderDispatcher().camera.getPos();
        Color colorN = n();
        float red = colorN.getRed() / 255.0f;
        float green = colorN.getGreen() / 255.0f;
        float blue = colorN.getBlue() / 255.0f;
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderTexture(0, JUMP_TEXTURE);
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        BufferBuilder BufferBuilderVarBegin = null;
        for (CircleInstance circleInstance : this.J) {
            circleInstance.a();
            if (circleInstance.b()) {
                arrayList.add(circleInstance);
            } else {
                float fC = circleInstance.c();
                float fD = circleInstance.d();
                if (fC > 0.01f && fD > 0.01f) {
                    if (BufferBuilderVarBegin == null) {
                        BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
                    }
                    Vec3d Vec3dVar = circleInstance.a;
                    float f = (float) ((Vec3dVar.x - ((double) fC)) - Vec3dVarGetPos.x);
                    float f2 = (float) ((Vec3dVar.x + ((double) fC)) - Vec3dVarGetPos.x);
                    float f3 = (float) ((Vec3dVar.y + 0.025d) - Vec3dVarGetPos.y);
                    float f4 = (float) ((Vec3dVar.z - ((double) fC)) - Vec3dVarGetPos.z);
                    float f5 = (float) ((Vec3dVar.z + ((double) fC)) - Vec3dVarGetPos.z);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f3, f4).texture(0.0f, 0.0f).color(red, green, blue, fD);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f3, f5).texture(0.0f, 1.0f).color(red, green, blue, fD);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f2, f3, f5).texture(1.0f, 1.0f).color(red, green, blue, fD);
                    BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f2, f3, f4).texture(1.0f, 0.0f).color(red, green, blue, fD);
                    z = true;
                }
            }
        }
        if (z && BufferBuilderVarBegin != null) {
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        this.J.removeAll(arrayList);
    }

    private void a(Vec3d Vec3dVar, double d, int i) {
        int iA = ColorUtils.a(i);
        int iB = this.m.b();
        int iB2 = this.o.b();
        int i2 = (iB2 & (-11)) - ((iB2 ^ (-1)) & 10);
        int i3 = (iB2 ^ 10) + (2 * (iB2 & 10));
        float fA = this.n.a();
        float f = fA - 0.05f;
        float f2 = fA + 0.05f;
        double dA = this.p.a();
        String strD = this.l.d();
        for (int i4 = 0; i4 < iB; i4++) {
            int iNextInt = ThreadLocalRandom.current().nextInt(i2, i3);
            float fNextFloat = f + (ThreadLocalRandom.current().nextFloat() * (f2 - f));
            Vec3d Vec3dVar2 = new Vec3d(ThreadLocalRandom.current().nextDouble(-d, d), ThreadLocalRandom.current().nextDouble(0.0d, d), ThreadLocalRandom.current().nextDouble(-d, d));
            if (Vec3dVar2.lengthSquared() > 0.0d) {
                Vec3dVar2 = Vec3dVar2.normalize().multiply(ThreadLocalRandom.current().nextDouble(0.005d, dA));
            }
            HudServiceRegistry.PARTICLES.a(Vec3dVar, Vec3dVar2, iNextInt, fNextFloat, p(), iA, strD);
        }
    }

    private Color n() {
        return this.h.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.i.a();
    }

    private Color o() {
        return this.q.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.r.a();
    }

    private Identifier p() {
        switch (this.k.d()) {
            case "Сердце":
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/heart.png");
            case "Искра":
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/sparkle.png");
            case "Снежинка":
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/snowflake.png");
            case "Сияние":
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/glow.png");
            case "Звезда":
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/star.png");
            case "Доллар":
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/dollar.png");
            default:
                return Identifier.of(DrunVisual.CLIENT_NAME, "textures/particle/heart.png");
        }
    }

    private float a(float f) {
        if (f == 1.0f) {
            return 1.0f;
        }
        return 1.0f - ((float) Math.pow(2.0d, (-10.0f) * f));
    }

    private void b(WorldRenderEvent worldRenderEvent) {
        if (c.world != null) {
            Vec3d Vec3dVarGetPos = c.getEntityRenderDispatcher().camera.getPos();
            worldRenderEvent.a().push();
            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(770, 771, 1, 0);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            Matrix4f matrix4fGetPositionMatrix = worldRenderEvent.a().peek().getPositionMatrix();
            Color colorR = r();
            float red = colorR.getRed() / 255.0f;
            float green = colorR.getGreen() / 255.0f;
            float blue = colorR.getBlue() / 255.0f;
            float fA = this.v.a();
            float fA2 = this.A.a();
            int i = (!this.y.a() || this.z.b("Шейдер")) ? 0 : 1;
            boolean zA = this.w.a();
            int i2 = (this.y.a() && this.z.b("Шейдер")) ? 1 : 0;
            double d = 0.005d;
            this.I.removeIf(circleInstance -> {
                circleInstance.a();
                if (circleInstance.b()) {
                    return true;
                }
                float fC = circleInstance.c();
                if (fC < 0.1f) {
                    return false;
                }
                float fMax = Math.max(0.0f, fC - fA);
                float f = fMax * fMax;
                float f2 = fC * fC;
                float fD = circleInstance.d();
                BlockPos BlockPosVarOfFloored = BlockPos.ofFloored(circleInstance.a);
                int iCeil = (int) Math.ceil(fC);
                int i3 = (iCeil & (-2)) + (1 & (iCeil ^ (-1))) + (2 * (iCeil & 1));
                ArrayList<float[]> arrayList = Bool.from(i) ? new ArrayList() : null;
                ArrayList<float[]> arrayList2 = zA ? new ArrayList() : null;
                ArrayList<Object[]> arrayList3 = Bool.from(i2) ? new ArrayList() : null;
                for (int i4 = (i3 - 1) ^ (-1); i4 <= i3; i4++) {
                    for (int i5 = -i3; i5 <= i3; i5++) {
                        int iGetX = BlockPosVarOfFloored.getX();
                        int i6 = i4;
                        double d2 = (((double) ((iGetX ^ i6) + (2 * (iGetX & i6)))) + 0.5d) - circleInstance.a.x;
                        double dGetZ = (((double) ((BlockPosVarOfFloored.getZ() - (i5 ^ (-1))) - 1)) + 0.5d) - circleInstance.a.z;
                        double d3 = (d2 * d2) + (dGetZ * dGetZ);
                        if (d3 <= f2) {
                            double dSqrt = Math.sqrt(Math.max(0.0d, ((double) f2) - d3));
                            int iFloor = (int) Math.floor(-dSqrt);
                            int iCeil2 = (int) Math.ceil(dSqrt);
                            for (int i7 = iFloor; i7 <= iCeil2; i7++) {
                                int iGetY = BlockPosVarOfFloored.getY();
                                int i8 = i7;
                                double d4 = (((double) (((iGetY & (i8 ^ (-1))) + (i8 & (iGetY ^ (-1)))) + (2 * (iGetY & i8)))) + 0.5d) - circleInstance.a.y;
                                double d5 = d3 + (d4 * d4);
                                if (d5 >= f && d5 <= f2) {
                                    BlockPos BlockPosVarAdd = BlockPosVarOfFloored.add(i4, i7, i5);
                                    BlockState BlockStateVarGetBlockState = c.world.getBlockState(BlockPosVarAdd);
                                    if (!BlockStateVarGetBlockState.isAir() && BlockStateVarGetBlockState.isOpaqueFullCube() && a(BlockPosVarAdd)) {
                                        try {
                                            VoxelShape VoxelShapeVarGetOutlineShape = BlockStateVarGetBlockState.getOutlineShape(c.world, BlockPosVarAdd);
                                            if (!VoxelShapeVarGetOutlineShape.isEmpty()) {
                                                Box BoxVarOffset = VoxelShapeVarGetOutlineShape.getBoundingBox().expand(d).offset(((double) BlockPosVarAdd.getX()) - Vec3dVarGetPos.x, ((double) BlockPosVarAdd.getY()) - Vec3dVarGetPos.y, ((double) BlockPosVarAdd.getZ()) - Vec3dVarGetPos.z);
                                                float fMax2 = Math.max(0.1f, fD * (1.0f - (Math.abs((fA > 0.0f ? (float) ((Math.sqrt(d5) - ((double) fMax)) / ((double) fA)) : 0.5f) - 0.5f) * 2.0f)));
                                                if (Bool.from(i)) {
                                                    float[] fArr = new float[7];
                                                    fArr[0] = (float) BoxVarOffset.minX;
                                                    fArr[1] = (float) BoxVarOffset.minY;
                                                    fArr[2] = (float) BoxVarOffset.minZ;
                                                    fArr[3] = (float) BoxVarOffset.maxX;
                                                    fArr[4] = (float) BoxVarOffset.maxY;
                                                    fArr[0 | 5] = (float) BoxVarOffset.maxZ;
                                                    fArr[6] = fA2 * fMax2;
                                                    arrayList.add(fArr);
                                                }
                                                if (zA) {
                                                    float[] fArr2 = new float[7];
                                                    fArr2[0] = (float) BoxVarOffset.minX;
                                                    fArr2[1] = (float) BoxVarOffset.minY;
                                                    fArr2[2] = (float) BoxVarOffset.minZ;
                                                    fArr2[2 | 1] = (float) BoxVarOffset.maxX;
                                                    fArr2[4] = (float) BoxVarOffset.maxY;
                                                    fArr2[5] = (float) BoxVarOffset.maxZ;
                                                    fArr2[6] = fMax2;
                                                    arrayList2.add(fArr2);
                                                }
                                                if (Bool.from(i2)) {
                                                    arrayList3.add(new Object[]{BoxVarOffset, Float.valueOf(fMax2)});
                                                }
                                            }
                                        } catch (Exception e) {
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                if (Bool.from(i) && !arrayList.isEmpty()) {
                    RenderSystem.disableCull();
                    RenderSystem.depthMask(false);
                    RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
                    BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
                    for (float[] fArr3 : arrayList) {
                        a(BufferBuilderVarBegin, matrix4fGetPositionMatrix, fArr3[0], fArr3[1], fArr3[2], fArr3[3], fArr3[4], fArr3[5], red, green, blue, fArr3[6]);
                    }
                    BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
                    RenderSystem.depthMask(true);
                    RenderSystem.enableCull();
                }
                if (Bool.from(i2) && !arrayList3.isEmpty()) {
                    for (Object[] objArr : arrayList3) {
                        a(worldRenderEvent.a(), (Box) objArr[0], colorR, ((Float) objArr[1]).floatValue());
                    }
                }
                if (zA && !arrayList2.isEmpty()) {
                    RenderSystem.lineWidth(this.x.a());
                    RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
                    GL11.glEnable(2848);
                    GL11.glHint(3154, 4354);
                    BufferBuilder BufferBuilderVarMethod_608272 = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);
                    for (float[] fArr4 : arrayList2) {
                        b(BufferBuilderVarMethod_608272, matrix4fGetPositionMatrix, fArr4[0], fArr4[1], fArr4[2], fArr4[3], fArr4[4], fArr4[5], red, green, blue, fArr4[6]);
                    }
                    BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
                    GL11.glDisable(2848);
                }
                return false;
            });
            RenderSystem.disableBlend();
            worldRenderEvent.a().pop();
        }
    }

    private void a(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        BufferBuilderVar.vertex(matrix4f, f, f5, f3).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f, f5, f6).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f4, f5, f6).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f4, f5, f3).color(f7, f8, f9, f10);
    }

    private void b(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10) {
        BufferBuilderVar.vertex(matrix4f, f, f5, f3).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f4, f5, f3).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f4, f5, f3).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f4, f5, f6).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f4, f5, f6).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f, f5, f6).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f, f5, f6).color(f7, f8, f9, f10);
        BufferBuilderVar.vertex(matrix4f, f, f5, f3).color(f7, f8, f9, f10);
    }

    private void a(MatrixStack MatrixStackVar, Box BoxVar, Color color, float f) {
        Optional<DrunVisualShaderProgram> optionalFind = ShaderLibrary.getRegistry().find(q());
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        if (!optionalFind.isPresent()) {
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
            b(BufferBuilderVarBegin, matrix4fGetPositionMatrix, BoxVar, color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, this.A.a() * f);
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            return;
        }
        DrunVisualShaderProgram pulseShaderProgram = optionalFind.get();
        float fCurrentTimeMillis = ((System.currentTimeMillis() % 100000) / 1000.0f) * this.C.a();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        pulseShaderProgram.d();
        pulseShaderProgram.a("time", fCurrentTimeMillis);
        pulseShaderProgram.a("screenSize", c.getWindow().getFramebufferWidth(), c.getWindow().getFramebufferHeight());
        pulseShaderProgram.a("baseColor", color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, 1.0f);
        pulseShaderProgram.a("alpha", this.D.a() * f);
        float f2 = (float) BoxVar.minX;
        float f3 = (float) BoxVar.minZ;
        float f4 = (float) BoxVar.maxX;
        float f5 = (float) BoxVar.maxY;
        float f6 = (float) BoxVar.maxZ;
        BufferBuilder BufferBuilderVarMethod_608272 = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f2, f5, f3).texture(0.0f, 0.0f);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f2, f5, f6).texture(0.0f, 1.0f);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f4, f5, f6).texture(1.0f, 1.0f);
        BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, f4, f5, f3).texture(1.0f, 0.0f);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
        pulseShaderProgram.e();
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
    }

    private String q() {
        switch (this.B.d()) {
            case "Звёзды":
                return "block_starfield";
            case "Паутина":
                return "block_cobweb";
            case "Плазма":
                return "block_plasma";
            default:
                return "block_nebula";
        }
    }

    private void a(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Box BoxVar, float f, float f2, float f3, float f4) {
        float f5 = (float) BoxVar.minX;
        float f6 = (float) BoxVar.minZ;
        float f7 = (float) BoxVar.maxX;
        float f8 = (float) BoxVar.maxY;
        float f9 = (float) BoxVar.maxZ;
        BufferBuilderVar.vertex(matrix4f, f5, f8, f6).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f7, f8, f6).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f7, f8, f6).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f7, f8, f9).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f7, f8, f9).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f5, f8, f9).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f5, f8, f9).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f5, f8, f6).color(f, f2, f3, f4);
    }

    private void b(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Box BoxVar, float f, float f2, float f3, float f4) {
        float f5 = (float) BoxVar.minX;
        float f6 = (float) BoxVar.minZ;
        float f7 = (float) BoxVar.maxX;
        float f8 = (float) BoxVar.maxY;
        float f9 = (float) BoxVar.maxZ;
        BufferBuilderVar.vertex(matrix4f, f5, f8, f6).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f5, f8, f9).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f7, f8, f9).color(f, f2, f3, f4);
        BufferBuilderVar.vertex(matrix4f, f7, f8, f6).color(f, f2, f3, f4);
    }

    private Color r() {
        return this.E.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.F.a();
    }

    private boolean a(BlockPos BlockPosVar) {
        BlockState BlockStateVarGetBlockState = c.world.getBlockState(BlockPosVar.up());
        return Bool.from((BlockStateVarGetBlockState.isAir() || !BlockStateVarGetBlockState.isOpaqueFullCube()) ? 1 : 0);
    }

    @Override
    public void f() {
        super.f();
        this.H.clear();
        this.I.clear();
        this.J.clear();
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
