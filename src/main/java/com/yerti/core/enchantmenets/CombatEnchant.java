package com.yerti.core.enchantmenets;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public class CombatEnchant extends Enchantment {

    private String name;
    private int maxLevel;
    private int startLevel;
    private int id;
    private Consumer<EntityDamageByEntityEvent> consumer;


    /**
     * Creates a custom combat enchantment with an event on entity damage
     * @param name
     * @param maxLevel
     * @param startLevel
     * @param id
     * @param consumer
     */
    public CombatEnchant(String name, int maxLevel, int startLevel, int id, Consumer<EntityDamageByEntityEvent> consumer) {
        super(id);
        this.name = name;
        this.maxLevel = maxLevel;
        this.startLevel = startLevel;
        this.id = id;
        this.consumer = consumer;

    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getMaxLevel() {
        return maxLevel;
    }

    @Override
    public int getStartLevel() {
        return startLevel;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return null;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return false;
    }

    public void sendEvent(EntityDamageByEntityEvent t) {
        consumer.accept(t);
    }
}
