package drunvisual.modules.utilities;

import java.util.Iterator;
import java.util.List;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.client.util.math.MatrixStack;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.core.Bool;
import drunvisual.entity.EntityFinder;
import drunvisual.entity.EntityTargetType;
import drunvisual.entity.EntityUtils;
import drunvisual.events.WorldRenderEvent;
import drunvisual.gui.friends.FriendLookup;
import drunvisual.render.world.WorldRenderUtils;

@ModuleInfo(a = "FC Helper", b = "Предпросмотр зоны действия донат-предметов", c = ModuleCategory.UTILITIES)
public class FcHelper extends ClientModule {
    private final BooleanSetting e = new BooleanSetting("Шаровая молния", true);
    private final BooleanSetting f = new BooleanSetting("Ком слизи", true);
    private final BooleanSetting g = new BooleanSetting("Черепаший захват", true);
    private final BooleanSetting h = new BooleanSetting("Паутина судьбы", true);
    private final BooleanSetting i = new BooleanSetting("Стан", true);
    private final BooleanSetting j = new BooleanSetting("Магнитный шар", true);
    public static int a;
    public static boolean b;

    @EventHandler
    public void a(WorldRenderEvent worldRenderEvent) {
        if (c.world == null || c.player == null) {
            return;
        }
        MatrixStack MatrixStackVarA = worldRenderEvent.a();
        float fB = worldRenderEvent.b();
        Item ItemVarGetItem = c.player.getMainHandStack().getItem();
        Item ItemVarMethod_79092 = c.player.getOffHandStack().getItem();
        if (this.e.k().booleanValue() && (ItemVarGetItem == Items.NETHER_STAR || ItemVarMethod_79092 == Items.NETHER_STAR)) {
            a(MatrixStackVarA, fB);
        }
        if (this.f.k().booleanValue()) {
            if (ItemVarGetItem == Items.SLIME_BALL || ItemVarMethod_79092 == Items.SLIME_BALL) {
                b(MatrixStackVarA, fB);
            }
        }
        if (this.g.k().booleanValue()) {
            if (ItemVarGetItem == Items.TURTLE_SCUTE) {
            } else if (ItemVarMethod_79092 == Items.TURTLE_SCUTE) {
            }
            a(MatrixStackVarA);
        }
        if (this.h.k().booleanValue()) {
            if (ItemVarGetItem == Items.COBWEB || ItemVarMethod_79092 == Items.COBWEB) {
                b(MatrixStackVarA);
            } else {
            }
        }
        if (this.i.k().booleanValue() && (ItemVarGetItem == Items.ENDER_EYE || ItemVarMethod_79092 == Items.ENDER_EYE)) {
            c(MatrixStackVarA, fB);
        }
        if (this.j.k().booleanValue()) {
            if (ItemVarGetItem == Items.FIREWORK_STAR || ItemVarMethod_79092 == Items.FIREWORK_STAR) {
                d(MatrixStackVarA, fB);
            }
        }
    }

    private void a(MatrixStack MatrixStackVar, float f) {
        Vec3d Vec3dVarA = a(f);
        Box BoxVar = new Box(Vec3dVarA.x - 10.0d, Vec3dVarA.y - 10.0d, Vec3dVarA.z - 10.0d, Vec3dVarA.x + 10.0d, Vec3dVarA.y + 10.0d, Vec3dVarA.z + 10.0d);
        WorldRenderUtils.a(MatrixStackVar, BoxVar, !a(BoxVar) ? -1 : -16711936);
    }

    private void b(MatrixStack MatrixStackVar, float f) {
        Vec3d Vec3dVarA = a(f);
        double d = Vec3dVarA.y + 1.0d;
        WorldRenderUtils.a(MatrixStackVar, new Box(Vec3dVarA.x - 5.0d, d, Vec3dVarA.z - 5.0d, Vec3dVarA.x + 5.0d, d, Vec3dVarA.z + 5.0d), !b(new Box(Vec3dVarA.x - 5.0d, d - 5.0d, Vec3dVarA.z - 5.0d, Vec3dVarA.x + 5.0d, d + 5.0d, Vec3dVarA.z + 5.0d)) ? -1 : -16711936);
    }

    private void a(MatrixStack MatrixStackVar) {
        BlockPos BlockPosVarGetBlockPos = c.player.getBlockPos();
        double dGetX = BlockPosVarGetBlockPos.getX() - 3;
        double dGetY = BlockPosVarGetBlockPos.getY() - 1;
        int iGetZ = BlockPosVarGetBlockPos.getZ();
        double d = (2 * (iGetZ & (-4))) - (iGetZ ^ 3);
        int iGetX = BlockPosVarGetBlockPos.getX();
        double d2 = (2 * (iGetX | 4)) - (iGetX ^ 4);
        int iGetY = BlockPosVarGetBlockPos.getY();
        Box BoxVar = new Box(dGetX, dGetY, d, d2, (iGetY & (-6)) + (5 & (iGetY ^ (-1))) + (2 * (iGetY & 5)), (BlockPosVarGetBlockPos.getZ() - (-5)) - 1);
        WorldRenderUtils.a(MatrixStackVar, BoxVar, !b(BoxVar) ? -1 : -16711936);
    }

    private void b(MatrixStack MatrixStackVar) {
        double dGetX;
        double d;
        double dGetZ;
        double d2;
        BlockPos BlockPosVarGetBlockPos = c.player.getBlockPos();
        float fGetYaw = ((c.player.getYaw() % 360.0f) + 360.0f) % 360.0f;
        BlockPos BlockPosVarAdd = (fGetYaw >= 315.0f || fGetYaw < 45.0f) ? BlockPosVarGetBlockPos.add(0, 0, 3) : (fGetYaw < 45.0f || fGetYaw >= 135.0f) ? (fGetYaw < 135.0f || fGetYaw >= 225.0f) ? BlockPosVarGetBlockPos.add(3, 0, 0) : BlockPosVarGetBlockPos.add(0, 0, -3) : BlockPosVarGetBlockPos.add(-3, 0, 0);
        double dGetY = BlockPosVarAdd.getY();
        int iGetY = BlockPosVarAdd.getY();
        double d3 = (iGetY | 3) + (iGetY & 3);
        if (fGetYaw >= 315.0f || fGetYaw < 45.0f || (fGetYaw >= 135.0f && fGetYaw < 225.0f)) {
            int iGetX = BlockPosVarAdd.getX();
            dGetX = (iGetX & (-2)) - ((iGetX ^ (-1)) & 1);
            int iMethod_102632 = BlockPosVarAdd.getX();
            d = (iMethod_102632 ^ 2) + (2 * (iMethod_102632 & 2));
            dGetZ = BlockPosVarAdd.getZ();
            int iGetZ = BlockPosVarAdd.getZ();
            d2 = (iGetZ & (-2)) + (1 & (iGetZ ^ (-1))) + (2 * (iGetZ & 1));
        } else {
            dGetX = BlockPosVarAdd.getX();
            int iMethod_102633 = BlockPosVarAdd.getX();
            d = (iMethod_102633 & (-2)) + (1 & (iMethod_102633 ^ (-1))) + (2 * (iMethod_102633 & 1));
            dGetZ = BlockPosVarAdd.getZ() - 1;
            int iMethod_102602 = BlockPosVarAdd.getZ();
            d2 = (iMethod_102602 ^ 2) + (2 * (iMethod_102602 & 2));
        }
        Box BoxVar = new Box(dGetX, dGetY, dGetZ, d, d3, d2);
        WorldRenderUtils.a(MatrixStackVar, BoxVar, !b(BoxVar) ? -1 : -16711936);
    }

    private void c(MatrixStack MatrixStackVar, float f) {
        int i;
        Vec3d Vec3dVarA = a(f);
        Box BoxVar = new Box(Vec3dVarA.x - 30.0d, Vec3dVarA.y - 30.0d, Vec3dVarA.z - 30.0d, Vec3dVarA.x + 30.0d, Vec3dVarA.y + 30.0d, Vec3dVarA.z + 30.0d);
        if (b(BoxVar)) {
            i = -16711936;
        } else {
            i = -1;
        }
        WorldRenderUtils.a(MatrixStackVar, BoxVar, i);
    }

    private void d(MatrixStack MatrixStackVar, float f) {
        Vec3d Vec3dVarA = a(f);
        WorldRenderUtils.a(MatrixStackVar, new Box(Vec3dVarA.x - 7.0d, Vec3dVarA.y + 1.0d, Vec3dVarA.z - 7.0d, Vec3dVarA.x + 7.0d, Vec3dVarA.y + 1.0d, Vec3dVarA.z + 7.0d), !b(new Box(Vec3dVarA.x - 7.0d, Vec3dVarA.y - 6.0d, Vec3dVarA.z - 7.0d, Vec3dVarA.x + 7.0d, Vec3dVarA.y + 8.0d, Vec3dVarA.z + 7.0d)) ? -1 : -16711936);
    }

    private Vec3d a(float f) {
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
                if (!EntityUtils.a((LivingEntity) PlayerEntityVar2) && !FriendLookup.a(PlayerEntityVar2.getName().getString()) && PlayerEntityVar2.getEquippedStack(EquipmentSlot.CHEST).getItem() == Items.ELYTRA) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean b(Box BoxVar) {
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

    private boolean a(List<BlockPos> list) {
        Iterator<Entity> it = EntityFinder.a(EntityVar -> {
            return EntityVar instanceof PlayerEntity;
        }, EntityTargetType.PLAYER).iterator();
        while (it.hasNext()) {
            PlayerEntity PlayerEntityVar = (PlayerEntity) it.next();
            if (PlayerEntityVar != c.player) {
                if (FriendLookup.a(PlayerEntityVar.getName().getString())) {
                    continue;
                } else {
                    BlockPos BlockPosVarGetBlockPos = PlayerEntityVar.getBlockPos();
                    Iterator<BlockPos> it2 = list.iterator();
                    while (it2.hasNext()) {
                        if (BlockPosVarGetBlockPos.equals(it2.next())) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
