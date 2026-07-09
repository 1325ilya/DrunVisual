package drunvisual.modules.utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.SliderSetting;

@ModuleInfo(a = "Healing Helper", b = "Highlights useful healing items.", c = ModuleCategory.UTILITIES)
public class HealingHelper extends ClientModule {
    private final BooleanSetting onlyWhenNeeded = new BooleanSetting("Only When Needed", true);
    private final SliderSetting healthThreshold = new SliderSetting("Health Threshold", 14.0f, 1.0f, 20.0f, 1.0f);
    private final SliderSetting hungerThreshold = new SliderSetting("Hunger Threshold", 15.0f, 1.0f, 20.0f, 1.0f);
    private final SliderSetting minimumPriority = new SliderSetting("Minimum Priority", 1.0f, 1.0f, 4.0f, 1.0f);
    private final Map<Item, Integer> itemPriority;

    public HealingHelper() {
        HashMap map = new HashMap();
        map.put(Items.POTION, 1);
        map.put(Items.GOLDEN_CARROT, 2);
        map.put(Items.GOLDEN_APPLE, 3);
        map.put(Items.ENCHANTED_GOLDEN_APPLE, 4);
        this.itemPriority = Collections.unmodifiableMap(map);
    }

    public boolean a(ItemStack ItemStackVar) {
        if (ItemStackVar == null || ItemStackVar.isEmpty() || a(ItemStackVar.getItem()) < this.minimumPriority.b()) {
            return false;
        }
        return !this.onlyWhenNeeded.a() || shouldUseNow(ItemStackVar.getItem());
    }

    public List<Item> n() {
        return new ArrayList(this.itemPriority.keySet());
    }

    public int a(Item ItemVar) {
        return this.itemPriority.getOrDefault(ItemVar, 0).intValue();
    }

    public BooleanSetting o() {
        return this.onlyWhenNeeded;
    }

    public SliderSetting p() {
        return this.healthThreshold;
    }

    public SliderSetting q() {
        return this.hungerThreshold;
    }

    public SliderSetting r() {
        return this.minimumPriority;
    }

    public Map<Item, Integer> s() {
        return this.itemPriority;
    }

    private boolean shouldUseNow(Item ItemVar) {
        if (c.player == null) {
            return true;
        }
        return ItemVar == Items.GOLDEN_CARROT ? c.player.getHungerManager().getFoodLevel() <= this.hungerThreshold.b() : c.player.getHealth() + c.player.getAbsorptionAmount() <= this.healthThreshold.a();
    }
}
