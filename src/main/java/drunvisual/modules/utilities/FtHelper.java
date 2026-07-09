package drunvisual.modules.utilities;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.RaycastContext;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.module.ModuleRegistry;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.ColorSetting;
import drunvisual.settings.SettingGroup;
import drunvisual.settings.SliderSetting;
import drunvisual.core.Bool;
import drunvisual.entity.EntityFinder;
import drunvisual.entity.EntityTargetType;
import drunvisual.entity.EntityUtils;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.SoundPlayEvent;
import drunvisual.events.WorldRenderEvent;
import drunvisual.gui.friends.FriendLookup;
import drunvisual.modules.hud.ClientColor;
import drunvisual.render.world.WorldRenderUtils;

@ModuleInfo(a = "FT Helper", b = "Предпросмотр зоны действия донат-предметов", c = ModuleCategory.UTILITIES)
public class FtHelper extends ClientModule {
    private final BooleanSetting a = new BooleanSetting("Трапка", true);
    private final BooleanSetting b = new BooleanSetting("Пласт", true);
    private final BooleanSetting e = new BooleanSetting("Дезориентация", true);
    private final BooleanSetting f = new BooleanSetting("Явная пыль", true);
    private final BooleanSetting g = new BooleanSetting("Снежок заморозки", true);
    private final SettingGroup h = new SettingGroup("Настройки трапки");
    private final BooleanSetting i = new BooleanSetting("Таймер действия", true);
    private final BooleanSetting j = new BooleanSetting("Зона драконьей трапки", false);
    private final SettingGroup k = new SettingGroup("Настройки снежка");
    private final SliderSetting l = new SliderSetting("Жирность линии", 2.5f, 0.5f, 10.0f);
    private final SettingGroup m = new SettingGroup("Цвет");
    private final BooleanSetting n = new BooleanSetting("Цвет клиента", true);
    private final ColorSetting o = new ColorSetting("Кастомный цвет", Color.WHITE).a(() -> {
        return Boolean.valueOf(Bool.from(!this.n.k().booleanValue() ? 1 : 0));
    });
    private boolean p = false;
    private long q = 0;
    private int r = 0;
    private long s = 0;
    private boolean t = false;
    private boolean u = false;
    private Vec3d v = null;
    private final List<Vec3d> w = new CopyOnWriteArrayList();

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        if (c.world == null || c.player == null) {
            return;
        }
        MatrixStack MatrixStackVarA = worldRenderEvent.a();
        float fB = worldRenderEvent.b();
        Item ItemVarGetItem = c.player.getMainHandStack().getItem();
        Item ItemVarMethod_79092 = c.player.getOffHandStack().getItem();
        if (this.a.k().booleanValue() && (ItemVarGetItem == Items.NETHERITE_SCRAP || ItemVarMethod_79092 == Items.NETHERITE_SCRAP)) {
            a(MatrixStackVarA);
        }
        if (this.b.k().booleanValue() && (ItemVarGetItem == Items.DRIED_KELP || ItemVarMethod_79092 == Items.DRIED_KELP)) {
            b(MatrixStackVarA);
        }
        if (this.e.k().booleanValue() && (ItemVarGetItem == Items.ENDER_EYE || ItemVarMethod_79092 == Items.ENDER_EYE)) {
            b(MatrixStackVarA, fB);
        }
        if (this.f.k().booleanValue() && (ItemVarGetItem == Items.SUGAR || ItemVarMethod_79092 == Items.SUGAR)) {
            c(MatrixStackVarA, fB);
        }
        if (this.g.k().booleanValue()) {
            if (ItemVarGetItem == Items.SNOWBALL || ItemVarMethod_79092 == Items.SNOWBALL) {
                a(MatrixStackVarA, fB);
            }
        }
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.world == null || c.player == null) {
            return;
        }
        if (this.i.k().booleanValue()) {
            int iN = n();
            boolean zIsCoolingDown = c.player.getItemCooldownManager().isCoolingDown(Items.NETHERITE_SCRAP.getDefaultStack());
            if (zIsCoolingDown && !this.u) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                int i = jCurrentTimeMillis - this.s < 500 ? 1 : 0;
                this.p = true;
                this.q = jCurrentTimeMillis;
                this.t = Bool.from(i);
            }
            this.u = zIsCoolingDown;
            this.r = iN;
        }
        if (this.p && this.i.k().booleanValue()) {
            o();
        }
    }

    @EventHandler
    public void a(SoundPlayEvent soundPlayEvent) {
        if (soundPlayEvent.d().getId().toString().equals("minecraft:entity.ender_dragon.growl")) {
            this.s = System.currentTimeMillis();
        }
    }

    private void a(MatrixStack MatrixStackVar, float f) {
    }

    private void a(MatrixStack MatrixStackVar, boolean z) {
    }

    private Vec3d a(float f) {
        Vec3d Vec3dVarGetCameraPosVec = c.player.getCameraPosVec(f);
        Vec3d Vec3dVarB = b(f);
        Vec3d Vec3dVarAdd = Vec3dVarGetCameraPosVec;
        this.w.add(Vec3dVarAdd);
        for (int i = 0; i < 200; i++) {
            Vec3d Vec3dVar = Vec3dVarAdd;
            Vec3dVarAdd = Vec3dVarAdd.add(Vec3dVarB);
            BlockHitResult BlockHitResultVarRaycast = c.world.raycast(new RaycastContext(Vec3dVar, Vec3dVarAdd, RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.NONE, c.player));
            if (BlockHitResultVarRaycast.getType() != HitResult.Type.MISS) {
                this.w.add(BlockHitResultVarRaycast.getPos());
                return BlockHitResultVarRaycast.getPos();
            }
            if (Vec3dVarAdd.y < c.world.getBottomY()) {
                this.w.add(Vec3dVarAdd);
                return Vec3dVarAdd;
            }
            this.w.add(Vec3dVarAdd);
            Vec3dVarB = Vec3dVarB.multiply(0.99d).subtract(0.0d, 0.03d, 0.0d);
        }
        return null;
    }

    private Vec3d b(float f) {
        return a(c.player.getYaw(), c.player.getPitch()).multiply(1.5d).add(new Vec3d(0.0d, c.player.getVelocity().y * 0.5d, 0.0d));
    }

    private Vec3d a(float f, float f2) {
        float fCos = MathHelper.cos(f2 * 0.017453292f);
        float fSin = MathHelper.sin(f2 * 0.017453292f);
        float fMethod_153622 = MathHelper.cos(f * 0.017453292f);
        return new Vec3d((-MathHelper.sin(f * 0.017453292f)) * fCos, -fSin, fMethod_153622 * fCos).normalize();
    }

    private int n() {
        int i = 0;
        for (int i2 = 0; i2 < c.player.getInventory().size(); i2++) {
            ItemStack ItemStackVarGetStack = c.player.getInventory().getStack(i2);
            if (ItemStackVarGetStack.getItem() == Items.NETHERITE_SCRAP) {
                int iGetCount = ItemStackVarGetStack.getCount();
                int i3 = i;
                i = (2 * (i3 | iGetCount)) - (i3 ^ iGetCount);
            }
        }
        return i;
    }

    private void o() {
        int i = this.t ? 30000 : 15000;
        if (System.currentTimeMillis() - this.q >= i) {
            this.p = false;
            this.t = false;
        } else {
            c.inGameHud.setOverlayMessage(Text.of(String.format("%s закончится через §b%.1f§f сек.", this.t ? "Драконья трапка" : "Трапка", Double.valueOf((((long) i) - (System.currentTimeMillis() - this.q)) / 1000.0d))), false);
        }
    }

    private void a(MatrixStack MatrixStackVar) {
        Box BoxVar;
        Box BoxVar2;
        BlockPos BlockPosVarGetBlockPos = c.player.getBlockPos();
        if (this.j.k().booleanValue()) {
            double dGetX = (BlockPosVarGetBlockPos.getX() - 4) + 1;
            double dGetY = BlockPosVarGetBlockPos.getY();
            double dGetZ = (BlockPosVarGetBlockPos.getZ() - 4) + 1;
            double dMethod_102632 = (BlockPosVarGetBlockPos.getX() - (-5)) - 1;
            int iGetY = BlockPosVarGetBlockPos.getY();
            double d = (iGetY & (-7)) + (6 & (iGetY ^ (-1))) + (2 * (iGetY & 6));
            int iGetZ = BlockPosVarGetBlockPos.getZ();
            BoxVar = new Box(dGetX, dGetY, dGetZ, dMethod_102632, d, (iGetZ & (-5)) + (4 & (iGetZ ^ (-1))) + (2 * (iGetZ & 4)));
            int iGetX = BlockPosVarGetBlockPos.getX();
            double d2 = ((double) ((iGetX ^ 3) - (2 * ((iGetX ^ (-1)) & 3)))) + 0.01d;
            double dMethod_102642 = ((double) BlockPosVarGetBlockPos.getY()) + 0.01d;
            int iMethod_102602 = BlockPosVarGetBlockPos.getZ();
            double d3 = ((double) ((2 * (iMethod_102602 & (-4))) - (iMethod_102602 ^ 3))) + 0.01d;
            int iMethod_102632 = BlockPosVarGetBlockPos.getX();
            double d4 = ((double) ((iMethod_102632 ^ 4) + (2 * (iMethod_102632 & 4)))) - 0.01d;
            int iMethod_102642 = BlockPosVarGetBlockPos.getY();
            double d5 = ((double) ((2 * (iMethod_102642 | 6)) - (iMethod_102642 ^ 6))) - 0.01d;
            int iMethod_102603 = BlockPosVarGetBlockPos.getZ();
            BoxVar2 = new Box(d2, dMethod_102642, d3, d4, d5, ((double) ((2 * (iMethod_102603 | 4)) - (iMethod_102603 ^ 4))) - 0.01d);
        } else {
            int iMethod_102633 = BlockPosVarGetBlockPos.getX();
            double d6 = (iMethod_102633 ^ 2) - (2 * ((iMethod_102633 ^ (-1)) & 2));
            double dMethod_102643 = BlockPosVarGetBlockPos.getY();
            double dMethod_102602 = BlockPosVarGetBlockPos.getZ() - 2;
            int iMethod_102634 = BlockPosVarGetBlockPos.getX();
            double d7 = (2 * (iMethod_102634 | 3)) - (iMethod_102634 ^ 3);
            int iMethod_102643 = BlockPosVarGetBlockPos.getY();
            double d8 = (2 * (iMethod_102643 | 4)) - (iMethod_102643 ^ 4);
            int iMethod_102604 = BlockPosVarGetBlockPos.getZ();
            BoxVar = new Box(d6, dMethod_102643, dMethod_102602, d7, d8, (iMethod_102604 | 3) + (iMethod_102604 & 3));
            int iMethod_102635 = BlockPosVarGetBlockPos.getX();
            double d9 = ((double) ((iMethod_102635 & (-3)) - ((iMethod_102635 ^ (-1)) & 2))) + 0.01d;
            double dMethod_102644 = ((double) BlockPosVarGetBlockPos.getY()) + 0.01d;
            int iMethod_102605 = BlockPosVarGetBlockPos.getZ();
            double d10 = ((double) ((iMethod_102605 & (-3)) - ((iMethod_102605 ^ (-1)) & 2))) + 0.01d;
            int iMethod_102636 = BlockPosVarGetBlockPos.getX();
            double d11 = ((double) (((iMethod_102636 & (-4)) + (3 & (iMethod_102636 ^ (-1)))) + (2 * (iMethod_102636 & 3)))) - 0.01d;
            int iMethod_102644 = BlockPosVarGetBlockPos.getY();
            BoxVar2 = new Box(d9, dMethod_102644, d10, d11, ((double) ((iMethod_102644 ^ 4) + (2 * (iMethod_102644 & 4)))) - 0.01d, ((double) ((BlockPosVarGetBlockPos.getZ() - (-4)) - 1)) - 0.01d);
        }
        int rgb = p().getRGB();
        WorldRenderUtils.b(MatrixStackVar, BoxVar2, rgb);
    }

    private void b(MatrixStack MatrixStackVar) {
        int c2 = 0;
        BlockPos BlockPosVarAdd;
        Box BoxVar;
        Box BoxVar2;
        int i;
        int i2;
        Box BoxVar3;
        Box BoxVar4;
        BlockPos BlockPosVarGetBlockPos = c.player.getBlockPos();
        float fGetYaw = c.player.getYaw();
        float fGetPitch = c.player.getPitch();
        int rgb = p().getRGB();
        boolean zBooleanValue = this.j.k().booleanValue();
        if (Math.abs(fGetPitch) > 45.0f) {
            if (fGetPitch < -45.0f) {
                if (zBooleanValue) {
                    int iGetX = BlockPosVarGetBlockPos.getX();
                    double d = (iGetX & (-4)) - ((iGetX ^ (-1)) & 3);
                    int iGetY = BlockPosVarGetBlockPos.getY();
                    double d2 = (iGetY | 3) + (iGetY & 3);
                    int iGetZ = BlockPosVarGetBlockPos.getZ();
                    double d3 = (iGetZ ^ 3) - (2 * ((iGetZ ^ (-1)) & 3));
                    int iMethod_102632 = BlockPosVarGetBlockPos.getX();
                    double d4 = (iMethod_102632 ^ 4) + (2 * (iMethod_102632 & 4));
                    int iMethod_102642 = BlockPosVarGetBlockPos.getY();
                    BoxVar3 = new Box(d, d2, d3, d4, (iMethod_102642 & (-8)) + (7 & (iMethod_102642 ^ (-1))) + (2 * (iMethod_102642 & 7)), (BlockPosVarGetBlockPos.getZ() - (-5)) - 1);
                    int iMethod_102633 = BlockPosVarGetBlockPos.getX();
                    double d5 = ((double) ((2 * (iMethod_102633 & (-4))) - (iMethod_102633 ^ 3))) + 0.01d;
                    int iMethod_102643 = BlockPosVarGetBlockPos.getY();
                    double d6 = ((double) ((iMethod_102643 & (-4)) + (3 & (iMethod_102643 ^ (-1))) + (2 * (iMethod_102643 & 3)))) + 0.01d;
                    int iMethod_102602 = BlockPosVarGetBlockPos.getZ();
                    double d7 = ((double) ((2 * (iMethod_102602 & (-4))) - (iMethod_102602 ^ 3))) + 0.01d;
                    int iMethod_102634 = BlockPosVarGetBlockPos.getX();
                    BoxVar4 = new Box(d5, d6, d7, ((double) ((iMethod_102634 ^ 4) + (2 * (iMethod_102634 & 4)))) - 0.01d, ((double) ((BlockPosVarGetBlockPos.getY() - (-8)) - 1)) - 0.01d, ((double) ((BlockPosVarGetBlockPos.getZ() - (-5)) - 1)) - 0.01d);
                } else {
                    double dGetX = (BlockPosVarGetBlockPos.getX() - 3) + 1;
                    int iMethod_102644 = BlockPosVarGetBlockPos.getY();
                    double d8 = (2 * (iMethod_102644 | 3)) - (iMethod_102644 ^ 3);
                    int iMethod_102603 = BlockPosVarGetBlockPos.getZ();
                    double d9 = (2 * (iMethod_102603 & (-3))) - (iMethod_102603 ^ 2);
                    int iMethod_102635 = BlockPosVarGetBlockPos.getX();
                    double d10 = (iMethod_102635 | 3) + (iMethod_102635 & 3);
                    int iMethod_102645 = BlockPosVarGetBlockPos.getY();
                    BoxVar3 = new Box(dGetX, d8, d9, d10, (2 * (iMethod_102645 | 5)) - (iMethod_102645 ^ 5), (BlockPosVarGetBlockPos.getZ() - (-4)) - 1);
                    double dMethod_102632 = ((double) ((BlockPosVarGetBlockPos.getX() - 3) + 1)) + 0.01d;
                    double dGetY = ((double) ((BlockPosVarGetBlockPos.getY() - (-4)) - 1)) + 0.01d;
                    int iMethod_102604 = BlockPosVarGetBlockPos.getZ();
                    double d11 = ((double) ((2 * (iMethod_102604 & (-3))) - (iMethod_102604 ^ 2))) + 0.01d;
                    int iMethod_102636 = BlockPosVarGetBlockPos.getX();
                    double d12 = ((double) ((2 * (iMethod_102636 | 3)) - (iMethod_102636 ^ 3))) - 0.01d;
                    int iMethod_102646 = BlockPosVarGetBlockPos.getY();
                    double d13 = ((double) ((iMethod_102646 | 5) + (iMethod_102646 & 5))) - 0.01d;
                    int iMethod_102605 = BlockPosVarGetBlockPos.getZ();
                    BoxVar4 = new Box(dMethod_102632, dGetY, d11, d12, d13, ((double) ((2 * (iMethod_102605 | 3)) - (iMethod_102605 ^ 3))) - 0.01d);
                }
            } else if (zBooleanValue) {
                int iMethod_102637 = BlockPosVarGetBlockPos.getX();
                double d14 = (2 * (iMethod_102637 & (-4))) - (iMethod_102637 ^ 3);
                int iMethod_102647 = BlockPosVarGetBlockPos.getY();
                double d15 = (2 * (iMethod_102647 & (-4))) - (iMethod_102647 ^ 3);
                int iMethod_102606 = BlockPosVarGetBlockPos.getZ();
                double d16 = (iMethod_102606 & (-4)) - ((iMethod_102606 ^ (-1)) & 3);
                int iMethod_102638 = BlockPosVarGetBlockPos.getX();
                double d17 = (2 * (iMethod_102638 | 4)) - (iMethod_102638 ^ 4);
                int iMethod_102648 = BlockPosVarGetBlockPos.getY();
                double d18 = (iMethod_102648 ^ 1) - (2 * ((iMethod_102648 ^ (-1)) & 1));
                int iMethod_102607 = BlockPosVarGetBlockPos.getZ();
                BoxVar3 = new Box(d14, d15, d16, d17, d18, (2 * (iMethod_102607 | 4)) - (iMethod_102607 ^ 4));
                int iMethod_102639 = BlockPosVarGetBlockPos.getX();
                double d19 = ((double) ((iMethod_102639 ^ 3) - (2 * ((iMethod_102639 ^ (-1)) & 3)))) + 0.01d;
                double dMethod_102642 = ((double) (BlockPosVarGetBlockPos.getY() - 3)) + 0.01d;
                int iMethod_102608 = BlockPosVarGetBlockPos.getZ();
                double d20 = ((double) ((iMethod_102608 & (-4)) - ((iMethod_102608 ^ (-1)) & 3))) + 0.01d;
                int iMethod_1026310 = BlockPosVarGetBlockPos.getX();
                double d21 = ((double) ((iMethod_1026310 ^ 4) + (2 * (iMethod_1026310 & 4)))) - 0.01d;
                double dMethod_102643 = ((double) ((BlockPosVarGetBlockPos.getY() - 2) + 1)) - 0.01d;
                int iMethod_102609 = BlockPosVarGetBlockPos.getZ();
                BoxVar4 = new Box(d19, dMethod_102642, d20, d21, dMethod_102643, ((double) ((iMethod_102609 ^ 4) + (2 * (iMethod_102609 & 4)))) - 0.01d);
            } else {
                double dMethod_102633 = BlockPosVarGetBlockPos.getX() - 2;
                int iMethod_102649 = BlockPosVarGetBlockPos.getY();
                double d22 = (iMethod_102649 ^ 3) - (2 * ((iMethod_102649 ^ (-1)) & 3));
                double dGetZ = (BlockPosVarGetBlockPos.getZ() - 3) + 1;
                int iMethod_1026311 = BlockPosVarGetBlockPos.getX();
                double d23 = (2 * (iMethod_1026311 | 3)) - (iMethod_1026311 ^ 3);
                int iMethod_1026410 = BlockPosVarGetBlockPos.getY();
                double d24 = (iMethod_1026410 ^ 1) - (2 * ((iMethod_1026410 ^ (-1)) & 1));
                int iMethod_1026010 = BlockPosVarGetBlockPos.getZ();
                BoxVar3 = new Box(dMethod_102633, d22, dGetZ, d23, d24, (iMethod_1026010 & (-4)) + (3 & (iMethod_1026010 ^ (-1))) + (2 * (iMethod_1026010 & 3)));
                int iMethod_1026312 = BlockPosVarGetBlockPos.getX();
                double d25 = ((double) ((2 * (iMethod_1026312 & (-3))) - (iMethod_1026312 ^ 2))) + 0.01d;
                double dMethod_102644 = ((double) ((BlockPosVarGetBlockPos.getY() - 4) + 1)) + 0.01d;
                double dMethod_102602 = ((double) ((BlockPosVarGetBlockPos.getZ() - 3) + 1)) + 0.01d;
                int iMethod_1026313 = BlockPosVarGetBlockPos.getX();
                double d26 = ((double) ((iMethod_1026313 | 3) + (iMethod_1026313 & 3))) - 0.01d;
                int iMethod_1026411 = BlockPosVarGetBlockPos.getY();
                double d27 = ((double) ((2 * (iMethod_1026411 & (-2))) - (iMethod_1026411 ^ 1))) - 0.01d;
                int iMethod_1026011 = BlockPosVarGetBlockPos.getZ();
                BoxVar4 = new Box(d25, dMethod_102644, dMethod_102602, d26, d27, ((double) ((2 * (iMethod_1026011 | 3)) - (iMethod_1026011 ^ 3))) - 0.01d);
            }
            WorldRenderUtils.b(MatrixStackVar, BoxVar4, rgb);
            return;
        }
        float f = ((fGetYaw % 360.0f) + 360.0f) % 360.0f;
        if (Math.abs(f - 45.0f) < ((float) 22) || Math.abs(f - 135.0f) < ((float) 22) || Math.abs(f - 225.0f) < ((float) 22) || Math.abs(f - 315.0f) < ((float) 22)) {
            float fAbs = Math.abs(f - 45.0f);
            float fAbs2 = Math.abs(f - 135.0f);
            float fAbs3 = Math.abs(f - 225.0f);
            float fMin = Math.min(Math.min(fAbs, fAbs2), Math.min(fAbs3, Math.abs(f - 315.0f)));
            int i3 = fMin == fAbs ? 45 : fMin == fAbs2 ? 135 : fMin == fAbs3 ? 225 : 315;
            Math.toRadians(i3);
            if (i3 == 45) {
                i = -3;
                i2 = 2;
            } else if (i3 == 135) {
                i = -3;
                i2 = -2;
            } else if (i3 == 225) {
                i = 2;
                i2 = -2;
            } else {
                i = 2;
                i2 = 2;
            }
            BlockPos BlockPosVarMethod_100692 = BlockPosVarGetBlockPos.add(i, -1, i2);
            ArrayList arrayList = new ArrayList();
            int i4 = zBooleanValue ? -3 : -2;
            int i5 = zBooleanValue ? 3 : 2;
            int i6 = zBooleanValue ? 7 : 5;
            if (i3 == 45 || i3 == 225) {
                for (int i7 = i4; i7 <= i5; i7++) {
                    for (int i8 = 0; i8 < i6; i8++) {
                        arrayList.add(BlockPosVarMethod_100692.add(i7, i8, i7));
                        arrayList.add(BlockPosVarMethod_100692.add((i7 - (-2)) - 1, i8, i7));
                    }
                }
            } else {
                for (int i9 = i4; i9 <= i5; i9++) {
                    for (int i10 = 0; i10 < i6; i10++) {
                        arrayList.add(BlockPosVarMethod_100692.add(i9, i10, -i9));
                        int i11 = i9;
                        arrayList.add(BlockPosVarMethod_100692.add((i11 ^ 1) + (2 * (i11 & 1)), i10, -i9));
                    }
                }
            }
            WorldRenderUtils.b(MatrixStackVar, arrayList, rgb);
            return;
        }
        if (f >= 315.0f || f < 45.0f) {
            BlockPosVarAdd = BlockPosVarGetBlockPos.add(0, -1, 3);
        } else if (f >= 45.0f && f < 135.0f) {
            c2 = 90;
            BlockPosVarAdd = BlockPosVarGetBlockPos.add((3 - 1) ^ (-1), -1, 0);
        } else if (f < 135.0f || f >= 225.0f) {
            BlockPosVarAdd = BlockPosVarGetBlockPos.add(3, -1, 0);
        } else {
            BlockPosVarAdd = BlockPosVarGetBlockPos.add(0, -1, (3 - 1) ^ (-1));
        }
        if (c2 == 0 || c2 == 180) {
            if (zBooleanValue) {
                double dMethod_102645 = BlockPosVarAdd.getY();
                int iMethod_1026012 = BlockPosVarAdd.getZ();
                double d28 = (iMethod_1026012 ^ 1) - (2 * ((iMethod_1026012 ^ (-1)) & 1));
                int iMethod_1026314 = BlockPosVarAdd.getX();
                double d29 = (2 * (iMethod_1026314 | 4)) - (iMethod_1026314 ^ 4);
                int iMethod_1026412 = BlockPosVarAdd.getY();
                double d30 = (iMethod_1026412 & (-8)) + (7 & (iMethod_1026412 ^ (-1))) + (2 * (iMethod_1026412 & 7));
                int iMethod_1026013 = BlockPosVarAdd.getZ();
                BoxVar = new Box((BlockPosVarAdd.getX() - 4) + 1, dMethod_102645, d28, d29, d30, (iMethod_1026013 & (-2)) + (1 & (iMethod_1026013 ^ (-1))) + (2 * (iMethod_1026013 & 1)));
                int iMethod_1026315 = BlockPosVarAdd.getX();
                double d31 = ((double) ((iMethod_1026315 & (-4)) - ((iMethod_1026315 ^ (-1)) & 3))) + 0.01d;
                double dMethod_102646 = ((double) BlockPosVarAdd.getY()) + 0.01d;
                double dMethod_102603 = ((double) (BlockPosVarAdd.getZ() - 1)) + 0.01d;
                int iMethod_1026316 = BlockPosVarAdd.getX();
                double d32 = ((double) ((iMethod_1026316 ^ 4) + (2 * (iMethod_1026316 & 4)))) - 0.01d;
                int iMethod_1026413 = BlockPosVarAdd.getY();
                double d33 = ((double) (((iMethod_1026413 & (-8)) + (7 & (iMethod_1026413 ^ (-1)))) + (2 * (iMethod_1026413 & 7)))) - 0.01d;
                int iMethod_1026014 = BlockPosVarAdd.getZ();
                BoxVar2 = new Box(d31, dMethod_102646, dMethod_102603, d32, d33, ((double) ((2 * (iMethod_1026014 | 1)) - (iMethod_1026014 ^ 1))) - 0.01d);
            } else {
                double dMethod_102634 = BlockPosVarAdd.getX() - 2;
                double dMethod_102647 = BlockPosVarAdd.getY();
                int iMethod_1026015 = BlockPosVarAdd.getZ();
                double d34 = (iMethod_1026015 & (-2)) - ((iMethod_1026015 ^ (-1)) & 1);
                int iMethod_1026317 = BlockPosVarAdd.getX();
                double d35 = (iMethod_1026317 | 3) + (iMethod_1026317 & 3);
                int iMethod_1026414 = BlockPosVarAdd.getY();
                double d36 = (iMethod_1026414 ^ 5) + (2 * (iMethod_1026414 & 5));
                int iMethod_1026016 = BlockPosVarAdd.getZ();
                BoxVar = new Box(dMethod_102634, dMethod_102647, d34, d35, d36, (iMethod_1026016 | 1) + (iMethod_1026016 & 1));
                int iMethod_1026318 = BlockPosVarAdd.getX();
                double d37 = ((double) ((iMethod_1026318 & (-3)) - ((iMethod_1026318 ^ (-1)) & 2))) + 0.01d;
                double dMethod_102648 = ((double) BlockPosVarAdd.getY()) + 0.01d;
                double dMethod_102604 = ((double) (BlockPosVarAdd.getZ() - 1)) + 0.01d;
                int iMethod_1026319 = BlockPosVarAdd.getX();
                double d38 = ((double) ((iMethod_1026319 | 3) + (iMethod_1026319 & 3))) - 0.01d;
                int iMethod_1026415 = BlockPosVarAdd.getY();
                double d39 = ((double) ((iMethod_1026415 | 5) + (iMethod_1026415 & 5))) - 0.01d;
                int iMethod_1026017 = BlockPosVarAdd.getZ();
                BoxVar2 = new Box(d37, dMethod_102648, dMethod_102604, d38, d39, ((double) ((2 * (iMethod_1026017 | 1)) - (iMethod_1026017 ^ 1))) - 0.01d);
            }
        } else if (zBooleanValue) {
            double dMethod_102635 = BlockPosVarAdd.getX() - 1;
            double dMethod_102649 = BlockPosVarAdd.getY();
            int iMethod_1026018 = BlockPosVarAdd.getZ();
            double d40 = (2 * (iMethod_1026018 & (-4))) - (iMethod_1026018 ^ 3);
            int iMethod_1026320 = BlockPosVarAdd.getX();
            double d41 = (2 * (iMethod_1026320 | 1)) - (iMethod_1026320 ^ 1);
            double dMethod_1026410 = (BlockPosVarAdd.getY() - (-8)) - 1;
            int iMethod_1026019 = BlockPosVarAdd.getZ();
            BoxVar = new Box(dMethod_102635, dMethod_102649, d40, d41, dMethod_1026410, (2 * (iMethod_1026019 | 4)) - (iMethod_1026019 ^ 4));
            int iMethod_1026321 = BlockPosVarAdd.getX();
            double d42 = ((double) ((2 * (iMethod_1026321 & (-2))) - (iMethod_1026321 ^ 1))) + 0.01d;
            double dMethod_1026411 = ((double) BlockPosVarAdd.getY()) + 0.01d;
            int iMethod_1026020 = BlockPosVarAdd.getZ();
            double d43 = ((double) ((iMethod_1026020 ^ 3) - (2 * ((iMethod_1026020 ^ (-1)) & 3)))) + 0.01d;
            int iMethod_1026322 = BlockPosVarAdd.getX();
            double d44 = ((double) (((iMethod_1026322 & (-2)) + (1 & (iMethod_1026322 ^ (-1)))) + (2 * (iMethod_1026322 & 1)))) - 0.01d;
            int iMethod_1026416 = BlockPosVarAdd.getY();
            double d45 = ((double) ((iMethod_1026416 ^ 7) + (2 * (iMethod_1026416 & 7)))) - 0.01d;
            int iMethod_1026021 = BlockPosVarAdd.getZ();
            BoxVar2 = new Box(d42, dMethod_1026411, d43, d44, d45, ((double) ((2 * (iMethod_1026021 | 4)) - (iMethod_1026021 ^ 4))) - 0.01d);
        } else {
            int iMethod_1026323 = BlockPosVarAdd.getX();
            double d46 = (2 * (iMethod_1026323 & (-2))) - (iMethod_1026323 ^ 1);
            double dMethod_1026412 = BlockPosVarAdd.getY();
            int iMethod_1026022 = BlockPosVarAdd.getZ();
            double d47 = (2 * (iMethod_1026022 & (-3))) - (iMethod_1026022 ^ 2);
            double dMethod_102636 = (BlockPosVarAdd.getX() - (-2)) - 1;
            int iMethod_1026417 = BlockPosVarAdd.getY();
            double d48 = (iMethod_1026417 & (-6)) + (5 & (iMethod_1026417 ^ (-1))) + (2 * (iMethod_1026417 & 5));
            int iMethod_1026023 = BlockPosVarAdd.getZ();
            BoxVar = new Box(d46, dMethod_1026412, d47, dMethod_102636, d48, (2 * (iMethod_1026023 | 3)) - (iMethod_1026023 ^ 3));
            double dMethod_1026413 = ((double) BlockPosVarAdd.getY()) + 0.01d;
            int iMethod_1026024 = BlockPosVarAdd.getZ();
            double d49 = ((double) ((iMethod_1026024 & (-3)) - ((iMethod_1026024 ^ (-1)) & 2))) + 0.01d;
            int iMethod_1026324 = BlockPosVarAdd.getX();
            double d50 = ((double) (((iMethod_1026324 & (-2)) + (1 & (iMethod_1026324 ^ (-1)))) + (2 * (iMethod_1026324 & 1)))) - 0.01d;
            int iMethod_1026418 = BlockPosVarAdd.getY();
            double d51 = ((double) (((iMethod_1026418 & (-6)) + (5 & (iMethod_1026418 ^ (-1)))) + (2 * (iMethod_1026418 & 5)))) - 0.01d;
            int iMethod_1026025 = BlockPosVarAdd.getZ();
            BoxVar2 = new Box(((double) ((BlockPosVarAdd.getX() - 2) + 1)) + 0.01d, dMethod_1026413, d49, d50, d51, ((double) ((2 * (iMethod_1026025 | 3)) - (iMethod_1026025 ^ 3))) - 0.01d);
        }
        WorldRenderUtils.b(MatrixStackVar, BoxVar2, rgb);
    }

    private void b(MatrixStack MatrixStackVar, float f) {
        Vec3d Vec3dVarC = c(f);
        WorldRenderUtils.a(MatrixStackVar, Vec3dVarC, 10.0f, 64, a(Vec3dVarC, 10.0d) ? -16711936 : -1, 0.1f);
    }

    private void c(MatrixStack MatrixStackVar, float f) {
        Vec3d Vec3dVarC = c(f);
        WorldRenderUtils.a(MatrixStackVar, Vec3dVarC, 10.0f, 64, a(Vec3dVarC, 10.0d) ? -16711936 : -1, 0.1f);
    }

    private Vec3d c(float f) {
        return new Vec3d(c.player.prevX + ((c.player.getX() - c.player.prevX) * ((double) f)), c.player.prevY + ((c.player.getY() - c.player.prevY) * ((double) f)), c.player.prevZ + ((c.player.getZ() - c.player.prevZ) * ((double) f)));
    }

    private boolean a(Box BoxVar) {
        Iterator<Entity> it = EntityFinder.a(EntityVar -> {
            return Bool.from(((EntityVar instanceof PlayerEntity) && BoxVar.contains(EntityVar.getPos())) ? 1 : 0);
        }, EntityTargetType.PLAYER).iterator();
        while (it.hasNext()) {
            PlayerEntity PlayerEntityVar = (PlayerEntity) it.next();
            if (PlayerEntityVar != c.player) {
                PlayerEntity PlayerEntityVar2 = PlayerEntityVar;
                if (!EntityUtils.a((LivingEntity) PlayerEntityVar2) && !FriendLookup.a(PlayerEntityVar2.getName().getString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(Vec3d Vec3dVar, double d) {
        double d2 = d * d;
        Iterator<Entity> it = EntityFinder.a(EntityVar -> {
            return Bool.from((!(EntityVar instanceof PlayerEntity) || EntityVar.squaredDistanceTo(Vec3dVar) > d2) ? 0 : 1);
        }, EntityTargetType.PLAYER).iterator();
        while (it.hasNext()) {
            PlayerEntity PlayerEntityVar = (PlayerEntity) it.next();
            if (PlayerEntityVar != c.player) {
                PlayerEntity PlayerEntityVar2 = PlayerEntityVar;
                if (!EntityUtils.a((LivingEntity) PlayerEntityVar2) && !FriendLookup.a(PlayerEntityVar2.getName().getString())) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean a(List<BlockPos> list) {
        Iterator<Entity> it = EntityFinder.a(EntityVar -> {
            return EntityVar instanceof PlayerEntity;
        }, EntityTargetType.PLAYER).iterator();
        while (it.hasNext()) {
            PlayerEntity PlayerEntityVar = (PlayerEntity) it.next();
            if (PlayerEntityVar != c.player && !FriendLookup.a(PlayerEntityVar.getName().getString())) {
                BlockPos BlockPosVarGetBlockPos = PlayerEntityVar.getBlockPos();
                Iterator<BlockPos> it2 = list.iterator();
                while (it2.hasNext()) {
                    if (BlockPosVarGetBlockPos.equals(it2.next())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private Color p() {
        ClientColor clientColor;
        return (!this.n.k().booleanValue() || (clientColor = ModuleRegistry.CLIENT_COLOR) == null) ? this.o.k() : clientColor.n();
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
