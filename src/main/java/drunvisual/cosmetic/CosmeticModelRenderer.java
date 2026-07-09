package drunvisual.cosmetic;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.OverlayTexture;
import org.joml.Matrix4f;
import ru.drunvisual.DrunVisual;
import drunvisual.core.Bool;
import drunvisual.model.ModelCube;
import drunvisual.model.ModelDefinition;
import drunvisual.model.ModelPart;
import drunvisual.model.ModelTransformApplier;
import drunvisual.model.ModelVertex;
import drunvisual.model.ModelVertexUv;

public class CosmeticModelRenderer {
    private static CosmeticModelRenderer c;
    private final Map<Integer, ModelDefinition> d = new ConcurrentHashMap();
    private final Map<Integer, CompiledModel> e = new ConcurrentHashMap();
    private final Set<Integer> f = ConcurrentHashMap.newKeySet();
    private final Map<Integer, Long> g = new ConcurrentHashMap();
    private final Map<Integer, Map<String, float[]>> h = new ConcurrentHashMap();
    private final CosmeticModelLoader i = new CosmeticModelLoader();
    public static int a;
    public static boolean b;

    private static class CompiledModel {
        String a;
        boolean b;
        float c;
        Map<String, PartPoseMap> d = new HashMap();
        public static int e;
        public static boolean f;

        private CompiledModel() {
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static class PartPoseMap {
        Map<Float, float[]> a = new HashMap();
        Map<Float, float[]> b = new HashMap();
        Map<Float, float[]> c = new HashMap();
        public static int d;
        public static boolean e;

        private PartPoseMap() {
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    public static CosmeticModelRenderer a() {
        if (c == null) {
            c = new CosmeticModelRenderer();
        }
        return c;
    }

    public void a(CosmeticModel cosmeticModel, MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i) {
        ModelDefinition modelDefinitionA;
        if (cosmeticModel == null || cosmeticModel.e() == null || (modelDefinitionA = a(cosmeticModel)) == null) {
            return;
        }
        CompiledModel compiledModelB = b(cosmeticModel);
        if (compiledModelB != null) {
            a(modelDefinitionA, compiledModelB, cosmeticModel.b());
        }
        VertexConsumer buffer = VertexConsumerProviderVar.getBuffer(RenderLayer.getEntityCutoutNoCull(cosmeticModel.e()));
        RenderSystem.disableCull();
        Iterator<ModelPart> it = modelDefinitionA.a.iterator();
        while (it.hasNext()) {
            a(it.next(), MatrixStackVar, buffer, i, OverlayTexture.DEFAULT_UV, 1.0f, 1.0f, 1.0f, 1.0f);
        }
        RenderSystem.enableCull();
    }

    private ModelDefinition a(CosmeticModel cosmeticModel) {
        int iB = cosmeticModel.b();
        if (this.d.containsKey(Integer.valueOf(iB))) {
            return this.d.get(Integer.valueOf(iB));
        }
        ModelDefinition modelDefinitionA = this.i.a(cosmeticModel);
        if (modelDefinitionA != null) {
            this.d.put(Integer.valueOf(iB), modelDefinitionA);
            a(iB, modelDefinitionA);
            DrunVisual.getLOGGER().info("crypt" + cosmeticModel.a());
        }
        return modelDefinitionA;
    }

    private void a(int i, ModelDefinition modelDefinition) {
        HashMap map = new HashMap();
        Iterator<ModelPart> it = modelDefinition.a.iterator();
        while (it.hasNext()) {
            a(it.next(), map);
        }
        this.h.put(Integer.valueOf(i), map);
    }

    private void a(ModelPart modelPart, Map<String, float[]> map) {
        map.put(modelPart.d, new float[]{modelPart.a(), modelPart.b(), modelPart.c(), modelPart.d(), modelPart.e(), modelPart.f(), modelPart.g(), modelPart.h(), modelPart.i()});
        Iterator<ModelPart> it = modelPart.b.iterator();
        while (it.hasNext()) {
            a(it.next(), map);
        }
    }

    private void a(ModelPart modelPart, MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, int i, int i2, float f, float f2, float f3, float f4) {
        if (modelPart.e) {
            return;
        }
        MatrixStackVar.push();
        ModelTransformApplier.translatePartOffset(modelPart, MatrixStackVar);
        ModelTransformApplier.translatePartPivot(modelPart, MatrixStackVar);
        ModelTransformApplier.rotatePart(modelPart, MatrixStackVar);
        ModelTransformApplier.scalePart(modelPart, MatrixStackVar);
        ModelTransformApplier.translatePartPivotBack(modelPart, MatrixStackVar);
        Iterator<ModelCube> it = modelPart.c.iterator();
        while (it.hasNext()) {
            a(it.next(), MatrixStackVar, VertexConsumerVar, i, i2, f, f2, f3, f4);
        }
        Iterator<ModelPart> it2 = modelPart.b.iterator();
        while (it2.hasNext()) {
            a(it2.next(), MatrixStackVar, VertexConsumerVar, i, i2, f, f2, f3, f4);
        }
        MatrixStackVar.pop();
    }

    private void a(ModelCube modelCube, MatrixStack MatrixStackVar, VertexConsumer VertexConsumerVar, int i, int i2, float f, float f2, float f3, float f4) {
        MatrixStackVar.push();
        ModelTransformApplier.translateCubePivot(modelCube, MatrixStackVar);
        ModelTransformApplier.rotateCube(modelCube, MatrixStackVar);
        ModelTransformApplier.translateCubePivotBack(modelCube, MatrixStackVar);
        MatrixStack.Entry class_4665VarPeek = MatrixStackVar.peek();
        Matrix4f matrix4fGetPositionMatrix = class_4665VarPeek.getPositionMatrix();
        for (ModelVertex modelVertex : modelCube.a) {
            if (modelVertex != null) {
                float fA = modelVertex.b.a();
                float fB = modelVertex.b.b();
                float fC = modelVertex.b.c();
                if (modelCube.b.b() == 0.0f || modelCube.b.c() == 0.0f) {
                    if (fA >= 0.0f) {
                    } else {
                        fA = -fA;
                    }
                }
                if ((modelCube.b.a() == 0.0f || modelCube.b.c() == 0.0f) && fB < 0.0f) {
                    fB = -fB;
                }
                if ((modelCube.b.a() == 0.0f || modelCube.b.b() == 0.0f) && fC < 0.0f) {
                    fC = -fC;
                }
                for (ModelVertexUv modelVertexUv : modelVertex.a) {
                    VertexConsumerVar.vertex(matrix4fGetPositionMatrix, modelVertexUv.position.a(), modelVertexUv.position.b(), modelVertexUv.position.c()).color(f, f2, f3, f4).texture(modelVertexUv.u, modelVertexUv.v).overlay(i2).light(i).normal(class_4665VarPeek, fA, fB, fC);
                }
            }
        }
        MatrixStackVar.pop();
    }

    private CompiledModel b(CosmeticModel cosmeticModel) {
        int iB = cosmeticModel.b();
        if (this.e.containsKey(Integer.valueOf(iB))) {
            return this.e.get(Integer.valueOf(iB));
        }
        if (this.f.contains(Integer.valueOf(iB))) {
            return null;
        }
        JsonObject jsonObjectQ = cosmeticModel.q();
        if (jsonObjectQ == null) {
            this.f.add(Integer.valueOf(iB));
            return null;
        }
        try {
            CompiledModel compiledModelA = a(jsonObjectQ);
            if (compiledModelA != null) {
                this.e.put(Integer.valueOf(iB), compiledModelA);

                DrunVisual.getLOGGER().info("crypt" + cosmeticModel.a());
            } else {
                this.f.add(Integer.valueOf(iB));
            }
            return compiledModelA;
        } catch (Exception e) {

            DrunVisual.getLOGGER().error("crypt" + cosmeticModel.a(), e);
            this.f.add(Integer.valueOf(iB));
            return null;
        }
    }

    private CompiledModel a(JsonObject jsonObject) {
        CompiledModel compiledModel = new CompiledModel();
        if (!jsonObject.has("crypt")) {
            return null;
        }
        Iterator it = jsonObject.getAsJsonObject("crypt").entrySet().iterator();
        if (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String str = (String) entry.getKey();
            JsonObject asJsonObject = ((JsonElement) entry.getValue()).getAsJsonObject();
            compiledModel.a = str;
            compiledModel.b = Bool.from((asJsonObject.has("crypt") && asJsonObject.get("crypt").getAsBoolean()) ? 1 : 0);
            compiledModel.c = !asJsonObject.has("crypt") ? 1.0f : asJsonObject.get("crypt").getAsFloat();
            if (asJsonObject.has("crypt")) {
                for (Map.Entry entry2 : asJsonObject.getAsJsonObject("crypt").entrySet()) {
                    String str2 = (String) entry2.getKey();
                    JsonObject asJsonObject2 = ((JsonElement) entry2.getValue()).getAsJsonObject();
                    PartPoseMap partPoseMap = new PartPoseMap();
                    if (asJsonObject2.has("crypt")) {
                        partPoseMap.a = a(asJsonObject2.get("crypt"));
                    }
                    if (asJsonObject2.has("crypt")) {
                        partPoseMap.b = a(asJsonObject2.get("crypt"));
                    }
                    if (asJsonObject2.has("crypt")) {
                        partPoseMap.c = a(asJsonObject2.get("crypt"));
                    }
                    compiledModel.d.put(str2, partPoseMap);
                }
            }
        }
        return compiledModel;
    }

    private Map<Float, float[]> a(JsonElement jsonElement) {
        HashMap map = new HashMap();
        if (jsonElement.isJsonObject()) {
            for (Map.Entry entry : jsonElement.getAsJsonObject().entrySet()) {
                try {
                    float f = Float.parseFloat((String) entry.getKey());
                    JsonElement jsonElement2 = (JsonElement) entry.getValue();
                    float[] fArr = new float[3];
                    if (jsonElement2.isJsonObject()) {
                        JsonObject asJsonObject = jsonElement2.getAsJsonObject();

                        if (asJsonObject.has("crypt")) {

                            JsonArray asJsonArray = asJsonObject.getAsJsonArray("crypt");
                            fArr[0] = asJsonArray.get(0).getAsFloat();
                            fArr[1] = asJsonArray.get(1).getAsFloat();
                            fArr[2] = asJsonArray.get(2).getAsFloat();
                        }
                    } else if (jsonElement2.isJsonArray()) {
                        JsonArray asJsonArray2 = jsonElement2.getAsJsonArray();
                        fArr[0] = asJsonArray2.get(0).getAsFloat();
                        fArr[1] = asJsonArray2.get(1).getAsFloat();
                        fArr[2] = asJsonArray2.get(2).getAsFloat();
                    }
                    map.put(Float.valueOf(f), fArr);
                } catch (NumberFormatException e) {
                }
            }
        }
        return map;
    }

    private void a(ModelDefinition modelDefinition, CompiledModel compiledModel, int i) {
        float fMin;
        Map<String, float[]> map = this.h.get(Integer.valueOf(i));
        if (map != null) {
            float fCurrentTimeMillis = (System.currentTimeMillis() - this.g.computeIfAbsent(Integer.valueOf(i), num -> {
                return Long.valueOf(System.currentTimeMillis());
            }).longValue()) / 1000.0f;
            if (!compiledModel.b) {
                fMin = Math.min(fCurrentTimeMillis, compiledModel.c);
            } else if (compiledModel.c > 0.0f) {
                fMin = fCurrentTimeMillis % compiledModel.c;
            } else {
                fMin = Math.min(fCurrentTimeMillis, compiledModel.c);
            }
            Iterator<ModelPart> it = modelDefinition.a.iterator();
            while (it.hasNext()) {
                b(it.next(), map);
            }
            for (Map.Entry<String, PartPoseMap> entry : compiledModel.d.entrySet()) {
                String key = entry.getKey();
                PartPoseMap value = entry.getValue();
                ModelPart modelPartA = a(modelDefinition, key);
                if (modelPartA != null) {
                    float[] fArr = map.get(key);
                    if (fArr == null) {
                        float[] fArr2 = new float[9];
                        fArr2[0] = 0.0f;
                        fArr2[1] = 0.0f;
                        fArr2[2] = 0.0f;
                        fArr2[3] = 0.0f;
                        fArr2[4 | 0] = 0.0f;
                        fArr2[5] = 0.0f;
                        fArr2[6] = 1.0f;
                        fArr2[7] = 1.0f;
                        fArr2[0 | 8] = 1.0f;
                        fArr = fArr2;
                    }
                    if (!value.a.isEmpty()) {
                        float[] fArrA = a(value.a, fMin);
                        modelPartA.a(fArr[0] + ((float) Math.toRadians(-fArrA[0])));
                        modelPartA.b(fArr[1] + ((float) Math.toRadians(-fArrA[1])));
                        modelPartA.c(fArr[2] + ((float) Math.toRadians(fArrA[2])));
                    }
                    if (!value.b.isEmpty()) {
                        float[] fArrA2 = a(value.b, fMin);
                        modelPartA.d(fArr[3] + fArrA2[0]);
                        modelPartA.e(fArr[4] + fArrA2[1]);
                        modelPartA.f(fArr[5] + fArrA2[2]);
                    }
                    if (!value.c.isEmpty()) {
                        float[] fArrA3 = a(value.c, fMin);
                        modelPartA.g(fArr[6] * fArrA3[0]);
                        modelPartA.h(fArr[7] * fArrA3[1]);
                        modelPartA.i(fArr[8] * fArrA3[2]);
                    }
                }
            }
        }
    }

    private void b(ModelPart modelPart, Map<String, float[]> map) {
        float[] fArr = map.get(modelPart.d);
        if (fArr != null) {
            modelPart.a(fArr[0]);
            modelPart.b(fArr[1]);
            modelPart.c(fArr[2]);
            modelPart.d(fArr[3]);
            modelPart.e(fArr[4]);
            modelPart.f(fArr[5]);
            modelPart.g(fArr[6]);
            modelPart.h(fArr[7]);
            modelPart.i(fArr[8]);
        }
        Iterator<ModelPart> it = modelPart.b.iterator();
        while (it.hasNext()) {
            b(it.next(), map);
        }
    }

    private ModelPart a(ModelDefinition modelDefinition, String str) {
        Iterator<ModelPart> it = modelDefinition.a.iterator();
        while (it.hasNext()) {
            ModelPart modelPartA = a(it.next(), str);
            if (modelPartA != null) {
                return modelPartA;
            }
        }
        return null;
    }

    private ModelPart a(ModelPart modelPart, String str) {
        if (modelPart.d.equals(str)) {
            return modelPart;
        }
        Iterator<ModelPart> it = modelPart.b.iterator();
        while (it.hasNext()) {
            ModelPart modelPartA = a(it.next(), str);
            if (modelPartA != null) {
                return modelPartA;
            }
        }
        return null;
    }

    private float[] a(Map<Float, float[]> map, float f) {
        if (map.isEmpty()) {
            float[] fArr = new float[3];
            fArr[0] = 0.0f;
            fArr[1] = 0.0f;
            fArr[2] = 0.0f;
            return fArr;
        }
        Float fValueOf = null;
        Float fValueOf2 = null;
        float[] value = null;
        float[] value2 = null;
        for (Map.Entry<Float, float[]> entry : map.entrySet()) {
            float fFloatValue = entry.getKey().floatValue();
            if (fFloatValue <= f && (fValueOf == null || fFloatValue > fValueOf.floatValue())) {
                fValueOf = Float.valueOf(fFloatValue);
                value = entry.getValue();
            }
            if (fFloatValue >= f && (fValueOf2 == null || fFloatValue < fValueOf2.floatValue())) {
                fValueOf2 = Float.valueOf(fFloatValue);
                value2 = entry.getValue();
            }
        }
        if (value == null && value2 == null) {
            float[] fArr2 = new float[3];
            fArr2[0] = 0.0f;
            fArr2[1] = 0.0f;
            fArr2[2] = 0.0f;
            return fArr2;
        }
        if (value == null) {
            return value2;
        }
        if (value2 != null && !fValueOf.equals(fValueOf2)) {
            float fFloatValue2 = (f - fValueOf.floatValue()) / (fValueOf2.floatValue() - fValueOf.floatValue());
            return new float[]{value[0] + (fFloatValue2 * (value2[0] - value[0])), value[1] + (fFloatValue2 * (value2[1] - value[1])), value[2] + (fFloatValue2 * (value2[2] - value[2]))};
        }
        return value;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
