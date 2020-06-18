package com.yerti.core.titles

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

/**
 * 1.8 Title util  from Maxim
 * http://git.mvdw-software.com/plugins/titlemotd/blob/db46e305e57c318a5cc063a531e3960a8d5a19a5/src/be/maximvdw/titlemotd/ui/Title.java
 */
class Title {
    /**
     * Get title text
     *
     * @return Title text
     */
    /**
     * Set title text
     *
     * @param title
     * Title
     */
    /* Title text and color */
    var title = ""
    /**
     * Set the title color
     *
     * @param color
     * Chat color
     */
    var titleColor = ChatColor.WHITE
    /**
     * Get subtitle text
     *
     * @return Subtitle text
     */
    /**
     * Set subtitle text
     *
     * @param subtitle
     * Subtitle text
     */
    /* Subtitle text and color */
    var subtitle = ""
    /**
     * Set the subtitle color
     *
     * @param color
     * Chat color
     */
    var subtitleColor = ChatColor.WHITE
    /**
     * Set title fade in time
     *
     * @param time
     * Time
     */
    /* Title timings */
    var fadeInTime = -1
    /**
     * Set title stay time
     *
     * @param time
     * Time
     */
    var stayTime = -1
    /**
     * Set title fade out time
     *
     * @param time
     * Time
     */
    var fadeOutTime = -1
    var isTicks = false
        private set

    constructor() {
        loadClasses()
    }

    /**
     * Create a new 1.8 title
     *
     * @param title
     * Title
     */
    constructor(title: String) {
        this.title = title
        loadClasses()
    }

    /**
     * Create a new 1.8 title
     *
     * @param title
     * Title text
     * @param subtitle
     * Subtitle text
     */
    constructor(title: String, subtitle: String) {
        this.title = title
        this.subtitle = subtitle
        loadClasses()
    }

    /**
     * Copy 1.8 title
     *
     * @param title
     * Title
     */
    constructor(title: Title) { // Copy title
        this.title = title.title
        subtitle = title.subtitle
        titleColor = title.titleColor
        subtitleColor = title.subtitleColor
        fadeInTime = title.fadeInTime
        fadeOutTime = title.fadeOutTime
        stayTime = title.stayTime
        isTicks = title.isTicks
        loadClasses()
    }

    /**
     * Create a new 1.8 title
     *
     * @param title
     * Title text
     * @param subtitle
     * Subtitle text
     * @param fadeInTime
     * Fade in time
     * @param stayTime
     * Stay on screen time
     * @param fadeOutTime
     * Fade out time
     */
    constructor(title: String, subtitle: String, fadeInTime: Int, stayTime: Int,
                fadeOutTime: Int) {
        this.title = title
        this.subtitle = subtitle
        this.fadeInTime = fadeInTime
        this.stayTime = stayTime
        this.fadeOutTime = fadeOutTime
        loadClasses()
    }

    /**
     * Load spigot and NMS classes
     */
    private fun loadClasses() {
        if (packetTitle == null) {
            packetTitle = getNMSClass("PacketPlayOutTitle")
            packetActions = getNMSClass("PacketPlayOutTitle\$EnumTitleAction")
            chatBaseComponent = getNMSClass("IChatBaseComponent")
            nmsChatSerializer = getNMSClass("ChatComponentText")
        }
    }

    /**
     * Set timings to ticks
     */
    fun setTimingsToTicks() {
        isTicks = true
    }

    /**
     * Set timings to seconds
     */
    fun setTimingsToSeconds() {
        isTicks = false
    }

    /**
     * Send the title to a player
     *
     * @param player
     * Player
     */
    fun send(player: Player) {
        if (packetTitle != null) { // First reset previous settings
            resetTitle(player)
            try { // Send timings first
                val handle = getHandle(player)
                val connection = getField(handle!!.javaClass,
                        "playerConnection")!![handle]
                val actions: Array<*> = packetActions!!.enumConstants
                val sendPacket = getMethod(connection.javaClass,
                        "sendPacket")
                var packet = packetTitle!!.getConstructor(packetActions,
                        chatBaseComponent, Integer.TYPE, Integer.TYPE,
                        Integer.TYPE).newInstance(actions[2], null,
                        fadeInTime * if (isTicks) 1 else 20,
                        stayTime * if (isTicks) 1 else 20,
                        fadeOutTime * if (isTicks) 1 else 20)
                // Send if set
                if (fadeInTime != -1 && fadeOutTime != -1 && stayTime != -1) sendPacket!!.invoke(connection, packet)
                // Send title
                var serialized = nmsChatSerializer!!.getConstructor(
                        String::class.java).newInstance(
                        ChatColor.translateAlternateColorCodes('&', title))
                packet = packetTitle!!.getConstructor(packetActions,
                        chatBaseComponent).newInstance(actions[0], serialized)
                sendPacket!!.invoke(connection, packet)
                if (subtitle != "") { // Send subtitle if present
                    serialized = nmsChatSerializer!!.getConstructor(String::class.java)
                            .newInstance(
                                    ChatColor.translateAlternateColorCodes('&',
                                            subtitle))
                    packet = packetTitle!!.getConstructor(packetActions,
                            chatBaseComponent).newInstance(actions[1],
                            serialized)
                    sendPacket.invoke(connection, packet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTimes(player: Player) {
        if (packetTitle != null) {
            try {
                val handle = getHandle(player)
                val connection = getField(handle!!.javaClass,
                        "playerConnection")!![handle]
                val actions: Array<*> = packetActions!!.enumConstants
                val sendPacket = getMethod(connection.javaClass,
                        "sendPacket")
                val packet = packetTitle!!.getConstructor(
                        *arrayOf(packetActions, chatBaseComponent,
                                Integer.TYPE, Integer.TYPE, Integer.TYPE))
                        .newInstance(
                                *arrayOf(
                                        actions[2],
                                        null,
                                        Integer.valueOf(fadeInTime
                                                * if (isTicks) 1 else 20),
                                        Integer.valueOf(stayTime
                                                * if (isTicks) 1 else 20),
                                        Integer.valueOf(fadeOutTime
                                                * if (isTicks) 1 else 20)))
                if (fadeInTime != -1 && fadeOutTime != -1
                        && stayTime != -1) {
                    sendPacket!!.invoke(connection, packet)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateTitle(player: Player) {
        if (packetTitle != null) {
            try {
                val handle = getHandle(player)
                val connection = getField(handle!!.javaClass,
                        "playerConnection")!![handle]
                val actions: Array<*> = packetActions!!.enumConstants
                val sendPacket = getMethod(connection.javaClass,
                        "sendPacket")
                val serialized = nmsChatSerializer!!.getConstructor(
                        String::class.java)
                        .newInstance(
                                ChatColor.translateAlternateColorCodes('&',
                                        title))
                val packet = packetTitle!!
                        .getConstructor(
                                *arrayOf(packetActions,
                                        chatBaseComponent)).newInstance(
                                *arrayOf(actions[0], serialized))
                sendPacket!!.invoke(connection, packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateSubtitle(player: Player) {
        if (packetTitle != null) {
            try {
                val handle = getHandle(player)
                val connection = getField(handle!!.javaClass,
                        "playerConnection")!![handle]
                val actions: Array<*> = packetActions!!.enumConstants
                val sendPacket = getMethod(connection.javaClass,
                        "sendPacket")
                val serialized = nmsChatSerializer!!.getConstructor(
                        String::class.java)
                        .newInstance(
                                ChatColor.translateAlternateColorCodes('&',
                                        subtitle))
                val packet = packetTitle!!
                        .getConstructor(
                                *arrayOf(packetActions,
                                        chatBaseComponent)).newInstance(
                                *arrayOf(actions[1], serialized))
                sendPacket!!.invoke(connection, packet)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * Broadcast the title to all players
     */
    fun broadcast() {
        for (p in Bukkit.getOnlinePlayers()) {
            send(p)
        }
    }

    /**
     * Clear the title
     *
     * @param player
     * Player
     */
    fun clearTitle(player: Player) {
        try { // Send timings first
            val handle = getHandle(player)
            val connection = getField(handle!!.javaClass, "playerConnection")
                    ?.get(handle)
            val actions: Array<*> = packetActions!!.enumConstants
            val sendPacket = getMethod(connection!!.javaClass, "sendPacket")
            val packet = packetTitle!!.getConstructor(packetActions,
                    chatBaseComponent).newInstance(actions[3], null)
            sendPacket!!.invoke(connection, packet)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * Reset the title settings
     *
     * @param player
     * Player
     */
    fun resetTitle(player: Player) {
        try { // Send timings first
            val handle = getHandle(player)
            val connection = getField(handle!!.javaClass, "playerConnection")
                    ?.get(handle)
            val actions: Array<*> = packetActions!!.enumConstants
            val sendPacket = getMethod(connection!!.javaClass, "sendPacket")
            val packet = packetTitle!!.getConstructor(packetActions,
                    chatBaseComponent).newInstance(actions[4], null)
            sendPacket!!.invoke(connection, packet)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun getPrimitiveType(clazz: Class<*>): Class<*>? {
        return if (CORRESPONDING_TYPES.containsKey(clazz)) CORRESPONDING_TYPES[clazz] else clazz
    }

    private fun toPrimitiveTypeArray(classes: Array<out Class<*>>): Array<Class<*>?> {
        val a = classes?.size ?: 0
        val types = arrayOfNulls<Class<*>?>(a)
        for (i in 0 until a) types[i] = getPrimitiveType(classes!![i])
        return types
    }

    private fun getHandle(obj: Any): Any? {
        return try {
            getMethod("getHandle", obj.javaClass)!!.invoke(obj)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getMethod(name: String, clazz: Class<*>,
                          vararg paramTypes: Class<*>): Method? {
        val t = toPrimitiveTypeArray(paramTypes)
        for (m in clazz.methods) {
            val types = toPrimitiveTypeArray(m.parameterTypes)
            if (m.name == name && equalsTypeArray(types, t)) return m
        }
        return null
    }

    private val version: String
        private get() {
            val name = Bukkit.getServer().javaClass.getPackage().name
            return name.substring(name.lastIndexOf('.') + 1) + "."
        }

    private fun getNMSClass(className: String): Class<*>? {
        val fullName = "net.minecraft.server.$version$className"
        var clazz: Class<*>? = null
        try {
            clazz = Class.forName(fullName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return clazz
    }

    private fun getField(clazz: Class<*>, name: String): Field? {
        return try {
            val field = clazz.getDeclaredField(name)
            field.isAccessible = true
            field
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun getMethod(clazz: Class<*>, name: String, vararg args: Class<*>): Method? {
        for (m in clazz.methods) if (m.name == name && (args.size == 0 || ClassListEqual(args as Array<Class<*>>,
                        m.parameterTypes))) {
            m.isAccessible = true
            return m
        }
        return null
    }

    private fun ClassListEqual(l1: Array<Class<*>>, l2: Array<Class<*>>): Boolean {
        var equal = true
        if (l1.size != l2.size) return false
        for (i in l1.indices) if (l1[i] != l2[i]) {
            equal = false
            break
        }
        return equal
    }

    companion object {
        /* Title packet */
        private var packetTitle: Class<*>? = null
        /* Title packet actions ENUM */
        private var packetActions: Class<*>? = null
        /* Chat serializer */
        private var nmsChatSerializer: Class<*>? = null
        private var chatBaseComponent: Class<*>? = null
        private val CORRESPONDING_TYPES: Map<Class<*>, Class<*>?> = HashMap()
        private fun equalsTypeArray(a: Array<Class<*>?>, o: Array<Class<*>?>): Boolean {
            if (a.size != o.size) return false
            for (i in a.indices) if (a[i] != o[i] && !a[i]!!.isAssignableFrom(o[i])) return false
            return true
        }
    }
}