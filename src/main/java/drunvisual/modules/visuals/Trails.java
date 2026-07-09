package drunvisual.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayerEntity;
import org.joml.Matrix4f;
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
import drunvisual.events.ClientTickEvent;
import drunvisual.events.WorldRenderEvent;
import drunvisual.events.WorldRenderStartEvent;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.util.ColorUtils;

@ModuleInfo(a = "Trails", b = "Отображает след игрока", c = ModuleCategory.VISUALS)
public class Trails extends ClientModule {
    private double x;
    private double y;
    private double z;
    public static int a;
    public static boolean b;
    private final ModeSetting e = new ModeSetting("Режим ходьбы", new String[]{"Только след", "Только частицы", "След + частицы"}, "Только след");
    private final BooleanSetting f = new BooleanSetting("Показывать от первого лица", false);
    private final SettingGroup g = new SettingGroup("След").a(() -> {
        return Boolean.valueOf(this.e.b("След + частицы"));
    });
    private final ModeSetting h = new ModeSetting("Тип следа", new String[]{"Сплошной", "Пунктир", "Затухающий"}, "Сплошной").a(() -> {
        if (this.e.b("Только след")) {
        } else if (!this.e.b("След + частицы")) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    });
    private final SliderSetting i = new SliderSetting("Длина следа", 5.0f, 1.0f, 20.0f, 1.0f).a(() -> {
        return (this.e.b("Только след") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting j = new SliderSetting("Ширина следа", 0.1f, 0.05f, 0.5f, 0.01f).a(() -> {
        return (this.e.b("Только след") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting k = new BooleanSetting("Цвет клиента", true).a(() -> {
        return (this.e.b("Только след") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ColorSetting l = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return ((this.e.b("Только след") || this.e.b("След + частицы")) && !this.k.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SettingGroup m = new SettingGroup("Частицы").a(() -> {
        return Boolean.valueOf(this.e.b("След + частицы"));
    });
    private final ModeSetting n = new ModeSetting("Режим частиц", new String[]{"Под ногами", "По телу"}, "Под ногами").a(() -> {
        return (this.e.b("Только частицы") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting o = new ModeSetting("Тип частиц", new String[]{"Сердце", "Искра", "Снежинка", "Сияние", "Звезда", "Доллар"}, "Сердце").a(() -> {
        return (this.e.b("Только частицы") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final ModeSetting p = new ModeSetting("Режим физики", new String[]{"Реалистичная", "Без коллизий", "Без физики", "Притяжение"}, "Реалистичная").a(() -> {
        return (this.e.b("Только частицы") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting q = new SliderSetting("Количество частиц", 4.0f, 1.0f, 20.0f, 1.0f).a(() -> {
        return (this.e.b("Только частицы") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting r = new SliderSetting("Размер частиц", 0.3f, 0.1f, 0.75f, 0.05f).a(() -> {
        return (this.e.b("Только частицы") || this.e.b("crypt")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting s = new SliderSetting("Время жизни", 20.0f, 10.0f, 50.0f, 5.0f).a(() -> {
        return (this.e.b("Только частицы") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final SliderSetting t = new SliderSetting("Сила разлёта", 0.3f, 0.15f, 0.35f, 0.01f).a(() -> {
        return (this.e.b("Только частицы") || this.e.b("След + частицы")) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting u = new BooleanSetting("Цвет клиента", Bool.from(-950111800)).a(() -> {
        if (this.e.b("Только частицы")) {
        } else if (!this.e.b("След + частицы")) {
            return Boolean.valueOf(false);
        }
        return Boolean.valueOf(true);
    });
    private final ColorSetting v = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return ((this.e.b("Только частицы") || this.e.b("След + частицы")) && !this.u.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final Deque<TrailPoint> w = new ArrayDeque();
    private boolean A = false;

    private static class TrailPoint {
        final Vec3d a;
        final long b;
        public static int c;
        public static boolean d;

        TrailPoint(Vec3d Vec3dVar) {
            this(Vec3dVar, System.currentTimeMillis());
        }

        TrailPoint(Vec3d Vec3dVar, long j) {
            this.a = Vec3dVar;
            this.b = j;
        }

        boolean a(int i) {
            return Bool.from(System.currentTimeMillis() - this.b <= ((long) i) ? 0 : 1);
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    @EventHandler
    public void a(WorldRenderStartEvent worldRenderStartEvent) {
        ClientPlayerEntity ClientPlayerEntityVar;
        String strD = this.e.d();
        if ((!strD.equals("Только след") && !strD.equals("След + частицы")) || (ClientPlayerEntityVar = c.player) == null || c.world == null) {
            return;
        }
        float fA = worldRenderStartEvent.a();
        this.w.removeIf(trailPoint -> {
            return trailPoint.a(500);
        });
        Vec3d Vec3dVar = new Vec3d(MathHelper.lerp(fA, ClientPlayerEntityVar.prevX, ClientPlayerEntityVar.getX()), MathHelper.lerp(fA, ClientPlayerEntityVar.prevY, ClientPlayerEntityVar.getY()), MathHelper.lerp(fA, ClientPlayerEntityVar.prevZ, ClientPlayerEntityVar.getZ()));
        if (this.w.isEmpty() || this.w.getLast().a.squaredDistanceTo(Vec3dVar) > 1.0E-4d) {
            this.w.addLast(new TrailPoint(Vec3dVar));
            while (this.w.size() > this.i.a() * 10.0f) {
                this.w.pollFirst();
            }
        }
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        String strD = this.e.d();
        if ((strD.equals("Только частицы") || strD.equals("След + частицы")) && c.player != null) {
            if (!c.options.getPerspective().isFirstPerson() || this.f.k().booleanValue()) {
                double dGetX = c.player.getX();
                double dGetY = c.player.getY();
                double dGetZ = c.player.getZ();
                if (!this.A) {
                    this.x = dGetX;
                    this.y = dGetY;
                    this.z = dGetZ;
                    this.A = true;
                    return;
                }
                double d = dGetX - this.x;
                double d2 = dGetY - this.y;
                double d3 = dGetZ - this.z;
                double dSqrt = Math.sqrt((d * d) + (d2 * d2) + (d3 * d3));
                this.x = dGetX;
                this.y = dGetY;
                this.z = dGetZ;
                if (dSqrt > 0.01d) {
                    String strD2 = this.n.d();
                    if (strD2.equals("Под ногами")) {
                        a(c.player.getPos(), 0.1d, o().getRGB());
                        return;
                    }
                    if (strD2.equals("По телу")) {
                        double dGetHeight = c.player.getHeight();
                        for (int i = 0; i < 5; i++) {
                            a(c.player.getPos().add(0.0d, (dGetHeight / 4.0d) * ((double) i), 0.0d), 0.08d, o().getRGB());
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        String strD = this.e.d();
        if (!(strD.equals("Только след") || strD.equals("След + частицы")) || c.world == null || c.player == null || this.w.size() < 2) {
            return;
        }
        if (!c.options.getPerspective().isFirstPerson() || this.f.k().booleanValue()) {
            MatrixStack MatrixStackVarA = worldRenderEvent.a();
            MatrixStackVarA.push();
            Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.lineWidth(this.j.a() * 3.0f);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            Matrix4f matrix4fGetPositionMatrix = MatrixStackVarA.peek().getPositionMatrix();
            ArrayList arrayList = new ArrayList(this.w);
            String strD2 = this.h.d();
            if ("Пунктир".equals(strD2)) {
                b(matrix4fGetPositionMatrix, c.player.getHeight(), arrayList, Vec3dVarGetPos);
            } else if ("Затухающий".equals(strD2)) {
                c(matrix4fGetPositionMatrix, c.player.getHeight(), arrayList, Vec3dVarGetPos);
            } else {
                a(matrix4fGetPositionMatrix, c.player.getHeight(), arrayList, Vec3dVarGetPos);
            }
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
            RenderSystem.lineWidth(1.0f);
            MatrixStackVarA.pop();
        }
    }

    private void a(Vec3d Vec3dVar, double d, int i) {
        int iA = ColorUtils.a(i);
        int iB = this.q.b();
        int iB2 = this.s.b();
        int i2 = (2 * (iB2 & (-11))) - (iB2 ^ 10);
        int i3 = (iB2 ^ 10) + (2 * (iB2 & 10));
        float fA = this.r.a();
        float f = fA - 0.05f;
        float f2 = fA + 0.05f;
        double dA = this.t.a();
        String strD = this.p.d();
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

    private void a(Matrix4f matrix4f, float f, List<TrailPoint> list, Vec3d Vec3dVar) {
        a(matrix4f, f, list, Vec3dVar, false);
        a(matrix4f, list, Vec3dVar, false);
        a(matrix4f, a(list, f), Vec3dVar, false);
    }

    private void b(Matrix4f matrix4f, float f, List<TrailPoint> list, Vec3d Vec3dVar) {
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINES, VertexFormats.POSITION_COLOR);
        for (int i = 0; (i - (-2)) - 1 < list.size(); i += 2) {
            a(BufferBuilderVarBegin, matrix4f, list.get(i), list.get((i - (-2)) - 1), Vec3dVar, f, i, list.size());
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    private void c(Matrix4f matrix4f, float f, List<TrailPoint> list, Vec3d Vec3dVar) {
        a(matrix4f, f, list, Vec3dVar, true);
        a(matrix4f, list, Vec3dVar, true);
        a(matrix4f, a(list, f), Vec3dVar, true);
    }

    private void a(Matrix4f matrix4f, float f, List<TrailPoint> list, Vec3d Vec3dVar, boolean z) {
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < list.size(); i++) {
            a(BufferBuilderVarBegin, matrix4f, f, list.get(i).a, Vec3dVar, b(i), a(i, list.size(), z) * 0.5f);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    private void a(Matrix4f matrix4f, List<TrailPoint> list, Vec3d Vec3dVar, boolean z) {
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < list.size(); i++) {
            a(BufferBuilderVarBegin, matrix4f, list.get(i).a, Vec3dVar, b(i), a(i, list.size(), z));
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    private void a(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, TrailPoint trailPoint, TrailPoint trailPoint2, Vec3d Vec3dVar, float f, int i, int i2) {
        float fA = a(i, i2, false);
        Color colorB = b(i);
        a(BufferBuilderVar, matrix4f, trailPoint.a, Vec3dVar, colorB, fA);
        a(BufferBuilderVar, matrix4f, trailPoint2.a, Vec3dVar, colorB, fA);
        a(BufferBuilderVar, matrix4f, trailPoint.a.add(0.0d, f, 0.0d), Vec3dVar, colorB, fA);
        a(BufferBuilderVar, matrix4f, trailPoint2.a.add(0.0d, f, 0.0d), Vec3dVar, colorB, fA);
    }

    private List<TrailPoint> a(List<TrailPoint> list, float f) {
        ArrayList arrayList = new ArrayList(list.size());
        list.forEach(trailPoint -> {
            arrayList.add(new TrailPoint(trailPoint.a.add(0.0d, f, 0.0d), trailPoint.b));
        });
        return arrayList;
    }

    private float a(int i, int i2, boolean z) {
        float f = i2 <= 0 ? 0.0f : ((float) i) / ((float) i2);
        return !z ? f : f * f;
    }

    private Color b(int i) {
        Color colorN = n();
        Color colorN2 = n();
        float fMin = Math.min(1.0f, i / 20.0f);
        return new Color((int) ((colorN.getRed() * (1.0f - fMin)) + (colorN2.getRed() * fMin)), (int) ((colorN.getGreen() * (1.0f - fMin)) + (colorN2.getGreen() * fMin)), (int) ((colorN.getBlue() * (1.0f - fMin)) + (colorN2.getBlue() * fMin)));
    }

    private Color n() {
        return !this.k.a() ? this.l.a() : ModuleRegistry.CLIENT_COLOR.n();
    }

    private Color o() {
        return !this.u.a() ? this.v.a() : ModuleRegistry.CLIENT_COLOR.n();
    }

    private Identifier p() {
        String strD = this.o.d();
        byte b2 = -1;
        switch (strD.hashCode()) {
            case -2018961832:
                break;
            case 860345114:
                break;
            case 934967833:
                break;
            case 1001367009:
                if (strD.equals("Искра")) {
                    if (b) {
                        throw new IllegalAccessError();
                    }
                }
                break;
            case 1224355287:
                break;
            case 1227580930:
                break;
        }
        switch (b2) {
            case EventPriority.MEDIUM /* 0 */:
                return Identifier.of("drunvisual", "textures/particle/heart.png");
            case ConfigState.a /* 1 */:
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
                return Identifier.of("drunvisual", "textures/particle/heart.png");
        }
    }

    private void a(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, float f, Vec3d Vec3dVar, Vec3d Vec3dVar2, Color color, float f2) {
        a(BufferBuilderVar, matrix4f, Vec3dVar, Vec3dVar2, color, f2);
        a(BufferBuilderVar, matrix4f, Vec3dVar.add(0.0d, f, 0.0d), Vec3dVar2, color, f2);
    }

    private void a(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Vec3d Vec3dVar, Vec3d Vec3dVar2, Color color, float f) {
        BufferBuilderVar.vertex(matrix4f, (float) (Vec3dVar.x - Vec3dVar2.x), (float) (Vec3dVar.y - Vec3dVar2.y), (float) (Vec3dVar.z - Vec3dVar2.z)).color(color.getRed(), color.getGreen(), color.getBlue(), (int) (f * 255.0f));
    }

    @Override
    public void f() {
        super.f();
        this.w.clear();
        this.A = false;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
