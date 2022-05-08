package net.frox.lifesteal.events;

import net.frox.lifesteal.HealthManager;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

public class RightClickEvent implements Listener {

    @EventHandler
    public static void onPlayerInteract (PlayerInteractEvent event){

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if (event.getHand() != EquipmentSlot.HAND) return;

        ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getItemMeta() == null) return;
        if (!item.getItemMeta().getDisplayName().equals("Heart")) return;
        if (item.getType() != Material.RED_DYE) return;
        if (!item.getItemMeta().hasEnchant(Enchantment.LUCK)) return;
        if (!HealthManager.increaseHealth(event.getPlayer())){
            if (event.getPlayer().getInventory().getItemInMainHand().getAmount() > 1) {
                event.getPlayer().getInventory().getItemInMainHand().setAmount(event.getPlayer().getInventory().getItemInMainHand().getAmount() - 1);
            } else {
                event.getPlayer().getInventory().setItemInMainHand(null);
            }
        }


    }

}
