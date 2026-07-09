package drunvisual.events;

import net.minecraft.network.packet.Packet;

public class PacketEvent extends CancellableDrunVisualEvent {
    private final Packet<?> packet;
    private final MessageDirection direction;

    public enum MessageDirection {
        RECEIVE,
        SEND;

        public static final MessageDirection RECIEVE = RECEIVE;
    }

    public PacketEvent(Packet<?> PacketVar, MessageDirection messageDirection) {
        this.packet = PacketVar;
        this.direction = messageDirection;
    }

    public Packet<?> packet() {
        return this.packet;
    }

    public MessageDirection direction() {
        return this.direction;
    }

    public Packet<?> d() {
        return this.packet;
    }

    public MessageDirection e() {
        return this.direction;
    }
}
