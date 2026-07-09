package drunvisual.modules.utilities;

import java.util.Iterator;
import lombok.Generated;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.text.MutableText;
import drunvisual.events.PacketEvent;
import drunvisual.module.ClientModule;
import drunvisual.module.ModuleCategory;
import drunvisual.module.ModuleInfo;
import drunvisual.settings.BooleanSetting;
import drunvisual.media.chat.ChatMessages;

@ModuleInfo(a = "Item Pickup Logger", b = "Логирует поднятые предметы в чат", c = ModuleCategory.UTILITIES)
public class ItemPickupLogger extends ClientModule {
    private final BooleanSetting e = new BooleanSetting("Только донат предметы", false);
    public static int a;
    public static boolean b;

    @EventHandler
    public void a(PacketEvent packetEvent) {
        if (packetEvent.e() != PacketEvent.MessageDirection.RECIEVE || c.player == null || c.world == null || !(packetEvent.d() instanceof ItemPickupAnimationS2CPacket)) {
            return;
        }
        ItemPickupAnimationS2CPacket ItemPickupAnimationS2CPacketVarD = (ItemPickupAnimationS2CPacket) packetEvent.d();
        if (ItemPickupAnimationS2CPacketVarD.getCollectorEntityId() != c.player.getId()) {
            return;
        }
        net.minecraft.entity.Entity entityById = c.world.getEntityById(ItemPickupAnimationS2CPacketVarD.getEntityId());
        if (entityById instanceof ItemEntity) {
            ItemEntity ItemEntityVarGetEntityById = (ItemEntity) entityById;
            ItemStack ItemStackVarGetStack = ItemEntityVarGetEntityById.getStack();
            if (ItemStackVarGetStack.isEmpty()) {
                return;
            }
            boolean zA = a(ItemStackVarGetStack);
            if (!this.e.k().booleanValue() || zA) {
                Text TextVarGetName = ItemStackVarGetStack.getName();
                int iGetCount = ItemStackVarGetStack.getCount();
                MutableText MutableTextVarAppend = !zA ? Text.literal("§7Поднят: ").append(TextVarGetName) : Text.literal("§7Поднят донат предмет: ").append(TextVarGetName);
                if (iGetCount > 1) {
                    MutableTextVarAppend.append(Text.literal(" §7x" + iGetCount));
                }
                ChatMessages.b((Text) MutableTextVarAppend);
            }
        }
    }

    private boolean a(ItemStack ItemStackVar) {
        Text MutableTextVarGetName = ItemStackVar.getName();
        if (MutableTextVarGetName.getString().contains("★")) {
            return true;
        }
        if (MutableTextVarGetName instanceof MutableText) {
            MutableText MutableTextVar = (MutableText) MutableTextVarGetName;
            if (MutableTextVar.getStyle().isObfuscated()) {
                return true;
            }
            Iterator it = MutableTextVar.getSiblings().iterator();
            while (it.hasNext()) {
                if (((Text) it.next()).getStyle().isObfuscated()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Generated
    public BooleanSetting n() {
        return this.e;
    }

    public static String c(String str, String str2, int i, int i2, int i3, int i4) {
        return null;
    }
}
