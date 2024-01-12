package gov.babalar.management.keys;

import gov.babalar.Myth;
import gov.babalar.event.events.KeyEvent;
import gov.babalar.helpers.Mapper;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class KeyListener {



    public static void register(int rPort)
    {
        new Thread(() -> {
            try(ServerSocket serverSocket = new ServerSocket(rPort))
            {
                while(!Myth.mainThreads[0].isInterrupted()) {
                    Socket socket = serverSocket.accept();
                    new Thread(() -> {
                        while(true)
                        {
                            try {
                                DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
                                int key = dataInputStream.readInt();
                                if (Mapper.getCurrentScreen() == null) {
                                    KeyEvent keyEvent = new KeyEvent(key);
                                    Myth.bus.post(keyEvent);
                                }
                            }catch (Exception e)
                            {
                                Myth.logger.logException(e);
                            }
                        }
                    }).start();
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null , "ERROR ON LISTENING KEY: " + e.getClass() + " - " + e.getMessage());
                Myth.logger.logException(e);
            }
        }).start();
    }


}
