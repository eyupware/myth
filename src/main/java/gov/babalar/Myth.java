package gov.babalar;

import com.google.common.eventbus.EventBus;
import gov.babalar.classdefiner.ClassDefiner;
import gov.babalar.fordefine.Gui;
import gov.babalar.fordefine.RenderOverlay;
import gov.babalar.logging.Logger;
import gov.babalar.management.ModuleManager;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Module;
import gov.babalar.utils.ClassUtils;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * ----------
 * 9/4/2023
 * 7:36 PM
 * ----------
 **/
public class Myth {

    public static Thread[] mainThreads = new Thread[2];

    public static Logger logger;
    public static Class<?>[] classes;
    public static HashMap<Integer, Object> CLIENTBOUND_packets = new HashMap<>();
    public static HashMap<Integer, Object> SERVERBOUND_packets = new HashMap<>();

    public static String VERSION = "v0.8";

    public static final EventBus bus = new EventBus();



    public static void init()
    {
        logger = new Logger();
        try
        {
            setup();
            start();
        }catch (Exception e)
        {
            logger.logException(e);
        }
    }

    public static void setup() throws Exception
    {
        logger.log("Setupping myth...");
        MainMapping.generate();
        ModuleManager.INSTANCE.setup();
        try {
            ClassDefiner.defineDesignedClass(Gui.class);
            ClassDefiner.defineDesignedClass(RenderOverlay.class);
        }catch (Exception e) {
            Myth.logger.logException(e);
        }
        logger.log("Setted up myth...");
    }

    public static void start() throws Exception
    {
        (Myth.mainThreads[1] = new Thread(() -> {
            while (!Myth.mainThreads[1].isInterrupted()) {
                try {
                    for (Module module : ModuleManager.modules) {
                        if(module.isEnabled())
                            module.run();
                    }
                    Thread.sleep(10);
                }catch (Exception e) {
                    logger.logException(e);
                }
            }
        }, "Module Tick Thread")).start();
    }



}
