package gov.babalar.management;

import com.google.common.eventbus.Subscribe;
import gov.babalar.Myth;
import gov.babalar.event.events.KeyEvent;
import gov.babalar.event.events.PacketSentEvent;
import gov.babalar.helpers.Mapper;
import gov.babalar.module.Module;
import gov.babalar.module.s.combat.KillAura;
import gov.babalar.module.s.combat.NoKnockback;
import gov.babalar.module.s.misc.*;
import gov.babalar.module.s.motion.*;
import gov.babalar.module.s.visual.ClickGui;
import gov.babalar.module.s.visual.Hud;
import gov.babalar.utils.ReflectionUtils;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opencl.CLCompileProgramCallback;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ----------
 * 9/3/2023
 * 3:07 AM
 * ----------
 **/
public enum ModuleManager {

    INSTANCE;

    public static ArrayList<Module> modules = new ArrayList<>();

    @Subscribe
    public void listenKey(KeyEvent event) {
        for (Module mod : modules) {
            if (mod.getKey() == event.getKey())
                mod.toggle();
        }
    }

    @Subscribe
    public void onPacketSent(PacketSentEvent event) {
        if(event.getPacket().getClass().equals(Myth.SERVERBOUND_packets.get(1).getClass()))
        {
            if(Mapper.methods.get("getMessage") == null)
                Mapper.methods.put("getMessage", Arrays.stream(ReflectionUtils.getAllMethods(event.getPacket().getClass())).filter(method -> method.toString().contains("public")).filter(method -> method.getParameterCount() == 0).filter(method -> method.getReturnType().equals(String.class)).findFirst().orElse(null));
            String message = (String) Mapper.invokeMethod(Mapper.methods.get("getMessage"), event.getPacket());
            if(message == null)
                return;
            if(message.startsWith(".")) {
                event.setCancelled(true);
                CommandManager.handle(message.replaceFirst("." , ""));
            }
        } else if(event.getPacket().getClass().equals(Myth.SERVERBOUND_packets.get(4).getClass()))
        {
            try {
                Class<?> c04Class = event.getPacket().getClass();
                if(Mapper.fields.get("movePField") == null) {
                    List<Field> fieldList = new ArrayList<>();
                    Arrays.stream(ReflectionUtils.getAllFields1(c04Class)).filter(field -> field.getType().equals(float.class))
                            .filter(field -> field.toString().contains("protected")).forEach(fieldList::add);
                    for (Method method : ReflectionUtils.getAllMethods1(c04Class)) {
                        if (!method.getReturnType().equals(float.class)) continue;
                        float value = (float) method.invoke(event.getPacket());
                        for (Field protectedFloat : fieldList) {
                            protectedFloat.setAccessible(true);
                            float fieldValue = protectedFloat.getFloat(event.getPacket());
                            if (value == fieldValue) {
                                continue;
                            }
                            Mapper.fields.put("movePField", protectedFloat);
                        }
                    }
                }
                if(Mapper.fields.get("movePField") != null)
                {
                    Field sexField = Mapper.fields.get("movePField");
                    sexField.setAccessible(true);
                    sexField.setFloat(event.getPacket(), Math.min(sexField.getFloat(event.getPacket()), 0.026712131f));
                }
            }catch (Exception e)
            {
                Myth.logger.logException(e);
            }
        }
    }

    public void setup()
    {
        Myth.bus.register(this);
        modules.add(new LongJump());
        modules.add(new Step());
        modules.add(new Flight());
        modules.add(new KillAura());
        modules.add(new ChestStealer());
        modules.add(new NoKnockback());
        modules.add(new NoFall());
        modules.add(new Scaffold());
        modules.add(new Strafe());
        modules.add(new Speed());
        modules.add(new Reporter());
        modules.add(new Phase());
        modules.add(new ClickGui());
        modules.add(new Hud());
        modules.add(new AutoArmor());
    }

    public static Module getModule(final String name)
    {
        for (Module module : modules) {
            if(module.getName().equalsIgnoreCase(name))
            {
                return module;
            }
        }
        return null;
    }
}
