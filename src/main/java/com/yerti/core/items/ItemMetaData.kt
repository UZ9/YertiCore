package com.yerti.core.items

import com.google.common.collect.Lists
import net.minecraft.server.v1_8_R3.*
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack
import org.bukkit.inventory.ItemStack

object ItemMetaData {
    fun setMetadata(item: ItemStack?, metadata: String?, value: Any?): ItemStack {
        return CraftItemStack.asBukkitCopy(setMetadata(CraftItemStack.asNMSCopy(item), metadata, value))
    }

    fun setMetadata(item: net.minecraft.server.v1_8_R3.ItemStack, metadata: String?, value: Any?): net.minecraft.server.v1_8_R3.ItemStack {
        if (item.tag == null) {
            item.tag = NBTTagCompound()
        }
        setTag(item.tag, metadata, value)
        return item
    }

    fun hasMetadata(item: ItemStack?, metadata: String?): Boolean {
        return hasMetadata(CraftItemStack.asNMSCopy(item), metadata)
    }

    fun hasMetadata(item: net.minecraft.server.v1_8_R3.ItemStack, metadata: String?): Boolean {
        return item.tag != null && item.tag.hasKey(metadata)
    }

    fun getMetadata(item: ItemStack?, metadata: String?): Any? {
        return getMetadata(CraftItemStack.asNMSCopy(item), metadata)
    }

    fun getMetadata(item: net.minecraft.server.v1_8_R3.ItemStack, metadata: String?): Any? {
        return if (!hasMetadata(item, metadata)) null else getObject(item.tag[metadata])
    }

    private fun setTag(tag: NBTTagCompound, tagString: String?, value: Any?): NBTTagCompound {
        var base: NBTBase? = null
        if (value is Boolean) {
            base = NBTTagByte((if (value) 1 else 0).toByte())
        } else if (value is Long) {
            base = NBTTagLong((value as Long?)!!)
        } else if (value is Int) {
            base = NBTTagInt((value as Int?)!!)
        } else if (value is Byte) {
            base = NBTTagByte((value as Byte?)!!)
        } else if (value is Double) {
            base = NBTTagDouble((value as Double?)!!)
        } else if (value is Float) {
            base = NBTTagFloat((value as Float?)!!)
        } else if (value is String) {
            base = NBTTagString(value as String?)
        } else if (value is Short) {
            base = NBTTagShort((value as Short?)!!)
        }
        if (base != null) {
            tag[tagString] = base
        }
        return tag
    }

    private fun getObject(tag: NBTBase): Any? {
        if (tag is NBTTagEnd) {
            return null
        } else if (tag is NBTTagLong) {
            return tag.c()
        } else if (tag is NBTTagByte) {
            return tag.f()
        } else if (tag is NBTTagShort) {
            return tag.e()
        } else if (tag is NBTTagInt) {
            return tag.d()
        } else if (tag is NBTTagFloat) {
            return tag.h()
        } else if (tag is NBTTagDouble) {
            return tag.g()
        } else if (tag is NBTTagByteArray) {
            return tag.c()
        } else if (tag is NBTTagString) {
            return tag.a_()
        } else if (tag is NBTTagList) {
            var list: List<NBTBase>? = null
            try {
                val field = tag.javaClass.getDeclaredField("list")
                field.isAccessible = true
                list = field[tag] as List<NBTBase>
            } catch (e: Exception) {
                e.printStackTrace()
            }
            if (list == null) return null
            val toReturn: MutableList<Any?> = Lists.newArrayList()
            for (base in list) {
                toReturn.add(getObject(base))
            }
            return toReturn
        } else if (tag is NBTTagCompound) {
            return tag
        } else if (tag is NBTTagIntArray) {
            return tag.c()
        }
        return null
    }
}