package com.yerti.core.items;

/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2020 Crypto Morin
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR
 * PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE
 * FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
 * ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */



/*
 * References
 *
 * * * GitHub: https://github.com/CryptoMorin/XSeries/blob/master/XBlock.java
 * * XSeries: https://www.spigotmc.org/threads/378136/
 * BlockState (Old): https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/BlockState.html
 * BlockData (New): https://hub.spigotmc.org/javadocs/spigot/org/bukkit/block/data/BlockData.html
 * MaterialData (Old): https://hub.spigotmc.org/javadocs/spigot/org/bukkit/material/MaterialData.html
 */

import com.yerti.core.block.BlockUtils;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.MaterialData;

import java.util.EnumSet;
import java.util.List;

/**
 * <b>XBlock</b> - MaterialData/BlockData Support<br>
 * Supports 1.8+ - Requires XMaterial
 *
 * @author Crypto Morin
 * @version 1.0.0
 * @see Block
 * @see BlockState
 * @see MaterialData
 * @see XMaterial
 */
@SuppressWarnings("deprecation")
public final class XBlock {
    public static final EnumSet<XMaterial> CROPS = EnumSet.of(
            XMaterial.CARROT, XMaterial.POTATO, XMaterial.NETHER_WART, XMaterial.WHEAT_SEEDS, XMaterial.PUMPKIN_SEEDS,
            XMaterial.MELON_SEEDS, XMaterial.BEETROOT_SEEDS, XMaterial.SUGAR_CANE, XMaterial.BAMBOO_SAPLING, XMaterial.CHORUS_PLANT,
            XMaterial.KELP, XMaterial.SEA_PICKLE, XMaterial.BROWN_MUSHROOM, XMaterial.RED_MUSHROOM
    );
    public static final EnumSet<XMaterial> DANGEROUS = EnumSet.of(
            XMaterial.MAGMA_BLOCK, XMaterial.LAVA, XMaterial.CAMPFIRE, XMaterial.FIRE
    );
    public static final int CAKE_SLICES = 6;
    private static final boolean ISFLAT = XMaterial.isNewVersion();

    /**
     * Checks if the block is a container.
     * Containers are chests, hoppers, enderchests and everything that
     * has an inventory.
     *
     * @param block the block to check.
     * @return true if the block is a container, otherwise false.
     */
    public static boolean isContainer(Block block) {
        return block.getState() instanceof InventoryHolder;
    }


    /**
     * Any material that can be planted.
     */
    public static boolean isCrops(Material material) {
        return CROPS.contains(XMaterial.matchXMaterial(material));
    }

    /**
     * Any material that can damage the player.
     */
    public static boolean isDangerous(Block block) {
        return DANGEROUS.contains(XMaterial.matchXMaterial(block.getType()));
    }

    public static boolean isCake(Material material) {
        return ISFLAT ? material == Material.CAKE : material.name().equals("CAKE_BLOCK");
    }

    public static boolean isWheat(Material material) {
        return ISFLAT ? material == Material.WHEAT : material.name().equals("CROPS");
    }

    public static boolean isSugarCane(Material material) {
        return ISFLAT ? material == Material.SUGAR_CANE : material.name().equals("SUGAR_CANE_BLOCK");
    }

    public static boolean isBeetroot(Material material) {
        return ISFLAT ? material == Material.SUGAR_CANE : material.name().equals("BEETROOT_BLOCK");
    }

    public static boolean isNetherWart(Material material) {
        return ISFLAT ? material == Material.NETHER_WARTS : material.name().equals("NETHER_WARTS");
    }

    public static boolean isCarrot(Material material) {
        return ISFLAT ? material.name().equals("CARROTS") : material == Material.CARROT;
    }

    public static boolean isPotato(Material material) {
        return ISFLAT ? material.name().equals("POTATOES") : material == Material.POTATO;
    }


    public static void setColorWool(Block block, DyeColor color) {
        BlockUtils.setBlockSuperFast(block, 35, color.getData(), false);
    }

    public static void setBlock(Block block, XMaterial material) {
        BlockUtils.setBlockSuperFast(block, material.getId(), material.getData(), false);
    }

    public static boolean isWater(Material material) {
        String name = material.name();
        return name.equals("WATER") || name.equals("STATIONARY_WATER");
    }

    public static boolean isOneOf(Block block, List<String> blocks) {
        return XMaterial.isOneOf(block.getType(), blocks);
    }

    /**
     * <b>Universal Method</b>
     * <p>
     * Check if the block type matches the specified XMaterial
     *
     * @param block    the block to check.
     * @param material the XMaterial similar to this block type.
     * @return true if the block type is similar to the material.
     */
    public static boolean isType(Block block, XMaterial material) {
        Material mat = block.getType();

        switch (material) {
            case CAKE:
                return isCake(mat);
            case NETHER_WART:
                return isNetherWart(mat);
            case CARROT:
            case CARROTS:
                return isCarrot(mat);
            case POTATO:
            case POTATOES:
                return isPotato(mat);
            case WHEAT:
            case WHEAT_SEEDS:
                return isWheat(mat);
            case BEETROOT:
            case BEETROOT_SEEDS:
            case BEETROOTS:
                return isBeetroot(mat);
            case SUGAR_CANE:
                return isSugarCane(mat);
            case WATER:
                return isWater(mat);
            case AIR:
                return isAir(mat);
        }

        return false;
    }

    public static boolean isAir(Material material) {
        // Only air material names end with "IR"
        return material.name().endsWith("IR");
    }


    private static boolean isMaterial(Block block, String... materials) {
        String type = block.getType().name();
        for (String material : materials)
            if (type.equals(material)) return true;
        return false;
    }
}