package com.yerti.core.inventories

import com.yerti.core.inventories.AnvilGUI.Slot
import net.minecraft.server.v1_8_R3.*
import org.apache.commons.lang.Validate
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.craftbukkit.v1_8_R3.event.CraftEventFactory
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.HandlerList
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.util.function.BiFunction
import java.util.function.Consumer

//https://github.com/WesJD/AnvilGUI/blob/master/api/src/main/java/net/wesjd/anvilgui/AnvilGUI.java
class AnvilGUI private constructor(
        /**
         * The [Plugin] that this anvil GUI is associated with
         */
        private val plugin: Plugin?,
        /**
         * The player who has the GUI open
         */
        private val player: Player,
        /**
         * The text that will be displayed to the user
         */
        private val text: String,
        /**
         * A state that decides where the anvil GUI is able to be closed by the user
         */
        private val preventClose: Boolean,
        /**
         * An [Consumer] that is called when the anvil GUI is closed
         */
        private val closeListener: Consumer<Player>?,
        /**
         * An [BiFunction] that is called when the [Slot.OUTPUT] slot has been clicked
         */
        private val completeFunction: BiFunction<Player, String, Response>?
) {
    /**
     * The ItemStack that is in the [Slot.INPUT_LEFT] slot.
     */
    private var insert: ItemStack? = null
    /**
     * The container id of the inventory, used for NMS methods
     */
    private var containerId = 0
    /**
     * Returns the Bukkit inventory for this anvil gui
     * @return the [Inventory] for this anvil gui
     */
    /**
     * The inventory that is used on the Bukkit side of things
     */
    var inventory: Inventory? = null
        private set
    /**
     * The listener holder class
     */
    private val listener = ListenUp()
    /**
     * Represents the state of the inventory being open
     */
    private var open = false


    /**
     * Opens the anvil GUI
     */
    private fun openInventory() {
        val paper = ItemStack(Material.PAPER)
        val paperMeta = paper.itemMeta
        paperMeta.displayName = text
        paper.itemMeta = paperMeta
        insert = paper
        CraftEventFactory.handleInventoryCloseEvent(toNMS(player))
        toNMS(player).activeContainer = toNMS(player).defaultContainer
        Bukkit.getPluginManager().registerEvents(listener, plugin)
        val container: Any = AnvilContainer(toNMS(player))
        inventory = (container as Container).bukkitView.topInventory
        inventory!!.setItem(Slot.INPUT_LEFT, insert)
        containerId = toNMS(player).nextContainerCounter()
        toNMS(player).playerConnection.sendPacket(PacketPlayOutOpenWindow(containerId, "minecraft:anvil", ChatMessage(Blocks.ANVIL.a() + ".name")))
        toNMS(player).activeContainer = container
        container.windowId = containerId
        container.addSlotListener(toNMS(player))
        open = true
    }

    /**
     * Closes the inventory if it's open.
     *
     * @throws IllegalArgumentException If the inventory isn't open
     */
    fun closeInventory() {
        Validate.isTrue(open, "You can't close an inventory that isn't open!")
        open = false
        CraftEventFactory.handleInventoryCloseEvent(toNMS(player))
        toNMS(player).activeContainer = toNMS(player).defaultContainer
        toNMS(player).playerConnection.sendPacket(PacketPlayOutCloseWindow(containerId))
        HandlerList.unregisterAll(listener)
        closeListener?.accept(player)
    }

    /**
     * Simply holds the listeners for the GUI
     */
    private inner class ListenUp : Listener {
        @EventHandler
        fun onInventoryClick(event: InventoryClickEvent) {
            if (event.inventory == inventory && event.rawSlot < 3) {
                event.isCancelled = true
                val clicker = event.whoClicked as Player
                if (event.rawSlot == Slot.OUTPUT) {
                    val clicked = inventory!!.getItem(Slot.OUTPUT)
                    if (clicked == null || clicked.type == Material.AIR) return
                    val response = completeFunction!!.apply(clicker, if (clicked.hasItemMeta()) clicked.itemMeta.displayName else "")
                    if (response.text != null) {
                        val meta = clicked.itemMeta
                        meta.displayName = response.text
                        clicked.itemMeta = meta
                        inventory!!.setItem(Slot.INPUT_LEFT, clicked)
                    } else {
                        closeInventory()
                    }
                }
            }
        }

        @EventHandler
        fun onInventoryClose(event: InventoryCloseEvent) {
            if (open && event.inventory == inventory) {
                closeInventory()
                if (preventClose) {
                    Bukkit.getScheduler().runTask(plugin) { openInventory() }
                }
            }
        }
    }

    /**
     * A builder class for an [AnvilGUI] object
     */
    class Builder {
        /**
         * An [Consumer] that is called when the anvil GUI is closed
         */
        private var closeListener: Consumer<Player>? = null
        /**
         * A state that decides where the anvil GUI is able to be closed by the user
         */
        private var preventClose = false
        /**
         * An [BiFunction] that is called when the anvil output slot has been clicked
         */
        private var completeFunction: BiFunction<Player, String, Response>? = null
        /**
         * The [Plugin] that this anvil GUI is associated with
         */
        private var plugin: Plugin? = null
        /**
         * The text that will be displayed to the user
         */
        private var text = ""

        /**
         * Prevents the closing of the anvil GUI by the user
         * @return The [Builder] instance
         */
        fun preventClose(): Builder {
            preventClose = true
            return this
        }

        /**
         * Listens for when the inventory is closed
         * @param closeListener An [Consumer] that is called when the anvil GUI is closed
         * @return The [Builder] instance
         * @throws IllegalArgumentException when the closeListener is null
         */
        fun onClose(closeListener: Consumer<Player>?): Builder {
            Validate.notNull(closeListener, "closeListener cannot be null")
            this.closeListener = closeListener
            return this
        }

        /**
         * Handles the inventory output slot when it is clicked
         * @param completeFunction An [BiFunction] that is called when the user clicks the output slot
         * @return The [Builder] instance
         * @throws IllegalArgumentException when the completeFunction is null
         */
        fun onComplete(completeFunction: BiFunction<Player, String, Response>?): Builder {
            Validate.notNull(completeFunction, "Complete function cannot be null")
            this.completeFunction = completeFunction
            return this
        }

        /**
         * Sets the plugin for the [AnvilGUI]
         * @param plugin The [Plugin] this anvil GUI is associated with
         * @return The [Builder] instance
         * @throws IllegalArgumentException if the plugin is null
         */
        fun plugin(plugin: Plugin?): Builder {
            Validate.notNull(plugin, "Plugin cannot be null")
            this.plugin = plugin
            return this
        }

        /**
         * Sets the text that is to be displayed to the user
         * @param text The text that is to be displayed to the user
         * @return The [Builder] instance
         * @throws IllegalArgumentException if the text is null
         */
        fun text(text: String): Builder {
            Validate.notNull(text, "Text cannot be null")
            this.text = text
            return this
        }

        /**
         * Creates the anvil GUI and opens it for the player
         * @param player The [Player] the anvil GUI should open for
         * @return The [AnvilGUI] instance from this builder
         * @throws IllegalArgumentException when the onComplete function, plugin, or player is null
         */
        fun open(player: Player): AnvilGUI {
            Validate.notNull(plugin, "Plugin cannot be null")
            Validate.notNull(completeFunction, "Complete function cannot be null")
            Validate.notNull(player, "Player cannot be null")
            return AnvilGUI(plugin, player, text, preventClose, closeListener, completeFunction)
        }
    }

    /**
     * Represents a response when the player clicks the output item in the anvil GUI
     */
    class Response
    /**
     * Creates a response to the user's input
     * @param text The text that is to be displayed to the user, which can be null to close the inventory
     */ private constructor(
            /**
             * The text that is to be displayed to the user
             */
            val text: String?) {
        /**
         * Gets the text that is to be displayed to the user
         * @return The text that is to be displayed to the user
         */

        companion object {
            /**
             * Returns an [Response] object for when the anvil GUI is to close
             * @return An [Response] object for when the anvil GUI is to close
             */
            fun close(): Response {
                return Response(null)
            }

            /**
             * Returns an [Response] object for when the anvil GUI is to display text to the user
             * @param text The text that is to be displayed to the user
             * @return An [Response] object for when the anvil GUI is to display text to the user
             */
            fun text(text: String?): Response {
                return Response(text)
            }
        }

    }

    /**
     * Class wrapping the magic constants of slot numbers in an anvil GUI
     */
    object Slot {
        /**
         * The slot on the far left, where the first input is inserted. An [ItemStack] is always inserted
         * here to be renamed
         */
        const val INPUT_LEFT = 0
        /**
         * Not used, but in a real anvil you are able to put the second item you want to combine here
         */
        const val INPUT_RIGHT = 1
        /**
         * The output slot, where an item is put when two items are combined from [.INPUT_LEFT] and
         * [.INPUT_RIGHT] or [.INPUT_LEFT] is renamed
         */
        const val OUTPUT = 2
    }

    private fun toNMS(player: Player): EntityPlayer {
        return (player as CraftPlayer).handle
    }

    private inner class AnvilContainer(entityhuman: EntityHuman) : ContainerAnvil(entityhuman.inventory, entityhuman.world, BlockPosition(0, 0, 0), entityhuman) {
        override fun a(human: EntityHuman): Boolean {
            return true
        }
    }

    /**
     * Create an AnvilGUI and open it for the player.
     *
     * @param plugin A [org.bukkit.plugin.java.JavaPlugin] instance
     * @param player The [Player] to open the inventory for
     * @param text What to have the text already set to
     * @param preventClose Whether to prevent the inventory from closing
     * @param closeListener A [Consumer] when the inventory closes
     * @param completeFunction A [BiFunction] that is called when the player clicks the [Slot.OUTPUT] slot
     */
    init {
        openInventory()
    }
}