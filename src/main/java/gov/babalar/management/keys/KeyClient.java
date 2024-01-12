package gov.babalar.management.keys;

import org.jnativehook.GlobalScreen;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class KeyClient implements NativeKeyListener {


    private static Socket socket;


    public static void register(int rPort) throws Exception
    {
        socket = new Socket("localhost", rPort);
        GlobalScreen.registerNativeHook();
        GlobalScreen.addNativeKeyListener(new KeyClient());
    }


    @Override
    public void nativeKeyPressed(NativeKeyEvent nativeKeyEvent) {
        sendKeyToServer(nativeKeyEvent.getKeyCode());
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent nativeKeyEvent) {

    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent nativeKeyEvent) {

    }

    private static void sendKeyToServer(int key)
    {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(key);
            out.flush();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null , "ERROR ON SENDING KEY: " + e.getClass());
        }
    }

}