package drunvisual.modules.utilities;

import java.util.HashMap;
import java.util.Map;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.potion.Potion;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.component.DataComponentTypes;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SliderSetting;
import drunvisual.core.Bool;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.ItemUseEvent;
import drunvisual.events.ItemUseFinishEvent;
import drunvisual.player.CombatState;

@ModuleInfo(a = "Cooldowns", b = "Добавляет кастомный кулдаун на зелья исцеления", c = ModuleCategory.UTILITIES)
public class Cooldowns extends ClientModule {
    private final BooleanSetting e = new BooleanSetting("Преобразовывать задержки", true);
    private final BooleanSetting f = new BooleanSetting("Только в Пвп (Исцеление)", true);
    private final SliderSetting g = new SliderSetting("Размер шрифта таймеров", 1.0f, 0.5f, 2.0f, 0.1f);
    private final Map<Item, Long> h = new HashMap();
    public static int a;
    public static boolean b;

    @Override
    public void f() {
        this.h.clear();
        super.f();
    }

    @EventHandler
    public void a(ItemUseFinishEvent itemUseFinishEvent) {
        if (!this.f.k().booleanValue()) {
        } else if (!CombatState.a().a()) {
            return;
        }
        ItemStack ItemStackVarD = itemUseFinishEvent.d();
        if (a(ItemStackVarD)) {
            this.h.put(ItemStackVarD.getItem(), Long.valueOf(System.currentTimeMillis()));
        }
    }

    @EventHandler
    public void a(ItemUseEvent itemUseEvent) {
        if (c.player != null) {
            net.minecraft.item.ItemStack stackInHand = itemUseEvent.d().getStackInHand(itemUseEvent.f());
            if (a(stackInHand)) {
                if (System.currentTimeMillis() - this.h.getOrDefault(stackInHand.getItem(), 0L).longValue() < 18500.0d) {
                    itemUseEvent.b();
                }
            }
        }
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (!this.f.k().booleanValue()) {
        } else {
            if (CombatState.a().a()) {
                return;
            }
            this.h.clear();
        }
    }

    public static boolean a(ItemStack ItemStackVar) {
        PotionContentsComponent PotionContentsComponentVar;
        if (!(ItemStackVar.getItem() instanceof PotionItem)) {
            return false;
        }
        if (ItemStackVar.contains(DataComponentTypes.POTION_CONTENTS) && (PotionContentsComponentVar = (PotionContentsComponent) ItemStackVar.get(DataComponentTypes.POTION_CONTENTS)) != null) {
            return (PotionContentsComponentVar.potion().isPresent() && ((Potion) ((RegistryEntry) PotionContentsComponentVar.potion().get()).value()).getEffects().stream().anyMatch(StatusEffectInstanceVar -> {
                return Bool.from(StatusEffectInstanceVar.getEffectType() != StatusEffects.INSTANT_HEALTH ? 0 : 1);
            })) ? true : PotionContentsComponentVar.customEffects().stream().anyMatch(StatusEffectInstanceVar2 -> {
                return Bool.from(StatusEffectInstanceVar2.getEffectType() != StatusEffects.INSTANT_HEALTH ? 0 : 1);
            });
        }
        return false;
    }

    private float c(Item ItemVar) {
        long jCurrentTimeMillis = System.currentTimeMillis() - this.h.getOrDefault(ItemVar, 0L).longValue();
        if (jCurrentTimeMillis >= 18500.0d) {
            this.h.remove(ItemVar);
            return 0.0f;
        }
        return 1.0f - ((float) (jCurrentTimeMillis / 18500.0d));
    }

    public float a(Item ItemVar) {
        return c(ItemVar);
    }

    public boolean b(Item ItemVar) {
        if (this.h.containsKey(ItemVar)) {
            if (a(ItemVar) > 0.0f) {
                return true;
            }
        } else if (b) {
            throw new ExceptionInInitializerError();
        }
        return false;
    }

    public boolean n() {
        return this.e.k().booleanValue();
    }

    @Generated
    public BooleanSetting o() {
        return this.e;
    }

    @Generated
    public BooleanSetting p() {
        return this.f;
    }

    @Generated
    public SliderSetting q() {
        return this.g;
    }

    @Generated
    public Map<Item, Long> r() {
        return this.h;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
