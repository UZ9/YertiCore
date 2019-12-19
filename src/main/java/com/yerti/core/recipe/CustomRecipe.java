package com.yerti.core.recipe;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;

import java.util.HashMap;
import java.util.Map;

public class CustomRecipe {

    private ShapedRecipe recipe;
    //Output, Matrix Input
    private Map<Character, ItemStack> matrixKey = new HashMap<>();
    private static Map<ItemStack, ItemStack[]> matrixMap = new HashMap<>();

    /**
     * Creates a new custom recipe
     * @param output the output of the recipe
     */
    public CustomRecipe(ItemStack output) {
        recipe = new ShapedRecipe(output);
    }

    /**
     * Sets the shape of the recipe
     * @param shape
     */
    public void shape(String... shape) {
        recipe.shape(shape);
    }

    /**
     * Sets one of the ingredients of the recipe using a key-value setup
     * @param key
     * @param ingredient
     */
    public void setIngredient(char key, ItemStack ingredient) {
        recipe.setIngredient(key, ingredient.getType());
        matrixKey.put(key, ingredient);
    }

    /**
     * Builds a {@link ShapedRecipe} that can be used in the Bukkit createRecipe method
     * @return
     */
    public ShapedRecipe build() {
        ItemStack[] matrix = new ItemStack[9];

        int index = 0;

        for (String string : recipe.getShape()) {
            for (char c : string.toCharArray()) {
                matrix[index] = matrixKey.get(c);
                index++;
            }
        }

        matrixMap.put(recipe.getResult(), matrix);


        return recipe;


    }

    /**
     * Retrieves the matrix map of the custom recipes
     * @return
     */
    static Map<ItemStack, ItemStack[]> getMatrixMap() {
        return matrixMap;
    }
}
