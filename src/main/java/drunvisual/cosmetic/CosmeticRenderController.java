package drunvisual.cosmetic;

import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import drunvisual.config.ConfigState;
import drunvisual.render.AttachmentPoint;
import drunvisual.render.MatrixTransformStack;

public class CosmeticRenderController {
    private static CosmeticRenderController c;
    private final CosmeticModelRenderer d = CosmeticModelRenderer.a();
    private final MatrixTransformStack e = new MatrixTransformStack();
    private static final float f = 57.295776f;
    public static int a;
    public static boolean b;

    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$drunvisual$AttachmentPoint = new int[AttachmentPoint.values().length];

        static {
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.HEAD.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.ABOVE_HEAD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.BODY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.RIGHT_ARM.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.LEFT_ARM.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.RIGHT_LEG.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.LEFT_LEG.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
            try {
                $SwitchMap$drunvisual$AttachmentPoint[AttachmentPoint.FREE.ordinal()] = 8;
            } catch (NoSuchFieldError e8) {
            }
        }
    }

    public static CosmeticRenderController a() {
        if (c == null) {
            c = new CosmeticRenderController();
        }
        return c;
    }

    public void a(CosmeticModel cosmeticModel, AbstractClientPlayerEntity AbstractClientPlayerEntityVar, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, PlayerEntityModel PlayerEntityModelVar, float f2) {
        if (cosmeticModel == null || cosmeticModel.e() == null) {
            return;
        }
        this.e.a(MatrixStackVar);
        this.e.a();
        float fA = a(cosmeticModel, PlayerEntityModelVar);
        this.e.f(180.0f);
        this.e.a(cosmeticModel.h(), cosmeticModel.i() + fA, cosmeticModel.j());
        this.e.e(cosmeticModel.k());
        this.e.d(cosmeticModel.l());
        this.e.f(cosmeticModel.m());
        this.e.b(cosmeticModel.g(), cosmeticModel.g(), cosmeticModel.g());
        this.d.a(cosmeticModel, MatrixStackVar, VertexConsumerProviderVar, i);
        this.e.b();
    }

    private float a(CosmeticModel cosmeticModel, PlayerEntityModel PlayerEntityModelVar) {
        float f2 = 0.0f;
        AttachmentPoint attachmentPointF = cosmeticModel.f();
        if (PlayerEntityModelVar == null) {
            return 0.0f;
        }
        switch (AnonymousClass1.$SwitchMap$drunvisual$AttachmentPoint[attachmentPointF.ordinal()]) {
            case ConfigState.a /* 1 */:
                a(PlayerEntityModelVar.head);
                f2 = 0.5f;
                break;
            case 2:
                f2 = 0.75f;
                break;
            case 3:
                a(PlayerEntityModelVar.body);
                f2 = -0.3f;
                break;
            case 4:
                a(PlayerEntityModelVar.rightArm);
                f2 = -0.25f;
                break;
            case 5:
                a(PlayerEntityModelVar.leftArm);
                f2 = -0.25f;
                break;
            case 6:
                a(PlayerEntityModelVar.rightLeg);
                f2 = -0.35f;
                break;
            case 7:
                a(PlayerEntityModelVar.leftLeg);
                f2 = -0.35f;
                break;
        }
        return f2;
    }

    private void a(ModelPart ModelPartVar) {
        this.e.a(ModelPartVar.pivotX * 0.0625f, ModelPartVar.pivotY * 0.0625f, ModelPartVar.pivotZ * 0.0625f);
        this.e.d(ModelPartVar.pitch * f, ModelPartVar.yaw * f, ModelPartVar.roll * f);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
