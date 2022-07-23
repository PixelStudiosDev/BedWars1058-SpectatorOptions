package me.cubecrafter.spectatoroptions.menus;

import lombok.RequiredArgsConstructor;
import me.cubecrafter.spectatoroptions.utils.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public abstract class Menu implements InventoryHolder {

    protected final Player player;
    private Inventory inventory;
    public abstract String getTitle();
    public abstract int getRows();
    public abstract Map<Integer, MenuItem> getItems();

    public void openMenu() {
        updateMenu();
        player.openInventory(getInventory());
    }

    public void closeMenu() {
        player.closeInventory();
    }

    public void updateMenu() {
        getInventory().clear();
        for (Map.Entry<Integer, MenuItem> entry : getItems().entrySet()) {
            getInventory().setItem(entry.getKey(), entry.getValue().getItem());
        }
    }

    public void setFiller(ItemStack filler, List<Integer> slots) {
        slots.forEach(slot -> getInventory().setItem(slot, filler));
    }

    @Override
    public Inventory getInventory() {
        if (inventory == null) inventory = Bukkit.createInventory(this, getRows() * 9, TextUtil.color(getTitle()));
        return inventory;
    }

}
