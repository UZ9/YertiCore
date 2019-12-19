package com.yerti.core.hologram;


import com.yerti.core.utils.ChatUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;

public class Hologram {

    /**
     * Creates a simple hologram based off of a block's location
     * @param block
     * @param name
     */
    public Hologram(Block block, String name) {
        ArmorStand stand = getArmorStand(block.getLocation().clone().add(.5, 2, .5), true);
        stand.setCustomName(ChatUtils.translate(name));

    }

    /**
     * Creates a simple hologram off of a location
     * @param location
     * @param name
     */
    public Hologram(Location location, String name) {
        ArmorStand stand = getArmorStand(location, true);
        stand.setCustomName(ChatUtils.translate(name));
    }

    /**
     * Retrieves an armorstand at a location
     * @param loc the target location
     * @param create If create is set to true, if the entity isn't found one will be created
     * @return
     */
    public ArmorStand getArmorStand(Location loc, boolean create) {
        for (Entity entity : loc.getChunk().getEntities()) {
            if (entity instanceof ArmorStand && entity.getCustomName() != null && entity.getLocation().distanceSquared(loc) < 0.4D) return (ArmorStand) entity;
        }
        if (!create) return null;
        return create(loc);
    }

    /**
     * Creates an ArmorStand hologram based off of a location
     * @param loc
     * @return
     */
    public ArmorStand create(Location loc) {
        ArmorStand stand = loc.getWorld().spawn(loc, ArmorStand.class);
        stand.setVisible(false);
        stand.setMarker(true);
        stand.setGravity(false);
        stand.setBasePlate(false);
        stand.setCustomNameVisible(true);
        stand.setRemoveWhenFarAway(false);
        return stand;
    }



}
