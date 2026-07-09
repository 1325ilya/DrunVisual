package drunvisual.inventory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.item.Item;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registries;
import ru.drunvisual.mixin.accessor.ItemCooldownEntryAccessor;
import ru.drunvisual.mixin.accessor.ItemCooldownManagerAccessor;
import drunvisual.core.Bool;

public class CooldownInfo {
    private static Field d;
    private static Field e;
    private static Field f;
    private static Field g;
    public static int b;
    public static boolean c;
    private static final Item[] WATCHED_ITEMS = {Items.CHORUS_FRUIT, Items.POPPED_CHORUS_FRUIT, Items.ENDER_PEARL, Items.SHIELD, Items.CROSSBOW, Items.GOLDEN_APPLE, Items.ENCHANTED_GOLDEN_APPLE, Items.TOTEM_OF_UNDYING, Items.TRIDENT, Items.BOW, Items.WIND_CHARGE};
    public static final ItemCooldownSnapshot a = new ItemCooldownSnapshot(0, 0, 0);

    public static final class ActiveGroupCooldown {
        public final Identifier groupId;
        public final int remainingTicks;

        public ActiveGroupCooldown(Identifier IdentifierVar, int i) {
            this.groupId = IdentifierVar;
            this.remainingTicks = i;
        }
    }

    public static class ItemCooldownSnapshot {
        public final int a;
        public final int b;
        public final int c;
        public static int d;
        public static boolean e;

        public ItemCooldownSnapshot(int i, int i2, int i3) {
            this.a = i;
            this.b = i2;
            this.c = i3;
        }

        public boolean a() {
            return Bool.from(this.c <= this.a ? 0 : 1);
        }

        public float b() {
            float f;
            if (a()) {
                f = ((this.c + (this.a ^ (-1))) + 1) / 20.0f;
            } else {
                f = 0.0f;
            }
            return f;
        }

        public float c() {
            if (this.c <= this.a) {
                return 0.0f;
            }
            int i = this.a;
            int i2 = this.c;
            return ((2 * (i2 & (i ^ (-1)))) - (i2 ^ i)) / ((this.c + (this.b ^ (-1))) + 1);
        }

        public static String a(String str, String str2, int i, int i2, int i3, int i4) {
            return null;
        }
    }

    private static Field a(Class<?> cls, String... strArr) {
        for (String str : strArr) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                declaredField.setAccessible(true);
                return declaredField;
            } catch (NoSuchFieldException e2) {
            }
        }
        return null;
    }

    private static Class<?> a() {
        try {
            return Class.forName("net.minecraft.entity.player.ItemCooldownManager$Entry");
        } catch (ClassNotFoundException e2) {
            try {

                return Class.forName("net.minecraft.ItemCooldownManager$class_1797");
            } catch (ClassNotFoundException e3) {
                return null;
            }
        }
    }

    public static ItemCooldownSnapshot a(ItemCooldownManager ItemCooldownManagerVar, Item ItemVar) {
        return a(ItemCooldownManagerVar, ItemCooldownManagerVar.getGroup(ItemVar.getDefaultStack()));
    }

    public static ItemCooldownSnapshot a(ItemCooldownManager ItemCooldownManagerVar, Identifier IdentifierVar) {
        try {
            ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor) ItemCooldownManagerVar;
            Object obj = itemCooldownManagerAccessor.drunvisual$getEntries().get(IdentifierVar);
            if (obj == null) {
                return a;
            }
            ItemCooldownEntryAccessor itemCooldownEntryAccessor = (ItemCooldownEntryAccessor) obj;
            return new ItemCooldownSnapshot(itemCooldownManagerAccessor.drunvisual$getTick(), itemCooldownEntryAccessor.drunvisual$getStartTick(), itemCooldownEntryAccessor.drunvisual$getEndTick());
        } catch (Throwable th) {
            return a(ItemCooldownManagerVar, IdentifierVar, false);
        }
    }

    private static ItemCooldownSnapshot a(ItemCooldownManager ItemCooldownManagerVar, Identifier IdentifierVar, boolean z) {
        if (d == null || e == null || f == null || g == null) {
            return a;
        }
        try {
            int i = d.getInt(ItemCooldownManagerVar);
            Object obj = ((Map) e.get(ItemCooldownManagerVar)).get(IdentifierVar);
            return obj == null ? a : new ItemCooldownSnapshot(i, f.getInt(obj), g.getInt(obj));
        } catch (Exception e2) {
            return a;
        }
    }

    public static List<ActiveGroupCooldown> collectActiveGroups(ItemCooldownManager ItemCooldownManagerVar) {
        ArrayList arrayList = new ArrayList();
        try {
            ItemCooldownManagerAccessor itemCooldownManagerAccessor = (ItemCooldownManagerAccessor) ItemCooldownManagerVar;
            int iPulse$getTick = itemCooldownManagerAccessor.drunvisual$getTick();
            for (Map.Entry<Identifier, ?> entry : itemCooldownManagerAccessor.drunvisual$getEntries().entrySet()) {
                int iPulse$getEndTick = ((ItemCooldownEntryAccessor) entry.getValue()).drunvisual$getEndTick();
                if (iPulse$getEndTick > iPulse$getTick) {
                    arrayList.add(new ActiveGroupCooldown(entry.getKey(), iPulse$getEndTick - iPulse$getTick));
                }
            }
        } catch (Throwable th) {
            arrayList.addAll(collectActiveGroupsReflective(ItemCooldownManagerVar));
        }
        return arrayList;
    }

    private static List<ActiveGroupCooldown> collectActiveGroupsReflective(ItemCooldownManager ItemCooldownManagerVar) {
        ArrayList arrayList = new ArrayList();
        if (d != null && e != null && f != null && g != null) {
            try {
                int i = d.getInt(ItemCooldownManagerVar);
                Map<?, ?> map = (Map<?, ?>) e.get(ItemCooldownManagerVar);
                if (map != null) {
                    for (Map.Entry<?, ?> entry : map.entrySet()) {
                        Object key = entry.getKey();
                        if (key instanceof Identifier) {
                            Identifier IdentifierVar = (Identifier) key;
                            int i2 = g.getInt(entry.getValue());
                            if (i2 > i) {
                                arrayList.add(new ActiveGroupCooldown(IdentifierVar, i2 - i));
                            }
                        }
                    }
                }
            } catch (Exception e2) {
            }
        }
        return arrayList;
    }

    public static ItemStack stackForGroup(ItemCooldownManager ItemCooldownManagerVar, Identifier IdentifierVar) {
        Item ItemVar = (Item) Registries.ITEM.get(IdentifierVar);
        if (ItemVar != Items.AIR) {
            return ItemVar.getDefaultStack();
        }
        for (Item ItemVar2 : WATCHED_ITEMS) {
            ItemStack ItemStackVarGetDefaultStack = ItemVar2.getDefaultStack();
            if (IdentifierVar.equals(ItemCooldownManagerVar.getGroup(ItemStackVarGetDefaultStack))) {
                return ItemStackVarGetDefaultStack;
            }
        }
        return new ItemStack(Items.PAPER);
    }

    public static String a(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }

    static {
        try {

d = a((Class<?>) ItemCooldownManager.class, "tick", "tick");
            if (d == null) {
                Field[] declaredFields = ItemCooldownManager.class.getDeclaredFields();
                int length = declaredFields.length;
                int i3 = 0;
                while (true) {
                    if (i3 >= length) {
                        break;
                    }
                    Field field = declaredFields[i3];
                    if (field.getType() == Integer.TYPE) {
                        field.setAccessible(true);
                        d = field;
                        break;
                    } else {
                        i3++;
                    }
                }
            }
            e = a((Class<?>) ItemCooldownManager.class, "entries", "cooldowns", "entries");
            if (e == null) {
                Field[] declaredFields2 = ItemCooldownManager.class.getDeclaredFields();
                int length2 = declaredFields2.length;
                int i7 = 0;
                while (true) {
                    if (i7 >= length2) {
                        break;
                    }
                    Field field2 = declaredFields2[i7];
                    if (Map.class.isAssignableFrom(field2.getType())) {
                        field2.setAccessible(true);
                        e = field2;
                        break;
                    }
                    i7++;
                }
            }
            Class<?> clsA = null;
            Class<?>[] declaredClasses = ItemCooldownManager.class.getDeclaredClasses();
            int length3 = declaredClasses.length;
            int i8 = 0;
            while (i8 < length3) {
                Class<?> cls = declaredClasses[i8];

                if (cls.getSimpleName().equals("Entry") || cls.isRecord()) {
                    clsA = cls;
                    break;
                } else {
                    i8++;
                }
            }
            if (clsA == null) {
                clsA = a();
            }
            if (clsA != null) {
                f = a(clsA, "startTime", "startTick", "startTick");
                g = a(clsA, "endTime", "endTick", "endTick");
                if (f == null || g == null) {
                    Field field3 = null;
                    Field field4 = null;
                    for (Field field5 : clsA.getDeclaredFields()) {
                        if (field5.getType() == Integer.TYPE) {
                            field5.setAccessible(true);
                            if (field3 == null) {
                                field3 = field5;
                            } else if (field4 == null) {
                                field4 = field5;
                            }
                        }
                    }
                    if (field3 != null && field4 != null) {
                        f = field3;
                        g = field4;
                    }
                }
            }
        } catch (Exception e2) {
        }
    }
}
