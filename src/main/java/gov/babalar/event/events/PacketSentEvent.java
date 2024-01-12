package gov.babalar.event.events;

import gov.babalar.event.Event;

public class PacketSentEvent extends Event {

    public Object packet;

    public PacketSentEvent(Object packet) {
        this.packet = packet;
    }

    public Object getPacket() {
        return packet;
    }

    public void setPacket(Object packet) {
        this.packet = packet;
    }
}

