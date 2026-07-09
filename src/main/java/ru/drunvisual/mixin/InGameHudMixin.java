package ru.drunvisual.mixin;

import java.util.function.Function;
import net.minecraft.util.Arm;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.Identifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.PlayerListHud;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import drunvisual.modules.visuals.Animations;
import drunvisual.inventory.CooldownInfo;
import drunvisual.modules.utilities.Cooldowns;
import drunvisual.modules.visuals.Crosshair;
import drunvisual.events.EventBusService;
import drunvisual.modules.utilities.FastSwap;
import drunvisual.gui.core.GuiInput;
import drunvisual.animation.HotbarSelectionAnimation;
import drunvisual.hud.core.HudElementManager;
import drunvisual.events.HudRenderPostEvent;
import drunvisual.events.HudRenderPreEvent;
import drunvisual.util.KeyNameFormatter;
import drunvisual.animation.PlayerListScaleAnimation;
import drunvisual.modules.visuals.RenderTweaks;
import drunvisual.render.ScreenPoint;
import drunvisual.render.ScreenScale;
import drunvisual.module.ModuleRegistry;

@Mixin({InGameHud.class})
public abstract class InGameHudMixin {

    @Shadow
    @Final
    private static Identifier HOTBAR_SELECTION_TEXTURE;

    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Final
    private PlayerListHud playerListHud;

    @Unique
    private int deferredHighlightY;

    @Unique
    private boolean deferHighlight = false;

    @Unique
    private boolean prevTabShown = false;

    @Shadow
    private void renderHotbarItem(DrawContext DrawContextVar, int i, int i2, RenderTickCounter RenderTickCounterVar, PlayerEntity PlayerEntityVar, ItemStack ItemStackVar, int i3) {
    }

    @Inject(method = {"renderCrosshair"}, at = {@At("HEAD")}, cancellable = true)
    private void onRenderCrosshair(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        Crosshair crosshair = ModuleRegistry.CROSSHAIR;
        if (crosshair == null || !crosshair.k()) {
            return;
        }
        callbackInfo.cancel();
    }

    @Unique
    private boolean isHotbarAnimationEnabled() {
        return ModuleRegistry.ANIMATIONS != null && ModuleRegistry.ANIMATIONS.k() && ModuleRegistry.ANIMATIONS.g.a();
    }

    @Inject(method = {"renderHotbar"}, at = {@At("HEAD")})
    private void onRenderHotbarHead(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        if (!isHotbarAnimationEnabled() || this.client.player == null) {
            return;
        }
        HotbarSelectionAnimation hotbarSelectionAnimationP = Animations.p();
        hotbarSelectionAnimationP.a(this.client.player.getInventory().selectedSlot, DrawContextVar.getScaledWindowWidth() / 2, (long) ModuleRegistry.ANIMATIONS.h.k().floatValue());
        hotbarSelectionAnimationP.a();
    }

    @Redirect(method = {"renderHotbar"}, at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawGuiTexture(Ljava/util/function/Function;Lnet/minecraft/util/Identifier;IIII)V", ordinal = 1))
    private void redirectHotbarSelection(DrawContext DrawContextVar, Function<Identifier, RenderLayer> function, Identifier IdentifierVar, int i, int i2, int i3, int i4) {
        if (!isHotbarAnimationEnabled()) {
            DrawContextVar.drawGuiTexture(function, IdentifierVar, i, i2, i3, i4);
        } else {
            this.deferredHighlightY = i2;
            this.deferHighlight = true;
        }
    }

    @Inject(method = {"renderHotbar"}, at = {@At("RETURN")})
    private void renderDeferredHighlight(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        if (this.deferHighlight) {
            this.deferHighlight = false;
            int iRound = Math.round(Animations.p().b());
            DrawContextVar.draw();
            MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
            MatrixStackVarGetMatrices.push();
            MatrixStackVarGetMatrices.translate(0.0f, 0.0f, 200.0f);
            DrawContextVar.drawGuiTexture(RenderLayer::getGuiTextured, HOTBAR_SELECTION_TEXTURE, iRound, this.deferredHighlightY, 24, 23);
            MatrixStackVarGetMatrices.pop();
        }
    }

    @Inject(method = {"renderHotbar"}, at = {@At("RETURN")})
    private void renderArmorHud(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        ClientPlayerEntity ClientPlayerEntityVar;
        if (!ModuleRegistry.ARMOR_HUD.k() || MinecraftClient.getInstance().player == null || (ClientPlayerEntityVar = this.client.player) == null) {
            return;
        }
        int iGetScaledWindowWidth = DrawContextVar.getScaledWindowWidth() / 2;
        int i = (ClientPlayerEntityVar.getMainArm().getOpposite() != Arm.RIGHT || ClientPlayerEntityVar.getOffHandStack().isEmpty()) ? 0 : 24;
        for (int i2 = 0; i2 < 4; i2++) {
            renderHotbarItem(DrawContextVar, iGetScaledWindowWidth + 92 + 10 + (i2 * 20) + 2 + i, (DrawContextVar.getScaledWindowHeight() - 16) - 3, RenderTickCounterVar, ClientPlayerEntityVar, ClientPlayerEntityVar.getInventory().getArmorStack(3 - i2), i2 + 1);
        }
    }

    @Inject(method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"}, at = {@At("HEAD")}, cancellable = true)
    private void onRenderScoreboardSidebar(DrawContext DrawContextVar, ScoreboardObjective ScoreboardObjectiveVar, CallbackInfo callbackInfo) {
        RenderTweaks renderTweaks = ModuleRegistry.RENDER_TWEAKS;
        if (renderTweaks == null || !renderTweaks.r()) {
            return;
        }
        callbackInfo.cancel();
    }

    @Inject(method = {"render"}, at = {@At("HEAD")})
    private void onRenderPre(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        ScreenPoint screenPointA = ScreenScale.a((int) GuiInput.e(), (int) GuiInput.f());
        EventBusService.EVENT_BUS.post(new HudRenderPreEvent(DrawContextVar.getMatrices(), screenPointA.a(), screenPointA.b(), RenderTickCounterVar.getTickDelta(true)));
    }

    @Inject(method = {"render"}, at = {@At("TAIL")})
    private void onRender(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        ScreenPoint screenPointA = ScreenScale.a((int) GuiInput.e(), (int) GuiInput.f());
        ScreenScale.a(2.0d);
        EventBusService.EVENT_BUS.post(new HudRenderPostEvent(DrawContextVar.getMatrices(), screenPointA.a(), screenPointA.b(), RenderTickCounterVar.getTickDelta(true)));
        ScreenScale.a();
        HudElementManager.a().a(DrawContextVar.getMatrices());
        HudElementManager.a().b(DrawContextVar.getMatrices());
    }

    @Inject(method = {"renderPlayerList"}, at = {@At("HEAD")}, cancellable = true)
    private void onRenderPlayerList(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        if (ModuleRegistry.ANIMATIONS == null || !ModuleRegistry.ANIMATIONS.k() || !ModuleRegistry.ANIMATIONS.a.a()) {
            this.prevTabShown = false;
            return;
        }
        callbackInfo.cancel();
        if (this.client.world == null || this.client.player == null) {
            return;
        }
        Scoreboard ScoreboardVarGetScoreboard = this.client.world.getScoreboard();
        ScoreboardObjective ScoreboardObjectiveVarGetObjectiveForSlot = ScoreboardVarGetScoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.LIST);
        boolean z = this.client.options.playerListKey.isPressed() && !(this.client.isInSingleplayer() && this.client.player.networkHandler.getListedPlayerListEntries().size() <= 1 && ScoreboardObjectiveVarGetObjectiveForSlot == null);
        PlayerListScaleAnimation playerListScaleAnimationN = Animations.n();
        long jFloatValue = (long) ModuleRegistry.ANIMATIONS.b.k().floatValue();
        if (z && !this.prevTabShown) {
            playerListScaleAnimationN.a(jFloatValue);
        } else if (!z && this.prevTabShown) {
            playerListScaleAnimationN.b(jFloatValue);
        }
        this.prevTabShown = z;
        playerListScaleAnimationN.a();
        if (!(z || playerListScaleAnimationN.d())) {
            this.playerListHud.setVisible(false);
            return;
        }
        this.playerListHud.setVisible(true);
        float fB = playerListScaleAnimationN.b();
        if (fB < 0.001f) {
            this.playerListHud.setVisible(false);
            return;
        }
        MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
        MatrixStackVarGetMatrices.push();
        float fGetScaledWindowWidth = DrawContextVar.getScaledWindowWidth() / 2.0f;
        MatrixStackVarGetMatrices.translate(fGetScaledWindowWidth, 0.0f, 0.0f);
        MatrixStackVarGetMatrices.scale(fB, fB, 1.0f);
        MatrixStackVarGetMatrices.translate(-fGetScaledWindowWidth, 0.0f, 0.0f);
        this.playerListHud.render(DrawContextVar, DrawContextVar.getScaledWindowWidth(), ScoreboardVarGetScoreboard, ScoreboardObjectiveVarGetObjectiveForSlot);
        MatrixStackVarGetMatrices.pop();
    }

    @Inject(method = {"renderHotbar"}, at = {@At("TAIL")})
    private void renderHotbarItemBinds(DrawContext DrawContextVar, RenderTickCounter RenderTickCounterVar, CallbackInfo callbackInfo) {
        int iCeil;
        int i;
        MinecraftClient MinecraftClientVarGetInstance = MinecraftClient.getInstance();
        ClientPlayerEntity ClientPlayerEntityVar = MinecraftClientVarGetInstance.player;
        if (ClientPlayerEntityVar != null) {
            FastSwap fastSwap = ModuleRegistry.FAST_SWAP;
            boolean z = fastSwap != null && fastSwap.E().k().booleanValue() && fastSwap.k();
            Cooldowns cooldowns = ModuleRegistry.COOLDOWNS;
            boolean z2 = cooldowns != null && cooldowns.k() && cooldowns.n();
            TextRenderer TextRendererVar = MinecraftClientVarGetInstance.textRenderer;
            int iGetScaledWindowWidth = DrawContextVar.getScaledWindowWidth();
            int iGetScaledWindowHeight = DrawContextVar.getScaledWindowHeight();
            for (int i2 = 0; i2 < 9; i2++) {
                ItemStack ItemStackVarGetStack = ClientPlayerEntityVar.getInventory().getStack(i2);
                if (!ItemStackVarGetStack.isEmpty()) {
                    int key = getKey(ItemStackVarGetStack, fastSwap);
                    boolean z3 = z2 && hasAnyCooldown(ItemStackVarGetStack, cooldowns);
                    boolean z4 = (key == -1 || key == 0) ? false : true;
                    if (z3 || z4) {
                        int i3 = ((iGetScaledWindowWidth / 2) - 91) + (i2 * 20) + 2;
                        int i4 = (iGetScaledWindowHeight - 16) - 3;
                        if (z4 && !z3 && z) {
                            String strA = KeyNameFormatter.a(key);
                            float fMin = Math.min(1.0f, 14.0f / TextRendererVar.getWidth(strA)) * fastSwap.F().k().floatValue();
                            MatrixStack MatrixStackVarGetMatrices = DrawContextVar.getMatrices();
                            MatrixStackVarGetMatrices.push();
                            MatrixStackVarGetMatrices.translate(0.0f, 0.0f, 400.0f);
                            MatrixStackVarGetMatrices.scale(fMin, fMin, 1.0f);
                            DrawContextVar.drawText(TextRendererVar, strA, (int) ((i3 + 2.0f) / fMin), (int) ((i4 + 1.0f) / fMin), 16777215, true);
                            MatrixStackVarGetMatrices.pop();
                        }
                        if (z3) {
                            CooldownInfo.ItemCooldownSnapshot itemCooldownSnapshotA = CooldownInfo.a(ClientPlayerEntityVar.getItemCooldownManager(), ItemStackVarGetStack.getItem());
                            if (Cooldowns.a(ItemStackVarGetStack) && cooldowns.b(ItemStackVarGetStack.getItem())) {
                                float fA = cooldowns.a(ItemStackVarGetStack.getItem());
                                iCeil = (int) Math.ceil(((double) fA) * 18.5d);
                                i = fA <= 0.33f ? 65280 : fA <= 0.66f ? 16776960 : 16711680;
                            } else {
                                iCeil = (int) Math.ceil(itemCooldownSnapshotA.b());
                                float fC = itemCooldownSnapshotA.c();
                                i = fC <= 0.33f ? 65280 : fC <= 0.66f ? 16776960 : 16711680;
                            }
                            String strValueOf = String.valueOf(iCeil);
                            float fMin2 = Math.min(1.0f, 14.0f / TextRendererVar.getWidth(strValueOf)) * cooldowns.q().k().floatValue();
                            MatrixStack MatrixStackVarMethod_514482 = DrawContextVar.getMatrices();
                            MatrixStackVarMethod_514482.push();
                            MatrixStackVarMethod_514482.translate(0.0f, 0.0f, 400.0f);
                            MatrixStackVarMethod_514482.scale(fMin2, fMin2, 1.0f);
                            DrawContextVar.drawText(TextRendererVar, strValueOf, (int) ((i3 + 2.0f) / fMin2), (int) ((i4 + 1.0f) / fMin2), i, true);
                            MatrixStackVarMethod_514482.pop();
                        }
                    }
                }
            }
        }
    }

    @Unique
    private boolean hasAnyCooldown(ItemStack ItemStackVar, Cooldowns cooldowns) {
        return CooldownInfo.a(MinecraftClient.getInstance().player.getItemCooldownManager(), ItemStackVar.getItem()).a() || (Cooldowns.a(ItemStackVar) && cooldowns.b(ItemStackVar.getItem()));
    }

    @Unique
    private int getKey(ItemStack ItemStackVar, FastSwap fastSwap) {
        if (fastSwap == null) {
            return -1;
        }
        Item ItemVarGetItem = ItemStackVar.getItem();
        if (ItemVarGetItem == Items.NETHERITE_SCRAP) {
            return getKeyOrDefault(fastSwap.q().a());
        }
        if (ItemVarGetItem == Items.DRIED_KELP) {
            return getKeyOrDefault(fastSwap.r().a());
        }
        if (ItemVarGetItem == Items.ENDER_EYE) {
            int iA = fastSwap.s().a();
            return iA > 0 ? iA : getKeyOrDefault(fastSwap.y().a());
        }
        if (ItemVarGetItem == Items.SUGAR) {
            return getKeyOrDefault(fastSwap.t().a());
        }
        if (ItemVarGetItem == Items.CHORUS_FRUIT) {
            return getKeyOrDefault(fastSwap.n().a());
        }
        if (ItemVarGetItem == Items.ENDER_PEARL) {
            return getKeyOrDefault(fastSwap.o().a());
        }
        if (ItemVarGetItem == Items.POTION && fastSwap.a(ItemStackVar)) {
            return getKeyOrDefault(fastSwap.p().a());
        }
        if (ItemVarGetItem == Items.NETHER_STAR) {
            return getKeyOrDefault(fastSwap.u().a());
        }
        if (ItemVarGetItem == Items.SLIME_BALL) {
            return getKeyOrDefault(fastSwap.v().a());
        }
        if (ItemVarGetItem == Items.TURTLE_SCUTE) {
            return getKeyOrDefault(fastSwap.w().a());
        }
        if (ItemVarGetItem == Items.COBWEB) {
            return getKeyOrDefault(fastSwap.x().a());
        }
        if (ItemVarGetItem == Items.FIREWORK_STAR) {
            return getKeyOrDefault(fastSwap.z().a());
        }
        return -1;
    }

    @Unique
    private int getKeyOrDefault(int i) {
        if (i <= 0) {
            return -1;
        }
        return i;
    }
}
