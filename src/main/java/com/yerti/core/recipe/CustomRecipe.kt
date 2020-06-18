package com.yerti.core.recipe

import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.ShapedRecipe
import java.util.*

class CustomRecipe(output: ItemStack?) {
    private val recipe: ShapedRecipe
    //Output, Matrix Input
    private val matrixKey: MutableMap<Char, ItemStack> = HashMap()

    fun shape(vararg shape: String?) {
        recipe.shape(*shape)
    }

    fun setIngredient(key: Char, ingredient: ItemStack) {
        recipe.setIngredient(key, ingredient.type)
        matrixKey[key] = ingredient
    }

    fun build(): ShapedRecipe {
        val matrix = arrayOfNulls<ItemStack>(9)
        var index = 0
        for (string in recipe.shape) {
            for (c in string.toCharArray()) {
                matrix[index] = matrixKey[c]
                index++
            }
        }
        matrixMap[recipe.result] = matrix
        return recipe
    }

    companion object {
        private val matrixMap: MutableMap<ItemStack, Array<ItemStack?>> = HashMap()
        fun getMatrixMap(): Map<ItemStack, Array<ItemStack?>>? {
            return matrixMap
        }
    }

    init {
        recipe = ShapedRecipe(output)
    }
}