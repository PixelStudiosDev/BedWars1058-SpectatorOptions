package me.cubecrafter.spectatoroptions.utils;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Menu {

    private final Inventory inv;

    public Menu(int size, String title){
        inv = Bukkit.createInventory(null, size, title);
    }

    public void setItem(String material, int slot, boolean glow, String name, List<String> lore){
        ItemStack item = XMaterial.matchXMaterial(material).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(glow){
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        inv.setItem(slot, item);
    }

    public void setItem(String material, int slot, boolean glow, String name, List<String> lore, String nbt){
        ItemStack item = XMaterial.matchXMaterial(material).get().parseItem();
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        if(glow){
            meta.addEnchant(Enchantment.DURABILITY, 1, true);
            meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        }
        item.setItemMeta(meta);
        if (nbt != null) {
            NBTItem ni = new NBTItem(item);
            ni.setString("id", nbt);
            item = ni.getItem();
        }
        inv.setItem(slot, item);
    }

    public void openMenu(Player p){
        p.openInventory(inv);
    }

    public Inventory getMenu(){
        return inv;
    }

}
