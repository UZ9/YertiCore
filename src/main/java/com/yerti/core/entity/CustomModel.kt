package com.yerti.core.entity

import java.util.*

class CustomModel {
    private val armorstands: MutableList<CustomModelPart>
    fun getArmorstands(): List<CustomModelPart> {
        return armorstands
    }

    fun addArmorStand(model: CustomModelPart) {
        armorstands.add(model)
    }

    fun removeArmorStand(model: CustomModelPart) {
        val optional = armorstands.stream().filter { m: CustomModelPart -> m == model }.findFirst()
        optional.ifPresent { armorStandModel: CustomModelPart? -> armorstands.remove(armorStandModel) }
    }

    fun destroy() {
        for (part in armorstands) {
            part.armorStand.remove()
        }
    }

    init {
        armorstands = ArrayList()
    }
}