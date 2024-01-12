package gov.babalar.utils.render;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;

public class GLUtil {

	public static void render(int mode, Runnable render) {
		glBegin(mode);
		render.run();
		glEnd();
	}

	public static void startBlend() {
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
	}

	public static void endBlend() {
		glDisable(GL_BLEND);
	}

	public static void setup2DRendering(Runnable f) {
		startBlend();
		glDisable(GL_TEXTURE_2D);
		f.run();
		glEnable(GL_TEXTURE_2D);
		endBlend();
	}

	public static void setupDrawString(Runnable f) {
		glPushMatrix();
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		f.run();
		glDisable(GL_BLEND);
		glPopMatrix();
	}

}
