package com.yerti.core.enchantmenets

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageByEntityEvent
import java.util.*

class EnchantmentHandler : Listener {
    @EventHandler
    fun onHit(event: EntityDamageByEntityEvent) {
        if (event.damager is Player) {
            val player = event.damager as Player
            val stack = player.itemInHand
            for (enchant in enchants) {
                if (stack.containsEnchantment(enchant)) {
                    enchant.sendEvent(event)
                }
            }
        }
    }

    companion object {
        var enchants: List<CombatEnchant> = ArrayList()
    }
}