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

@ModuleInfo(a = "Nimb", b = "Draws a halo above the player.", c = ModuleCategory.VISUALS)
public class Nimb extends ClientModule {
    private static final int SEGMENTS = 72;
    private final SliderSetting lineWidth = new SliderSetting("Line Width", 1.5f, 1.0f, 3.0f, 0.5f);
    private final SliderSetting radius = new SliderSetting("Radius", 0.28f, 0.18f, 0.55f, 0.02f);
    private final SliderSetting yOffset = new SliderSetting("Y Offset", 0.05f, -0.3f, 0.25f, 0.02f);
    private final SettingGroup colorGroup = new SettingGroup("Color");
    private final BooleanSetting useClientColor = new BooleanSetting("Use Client Color", true);
    private final ColorSetting color = new ColorSetting("Custom Color", Color.WHITE).a(() -> {
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
        RenderSystem.lineWidth(this.lineWidth.a());
        for (net.minecraft.client.network.AbstractClientPlayerEntity ClientPlayerEntityVar : c.world.getPlayers()) {
            if (!ClientPlayerEntityVar.isInvisible() && (ClientPlayerEntityVar != c.player || !c.options.getPerspective().isFirstPerson())) {
                renderHalo(MatrixStackVarA, Vec3dVarGetPos, ClientPlayerEntityVar, worldRenderEvent.b());
            }
        }
        RenderSystem.lineWidth(1.0f);
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.disableBlend();
        MatrixStackVarA.pop();
    }

    private void renderHalo(MatrixStack MatrixStackVar, Vec3d Vec3dVar, PlayerEntity PlayerEntityVar, float f) {
        double dLerp = MathHelper.lerp(f, PlayerEntityVar.prevX, PlayerEntityVar.getX()) - Vec3dVar.x;
        double dMethod_164362 = (MathHelper.lerp(f, PlayerEntityVar.prevY, PlayerEntityVar.getY()) - Vec3dVar.y) + ((double) PlayerEntityVar.getEyeHeight(PlayerEntityVar.getPose())) + 0.05d + ((double) this.yOffset.a());
        double dMethod_164363 = MathHelper.lerp(f, PlayerEntityVar.prevZ, PlayerEntityVar.getZ()) - Vec3dVar.z;
        float fA = this.radius.a();
        Color colorN = n();
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        RenderSystem.setShader(ShaderProgramKeys.POSITION_COLOR);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.DEBUG_LINE_STRIP, VertexFormats.POSITION_COLOR);
        for (int i = 0; i <= SEGMENTS; i++) {
            double d = ((double) (i / 72.0f)) * 3.141592653589793d * 2.0d;
            vertex(BufferBuilderVarBegin, matrix4fGetPositionMatrix, dLerp + (Math.cos(d) * ((double) fA)), dMethod_164362, dMethod_164363 + (Math.sin(d) * ((double) fA)), colorN, 0.9f);
        }
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
    }

    private Color n() {
        return this.useClientColor.a() ? ModuleRegistry.CLIENT_COLOR.n() : this.color.a();
    }

    private static void vertex(BufferBuilder BufferBuilderVar, Matrix4f matrix4f, double d, double d2, double d3, Color color, float f) {
        BufferBuilderVar.vertex(matrix4f, (float) d, (float) d2, (float) d3).color(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f, f);
    }
}
