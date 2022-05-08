package net.frox.lifesteal.elimination;

import net.frox.lifesteal.Lifesteal;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;


public class EliminationManager {

    static Plugin Main = Lifesteal.plugin;
    private static FileConfiguration listConfig = null;
    private static File file;

    private static void reload() {
        file = new File(Main.getDataFolder(), "banlist.yml");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        listConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static List<EliminatedPlayer> list(){

        reload();
        return (List<EliminatedPlayer>) listConfig.getList("eliminatedList");

    }

    public static FileConfiguration get(){

        reload();
        return listConfig;

    }

    public static void add(UUID uuid, @Nullable String killer){

        reload();
        List<EliminatedPlayer> eliminatedPlayerList = (List<EliminatedPlayer>) listConfig.getList("eliminatedList");
        EliminatedPlayer newEliminated = new EliminatedPlayer() {};
        if (killer == null){
            newEliminated.killer = "environment";
        } else {
            newEliminated.killer = killer;
        }
        newEliminated.uuid = uuid;
        eliminatedPlayerList.add(newEliminated);
        try {
            listConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Bukkit.getPlayer(uuid).isOp()) {
            Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
            Bukkit.getPlayer(uuid).sendMessage("You got eliminated by " + killer);
        } else {
            Bukkit.getPlayer(uuid).kickPlayer("You got eliminated by " + killer);
        }
    }

    public static void remove(UUID uuid){
        reload();
        List<EliminatedPlayer> eliminatedPlayerList = (List<EliminatedPlayer>) listConfig.getList("eliminatedList");
        assert eliminatedPlayerList != null;
        eliminatedPlayerList.removeIf(player -> player.uuid.equals(uuid));
        try {
            listConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
