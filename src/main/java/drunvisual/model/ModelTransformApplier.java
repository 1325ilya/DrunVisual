package drunvisual.model;

import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;

public final class ModelTransformApplier {
    private static final float MODEL_SCALE = 16.0f;

    private ModelTransformApplier() {
    }

    public static void translatePartOffset(ModelPart modelPart, MatrixStack MatrixStackVar) {
        MatrixStackVar.translate((-modelPart.d()) / MODEL_SCALE, modelPart.e() / MODEL_SCALE, modelPart.f() / MODEL_SCALE);
    }

    public static void translatePartPivot(ModelPart modelPart, MatrixStack MatrixStackVar) {
        MatrixStackVar.translate(modelPart.j() / MODEL_SCALE, modelPart.k() / MODEL_SCALE, modelPart.l() / MODEL_SCALE);
    }

    public static void translatePartPivotBack(ModelPart modelPart, MatrixStack MatrixStackVar) {
        MatrixStackVar.translate((-modelPart.j()) / MODEL_SCALE, (-modelPart.k()) / MODEL_SCALE, (-modelPart.l()) / MODEL_SCALE);
    }

    public static void rotatePart(ModelPart modelPart, MatrixStack MatrixStackVar) {
        if (modelPart.c() != 0.0f) {
            MatrixStackVar.multiply(new Quaternionf().rotationZ(modelPart.c()));
        }
        if (modelPart.b() != 0.0f) {
            MatrixStackVar.multiply(new Quaternionf().rotationY(modelPart.b()));
        }
        if (modelPart.a() != 0.0f) {
            MatrixStackVar.multiply(new Quaternionf().rotationX(modelPart.a()));
        }
    }

    public static void scalePart(ModelPart modelPart, MatrixStack MatrixStackVar) {
        MatrixStackVar.scale(modelPart.g(), modelPart.h(), modelPart.i());
    }

    public static void translateCubePivot(ModelCube modelCube, MatrixStack MatrixStackVar) {
        MatrixStackVar.translate(modelCube.c.a() / MODEL_SCALE, modelCube.c.b() / MODEL_SCALE, modelCube.c.c() / MODEL_SCALE);
    }

    public static void translateCubePivotBack(ModelCube modelCube, MatrixStack MatrixStackVar) {
        MatrixStackVar.translate((-modelCube.c.a()) / MODEL_SCALE, (-modelCube.c.b()) / MODEL_SCALE, (-modelCube.c.c()) / MODEL_SCALE);
    }

    public static void rotateCube(ModelCube modelCube, MatrixStack MatrixStackVar) {
        if (modelCube.d.c() != 0.0f) {
            MatrixStackVar.multiply(new Quaternionf().rotationZ(modelCube.d.c()));
        }
        if (modelCube.d.b() != 0.0f) {
            MatrixStackVar.multiply(new Quaternionf().rotationY(modelCube.d.b()));
        }
        if (modelCube.d.a() != 0.0f) {
            MatrixStackVar.multiply(new Quaternionf().rotationX(modelCube.d.a()));
        }
    }
}
