package com.yerti.core.particles;

import com.yerti.core.utils.NMSUtils;
import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.Objects;


public class ParticleManager {

    /**
     * Sends a colored particle to a location
     * @param location
     * @param r
     * @param g
     * @param b
     */
    public static void sendColorParticle(Location location, int r, int g, int b) {
        Class<?> playOutWorldParticles = NMSUtils.getNMSClass("PacketPlayOutWorldParticles");



        PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), r, g, b, (float) 255,0, 10);

        for (Player player : Bukkit.getOnlinePlayers()) {
            try {
                Class<?> particleEnum = NMSUtils.getNMSClass("EnumParticle");
                Object flameValue = NMSUtils.getEnum(Objects.requireNonNull(particleEnum), "FLAME");





                Object particlePacket = Objects.requireNonNull(NMSUtils.getNMSClass("PacketPlayOutWorldParticles")).getConstructor(NMSUtils.getNMSClass("EnumParticle"), boolean.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, float.class, int.class).newInstance(flameValue, true, (float) location.getX(), (float) location.getY(), (float) location.getZ(), r, g, b, (float) 255,0, 10);
                NMSUtils.sendPacket(player, particlePacket);

                Bukkit.broadcastMessage("Played packet");



            } catch (Exception e) {
                e.printStackTrace();
            }

            //((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
        }

    }

    /**
     * Draws a line of particles between 2 locations
     * @param start
     * @param end
     * @param r
     * @param b
     * @param g
     */
    public static void drawLine(Location start, Location end, int r, int b, int g) {
        World world = start.getWorld();

        Validate.isTrue(world.equals(end.getWorld()), "Locations cannot be in different worlds.");

        double distance = start.distance(end);

        Vector v1 = start.toVector();
        Vector v2 = end.toVector();

        Vector vector = v2.clone().subtract(v1).normalize().multiply(2);

        int covered = 0;

        for (; covered < distance; v1.add(vector)) {
            PacketPlayOutWorldParticles particles = new PacketPlayOutWorldParticles(EnumParticle.FLAME, true, (float) v1.getX(), (float) v1.getY(), (float) v1.getZ(), r, g, b, (float) 255,0, 10);

            for (Player player : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer) player).getHandle().playerConnection.sendPacket(particles);
            }

            covered += 2;



        }
    }



}
