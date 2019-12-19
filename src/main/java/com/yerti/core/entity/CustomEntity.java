package com.yerti.core.entity;


import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.Nullable;

/**
 * Custom Entity modifier off of a living entity
 */
public class CustomEntity {

    private LivingEntity entity;

    /**
     *
     * @param entity a living entity
     * @param armor the armor to put on (leave null if no armor)
     * @param hand item to be held in hand (leave null if nothing in hand)
     * @param health health for the mob (up to 1024)
     * @param effects potion effects for the mob (leave null if nothing)
     */
    public CustomEntity(LivingEntity entity, @Nullable ItemStack[] armor, @Nullable ItemStack hand, int health, @Nullable PotionEffect[] effects) {
        this.entity = entity;

        if (armor != null) entity.getEquipment().setArmorContents(armor);
        if (hand != null) entity.getEquipment().setItemInHand(hand);
        if (health != 0) {
            entity.setMaxHealth(health);
            entity.setHealth(health);
        }
        if (effects != null) {
            for (PotionEffect effect : effects) {
                entity.addPotionEffect(effect);
            }
        }


    }


}
