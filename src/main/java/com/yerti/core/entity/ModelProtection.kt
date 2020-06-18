package com.yerti.core.entity

import org.bukkit.entity.ArmorStand
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerInteractAtEntityEvent

class ModelProtection : Listener {
    @EventHandler
    fun onEntityDamage(event: EntityDamageEvent) {
        if (event.entity is ArmorStand) {
            val stand = event.entity as ArmorStand
            if (stand.customName == "CustomModelPart") {
                println("Cancelling")
                event.isCancelled = true
            }
        }
    }

    @EventHandler
    fun onEntityInteract(event: PlayerInteractAtEntityEvent) {
        if (event.rightClicked is ArmorStand) {
            val stand = event.rightClicked as ArmorStand
            if (stand.customName == "CustomModelPart") {
                event.isCancelled = true
            }
        }
    }
}