package com.yerti.core.utils;

/**
 * Enum for storing all of the data values of dye colors
 * Used in {@link org.bukkit.inventory.ItemStack} with {@link org.bukkit.Material#INK_SACK} as a material
 */
public enum DyeColors {

    BLACK(0), RED(1), GREEN(2), BROWN(3), BLUE(4), PURPLE(5), CYAN(6), LIGHT_GRAY(7), GRAY(8), PINK(9), LIME(10), YELLOW(11), LIGHT_BLUE(12), MAGENTA(13), ORAGNE(14), WHITE(15);

    /**
     * Data value of the enum
     */
    public int data;

    DyeColors(int data) {
        this.data = data;
    }





}
