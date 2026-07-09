package ru.drunvisual.mixin.accessor;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({GameRenderer.class})
public interface GameRendererAccessor {
    @Invoker("getFov")
    float drunvisual$getFov(Camera CameraVar, float f, boolean z);
}
