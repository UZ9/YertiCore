package com.yerti.core.config

import com.yerti.core.utils.LocationUtils.serializeLocation
import org.bukkit.Location
import org.bukkit.configuration.ConfigurationSection
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.Plugin
import java.io.File
import java.io.IOException
import java.util.*

class Config {
    var config: FileConfiguration
    var file: File

    constructor(pl: Plugin) {
        pl.saveDefaultConfig()
        file = File("plugins/" + pl.name.replace(" ", "_"), "config.yml")
        config = YamlConfiguration.loadConfiguration(file)
    }

    constructor(pl: Plugin, name: String?) {
        file = File("plugins/" + pl.name.replace(" ", "_"), name)
        config = YamlConfiguration.loadConfiguration(file)
        config.options().copyDefaults(true)
    }

    fun setPrefix(prefix: String?) {
        config["prefix"] = prefix
    }

    fun getValue(key: String?): Any {
        return config[key]
    }

    fun getTranslation(key: String?): String {
        return getValue(key) as String
    }

    operator fun set(key: String?, value: Any) {
        if (value is String) {
            config[key] = value.toString()
        } else if (value is UUID) {
            config[key] = value.toString()
        } else if (value is Long) {
            config[key] = java.lang.Long.valueOf(value.toString())
        } else if (value is Location) {
            config[key] = serializeLocation(value)
        } else {
            config[key] = value
        }
    }

    fun getConfigurationSection(path: String?): ConfigurationSection {
        return config.getConfigurationSection(path)
    }

    fun getDouble(key: String?): Double {
        return config.getDouble(key)
    }

    fun getInt(key: String?): Int {
        return config.getInt(key)
    }

    fun save() {
        try {
            config.save(file)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}