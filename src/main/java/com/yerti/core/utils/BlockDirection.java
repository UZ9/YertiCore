package com.yerti.core.utils;

import org.bukkit.Location;
import org.bukkit.block.BlockFace;

/**
 * Enum for easily offsetting by an amount from a location
 */
public enum BlockDirection {

    EAST(BlockFace.EAST),WEST(BlockFace.WEST),NORTH(BlockFace.SOUTH),SOUTH(BlockFace.NORTH);

    private BlockFace face;

    BlockDirection(BlockFace face) {
        this.face = face;
    }

    /**
     * Returns a {@link Location} with an offset in a certain direction
     * @param location
     * @param offsetBlocks
     * @return new location
     */
    public Location offsetLocation(Location location, int offsetBlocks) {
        Location output = location.clone();

        switch (this) {
            case EAST:
                output = output.add(offsetBlocks, 0, 0);
                break;
            case WEST:
                output = output.add(-offsetBlocks, 0, 0);
                break;
            case NORTH:
                output = output.add(0, 0, offsetBlocks);
                break;
            case SOUTH:
                output = output.add(0, 0, -offsetBlocks);
                break;
        }

        return output;
    }

    /**
     * Retrieves the associated block face with the enum
     * @return
     */
    public BlockFace getBlockFace() {
        return this.face;
    }

}
