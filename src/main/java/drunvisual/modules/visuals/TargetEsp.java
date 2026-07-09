package drunvisual.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
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
import drunvisual.core.Bool;
import drunvisual.entity.EntityUtils;
import drunvisual.events.WorldRenderEvent;
import drunvisual.hud.core.HudServiceRegistry;
import drunvisual.modules.hud.ClientColor;
import drunvisual.render.shader.DrunVisualShaderProgram;
import drunvisual.render.shader.ShaderLibrary;
import drunvisual.render.world.WorldRenderUtils;
import drunvisual.util.ColorUtils;

@ModuleInfo(a = "Target ESP", b = "Отображает ESP вокруг цели", c = ModuleCategory.VISUALS)
public class TargetEsp extends ClientModule {
    public static int a;
    public static boolean b;
    private final ModeSetting e = new ModeSetting("Режим", new String[]{"Призраки", "Круг", "Квадратик", "Орбита"}, "Орбита");
    private final SettingGroup f = new SettingGroup("Призраки").a(() -> {
        return Boolean.valueOf(this.e.b("Призраки"));
    });
    private final SliderSetting g = new SliderSetting("Скорость анимации", 1.5f, 0.5f, 5.0f, 0.1f).a(() -> {
        return Boolean.valueOf(this.e.b("Призраки"));
    });
    private final SliderSetting h = new SliderSetting("Размер частиц", 0.25f, 0.05f, 0.5f, 0.01f).a(() -> {
        return Boolean.valueOf(this.e.b("Призраки"));
    });
    private final SliderSetting i = new SliderSetting("Количество призраков", 4.0f, 2.0f, 6.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.e.b("Призраки"));
    });
    private final SettingGroup j = new SettingGroup("Круг").a(() -> {
        return Boolean.valueOf(this.e.b("Круг"));
    });
    private final SliderSetting k = new SliderSetting("Скорость анимации", 1.5f, 0.5f, 5.0f, 0.1f).a(() -> {
        return Boolean.valueOf(this.e.b("Круг"));
    });
    private final SettingGroup l = new SettingGroup("Квадратик").a(() -> {
        return Boolean.valueOf(this.e.b("Квадратик"));
    });
    private final SliderSetting m = new SliderSetting("Скорость анимации", 2.5f, 0.5f, 5.0f, 0.1f).a(() -> {
        return Boolean.valueOf(this.e.b("Квадратик"));
    });
    private final SliderSetting n = new SliderSetting("Размер квадратика", 1.4f, 0.5f, 2.0f, 0.1f).a(() -> {
        return Boolean.valueOf(this.e.b("Квадратик"));
    });
    private final SettingGroup o = new SettingGroup("Орбита").a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final ModeSetting p = new ModeSetting("Форма", new String[]{"Стрелки", "Ромбы", "Кубы"}, "Стрелки").a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting q = new SliderSetting("Скорость анимации", 1.5f, 0.5f, 5.0f, 0.1f).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting r = new SliderSetting("Фигур по кругу", 3.0f, 2.0f, 8.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting s = new SliderSetting("Слоёв по высоте", 3.0f, 2.0f, 5.0f, 1.0f).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting t = new SliderSetting("Отступ между слоями", 1.0f, 0.3f, 2.0f, 0.05f).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting u = new SliderSetting("Дистанция", 0.9f, 0.5f, 2.0f, 0.05f).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting v = new SliderSetting("Размер фигур", 0.2f, 0.08f, 0.4f, 0.01f).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final BooleanSetting w = new BooleanSetting("Вращение", true).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final BooleanSetting x = new BooleanSetting("Шейдер", true).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final ModeSetting y = new ModeSetting("Тип шейдера", new String[]{"Небула", "Звёзды", "Паутина", "Плазма"}, "Небула").a(() -> {
        return (this.e.b("Орбита") && this.x.a()) ? Boolean.valueOf(true) : Boolean.valueOf(false);
    });
    private final BooleanSetting z = new BooleanSetting("Свечение", true).a(() -> {
        return Boolean.valueOf(this.e.b("Орбита"));
    });
    private final SliderSetting A = new SliderSetting("Размер свечения", 0.85f, 0.1f, 1.0f, 0.05f).a(() -> {
        return Boolean.valueOf(Bool.from((this.e.b("Орбита") && this.z.a()) ? 1 : 0));
    });
    private final SliderSetting B = new SliderSetting("Прозрачность свечения", 0.25f, 0.1f, 1.0f, 0.05f).a(() -> {
        return Boolean.valueOf(Bool.from((this.e.b("Орбита") && this.z.a()) ? 1 : 0));
    });
    private final SettingGroup C = new SettingGroup("Цвет");
    private final BooleanSetting D = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting E = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return Boolean.valueOf(Bool.from(this.D.a() ? 0 : 1));
    });
    private final SettingGroup F = new SettingGroup("При ударе");
    private final BooleanSetting G = new BooleanSetting("Включить", Bool.from(-1673835725));
    private final BooleanSetting H = new BooleanSetting("Изменять цвет", true).a(() -> {
        return Boolean.valueOf(this.G.a());
    });
    private final ColorSetting I = new ColorSetting("Цвет урона", new Color(255, 50, 50)).a(() -> {
        return Boolean.valueOf(Bool.from((this.G.a() && this.H.a()) ? 1 : 0));
    });
    private final BooleanSetting J = new BooleanSetting("Ускорять анимацию", true).a(() -> {
        return Boolean.valueOf(this.G.a());
    });
    private final SliderSetting K = new SliderSetting("Множитель ускорения", 3.0f, 1.2f, 5.0f, 0.1f).a(() -> {
        return Boolean.valueOf(Bool.from((this.G.a() && this.J.a()) ? 1 : 0));
    });
    private final SliderSetting L = new SliderSetting("Длительность эффекта", 0.8f, 0.3f, 2.0f, 0.1f).a(() -> {
        return Boolean.valueOf(Bool.from((this.G.a() && this.J.a()) ? 1 : 0));
    });
    private final Map<LivingEntity, AnimationState> M = new HashMap();
    private final Identifier N = Identifier.of(DrunVisual.CLIENT_NAME, "textures/target.png");
    private final BooleanSetting targetPlayers = new BooleanSetting("Players", true);
    private final BooleanSetting targetMobs = new BooleanSetting("Mobs", true);
    private final Map<LivingEntity, Integer> O = new HashMap();
    private final Map<LivingEntity, Long> P = new HashMap();
    private final Map<LivingEntity, Double> Q = new HashMap();
    private final Map<LivingEntity, Double> R = new HashMap();
    private long S = System.currentTimeMillis();

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        long lastTime = this.S;
        this.S = System.currentTimeMillis();
        float fMin = Math.min((this.S - lastTime) / 1000.0f, 0.1f);
        LivingEntity LivingEntityVarP = p();
        if (LivingEntityVarP != null && a(LivingEntityVarP)) {
            this.M.computeIfAbsent(LivingEntityVarP, LivingEntityVar -> {
                return new AnimationState();
            }).a(1.0d, 0.5d, Easing.f, true);
        }
        HashSet<LivingEntity> hashSet = new HashSet();
        for (Map.Entry<LivingEntity, AnimationState> entry : this.M.entrySet()) {
            LivingEntity key = entry.getKey();
            if (a(key)) {
                AnimationState value = entry.getValue();
                if (key != LivingEntityVarP) {
                    value.a(0.0d, 0.5d, Easing.f, true);
                }
                value.a();
                if (!value.d() || value.i() != 0.0d) {
                    a(key, fMin);
                    switch (this.e.d()) {
                        case "Призраки":
                            b(worldRenderEvent, key, (float) value.j());
                            break;
                        case "Круг":
                            c(worldRenderEvent, key, (float) value.j());
                            break;
                        case "Квадратик":
                            a(worldRenderEvent, key, (float) value.j());
                            break;
                        case "Орбита":
                            d(worldRenderEvent, key, (float) value.j());
                            break;
                    }
                } else {
                    hashSet.add(key);
                }
            } else {
                hashSet.add(key);
            }
        }
        for (LivingEntity LivingEntityVar2 : hashSet) {
            this.M.remove(LivingEntityVar2);
            this.Q.remove(LivingEntityVar2);
            this.R.remove(LivingEntityVar2);
        }
    }

    private void a(LivingEntity LivingEntityVar, float f) {
        float fC = c(LivingEntityVar);
        this.Q.put(LivingEntityVar, Double.valueOf((this.Q.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue() + (((double) (f * fC)) * 50.0d)) % 360.0d));
        this.R.put(LivingEntityVar, Double.valueOf(this.R.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue() + (((double) (f * fC)) * 2.5d)));
    }

    private boolean a(LivingEntity LivingEntityVar) {
        if (LivingEntityVar == null) {
            return false;
        }
        if (!matchesTargetType(LivingEntityVar)) {
            return false;
        }
        if (LivingEntityVar.isAlive() && LivingEntityVar.getWorld() == c.world) {
            return (LivingEntityVar.getWidth() <= 0.0f || LivingEntityVar.getHeight() <= 0.0f) ? false : LivingEntityVar.squaredDistanceTo(c.player) <= 10000.0d ? true : false;
        }
        return false;
    }

    private LivingEntity p() {
        if (c.player == null || c.world == null) {
            return null;
        }
        LivingEntity LivingEntityVarH = HudServiceRegistry.TARGETS.h();
        if (a(LivingEntityVarH)) {
            return LivingEntityVarH;
        }
        Entity EntityVar = c.targetedEntity;
        if (EntityVar instanceof LivingEntity) {
            LivingEntity LivingEntityVar = (LivingEntity) EntityVar;
            if (a(LivingEntityVar)) {
                return LivingEntityVar;
            }
        }
        LivingEntity LivingEntityVar2 = null;
        double d = 4096.0d;
        for (LivingEntity LivingEntityVar3 : c.world.getEntitiesByClass(LivingEntity.class, c.player.getBoundingBox().expand(64.0d), LivingEntityVar4 -> {
            return a(LivingEntityVar4);
        })) {
            double dSquaredDistanceTo = LivingEntityVar3.squaredDistanceTo(c.player);
            if (dSquaredDistanceTo < d) {
                d = dSquaredDistanceTo;
                LivingEntityVar2 = LivingEntityVar3;
            }
        }
        return LivingEntityVar2;
    }

    private boolean matchesTargetType(LivingEntity LivingEntityVar) {
        if (LivingEntityVar == null || LivingEntityVar == c.player) {
            return false;
        }
        return LivingEntityVar instanceof PlayerEntity ? this.targetPlayers.a() : this.targetMobs.a();
    }

    private void a(WorldRenderEvent worldRenderEvent, LivingEntity LivingEntityVar, float f) {
        if (f <= 0.0f || LivingEntityVar == null || !a(LivingEntityVar)) {
            return;
        }
        Vec3d Vec3dVarB = EntityUtils.b(LivingEntityVar, worldRenderEvent.b());
        if (Vec3dVarB == null) {
            Vec3dVarB = LivingEntityVar.getPos();
        }
        if (Vec3dVarB != null) {
            double dDoubleValue = this.Q.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue();
            double dDoubleValue2 = this.R.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue();
            float fA = ((float) (((double) this.n.a()) * (1.0d + (0.05d * Math.sin(dDoubleValue2))))) * f;
            WorldRenderUtils.a(worldRenderEvent.a(), new Vec3d(Vec3dVarB.x, Vec3dVarB.y + ((double) (LivingEntityVar.getHeight() / 2.0f)), Vec3dVarB.z), fA, ColorUtils.a(ColorUtils.a(b(LivingEntityVar).getRGB()), Math.max(1, (int) (f * 255.0f))), this.N, (float) dDoubleValue, true);
        }
    }

    private void b(WorldRenderEvent worldRenderEvent, LivingEntity LivingEntityVar, float f) {
        if (!a(LivingEntityVar) || f <= 0.0f) {
            return;
        }
        Vec3d Vec3dVarB = EntityUtils.b(LivingEntityVar, worldRenderEvent.b());
        if (Vec3dVarB == null) {
            Vec3dVarB = LivingEntityVar.getPos();
        }
        if (Vec3dVarB != null) {
            double radians = Math.toRadians(50.0d) / 15.0d;
            int iB = this.i.b();
            double dDoubleValue = this.R.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue();
            double dMax = ((double) Math.max(LivingEntityVar.getWidth(), 0.5f)) + 0.3d;
            double dMax2 = Vec3dVarB.y + ((double) (Math.max(LivingEntityVar.getHeight(), 0.5f) / 2.0f));
            double d = f;
            Vec3d[] Vec3dVarArr = new Vec3d[6];
            Vec3dVarArr[0 | 0] = new Vec3d(1.0d, 1.0d, 1.0d);
            Vec3dVarArr[1] = new Vec3d(-1.0d, 1.0d, -1.0d);
            Vec3dVarArr[2] = new Vec3d(1.0d, -1.0d, 1.0d);
            Vec3dVarArr[3] = new Vec3d(-1.0d, -1.0d, 1.0d);
            Vec3dVarArr[4] = new Vec3d(1.0d, 1.0d, -1.0d);
            Vec3dVarArr[5] = new Vec3d(-1.0d, -1.0d, -1.0d);
            for (int i = 0; i < iB; i++) {
                double d2 = dDoubleValue + ((((double) i) * 3.141592653589793d) / 2.0d);
                Vec3d Vec3dVar = Vec3dVarArr[i];
                double dSqrt = Math.sqrt((Vec3dVar.x * Vec3dVar.x) + (Vec3dVar.y * Vec3dVar.y) + (Vec3dVar.z * Vec3dVar.z));
                Vec3d Vec3dVar2 = new Vec3d(Vec3dVar.x / dSqrt, Vec3dVar.y / dSqrt, Vec3dVar.z / dSqrt);
                Vec3d Vec3dVar3 = new Vec3d(0.0d, 1.0d, 0.0d);
                if (Math.abs((Vec3dVar2.x * Vec3dVar3.x) + (Vec3dVar2.y * Vec3dVar3.y) + (Vec3dVar2.z * Vec3dVar3.z)) > 0.99d) {
                    Vec3dVar3 = new Vec3d(1.0d, 0.0d, 0.0d);
                }
                Vec3d Vec3dVar4 = new Vec3d((Vec3dVar2.y * Vec3dVar3.z) - (Vec3dVar2.z * Vec3dVar3.y), (Vec3dVar2.z * Vec3dVar3.x) - (Vec3dVar2.x * Vec3dVar3.z), (Vec3dVar2.x * Vec3dVar3.y) - (Vec3dVar2.y * Vec3dVar3.x));
                double dSqrt2 = Math.sqrt((Vec3dVar4.x * Vec3dVar4.x) + (Vec3dVar4.y * Vec3dVar4.y) + (Vec3dVar4.z * Vec3dVar4.z));
                Vec3d Vec3dVar5 = new Vec3d(Vec3dVar4.x / dSqrt2, Vec3dVar4.y / dSqrt2, Vec3dVar4.z / dSqrt2);
                Vec3d Vec3dVar6 = new Vec3d((Vec3dVar2.y * Vec3dVar5.z) - (Vec3dVar2.z * Vec3dVar5.y), (Vec3dVar2.z * Vec3dVar5.x) - (Vec3dVar2.x * Vec3dVar5.z), (Vec3dVar2.x * Vec3dVar5.y) - (Vec3dVar2.y * Vec3dVar5.x));
                double dSqrt3 = Math.sqrt((Vec3dVar6.x * Vec3dVar6.x) + (Vec3dVar6.y * Vec3dVar6.y) + (Vec3dVar6.z * Vec3dVar6.z));
                Vec3d Vec3dVar7 = new Vec3d(Vec3dVar6.x / dSqrt3, Vec3dVar6.y / dSqrt3, Vec3dVar6.z / dSqrt3);
                for (int i2 = 0; i2 < 15; i2++) {
                    double d3 = (((double) i2) * radians) + d2;
                    double dCos = Math.cos(d3);
                    double dSin = Math.sin(d3);
                    Vec3d Vec3dVar8 = new Vec3d(((Vec3dVar5.x * dCos) + (Vec3dVar7.x * dSin)) * dMax, ((Vec3dVar5.y * dCos) + (Vec3dVar7.y * dSin)) * dMax, ((Vec3dVar5.z * dCos) + (Vec3dVar7.z * dSin)) * dMax);
                    double d4 = Vec3dVarB.x + Vec3dVar8.x;
                    double d5 = dMax2 + Vec3dVar8.y;
                    double d6 = Vec3dVarB.z + Vec3dVar8.z;
                    try {
                        WorldRenderUtils.a(worldRenderEvent.a(), new Vec3d(d4, d5, d6), this.h.a() * (1.0f + (i2 / 15.0f)), ColorUtils.a(ColorUtils.a(b(LivingEntityVar).getRGB()), Math.max(1, (int) (d * 150.0d))));
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    private void c(WorldRenderEvent worldRenderEvent, LivingEntity LivingEntityVar, float f) {
        if (f <= 0.0f || LivingEntityVar == null || !a(LivingEntityVar)) {
            return;
        }
        float fGetWidth = LivingEntityVar.getWidth() * 0.7f;
        Vec3d Vec3dVarB = EntityUtils.b(LivingEntityVar, worldRenderEvent.b());
        if (Vec3dVarB == null) {
            Vec3dVarB = LivingEntityVar.getPos();
        }
        double dDoubleValue = this.R.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue() % 6.283185307179586d;
        double d = 6.283185307179586d / 2.0d;
        boolean z = dDoubleValue > d;
        double d2 = dDoubleValue / d;
        double d3 = !z ? 1.0d - d2 : d2 - 1.0d;
        double dPow = d3 >= 0.5d ? 1.0d - (Math.pow(((-2.0d) * d3) + 2.0d, 2.0d) / 2.0d) : 2.0d * d3 * d3;
        double dGetHeight = ((double) (LivingEntityVar.getHeight() / 2.0f)) * (dPow <= 0.5d ? dPow : 1.0d - dPow) * ((double) (!z ? 1 : -1));
        Tessellator TessellatorVarGetInstance = Tessellator.getInstance();
        MatrixStack MatrixStackVarA = worldRenderEvent.a();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        Color colorB = b(LivingEntityVar);
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVarA.peek().getPositionMatrix();
        MatrixStackVarA.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        RenderSystem.lineWidth(3.0f);
        BufferBuilder BufferBuilderVarBegin = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.TRIANGLE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i <= 360; i++) {
            double dCos = Math.cos(Math.toRadians(i));
            double dSin = Math.sin(Math.toRadians(i));
            int red = colorB.getRed();
            int green = colorB.getGreen();
            int blue = colorB.getBlue();
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) ((Vec3dVarB.x + (dCos * ((double) fGetWidth))) - Vec3dVarGetPos.x), (float) ((Vec3dVarB.y + (((double) LivingEntityVar.getHeight()) * dPow)) - Vec3dVarGetPos.y), (float) ((Vec3dVarB.z + (dSin * ((double) fGetWidth))) - Vec3dVarGetPos.z)).color(red, green, blue, (int) (100.0f * f));
            BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, (float) ((Vec3dVarB.x + (dCos * ((double) fGetWidth))) - Vec3dVarGetPos.x), (float) (((Vec3dVarB.y + (((double) LivingEntityVar.getHeight()) * dPow)) + dGetHeight) - Vec3dVarGetPos.y), (float) ((Vec3dVarB.z + (dSin * ((double) fGetWidth))) - Vec3dVarGetPos.z)).color(red, green, blue, 0);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        BufferBuilder BufferBuilderVarMethod_608272 = TessellatorVarGetInstance.begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i2 = 0; i2 <= 360; i2++) {
            BufferBuilderVarMethod_608272.vertex(matrix4fGetPositionMatrix, (float) ((Vec3dVarB.x + (Math.cos(Math.toRadians(i2)) * ((double) fGetWidth))) - Vec3dVarGetPos.x), (float) ((Vec3dVarB.y + (((double) LivingEntityVar.getHeight()) * dPow)) - Vec3dVarGetPos.y), (float) ((Vec3dVarB.z + (Math.sin(Math.toRadians(i2)) * ((double) fGetWidth))) - Vec3dVarGetPos.z)).color(colorB.getRed(), colorB.getGreen(), colorB.getBlue(), (int) (255.0f * f));
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        RenderSystem.depthMask(true);
        GL11.glDisable(2848);
        MatrixStackVarA.pop();
    }

    private String a(ModeSetting modeSetting) {
        switch (modeSetting.d()) {
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

    private void d(WorldRenderEvent worldRenderEvent, LivingEntity LivingEntityVar, float f) {
        if (f > 0.0f && a(LivingEntityVar)) {
            Vec3d Vec3dVarB = EntityUtils.b(LivingEntityVar, worldRenderEvent.b());
            if (Vec3dVarB == null) {
                Vec3dVarB = LivingEntityVar.getPos();
            }
            if (Vec3dVarB == null) {
                return;
            }
            MatrixStack MatrixStackVarA = worldRenderEvent.a();
            Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
            Color colorB = b(LivingEntityVar);
            int iB = this.r.b();
            int iB2 = this.s.b();
            float fA = this.u.a() + (LivingEntityVar.getWidth() / 2.0f);
            float fA2 = this.v.a() * f;
            float fGetHeight = LivingEntityVar.getHeight();
            Vec3d Vec3dVar = new Vec3d(Vec3dVarB.x, Vec3dVarB.y + ((double) (fGetHeight / 2.0f)), Vec3dVarB.z);
            double dDoubleValue = this.w.a() ? this.Q.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue() : 0.0d;
            double dDoubleValue2 = this.R.getOrDefault(LivingEntityVar, Double.valueOf(0.0d)).doubleValue();
            float fSin = (float) (((double) fA) + (Math.sin(dDoubleValue2) * 0.08d));
            Optional<DrunVisualShaderProgram> optionalEmpty = Optional.empty();
            if (this.x.a()) {
                optionalEmpty = ShaderLibrary.getRegistry().find(a(this.y));
                if (optionalEmpty.isPresent() && !optionalEmpty.get().b()) {
                    optionalEmpty = Optional.empty();
                }
            }
            String strD = this.p.d();
            float f2 = ((iB2 - 2) + 1) / 2.0f;
            float fA3 = fGetHeight * this.t.a();
            float f3 = (fGetHeight - fA3) / 2.0f;
            if (this.z.a()) {
                float fA4 = this.A.a();
                int iA = ColorUtils.a(ColorUtils.a(colorB.getRGB()), (int) (f * 255.0f * this.B.a()));
                for (int i = 0; i < iB2; i++) {
                    float f4 = (float) (Vec3dVarB.y + ((double) (f3 + ((fA3 * i) / ((iB2 & (-2)) - ((iB2 ^ (-1)) & 1))))));
                    float fAbs = fSin * (1.0f - ((Math.abs(i - f2) / Math.max(f2, 0.001f)) * 0.3f));
                    double d = ((360.0d / ((double) iB)) / 2.0d) * ((double) (i % 2));
                    for (int i2 = 0; i2 < iB; i2++) {
                        double radians = Math.toRadians(dDoubleValue + d + ((360.0d * ((double) i2)) / ((double) iB)));
                        float fCos = (float) (Vec3dVar.x + (((double) fAbs) * Math.cos(radians)));
                        float fSin2 = (float) (Vec3dVar.z + (((double) fAbs) * Math.sin(radians)));
                        float f5 = fCos;
                        float f6 = f4;
                        float f7 = fSin2;
                        if (strD.equals("Стрелки")) {
                            double d2 = Vec3dVar.x - ((double) fCos);
                            double d3 = Vec3dVar.y - ((double) f4);
                            double d4 = Vec3dVar.z - ((double) fSin2);
                            double dSqrt = Math.sqrt((d2 * d2) + (d3 * d3) + (d4 * d4));
                            if (dSqrt > 0.001d) {
                                d2 /= dSqrt;
                                d3 /= dSqrt;
                                d4 /= dSqrt;
                            }
                            float f8 = fA2 * 0.75f;
                            f5 = (float) (((double) f5) + (d2 * ((double) f8)));
                            f6 = (float) (((double) f6) + (d3 * ((double) f8)));
                            f7 = (float) (((double) f7) + (d4 * ((double) f8)));
                        }
                        WorldRenderUtils.a(MatrixStackVarA, new Vec3d(f5, f6, f7), fA4, iA);
                    }
                }
            }
            MatrixStackVarA.push();
            RenderSystem.enableBlend();
            RenderSystem.defaultBlendFunc();
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
            Matrix4f matrix4fGetPositionMatrix = MatrixStackVarA.peek().getPositionMatrix();
            byte b2 = -1;
            if (strD.equals("Стрелки")) {
                b2 = 0;
            } else if (strD.equals("Ромбы")) {
                b2 = 1;
            } else if (strD.equals("Кубы")) {
                b2 = 2;
            }
            int totalPasses = optionalEmpty.isPresent() ? 2 : 1;
            for (int pass = 0; pass < totalPasses; pass++) {
                boolean shaderPass = pass == 1;
                if (shaderPass) {
                    DrunVisualShaderProgram pulseShaderProgram = optionalEmpty.get();
                    pulseShaderProgram.d();
                    pulseShaderProgram.a("time", (float) (dDoubleValue2 * 0.4d));
                    pulseShaderProgram.a("screenSize", c.getWindow().getFramebufferWidth(), c.getWindow().getFramebufferHeight());
                    pulseShaderProgram.a("baseColor", colorB.getRed() / 255.0f, colorB.getGreen() / 255.0f, colorB.getBlue() / 255.0f, 1.0f);
                    pulseShaderProgram.a("alpha", f * 0.85f);
                }
                for (int i3 = 0; i3 < iB2; i3++) {
                    float f9 = (float) (Vec3dVarB.y + ((double) (f3 + ((fA3 * i3) / ((2 * (iB2 & (-2))) - (iB2 ^ 1))))));
                    float fAbs2 = fSin * (1.0f - ((Math.abs(i3 - f2) / Math.max(f2, 0.001f)) * 0.3f));
                    double d5 = ((360.0d / ((double) iB)) / 2.0d) * ((double) (i3 % 2));
                    for (int i4 = 0; i4 < iB; i4++) {
                        double radians2 = Math.toRadians(dDoubleValue + d5 + ((360.0d * ((double) i4)) / ((double) iB)));
                        float fCos2 = (float) (Vec3dVar.x + (((double) fAbs2) * Math.cos(radians2)));
                        float fSin3 = (float) (Vec3dVar.z + (((double) fAbs2) * Math.sin(radians2)));
                        double d6 = Vec3dVar.x - ((double) fCos2);
                        double d7 = Vec3dVar.y - ((double) f9);
                        double d8 = Vec3dVar.z - ((double) fSin3);
                        double dSqrt2 = Math.sqrt((d6 * d6) + (d7 * d7) + (d8 * d8));
                        double d9 = d6 / dSqrt2;
                        double d10 = d7 / dSqrt2;
                        double d11 = d8 / dSqrt2;
                        double d12 = -d11;
                        double d13 = 0.0d;
                        double d14 = d9;
                        double dSqrt3 = Math.sqrt((d12 * d12) + (d13 * d13) + (d14 * d14));
                        if (dSqrt3 > 0.001d) {
                            d12 /= dSqrt3;
                            d13 /= dSqrt3;
                            d14 /= dSqrt3;
                        }
                        double d15 = (d13 * d11) - (d14 * d10);
                        double d16 = (d14 * d9) - (d12 * d11);
                        double d17 = (d12 * d10) - (d13 * d9);
                        int i5 = (int) (f * 220.0f);
                        switch (b2) {
                            case 0:
                                a(matrix4fGetPositionMatrix, Vec3dVarGetPos, colorB, shaderPass, fCos2, f9, fSin3, d9, d10, d11, d15, d16, d17, d12, d13, d14, fA2, i5);
                                break;
                            case 1:
                                b(matrix4fGetPositionMatrix, Vec3dVarGetPos, colorB, shaderPass, fCos2, f9, fSin3, d9, d10, d11, d15, d16, d17, d12, d13, d14, fA2, i5);
                                break;
                            case 2:
                                a(matrix4fGetPositionMatrix, Vec3dVarGetPos, colorB, shaderPass, fCos2, f9, fSin3, d15, d16, d17, d12, d13, d14, fA2, i5);
                                break;
                        }
                    }
                }
                if (shaderPass) {
                    optionalEmpty.get().e();
                }
            }
            RenderSystem.depthMask(true);
            RenderSystem.enableCull();
            RenderSystem.disableBlend();
            MatrixStackVarA.pop();
        }
    }

    private void a(Matrix4f matrix4f, Vec3d Vec3dVar, Color color, boolean z, float f, float f2, float f3, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, float f4, int i) {
        float f5 = f4 * 0.5f;
        a(matrix4f, color, z, (float) ((((double) f) + ((d * ((double) f4)) * 1.5d)) - Vec3dVar.x), (float) ((((double) f2) + ((d2 * ((double) f4)) * 1.5d)) - Vec3dVar.y), (float) ((((double) f3) + ((d3 * ((double) f4)) * 1.5d)) - Vec3dVar.z), (float) ((((double) f) + (d4 * ((double) f5))) - Vec3dVar.x), (float) ((((double) f2) + (d5 * ((double) f5))) - Vec3dVar.y), (float) ((((double) f3) + (d6 * ((double) f5))) - Vec3dVar.z), (float) ((((double) f) - (d4 * ((double) f5))) - Vec3dVar.x), (float) ((((double) f2) - (d5 * ((double) f5))) - Vec3dVar.y), (float) ((((double) f3) - (d6 * ((double) f5))) - Vec3dVar.z), (float) ((((double) f) + (d7 * ((double) f5))) - Vec3dVar.x), (float) ((((double) f2) + (d8 * ((double) f5))) - Vec3dVar.y), (float) ((((double) f3) + (d9 * ((double) f5))) - Vec3dVar.z), (float) ((((double) f) - (d7 * ((double) f5))) - Vec3dVar.x), (float) ((((double) f2) - (d8 * ((double) f5))) - Vec3dVar.y), (float) ((((double) f3) - (d9 * ((double) f5))) - Vec3dVar.z), i);
    }

    private void b(Matrix4f matrix4f, Vec3d Vec3dVar, Color color, boolean z, float f, float f2, float f3, double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8, double d9, float f4, int i) {
        double d10 = (d5 * d9) - (d6 * d8);
        double d11 = (d6 * d7) - (d4 * d9);
        double d12 = (d4 * d8) - (d5 * d7);
        float f5 = (float) ((((double) f) + ((d4 * ((double) f4)) * 1.2d)) - Vec3dVar.x);
        float f6 = (float) ((((double) f2) + ((d5 * ((double) f4)) * 1.2d)) - Vec3dVar.y);
        float f7 = (float) ((((double) f3) + ((d6 * ((double) f4)) * 1.2d)) - Vec3dVar.z);
        float f8 = (float) ((((double) f) - ((d4 * ((double) f4)) * 1.2d)) - Vec3dVar.x);
        float f9 = (float) ((((double) f2) - ((d5 * ((double) f4)) * 1.2d)) - Vec3dVar.y);
        float f10 = (float) ((((double) f3) - ((d6 * ((double) f4)) * 1.2d)) - Vec3dVar.z);
        float f11 = f4 * 0.6f;
        float f12 = (float) (((((double) f) + (d7 * ((double) f11))) + (d10 * ((double) f11))) - Vec3dVar.x);
        float f13 = (float) (((((double) f2) + (d8 * ((double) f11))) + (d11 * ((double) f11))) - Vec3dVar.y);
        float f14 = (float) (((((double) f3) + (d9 * ((double) f11))) + (d12 * ((double) f11))) - Vec3dVar.z);
        float f15 = (float) (((((double) f) + (d7 * ((double) f11))) - (d10 * ((double) f11))) - Vec3dVar.x);
        float f16 = (float) (((((double) f2) + (d8 * ((double) f11))) - (d11 * ((double) f11))) - Vec3dVar.y);
        float f17 = (float) (((((double) f3) + (d9 * ((double) f11))) - (d12 * ((double) f11))) - Vec3dVar.z);
        float f18 = (float) (((((double) f) - (d7 * ((double) f11))) - (d10 * ((double) f11))) - Vec3dVar.x);
        float f19 = (float) (((((double) f2) - (d8 * ((double) f11))) - (d11 * ((double) f11))) - Vec3dVar.y);
        float f20 = (float) (((((double) f3) - (d9 * ((double) f11))) - (d12 * ((double) f11))) - Vec3dVar.z);
        float f21 = (float) (((((double) f) - (d7 * ((double) f11))) + (d10 * ((double) f11))) - Vec3dVar.x);
        float f22 = (float) (((((double) f2) - (d8 * ((double) f11))) + (d11 * ((double) f11))) - Vec3dVar.y);
        float f23 = (float) (((((double) f3) - (d9 * ((double) f11))) + (d12 * ((double) f11))) - Vec3dVar.z);
        a(matrix4f, color, z, f5, f6, f7, f12, f13, f14, f15, f16, f17, i);
        a(matrix4f, color, z, f5, f6, f7, f15, f16, f17, f18, f19, f20, i);
        a(matrix4f, color, z, f5, f6, f7, f18, f19, f20, f21, f22, f23, i);
        a(matrix4f, color, z, f5, f6, f7, f21, f22, f23, f12, f13, f14, i);
        a(matrix4f, color, z, f8, f9, f10, f15, f16, f17, f12, f13, f14, i);
        a(matrix4f, color, z, f8, f9, f10, f18, f19, f20, f15, f16, f17, i);
        a(matrix4f, color, z, f8, f9, f10, f21, f22, f23, f18, f19, f20, i);
        a(matrix4f, color, z, f8, f9, f10, f12, f13, f14, f21, f22, f23, i);
    }

    private void a(Matrix4f matrix4f, Vec3d Vec3dVar, Color color, boolean z, float f, float f2, float f3, double d, double d2, double d3, double d4, double d5, double d6, float f4, int i) {
        float f5 = f4 * 0.5f;
        double d7 = (d2 * d6) - (d3 * d5);
        double d8 = (d3 * d4) - (d * d6);
        double d9 = (d * d5) - (d2 * d4);
        float[][] fArr = new float[8][3];
        int i2 = 0;
        for (int i3 = -1; i3 <= 1; i3 += 2) {
            for (int i4 = -1; i4 <= 1; i4 += 2) {
                for (int i5 = -1; i5 <= 1; i5 += 2) {
                    fArr[i2][0] = (float) ((((((double) f) + ((d * ((double) f5)) * ((double) i3))) + ((d4 * ((double) f5)) * ((double) i4))) + ((d7 * ((double) f5)) * ((double) i5))) - Vec3dVar.x);
                    fArr[i2][1] = (float) ((((((double) f2) + ((d2 * ((double) f5)) * ((double) i3))) + ((d5 * ((double) f5)) * ((double) i4))) + ((d8 * ((double) f5)) * ((double) i5))) - Vec3dVar.y);
                    fArr[i2][2] = (float) ((((((double) f3) + ((d3 * ((double) f5)) * ((double) i3))) + ((d6 * ((double) f5)) * ((double) i4))) + ((d9 * ((double) f5)) * ((double) i5))) - Vec3dVar.z);
                    i2++;
                }
            }
        }
        for (int[] objArr : new int[][]{new int[]{0, 1, 3, 2}, new int[]{4, 6, 7, 5}, new int[]{0, 4, 5, 1}, new int[]{2, 3, 7, 6}, new int[]{0, 2, 6, 4}, new int[]{1, 5, 7, 3}}) {
            a(matrix4f, color, z, fArr[objArr[0]][0], fArr[objArr[0]][1], fArr[objArr[0]][2], fArr[objArr[1]][0], fArr[objArr[1]][1], fArr[objArr[1]][2], fArr[objArr[2]][0], fArr[objArr[2]][1], fArr[objArr[2]][2], i);
            a(matrix4f, color, z, fArr[objArr[0]][0], fArr[objArr[0]][1], fArr[objArr[0]][2], fArr[objArr[2]][0], fArr[objArr[2]][1], fArr[objArr[2]][2], fArr[objArr[3]][0], fArr[objArr[3]][1], fArr[objArr[3]][2], i);
        }
    }

    private void a(Matrix4f matrix4f, Color color, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, int i) {
        if (!z) {
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
            BufferBuilderVarBegin.vertex(matrix4f, f, f2, f3).color(color.getRed(), color.getGreen(), color.getBlue(), i);
            BufferBuilderVarBegin.vertex(matrix4f, f4, f5, f6).color(color.getRed(), color.getGreen(), color.getBlue(), (i * 3) / 4);
            BufferBuilderVarBegin.vertex(matrix4f, f7, f8, f9).color(color.getRed(), color.getGreen(), color.getBlue(), (i * 3) / 4);
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
            return;
        }
        float[] fArr = new float[15];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        fArr[3] = 0.5f;
        fArr[4] = 0.0f;
        fArr[5] = f4;
        fArr[6] = f5;
        fArr[7] = f6;
        fArr[8] = 0.0f;
        fArr[9] = 1.0f;
        fArr[10] = f7;
        fArr[11] = f8;
        fArr[12] = f9;
        fArr[13] = 1.0f;
        fArr[14] = 1.0f;
        DrunVisualShaderProgram.a(fArr, 3);
    }

    private void a(Matrix4f matrix4f, Color color, boolean z, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13, float f14, float f15, int i) {
        a(matrix4f, color, z, f, f2, f3, f4, f5, f6, f10, f11, f12, i);
        a(matrix4f, color, z, f, f2, f3, f10, f11, f12, f7, f8, f9, i);
        a(matrix4f, color, z, f, f2, f3, f7, f8, f9, f13, f14, f15, i);
        a(matrix4f, color, z, f, f2, f3, f13, f14, f15, f4, f5, f6, i);
    }

    private Color b(LivingEntity LivingEntityVar) {
        if (this.G.a()) {
            if (!this.H.a()) {
            } else if (LivingEntityVar != null && LivingEntityVar.hurtTime > 0) {
                float f = LivingEntityVar.hurtTime / 10.0f;
                Color colorN = n();
                Color colorA = this.I.a();
                int red = (int) (colorN.getRed() + ((colorA.getRed() - colorN.getRed()) * f));
                float green = colorN.getGreen();
                int green2 = colorA.getGreen();
                int green3 = colorN.getGreen();
                return new Color(Math.max(0, Math.min(255, red)), Math.max(0, Math.min(255, (int) (green + (((2 * (green2 & (green3 ^ (-1)))) - (green2 ^ green3)) * f)))), Math.max(0, Math.min(255, (int) (colorN.getBlue() + ((colorA.getBlue() - colorN.getBlue()) * f)))));
            }
        }
        return n();
    }

    private Color n() {
        if (this.D.a()) {
            ClientColor clientColor = ModuleRegistry.CLIENT_COLOR;
            if (clientColor != null) {
                return clientColor.n();
            }
        } else {
        }
        return this.E.a();
    }

    private float o() {
        switch (this.e.d()) {
            case "Призраки":
                return this.g.a();
            case "Круг":
                return this.k.a();
            case "Квадратик":
                return this.m.a();
            case "Орбита":
                return this.q.a();
            default:
                return 1.5f;
        }
    }

    private float c(LivingEntity LivingEntityVar) {
        float f;
        float fO = o();
        if (!this.G.a() || !this.J.a() || LivingEntityVar == null) {
            return fO;
        }
        int i = LivingEntityVar.hurtTime;
        int iIntValue = this.O.getOrDefault(LivingEntityVar, 0).intValue();
        this.O.put(LivingEntityVar, Integer.valueOf(i));
        if (i >= 9 && iIntValue < 9) {
            this.P.put(LivingEntityVar, Long.valueOf(System.currentTimeMillis()));
        }
        Long lastSeen = this.P.get(LivingEntityVar);
        if (lastSeen == null) {
            return fO;
        }
        float fCurrentTimeMillis = (System.currentTimeMillis() - lastSeen.longValue()) / 1000.0f;
        float fA = this.L.a();
        if (fCurrentTimeMillis > fA) {
            return fO;
        }
        float f2 = fCurrentTimeMillis / fA;
        if (f2 >= 0.15f) {
            float f3 = (f2 - 0.15f) / 0.85f;
            f = 1.0f - ((f3 * f3) * (3.0f - (2.0f * f3)));
        } else {
            float f4 = f2 / 0.15f;
            f = f4 * f4 * (3.0f - (2.0f * f4));
        }
        return fO * (1.0f + ((this.K.a() - 1.0f) * f));
    }

    @Override
    public void f() {
        super.f();
        this.M.clear();
        this.O.clear();
        this.P.clear();
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
