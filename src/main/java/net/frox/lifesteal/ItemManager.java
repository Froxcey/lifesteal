package net.frox.lifesteal;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemManager {

    public static ItemStack heart;

    public static void init(){
        createHeart();
    }

    private static void createHeart() {
        ItemStack item = new ItemStack(Material.RED_DYE, 1);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName("Heart");
        itemMeta.setCustomModelData(15469);
        item.setItemMeta(itemMeta);
        item.addUnsafeEnchantment(Enchantment.LUCK, 1);
        item.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        heart = item;
    }
}
