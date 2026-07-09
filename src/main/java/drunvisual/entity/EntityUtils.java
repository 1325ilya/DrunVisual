package drunvisual.entity;

import java.util.Iterator;
import lombok.Generated;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;

public final class EntityUtils {
    public static int a;
    public static boolean b;

    public static Vec3d a(Entity EntityVar, float f) {
        Vec3d Vec3dVarA = a(EntityVar);
        return Vec3dVarA.add(EntityVar.getPos().subtract(Vec3dVarA).multiply(f));
    }

    public static Vec3d a(Entity EntityVar) {
        return new Vec3d(EntityVar.prevX, EntityVar.prevY, EntityVar.prevZ);
    }

    public static Vec3d b(Entity EntityVar, float f) {
        return a(EntityVar, f);
    }

    public static boolean a(LivingEntity LivingEntityVar) {
        if (!(LivingEntityVar instanceof PlayerEntity)) {
            return false;
        }
        PlayerEntity PlayerEntityVar = (PlayerEntity) LivingEntityVar;
        if (!PlayerEntityVar.isInvisible()) {
            return false;
        }
        Iterator it = PlayerEntityVar.getArmorItems().iterator();
        while (it.hasNext()) {
            if (!((ItemStack) it.next()).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Generated
    private EntityUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
