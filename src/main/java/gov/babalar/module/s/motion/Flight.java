package gov.babalar.module.s.motion;

import com.google.common.eventbus.Subscribe;
import gov.babalar.Myth;
import gov.babalar.event.events.PacketSentEvent;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import gov.babalar.utils.ReflectionUtils;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Flight extends Module {
    public Flight() {
        super("Flight", Category.MOTION, NativeKeyEvent.VC_F);
    }
    public ArrayList<Object> packets = new ArrayList<>();
    @Override
    public void onEnable() {
        Mapper.addChatMessage("Packet Collect Started: " + packets.size());
    }

    @Override
    public void onDisable() {
        Mapper.addChatMessage("Dispatched packets: " + packets.size());;
        Object lastC04 = null;
        int c04s = 0;
        for (Object packet : packets) {
            if(packet.getClass().equals(Myth.SERVERBOUND_packets.get(4).getClass()))
            {
                c04s++;
                lastC04 = packet;
            }
        }
        Mapper.addChatMessage("C04S: " + c04s);
        packets.forEach(Mapper::sendPacket);
        if(lastC04 != null) {
            for (int i = 0; i < c04s; i++) {
                Mapper.sendPacket(lastC04);
            }
        }
        packets.clear();
    }
    @Override
    public void run() {
        try
        {
           {
               Mapper.setMotionY(0);
           }
        }
        catch(Exception e)
        {
            Myth.logger.logException(e);
        }

    }
    @Subscribe
    public void onPacketSent(PacketSentEvent event) {
        if(this.isEnabled()) {
            packets.add(event.getPacket());
            event.setCancelled(true);
        }
    }
}