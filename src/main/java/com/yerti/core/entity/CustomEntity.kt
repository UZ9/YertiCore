package com.yerti.core.entity

import org.bukkit.entity.LivingEntity
import org.bukkit.inventory.ItemStack
import org.bukkit.potion.PotionEffect

/**
 * Custom Entity modifier off of a living entity
 */
class CustomEntity(private val entity: LivingEntity, armor: Array<ItemStack?>?, hand: ItemStack?, health: Int, effects: Array<PotionEffect?>?) {

    /**
     *
     * @param entity a living entity
     * @param armor the armor to put on (leave null if no armor)
     * @param hand item to be held in hand (leave null if nothing in hand)
     * @param health health for the mob (up to 1024)
     * @param effects potion effects for the mob (leave null if nothing)
     */
    init {
        if (armor != null) entity.equipment.armorContents = armor
        if (hand != null) entity.equipment.itemInHand = hand
        if (health != 0) {
            entity.maxHealth = health.toDouble()
            entity.health = health.toDouble()
        }
        if (effects != null) {
            for (effect in effects) {
                entity.addPotionEffect(effect)
            }
        }
    }
}