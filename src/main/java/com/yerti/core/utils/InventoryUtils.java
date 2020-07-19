package com.yerti.core.utils;


import com.yerti.core.items.ItemStackBuilder;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InventoryUtils {

    /**
     * Checks if the player can fit the ItemStack within their inventory.
     *
     * @param player
     * @param itemStack
     * @return
     */
    public static boolean hasInventorySpace(Player player, ItemStack itemStack) {

        int freeSpace = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack currentItemStack = player.getInventory().getItem(i);

            //Itemstack is Material.AIR
            if (currentItemStack == null) {
                freeSpace += itemStack.getMaxStackSize();
                //Item has same item meta
            } else if (currentItemStack.isSimilar(itemStack)) {
                freeSpace += itemStack.getMaxStackSize() - currentItemStack.getAmount();
            }

        }

        return freeSpace >= itemStack.getAmount();

    }

    /**
     * Checks if the inventory can fit the ItemStack within their inventory.
     *
     * @param inventory
     * @param itemStack
     * @return
     */
    public static boolean hasInventorySpace(Inventory inventory, ItemStack itemStack) {

        int freeSpace = 0;

        for (int i = 0; i < 9; i++) {
            ItemStack currentItemStack = inventory.getItem(i);

            //Itemstack is Material.AIR
            if (currentItemStack == null) {
                freeSpace += itemStack.getMaxStackSize();
                //Item has same item meta
            } else if (currentItemStack.isSimilar(itemStack)) {
                freeSpace += itemStack.getMaxStackSize() - currentItemStack.getAmount();
            }

        }

        return freeSpace >= itemStack.getAmount();

    }

    /**
     * Checks if the player have an amount of slots open
     *
     * @param player
     * @param slots
     * @return
     */
    public static boolean hasOpenSlots(Player player, int slots) {

        int freeSlots = 0;

        for (int i = 0; i < 36; i++) {
            ItemStack currentItemStack = player.getInventory().getItem(i);

            if (currentItemStack == null) {
                freeSlots++;
            }
        }

        return freeSlots >= slots;
    }

    /**
     * Returns the amount of a material in a given inventory
     *
     * @param inventory
     * @param material
     * @return
     */
    public static int getItemCount(Inventory inventory, Material material) {
        int amount = 0;
        for (ItemStack stack : inventory.all(material).values()) {
            amount += stack.getAmount();

        }

        return amount;
    }

    /**
     * Returns the amount of an itemstack in a given inventory
     *
     * @param inventory
     * @param item
     * @return
     */
    public static int getItemCount(Inventory inventory, ItemStack item) {
        int amount = 0;
        for (ItemStack stack : inventory.getContents()) {
            if (stack == null || stack.getType() == Material.AIR) continue;

            if (new ItemStackBuilder(stack).isSimilar(item)) {
                amount += stack.getAmount();
            }
        }

        return amount;
    }

    /**
     * Removes an amount of a material from an inventory
     *
     * @param inventory
     * @param stack
     * @param amount
     * @return
     */
    public static int removeItems(Inventory inventory, ItemStack stack, int amount) {

        if (stack == null || inventory == null || stack.getType() == Material.AIR)
            return -1;
        if (amount <= 0)
            return -1;

        for (ItemStack invStack : inventory.getContents()) {
            if (invStack == null) continue;
            if (amount == 0) return 1;

            if (new ItemStackBuilder(stack).isSimilar(invStack)) {
                if (invStack.getAmount() <= amount) {
                    inventory.remove(invStack);
                    amount -= invStack.getAmount();
                } else {
                    invStack.setAmount(invStack.getAmount() - amount);
                    amount = 0;
                }
            }
        }

        return 1;
    }

    /**
     * Serializes an {@link Inventory} to Base64
     * @param inventory A {@link Inventory}
     * @return A Base64 serialized inventory, if a {@link IOException} occurs the method will return "".
     */
    public static String serializeInventory(Inventory inventory) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            BukkitObjectOutputStream boos = new BukkitObjectOutputStream(baos);

            ItemStack[] contents = inventory.getContents();

            boos.writeInt(inventory.getContents().length);

            for (int i = 0; i < inventory.getSize(); i++) {
                boos.writeObject(contents[i]);
            }

            boos.close();

            return Base64Coder.encodeLines(baos.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Deserializes a Base64 {@link String} into a {@link Inventory}
     * @param serialized The serialized Base74 {@link String}
     * @return A {@link Inventory}, if a {@link ClassNotFoundException} or {@link IOException} exception occurs the method will return null.
     */
    public static Inventory deserializeInventory(String serialized) {
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(Base64Coder.decodeLines(serialized));
            BukkitObjectInputStream bois = new BukkitObjectInputStream(bais);

            Inventory inventory = Bukkit.createInventory(null, bois.readInt());

            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) bois.readObject());
            }

            bois.close();
            return inventory;
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }


}
