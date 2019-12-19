package com.yerti.core.block;

import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Various utilities for {@link Block}
 */
public class BlockUtils {

    /**
     * Breaks a block as if it was from a player
     * @param p the target player
     * @param b the target block
     */
    public static void breakBlock(Player p, Block b) {
        BlockBreakEvent blockBreakEvent = new BlockBreakEvent(b, p);
        Bukkit.getServer().getPluginManager().callEvent(blockBreakEvent);
        if (!blockBreakEvent.isCancelled()) {
            blockBreakEvent.setCancelled(true);
            for (ItemStack drop : b.getDrops()) {
                b.getWorld().dropItem(b.getLocation(), drop);
            }
            b.getWorld().playEffect(b.getLocation(), Effect.STEP_SOUND, b.getType());
            b.setType(Material.AIR);
        }

    }

    public static List<Location> getBlocks(Location l, int radius)
    {
        World w = l.getWorld();
        int xCoord = (int) l.getX();
        int zCoord = (int) l.getZ();
        int YCoord = (int) l.getY();

        List<Location> tempList = new ArrayList<>();
        for (int x = -radius; x <= radius; x++)
        {
            for (int z = -radius; z <= radius; z++)
            {
                for (int y = -radius; y <= radius; y++)
                {
                    tempList.add(new Location(w, xCoord + x, YCoord + y, zCoord + z));
                }
            }
        }
        return tempList;
    }




}
