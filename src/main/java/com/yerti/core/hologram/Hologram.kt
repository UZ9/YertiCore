package com.yerti.core.hologram

import com.yerti.core.utils.ChatUtils.translate
import org.bukkit.Location
import org.bukkit.block.Block
import org.bukkit.entity.ArmorStand

class Hologram {
    constructor(block: Block, name: String?) {
        val stand = getArmorStand(block.location.clone().add(.5, 2.0, .5), true)
        stand!!.customName = translate(name!!)
    }

    constructor(location: Location, name: String?) {
        val stand = getArmorStand(location, true)
        stand!!.customName = translate(name!!)
    }

    fun getArmorStand(loc: Location, create: Boolean): ArmorStand? {
        for (entity in loc.chunk.entities) {
            if (entity is ArmorStand && entity.getCustomName() != null && entity.getLocation().distanceSquared(loc) < 0.4) return entity
        }
        return if (!create) null else create(loc)
    }

    fun create(loc: Location): ArmorStand {
        val stand = loc.world.spawn(loc, ArmorStand::class.java)
        stand.isVisible = false
        stand.isMarker = true
        stand.setGravity(false)
        stand.setBasePlate(false)
        stand.isCustomNameVisible = true
        stand.removeWhenFarAway = false
        return stand
    }
}