package com.yerti.core.block.storage;

import org.bukkit.Location;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public abstract class PersistentBlockStorage {
    private Map<Location, Map<String, Serializable>> data = new HashMap<>();

    public void storeValue(Location location, String key, Serializable value) { data.get(location).put(key, value); }

    public Object getValue(Location location, String key) {
        return data.get(location).get(key);
    }

    public abstract void load();

    public abstract void save();

    protected Map<Location, Map<String, Serializable>> getAllData() {
        return data;
    }
}
