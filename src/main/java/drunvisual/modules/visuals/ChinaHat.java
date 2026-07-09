package drunvisual.modules.visuals;

import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
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
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.events.WorldRenderEvent;

@ModuleInfo(a = "China Hat", b = "Draws a cone hat above players.", c = ModuleCategory.VISUALS)
public class ChinaHat extends ClientModule {
    private static final int SEGMENTS = 90;
    private final SliderSetting height = new SliderSetting("Height", 0.3f, 0.1f, 0.6f, 0.05f);
    private final SliderSetting radius = new SliderSetting("Radius", 0.5f, 0.1f, 1.0f, 0.05f);
    private final BooleanSetting followSneaking = new BooleanSetting("Follow Sneaking", true);
    private final SettingGroup colorGroup = new SettingGroup("Color");
    private final BooleanSetting useClientColor = new BooleanSetting("Use Client Color", true);
    private final ColorSetting color = new ColorSetting("Custom Color", new Color(120, 80, 255)).a(() -> {
        return Boolean.valueOf(!this.useClientColor.a());
    });

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        if (c.player == null || c.world == null || c.gameRenderer == null) {
            return;
        }
        MatrixStack MatrixStackVarA = worldRenderEvent.a();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        MatrixStackVarA.push();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableCull();
        RenderSystem.enableDepthTest();
        RenderSystem.depthMask(false);
        for (net.minecraft.client.network.AbstractClientPlayerEntity ClientPlayerEntityVar : c.world.getPlayers()) {
            if (!ClientPlayerEntityVar.isInvisible() && (ClientPlayerEntityVar != c.player || !c.options.getPerspective().isFirstPerson())) {
                renderHat(MatrixStackVarA, Vec3dVarGetPos, ClientPlayerEntityVar, worldRenderEvent.b());
            }
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        MatrixStackVarA.pop();
    }

    private void renderHat(MatrixStack MatrixStackVar, Vec3d Vec3dVar, PlayerEntity PlayerEntityVar, float f) {
        double dLerp = MathHelper.lerp(f, PlayerEntityVar.prevX, PlayerEntityVar.getX()) - Vec3dVar.x;
        double dMethod_164362 = (MathHelper.lerp(f, PlayerEntityVar.prevY, PlayerEntityVar.getY()) - Vec3dVar.y) + ((double) PlayerEntityVar.getHeight()) + 0.05d;
        double dMethod_164363 = MathHelper.lerp(f, PlayerEntityVar.prevZ, PlayerEntityVar.getZ()) - Vec3dVar.z;
        if (this.followSneaking.a() && PlayerEntityVar.isSneaking()) {
            dMethod_164362 -= 0.18d;
        }
        float fA = this.radius.a();
        float fA2 = this.height.a();
        Color colorN = n();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.TRIANGLES, VertexFormats.POSITION_COLOR);
        for (int i = 0; i < SEGMENTS; i++) {
            float f2 = i / 90.0f;
            float f3 = (i + 1) / 90.0f;
            double d = ((double) f2) * 3.141592653589793d * 2.0d;
            double d2 = ((double) f3) * 3.141592653589793d * 2.0d;
            Color colorA = a(colorN, f2);
            vertex(BufferBuilderVarBegin, matrix4fGetPositionMatrix, dLerp, dMethod_164362 + ((double) fA2), dMethod_164363, colorA, 0.55f);
            vertex(BufferBuilderVarBegin, matrix4fGetPositionMatrix, dLerp + (Math.cos(d) * ((double) fA)), dMethod_164362, dMethod_164363 + (Math.sin(d) * ((double) fA)), colorA, 0.28f);
            vertex(BufferBuilderVarBegin, matrix4fGetPositionMatrix, dLerp + (Math.cos(d2) * ((double) fA)), dMethod_164362, dMethod_164363 + (Math.sin(d2) * ((double) fA)), a(colorN, f3), 0.28f);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        RenderSystem.lineWidth(2.0f);
        BufferBuilder BufferBuilderVarMethod_608272 = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINES, VertexFormats.POSITION_COLOR);
        for (int i2 = 0; i2 < SEGMENTS; i2++) {
            float f4 = i2 / 90.0f;
            float f5 = (i2 + 1) / 90.0f;
            double d3 = ((double) f4) * 3.141592653589793d * 2.0d;
            double d4 = ((double) f5) * 3.141592653589793d * 2.0d;
            vertex(BufferBuilderVarMethod_608272, matrix4fGetPositionMatrix, dLerp + (Math.cos(d3) * ((double) fA)), dMethod_164362, dMethod_164363 + (Math.sin(d3) * ((double) fA)), a(colorN, f4), 0.95f);
            vertex(BufferBuilderVarMethod_608272, matrix4fGetPositionMatrix, dLerp + (Math.cos(d4) * ((double) fA)), dMethod_164362, dMethod_164363 + (Math.sin(d4) * ((double) fA)), a(colorN, f5), 0.95f);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarMethod_608272.end());
        RenderSystem.lineWidth(1.0f);
    }

    private Color n() {
        return this.useClientColor.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.color.a();
    }

    private Color a(Color color, float f) {
        float[] fArrRGBtoHSB = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), (float[]) null);
        fArrRGBtoHSB[0] = (fArrRGBtoHSB[0] + (((float) Math.sin(((double) f) * 3.141592653589793d)) * 0.08f)) % 1.0f;
        if (fArrRGBtoHSB[0] < 0.0f) {
            fArrRGBtoHSB[0] = fArrRGBtoHSB[0] + 1.0f;
        }
        return Color.getHSBColor(fArrRGBtoHSB[0], fArrRGBtoHSB[1], fArrRGBtoHSB[2]);
    }

    private static void vertex(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, double d, double d2, double d3, Color color, float f) {
        BufferBuilderVar.vertex(matrix4f, (float) d, (float) d2, (float) d3).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, f);
    }
}
