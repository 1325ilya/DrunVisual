package drunvisual.cosmetic;

import java.util.Collections;
import java.util.List;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.network.AbstractClientPlayerEntity;

public class CosmeticFeatureRenderer extends FeatureRenderer<PlayerEntityRenderState, PlayerEntityModel> {
    private final CosmeticRenderController b;
    private static final String[] a = {"crypt", "crypt"};
    private static boolean c = false;

    public CosmeticFeatureRenderer(FeatureRendererContext<PlayerEntityRenderState, PlayerEntityModel> FeatureRendererContextVar) {
        super(FeatureRendererContextVar);
        this.b = CosmeticRenderController.a();
    }

    public void render(MatrixStack MatrixStackVar, VertexConsumerProvider VertexConsumerProviderVar, int i, PlayerEntityRenderState PlayerEntityRenderStateVar, float f, float f2) {
    }

    private AbstractClientPlayerEntity a(MinecraftClient MinecraftClientVar, int i) {
        return null;
    }

    private List<CosmeticModel> a(AbstractClientPlayerEntity AbstractClientPlayerEntityVar) {
        return Collections.emptyList();
    }

    private Integer a(AbstractClientPlayerEntity AbstractClientPlayerEntityVar, String str, MinecraftClient MinecraftClientVar) {
        return null;
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
