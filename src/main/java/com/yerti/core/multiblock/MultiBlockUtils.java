package com.yerti.core.multiblock;


import org.bukkit.Location;
import org.bukkit.Material;

public class MultiBlockUtils {

    //Find outer box: ceil(size / 2)
    public static boolean found(Location loc, int xSize, int ySize, int zSize, MultiBlockPattern pattern) {

        loc = loc.subtract(Math.ceil(xSize / 2.) - 1, Math.ceil(ySize / 2.) - 1, Math.ceil(zSize / 2.) - 1);

        Material[][][] matrix = pattern.build();

        for (int x = 0; x <xSize; x++) {
            for (int y = 0; y < ySize; y++) {
                for (int z = 0; z < zSize; z++) {
                    //new Location(loc.getWorld(), x + loc.getX(), y + loc.getY(), z + loc.getZ()).getBlock().setType(matrix[x][y][z]);

                    if (matrix[x][y][z] == Material.AIR) continue;

                    if (loc.clone().add(x, y, z).getBlock().getType() != matrix[x][y][z]) {
                        return false;
                    }
                }
            }
        }

        return true;


    }


}
