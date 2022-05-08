package net.frox.lifesteal.commands.completers;

import net.frox.lifesteal.elimination.EliminatedPlayer;
import net.frox.lifesteal.elimination.EliminationManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class LsadminCompleter implements TabCompleter {
    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

        if (!sender.isOp()) return Collections.singletonList("");
        if (args.length == 1){
            final String[] COMMANDS = {"config", "eliminate", "help", "reset", "revive", "set"};
            ArrayList<String> completerList = new ArrayList<>();
            for (String cmd : COMMANDS) {
                if (cmd.startsWith(args[0])){
                    completerList.add(cmd);
                }
            }
            return completerList;
        }
        if (args.length == 2){
            ArrayList<String> playerList = new ArrayList<>();
            switch (args[0]){
                case "give":
                case "heart":
                case "reset":
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                            playerList.add(player.getName());
                    }
                    return playerList;
                case "eliminate":
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                            playerList.add(player.getName());
                    }
                    for (@NotNull OfflinePlayer player : Bukkit.getOfflinePlayers()) {
                        if (player.getName().toLowerCase().startsWith(args[1].toLowerCase()))
                            playerList.add(player.getName());
                    }
                    return playerList;
                case "revive":
                    if (EliminationManager.list() == null) {
                        return Collections.singletonList("");
                    }
                    for (EliminatedPlayer player : EliminationManager.list()){
                        if (Bukkit.getOfflinePlayer(player.uuid).getName().toLowerCase().startsWith(args[1].toLowerCase()))
                            playerList.add(Bukkit.getOfflinePlayer(player.uuid).getName());
                    }
                    return playerList;
                case "config":
                    return Arrays.asList("maxHealth", "environmentLifesteal");
                default:
                    return Collections.singletonList("");
            }
        }
        return Collections.singletonList("");
    }
}
