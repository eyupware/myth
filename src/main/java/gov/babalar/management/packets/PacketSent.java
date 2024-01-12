package gov.babalar.management.packets;

import gov.babalar.Myth;
import gov.babalar.event.events.PacketReceiveEvent;
import gov.babalar.event.events.PacketSentEvent;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.MessageToByteEncoder;

public class PacketSent extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        PacketSentEvent packetSentEvent = new PacketSentEvent(msg);
        Myth.bus.post(packetSentEvent);
        if(packetSentEvent.isCancelled())
        {
            return;
        }
        super.write(ctx, packetSentEvent.getPacket(), promise);
    }
}
