package drunvisual.render;

import com.mojang.blaze3d.systems.RenderSystem;

public final class BlendRenderScope {
    private BlendRenderScope() {
    }

    public static void runBlended(Runnable runnable) {
        RenderSystem.enableBlend();
        FramebufferCapture.applyBlendState();
        runnable.run();
        RenderSystem.disableBlend();
    }
}
