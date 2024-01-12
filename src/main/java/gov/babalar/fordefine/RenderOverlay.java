package gov.babalar.fordefine;

import gov.babalar.mapping.components.MappingClazz;
import gov.babalar.mapping.s.MainMapping;
import gov.babalar.notes.AddAsMethod;
import gov.babalar.notes.MapThisClass;
import org.lwjgl.opengl.GL11;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.prefs.Preferences;

import static org.lwjgl.opengl.GL11.*;

/**
 * ----------
 * 9/17/2023
 * 9:23 PM
 * ----------
 **/
@MapThisClass(className = "MythOverlay" , superClass = "%GuiInGame%")
public class RenderOverlay {

    //@MapThisMethod(methodName = "#GuiInGame0renderOverlay#" , descriptor = "(F)V")
    public void drawMethod(float partialTicks)
    {
        try {
            GL11.glPushMatrix();
            drawRect2(2 , 1 , 155 , 14 , 0x90000000);
            SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
            Date date = new Date();
            drawString(String.format("§s§l§oM§f§l§oyth §8| §fgg/mythso §8| §7[§f%s§7]" , formatter.format(date)), 11 , 5 , -1, true);
            GL11.glPopMatrix();
            String[] modules = Preferences.userRoot().get("modules" , "").split("%%%");
            for(int i = 0; i < modules.length; i++)
            {
                drawString(modules[i] , 5 , 22 + (i * 10) , -1, true);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public static void drawString(String s , float x , float y , int color, boolean shadow)
    {

    }
    public void drawRect2(double x, double y, double width, double height, int color) {
        int radius1 = 6;
        int radius2 = 6;
        int radius3 = 6;
        int radius4 = 6;
        double x2 = x + width;
        double  y2 = y + height;
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);

        GL11.glPushMatrix();
        GL11.glBegin(9);
        this.color(color, (float) (color >> 24 & 0xFF) / 255.0f);
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x + radius1 + Math.sin(i * 3.141592653589793 / 180.0) * (radius1 * -1.0), y + radius1 + Math.cos(i * 3.141592653589793 / 180.0) * (radius1 * -1.0));
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x + radius2 + Math.sin(i * 3.141592653589793 / 180.0) * (radius2 * -1.0), y2 - radius2 + Math.cos(i * 3.141592653589793 / 180.0) * (radius2 * -1.0));
        }
        for (int i = 0; i <= 90; i += 1) {
            GL11.glVertex2d(x2 - radius3 + Math.sin(i * 3.141592653589793 / 180.0) * radius3, y2 - radius3 + Math.cos(i * 3.141592653589793 / 180.0) * radius3);
        }
        for (int i = 90; i <= 180; i += 1) {
            GL11.glVertex2d(x2 - radius4 + Math.sin(i * 3.141592653589793 / 180.0) * radius4, y + radius4 + Math.cos(i * 3.141592653589793 / 180.0) * radius4);
        }
        GL11.glEnd();
        GL11.glPopMatrix();

        glEnable(GL_TEXTURE_2D);
        glEnable(GL_BLEND);
        glDisable(GL_LINE_SMOOTH);
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);

    }

    public void color(int color, float alpha) {
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glColor4f(r,  g,  b,  alpha);
    }
    @AddAsMethod
    public static MethodNode renderGameOverlay()
    {
        MappingClazz GuiIngameClazz = MainMapping.map.get("GuiInGame");
        String guiIG = GuiIngameClazz.getClazz().getName().replace('.' , '/');
        String MythOverlayClassName  = RenderOverlay.class.getName().replace('.', '/');
        String renderOverlayName = GuiIngameClazz.getMethods().get("renderOverlay").getName();
        MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC, renderOverlayName, String.format("(%s)V" ,"F") , null, null);
        methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD , 0));
        methodNode.instructions.add(new VarInsnNode(Opcodes.FLOAD , 1));
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL , guiIG , renderOverlayName , String.format("(%s)V" , "F") , false));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD , 0));
        methodNode.instructions.add(new VarInsnNode(Opcodes.FLOAD , 1));
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL , MythOverlayClassName , "drawMethod" , String.format("(%s)V" , "F") , false));
        methodNode.instructions.add(new InsnNode(Opcodes.RETURN));
        return methodNode;
    }
    @AddAsMethod
    public static MethodNode drawStringBuilder()
    {
        /*String fontRenderer = MainMapping.map.get("FontRenderer").getClazz().getName().replace('.' , '/');
        String minecraft = MainMapping.map.get("Minecraft").getClazz().getName().replace('.' , '/');
        String getMinecraft = MainMapping.map.get("Minecraft").getMethods().get("getMinecraft").getName();
        String fontRendererObj = MainMapping.map.get("Minecraft").getFields().get("fontRendererObj").getName();
        String drawString = MainMapping.map.get("FontRenderer").getMethods().get("drawString").getName();
        MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "drawString", "(Ljava/lang/String;III)V" , null, null);
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC , minecraft , getMinecraft , String.format("()L%s;" , minecraft)));
        methodNode.instructions.add(new FieldInsnNode(Opcodes.GETFIELD , minecraft , fontRendererObj , String.format("L%s;" , fontRenderer)));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD , 0));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ILOAD , 1));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ILOAD , 2));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ILOAD , 3));
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL , fontRenderer , drawString, "(Ljava/lang/String;III)I"));
        methodNode.instructions.add(new InsnNode(Opcodes.POP));
        methodNode.instructions.add(new InsnNode(Opcodes.RETURN));*/
        String fontRenderer = MainMapping.map.get("FontRenderer").getClazz().getName().replace('.' , '/');
        String minecraft = MainMapping.map.get("Minecraft").getClazz().getName().replace('.' , '/');
        String getMinecraft = MainMapping.map.get("Minecraft").getMethods().get("getMinecraft").getName();
        String fontRendererObj = MainMapping.map.get("Minecraft").getFields().get("fontRendererObj").getName();
        String drawString = MainMapping.map.get("FontRenderer").getMethods().get("drawStringWithShadow").getName();
        MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC | Opcodes.ACC_STATIC, "drawString", "(Ljava/lang/String;FFIZ)V" , null, null);
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKESTATIC , minecraft , getMinecraft , String.format("()L%s;" , minecraft)));
        methodNode.instructions.add(new FieldInsnNode(Opcodes.GETFIELD , minecraft , fontRendererObj , String.format("L%s;" , fontRenderer)));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD , 0));//string
        methodNode.instructions.add(new VarInsnNode(Opcodes.FLOAD , 1));//float
        methodNode.instructions.add(new VarInsnNode(Opcodes.FLOAD , 2));//float
        methodNode.instructions.add(new VarInsnNode(Opcodes.ILOAD , 3));//color - int
        methodNode.instructions.add(new VarInsnNode(Opcodes.ILOAD , 4));//boolean - int
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKEVIRTUAL , fontRenderer , drawString, "(Ljava/lang/String;FFIZ)I"));
        methodNode.instructions.add(new InsnNode(Opcodes.POP));
        methodNode.instructions.add(new InsnNode(Opcodes.RETURN));
        return methodNode;
    }
    @AddAsMethod
    public static MethodNode buildInit()
    {
        String guiIG = MainMapping.map.get("GuiInGame").getClazz().getName().replace('.' , '/');
        String minecraft = MainMapping.map.get("Minecraft").getClazz().getName().replace('.' , '/');
        MethodNode methodNode = new MethodNode(Opcodes.ACC_PUBLIC, "<init>", String.format("(L%s;)V" ,minecraft) , null, null);
        methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD , 0));
        methodNode.instructions.add(new VarInsnNode(Opcodes.ALOAD , 1));
        methodNode.instructions.add(new MethodInsnNode(Opcodes.INVOKESPECIAL , guiIG , "<init>" , String.format("(L%s;)V" , minecraft) , false));
        methodNode.instructions.add(new InsnNode(Opcodes.RETURN));
        return methodNode;
    }


}