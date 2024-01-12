package gov.babalar;

import gov.babalar.management.keys.KeyListener;
import gov.babalar.management.keys.KeyClient;
import gov.babalar.utils.ClassUtils;
import gov.babalar.utils.math.MathHelper;

import javax.swing.*;
import java.util.Random;

/**
 * ----------
 * 9/4/2023
 * 7:35 PM
 * ----------
 **/
public class Main {

    public static ClassLoader currClassLoader;

    /*
    static {
        yuh();
    }*/


    public static void main(String[] args) {
        try {
            if(args.length == 2)
            {
                if(args[0].equals("socket"))
                {
                    KeyClient.register(Integer.parseInt(args[1]));
                    return;
                }
            }
            int rPort = MathHelper.getRandomIntegerInRange(new Random(), 5555, 8888);
            yuh();
            KeyListener.register(rPort);
            Runtime.getRuntime().exec("cmd /c java -jar C:/myth.jar socket " + rPort);
        }catch(Exception e)
        {
            if(Myth.logger != null)
            {
                Myth.logger.logException(e);
            } else {
                JOptionPane.showMessageDialog(null , "ERROR ON START MYTH: " + e.getClass());
            }
        }
    }


    public static void yuh()
    {
        currClassLoader = ClassUtils.currentThreadClassLoader();
        (Myth.mainThreads[0] = new Thread(() ->{
            try {
                Myth.classes = ClassUtils.getClasses(currClassLoader, false).toArray(new Class[0]);
                Myth.init();
            }catch (Exception e) {
                JOptionPane.showMessageDialog(null , "FAILED TO INIT MYTH: " + e.getClass());
            }
        }, "Myth Thread")).start();
    }


}
