package engine.tools;

import org.joml.Vector3f;
import org.joml.Vector4f;

import java.awt.*;

public class RoffColor {
    private int red, green, blue, alpha;
    public static RoffColor WHITE = RoffColor.from(Color.WHITE);
    private static Vector3f color = new Vector3f();
    private static Vector4f colorA = new Vector4f();
    public RoffColor(int red, int green, int blue, int alpha) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
    }

    public static RoffColor from(Color color) {
        return new RoffColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public void setRed(int red) {
        this.red = red;
    }

    public void setAlpha(int alpha) {
        this.alpha = alpha;
    }

    public void setGreen(int green) {
        this.green = green;
    }


    public void setBlue(int blue) {
        this.blue = blue;
    }

    public float getNormalizedRed() {
        return red / 255f;
    }

    public float getNormalizedBlue() {
        return blue / 255f;
    }

    public float getNormalizedGreen() {
        return green / 255f;
    }

    public float getNormalizedAlpha() {
        return alpha / 255f;
    }

    public Vector3f toVec3() {
        color.x = red / 255f;
        color.y = green / 255f;
        color.z = blue / 255f;
        return color;
    }

    public void set(RoffColor color) {
        this.red = color.getRed();
        this.green = color.getGreen();
        this.blue = color.getBlue();
        this.alpha = color.getAlpha();
    }

    public Vector4f toVec4() {
        colorA.x = red / 255f;
        colorA.y = green / 255f;
        colorA.z = blue / 255f;
        colorA.w = alpha / 255f;
        return colorA;
    }
}