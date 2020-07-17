package com.yerti.core.block.storage;

import com.yerti.core.database.sql.PreparedStatementBuilder;
import com.yerti.core.database.sql.SQLManager;
import com.yerti.core.utils.LocationUtils;
import org.bukkit.Location;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

public class SQLPersistentBlockStorage extends PersistentBlockStorage {

    private SQLManager sqlManager;

    public SQLPersistentBlockStorage(SQLManager sqlManager) {
        this.sqlManager = sqlManager;
        load();
    }

    @Override
    public void load() {
        sqlManager.execute("CREATE TABLE IF NOT EXISTS block_data (location tinytext PRIMARY KEY, data TEXT");

        ResultSet set = sqlManager.query("SELECT * from block_data");

        while (true) {
            try {
                if (!set.next()) break;

                Location location = LocationUtils.deserializeLocation(set.getString(1));
                String data = set.getString(2);

                JSONObject newObject = new JSONObject(data);

                Map<String, Object> d = newObject.toMap();

                //TODO: Is there a better alternative?
                Map<String, Serializable> newData = d.entrySet().stream().collect(Collectors.toMap(
                        Map.Entry::getKey,
                        e -> (Serializable) e.getValue()
                ));

                getAllData().put(location, newData);



            } catch (SQLException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void save() {
        //TODO: Find a more optimal solution to this problem than creating a new map, but I still don't fully comprehend Gson serialization
        getAllData().forEach((key, value) -> {
            new PreparedStatementBuilder(sqlManager, "INSERT INTO data (location, data) VALUES(?, ?) ON DUPLICATE KEY UPDATE data=?")
                    .setString(1, LocationUtils.serializeLocation(key))
                    .setString(2, new JSONObject(value).toString())
                    .setString(3, new JSONObject(value).toString())
                    .execute();
        });



    }
}
