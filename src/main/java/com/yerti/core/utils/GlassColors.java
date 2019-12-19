package com.yerti.core.utils;

/**
 * Enum for storing all of the data values of glass colors
 * Used in {@link org.bukkit.inventory.ItemStack} with {@link org.bukkit.Material#STAINED_GLASS} or {@link org.bukkit.Material#STAINED_GLASS_PANE} as a material
 */
public enum GlassColors {

    WHITE(0), ORANGE(1), MAGENTA(2), LIGHT_BLUE(3), YELLOW(4), LIME(5), PINK(6), GRAY(7), LIGHT_GRAY(8), CYAN(9), PURPLE(10), BLUE(11), BROWN(12), GREEN(13), RED(14), BLACK(15);

    /**
     * Data value of the enum
     */
    public int data;

    GlassColors(int data) {
        this.data = data;
    }
}
