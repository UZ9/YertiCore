package com.yerti.core.block;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

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



}
