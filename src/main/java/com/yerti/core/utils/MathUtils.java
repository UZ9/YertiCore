package com.yerti.core.utils;

public class MathUtils {

    /**
     * Clamps a value based off of a min and max
     * @param value
     * @param min
     * @param max
     * @return clamped value
     */
    public static int clamp(int value, int min, int max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }

    /**
     * Clamps a value based off of a min and max
     * @param value
     * @param min
     * @param max
     * @return clamped value
     */
    public static long clamp(long value, long min, long max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }

    /**
     * Clamps a value based off of a min and max
     * @param value
     * @param min
     * @param max
     * @return clamped value
     */
    public static float clamp(float value, float min, float max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }

    /**
     * Clamps a value based off of a min and max
     * @param value
     * @param min
     * @param max
     * @return clamped value
     */
    public static double clamp(double value, double min, double max) {
        if (value > max) return max;
        if (value < min) return min;
        return value;
    }




}
