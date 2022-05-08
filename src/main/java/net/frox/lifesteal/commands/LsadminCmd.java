package net.frox.lifesteal.commands;

import net.frox.lifesteal.Lifesteal;
import org.bukkit.Bukkit;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class LsadminCmd implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!sender.isOp()){
            sender.sendMessage("You don't have permission to do that!");
            return false;
        }
        if (args.length == 0) {
            sender.sendMessage("Send /lsadmin help to get help");
            return true;
        }
        AttributeInstance playerHealthCap = ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH);
        switch (args[0]){
            case "reset":
                if (args.length != 2){
                    ((Player) sender).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                    return false;
                }
                Bukkit.getPlayer(args[1]).getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20);
                break;
            case "revive":
                if (args.length != 2){
                    sender.sendMessage("Invalid arguments. Usage: /lsadmin [Player]");
                    return false;
                }
                break;
            case "help":
                sender.sendMessage("/lsadmin");
                sender.sendMessage("    /lifesteal");
                break;
            default:
                sender.sendMessage("Send /lsadmin help to get help");
                break;
        }
        return false;
    }
}
