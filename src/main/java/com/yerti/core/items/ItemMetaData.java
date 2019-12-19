package com.yerti.core.items;

import com.google.common.collect.Lists;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;

import java.lang.reflect.Field;
import java.util.List;

public class ItemMetaData {

    //TODO: Replace with NMSUtils

    /**
     * Sets the metdata information of an itemstack with a value
     * @param item
     * @param metadata
     * @param value
     * @return
     */
    public static org.bukkit.inventory.ItemStack setMetadata(org.bukkit.inventory.ItemStack item, String metadata, Object value){
        return CraftItemStack.asBukkitCopy(setMetadata(CraftItemStack.asNMSCopy(item), metadata, value));
    }


    /**
     * Sets the metadata of an itemstack with a value
     * @param item
     * @param metadata
     * @param value
     * @return
     */
    public static ItemStack setMetadata(ItemStack item, String metadata, Object value){
        if(item.getTag() == null){
            item.setTag(new NBTTagCompound());
        }
        setTag(item.getTag(), metadata, value);
        return item;
    }

    /**
     * Checks if an object has a specific key of metadata
     * @param item
     * @param metadata
     * @return
     */
    public static boolean hasMetadata(org.bukkit.inventory.ItemStack item, String metadata){
        return hasMetadata(CraftItemStack.asNMSCopy(item), metadata);
    }

    /**
     * Checks if an object has a specific key of metadata
     * @param item
     * @param metadata
     * @return
     */
    public static boolean hasMetadata(ItemStack item, String metadata){
        return item.getTag() != null && item.getTag().hasKey(metadata);
    }

    /**
     * Retrieves the metadata of a key from an item
     * @param item
     * @param metadata
     * @return
     */
    public static Object getMetadata(org.bukkit.inventory.ItemStack item, String metadata){
        return getMetadata(CraftItemStack.asNMSCopy(item), metadata);
    }

    /**
     * Retrieves the metadata of a key from an item
     * @param item
     * @param metadata
     * @return
     */
    public static Object getMetadata(ItemStack item, String metadata){
        if(!hasMetadata(item, metadata))return null;
        return getObject(item.getTag().get(metadata));
    }

    private static NBTTagCompound setTag(NBTTagCompound tag, String tagString, Object value) {

        NBTBase base = null;

        if (value instanceof Boolean) {
            base = new NBTTagByte((byte)(((Boolean)value).booleanValue() ? 1 : 0));
        } else if (value instanceof Integer) {
            base = new NBTTagInt((Integer) value);
        } else if (value instanceof Byte) {
            base = new NBTTagByte((Byte) value);
        } else if (value instanceof Double) {
            base = new NBTTagDouble((Double) value);
        } else if (value instanceof Float) {
            base = new NBTTagFloat((Float) value);
        } else if (value instanceof String) {
            base = new NBTTagString((String) value);
        } else if (value instanceof Short) {
            base = new NBTTagShort((Short) value);
        } else if (value instanceof Long) {
            base = new NBTTagLong((Long) value);
        }

        if(base != null){
            tag.set(tagString, base);
        }

        return tag;
    }

    @SuppressWarnings("unchecked")
    /**
     * Retrieves an object off ofo an NBTTag (Still in development, may not be used)
     */
    private static Object getObject(NBTBase tag){
        if(tag instanceof NBTTagEnd){
            return null;
        }else if(tag instanceof NBTTagByte){
            return ((NBTTagByte) tag).f();
        }else if(tag instanceof NBTTagShort){
            return ((NBTTagShort) tag).e();
        }else if(tag instanceof NBTTagInt){
            return ((NBTTagInt) tag).e();
        }else if(tag instanceof NBTTagLong){
            return ((NBTTagLong) tag).d();
        }else if(tag instanceof NBTTagFloat){
            return ((NBTTagFloat) tag).h();
        }else if(tag instanceof NBTTagDouble){
            return ((NBTTagDouble) tag).h();
        }else if(tag instanceof NBTTagByteArray){
            return ((NBTTagByteArray) tag).c();
        }else if(tag instanceof NBTTagString){
            return ((NBTTagString) tag).a_();
        }else if(tag instanceof NBTTagList){
            List<NBTBase> list = null;
            try {
                Field field = tag.getClass().getDeclaredField("list");
                field.setAccessible(true);
                list = (List<NBTBase>)field.get(tag);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(list == null)return null;
            List<Object> toReturn = Lists.newArrayList();
            for(NBTBase base : list){
                toReturn.add(getObject(base));
            }
            return toReturn;
        }else if(tag instanceof NBTTagCompound){
            return tag;
        }else if(tag instanceof NBTTagIntArray){
            return ((NBTTagIntArray) tag).c();
        }
        return null;
    }

}