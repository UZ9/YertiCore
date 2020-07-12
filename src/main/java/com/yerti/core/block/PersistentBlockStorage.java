package com.yerti.core.block;

import com.yerti.core.utils.LocationUtils;
import com.yerti.core.utils.SerializationUtils;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PersistentBlockStorage {

    private FileConfiguration config;
    private Map<Location, Map<String, Serializable>> data;

    public PersistentBlockStorage(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        this.config = YamlConfiguration.loadConfiguration(file);
        this.data = new HashMap<>();

        load();
    }

    public void storeValue(Location location, String key, Serializable value) {
        data.get(location).put(key, value);
    }

    public Object getValue(Location location, String key) {
        return data.get(location).get(key);
    }

    private void load() {
        try {
            config.getConfigurationSection("data").getKeys(false).forEach(loc -> {
                Location deserializedLoc = LocationUtils.deserializeLocation(loc);
                Map<String, Serializable> mapToAdd = new HashMap<>();

                config.getConfigurationSection("data." + loc).getKeys(false).forEach(flag -> {
                    try {
                        mapToAdd.put("data." + loc + "." + flag, (Serializable) SerializationUtils.fromString(config.getString("data." + loc + "." + flag)));
                    } catch (IOException | ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                });

                data.put(deserializedLoc, mapToAdd);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        data.forEach((key1, value1) -> {
            String serializedLoc = LocationUtils.serializeLocation(key1);

            value1.forEach((key, value) -> {
                try {
                    config.set("data." + serializedLoc + "." + key, SerializationUtils.toString(value));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });
    }


}
