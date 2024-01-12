package gov.babalar.utils.render;

import static gov.babalar.utils.math.MathHelper.*;

import java.awt.Color;

public class ColorUtil 
{
	public static int applyOpacity(int color, float opacity) {
		Color old = new Color(color);
		return applyOpacity(old, opacity).getRGB();
	}

	public static Color applyOpacity(Color color, float opacity) {
		opacity = Math.min(1.0f, Math.max(0.0f, opacity));
		return new Color(color.getRed(), color.getGreen(), color.getBlue(), (int) ((float) color.getAlpha() * opacity));
	}

	public static Color brighter(Color color, float FACTOR) {
		int r = color.getRed();
		int g = color.getGreen();
		int b = color.getBlue();
		int alpha = color.getAlpha();
		int i = (int) (1.0 / (1.0 - FACTOR));
		if (r == 0 && g == 0 && b == 0) {
			return new Color(i, i, i, alpha);
		}
		if (r > 0 && r < i) r = i;
		if (g > 0 && g < i) g = i;
		if (b > 0 && b < i) b = i;

		return new Color(Math.min((int) (r / FACTOR), 255),
				Math.min((int) (g / FACTOR), 255),
				Math.min((int) (b / FACTOR), 255),
				alpha);
	}

	public static Color interpolateColorsBackAndForth(int speed, int index, Color start, Color end, boolean trueColor) {
		int angle = (int) (((System.currentTimeMillis()) / speed + index) % 360);
		angle = (angle >= 180 ? 360 - angle : angle) * 2;
		return trueColor ? ColorUtil.interpolateColorHue(start, end, angle / 360f) : ColorUtil.interpolateColorC(start, end, angle / 360f);
	}

	public static Color interpolateColorHue(Color color1, Color color2, float amount) {
		amount = Math.min(1, Math.max(0, amount));

		float[] color1HSB = Color.RGBtoHSB(color1.getRed(), color1.getGreen(), color1.getBlue(), null);
		float[] color2HSB = Color.RGBtoHSB(color2.getRed(), color2.getGreen(), color2.getBlue(), null);

		Color resultColor = Color.getHSBColor(interpolateFloat(color1HSB[0], color2HSB[0], amount),
				interpolateFloat(color1HSB[1], color2HSB[1], amount), interpolateFloat(color1HSB[2], color2HSB[2], amount));

		return ColorUtil.applyOpacity(resultColor, interpolateInt(color1.getAlpha(), color2.getAlpha(), amount) / 255f);
	}

    public static Color interpolateColorC(Color color1, Color color2, float amount) {
        amount = Math.min(1, Math.max(0, amount));
        return new Color(interpolateInt(color1.getRed(), color2.getRed(), amount),
                interpolateInt(color1.getGreen(), color2.getGreen(), amount),
                interpolateInt(color1.getBlue(), color2.getBlue(), amount),
                interpolateInt(color1.getAlpha(), color2.getAlpha(), amount));
    }
	
}
