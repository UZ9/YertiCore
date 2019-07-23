package com.yerti.core.entity;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;

public class CustomEntityUtilities {

    public static void setHelmet(LivingEntity e, ItemStack helmet) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setHelmet(helmet);
    }

    public static void setChestplate(LivingEntity e, ItemStack chestplate) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setChestplate(chestplate);
    }

    public static void setLeggings(LivingEntity e, ItemStack leggings) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setChestplate(leggings);
    }

    public static void setBoots(LivingEntity e, ItemStack boots) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setChestplate(boots);
    }

    public static void setArmor(LivingEntity e, ItemStack[] armor) {

        for (int i = 0; i < 4; i++) {
            EntityEquipment entityEquipment = e.getEquipment();
            entityEquipment.setArmorContents(armor);
        }

    }

    public static void setHand(LivingEntity e, ItemStack item) {
        EntityEquipment entityEquipment = e.getEquipment();
        entityEquipment.setItemInHand(item);
    }

}
