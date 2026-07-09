package drunvisual.modules.utilities;

import com.mojang.authlib.GameProfile;
import java.util.UUID;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.sound.SoundEvents;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.client.network.OtherClientPlayerEntity;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SliderSetting;
import drunvisual.settings.StringSetting;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.MouseButtonEvent;
import drunvisual.events.WorldChangeEvent;

@ModuleInfo(a = "FakePlayer", b = "Спавнит копию игрока на клиенте для тренировки.", c = ModuleCategory.UTILITIES)
public final class FakePlayer extends ClientModule {
    public final StringSetting name = new StringSetting("Name", "FakePlayer");
    public final BooleanSetting lookAtPlayer = new BooleanSetting("Look At Player", true);
    public final BooleanSetting totem = new BooleanSetting("Totem", true);
    public final BooleanSetting copyEquip = new BooleanSetting("Copy Equipment", true);
    public final SliderSetting health = new SliderSetting("Health", 20.0f, 1.0f, 40.0f, 1.0f);
    private OtherClientPlayerEntity fakePlayer;
    private float currentHp;
    private static final byte STATUS_HURT = 2;
    private static final byte STATUS_TOTEM = 35;
    private static final int FAKE_ID = -42069;

    public FakePlayer() {
        collectSettings();
    }

    @Override
    public void onEnable() {
        spawn();
    }

    @Override
    public void onDisable() {
        remove();
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (c.player == null || c.world == null) {
            return;
        }
        if (this.fakePlayer == null) {
            spawn();
            return;
        }
        if (this.fakePlayer.hurtTime > 0) {
            this.fakePlayer.hurtTime--;
        }
        this.fakePlayer.setHealth(this.currentHp);
        if (this.lookAtPlayer.get()) {
            double dGetX = c.player.getX() - this.fakePlayer.getX();
            double dGetZ = c.player.getZ() - this.fakePlayer.getZ();
            double dGetEyeY = c.player.getEyeY() - this.fakePlayer.getEyeY();
            double dSqrt = Math.sqrt((dGetX * dGetX) + (dGetZ * dGetZ));
            float degrees = ((float) Math.toDegrees(Math.atan2(dGetZ, dGetX))) - 90.0f;
            float f = (float) (-Math.toDegrees(Math.atan2(dGetEyeY, dSqrt)));
            this.fakePlayer.setYaw(degrees);
            this.fakePlayer.setPitch(f);
            this.fakePlayer.headYaw = degrees;
            this.fakePlayer.bodyYaw = degrees;
        }
    }

    @EventHandler
    public void onMouseButton(MouseButtonEvent mouseButtonEvent) {
        if (c.player == null || c.world == null || this.fakePlayer == null || mouseButtonEvent.button() != 0 || mouseButtonEvent.action() != 1 || c.currentScreen != null) {
            return;
        }
        if (c.crosshairTarget instanceof EntityHitResult) {
            EntityHitResult EntityHitResultVar = (EntityHitResult) c.crosshairTarget;
            if (EntityHitResultVar.getEntity() == this.fakePlayer) {
                mouseButtonEvent.cancel();
                applyHit();
            }
        }
    }

    @EventHandler
    public void onWorldChange(WorldChangeEvent worldChangeEvent) {
        remove();
        if (k()) {
            this.currentHp = this.health.get();
            spawn();
        }
    }

    private void applyHit() {
        if (c.player == null || c.world == null || this.fakePlayer == null) {
            return;
        }
        float fGetAttackCooldownProgress = c.player.getAttackCooldownProgress(0.5f);
        if (fGetAttackCooldownProgress < 0.2f) {
            return;
        }
        float fGetAttributeValue = ((float) c.player.getAttributeValue(EntityAttributes.ATTACK_DAMAGE)) * (0.2f + (fGetAttackCooldownProgress * fGetAttackCooldownProgress * 0.8f));
        if ((c.player.isOnGround() || c.player.fallDistance <= 0.0f || c.player.isSprinting() || c.player.isClimbing() || c.player.isTouchingWater() || c.player.getVehicle() != null || c.player.hasStatusEffect(StatusEffects.BLINDNESS) || fGetAttackCooldownProgress <= 0.9f) ? false : true) {
            fGetAttributeValue *= 1.5f;
            c.player.addCritParticles(this.fakePlayer);
            c.world.playSound(this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_CRIT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        } else if (fGetAttackCooldownProgress > 0.9f) {
            c.world.playSound(this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_STRONG, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        } else {
            c.world.playSound(this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(), SoundEvents.ENTITY_PLAYER_ATTACK_WEAK, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        }
        float fMethod_453252 = (float) this.fakePlayer.getAttributeValue(EntityAttributes.ARMOR);
        float fMax = Math.max(0.0f, fGetAttributeValue * (1.0f - (Math.min(20.0f, Math.max(fMethod_453252 * 0.2f, Math.min(fMethod_453252, fMethod_453252 - (fGetAttributeValue / (2.0f + (((float) this.fakePlayer.getAttributeValue(EntityAttributes.ARMOR_TOUGHNESS)) / 4.0f)))))) / 25.0f)));
        this.fakePlayer.hurtTime = 10;
        this.fakePlayer.maxHurtTime = 10;
        this.fakePlayer.handleStatus((byte) 2);
        c.world.playSound(this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(), SoundEvents.ENTITY_PLAYER_HURT, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
        c.player.resetLastAttackedTicks();
        this.currentHp = Math.max(0.0f, this.currentHp - fMax);
        this.fakePlayer.setHealth(this.currentHp);
        if (this.currentHp <= 0.0f) {
            onFatalHit();
        }
    }

    private void onFatalHit() {
        if (this.fakePlayer == null || c.world == null) {
            return;
        }
        if (!(this.totem.get() && (this.fakePlayer.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == Items.TOTEM_OF_UNDYING || this.fakePlayer.getEquippedStack(EquipmentSlot.MAINHAND).getItem() == Items.TOTEM_OF_UNDYING))) {
            c.world.playSound(this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(), SoundEvents.ENTITY_PLAYER_DEATH, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
            remove();
            this.currentHp = this.health.get();
            spawn();
            return;
        }
        this.currentHp = 1.0f;
        this.fakePlayer.setHealth(this.currentHp);
        this.fakePlayer.handleStatus((byte) 35);
        if (this.fakePlayer.getEquippedStack(EquipmentSlot.OFFHAND).getItem() == Items.TOTEM_OF_UNDYING) {
            this.fakePlayer.equipStack(EquipmentSlot.OFFHAND, ItemStack.EMPTY);
        } else {
            this.fakePlayer.equipStack(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
        }
        c.world.playSound(this.fakePlayer.getX(), this.fakePlayer.getY(), this.fakePlayer.getZ(), SoundEvents.ITEM_TOTEM_USE, SoundCategory.PLAYERS, 1.0f, 1.0f, false);
    }

    private void spawn() {
        if (c.player == null || c.world == null) {
            return;
        }
        remove();
        this.currentHp = this.health.get();
        ClientWorld ClientWorldVar = c.world;
        String str = this.name.get();
        if (str == null || str.isBlank()) {
            str = "FakePlayer";
        }
        OtherClientPlayerEntity OtherClientPlayerEntityVar = new OtherClientPlayerEntity(ClientWorldVar, new GameProfile(UUID.randomUUID(), str));
        OtherClientPlayerEntityVar.copyFrom(c.player);
        OtherClientPlayerEntityVar.setId(FAKE_ID);
        double radians = Math.toRadians(c.player.getYaw());
        OtherClientPlayerEntityVar.refreshPositionAndAngles(c.player.getX() - (Math.sin(radians) * 2.0d), c.player.getY(), c.player.getZ() + (Math.cos(radians) * 2.0d), c.player.getYaw() + 180.0f, 0.0f);
        OtherClientPlayerEntityVar.headYaw = OtherClientPlayerEntityVar.getYaw();
        OtherClientPlayerEntityVar.bodyYaw = OtherClientPlayerEntityVar.getYaw();
        OtherClientPlayerEntityVar.setHealth(this.currentHp);
        if (this.copyEquip.get()) {
            for (EquipmentSlot EquipmentSlotVar : EquipmentSlot.values()) {
                OtherClientPlayerEntityVar.equipStack(EquipmentSlotVar, c.player.getEquippedStack(EquipmentSlotVar).copy());
            }
        }
        if (this.totem.get()) {
            OtherClientPlayerEntityVar.equipStack(EquipmentSlot.OFFHAND, new ItemStack(Items.TOTEM_OF_UNDYING));
        }
        ClientWorldVar.addEntity(OtherClientPlayerEntityVar);
        this.fakePlayer = OtherClientPlayerEntityVar;
    }

    private void remove() {
        if (this.fakePlayer == null) {
            return;
        }
        if (c.world != null) {
            c.world.removeEntity(this.fakePlayer.getId(), Entity.RemovalReason.DISCARDED);
        }
        this.fakePlayer = null;
    }
}
