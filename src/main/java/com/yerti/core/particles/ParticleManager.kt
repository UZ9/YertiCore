package com.yerti.core.particles

import net.minecraft.server.v1_8_R3.EnumParticle
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles
import org.apache.commons.lang.Validate
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer

object ParticleManager {
    fun sendColorParticle(location: Location, r: Int, g: Int, b: Int) {
        val particles = PacketPlayOutWorldParticles(EnumParticle.FLAME, true, location.x.toFloat(), location.y.toFloat(), location.z.toFloat(), r.toFloat(), g.toFloat(), b.toFloat(), 255.toFloat(), 0, 10)
        for (player in Bukkit.getOnlinePlayers()) {
            (player as CraftPlayer).handle.playerConnection.sendPacket(particles)
        }
    }

    fun drawLine(start: Location, end: Location, r: Int, b: Int, g: Int) {
        val world = start.world
        Validate.isTrue(world == end.world, "Locations cannot be in different worlds.")
        val distance = start.distance(end)
        val v1 = start.toVector()
        val v2 = end.toVector()
        val vector = v2.clone().subtract(v1).normalize().multiply(2)
        var covered = 0
        while (covered < distance) {
            val particles = PacketPlayOutWorldParticles(EnumParticle.FLAME, true, v1.x.toFloat(), v1.y.toFloat(), v1.z.toFloat(), r.toFloat(), g.toFloat(), b.toFloat(), 255.toFloat(), 0, 10)
            for (player in Bukkit.getOnlinePlayers()) {
                (player as CraftPlayer).handle.playerConnection.sendPacket(particles)
            }
            covered += 2
            v1.add(vector)
        }
    }
}