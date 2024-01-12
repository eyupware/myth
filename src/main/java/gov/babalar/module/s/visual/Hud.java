package gov.babalar.module.s.visual;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.management.ModuleManager;
import gov.babalar.mapping.s.DefineMapping;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.prefs.Preferences;

/**
 * ----------
 * 9/16/2023
 * 9:40 PM
 * ----------
 **/
public class Hud extends Module {
    public Hud() {
        super("Hud", Category.VISUAL, 0);
        setEnabled(true);
        Myth.bus.register(this);
    }
    public static Object drawObject = null;

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
        try {
            if(drawObject == null) {
                new Thread(() -> {
                    while (drawObject == null) {
                        try {
                            drawObject = DefineMapping.env.get("MythOverlay").getDeclaredConstructors()[0].newInstance(Mapper.getMinecraft());
                        } catch (Exception e) {
                            try {
                                Thread.sleep(5000);
                            } catch (InterruptedException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    }
                }).start();
            }
            if(drawObject == null)
                return;
            MainMapping.map.get("Minecraft").getFields().get("guiInGame").set(Mapper.getMinecraft(), drawObject);
            ArrayList<String> modules = new ArrayList<>();
            for (Module module : ModuleManager.modules) {
                if(module.isEnabled())
                    modules.add(module.getName());
            }
            modules.sort((o1, o2) -> o2.length() - o1.length());
            Preferences.userRoot().put("modules" , String.join("%%%",modules.toArray(new String[0])));
            //MainMapping.map.get("GuiInGame").getFields().get("SoDrawer").set(MainMapping.map.get("Minecraft").getFields().get("guiInGame").get(Mapper.getMinecraft()) , drawObject);
        } catch (Exception e) {
            Myth.logger.logException(e);
        }
    }
}