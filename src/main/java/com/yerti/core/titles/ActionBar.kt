package com.yerti.core.titles

import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player

class ActionBar(text: String) {
    private val packet: PacketPlayOutChat
    fun sendToPlayer(p: Player) {
        (p as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }

    fun sendToAll() {
        for (p in Bukkit.getServer().onlinePlayers) {
            (p as CraftPlayer).handle.playerConnection.sendPacket(packet)
        }
    }

    init {
        var text = text
        text = ChatColor.translateAlternateColorCodes('&', text)
        packet = PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"$text\"}"), 2.toByte())
    }
}