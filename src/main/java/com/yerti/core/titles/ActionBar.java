package com.yerti.core.titles;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionBar {

    //TODO: Replace version dependent packet with reflection methods recently added in NMSUtils
    private PacketPlayOutChat packet;

    /**
     * Creates an action bar with a string of text
     * Actions bars can be sent either individually to a player or to the entire server
     * @param text
     */
    public ActionBar(String text) {
        text = ChatColor.translateAlternateColorCodes('&', text);
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + text + "\"}"), (byte) 2);
        this.packet = packet;
    }

    /**
     * Sends the actionbar to a specific player
     * @param p
     */
    public void sendToPlayer(Player p) {
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);
    }

    /**
     * Sends the actionbar to everyone on the server
     */
    public void sendToAll() {
        for (Player p : Bukkit.getServer().getOnlinePlayers()) {
            ((CraftPlayer)p).getHandle().playerConnection.sendPacket(packet);;
        }
    }

}