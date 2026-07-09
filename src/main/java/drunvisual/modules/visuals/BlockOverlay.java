package drunvisual.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import java.util.Objects;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.block.BlockState;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
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
import drunvisual.events.BlockOutlineEvent;

@ModuleInfo(a = "Block Overlay", b = "Красиво выделяет блок, на который наведен игрок", c = ModuleCategory.VISUALS)
public class BlockOverlay extends ClientModule {
    private static final String FILL_NORMAL = "Обычная";
    private static final String FILL_SHADER = "Шейдер";
    private static final String ANIMATION_NONE = "Нет";
    private static final String ANIMATION_PULSE = "Пульсация";
    private static final String ANIMATION_WAVE = "Волна";
    private static final String SHADER_NEBULA = "Небула";
    private static final String SHADER_STARS = "Звёзды";
    private static final String SHADER_WEB = "Паутина";
    private static final String SHADER_PLASMA = "Плазма";
    private final SettingGroup outlineGroup = new SettingGroup("Обводка");
    private final BooleanSetting outlineEnabled = new BooleanSetting("Обводка", true);
    private final SliderSetting lineWidth;
    private final SettingGroup fillGroup;
    private final BooleanSetting fillEnabled;
    private final ModeSetting fillType;
    private final SliderSetting fillAlpha;
    private final ModeSetting shaderType;
    private final SliderSetting shaderSpeed;
    private final SliderSetting shaderAlpha;
    private final SettingGroup animationGroup;
    private final ModeSetting animationMode;
    private final SettingGroup colorGroup;
    private final BooleanSetting useClientColor;
    private final ColorSetting customColor;

    public BlockOverlay() {
        SliderSetting sliderSetting = new SliderSetting("Толщина линий", 2.0f, 1.0f, 5.0f, 0.5f);
        BooleanSetting booleanSetting = this.outlineEnabled;
        Objects.requireNonNull(booleanSetting);
        this.lineWidth = sliderSetting.a(booleanSetting::a);
        this.fillGroup = new SettingGroup("Заливка");
        this.fillEnabled = new BooleanSetting("Заливка", true);
        ModeSetting modeSetting = new ModeSetting("Тип заливки", new String[]{FILL_NORMAL, FILL_SHADER}, FILL_NORMAL);
        BooleanSetting booleanSetting2 = this.fillEnabled;
        Objects.requireNonNull(booleanSetting2);
        this.fillType = modeSetting.a(booleanSetting2::a);
        this.fillAlpha = new SliderSetting("Прозрачность заливки", 0.3f, 0.1f, 1.0f, 0.05f).a(() -> {
            return Boolean.valueOf(this.fillEnabled.a() && this.fillType.b(FILL_NORMAL));
        });
        this.shaderType = new ModeSetting(FILL_SHADER, new String[]{SHADER_NEBULA, SHADER_STARS, SHADER_WEB, SHADER_PLASMA}, SHADER_NEBULA).a(() -> {
            return Boolean.valueOf(this.fillEnabled.a() && this.fillType.b(FILL_SHADER));
        });
        this.shaderSpeed = new SliderSetting("Скорость анимации", 1.0f, 0.1f, 3.0f, 0.1f).a(() -> {
            return Boolean.valueOf(this.fillEnabled.a() && this.fillType.b(FILL_SHADER));
        });
        this.shaderAlpha = new SliderSetting("Прозрачность", 1.0f, 0.1f, 1.0f, 0.05f).a(() -> {
            return Boolean.valueOf(this.fillEnabled.a() && this.fillType.b(FILL_SHADER));
        });
        this.animationGroup = new SettingGroup("Анимация");
        this.animationMode = new ModeSetting("Режим анимации", new String[]{ANIMATION_NONE, ANIMATION_PULSE, ANIMATION_WAVE}, ANIMATION_NONE);
        this.colorGroup = new SettingGroup("Цвет");
        this.useClientColor = new BooleanSetting("Цвет клиента", true);
        this.customColor = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
            return Boolean.valueOf(!this.useClientColor.a());
        });
    }

    @EventHandler
    public void onBlockOutline(BlockOutlineEvent blockOutlineEvent) {
        blockOutlineEvent.b();
        BlockState BlockStateVarBlockState = blockOutlineEvent.blockState();
        if (BlockStateVarBlockState.isAir()) {
            return;
        }
        Box BoxVarOffset = BlockStateVarBlockState.getOutlineShape(c.world, blockOutlineEvent.blockPos()).getBoundingBox().offset(((double) blockOutlineEvent.blockPos().getX()) - blockOutlineEvent.cameraX(), ((double) blockOutlineEvent.blockPos().getY()) - blockOutlineEvent.cameraY(), ((double) blockOutlineEvent.blockPos().getZ()) - blockOutlineEvent.cameraZ());
        renderOverlay(blockOutlineEvent.matrices(), applyPrimaryAnimation(BoxVarOffset), overlayColor());
        if (this.animationMode.b(ANIMATION_WAVE)) {
            renderWave(blockOutlineEvent.matrices(), BoxVarOffset, overlayColor());
        }
    }

    private Box applyPrimaryAnimation(Box BoxVar) {
        return !this.animationMode.b(ANIMATION_PULSE) ? BoxVar : scaleBox(BoxVar, 1.0f + (((float) ((Math.sin(System.currentTimeMillis() / 260.0d) + 1.0d) * 0.5d)) * 0.04f));
    }

    private void renderOverlay(MatrixStack MatrixStackVar, Box BoxVar, Color color) {
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        RenderSystem.depthFunc(519);
        if (this.fillEnabled.a()) {
            drawFill(MatrixStackVar, BoxVar, color);
        }
        if (this.outlineEnabled.a()) {
            drawOutline(MatrixStackVar, BoxVar, color, 1.0f, this.lineWidth.a());
        }
        RenderSystem.depthFunc(515);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    private void renderWave(MatrixStack MatrixStackVar, Box BoxVar, Color color) {
        float fCurrentTimeMillis = (System.currentTimeMillis() % 1000) / 1000.0f;
        Box BoxVarScaleBox = scaleBox(BoxVar, 1.0f + (fCurrentTimeMillis * 0.18f));
        MatrixStackVar.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.depthFunc(519);
        drawOutline(MatrixStackVar, BoxVarScaleBox, color, 1.0f - fCurrentTimeMillis, Math.max(1.0f, this.lineWidth.a() - 0.5f));
        RenderSystem.depthFunc(515);
        RenderSystem.disableBlend();
        MatrixStackVar.pop();
    }

    private void drawOutline(MatrixStack MatrixStackVar, Box BoxVar, Color color, float f, float f2) {
        GL11.glEnable(2848);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        RenderSystem.lineWidth(f2);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        addBoxLines(BufferBuilderVarBegin, MatrixStackVar.peek().getPositionMatrix(), BoxVar, color, f);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.lineWidth(1.0f);
        GL11.glDisable(2848);
    }

    private void drawFill(MatrixStack MatrixStackVar, Box BoxVar, Color color) {
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_COLOR);
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        if (this.fillType.b(FILL_SHADER)) {
            addShaderBoxQuads(BufferBuilderVarBegin, matrix4fGetPositionMatrix, BoxVar, this.shaderAlpha.a());
        } else {
            addBoxQuads(BufferBuilderVarBegin, matrix4fGetPositionMatrix, BoxVar, color, this.fillAlpha.a());
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    private Color overlayColor() {
        return this.useClientColor.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.customColor.a();
    }

    private Color shaderColor(float f, float f2, float f3, float f4) {
        float fCurrentTimeMillis = ((System.currentTimeMillis() % 100000) / 1000.0f) * this.shaderSpeed.a();
        String strD = this.shaderType.d();
        if (SHADER_STARS.equals(strD)) {
            int iRound = 90 + Math.round(((float) ((Math.sin((f * 7.0f) + (f2 * 11.0f) + (f3 * 13.0f) + (fCurrentTimeMillis * 4.0f)) + 1.0d) * 0.5d)) * 165.0f);
            return new Color(iRound, iRound, 255, Math.round(f4 * 255.0f));
        }
        if (!SHADER_WEB.equals(strD)) {
            return SHADER_PLASMA.equals(strD) ? withAlpha(Color.getHSBColor(wrap((((float) (Math.sin((f * 2.4f) + fCurrentTimeMillis) + Math.cos((f3 * 2.8f) - fCurrentTimeMillis))) * 0.12f) + (fCurrentTimeMillis * 0.08f)), 0.9f, 1.0f), f4) : withAlpha(Color.getHSBColor(wrap(0.64f + (((float) Math.sin(((f + f2 + f3) * 1.7f) + fCurrentTimeMillis)) * 0.08f) + (fCurrentTimeMillis * 0.03f)), 0.75f, 1.0f), f4);
        }
        int iRound2 = 170 + Math.round(Math.abs((float) Math.sin(((f + f3) * 8.0f) + (fCurrentTimeMillis * 2.0f))) * 85.0f);
        return new Color(iRound2, iRound2, iRound2, Math.round(f4 * 255.0f));
    }

    private static Box scaleBox(Box BoxVar, float f) {
        Vec3d Vec3dVarGetCenter = BoxVar.getCenter();
        double d = (BoxVar.maxX - BoxVar.minX) * 0.5d * ((double) f);
        double d2 = (BoxVar.maxY - BoxVar.minY) * 0.5d * ((double) f);
        double d3 = (BoxVar.maxZ - BoxVar.minZ) * 0.5d * ((double) f);
        return new Box(Vec3dVarGetCenter.x - d, Vec3dVarGetCenter.y - d2, Vec3dVarGetCenter.z - d3, Vec3dVarGetCenter.x + d, Vec3dVarGetCenter.y + d2, Vec3dVarGetCenter.z + d3);
    }

    private static void addBoxLines(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Box BoxVar, Color color, float f) {
        float f2 = (float) BoxVar.minX;
        float f3 = (float) BoxVar.minY;
        float f4 = (float) BoxVar.minZ;
        float f5 = (float) BoxVar.maxX;
        float f6 = (float) BoxVar.maxY;
        float f7 = (float) BoxVar.maxZ;
        line(BufferBuilderVar, matrix4f, f2, f3, f4, f5, f3, f4, color, f);
        line(BufferBuilderVar, matrix4f, f2, f3, f4, f2, f6, f4, color, f);
        line(BufferBuilderVar, matrix4f, f2, f3, f4, f2, f3, f7, color, f);
        line(BufferBuilderVar, matrix4f, f5, f6, f7, f2, f6, f7, color, f);
        line(BufferBuilderVar, matrix4f, f5, f6, f7, f5, f3, f7, color, f);
        line(BufferBuilderVar, matrix4f, f5, f6, f7, f5, f6, f4, color, f);
        line(BufferBuilderVar, matrix4f, f2, f6, f4, f5, f6, f4, color, f);
        line(BufferBuilderVar, matrix4f, f5, f3, f4, f5, f3, f7, color, f);
        line(BufferBuilderVar, matrix4f, f2, f3, f7, f5, f3, f7, color, f);
        line(BufferBuilderVar, matrix4f, f2, f6, f7, f2, f6, f4, color, f);
        line(BufferBuilderVar, matrix4f, f2, f6, f7, f2, f3, f7, color, f);
        line(BufferBuilderVar, matrix4f, f5, f6, f4, f5, f3, f4, color, f);
    }

    private static void addBoxQuads(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Box BoxVar, Color color, float f) {
        float f2 = (float) BoxVar.minX;
        float f3 = (float) BoxVar.minY;
        float f4 = (float) BoxVar.minZ;
        float f5 = (float) BoxVar.maxX;
        float f6 = (float) BoxVar.maxY;
        float f7 = (float) BoxVar.maxZ;
        quad(BufferBuilderVar, matrix4f, color, f, f2, f3, f7, f5, f3, f7, f5, f6, f7, f2, f6, f7);
        quad(BufferBuilderVar, matrix4f, color, f, f2, f6, f4, f5, f6, f4, f5, f3, f4, f2, f3, f4);
        quad(BufferBuilderVar, matrix4f, color, f, f2, f3, f4, f2, f3, f7, f2, f6, f7, f2, f6, f4);
        quad(BufferBuilderVar, matrix4f, color, f, f5, f6, f4, f5, f6, f7, f5, f3, f7, f5, f3, f4);
        quad(BufferBuilderVar, matrix4f, color, f, f2, f6, f4, f2, f6, f7, f5, f6, f7, f5, f6, f4);
        quad(BufferBuilderVar, matrix4f, color, f, f5, f3, f4, f5, f3, f7, f2, f3, f7, f2, f3, f4);
    }

    private void addShaderBoxQuads(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Box BoxVar, float f) {
        float f2 = (float) BoxVar.minX;
        float f3 = (float) BoxVar.minY;
        float f4 = (float) BoxVar.minZ;
        float f5 = (float) BoxVar.maxX;
        float f6 = (float) BoxVar.maxY;
        float f7 = (float) BoxVar.maxZ;
        shaderQuad(BufferBuilderVar, matrix4f, f, f2, f3, f7, f5, f3, f7, f5, f6, f7, f2, f6, f7);
        shaderQuad(BufferBuilderVar, matrix4f, f, f2, f6, f4, f5, f6, f4, f5, f3, f4, f2, f3, f4);
        shaderQuad(BufferBuilderVar, matrix4f, f, f2, f3, f4, f2, f3, f7, f2, f6, f7, f2, f6, f4);
        shaderQuad(BufferBuilderVar, matrix4f, f, f5, f6, f4, f5, f6, f7, f5, f3, f7, f5, f3, f4);
        shaderQuad(BufferBuilderVar, matrix4f, f, f2, f6, f4, f2, f6, f7, f5, f6, f7, f5, f6, f4);
        shaderQuad(BufferBuilderVar, matrix4f, f, f5, f3, f4, f5, f3, f7, f2, f3, f7, f2, f3, f4);
    }

    private static void line(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, Color color, float f7) {
        vertex(BufferBuilderVar, matrix4f, f, f2, f3, color, f7);
        vertex(BufferBuilderVar, matrix4f, f4, f5, f6, color, f7);
    }

    private static void quad(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, Color color, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13) {
        vertex(BufferBuilderVar, matrix4f, f2, f3, f4, color, f);
        vertex(BufferBuilderVar, matrix4f, f5, f6, f7, color, f);
        vertex(BufferBuilderVar, matrix4f, f8, f9, f10, color, f);
        vertex(BufferBuilderVar, matrix4f, f11, f12, f13, color, f);
    }

    private void shaderQuad(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, float f10, float f11, float f12, float f13) {
        vertex(BufferBuilderVar, matrix4f, f2, f3, f4, shaderColor(f2, f3, f4, f), f);
        vertex(BufferBuilderVar, matrix4f, f5, f6, f7, shaderColor(f5, f6, f7, f), f);
        vertex(BufferBuilderVar, matrix4f, f8, f9, f10, shaderColor(f8, f9, f10, f), f);
        vertex(BufferBuilderVar, matrix4f, f11, f12, f13, shaderColor(f11, f12, f13, f), f);
    }

    private static void vertex(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, float f, float f2, float f3, Color color, float f4) {
        BufferBuilderVar.vertex(matrix4f, f, f2, f3).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, (color.getAlpha() / 255.0f) * f4);
    }

    private static Color withAlpha(Color color, float f) {
        return new Color(color.getRed(), color.getGreen(), color.getBlue(), Math.round(f * 255.0f));
    }

    private static float wrap(float f) {
        float f2 = f % 1.0f;
        return f2 < 0.0f ? f2 + 1.0f : f2;
    }
}
