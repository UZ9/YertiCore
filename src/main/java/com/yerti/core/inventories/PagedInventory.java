package com.yerti.core.inventories;

import com.yerti.core.items.XMaterial;
import com.yerti.core.menus.MenuItem;
import com.yerti.core.utils.SkullUtils;
import org.bukkit.entity.Player;

import java.util.*;

public class PagedInventory {
    private List<CustomInventory> inventories;
    private Player player;

    private static Map<UUID, Integer> currentPages = new HashMap<>();

    public PagedInventory(Player player) {
        this.inventories = new ArrayList<>();
        this.player = player;
    }

    public void addInventory(CustomInventory inventory) {
        inventories.add(inventory);
    }

    public void open() {
        if (inventories.size() > 0) {
            currentPages.put(player.getUniqueId(), 0);
            player.openInventory(inventories.get(0).getInventory());
        }
    }

    public void addFooter(int index) {
        CustomInventory inventory = inventories.get(index);

        inventory.fill(inventory.getSlots() - 1 - 7, inventory.getSlots() - 1, XMaterial.GRAY_STAINED_GLASS_PANE.parseItem());

        //itemFromName is deprecated as it's off of name, but this is a Mojang controlled skin so the likelihood if it ever changing is slim
        if (index + 1 < inventories.size())
        inventory.setItem(inventory.getSlots() - 2, new MenuItem(SkullUtils.itemFromName("MHF_ArrowRight"), c -> player.getOpenInventory().getTopInventory().setContents(inventories.get(currentPages.get(player.getUniqueId()) + 1).getInventory().getContents())));

        if (index - 1 >= 0)
        inventory.setItem(inventory.getSlots() - 6, new MenuItem(SkullUtils.itemFromName("MHF_ArrowLeft"), c -> player.getOpenInventory().getTopInventory().setContents(inventories.get(currentPages.get(player.getUniqueId()) - 1).getInventory().getContents())));
    }


}
