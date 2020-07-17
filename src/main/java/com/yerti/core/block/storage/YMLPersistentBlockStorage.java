package com.yerti.core.block.storage;

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

public class YMLPersistentBlockStorage extends PersistentBlockStorage {

    private FileConfiguration config;


    public YMLPersistentBlockStorage(Plugin plugin, String fileName) {
        File file = new File(plugin.getDataFolder(), fileName);
        this.config = YamlConfiguration.loadConfiguration(file);

        load();
    }

    public void load() {
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

                getAllData().put(deserializedLoc, mapToAdd);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        getAllData().forEach((key1, value1) -> {
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
