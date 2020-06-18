package com.yerti.core.utils

import com.yerti.core.YertiPlugin.Companion.hookedPlugin

object NMSUtils {
    fun getNMSClass(name: String): Class<*>? {
        return try {
            Class.forName("net.minecraft.server." + hookedPlugin!!.server.version + "." + name)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
            null
        }
    }
}