package com.yerti.core.items;

import com.google.gson.*;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Contains several {@link ItemStack} utilities for ease
 */
public class ItemStackUtils {

    private static JsonParser parser = new JsonParser();
    private static Gson gson = new GsonBuilder().create();



    /**
     * @return the smelted {@link ItemStack} variant of the {@param itemStack}
     */
    public static ItemStack getSmeltedItemStack(final ItemStack itemStack) {
        switch (itemStack.getType()) {
            case PORK: {
                return new ItemStack(Material.GRILLED_PORK, itemStack.getAmount());
            }
            case RAW_BEEF: {
                return new ItemStack(Material.COOKED_BEEF, itemStack.getAmount());
            }
            case RAW_CHICKEN: {
                return new ItemStack(Material.COOKED_CHICKEN, itemStack.getAmount());
            }
            case RAW_FISH: {
                if (itemStack.getDurability() > 1) {
                    return null;
                }
                return new ItemStack(Material.COOKED_FISH, itemStack.getAmount(), itemStack.getDurability());
            }
            case POTATO_ITEM: {
                return new ItemStack(Material.BAKED_POTATO, itemStack.getAmount());
            }
            case IRON_ORE: {
                return new ItemStack(Material.IRON_INGOT, itemStack.getAmount());
            }
            case GOLD_ORE: {
                return new ItemStack(Material.GOLD_INGOT, itemStack.getAmount());
            }
            case SAND: {
                return new ItemStack(Material.GLASS, itemStack.getAmount());
            }
            case COBBLESTONE: {
                return new ItemStack(Material.STONE, itemStack.getAmount());
            }
            case CLAY_BALL: {
                return new ItemStack(Material.CLAY_BRICK, itemStack.getAmount());
            }
            case NETHERRACK: {
                return new ItemStack(Material.NETHER_BRICK_ITEM, itemStack.getAmount());
            }
            case CLAY: {
                return new ItemStack(Material.HARD_CLAY, itemStack.getAmount());
            }
            case DIAMOND_ORE: {
                return new ItemStack(Material.DIAMOND, itemStack.getAmount());
            }
            case LAPIS_ORE: {
                return new ItemStack(Material.INK_SACK, itemStack.getAmount(), (short) 4);
            }
            case REDSTONE_ORE: {
                return new ItemStack(Material.REDSTONE, itemStack.getAmount());
            }
            case COAL_ORE: {
                return new ItemStack(Material.COAL, itemStack.getAmount());
            }
            case EMERALD_ORE: {
                return new ItemStack(Material.EMERALD, itemStack.getAmount());
            }
            case QUARTZ_ORE: {
                return new ItemStack(Material.QUARTZ, itemStack.getAmount());
            }
            case LOG: {
                return new ItemStack(Material.COAL, itemStack.getAmount(), (short) 1);
            }
            case LOG_2: {
                return new ItemStack(Material.COAL, itemStack.getAmount(), (short) 1);
            }
            case CACTUS: {
                return new ItemStack(Material.INK_SACK, itemStack.getAmount(), (short) 2);
            }
            case SMOOTH_BRICK: {
                return new ItemStack(Material.SMOOTH_BRICK, itemStack.getAmount(), (short) 2);
            }
            case SPONGE: {
                if (itemStack.getDurability() < 1) {
                    return null;
                }
                return new ItemStack(Material.SPONGE, itemStack.getAmount(), (short) 0);
            }
            case MUTTON: {
                return new ItemStack(Material.COOKED_MUTTON, itemStack.getAmount());
            }
            case RABBIT: {
                return new ItemStack(Material.COOKED_RABBIT, itemStack.getAmount());
            }
            default: {
                return null;
            }
        }
    }

    /**
     * Serializes an itemstack and converts it to a JsonObject equivalent
     * @param item
     * @return
     */
    public static JsonObject serializeItemStack(final ItemStack item) {
        final JsonObject json = new JsonObject();
        json.addProperty("type", item.getType().name());
        json.addProperty("data", item.getData().getData());
        json.addProperty("amount", item.getAmount());
        json.addProperty("durability", item.getDurability());



        if (item.getEnchantments().size() > 0) {
            final JsonObject enchantments = new JsonObject();
            for (final Map.Entry<Enchantment, Integer> entry : item.getEnchantments().entrySet()) {
                final Enchantment ench = entry.getKey();
                final int lvl = entry.getValue();
                enchantments.addProperty(ench.getName(), lvl);
            }
            json.add("enchantments", enchantments);
        }
        if (item.hasItemMeta()) {
            final ItemMeta meta = item.getItemMeta();
            final JsonObject itemmeta = new JsonObject();
            if (meta.hasDisplayName()) {
                itemmeta.addProperty("displayname", meta.getDisplayName());

            }
            if (meta.hasLore()) {
                itemmeta.add("lore", gson.toJsonTree(meta.getLore()));
            }

            if (meta.getItemFlags().size() > 0) {
                StringBuilder serializedFlags = new StringBuilder();

                for (ItemFlag flag : meta.getItemFlags()) {
                    serializedFlags.append(flag.toString()).append("@@");
                }

                itemmeta.addProperty("flags", serializedFlags.toString());
            }


            json.add("itemmeta", itemmeta);
        }
        return json;
    }

    /**
     * Deserializes a serialized jsonobject to retrieve an itemstack
     * @param string
     * @return
     */
    public static ItemStack deserializeItemStack(final String string) {
        try {
            final JsonObject json = (JsonObject)parser.parse(string);
            final Material type = Material.getMaterial(json.get("type").getAsString());
            final byte data = json.get("data").getAsByte();
            final int amount = json.get("amount").getAsInt();
            final short durability = json.get("durability").getAsShort();
            final ItemStack item = new ItemStack(type, amount, (short)data);
            item.setDurability(durability);
            if (json.has("enchantments")) {
                final JsonObject enchantments = json.getAsJsonObject("enchantments");
                for (final Map.Entry<String, JsonElement> entry : enchantments.entrySet()) {
                    item.addUnsafeEnchantment(Enchantment.getByName(entry.getKey()), entry.getValue().getAsInt());
                }
            }
            if (json.has("itemmeta")) {
                final JsonObject itemmeta = json.getAsJsonObject("itemmeta");
                final ItemMeta meta = item.getItemMeta();
                if (itemmeta.has("displayname")) {
                    meta.setDisplayName(itemmeta.get("displayname").getAsString());
                }
                if (itemmeta.has("lore")) {
                    final List<String> lore = new ArrayList<String>();
                    final Iterator<JsonElement> it = itemmeta.get("lore").getAsJsonArray().iterator();
                    while (it.hasNext()) {
                        lore.add(it.next().getAsString());
                    }
                    meta.setLore(lore);
                }

                if (itemmeta.has("flags")) {
                    for (String flag : itemmeta.get("flags").getAsString().split("@@")) {
                        if (flag.equals("")) continue;
                        meta.addItemFlags(ItemFlag.valueOf(flag.toUpperCase()));

                    }
                }




                item.setItemMeta(meta);
            }
            return item;
        }
        catch (Exception ex) {
            return null;
        }
    }

}
