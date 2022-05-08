package net.frox.lifesteal.events;

import net.frox.lifesteal.HealthManager;
import net.frox.lifesteal.ItemManager;
import net.frox.lifesteal.Lifesteal;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

public class KillEvent implements Listener {

    static Plugin Main = Lifesteal.plugin;

    @EventHandler
    public static void onPlayerKilled(PlayerDeathEvent event){

        FileConfiguration config = Main.getConfig();
        Player player = event.getPlayer();

        if (!config.getBoolean("enabled")) return;

        if (event.getPlayer().getKiller() != null){

            HealthManager.decreaseHealth(player, player.getKiller().getName());
            boolean isAtMax = HealthManager.increaseHealth(player.getKiller());

            // Drop a heart if killer is at max health
            if (isAtMax) {
                event.getDrops().add(ItemManager.heart);
            }

        } else if (config.getBoolean("environmentLifesteal")) {
            Main.getServer().getConsoleSender().sendMessage(String.valueOf(config.getBoolean("environmentLifesteal")));
            // Environmental kill
            HealthManager.decreaseHealth(player, null);

        }
    }
}
