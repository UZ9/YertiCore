package com.yerti.core.enchantmenets;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EnchantmentHandler implements Listener {

    public static List<CombatEnchant> enchants = new ArrayList<>();

    @EventHandler
    public void onHit(EntityDamageByEntityEvent event) {

        if (event.getDamager() instanceof Player) {
            Player player = (Player) event.getDamager();

            ItemStack stack = player.getItemInHand();

            for (CombatEnchant enchant : enchants) {
                if (stack.containsEnchantment(enchant)) {
                    enchant.sendEvent(event);
                }
            }

        }

    }


}
