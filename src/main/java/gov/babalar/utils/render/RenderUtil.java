package gov.babalar.utils.render;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.FloatBuffer;

import org.lwjgl.Sys;
import org.lwjgl.opengl.GL11;

import static org.lwjgl.opengl.GL11.*;

public class RenderUtil {
	public long last2DFrame = System.currentTimeMillis();
	public long last3DFrame = System.currentTimeMillis();
	public float delta2DFrameTime;
	public float delta3DFrameTime;

	public static void glRenderStart() {
		GL11.glPushMatrix();
		GL11.glPushAttrib(1048575);
		GL11.glEnable(3042);
		glDisable(2884);
		glDisable(3553);
	}



	public static void circleNoSmoothRGB(double x, double y, double radius, int color) {
		radius /= 2;
		glEnable(GL_BLEND);
		glDisable(GL_TEXTURE_2D);
		glDisable(GL_CULL_FACE);
		color(color);
		glBegin(GL_TRIANGLE_FAN);

		for (double i = 0; i <= 360; i++) {
			double angle = (i * (Math.PI * 2)) / 360;
			glVertex2d(x + (radius * Math.cos(angle)) + radius, y + (radius * Math.sin(angle)) + radius);
		}

		glEnd();
		glEnable(GL_CULL_FACE);
		glEnable(GL_TEXTURE_2D);
	}

	public static void glRenderStop() {
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2884);
		glDisable((int) 3042);
		GL11.glPopAttrib();
		GL11.glPopMatrix();
	}
	public static float convertColor(int count, int color) {
		return (float) (color >> count & 0xFF) / 255.0f;
	}

	public static void setColor(Color c) {
		GL11.glColor4d((double) ((float) c.getRed() / 255.0f), (double) ((float) c.getGreen() / 255.0f),
				(double) ((float) c.getBlue() / 255.0f), (double) ((float) c.getAlpha() / 255.0f));
	}

	/*
	public static void drawGradientRound(float x, float y, float width, float height, float radius, Color bottomLeft, Color topLeft, Color bottomRight, Color topRight) {
		RenderUtil.setAlphaLimit(0);
		RenderUtil.resetColor();
		GLUtil.startBlend();
		roundedGradientShader.init();
		setupRoundedRectUniforms(x, y, width, height, radius, roundedGradientShader);
		//Top left
		roundedGradientShader.setUniformf("color1", topLeft.getRed() / 255f, topLeft.getGreen() / 255f, topLeft.getBlue() / 255f, topLeft.getAlpha() / 255f);
		// Bottom Left
		roundedGradientShader.setUniformf("color2", bottomLeft.getRed() / 255f, bottomLeft.getGreen() / 255f, bottomLeft.getBlue() / 255f, bottomLeft.getAlpha() / 255f);
		//Top Right
		roundedGradientShader.setUniformf("color3", topRight.getRed() / 255f, topRight.getGreen() / 255f, topRight.getBlue() / 255f, topRight.getAlpha() / 255f);
		//Bottom Right
		roundedGradientShader.setUniformf("color4", bottomRight.getRed() / 255f, bottomRight.getGreen() / 255f, bottomRight.getBlue() / 255f, bottomRight.getAlpha() / 255f);
		ShaderUtil.drawQuads(x - 1, y - 1, width + 2, height + 2);
		roundedGradientShader.unload();
		GLUtil.endBlend();
	}*/

	public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
		float f = (float) (col1 >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (col1 >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (col1 >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (col1 & 0xFF) / 255.0f;
		float f4 = (float) (col2 >> 24 & 0xFF) / 255.0f;
		float f5 = (float) (col2 >> 16 & 0xFF) / 255.0f;
		float f6 = (float) (col2 >> 8 & 0xFF) / 255.0f;
		float f7 = (float) (col2 & 0xFF) / 255.0f;
		RenderUtil.glRenderStart();
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glShadeModel((int) 7425);
		GL11.glPushMatrix();
		GL11.glBegin((int) 7);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glColor4f((float) f5, (float) f6, (float) f7, (float) f4);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
		glDisable((int) 2848);
		GL11.glShadeModel((int) 7424);
		GL11.glColor4d((double) 1.0, (double) 1.0, (double) 1.0, (double) 1.0);
		RenderUtil.glRenderStop();
	}

	public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
		float f = (float) (col1 >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (col1 >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (col1 >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (col1 & 0xFF) / 255.0f;
		float f4 = (float) (col2 >> 24 & 0xFF) / 255.0f;
		float f5 = (float) (col2 >> 16 & 0xFF) / 255.0f;
		float f6 = (float) (col2 >> 8 & 0xFF) / 255.0f;
		float f7 = (float) (col2 & 0xFF) / 255.0f;
		RenderUtil.glRenderStart();
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		GL11.glShadeModel((int) 7425);
		GL11.glPushMatrix();
		GL11.glBegin((int) 7);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glVertex2d((double) left, (double) top);
		GL11.glVertex2d((double) left, (double) bottom);
		GL11.glColor4f((float) f5, (float) f6, (float) f7, (float) f4);
		GL11.glVertex2d((double) right, (double) bottom);
		GL11.glVertex2d((double) right, (double) top);
		GL11.glEnd();
		GL11.glPopMatrix();
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
		glDisable((int) 2848);
		GL11.glShadeModel((int) 7424);
		RenderUtil.glRenderStop();
	}

	public static void drawRect(double x, double y, double d, double e, int color) {
		float f = (float) (color >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (color & 0xFF) / 255.0f;
		RenderUtil.glRenderStart();
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 7);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) d, (double) y);
		GL11.glVertex2d((double) d, (double) e);
		GL11.glVertex2d((double) x, (double) e);
		GL11.glEnd();
		RenderUtil.glRenderStop();
	}

	public static void drawBorderedRect(float xPos, float yPos, float width, float height, float lineWidth,
			int lineColor, int bgColor) {
		RenderUtil.drawRect(xPos, yPos, width, height, bgColor);
		float f = (float) (lineColor >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (lineColor >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (lineColor >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (lineColor & 0xFF) / 255.0f;
		RenderUtil.glRenderStart();
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glLineWidth((float) lineWidth);
		GL11.glEnable((int) 2848);
		GL11.glBegin((int) 1);
		GL11.glVertex2d((double) xPos, (double) yPos);
		GL11.glVertex2d((double) width, (double) yPos);
		GL11.glVertex2d((double) width, (double) yPos);
		GL11.glVertex2d((double) width, (double) height);
		GL11.glVertex2d((double) width, (double) height);
		GL11.glVertex2d((double) xPos, (double) height);
		GL11.glVertex2d((double) xPos, (double) height);
		GL11.glVertex2d((double) xPos, (double) yPos);
		GL11.glEnd();
		RenderUtil.glRenderStop();
	}

	public static void drawOctagon(float xPos, float yPos, float width, float height, float length, float angle,
			int color) {
		float f = RenderUtil.convertColor(24, color);
		float f1 = RenderUtil.convertColor(16, color);
		float f2 = RenderUtil.convertColor(8, color);
		float f3 = RenderUtil.convertColor(0, color);
		RenderUtil.glRenderStart();
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 9);
		GL11.glVertex2d((double) (xPos + length), (double) yPos);
		GL11.glVertex2d((double) (xPos + width - length), (double) yPos);
		GL11.glVertex2d((double) (xPos + width - length), (double) yPos);
		GL11.glVertex2d((double) (xPos + width), (double) (yPos + height / 2.0f - angle));
		GL11.glVertex2d((double) (xPos + width), (double) (yPos + height / 2.0f - angle));
		GL11.glVertex2d((double) (xPos + width), (double) (yPos + height / 2.0f + angle));
		GL11.glVertex2d((double) (xPos + width), (double) (yPos + height / 2.0f + angle));
		GL11.glVertex2d((double) (xPos + width - length), (double) (yPos + height));
		GL11.glVertex2d((double) (xPos + width - length), (double) (yPos + height));
		GL11.glVertex2d((double) (xPos + length), (double) (yPos + height));
		GL11.glVertex2d((double) (xPos + length), (double) (yPos + height));
		GL11.glVertex2d((double) xPos, (double) (yPos + height / 2.0f + angle));
		GL11.glVertex2d((double) xPos, (double) (yPos + height / 2.0f + angle));
		GL11.glVertex2d((double) xPos, (double) (yPos + height / 2.0f - angle));
		GL11.glVertex2d((double) xPos, (double) (yPos + height / 2.0f - angle));
		GL11.glVertex2d((double) (xPos + length), (double) yPos);
		GL11.glEnd();
		RenderUtil.glRenderStop();
	}

	public static void drawBorderedCircle(float x, float y, float radius, int lineWidth, int outsideC, int insideC) {
		RenderUtil.drawCircle(x, y, radius, insideC);
		RenderUtil.drawUnfilledCircle(x, y, radius, lineWidth, outsideC);
	}

	public static void drawCircle228(float x, float y, float radius, int lineWidth, int outsideC, int insideC,
			int jopaSlona) {
		RenderUtil.drawCircle228(x, y, radius, insideC, jopaSlona);
	}

	public static void drawUnfilledCircle228(float x, float y, float radius, float lineWidth, int color,
			int jopaSlona) {
		float f = (float) (color >> 16 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f2 = (float) (color & 0xFF) / 255.0f;
		float f3 = (float) (color >> 24 & 0xFF) / 255.0f;
		GL11.glEnable((int) 2848);
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDepthMask((boolean) true);
		GL11.glEnable((int) 2848);
		GL11.glHint((int) 3154, (int) 4354);
		GL11.glHint((int) 3155, (int) 4354);
		RenderUtil.enableBlend();
		GL11.glColor4f((float) f, (float) f1, (float) f2, (float) f3);
		GL11.glLineWidth((float) lineWidth);
		GL11.glBegin((int) 2);
		int i = 0;
		while (i <= jopaSlona) {
			GL11.glVertex2d((double) ((double) x + Math.sin((double) i * Math.PI / 180.0) * (double) radius),
					(double) ((double) y + Math.cos((double) i * Math.PI / 180.0) * (double) radius));
			++i;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
		glDisable((int) 2848);
		GL11.glHint((int) 3154, (int) 4352);
		GL11.glHint((int) 3155, (int) 4352);
		RenderUtil.disableBlend();
	}

	public static void drawUnfilledCircle(float x, float y, float radius, float lineWidth, int color) {
		float f = (float) (color >> 16 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f2 = (float) (color & 0xFF) / 255.0f;
		float f3 = (float) (color >> 24 & 0xFF) / 255.0f;
		GL11.glEnable((int) 2848);
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glDepthMask((boolean) true);
		GL11.glEnable((int) 2848);
		GL11.glHint((int) 3154, (int) 4354);
		GL11.glHint((int) 3155, (int) 4354);
		RenderUtil.enableBlend();
		GL11.glColor4f((float) f, (float) f1, (float) f2, (float) f3);
		GL11.glLineWidth((float) lineWidth);
		GL11.glBegin((int) 2);
		int i = 0;
		while (i <= 360) {
			GL11.glVertex2d((double) ((double) x + Math.sin((double) i * Math.PI / 180.0) * (double) radius),
					(double) ((double) y + Math.cos((double) i * Math.PI / 180.0) * (double) radius));
			++i;
		}
		GL11.glEnd();
		GL11.glScalef((float) 2.0f, (float) 2.0f, (float) 2.0f);
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
		glDisable((int) 2848);
		GL11.glHint((int) 3154, (int) 4352);
		GL11.glHint((int) 3155, (int) 4352);
		RenderUtil.disableBlend();
	}

	public static void drawRect2(double x, double y, double width, double height, int color) {
		GLUtil.setup2DRendering(() -> GLUtil.render(GL11.GL_QUADS, () -> {
			RenderUtil.color(color);
			GL11.glVertex2d(x, y);
			GL11.glVertex2d(x, y + height);
			GL11.glVertex2d(x + width, y + height);
			GL11.glVertex2d(x + width, y);
		}));
		RenderUtil.resetColor();
	}

	public static void drawCircle228(float x, float y, float radius, int color, int jopaSlona) {
		float f = (float) (color >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (color & 0xFF) / 255.0f;
		boolean flag = GL11.glIsEnabled((int) 3042);
		boolean flag1 = GL11.glIsEnabled((int) 2848);
		boolean flag2 = GL11.glIsEnabled((int) 3553);
		if (!flag) {
			GL11.glEnable((int) 3042);
		}
		if (!flag1) {
			GL11.glEnable((int) 2848);
		}
		if (flag2) {
			glDisable((int) 3553);
		}
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glLineWidth((float) 2.5f);
		GL11.glBegin((int) 3);
		int i = 0;
		while (i <= jopaSlona) {
			GL11.glVertex2d((double) ((double) x + Math.sin((double) i * Math.PI / 180.0) * (double) radius),
					(double) ((double) y + Math.cos((double) i * Math.PI / 180.0) * (double) radius));
			++i;
		}
		GL11.glEnd();
		glDisable((int) 2848);
		if (flag2) {
			GL11.glEnable((int) 3553);
		}
		if (!flag1) {
			glDisable((int) 2848);
		}
		if (!flag) {
			glDisable((int) 3042);
		}
	}

	public static void drawCircle(float x, float y, float radius, int color) {
		float f = (float) (color >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (color >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (color >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (color & 0xFF) / 255.0f;
		boolean flag = GL11.glIsEnabled((int) 3042);
		boolean flag1 = GL11.glIsEnabled((int) 2848);
		boolean flag2 = GL11.glIsEnabled((int) 3553);
		if (!flag) {
			GL11.glEnable((int) 3042);
		}
		if (!flag1) {
			GL11.glEnable((int) 2848);
		}
		if (flag2) {
			glDisable((int) 3553);
		}
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 9);
		int i = 0;
		while (i <= 360) {
			GL11.glVertex2d((double) ((double) x + Math.sin((double) i * Math.PI / 180.0) * (double) radius),
					(double) ((double) y + Math.cos((double) i * Math.PI / 180.0) * (double) radius));
			++i;
		}
		GL11.glEnd();
		glDisable((int) 2848);
		if (flag2) {
			GL11.glEnable((int) 3553);
		}
		if (!flag1) {
			glDisable((int) 2848);
		}
		if (!flag) {
			glDisable((int) 3042);
		}
	}

	public static void enableScissoring() {
		GL11.glEnable((int) 3089);
	}

	public static void disableScissoring() {
		glDisable((int) 3089);
	}

	public static void pushAttrib() {
		GL11.glPushAttrib((int) 8256);
	}

	public static void popAttrib() {
		GL11.glPopAttrib();
	}

	public static void color(int color, float alpha) {
		float r = (float) (color >> 16 & 0xFF) / 255.0f;
		float g = (float) (color >> 8 & 0xFF) / 255.0f;
		float b = (float) (color & 0xFF) / 255.0f;
		GL11.glColor4f((float) r, (float) g, (float) b, (float) alpha);
	}

	public static void color(int color) {
		RenderUtil.color(color, (float) (color >> 24 & 0xFF) / 255.0f);
	}

	public static void resetColor() {
		GL11.glColor4f((float) 1.0f, (float) 1.0f, (float) 1.0f, (float) 1.0f);
	}

	public static void clear(int mask) {
		GL11.glClear((int) mask);
	}

	public static void matrixMode(int mode) {
		GL11.glMatrixMode((int) mode);
	}

	public static void loadIdentity() {
		GL11.glLoadIdentity();
	}

	public static void pushMatrix() {
		GL11.glPushMatrix();
	}

	public static void popMatrix() {
		GL11.glPopMatrix();
	}

	public static void getFloat(int pname, FloatBuffer params) {
		GL11.glGetFloat(pname);
	}

	public static void ortho(double left, double right, double bottom, double top, double zNear, double zFar) {
		GL11.glOrtho((double) left, (double) right, (double) bottom, (double) top, (double) zNear, (double) zFar);
	}

	public static void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef((float) angle, (float) x, (float) y, (float) z);
	}

	public static void scale(float x, float y, float z) {
		GL11.glScalef((float) x, (float) y, (float) z);
	}

	public static void scale(float x, float y, float scale, Runnable data) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GL11.glScalef(scale, scale, 1);
		GL11.glTranslatef(-x, -y, 0);
		data.run();
		GL11.glPopMatrix();
	}

	public static Color rainbow(int speed, int index, float saturation, float brightness, float opacity) {
		int angle = (int) ((System.currentTimeMillis() / (long) speed + (long) index) % 360L);
		float hue = (float) angle / 360.0f;
		Color color = new Color(Color.HSBtoRGB(hue, saturation, brightness));
		return new Color(color.getRed(), color.getGreen(), color.getBlue(),
				Math.max(0, Math.min(255, (int) (opacity * 255.0f))));
	}

	public static void scale(double x, double y, double z) {
		GL11.glScaled((double) x, (double) y, (double) z);
	}

	public static void translate(float x, float y, float z) {
		GL11.glTranslatef((float) x, (float) y, (float) z);
	}

	public static void translate(double x, double y, double z) {
		GL11.glTranslated((double) x, (double) y, (double) z);
	}

	public static void bindTexture(int texture) {
		GL11.glBindTexture((int) 3553, (int) texture);
	}

	public static void depthMask(boolean flagIn) {
		GL11.glDepthMask((boolean) flagIn);
	}

	public static void disableBlend() {
		glDisable((int) 3042);
	}


	public static void scaleStart(float x, float y, float scale) {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0.0f);
		GL11.glScalef(scale, scale, 1.0f);
		GL11.glTranslatef(-x, -y, 0.0f);
	}

	public static void scaleEnd() {
		GL11.glPopMatrix();
	}
	public static void enableBlend() {
		GL11.glEnable((int) 3042);
	}

	public static void disableAlpha() {
		glDisable((int) 3008);
	}

	public static void enableAlpha() {
		GL11.glEnable((int) 3008);
	}

	public static void disableTexture2D() {
		glDisable((int) 3553);
	}

	public static void enableTexture2D() {
		GL11.glEnable((int) 3553);
	}

	public static void enableLighting() {
		GL11.glEnable((int) 2896);
	}

	public static void disableLighting() {
		glDisable((int) 2896);
	}

	public static void blendFunc(int srcFactor, int dstFactor) {
		GL11.glBlendFunc((int) srcFactor, (int) dstFactor);
	}

	public static void setAlphaLimit(float limit) {
		GL11.glEnable((int) 3008);
		GL11.glAlphaFunc((int) 516, (float) ((float) ((double) limit * 0.01)));
	}

	public static long getSystemTime() {
		return Sys.getTime() * 1000L / Sys.getTimerResolution();
	}

	public static int clamp(int num, int min, int max) {
		return num < min ? min : Math.min(num, max);
	}

	public static float clamp(float num, float min, float max) {
		return num < min ? min : Math.min(num, max);
	}

	public static int getRandomInRange(int min, int max) {
		return (int) (Math.random() * (double) (max - min) + (double) min);
	}

	public static double clamp(double num, double min, double max) {
		return num < min ? min : Math.min(num, max);
	}

	public static double getNormalDouble(double d, int numberAfterZopyataya) {
		return new BigDecimal(d).setScale(numberAfterZopyataya, RoundingMode.HALF_EVEN).doubleValue();
	}

	public static double getNormalDouble(double d) {
		return new BigDecimal(d).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
	}

	public static void push() {
		GL11.glPushMatrix();
	}

	public static void pop() {
		GL11.glPopMatrix();
	}

	public static void enable(int glTarget) {
		GL11.glEnable((int) glTarget);
	}

	public static void disable(int glTarget) {
		glDisable((int) glTarget);
	}

	public static void start() {
		RenderUtil.enable(3042);
		GL11.glBlendFunc((int) 770, (int) 771);
		RenderUtil.disable(3553);
		RenderUtil.disable(2884);
		RenderUtil.disableAlpha();
		RenderUtil.disable(2929);
	}

	public static void stop() {
		RenderUtil.enableAlpha();
		RenderUtil.enable(2929);
		RenderUtil.enable(2884);
		RenderUtil.enable(3553);
		RenderUtil.disable(3042);
		RenderUtil.color(Color.white);
	}

	public static void startSmooth() {
		RenderUtil.enable(2881);
		RenderUtil.enable(2848);
		RenderUtil.enable(2832);
	}

	public static void endSmooth() {
		RenderUtil.disable(2832);
		RenderUtil.disable(2848);
		RenderUtil.disable(2881);
	}

	public static void begin(int glMode) {
		GL11.glBegin((int) glMode);
	}

	public static void end() {
		GL11.glEnd();
	}

	public static void vertex(double x, double y) {
		GL11.glVertex2d((double) x, (double) y);
	}

	public static void translate(double x, double y) {
		GL11.glTranslated((double) x, (double) y, (double) 0.0);
	}

	public static void scale(double x, double y) {
		GL11.glScaled((double) x, (double) y, (double) 1.0);
	}

	public static void rotate(double x, double y, double z, double angle) {
		GL11.glRotated((double) angle, (double) x, (double) y, (double) z);
	}

	public static void color(double red, double green, double blue, double alpha) {
		GL11.glColor4d((double) red, (double) green, (double) blue, (double) alpha);
	}

	public static void color(double red, double green, double blue) {
		RenderUtil.color(red, green, blue, 1.0);
	}

	public static void color(Color color) {
		if (color == null) {
			color = Color.white;
		}
		RenderUtil.color((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f,
				(float) color.getBlue() / 255.0f, (float) color.getAlpha() / 255.0f);
	}

	public static void color(Color color, int alpha) {
		if (color == null) {
			color = Color.white;
		}
		RenderUtil.color((float) color.getRed() / 255.0f, (float) color.getGreen() / 255.0f,
				(float) color.getBlue() / 255.0f, 0.5);
	}

	public static void lineWidth(double width) {
		GL11.glLineWidth((float) ((float) width));
	}

	public static void rect(double x, double y, double width, double height, boolean filled, Color color) {
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		RenderUtil.begin(filled ? 6 : 1);
		RenderUtil.vertex(x, y);
		RenderUtil.vertex(x + width, y);
		RenderUtil.vertex(x + width, y + height);
		RenderUtil.vertex(x, y + height);
		if (!filled) {
			RenderUtil.vertex(x, y);
			RenderUtil.vertex(x, y + height);
			RenderUtil.vertex(x + width, y);
			RenderUtil.vertex(x + width, y + height);
		}
		RenderUtil.end();
		RenderUtil.stop();
	}

	public static void rect(double x, double y, double width, double height, boolean filled) {
		RenderUtil.rect(x, y, width, height, filled, null);
	}

	public static void rect(double x, double y, double width, double height, Color color) {
		RenderUtil.rect(x, y, width, height, true, color);
	}

	public static void rect(double x, double y, double width, double height) {
		RenderUtil.rect(x, y, width, height, true, null);
	}

	public static void rectCentered(double x, double y, double width, double height, boolean filled, Color color) {
		RenderUtil.rect(x -= width / 2.0, y -= height / 2.0, width, height, filled, color);
	}

	public static void rectCentered(double x, double y, double width, double height, boolean filled) {
		RenderUtil.rect(x -= width / 2.0, y -= height / 2.0, width, height, filled, null);
	}

	public static void rectCentered(double x, double y, double width, double height, Color color) {
		RenderUtil.rect(x -= width / 2.0, y -= height / 2.0, width, height, true, color);
	}

	public static void rectCentered(double x, double y, double width, double height) {
		RenderUtil.rect(x -= width / 2.0, y -= height / 2.0, width, height, true, null);
	}

	public static void gradient(double x, double y, double width, double height, boolean filled, Color color1,
			Color color2) {
		RenderUtil.start();
		GL11.glShadeModel((int) 7425);
		RenderUtil.enableAlpha();
		GL11.glAlphaFunc((int) 516, (float) 0.0f);
		if (color1 != null) {
			RenderUtil.color(color1);
		}
		RenderUtil.begin(filled ? 7 : 1);
		RenderUtil.vertex(x, y);
		RenderUtil.vertex(x + width, y);
		if (color2 != null) {
			RenderUtil.color(color2);
		}
		RenderUtil.vertex(x + width, y + height);
		RenderUtil.vertex(x, y + height);
		if (!filled) {
			RenderUtil.vertex(x, y);
			RenderUtil.vertex(x, y + height);
			RenderUtil.vertex(x + width, y);
			RenderUtil.vertex(x + width, y + height);
		}
		RenderUtil.end();
		GL11.glAlphaFunc((int) 516, (float) 0.1f);
		RenderUtil.disableAlpha();
		GL11.glShadeModel((int) 7424);
		RenderUtil.stop();
	}

	public static void gradient(double x, double y, double width, double height, Color color1, Color color2) {
		RenderUtil.gradient(x, y, width, height, true, color1, color2);
	}

	public static void gradientCentered(double x, double y, double width, double height, Color color1, Color color2) {
		RenderUtil.gradient(x -= width / 2.0, y -= height / 2.0, width, height, true, color1, color2);
	}

	public static void gradientSideways(double x, double y, double width, double height, boolean filled, Color color1,
			Color color2) {
		RenderUtil.start();
		GL11.glShadeModel((int) 7425);
		RenderUtil.disableAlpha();
		if (color1 != null) {
			RenderUtil.color(color1);
		}
		RenderUtil.begin(filled ? 6 : 1);
		RenderUtil.vertex(x, y);
		RenderUtil.vertex(x, y + height);
		if (color2 != null) {
			RenderUtil.color(color2);
		}
		RenderUtil.vertex(x + width, y + height);
		RenderUtil.vertex(x + width, y);
		RenderUtil.end();
		RenderUtil.enableAlpha();
		GL11.glShadeModel((int) 7424);
		RenderUtil.stop();
	}

	public static void gradientSideways(double x, double y, double width, double height, Color color1, Color color2) {
		RenderUtil.gradientSideways(x, y, width, height, true, color1, color2);
	}

	public static void gradientSidewaysCentered(double x, double y, double width, double height, Color color1,
			Color color2) {
		RenderUtil.gradientSideways(x -= width / 2.0, y -= height / 2.0, width, height, true, color1, color2);
	}

	public static void polygon(double x, double y, double sideLength, double amountOfSides, boolean filled,
			Color color) {
		sideLength /= 2.0;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		if (!filled) {
			GL11.glLineWidth((float) 2.0f);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(filled ? 6 : 3);
		double i = 0.0;
		while (i <= amountOfSides / 4.0) {
			double angle = i * 4.0 * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + sideLength * Math.cos(angle) + sideLength,
					y + sideLength * Math.sin(angle) + sideLength);
			i += 1.0;
		}
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
	}

	public static void polygon(double x, double y, double sideLength, int amountOfSides, boolean filled) {
		RenderUtil.polygon(x, y, sideLength, amountOfSides, filled, null);
	}

	public static void polygon(double x, double y, double sideLength, int amountOfSides, Color color) {
		RenderUtil.polygon(x, y, sideLength, amountOfSides, true, color);
	}

	public static void polygon(double x, double y, double sideLength, int amountOfSides) {
		RenderUtil.polygon(x, y, sideLength, amountOfSides, true, null);
	}

	public static void polygonCentered(double x, double y, double sideLength, int amountOfSides, boolean filled,
			Color color) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, amountOfSides, filled, color);
	}

	public static void polygonCentered(double x, double y, double sideLength, int amountOfSides, boolean filled) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, amountOfSides, filled, null);
	}

	public static void polygonCentered(double x, double y, double sideLength, int amountOfSides, Color color) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, amountOfSides, true, color);
	}

	public static void polygonCentered(double x, double y, double sideLength, int amountOfSides) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, amountOfSides, true, null);
	}

	public static void circle(double x, double y, double radius, boolean filled, Color color) {
		RenderUtil.polygon(x, y, radius, 360.0, filled, color);
	}

	public static void circle(double x, double y, double radius, boolean filled) {
		RenderUtil.polygon(x, y, radius, 360, filled);
	}

	public static void circle(double x, double y, double radius, Color color) {
		RenderUtil.polygon(x, y, radius, 360, color);
	}

	public static void circle(double x, double y, double radius) {
		RenderUtil.polygon(x, y, radius, 360);
	}

	public static void circleCentered(double x, double y, double radius, boolean filled, Color color) {
		RenderUtil.polygon(x -= radius / 2.0, y -= radius / 2.0, radius, 360.0, filled, color);
	}

	public static void circleCentered(double x, double y, double radius, boolean filled) {
		RenderUtil.polygon(x -= radius / 2.0, y -= radius / 2.0, radius, 360, filled);
	}

	public static void circleCentered(double x, double y, double radius, boolean filled, int sides) {
		RenderUtil.polygon(x -= radius / 2.0, y -= radius / 2.0, radius, sides, filled);
	}

	public static void circleCentered(double x, double y, double radius, Color color) {
		RenderUtil.polygon(x -= radius / 2.0, y -= radius / 2.0, radius, 360, color);
	}

	public static void circleCentered(double x, double y, double radius) {
		RenderUtil.polygon(x -= radius / 2.0, y -= radius / 2.0, radius, 360);
	}

	public static void triangle(double x, double y, double sideLength, boolean filled, Color color) {
		RenderUtil.polygon(x, y, sideLength, 3.0, filled, color);
	}

	public static void triangle(double x, double y, double sideLength, boolean filled) {
		RenderUtil.polygon(x, y, sideLength, 3, filled);
	}

	public static void triangle(double x, double y, double sideLength, Color color) {
		RenderUtil.polygon(x, y, sideLength, 3, color);
	}

	public static void triangle(double x, double y, double sideLength) {
		RenderUtil.polygon(x, y, sideLength, 3);
	}

	public static void triangleCentered(double x, double y, double sideLength, boolean filled, Color color) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, 3.0, filled, color);
	}

	public static void triangleCentered(double x, double y, double sideLength, boolean filled) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, 3, filled);
	}

	public static void triangleCentered(double x, double y, double sideLength, Color color) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, 3, color);
	}

	public static void triangleCentered(double x, double y, double sideLength) {
		RenderUtil.polygon(x -= sideLength / 2.0, y -= sideLength / 2.0, sideLength, 3);
	}

	public static void lineNoGl(double firstX, double firstY, double secondX, double secondY, Color color) {
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		RenderUtil.lineWidth(1.0f);
		GL11.glEnable((int) 2848);
		RenderUtil.begin(1);
		RenderUtil.vertex(firstX, firstY);
		RenderUtil.vertex(secondX, secondY);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
	}

	public static void line(double firstX, double firstY, double secondX, double secondY, double lineWidth,
			Color color) {
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		RenderUtil.lineWidth(lineWidth);
		GL11.glEnable((int) 2848);
		RenderUtil.begin(1);
		RenderUtil.vertex(firstX, firstY);
		RenderUtil.vertex(secondX, secondY);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
	}

	public static void line(double firstX, double firstY, double secondX, double secondY, double lineWidth) {
		RenderUtil.line(firstX, firstY, secondX, secondY, lineWidth, null);
	}

	public static void line(double firstX, double firstY, double secondX, double secondY, Color color) {
		RenderUtil.line(firstX, firstY, secondX, secondY, 0.0, color);
	}

	public static void line(double firstX, double firstY, double secondX, double secondY) {
		RenderUtil.line(firstX, firstY, secondX, secondY, 0.0, null);
	}

	public static void outlineInlinedGradientRect(double x, double y, double width, double height, double inlineOffset,
			Color color1, Color color2) {
		RenderUtil.gradient(x, y, width, inlineOffset, color1, color2);
		RenderUtil.gradient(x, y + height - inlineOffset, width, inlineOffset, color2, color1);
		RenderUtil.gradientSideways(x, y, inlineOffset, height, color1, color2);
		RenderUtil.gradientSideways(x + width - inlineOffset, y, inlineOffset, height, color2, color1);
	}

	public static void roundedRect(double x, double y, double width, double height, double edgeRadius, Color color) {
		double angle;
		double halfRadius = edgeRadius / 2.0;
		width -= halfRadius;
		height -= halfRadius;
		float sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		RenderUtil.begin(6);
		double i = 180.0;
		while (i <= 270.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + (double) sideLength * Math.cos(angle) + (double) sideLength,
					y + (double) sideLength * Math.sin(angle) + (double) sideLength);
			i += 1.0;
		}
		RenderUtil.vertex(x + (double) sideLength, y + (double) sideLength);
		RenderUtil.end();
		RenderUtil.stop();
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(6);
		i = 0.0;
		while (i <= 90.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + width + (double) sideLength * Math.cos(angle),
					y + height + (double) sideLength * Math.sin(angle));
			i += 1.0;
		}
		RenderUtil.vertex(x + width, y + height);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(6);
		i = 270.0;
		while (i <= 360.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + width + (double) sideLength * Math.cos(angle),
					y + (double) sideLength * Math.sin(angle) + (double) sideLength);
			i += 1.0;
		}
		RenderUtil.vertex(x + width, y + (double) sideLength);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(6);
		i = 90.0;
		while (i <= 180.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + (double) sideLength * Math.cos(angle) + (double) sideLength,
					y + height + (double) sideLength * Math.sin(angle));
			i += 1.0;
		}
		RenderUtil.vertex(x + (double) sideLength, y + height);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
		RenderUtil.rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);
		RenderUtil.rect(x, y + halfRadius, edgeRadius / 2.0, height - halfRadius, color);
		RenderUtil.rect(x + width, y + halfRadius, edgeRadius / 2.0, height - halfRadius, color);
		RenderUtil.rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
		RenderUtil.rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
	}

	public static void roundedOutLine(double x, double y, double width, double height, double thickness,
			double edgeRadius, Color color) {
		double angle;
		double halfRadius = edgeRadius / 2.0;
		width -= halfRadius;
		height -= halfRadius;
		float sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(1);
		double i = 180.0;
		while (i <= 270.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + (double) sideLength * Math.cos(angle) + (double) sideLength,
					y + (double) sideLength * Math.sin(angle) + (double) sideLength);
			i += 1.0;
		}
		RenderUtil.vertex(x + (double) sideLength, y + (double) sideLength);
		RenderUtil.end();
		RenderUtil.stop();
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(1);
		i = 0.0;
		while (i <= 90.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + width + (double) sideLength * Math.cos(angle),
					y + height + (double) sideLength * Math.sin(angle));
			i += 1.0;
		}
		RenderUtil.vertex(x + width, y + height);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(1);
		i = 270.0;
		while (i <= 360.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + width + (double) sideLength * Math.cos(angle),
					y + (double) sideLength * Math.sin(angle) + (double) sideLength);
			i += 1.0;
		}
		RenderUtil.vertex(x + width, y + (double) sideLength);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		GL11.glEnable((int) 2848);
		RenderUtil.begin(1);
		i = 90.0;
		while (i <= 180.0) {
			angle = i * (Math.PI * 2) / 360.0;
			RenderUtil.vertex(x + (double) sideLength * Math.cos(angle) + (double) sideLength,
					y + height + (double) sideLength * Math.sin(angle));
			i += 1.0;
		}
		RenderUtil.vertex(x + (double) sideLength, y + height);
		RenderUtil.end();
		glDisable((int) 2848);
		RenderUtil.stop();
	}

	public static void roundedRectCustom(double x, double y, double width, double height, double edgeRadius,
			Color color, boolean topLeft, boolean topRight, boolean bottomLeft, boolean bottomRight) {
		double angle;
		double i;
		double halfRadius = edgeRadius / 2.0;
		width -= halfRadius;
		height -= halfRadius;
		float sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		if (topLeft) {
			GL11.glEnable((int) 2848);
			RenderUtil.begin(6);
			i = 180.0;
			while (i <= 270.0) {
				angle = i * (Math.PI * 2) / 360.0;
				RenderUtil.vertex(x + (double) sideLength * Math.cos(angle) + (double) sideLength,
						y + (double) sideLength * Math.sin(angle) + (double) sideLength);
				i += 1.0;
			}
			RenderUtil.vertex(x + (double) sideLength, y + (double) sideLength);
			RenderUtil.end();
			glDisable((int) 2848);
			RenderUtil.stop();
		} else {
			RenderUtil.rect(x, y, (double) sideLength, (double) sideLength, color);
		}
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		if (bottomRight) {
			GL11.glEnable((int) 2848);
			RenderUtil.begin(6);
			i = 0.0;
			while (i <= 90.0) {
				angle = i * (Math.PI * 2) / 360.0;
				RenderUtil.vertex(x + width + (double) sideLength * Math.cos(angle),
						y + height + (double) sideLength * Math.sin(angle));
				i += 1.0;
			}
			RenderUtil.vertex(x + width, y + height);
			RenderUtil.end();
			glDisable((int) 2848);
			RenderUtil.stop();
		} else {
			RenderUtil.rect(x + width, y + height, (double) sideLength, (double) sideLength, color);
		}
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		if (topRight) {
			GL11.glEnable((int) 2848);
			RenderUtil.begin(6);
			i = 270.0;
			while (i <= 360.0) {
				angle = i * (Math.PI * 2) / 360.0;
				RenderUtil.vertex(x + width + (double) sideLength * Math.cos(angle),
						y + (double) sideLength * Math.sin(angle) + (double) sideLength);
				i += 1.0;
			}
			RenderUtil.vertex(x + width, y + (double) sideLength);
			RenderUtil.end();
			glDisable((int) 2848);
			RenderUtil.stop();
		} else {
			RenderUtil.rect(x + width, y, (double) sideLength, (double) sideLength, color);
		}
		sideLength = (float) edgeRadius;
		sideLength /= 2.0f;
		RenderUtil.start();
		if (color != null) {
			RenderUtil.color(color);
		}
		if (bottomLeft) {
			GL11.glEnable((int) 2848);
			RenderUtil.begin(6);
			i = 90.0;
			while (i <= 180.0) {
				angle = i * (Math.PI * 2) / 360.0;
				RenderUtil.vertex(x + (double) sideLength * Math.cos(angle) + (double) sideLength,
						y + height + (double) sideLength * Math.sin(angle));
				i += 1.0;
			}
			RenderUtil.vertex(x + (double) sideLength, y + height);
			RenderUtil.end();
			glDisable((int) 2848);
			RenderUtil.stop();
		} else {
			RenderUtil.rect(x, y + height, (double) sideLength, (double) sideLength, color);
		}
		RenderUtil.rect(x + halfRadius, y + halfRadius, width - halfRadius, height - halfRadius, color);
		RenderUtil.rect(x, y + halfRadius, edgeRadius / 2.0, height - halfRadius, color);
		RenderUtil.rect(x + width, y + halfRadius, edgeRadius / 2.0, height - halfRadius, color);
		RenderUtil.rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
		RenderUtil.rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
	}

	public static void roundedRectTop(double x, double y, double width, double height, double edgeRadius, Color color) {
		double halfRadius = edgeRadius / 2.0;
		RenderUtil.circle(x, y, edgeRadius, color);
		RenderUtil.circle(x + (width -= halfRadius) - edgeRadius / 2.0, y, edgeRadius, color);
		RenderUtil.rect(x, y + halfRadius, width + halfRadius, height -= halfRadius, color);
		RenderUtil.rect(x + halfRadius, y, width - halfRadius, halfRadius, color);
	}

	public static void roundedRectBottom(double x, double y, double width, double height, double edgeRadius,
			Color color) {
		double halfRadius = edgeRadius / 2.0;
		RenderUtil.circle(x + (width -= halfRadius) - edgeRadius / 2.0, y + (height -= halfRadius) - edgeRadius / 2.0,
				edgeRadius, color);
		RenderUtil.circle(x, y + height - edgeRadius / 2.0, edgeRadius, color);
		RenderUtil.rect(x, y, width + halfRadius, height, color);
		RenderUtil.rect(x + halfRadius, y + height, width - halfRadius, halfRadius, color);
	}

	public static void roundedRectRight(double x, double y, double width, double height, double edgeRadius,
			Color color1, Color color2) {
		double halfRadius = edgeRadius / 2.0;
		RenderUtil.circle(x + (width -= halfRadius) - edgeRadius / 2.0, y, edgeRadius, color2);
		RenderUtil.circle(x + width - edgeRadius / 2.0, y + (height -= halfRadius) - edgeRadius / 2.0, edgeRadius,
				color2);
		RenderUtil.gradientSideways(x, y, width, height + halfRadius, color1, color2);
		RenderUtil.rect(x + width, y + halfRadius, 5.0, height - halfRadius, color2);
	}

	public static void roundedRectRightTop(double x, double y, double width, double height, double edgeRadius,
			Color color1, Color color2) {
		double halfRadius = edgeRadius / 2.0;
		RenderUtil.circle(x + (width -= halfRadius) - edgeRadius / 2.0, y, edgeRadius, color2);
		RenderUtil.gradientSideways(x, y, width, (height -= halfRadius) + halfRadius, color1, color2);
		RenderUtil.rect(x + width, y + halfRadius, 5.0, height, color2);
	}

	public static void roundedRectRightBottom(double x, double y, double width, double height, double edgeRadius,
			Color color1, Color color2) {
		double halfRadius = edgeRadius / 2.0;
		RenderUtil.circle(x + (width -= halfRadius) - edgeRadius / 2.0, y + (height -= halfRadius) - edgeRadius / 2.0,
				edgeRadius, color2);
		RenderUtil.gradientSideways(x, y, width, height + halfRadius, color1, color2);
		RenderUtil.rect(x + width, y, 5.0, height, color2);
	}

	public static void drawBorder(float x, float y, float x2, float y2, float width, int color1) {
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 2848);
		RenderUtil.color(new Color(color1));
		GL11.glLineWidth((float) width);
		RenderUtil.glBegin(2);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		RenderUtil.glEnd();
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
		glDisable((int) 2848);
	}

	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha,
			float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable((int) 3042);
		GL11.glEnable((int) 2848);
		glDisable((int) 2929);
		glDisable((int) 3553);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glEnable((int) 3042);
		GL11.glLineWidth((float) lineWdith);
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
		GL11.glBegin((int) 2);
		GL11.glVertex3d((double) 0.0, (double) 1.62f, (double) 0.0);
		GL11.glVertex3d((double) x, (double) y, (double) z);
		GL11.glEnd();
		glDisable((int) 3042);
		GL11.glEnable((int) 3553);
		GL11.glEnable((int) 2929);
		glDisable((int) 2848);
		glDisable((int) 3042);
		GL11.glPopMatrix();
	}

	public static void color4f(float red, float green, float blue, float alpha) {
		GL11.glColor4f((float) red, (float) green, (float) blue, (float) alpha);
	}

	public static void lineWidth(float width) {
		GL11.glLineWidth((float) width);
	}

	public static void glBegin(int mode) {
		GL11.glBegin((int) mode);
	}

	public static void glEnd() {
		GL11.glEnd();
	}

	public static void putVertex3d(double x, double y, double z) {
		GL11.glVertex3d((double) x, (double) y, (double) z);
	}

	public static void drawCircle(int x, int y, double r, float f1, float f2, float f3, float f) {
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 2);
		int i = 0;
		while (i <= 360) {
			double x2 = Math.sin((double) i * Math.PI / 180.0) * r;
			double y2 = Math.cos((double) i * Math.PI / 180.0) * r;
			GL11.glVertex2d((double) ((double) x + x2), (double) ((double) y + y2));
			++i;
		}
		GL11.glEnd();
		glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
	}

	public static void drawFilledCircle(int x, int y, double r, int c) {
		float f = (float) (c >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (c >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (c >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (c & 0xFF) / 255.0f;
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 360) {
			double x2 = Math.sin((double) i * Math.PI / 180.0) * r;
			double y2 = Math.cos((double) i * Math.PI / 180.0) * r;
			GL11.glVertex2d((double) ((double) x + x2), (double) ((double) y + y2));
			++i;
		}
		GL11.glEnd();
		glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
	}

	public static void drawFilledCircle(int x, int y, double r, int c, int quality) {
		float f = (float) (c >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (c >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (c >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (c & 0xFF) / 255.0f;
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 360 / quality) {
			double x2 = Math.sin((double) (i * quality) * Math.PI / 180.0) * r;
			double y2 = Math.cos((double) (i * quality) * Math.PI / 180.0) * r;
			GL11.glVertex2d((double) ((double) x + x2), (double) ((double) y + y2));
			++i;
		}
		GL11.glEnd();
		glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
	}

	public static void drawFilledCircle(double x, double y, double r, int c, int quality) {
		float f = (float) (c >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (c >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (c >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (c & 0xFF) / 255.0f;
		GL11.glEnable((int) 3042);
		glDisable((int) 3553);
		GL11.glEnable((int) 2848);
		GL11.glBlendFunc((int) 770, (int) 771);
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 360 / quality) {
			double x2 = Math.sin((double) (i * quality) * Math.PI / 180.0) * r;
			double y2 = Math.cos((double) (i * quality) * Math.PI / 180.0) * r;
			GL11.glVertex2d((double) (x + x2), (double) (y + y2));
			++i;
		}
		GL11.glEnd();
		glDisable((int) 2848);
		GL11.glEnable((int) 3553);
		glDisable((int) 3042);
	}

	public static void drawFilledCircleNoGL(int x, int y, double r, int c) {
		float f = (float) (c >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (c >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (c >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (c & 0xFF) / 255.0f;
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 18) {
			double x2 = Math.sin((double) (i * 20) * Math.PI / 180.0) * r;
			double y2 = Math.cos((double) (i * 20) * Math.PI / 180.0) * r;
			GL11.glVertex2d((double) ((double) x + x2), (double) ((double) y + y2));
			++i;
		}
		GL11.glEnd();
	}

	public static void drawFilledCircleNoGL(int x, int y, double r, int c, int quality) {
		float f = (float) (c >> 24 & 0xFF) / 255.0f;
		float f1 = (float) (c >> 16 & 0xFF) / 255.0f;
		float f2 = (float) (c >> 8 & 0xFF) / 255.0f;
		float f3 = (float) (c & 0xFF) / 255.0f;
		GL11.glColor4f((float) f1, (float) f2, (float) f3, (float) f);
		GL11.glBegin((int) 6);
		int i = 0;
		while (i <= 360 / quality) {
			double x2 = Math.sin((double) (i * quality) * Math.PI / 180.0) * r;
			double y2 = Math.cos((double) (i * quality) * Math.PI / 180.0) * r;
			GL11.glVertex2d((double) ((double) x + x2), (double) ((double) y + y2));
			++i;
		}
		GL11.glEnd();
	}

	public static void quickDrawRect(float x, float y, float x2, float y2, int color) {
		RenderUtil.glColor(color);
		RenderUtil.glBegin(7);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		RenderUtil.glEnd();
	}

	public static void quickDrawBorderedRect(float x, float y, float x2, float y2, float width, int color1,
			int color2) {
		RenderUtil.quickDrawRect(x, y, x2, y2, color2);
		RenderUtil.glColor(color1);
		GL11.glLineWidth((float) width);
		RenderUtil.glBegin(2);
		GL11.glVertex2d((double) x2, (double) y);
		GL11.glVertex2d((double) x, (double) y);
		GL11.glVertex2d((double) x, (double) y2);
		GL11.glVertex2d((double) x2, (double) y2);
		RenderUtil.glEnd();
	}

	private static void glColor(int hex) {
		float alpha = (float) (hex >> 24 & 0xFF) / 255.0f;
		float red = (float) (hex >> 16 & 0xFF) / 255.0f;
		float green = (float) (hex >> 8 & 0xFF) / 255.0f;
		float blue = (float) (hex & 0xFF) / 255.0f;
		RenderUtil.color(red, green, blue, alpha);
	}

	public static void drawLine(final double startX, final double startY, final double endX, final double endY,
			final double kalinlik) {
		for (double i = startX; i < endX; ++i) {
			drawRect(i, endY - kalinlik, i + 1.0, endY + kalinlik, new Color(66, 9, 235).getRGB());
		}
	}

	public static void drawRainbowLine(final double startX, final double startY, final double endX, final double endY,
			final double kalinlik) {
		for (double i = startX; i < endX; ++i) {
			drawRect(i, endY - kalinlik, i + 1.0, endY + kalinlik,
					RenderUtil.getRainbow(1500, -15 * ((int) i + 10) * 3));
		}
	}

	public static Color getThemeColor(float colorOffset, float timeMultiplier) {
		return new Color(getRainbow(1500, (int) (-15 * colorOffset * 3)));
	}

	public static int getRainbow(int speed, int offset) {
		float hue = (System.currentTimeMillis() + (long) offset) % (long) speed;
		return Color.getHSBColor(hue /= (float) speed, 0.5f, 1.0f).getRGB();
	}


	public static void rainbowRectangle(float x, float y, float width, float height, float divider) {
		int i = 0;
		while ((float) i <= width) {
			RenderUtil.rect((double) (x + (float) i), (double) y, 1.0, (double) height,
					new Color(RenderUtil.getColor((float) i / divider, 0.7f, 1.0f)));
			++i;
		}
	}

	public static int getColor(float hueoffset, float saturation, float brightness) {
		float hue = (float) (System.currentTimeMillis() % 4500L) / 4500.0f;
		return Color.HSBtoRGB(hue - hueoffset / 54.0f, saturation, brightness);
	}
	public static void endCrop() {
		glDisable(3089);
	}


}
