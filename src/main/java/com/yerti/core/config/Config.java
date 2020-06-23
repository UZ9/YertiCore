package com.yerti.core.config;



import com.yerti.core.utils.LocationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class Config {

    FileConfiguration config;
    File file;


    public Config(Plugin pl) {
        pl.saveDefaultConfig();
        this.file = new File("plugins/" + pl.getName().replace(" ", "_"), "config.yml");
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    public Config(Plugin pl, String name) {
        this.file = new File("plugins/" + pl.getName().replace(" ", "_"), name);
        this.config = YamlConfiguration.loadConfiguration(file);
        config.options().copyDefaults(true);
    }

    public void setPrefix(String prefix) {
        config.set("prefix", prefix);
    }

    public Object getValue(String key) {
        return config.get(key);
    }

    public String getTranslation(String key) {
        return (String) getValue(key);
    }

    public void set(String key, Object value) {

        if (value instanceof String) {
            config.set(key, value.toString());
        } else if (value instanceof UUID) {
            config.set(key, value.toString());
        } else if (value instanceof Long) {
            config.set(key, Long.valueOf(value.toString()));
        } else if (value instanceof Location) {
            config.set(key, LocationUtils.serializeLocation((Location) value));
        } else {
            config.set(key, value);
        }



    }

    public ConfigurationSection getConfigurationSection(String path) {
        return config.getConfigurationSection(path);
    }

    public double getDouble(String key) {
        return config.getDouble(key);
    }

    public int getInt(String key) {
        return config.getInt(key);
    }

    public void save() {
        try {
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
