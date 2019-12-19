package com.yerti.core.multiblock;

import org.bukkit.Material;

import java.util.HashMap;
import java.util.Map;

public class MultiBlockPattern {

    private String[][] pattern;
    private Map<Character, Material> key = new HashMap<>();
    /**
     * Creates a builder based off of a pattern
     * @param pattern
     */
    public MultiBlockPattern(String[][] pattern) {
        this.pattern = pattern;
    }

    /**
     * Adds a key to the multiblock structure using a key-value system
     * @param key
     * @param type
     * @return
     */
    public MultiBlockPattern addKey(char key, Material type) {
        this.key.put(key, type);
        return this;
    }

    /**
     * Builds a material matrix of the MultiBlockPattern
     * @return
     */
    public Material[][][] build() {
        Material[][][] built = new Material[pattern.length][pattern[0].length][pattern[0].length];


        for (int row = 0; row < pattern.length; row++) {
            for (int col = 0; col < pattern[row].length; col++) {
                for (int c = 0; c < pattern[col][row].length(); c++) {
                    char character = pattern[col][row].toCharArray()[c];
                    built[row][col][c] = key.getOrDefault(character, Material.AIR);

                }

            }
        }

        return built;


    }





}
