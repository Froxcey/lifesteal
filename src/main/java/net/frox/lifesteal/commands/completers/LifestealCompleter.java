package net.frox.lifesteal.commands.completers;

import net.frox.lifesteal.elimination.EliminatedPlayer;
import net.frox.lifesteal.elimination.EliminationManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LifestealCompleter implements TabCompleter {

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {
        if (args.length == 1){
            final String[] COMMANDS = {"withdraw", "revive", "help", "about"};
            ArrayList<String> completerList = new ArrayList<>();
            for (String cmd : COMMANDS) {
                if (cmd.startsWith(args[0])){
                    completerList.add(cmd);
                }
            }
            return completerList;
        }
        if (args.length == 2 && args[0].equals("revive")){
            ArrayList<String> playerList = new ArrayList<>();
            if (EliminationManager.list() == null) {
                return Collections.singletonList("");
            }
            for (EliminatedPlayer player : EliminationManager.list()) {
                String cprName = Bukkit.getOfflinePlayer(player.uuid).getName();
                if (cprName.toLowerCase().startsWith(args[1].toLowerCase())){
                    playerList.add(cprName);
                }
            }
            return playerList;
        }
        return Collections.singletonList("");
    }
}
