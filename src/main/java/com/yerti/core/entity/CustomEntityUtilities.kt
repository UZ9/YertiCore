package com.yerti.core.entity

import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack

object CustomEntityUtilities {
    /**
     * Sets the helmet for a living entity
     * @param e
     * @param helmet
     */
    fun setHelmet(e: LivingEntity, helmet: ItemStack?) {
        val entityEquipment = e.equipment
        entityEquipment.helmet = helmet
    }

    /**
     * Sets the chestplate for a living entity
     * @param e
     * @param chestplate
     */
    fun setChestplate(e: LivingEntity, chestplate: ItemStack?) {
        val entityEquipment = e.equipment
        entityEquipment.chestplate = chestplate
    }

    /**
     * Sets the leggings for a living entity
     * @param e
     * @param leggings
     */
    fun setLeggings(e: LivingEntity, leggings: ItemStack?) {
        val entityEquipment = e.equipment
        entityEquipment.chestplate = leggings
    }

    /**
     * Sets the boots for a living entity
     * @param e
     * @param boots
     */
    fun setBoots(e: LivingEntity, boots: ItemStack?) {
        val entityEquipment = e.equipment
        entityEquipment.chestplate = boots
    }

    /**
     * Sets the armor contents for a living entity
     * @param e
     * @param armor
     */
    fun setArmor(e: LivingEntity, armor: Array<ItemStack?>?) {
        val entityEquipment = e.equipment
        entityEquipment.armorContents = armor
    }

    /**
     * Sets the hand for a living entity
     * Only for 1.8
     * @param e
     * @param item
     */
    fun setHand(e: LivingEntity, item: ItemStack?) {
        val entityEquipment = e.equipment
        entityEquipment.itemInHand = item
    }
}