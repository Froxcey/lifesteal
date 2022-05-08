package net.frox.lifesteal;

import net.frox.lifesteal.commands.*;
import net.frox.lifesteal.events.EventsInitialiser;
import org.bukkit.plugin.java.JavaPlugin;

public final class Lifesteal extends JavaPlugin {

    public static JavaPlugin plugin;

    @Override
    public void onEnable() {

        plugin = this;
        EventsInitialiser.init();
        ItemManager.init();
        CommandsManager.init();
        getServer().getConsoleSender().sendMessage("Lifesteal is enabled, welcome to hell!");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
