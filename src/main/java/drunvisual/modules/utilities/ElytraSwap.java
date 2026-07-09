package drunvisual.modules.utilities;

import java.util.Optional;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.component.type.EquippableComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.component.DataComponentTypes;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.KeySetting;
import drunvisual.config.ConfigState;
import drunvisual.core.Bool;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.KeyInputEvent;
import drunvisual.inventory.InventoryFinder;

@ModuleInfo(a = "Elytra Swap", b = "Автоматически меняет нагрудник на элитры при падении", c = ModuleCategory.UTILITIES)
public class ElytraSwap extends ClientModule {
    private final KeySetting e = new KeySetting("Кнопка свапа", -1);
    private boolean f = false;
    private int g = 0;
    private boolean h = false;
    private int i = 0;
    public static int a;
    public static boolean b;

    @EventHandler
    public void a(KeyInputEvent keyInputEvent) {
        if (c.player == null || c.world == null || c.currentScreen != null || keyInputEvent.a() != this.e.k().intValue() || keyInputEvent.c() != 1 || this.f) {
            return;
        }
        this.f = true;
        this.g = 0;
        this.h = false;
        this.i = 0;
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.player == null || !this.f) {
            return;
        }
        if (this.i > 0) {
            int i = this.i;
            this.i = (i ^ 1) - (2 * ((i ^ (-1)) & 1));
        }
        switch (this.g) {
            case EventPriority.MEDIUM /* 0 */:
                if (!c.player.getAbilities().creativeMode) {
                    if (!q()) {
                        if (!o().isPresent()) {
                            c.player.sendMessage(Text.of(g() + ": Свап отменен: нет элитр."), true);
                            this.f = false;
                        }
                    } else if (!p().isPresent()) {
                        c.player.sendMessage(Text.of(g() + ": Свап отменен: нет нагрудника."), true);
                        this.f = false;
                    }
                    if (!(c.currentScreen instanceof InventoryScreen)) {
                        c.setScreen(new InventoryScreen(c.player));
                    }
                    this.i = 2;
                    this.g = 1;
                } else {
                    this.g = 1;
                }
                break;
            case ConfigState.a /* 1 */:
                if ((c.currentScreen instanceof InventoryScreen) && !this.h) {
                    n();
                    this.h = true;
                    this.i = 2;
                    this.g = 2;
                    break;
                }
                break;
            case 2:
                if (c.currentScreen instanceof InventoryScreen) {
                    c.player.closeHandledScreen();
                }
                this.f = false;
                this.g = 0;
                break;
        }
    }

    private void n() {
        if (q()) {
            Optional<Integer> optionalP = p();
            if (optionalP.isPresent()) {
                b(optionalP.get().intValue());
                c.player.sendMessage(Text.of(g() + ": Снял элитры."), true);
                return;
            }
            return;
        }
        Optional<Integer> optionalO = o();
        if (optionalO.isPresent()) {
            b(optionalO.get().intValue());
            c.player.sendMessage(Text.of(g() + ": Надел элитры."), true);
        }
    }

    private void b(int i) {
        c.interactionManager.clickSlot(c.player.currentScreenHandler.syncId, i, 40, SlotActionType.SWAP, c.player);
        c.interactionManager.clickSlot(c.player.currentScreenHandler.syncId, 6, 40, SlotActionType.SWAP, c.player);
        c.interactionManager.clickSlot(c.player.currentScreenHandler.syncId, i, 40, SlotActionType.SWAP, c.player);
    }

    private Optional<Integer> o() {
        return InventoryFinder.a(ItemStackVar -> {
            return Bool.from(ItemStackVar.getItem() != Items.ELYTRA ? 0 : 1);
        }, true, false);
    }

    private Optional<Integer> p() {
        return InventoryFinder.a(this::a, true, false);
    }

    private boolean a(ItemStack ItemStackVar) {
        if (ItemStackVar.getItem() == Items.ELYTRA) {
            return false;
        }
        EquippableComponent EquippableComponentVar = (EquippableComponent) ItemStackVar.get(DataComponentTypes.EQUIPPABLE);
        return Bool.from((EquippableComponentVar == null || EquippableComponentVar.slot() != EquipmentSlot.CHEST) ? 0 : 1);
    }

    private boolean q() {
        return Bool.from(c.player.getEquippedStack(EquipmentSlot.CHEST).getItem() != Items.ELYTRA ? 0 : 1);
    }

    @Override
    public void e() {
        super.e();
        this.f = false;
        this.g = 0;
        this.h = false;
        this.i = 0;
    }

    @Override
    public void f() {
        super.f();
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
