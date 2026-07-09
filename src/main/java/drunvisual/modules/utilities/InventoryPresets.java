package drunvisual.modules.utilities;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.KeyInputEvent;
import drunvisual.media.chat.ChatMessages;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.settings.KeySetting;
import drunvisual.settings.SliderSetting;
import drunvisual.settings.StringSetting;

@ModuleInfo(a = "InventoryPresets", b = "Сохраняет и применяет раскладки хотбара.", c = ModuleCategory.UTILITIES)
public class InventoryPresets extends ClientModule {
    private static final int HOTBAR_SIZE = 9;
    private static final String EMPTY_TOKEN = "-";
    private final KeySetting savePreset1 = new KeySetting("Сохранить пресет 1", -1);
    private final KeySetting applyPreset1 = new KeySetting("Применить пресет 1", -1);
    private final KeySetting savePreset2 = new KeySetting("Сохранить пресет 2", -1);
    private final KeySetting applyPreset2 = new KeySetting("Применить пресет 2", -1);
    private final KeySetting savePreset3 = new KeySetting("Сохранить пресет 3", -1);
    private final KeySetting applyPreset3 = new KeySetting("Применить пресет 3", -1);
    private final SliderSetting actionDelay = new SliderSetting("Задержка", 1.0f, 0.0f, 6.0f, 1.0f);
    private final BooleanSetting chat = new BooleanSetting("Чат", true);
    private final StringSetting preset1Data = new StringSetting("Preset 1 Data", "").a(() -> {
        return Boolean.FALSE;
    });
    private final StringSetting preset2Data = new StringSetting("Preset 2 Data", "").a(() -> {
        return Boolean.FALSE;
    });
    private final StringSetting preset3Data = new StringSetting("Preset 3 Data", "").a(() -> {
        return Boolean.FALSE;
    });
    private int pendingPresetIndex = -1;
    private List<String> pendingTokens = List.of();
    private int applySlot = 0;
    private int actionCooldownTicks = 0;
    private boolean missingItems = false;

    @EventHandler
    public void onKey(KeyInputEvent keyInputEvent) {
        if (c.player == null || c.world == null || keyInputEvent.c() != 1 || c.currentScreen != null) {
            return;
        }
        int key = keyInputEvent.a();
        if (key == this.savePreset1.key()) {
            savePreset(0);
            return;
        }
        if (key == this.applyPreset1.key()) {
            beginApply(0);
            return;
        }
        if (key == this.savePreset2.key()) {
            savePreset(1);
            return;
        }
        if (key == this.applyPreset2.key()) {
            beginApply(1);
            return;
        }
        if (key == this.savePreset3.key()) {
            savePreset(2);
            return;
        }
        if (key == this.applyPreset3.key()) {
            beginApply(2);
        }
    }

    @EventHandler
    public void onTick(ClientTickEvent clientTickEvent) {
        if (this.pendingPresetIndex == -1 || c.player == null || c.interactionManager == null) {
            return;
        }
        if (c.currentScreen != null) {
            finishApply(false);
            return;
        }
        if (this.actionCooldownTicks > 0) {
            this.actionCooldownTicks--;
            return;
        }
        while (this.applySlot < HOTBAR_SIZE) {
            String wanted = tokenAt(this.pendingTokens, this.applySlot);
            if (wanted.isBlank() || EMPTY_TOKEN.equals(wanted) || isDesiredAlreadyInSlot(this.applySlot, wanted)) {
                this.applySlot++;
                continue;
            }
            int sourceIndex = findSourceIndex(wanted, this.applySlot);
            if (sourceIndex == -1) {
                this.missingItems = true;
                this.applySlot++;
                continue;
            }
            c.interactionManager.clickSlot(c.player.currentScreenHandler.syncId, inventoryIndexToSlotId(sourceIndex), this.applySlot, SlotActionType.SWAP, c.player);
            this.applySlot++;
            this.actionCooldownTicks = this.actionDelay.roundedInt();
            return;
        }
        finishApply(true);
    }

    private void savePreset(int index) {
        StringBuilder builder = new StringBuilder();
        for (int slot = 0; slot < HOTBAR_SIZE; slot++) {
            if (slot > 0) {
                builder.append('|');
            }
            ItemStack stack = c.player.getInventory().getStack(slot);
            builder.append(encodeStack(stack));
        }
        presetData(index).set(builder.toString());
        if (this.chat.get()) {
            ChatMessages.a((Object) ("Inventory Presets: сохранён пресет " + (index + 1) + "."));
        }
    }

    private void beginApply(int index) {
        String raw = presetData(index).get().trim();
        if (raw.isBlank()) {
            if (this.chat.get()) {
                ChatMessages.a((Object) ("Inventory Presets: пресет " + (index + 1) + " пуст."));
            }
            return;
        }
        this.pendingPresetIndex = index;
        this.pendingTokens = Arrays.asList(raw.split("\\|", -1));
        this.applySlot = 0;
        this.actionCooldownTicks = 0;
        this.missingItems = false;
        if (this.chat.get()) {
            ChatMessages.a((Object) ("Inventory Presets: применяю пресет " + (index + 1) + "."));
        }
    }

    private void finishApply(boolean success) {
        int index = this.pendingPresetIndex;
        this.pendingPresetIndex = -1;
        this.pendingTokens = List.of();
        this.applySlot = 0;
        this.actionCooldownTicks = 0;
        if (!this.chat.get()) {
            this.missingItems = false;
            return;
        }
        if (!success) {
            ChatMessages.a((Object) "Inventory Presets: применение остановлено.");
        } else if (this.missingItems) {
            ChatMessages.a((Object) ("Inventory Presets: пресет " + (index + 1) + " применён частично."));
        } else {
            ChatMessages.a((Object) ("Inventory Presets: пресет " + (index + 1) + " применён."));
        }
        this.missingItems = false;
    }

    private boolean isDesiredAlreadyInSlot(int slot, String token) {
        return encodeStack(c.player.getInventory().getStack(slot)).equals(token);
    }

    private int findSourceIndex(String wanted, int targetSlot) {
        for (int slot = 0; slot < 36; slot++) {
            if (slot == targetSlot) {
                continue;
            }
            if (encodeStack(c.player.getInventory().getStack(slot)).equals(wanted)) {
                return slot;
            }
        }
        return -1;
    }

    private int inventoryIndexToSlotId(int inventoryIndex) {
        if (inventoryIndex >= 0 && inventoryIndex < HOTBAR_SIZE) {
            return 36 + inventoryIndex;
        }
        return inventoryIndex;
    }

    private String encodeStack(ItemStack stack) {
        if (stack == null || stack.isEmpty()) {
            return EMPTY_TOKEN;
        }
        return Registries.ITEM.getId(stack.getItem()).toString().toLowerCase(Locale.ROOT);
    }

    private StringSetting presetData(int index) {
        switch (index) {
            case 0:
                return this.preset1Data;
            case 1:
                return this.preset2Data;
            default:
                return this.preset3Data;
        }
    }

    private String tokenAt(List<String> list, int index) {
        return index < list.size() ? list.get(index) : "";
    }

    @Override
    public void f() {
        super.f();
        this.pendingPresetIndex = -1;
        this.pendingTokens = List.of();
        this.applySlot = 0;
        this.actionCooldownTicks = 0;
        this.missingItems = false;
    }
}
