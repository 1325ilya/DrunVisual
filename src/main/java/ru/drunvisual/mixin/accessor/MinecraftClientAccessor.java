package ru.drunvisual.mixin.accessor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.session.Session;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin({MinecraftClient.class})
public interface MinecraftClientAccessor {
    @Accessor("session")
    Session getSession();

    @Invoker("doItemUse")
    void invokeDoItemUse();

    @Invoker("doAttack")
    boolean invokeDoAttack();
}
