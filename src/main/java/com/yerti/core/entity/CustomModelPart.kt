package com.yerti.core.entity

import org.bukkit.Location
import org.bukkit.entity.ArmorStand
import org.bukkit.inventory.ItemStack

class CustomModelPart(var location: Location) {
    var armorStand: ArmorStand
    fun arms(armValue: Boolean): CustomModelPart {
        armorStand.setArms(armValue)
        return this
    }

    fun basePlate(baseValue: Boolean): CustomModelPart {
        armorStand.setBasePlate(baseValue)
        return this
    }

    fun gravity(baseValue: Boolean): CustomModelPart {
        armorStand.setGravity(baseValue)
        return this
    }

    fun visibility(baseValue: Boolean): CustomModelPart {
        armorStand.isVisible = baseValue
        return this
    }

    fun small(value: Boolean): CustomModelPart {
        armorStand.isSmall = value
        return this
    }

    fun material(stack: ItemStack?): CustomModelPart {
        armorStand.helmet = stack
        return this
    }

    init {
        armorStand = location.world.spawn(location, ArmorStand::class.java)
        armorStand.canPickupItems = false
        armorStand.customName = "CustomModelPart"
        visibility(false)
    }
}