package com.yerti.core.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class CustomEntityUtilities {

    /**
     * Sets the helmet for a living entity
     *
     * @param e
     * @param helmet
     */
    public static void setHelmet(LivingEntity e, ItemStack helmet) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setHelmet(helmet);
    }

    /**
     * Sets the chestplate for a living entity
     *
     * @param e
     * @param chestplate
     */
    public static void setChestplate(LivingEntity e, ItemStack chestplate) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setChestplate(chestplate);
    }

    /**
     * Sets the leggings for a living entity
     *
     * @param e
     * @param leggings
     */
    public static void setLeggings(LivingEntity e, ItemStack leggings) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setChestplate(leggings);
    }

    /**
     * Sets the boots for a living entity
     *
     * @param e
     * @param boots
     */
    public static void setBoots(LivingEntity e, ItemStack boots) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setChestplate(boots);
    }

    /**
     * Sets the armor contents for a living entity
     *
     * @param e
     * @param armor
     */
    public static void setArmor(LivingEntity e, ItemStack[] armor) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setArmorContents(armor);
    }

    /**
     * Sets the hand for a living entity
     * Only for 1.8
     *
     * @param e
     * @param item
     */
    public static void setHand(LivingEntity e, ItemStack item) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setItemInHand(item);
    }

}
