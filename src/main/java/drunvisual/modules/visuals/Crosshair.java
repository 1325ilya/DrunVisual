package drunvisual.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.hit.HitResult;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.option.Perspective;
import org.joml.Matrix4f;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.core.Bool;
import drunvisual.events.HudRenderPostEvent;
import drunvisual.render.ScreenScale;

@ModuleInfo(a = "Crosshair", b = "Настраивает отображение прицела", c = ModuleCategory.VISUALS)
public class Crosshair extends ClientModule {
    private final SliderSetting e = new SliderSetting("Длина", 3.0f, 1.0f, 15.0f, 1.0f);
    private final SliderSetting f = new SliderSetting("Толщина", 1.0f, 1.0f, 10.0f, 1.0f);
    private final SliderSetting g = new SliderSetting("Отступ", 0.0f, 0.0f, 10.0f, 1.0f);
    private final SettingGroup h = new SettingGroup("Поведение");
    private final BooleanSetting i = new BooleanSetting("Обводка", true);
    private final BooleanSetting j = new BooleanSetting("Точка", false);
    private final BooleanSetting k = new BooleanSetting("Менять цвет при наводке", true);
    private final BooleanSetting l = new BooleanSetting("Динамический", false);
    private final SettingGroup m = new SettingGroup("Цвет");
    private final BooleanSetting n = new BooleanSetting("Цвет клиента", false);
    private final ColorSetting o = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return Boolean.valueOf(Bool.from(this.n.a() ? 0 : 1));
    });
    public static int a;
    public static boolean b;

    @EventHandler(priority = EventPriority.LOWEST)
    private void a(HudRenderPostEvent hudRenderPostEvent) {
        if (c.options.getPerspective() != Perspective.FIRST_PERSON || c.player == null) {
            return;
        }
        ScreenScale.a(2.0d);
        float fGetWidth = c.getWindow().getWidth() / 4.0f;
        float fGetHeight = c.getWindow().getHeight() / 4.0f;
        float fGetAttackCooldownProgress = 1.0f - c.player.getAttackCooldownProgress(hudRenderPostEvent.d());
        Color colorO = o();
        if (this.k.a() && n()) {
            colorO = new Color(255, 64, 64);
        }
        a(hudRenderPostEvent.a(), fGetWidth, fGetHeight, fGetAttackCooldownProgress, colorO);
        ScreenScale.a();
    }

    private void a(MatrixStack MatrixStackVar, float f, float f2, float f3, Color color) {
        float fA = !this.l.a() ? this.g.a() : this.g.a() + (8.0f * f3);
        float fA2 = this.f.a();
        float fA3 = this.e.a();
        if (this.i.a()) {
            Color color2 = Color.BLACK;
            a(MatrixStackVar, (f + fA) - 0.5f, (f2 - (fA2 / 2.0f)) - 0.5f, fA3 + (0.5f * 2.0f), fA2 + (0.5f * 2.0f), color2);
            a(MatrixStackVar, ((f - fA) - fA3) - 0.5f, (f2 - (fA2 / 2.0f)) - 0.5f, fA3 + (0.5f * 2.0f), fA2 + (0.5f * 2.0f), color2);
            a(MatrixStackVar, (f - (fA2 / 2.0f)) - 0.5f, ((f2 - fA) - fA3) - 0.5f, fA2 + (0.5f * 2.0f), fA3 + (0.5f * 2.0f), color2);
            a(MatrixStackVar, (f - (fA2 / 2.0f)) - 0.5f, (f2 + fA) - 0.5f, fA2 + (0.5f * 2.0f), fA3 + (0.5f * 2.0f), color2);
            if (this.j.a()) {
                a(MatrixStackVar, (f - (2.0f / 2.0f)) - 0.5f, (f2 - (2.0f / 2.0f)) - 0.5f, 2.0f + (0.5f * 2.0f), 2.0f + (0.5f * 2.0f), color2);
            }
        }
        a(MatrixStackVar, f + fA, f2 - (fA2 / 2.0f), fA3, fA2, color);
        a(MatrixStackVar, (f - fA) - fA3, f2 - (fA2 / 2.0f), fA3, fA2, color);
        a(MatrixStackVar, f - (fA2 / 2.0f), (f2 - fA) - fA3, fA2, fA3, color);
        a(MatrixStackVar, f - (fA2 / 2.0f), f2 + fA, fA2, fA3, color);
        if (this.j.a()) {
            a(MatrixStackVar, f - (2.0f / 2.0f), f2 - (2.0f / 2.0f), 2.0f, 2.0f, color);
        }
    }

    private void a(MatrixStack MatrixStackVar, float f, float f2, float f3, float f4, Color color) {
        float f5 = f + f3;
        float f6 = f2 + f4;
        float red = color.getRed() / 255.0f;
        float green = color.getGreen() / 255.0f;
        float blue = color.getBlue() / 255.0f;
        float alpha = color.getAlpha() / 255.0f;
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f6, 0.0f).color(red, green, blue, alpha);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f5, f6, 0.0f).color(red, green, blue, alpha);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f5, f2, 0.0f).color(red, green, blue, alpha);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, f, f2, 0.0f).color(red, green, blue, alpha);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.disableBlend();
    }

    private boolean n() {
        net.minecraft.util.hit.HitResult hit = c.crosshairTarget;
        if (hit == null || hit.getType() != HitResult.Type.ENTITY || !(hit instanceof net.minecraft.util.hit.EntityHitResult)) {
            return false;
        }
        return ((net.minecraft.util.hit.EntityHitResult) hit).getEntity() instanceof LivingEntity;
    }

    private Color o() {
        if (this.n.a()) {
            return ModuleRegistry.CLIENT_COLOR.n();
        }
        return this.o.a();
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
