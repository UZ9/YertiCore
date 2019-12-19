package com.yerti.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class CustomModelPart {

    Location location;
    ArmorStand stand;

    /**
     * Creates a CustomModelPart, a part attached to a CustomModel
     * Every CustomModelPart is a singular ArmorStand
     * @param location
     */
    public CustomModelPart(Location location) {
        this.location = location;
        this.stand = location.getWorld().spawn(location, ArmorStand.class);
        this.stand.setCanPickupItems(false);
        this.stand.setCustomName("CustomModelPart");

        visibility(false);


    }

    /**
     * Sets if the armorstand should have arms
     * @param armValue
     * @return
     */
    public CustomModelPart arms(boolean armValue) {
        stand.setArms(armValue);
        return this;
    }

    /**
     * Sets the baseplate of the armorstand
     * @param baseValue
     * @return
     */
    public CustomModelPart basePlate(boolean baseValue) {
        stand.setBasePlate(baseValue);
        return this;
    }

    /**
     * Sets the gravity of the armorstand
     * @param baseValue
     * @return
     */
    public CustomModelPart gravity(boolean baseValue) {
        stand.setGravity(baseValue);
        return this;
    }

    /**
     * Sets the visibility of the armorstand
     * @param baseValue
     * @return
     */
    public CustomModelPart visibility(boolean baseValue) {
        stand.setVisible(baseValue);
        return this;
    }

    /**
     * Sets if the armorstand is small
     * @param value
     * @return
     */
    public CustomModelPart small(boolean value) {
        stand.setSmall(value);
        return this;
    }

    /**
     * Sets the amterial of the helmet
     * @param stack
     * @return
     */
    public CustomModelPart material(ItemStack stack) {
        stand.setHelmet(stack);

        return this;
    }


    /**
     * Retrieves the armorstand
     * @return
     */
    public ArmorStand getArmorStand() {
        return stand;
    }


}
