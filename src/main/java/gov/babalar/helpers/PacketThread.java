package gov.babalar.helpers;

import gov.babalar.Myth;
import gov.babalar.management.packets.PacketReader;
import gov.babalar.management.packets.PacketSent;
import io.netty.channel.Channel;

public class PacketThread extends Thread{

    private final int timeout = 25;

    @Override
    public void run() {
        try {
            while(!this.isInterrupted()) {
                Thread.sleep(timeout * 1000);
                Channel channel = Mapper.getChannel();
                if (channel != null) {
                    if(channel.pipeline().get("myth_receive") == null) {
                        if(channel.pipeline().get("solauncher_receive") != null)
                            channel.pipeline().addBefore("solauncher_receive", "myth_receive", new PacketReader());
                        else
                            channel.pipeline().addBefore("packet_handler", "myth_receive", new PacketReader());
                    }
                    if(channel.pipeline().get("myth_sent") == null)
                    {
                        channel.pipeline().addAfter("encoder", "myth_sent", new PacketSent());
                    }
                    if(channel.pipeline().get("solauncher_receive") != null)
                    {
                        channel.pipeline().remove("solauncher_receive");
                    }
                    if(channel.pipeline().get("solauncher_sent") != null)
                    {
                        channel.pipeline().remove("solauncher_sent");
                    }
                }
            }
        }catch(Exception e)
        {
            Myth.logger.logException(e);
        }
    }
}
