package com.yerti.core.utils

import com.yerti.core.config.Config
import org.bukkit.Bukkit
import org.bukkit.Location

object LocationUtils {
    @JvmStatic
    fun serializeLocation(loc: Location): String {
        return loc.world.toString() + ";;" + loc.x + ";;" + loc.y + ";;" + loc.z
    }

    fun saveLocation(config: Config, loc: Location, path: String) {
        config["$path.world"] = loc.world.name
        config["$path.x"] = loc.x
        config["$path.y"] = loc.y
        config["$path.z"] = loc.z
        config.save()
    }

    fun getLocation(config: Config, path: String): Location? {
        if (config.getConfigurationSection(path) == null) return null
        val world = config.getTranslation("$path.world")
        val x = config.getDouble("$path.x")
        val y = config.getDouble("$path.y")
        val z = config.getDouble("$path.z")
        val yaw = config.getDouble("$path.yaw").toFloat()
        val pitch = config.getDouble("$path.pitch").toFloat()
        val newLocation = Location(Bukkit.getWorld(world), x, y, z)
        newLocation.yaw = yaw
        newLocation.pitch = pitch
        return newLocation
    }

    fun deserializeLocation(string: String): Location {
        val split = string.split(";;").toTypedArray()
        return Location(Bukkit.getWorld(split[0]), split[1].toDouble(), split[2].toDouble(), split[3].toDouble())
    }
}