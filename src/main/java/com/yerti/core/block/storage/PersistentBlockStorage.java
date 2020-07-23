package com.yerti.core.block.storage;

import com.yerti.core.logging.Logger;
import com.yerti.core.utils.LocationUtils;
import org.bukkit.Location;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

public abstract class PersistentBlockStorage {
    private Map<Location, Map<String, Serializable>> data = new HashMap<>();

    /**
     * is equivalent to:
     * <p><blockquote><pre>
     *     char data[] = {'a', 'b', 'c'};
     *     String str = new String(data);
     * </pre></blockquote><p>
     * Here are some more examples of how strings can be used:
     * <p><blockquote><pre>
     *     System.out.println("abc");
     *     String cde = "cde";
     *     System.out.println("abc" + cde);
     *     String c = "abc".substring(2,3);
     *     String d = cde.substring(1, 2);
     * </pre></blockquote>
     * @param location
     * @param key
     * @param value
     */
    public void storeValue(Location location, String key, Serializable value) {
        if (!data.containsKey(location)) data.put(location, new HashMap<>());

        data.get(location).put(key, value);
    }

    public Object getValue(Location location, String key) {
        if (!data.containsKey(location) || !data.get(location).containsKey(key)) {
            Logger.log(Level.SEVERE,  "Couldn't find key " + key + " in location " + LocationUtils.serializeLocation(location));
            return null;
        }

        return data.get(location).get(key);
    }

    public abstract void load();

    public abstract void save();

    protected Map<Location, Map<String, Serializable>> getAllData() {
        return data;
    }
}
