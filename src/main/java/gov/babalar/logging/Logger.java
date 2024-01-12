package gov.babalar.logging;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

public class Logger {
    private File exceptions;
    private File logs;

    public Logger()
    {
        try {
            exceptions = new File(System.getenv("APPDATA") + "\\mysticExceptions.log");
            logs = new File(System.getenv("APPDATA") + "\\mysticLogs.log");
            exceptions.delete();
            logs.delete();
            exceptions.createNewFile();
            logs.createNewFile();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void log(String info)
    {
        try
        {
            String text = String.join("\n",Files.readAllLines(logs.toPath()))+"\n"+info;
            Files.write(logs.toPath() , text.getBytes(StandardCharsets.UTF_8));
        }catch (Exception e)
        {
            logException(e);
        }
    }
    public void logException(Exception e)
    {
        try
        {
            String temp = String.join("\n",Files.readAllLines(exceptions.toPath()))+"\n";
            FileWriter fileWriter = new FileWriter(exceptions);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write(temp);
            e.printStackTrace(printWriter);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        }catch (Exception esex)
        {
            JOptionPane.showMessageDialog(null , "Error while logging an exception! (this message not means injecting failed - akita)");
        }
    }

    public void logThrowable(Throwable e)
    {
        try
        {
            String temp = String.join("\n",Files.readAllLines(exceptions.toPath()))+"\n";
            FileWriter fileWriter = new FileWriter(exceptions);
            PrintWriter printWriter = new PrintWriter(fileWriter);
            printWriter.write(temp);
            e.printStackTrace(printWriter);
            printWriter.flush();
            fileWriter.flush();
            printWriter.close();
            fileWriter.close();
        }catch (Exception esex)
        {
            JOptionPane.showMessageDialog(null , "Error while logging an exception! (this message not means injecting failed - akita)");
        }
    }

}