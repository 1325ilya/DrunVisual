package ru.drunvisual.mixin;

import net.minecraft.client.texture.NativeImage;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import drunvisual.render.NativeImagePointerAccessor;

@Mixin({NativeImage.class})
public class NativeImageMixin implements NativeImagePointerAccessor {

    @Shadow
    private long pointer;

    @Override
    public long pointer() {
        return this.pointer;
    }
}
