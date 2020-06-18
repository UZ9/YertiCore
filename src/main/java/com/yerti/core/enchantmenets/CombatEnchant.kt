package com.yerti.core.enchantmenets

import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.event.entity.EntityDamageByEntityEvent
import org.bukkit.inventory.ItemStack
import java.util.function.Consumer

class CombatEnchant(private val name: String, private val maxLevel: Int, private val startLevel: Int, id: Int, private val consumer: Consumer<EntityDamageByEntityEvent>) : Enchantment(id) {
    override fun getName(): String {
        return name
    }

    override fun getMaxLevel(): Int {
        return maxLevel
    }

    override fun getStartLevel(): Int {
        return startLevel
    }

    override fun getItemTarget(): EnchantmentTarget? {
        return null
    }

    override fun conflictsWith(enchantment: Enchantment): Boolean {
        return false
    }

    override fun canEnchantItem(itemStack: ItemStack): Boolean {
        return false
    }

    fun sendEvent(t: EntityDamageByEntityEvent) {
        consumer.accept(t)
    }

}