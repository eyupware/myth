package gov.babalar.event.events;

import gov.babalar.event.Event;

public class PacketReceiveEvent extends Event {

    public Object packet;

    public PacketReceiveEvent(Object packet) {
        this.packet = packet;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}
