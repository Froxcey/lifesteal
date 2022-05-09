package net.frox.lifesteal.events;

import net.frox.lifesteal.elimination.EliminatedPlayer;
import net.frox.lifesteal.elimination.EliminationManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.Iterator;
import java.util.List;

public class PlayerJoinEvent implements Listener {
    @EventHandler
    public static void onPlayerJoin(org.bukkit.event.player.PlayerJoinEvent event){

        Player player = event.getPlayer();

        String killer = null;

        // Check if banned
        boolean canJoin = true;
        if (EliminationManager.list() == null) return;
        List<EliminatedPlayer> eliminatedPlayerList = EliminationManager.list();
        Iterator<EliminatedPlayer> itr = eliminatedPlayerList.iterator();
        while (itr.hasNext() && canJoin) {
            EliminatedPlayer itrPlayer = itr.next();
            if (itrPlayer.uuid == player.getUniqueId()){
                canJoin = false;
                killer = itrPlayer.killer;
            }
        }
        if (!canJoin) {
            if(player.isOp()){
                player.setGameMode(GameMode.SPECTATOR);
                player.sendMessage("You got eliminated by " + killer);
            } else {
                player.kickPlayer("You got eliminated by " + killer + "\nYou can tell someone to revive you by doing /lifesteal revive " + player.getName());
            }

            return;
        }

        player.setResourcePack("https://github.com/froxcey/lifesteal/raw/main/Pack.zip", "3dd7c3c7a5eab80ce6b94c9cd4be371f4bcef1b8");
    }
}
