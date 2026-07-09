package drunvisual.effects;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ThreadLocalRandom;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gl.ShaderProgramKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Box;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.render.BufferRenderer;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.util.Identifier;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.Frustum;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import drunvisual.client.MinecraftContext;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.events.WorldRenderEvent;
import drunvisual.events.WorldRenderStartEvent;
import drunvisual.hud.core.HudService;
import drunvisual.hud.core.HudServiceInfo;
import drunvisual.util.ColorUtils;

@HudServiceInfo(enabledByDefault = true)
public class ParticleRenderService extends HudService implements MinecraftContext {
    private final CopyOnWriteArrayList<ParticleInstance> e = new CopyOnWriteArrayList<>();
    private long f = -1;
    public static int a;
    public static boolean b;

    public class ParticleInstance {
        final int a;
        Vec3d b;
        Vec3d c;
        Vec3d d;
        final float e;
        final CopyOnWriteArrayList<ParticleInstance> f;
        final Identifier g;
        final double i;
        final String j;
        final int m;
        private static final float p = 0.98f;
        final float h = ThreadLocalRandom.current().nextFloat() * 360.0f;
        int k = 0;
        float l = 0.0f;

        ParticleInstance(ParticleRenderService particleRenderService, Vec3d Vec3dVar, Vec3d Vec3dVar2, int i, float f, Identifier IdentifierVar, CopyOnWriteArrayList<ParticleInstance> copyOnWriteArrayList, int i2, double d, String str) {
            this.a = i;
            this.f = copyOnWriteArrayList;
            this.c = Vec3dVar;
            this.b = Vec3dVar;
            this.d = Vec3dVar2;
            this.e = f;
            this.g = IdentifierVar;
            this.m = i2;
            this.i = d;
            this.j = str;
        }

        void a() {
            int i = this.k;
            this.k = (i | 1) + (i & 1);
            if (this.k > this.a) {
                this.f.remove(this);
            }
            float f = 0.2f * 20.0f;
            if (this.k <= f) {
                this.l = this.k / f;
            } else if (this.k < this.a - f) {
                this.l = 1.0f;
            } else {
                int i2 = this.k;
                int i3 = this.a;
                this.l = ((i3 & (i2 ^ (-1))) - ((i3 ^ (-1)) & i2)) / f;
            }
            if (MinecraftContext.c.world == null || MinecraftContext.c.player == null) {
                this.f.remove(this);
                return;
            }
            String str = this.j;
            byte b = -1;
            switch (str.hashCode()) {
                case -978367247:
                    break;
                case -280961929:
                    break;
                case 440503968:
                    break;
                case 449348564:
                    break;
            }
            switch (b) {
                case EventPriority.MEDIUM /* 0 */:
                    b();
                    break;
                case ConfigState.a /* 1 */:
                    c();
                    break;
                case 2:
                    d();
                    break;
                case 3:
                    f();
                    break;
                default:
                    b();
                    break;
            }
        }

        private void b() {
            this.d = this.d.multiply(0.9800000190734863d);
            this.d = this.d.add(new Vec3d(0.0d, -this.i, 0.0d));
            this.d = this.d.add(new Vec3d(ThreadLocalRandom.current().nextDouble(-0.01d, 0.01d), ThreadLocalRandom.current().nextDouble(-0.01d, 0.01d), ThreadLocalRandom.current().nextDouble(-0.01d, 0.01d)));
            Vec3d Vec3dVarAdd = this.c.add(this.d);
            BlockHitResult BlockHitResultVarRaycast = MinecraftContext.c.world.raycast(new RaycastContext(this.c, Vec3dVarAdd, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, MinecraftContext.c.player));
            if (BlockHitResultVarRaycast.getType() == HitResult.Type.BLOCK) {
                Vec3d Vec3dVarGetPos = BlockHitResultVarRaycast.getPos();
                Direction DirectionVarGetSide = BlockHitResultVarRaycast.getSide();
                Vec3d Vec3dVar = new Vec3d(DirectionVarGetSide.getOffsetX(), DirectionVarGetSide.getOffsetY(), DirectionVarGetSide.getOffsetZ());
                Vec3d Vec3dVarSubtract = this.d.subtract(Vec3dVar.multiply(2.0d * this.d.dotProduct(Vec3dVar)));
                if (Vec3dVarSubtract.length() >= 0.1d) {
                    this.d = Vec3dVarSubtract.multiply(0.5d);
                } else {
                    this.d = new Vec3d(0.0d, 0.0d, 0.0d);
                }
                Vec3dVarAdd = Vec3dVarGetPos.add(this.d.normalize().multiply(0.001d));
            }
            this.b = this.c;
            this.c = Vec3dVarAdd;
        }

        private void c() {
            this.d = this.d.multiply(0.9800000190734863d);
            this.d = this.d.add(new Vec3d(0.0d, -this.i, 0.0d));
            this.d = this.d.add(new Vec3d(ThreadLocalRandom.current().nextDouble(-0.01d, 0.01d), ThreadLocalRandom.current().nextDouble(-0.01d, 0.01d), ThreadLocalRandom.current().nextDouble(-0.01d, 0.01d)));
            this.b = this.c;
            this.c = this.c.add(this.d);
            if (e()) {
                this.f.remove(this);
            }
        }

        private void d() {
            this.b = this.c;
            this.c = this.c.add(this.d);
            if (e()) {
                this.f.remove(this);
            }
        }

        private boolean e() {
            BlockPos BlockPosVarOfFloored = BlockPos.ofFloored(this.c);
            if (!MinecraftContext.c.world.getBlockState(BlockPosVarOfFloored).isSolidBlock(MinecraftContext.c.world, BlockPosVarOfFloored)) {
                return false;
            }
            double d = ((double) this.e) * 0.5d;
            double dGetX = this.c.x - ((double) BlockPosVarOfFloored.getX());
            double dGetY = this.c.y - ((double) BlockPosVarOfFloored.getY());
            double dGetZ = this.c.z - ((double) BlockPosVarOfFloored.getZ());
            return Bool.from((dGetX <= d || dGetX >= 1.0d - d || dGetY <= d || dGetY >= 1.0d - d || dGetZ <= d || dGetZ >= 1.0d - d) ? 0 : 1);
        }

        private void f() {
            Vec3d Vec3dVarSubtract = MinecraftContext.c.player.getPos().add(0.0d, MinecraftContext.c.player.getEyeHeight(MinecraftContext.c.player.getPose()), 0.0d).subtract(this.c);
            double dLength = Vec3dVarSubtract.length();
            if (dLength > 0.1d) {
                this.d = this.d.add(Vec3dVarSubtract.normalize().multiply(0.02d * (1.0d / Math.max(dLength, 1.0d))));
            }
            this.d = this.d.multiply(0.9800000190734863d);
            this.d = this.d.add(new Vec3d(ThreadLocalRandom.current().nextDouble(-0.008d, 0.008d), ThreadLocalRandom.current().nextDouble(-0.008d, 0.008d), ThreadLocalRandom.current().nextDouble(-0.008d, 0.008d)));
            this.b = this.c;
            this.c = this.c.add(this.d);
        }

        ParticleVertex a(Frustum FrustumVar) {
            Vec3d Vec3dVarAdd = this.b.add(this.c.subtract(this.b).multiply(MinecraftContext.c.getRenderTickCounter().getTickDelta(false))).add(0.0d, 0.25d, 0.0d);
            double d = ((double) this.e) * 0.5d;
            if (!FrustumVar.isVisible(new Box(Vec3dVarAdd.x - d, Vec3dVarAdd.y - d, Vec3dVarAdd.z - d, Vec3dVarAdd.x + d, Vec3dVarAdd.y + d, Vec3dVarAdd.z + d))) {
                return null;
            }
            int iA = ColorUtils.a(this.m, (int) (this.l * 255.0f));
            int i = iA >> 16;
            int i2 = ((i ^ (-1)) | 255) - (i ^ (-1));
            int i3 = iA >> 8;
            int i4 = ((i3 ^ (-1)) | 255) - (i3 ^ (-1));
            int i5 = ((iA ^ (-1)) | 255) - (iA ^ (-1));
            int i6 = iA >> 24;
            int i7 = ((i6 ^ (-1)) | 255) - (i6 ^ (-1));
            int i8 = (2 * (i2 | i4)) - (i2 ^ i4);
            return new ParticleVertex(Vec3dVarAdd, this.e, this.h, i2, i4, i5, i7, ((2 * (i8 | i5)) - (i8 ^ i5)) / 765.0f);
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public static class ParticleVertex {
        Vec3d a;
        float b;
        float c;
        int d;
        int e;
        int f;
        int g;
        float h;
        public static int i;
        public static boolean j;

        ParticleVertex(Vec3d Vec3dVar, float f, float f2, int i2, int i3, int i4, int i5, float f3) {
            this.a = Vec3dVar;
            this.b = f;
            this.c = f2;
            this.d = i2;
            this.e = i3;
            this.f = i4;
            this.g = i5;
            this.h = f3;
        }

        public static String a(String str, String str2, int i2, int i3, int i4, int i5) {
            return null;
        }
    }

    public void a(Vec3d Vec3dVar, Vec3d Vec3dVar2, int i, float f, Identifier IdentifierVar, int i2, double d, String str) {
        this.e.add(new ParticleInstance(this, Vec3dVar, Vec3dVar2, i, f, IdentifierVar, this.e, i2, d, str));
    }

    public int d() {
        return this.e.size();
    }

    public void a(Vec3d Vec3dVar, Vec3d Vec3dVar2, int i, float f, Identifier IdentifierVar, int i2, String str) {
        a(Vec3dVar, Vec3dVar2, i, f, IdentifierVar, i2, 0.02d, str);
    }

    public void a(Vec3d Vec3dVar, Vec3d Vec3dVar2, int i, float f, Identifier IdentifierVar, int i2, double d) {
        a(Vec3dVar, Vec3dVar2, i, f, IdentifierVar, i2, d, "Реалистичная");
    }

    public void a(Vec3d Vec3dVar, Vec3d Vec3dVar2, int i, float f, Identifier IdentifierVar, int i2) {
        a(Vec3dVar, Vec3dVar2, i, f, IdentifierVar, i2, 0.02d, "Реалистичная");
    }

    @EventHandler
    public void a(WorldRenderStartEvent worldRenderStartEvent) {
        if (c.world != null) {
            long jGetTime = c.world.getTime();
            if (jGetTime != this.f) {
                this.e.forEach(particleInstance -> {
                    particleInstance.a();
                });
                this.f = jGetTime;
            }
        }
    }

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        if (c.gameRenderer == null || c.gameRenderer.getCamera() == null) {
            return;
        }
        Frustum FrustumVar = new Frustum(new Matrix4f(worldRenderEvent.a().peek().getPositionMatrix()), new Matrix4f(RenderSystem.getProjectionMatrix()));
        FrustumVar.setPosition(c.gameRenderer.getCamera().getPos().x, c.gameRenderer.getCamera().getPos().y, c.gameRenderer.getCamera().getPos().z);
        a(worldRenderEvent.a(), FrustumVar);
    }

    private void a(MatrixStack MatrixStackVar, Frustum FrustumVar) {
        if (this.e.isEmpty()) {
            return;
        }
        HashMap<Identifier, List<ParticleVertex>> map = new HashMap<>();
        for (ParticleInstance particleInstance : this.e) {
            ParticleVertex particleVertexA = particleInstance.a(FrustumVar);
            if (particleVertexA != null) {
                map.computeIfAbsent(particleInstance.g, IdentifierVar -> {
                    return new ArrayList<>();
                }).add(particleVertexA);
            }
        }
        Vec3d Vec3dVarGetPos = c.gameRenderer.getCamera().getPos();
        for (Map.Entry<Identifier, List<ParticleVertex>> entry : map.entrySet()) {
            Identifier IdentifierVar2 = entry.getKey();
            List<ParticleVertex> list = entry.getValue();
            RenderSystem.setShaderTexture(0, IdentifierVar2);
            RenderSystem.setShader(ShaderProgramKeys.POSITION_TEX_COLOR);
            RenderSystem.enableDepthTest();
            RenderSystem.depthFunc(515);
            RenderSystem.disableCull();
            RenderSystem.depthMask(false);
            RenderSystem.enableBlend();
            boolean zAnyMatch = list.stream().anyMatch(particleVertex -> {
                return Bool.from(particleVertex.h >= 0.2f ? 0 : 1);
            });
            boolean zAnyMatch2 = list.stream().anyMatch(particleVertex2 -> {
                return Bool.from(particleVertex2.h < 0.2f ? 0 : 1);
            });
            if (zAnyMatch && zAnyMatch2) {
                a(MatrixStackVar, (ParticleVertex[]) list.stream().filter(particleVertex3 -> {
                    int i;
                    if (particleVertex3.h >= 0.2f) {
                        i = 0;
                    } else {
                        i = 1;
                    }
                    return Bool.from(i);
                }).toArray(i -> {
                    return new ParticleVertex[i];
                }), Vec3dVarGetPos, 770, 771);
                a(MatrixStackVar, (ParticleVertex[]) list.stream().filter(particleVertex4 -> {
                    return Bool.from(particleVertex4.h < 0.2f ? 0 : 1);
                }).toArray(i2 -> {
                    return new ParticleVertex[i2];
                }), Vec3dVarGetPos, 770, 1);
            } else if (zAnyMatch) {
                RenderSystem.blendFunc(770, 771);
                a(MatrixStackVar, (ParticleVertex[]) list.toArray(new ParticleVertex[0]), Vec3dVarGetPos, 770, 771);
            } else {
                RenderSystem.blendFunc(770, 1);
                a(MatrixStackVar, (ParticleVertex[]) list.toArray(new ParticleVertex[0]), Vec3dVarGetPos, 770, 1);
            }
            RenderSystem.defaultBlendFunc();
            RenderSystem.depthMask(true);
            RenderSystem.depthFunc(515);
            RenderSystem.enableCull();
        }
    }

    private void a(MatrixStack MatrixStackVar, ParticleVertex[] particleVertexArr, Vec3d Vec3dVar, int i, int i2) {
        if (particleVertexArr.length != 0) {
            RenderSystem.blendFunc(i, i2);
            BufferBuilder BufferBuilderVarBegin = Tessellator.getInstance().begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
            int length = particleVertexArr.length;
            int i3 = 0;
            while (i3 < length) {
                ParticleVertex particleVertex = particleVertexArr[i3];
                MatrixStackVar.push();
                MatrixStackVar.translate((-Vec3dVar.x) + particleVertex.a.x, (-Vec3dVar.y) + particleVertex.a.y, (-Vec3dVar.z) + particleVertex.a.z);
                MatrixStackVar.multiply(c.gameRenderer.getCamera().getRotation());
                MatrixStackVar.multiply(new Quaternionf().rotationZ((float) Math.toRadians(particleVertex.c)));
                MatrixStackVar.scale(particleVertex.b, particleVertex.b, particleVertex.b);
                Matrix4f matrix4fGetPositionMatrix = MatrixStackVar.peek().getPositionMatrix();
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, -0.5f, -0.5f, 0.0f).texture(0.0f, 0.0f).color(particleVertex.d, particleVertex.e, particleVertex.f, particleVertex.g);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, 0.5f, -0.5f, 0.0f).texture(1.0f, 0.0f).color(particleVertex.d, particleVertex.e, particleVertex.f, particleVertex.g);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, 0.5f, 0.5f, 0.0f).texture(1.0f, 1.0f).color(particleVertex.d, particleVertex.e, particleVertex.f, particleVertex.g);
                BufferBuilderVarBegin.vertex(matrix4fGetPositionMatrix, -0.5f, 0.5f, 0.0f).texture(0.0f, 1.0f).color(particleVertex.d, particleVertex.e, particleVertex.f, particleVertex.g);
                MatrixStackVar.pop();
                i3++;
            }
            BufferRenderer.drawWithGlobalProgram(BufferBuilderVarBegin.end());
        }
    }

    public static String b(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
