package gov.babalar.fordefine;

import gov.babalar.notes.MapThisClass;
import gov.babalar.notes.MapThisMethod;
import gov.babalar.utils.render.RenderUtil;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

/**
 * ----------
 * 9/16/2023
 * 9:33 PM
 * ----------
 **/
@MapThisClass(className = "MythGui" , superClass = "%GuiScreen%")
public class Gui {
    @MapThisMethod(methodName = "#GuiScreen0drawScreen#" , descriptor = "(IIF)V")
    public void drawMethod(int mouseX, int mouseY , float partialTicks)
    {
        drawRect2(250, 40, 85, 190, -1526726656);
    }


    public void drawRect2(double x, double y, double width, double height, int color) {
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glDisable(GL_TEXTURE_2D);
        glPushMatrix();
        glBegin(GL_QUADS);
        this.color(color, (float) (color >> 24 & 0xFF) / 255.0f);
        glVertex2d(x, y);
        glVertex2d(x, y + height);
        glVertex2d(x + width, y + height);
        glVertex2d(x + width, y);
        glEnd();
        glPopMatrix();
        glEnable(GL_TEXTURE_2D);
        glDisable(GL_BLEND);
        glColor4f(1.0f,  1.0f,  1.0f, 1.0f);
    }

    public void color(int color, float alpha) {
        float r = (float) (color >> 16 & 0xFF) / 255.0f;
        float g = (float) (color >> 8 & 0xFF) / 255.0f;
        float b = (float) (color & 0xFF) / 255.0f;
        GL11.glColor4f(r,  g,  b,  alpha);
    }
}
