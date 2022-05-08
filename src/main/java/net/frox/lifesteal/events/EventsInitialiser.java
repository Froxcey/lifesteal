package net.frox.lifesteal.events;

import net.frox.lifesteal.Lifesteal;
import org.bukkit.plugin.java.JavaPlugin;

public class EventsInitialiser {

    public static void init(){

        JavaPlugin main = Lifesteal.plugin;

        main.getServer().getPluginManager().registerEvents(new KillEvent(), main);
        main.getServer().getPluginManager().registerEvents(new PlayerJoinEvent(), main);
        main.getServer().getPluginManager().registerEvents(new RightClickEvent(), main);

    }

}
