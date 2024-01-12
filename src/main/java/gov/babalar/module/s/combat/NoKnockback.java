package gov.babalar.module.s.combat;

import com.google.common.eventbus.Subscribe;
import gov.babalar.Myth;
import gov.babalar.event.events.KeyEvent;
import gov.babalar.event.events.PacketReceiveEvent;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

public class NoKnockback extends Module {

    public NoKnockback() {
        super("NoKnockBack", Category.COMBAT, NativeKeyEvent.VC_B);
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {

    }

    @Subscribe
    public void onPacketReceive(PacketReceiveEvent event) {
        if(event.getPacket().getClass().equals(Myth.CLIENTBOUND_packets.get(18).getClass()))
        {
            event.setCancelled(true);
        }
    }
}
