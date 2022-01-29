package pv.ajuda.Inventory;

import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;

public class ItemBuilder {

    public static ItemStack getSkull(String skullName, String itemName, int ammount, List<String> lore) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();
        meta.setOwner(skullName);
        meta.setDisplayName(itemName);
        meta.setLore(lore);
        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack getItem(Material material,String name, int ammount, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setAmount(ammount);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(Material material,String name, int ammount, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        List<String> loreList = new ArrayList<>();
        for(String lores : lore)
            loreList.add(lores);
        meta.setLore(loreList);
        item.setAmount(ammount);
        item.setItemMeta(meta);
        return item;
    }

    public static ItemStack getItem(Material material,String name, int ammount) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        item.setAmount(ammount);
        item.setItemMeta(meta);
        return item;
    }
}
