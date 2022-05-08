package net.frox.lifesteal.commands;

import net.frox.lifesteal.Lifesteal;
import net.frox.lifesteal.commands.completers.LifestealCompleter;
import net.frox.lifesteal.commands.completers.LsadminCompleter;

public class CommandsManager {
    public static void init(){
        Lifesteal.plugin.getCommand("lsadmin").setExecutor(new LsadminCmd());
        Lifesteal.plugin.getCommand("lsadmin").setTabCompleter(new LsadminCompleter());
        Lifesteal.plugin.getCommand("lifesteal").setExecutor(new LifestealCmd());
        Lifesteal.plugin.getCommand("lifesteal").setTabCompleter(new LifestealCompleter());
    }
}
