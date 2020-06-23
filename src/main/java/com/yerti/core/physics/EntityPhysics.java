package com.yerti.core.physics;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.FallingBlock;
import org.bukkit.util.Vector;

public class EntityPhysics {

    /**
     * Launches a block with a custom velocity
     *
     * @param blockLoc
     * @param velocity
     */
    public static void launchBlock(Location blockLoc, Vector velocity) {
        Block block = blockLoc.getBlock();

        Material material = block.getType();
        byte data = block.getData();


        block.setType(Material.AIR);

        FallingBlock fallingBlock = blockLoc.getWorld().spawnFallingBlock(blockLoc, material, data);
        fallingBlock.setDropItem(false);
        fallingBlock.setVelocity(velocity);
    }


}
