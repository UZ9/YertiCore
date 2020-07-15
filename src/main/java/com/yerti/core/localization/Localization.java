package com.yerti.core.localization;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;

public class Localization {

    private LocalizationType type;
    private Map<String, String> translation;


    public Localization(JavaPlugin plugin, LocalizationType type) {
        this.type = type;
        this.translation = new HashMap<>();

        File filePath = new File(plugin.getDataFolder(), "localization" + File.pathSeparator + type.name().toLowerCase() + ".lang");

        try {
            Scanner scanner = new Scanner(filePath);

            while (scanner.hasNextLine()) {
                String[] split = scanner.nextLine().split("=");
                if (split.length != 2) {
                    Bukkit.getLogger().log(Level.SEVERE, "Incorrect formatting in " + filePath.getName() + ", ensure that it is correctly written.");
                    scanner.close();
                    return;
                } else {
                    translation.put(split[0], split[1]);
                }
            }


        } catch (FileNotFoundException e) {
            Bukkit.getLogger().log(Level.SEVERE, "Couldn't find the specified language's .properties file.");
        }


    }

    public String getValue(String key) {
        return translation.get(key);
    }


}
