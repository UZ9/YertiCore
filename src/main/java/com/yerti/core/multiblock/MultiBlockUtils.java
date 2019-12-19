package com.yerti.core.multiblock;


import com.yerti.core.utils.LocationUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

public class MultiBlockUtils {

    /**
     * Checks for if a MultiBlockPattern exists at a location, assuming the block clicked is the center
     * @param loc
     * @param xSize
     * @param ySize
     * @param zSize
     * @param pattern
     * @return
     */
    public static boolean found(Location loc, int xSize, int ySize, int zSize, MultiBlockPattern pattern) {
        loc = loc.subtract(Math.ceil(xSize / 2.) - 1, Math.ceil(ySize / 2.) - 1, Math.ceil(zSize / 2.) - 1);

        Material[][][] matrix = pattern.build();

        for (int x = 0; x <xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    //new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock().setType(matrix[x][y][z]);

                    if (matrix[x][y][z] == Material.AIR) continue;

                    if (new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock().getType() != matrix[x][y][z]) {

                        Location location = new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ());

                        Bukkit.broadcastMessage(LocationUtils.serializeLocation(location) + " has type of " + location.getBlock().getType() + ", which isn't " + matrix[x][y][z]);
                        return false;
                    }
                }
            }
        }

        return true;


    }


}
