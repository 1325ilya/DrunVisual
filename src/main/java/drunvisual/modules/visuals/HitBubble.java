package drunvisual.modules.visuals;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.SliderSetting;
import drunvisual.events.AttackEntityEvent;
import drunvisual.events.WorldRenderEvent;
import drunvisual.render.icons.IconTextureRegistry;

@ModuleInfo(a = "Hit Bubble", b = "Показывает анимированные пузыри при атаке", c = ModuleCategory.VISUALS)
public class HitBubble extends ClientModule {
    private static final long LIFETIME_MS = 600;
    private static final float RISE_DISTANCE = 0.6f;
    private static final float BASE_SIZE = 0.35f;
    private int hitCounter = 0;
    private final SliderSetting size = new SliderSetting("Size", BASE_SIZE, 0.15f, 0.8f, 0.05f);
    private final SliderSetting riseSpeed = new SliderSetting("Rise Speed", RISE_DISTANCE, 0.1f, 1.5f, 0.05f);
    private final CopyOnWriteArrayList<BubbleParticle> bubbles = new CopyOnWriteArrayList<>();

    private static final class BubbleParticle {
        final double x;
        final double y;
        final double z;
        final String textureKey;
        final long spawnTime;

        BubbleParticle(double d, double d2, double d3, String str, long j) {
            this.x = d;
            this.y = d2;
            this.z = d3;
            this.textureKey = str;
            this.spawnTime = j;
        }
    }

    @EventHandler
    public void onAttack(AttackEntityEvent attackEntityEvent) {
        Entity EntityVarA;
        if (k() && (EntityVarA = attackEntityEvent.a()) != null) {
            double dGetX = EntityVarA.getX();
            double dGetY = EntityVarA.getY() + ((double) EntityVarA.getHeight()) + 0.15d;
            double dGetZ = EntityVarA.getZ();
            boolean z = this.hitCounter % 2 == 0;
            this.hitCounter++;
            this.bubbles.add(new BubbleParticle(dGetX, dGetY, dGetZ, z ? IconTextureRegistry.BUBBLE_1 : IconTextureRegistry.BUBBLE_2, System.currentTimeMillis()));
        }
    }

    @EventHandler
    public void onWorldRender(WorldRenderEvent worldRenderEvent) {
        if (!k() || this.bubbles.isEmpty() || c.world == null || c.gameRenderer == null) {
            return;
        }
        MatrixStack MatrixStackVarA = worldRenderEvent.a();
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        float fGetYaw = c.gameRenderer.getCamera().getYaw();
        float fGetPitch = c.gameRenderer.getCamera().getPitch();
        long jCurrentTimeMillis = System.currentTimeMillis();
        ArrayList arrayList = new ArrayList();
        RenderSystem.enableBlend();
        RenderSystem.blendFunc(GlStateManager.SrcFactor.SRC_ALPHA, GlStateManager.DstFactor.ONE);
        RenderSystem.disableCull();
        RenderSystem.depthMask(false);
        for (BubbleParticle bubbleParticle : this.bubbles) {
            long j = jCurrentTimeMillis - bubbleParticle.spawnTime;
            if (j >= LIFETIME_MS) {
                arrayList.add(bubbleParticle);
            } else {
                renderBubble(MatrixStackVarA, Vec3dVarGetPos, fGetYaw, fGetPitch, bubbleParticle, j / 600.0f);
            }
        }
        RenderSystem.depthMask(true);
        RenderSystem.enableCull();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableBlend();
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        if (arrayList.isEmpty()) {
            return;
        }
        this.bubbles.removeAll(arrayList);
    }

    private void renderBubble(MatrixStack MatrixStackVar, Vec3d Vec3dVar, float f, float f2, BubbleParticle bubbleParticle, float f3) {
        double d = bubbleParticle.x - Vec3dVar.x;
        double dA = (bubbleParticle.y - Vec3dVar.y) + ((double) ((1.0f - ((1.0f - f3) * (1.0f - f3))) * this.riseSpeed.a()));
        double d2 = bubbleParticle.z - Vec3dVar.z;
        float fClamp = MathHelper.clamp(f3 < 0.15f ? f3 / 0.15f : f3 > 0.7f ? 1.0f - ((f3 - 0.7f) / 0.3f) : 1.0f, 0.0f, 1.0f);
        float f4 = f3 < 0.2f ? 0.4f + (0.8f * (f3 / 0.2f)) : 1.0f;
        Identifier IdentifierVar = IconTextureRegistry.get(bubbleParticle.textureKey);
        if (IdentifierVar == null) {
            return;
        }
        MatrixStackVar.push();
        MatrixStackVar.translate(d, dA, d2);
        MatrixStackVar.multiply(new Quaternionf().rotationYXZ((float) Math.toRadians(-f), (float) Math.toRadians(f2), 0.0f));
        float fA = (this.size.a() * f4) / 2.0f;
        RenderSystem.setShaderTexture(0, IdentifierVar);
        RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, fClamp);
        BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, -fA, -fA, 0.0f).texture(0.0f, 0.0f).color(1.0f, 1.0f, 1.0f, fClamp);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fA, -fA, 0.0f).texture(1.0f, 0.0f).color(1.0f, 1.0f, 1.0f, fClamp);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, fA, fA, 0.0f).texture(1.0f, 1.0f).color(1.0f, 1.0f, 1.0f, fClamp);
        BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, -fA, fA, 0.0f).texture(0.0f, 1.0f).color(1.0f, 1.0f, 1.0f, fClamp);
        BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        MatrixStackVar.pop();
    }
}
