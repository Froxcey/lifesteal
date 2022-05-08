package net.frox.lifesteal.commands;

import net.frox.lifesteal.HealthManager;
import net.frox.lifesteal.ItemManager;
import net.frox.lifesteal.Lifesteal;
import net.frox.lifesteal.elimination.EliminatedPlayer;
import net.frox.lifesteal.elimination.EliminationManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LifestealCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return false;
        }

        if (args.length == 0) {
            sender.sendMessage("Do /lifesteal help to get help");
            return false;
        }

        AttributeInstance playerHealthCap = ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        FileConfiguration config = Lifesteal.plugin.getConfig();

        switch (args[0]){
            case "withdraw":

                if (playerHealthCap.getBaseValue() <= 2) {
                    // Player is already at one heart cap
                    sender.sendMessage("You're already on one heart!");
                } else {
                    playerHealthCap.setBaseValue(playerHealthCap.getBaseValue() - 2);
                    ((Player) sender).getInventory().addItem(ItemManager.heart);
                }
                return true;

            case "revive":

                if (args.length != 2){
                    sender.sendMessage("Invalid arguments. Usage: /lsadmin [Player]");
                    return false;
                }

                if (EliminationManager.list() == null){
                    sender.sendMessage("This player is not found, please use tab completion");
                    return true;
                }

                for (EliminatedPlayer player : EliminationManager.list()){
                    if (Bukkit.getOfflinePlayer(player.uuid).getName() == args[1]){
                        if (!config.getBoolean("elimination") && ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).getBaseValue() <= 2) return true;
                        HealthManager.decreaseHealth(((Player) sender), "reviving " + Bukkit.getOfflinePlayer(player.uuid).getName());
                        EliminationManager.remove(player.uuid);
                    }
                }
                sender.sendMessage("This player is not found, please use tab completion");
                return true;

            case "help":
                sender.sendMessage("/lifesteal");
                sender.sendMessage("    withdraw: get a heart from heart bar");
                sender.sendMessage("    revive [player]: give a heart to an eliminated player");
                sender.sendMessage("    help: show this help message");
                sender.sendMessage("    about: show plugin information");
                return true;
            case "about":
                sender.sendMessage("================================================");
                sender.sendMessage("Another lifesteal plugin. Welcome to hell!");
                sender.sendMessage("Version: " + Lifesteal.plugin.getDescription().getVersion());
                sender.sendMessage("Made by Froxcey, all rights reserved");
                sender.sendMessage("Source available on github.com/froxcey/lifesteal");
                sender.sendMessage("================================================");
                break;
            default:
                sender.sendMessage("Do /lifesteal help to get help");
                break;
        }
        return false;
    }
}
