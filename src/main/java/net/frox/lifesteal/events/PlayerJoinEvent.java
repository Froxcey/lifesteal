package net.frox.lifesteal.events;

import net.frox.lifesteal.elimination.EliminationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.UUID;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public static void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){

        Player player = event.getPlayer();

        // Check if banned
        if (EliminationManager.list() == null) return;
        ArrayList<UUID> eliminatedPlayerList = EliminationManager.list();
        for (UUID playerUUID : eliminatedPlayerList) {
            if (playerUUID.compareTo(player.getUniqueId()) == 0) {
                String killer = EliminationManager.getReason(playerUUID);
                if (killer == null){
                    player.kickPlayer("Dunno why you're eliminated, but you are for sure.");
                    return;
                }
                player.kickPlayer("You got eliminated by " + killer + "\nYou can tell someone to revive you by doing /lifesteal revive " + player.getName());
                return;
            }
        }

        player.setResourcePack("https://github.com/froxcey/lifesteal/raw/main/Pack.zip", "9a19919e8776b3536808f381a32f0ba2a0b82637");
    }
}
