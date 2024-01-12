package gov.babalar.module.s.visual;

import gov.babalar.Myth;
import gov.babalar.helpers.Mapper;
import gov.babalar.mapping.s.DefineMapping;
import gov.babalar.module.Category;
import gov.babalar.module.Module;
import org.jnativehook.keyboard.NativeKeyEvent;

import java.util.Objects;

/**
 * ----------
 * 9/16/2023
 * 9:40 PM
 * ----------
 **/
public class ClickGui extends Module {
    public ClickGui() {
        super("ClickGui", Category.VISUAL, NativeKeyEvent.VC_P);
    }

    @Override
    public void onEnable() {
        try {
            Mapper.setCurrentScreen(DefineMapping.env.get("MythGui").newInstance());
            toggle();
        }catch (Exception e) {
            Myth.logger.logException(e);
        }
    }

    @Override
    public void onDisable() {

    }

    @Override
    public void run() {
    }
}
