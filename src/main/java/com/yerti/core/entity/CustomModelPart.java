package com.yerti.core.entity;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.inventory.ItemStack;

public class CustomModelPart {

    Location location;
    ArmorStand stand;

    public CustomModelPart(Location location) {
        this.location = location;
        this.stand = location.getWorld().spawn(location, ArmorStand.class);
        this.stand.setCanPickupItems(false);
        this.stand.setCustomName("CustomModelPart");

        visibility(false);


    }

    public CustomModelPart arms(boolean armValue) {
        stand.setArms(armValue);
        return this;
    }

    public CustomModelPart basePlate(boolean baseValue) {
        stand.setBasePlate(baseValue);
        return this;
    }

    public CustomModelPart gravity(boolean baseValue) {
        stand.setGravity(baseValue);
        return this;
    }

    public CustomModelPart visibility(boolean baseValue) {
        stand.setVisible(baseValue);
        return this;
    }

    public CustomModelPart small(boolean value) {
        stand.setSmall(value);
        return this;
    }

    public CustomModelPart material(ItemStack stack) {
        stand.setHelmet(stack);

        return this;
    }


    public ArmorStand getArmorStand() {
        return stand;
    }


}
