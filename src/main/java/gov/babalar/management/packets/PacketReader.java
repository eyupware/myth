package gov.babalar.management.packets;

import gov.babalar.Myth;
import gov.babalar.event.events.PacketReceiveEvent;
import gov.babalar.helpers.Mapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class    PacketReader extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception {
        PacketReceiveEvent packetReceiveEvent = new PacketReceiveEvent(packet);
        Myth.bus.post(packetReceiveEvent);
        if(packetReceiveEvent.isCancelled())
        {
            return;
        }
        super.channelRead(channelHandlerContext, packet);
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        Myth.logger.logThrowable(cause);
    }
}
