package drunvisual.modules.utilities;

import java.util.Optional;
import java.util.function.Predicate;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.text.Text;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.KeySetting;
import drunvisual.settings.ModeSetting;
import drunvisual.events.ClientTickEvent;
import drunvisual.events.KeyInputEvent;

@ModuleInfo(a = "Item Swap", b = "Автоматически меняет предмет в левой руке при нажатии клавиши", c = ModuleCategory.UTILITIES)
public class ItemSwap extends ClientModule {
    private final ModeSetting e = new ModeSetting("Менять с", new String[]{"Сфера", "Тотем"}, "Сфера");
    private final ModeSetting f = new ModeSetting("Менять на", new String[]{"Сфера", "Тотем"}, "Тотем");
    private final KeySetting g = new KeySetting("Кнопка свапа", -1);
    private boolean h = false;
    private boolean i = false;
    private int j = 0;
    private int k = 0;
    private boolean l = false;
    private int m = 0;
    private int n = 0;
    private String o = "";
    private int p = -1;
    private int q = -1;
    public static int a;
    public static boolean b;

    @EventHandler
    public void a(KeyInputEvent keyInputEvent) {
        if (c.player != null && c.world != null && c.currentScreen == null && keyInputEvent.a() == this.g.a() && keyInputEvent.c() == 1) {
            this.o = q();
            Optional<Integer> optionalA = a(ItemStackVar -> {
                return a(ItemStackVar, this.o);
            });
            if (optionalA.isPresent()) {
                b(optionalA.get().intValue());
                return;
            }
            if (!a(this.o)) {
                c.player.sendMessage(Text.literal("§cНе найден предмет для свапа!"), true);
                return;
            }
            this.h = true;
            this.j = 0;
            this.l = false;
            this.m = 0;
        }
    }

    @EventHandler
    public void a(ClientTickEvent clientTickEvent) {
        if (c.player != null) {
            if (this.i) {
                n();
            }
            if (this.h) {
                o();
            }
        }
    }

    private void b(int i) {
        this.p = c.player.getInventory().selectedSlot;
        this.q = i;
        this.i = true;
        this.k = 0;
        this.n = 0;
    }

    private void n() {
        if (this.n > 0) {
            this.n--;
        }
        switch (this.k) {
            case 0:
                c.player.getInventory().selectedSlot = this.q;
                this.n = 2;
                this.k = 1;
                break;
            case 1:
                c.player.networkHandler.sendPacket(new PlayerActionC2SPacket(PlayerActionC2SPacket.Action.SWAP_ITEM_WITH_OFFHAND, BlockPos.ORIGIN, Direction.DOWN));
                this.n = 2;
                this.k = 2;
                break;
            case 2:
                c.player.getInventory().selectedSlot = this.p;
                c.player.sendMessage(Text.literal("§fСвапнул на ").append(c.player.getOffHandStack().getName()), true);
                this.i = false;
                this.k = 0;
                break;
        }
    }

    private void o() {
        if (this.m > 0) {
            this.m--;
            return;
        }
        switch (this.j) {
            case 0:
                if (c.player.getAbilities().creativeMode) {
                    this.j = 1;
                } else if (!b(ItemStackVar -> {
                    return a(ItemStackVar, this.o);
                }).isPresent()) {
                    c.player.sendMessage(Text.literal("§cНе найден предмет для свапа!"), true);
                    this.h = false;
                } else {
                    if (!(c.currentScreen instanceof InventoryScreen)) {
                        c.setScreen(new InventoryScreen(c.player));
                    }
                    this.m = 2;
                    this.j = 1;
                }
                break;
            case 1:
                if ((c.currentScreen instanceof InventoryScreen) && !this.l) {
                    p();
                    this.l = true;
                    this.m = 2;
                    this.j = 2;
                    break;
                }
                break;
            case 2:
                if (c.currentScreen instanceof InventoryScreen) {
                    c.setScreen((Screen) null);
                }
                this.h = false;
                this.j = 0;
                break;
        }
    }

    private void p() {
        Optional<Integer> optionalB = b(ItemStackVar -> {
            return a(ItemStackVar, this.o);
        });
        if (optionalB.isPresent()) {
            c(optionalB.get().intValue());
            c.player.sendMessage(Text.literal("§fСвапнул на ").append(c.player.getOffHandStack().getName()), true);
        }
    }

    private void c(int i) {
        c.interactionManager.clickSlot(c.player.currentScreenHandler.syncId, i, 40, SlotActionType.SWAP, c.player);
    }

    private String q() {
        ItemStack ItemStackVarGetOffHandStack = c.player.getOffHandStack();
        String strD = this.e.d();
        return !a(ItemStackVarGetOffHandStack, strD) ? strD : this.f.d();
    }

    private boolean a(String str) {
        return ("Тотем".equals(str) || "Сфера".equals(str)) ? true : false;
    }

    private boolean a(ItemStack ItemStackVar, String str) {
        if (ItemStackVar.isEmpty()) {
            return false;
        }
        if ("Сфера".equals(str)) {
            return ItemStackVar.getItem() == Items.PLAYER_HEAD;
        }
        if ("Тотем".equals(str)) {
            return ItemStackVar.getItem() == Items.TOTEM_OF_UNDYING;
        }
        return false;
    }

    private Optional<Integer> a(Predicate<ItemStack> predicate) {
        for (int i = 0; i < 9; i++) {
            if (predicate.test(c.player.getInventory().getStack(i))) {
                return Optional.of(Integer.valueOf(i));
            }
        }
        return Optional.empty();
    }

    private Optional<Integer> b(Predicate<ItemStack> predicate) {
        for (int i = 9; i < 36; i++) {
            if (predicate.test(c.player.getInventory().getStack(i))) {
                return Optional.of(Integer.valueOf(i));
            }
        }
        return Optional.empty();
    }

    @Override
    public void e() {
        super.e();
        this.h = false;
        this.i = false;
        this.j = 0;
        this.k = 0;
        this.l = false;
        this.m = 0;
        this.n = 0;
    }

    @Override
    public void f() {
        super.f();
        if (c.currentScreen instanceof InventoryScreen) {
            c.player.closeHandledScreen();
        }
        this.i = false;
        this.h = false;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
