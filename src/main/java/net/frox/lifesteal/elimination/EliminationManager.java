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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


public class EliminationManager {

    static Plugin Main = Lifesteal.plugin;
    private static FileConfiguration listConfig = null;
    private static File file;

    private static void reload() {
        file = new File(Main.getDataFolder(), "banlist.yml");
        if (!file.exists()) {
            Main.saveResource("banlist.yml", false);
            file = new File(Main.getDataFolder(), "banlist.yml");
        }
        listConfig = YamlConfiguration.loadConfiguration(file);
    }

    public static ArrayList<UUID> list(){

        reload();

        List<String> eliminatedList = (List<String>) listConfig.getList("EliminatedList");
        ArrayList<UUID> eliminatedUUID = new ArrayList<UUID>();
        for(String playerId: eliminatedList){
            if (playerId == null) continue;
            eliminatedUUID.add(UUID.fromString(playerId));
        };
        return eliminatedUUID;

    }

    public static String getReason(UUID uuid){

        reload();

        return listConfig.getString(String.valueOf(uuid));

    }

    public static void add(UUID uuid, @Nullable String killer){

        if (Bukkit.getPlayer(uuid).isOp()) {
            Bukkit.getPlayer(uuid).setGameMode(GameMode.SPECTATOR);
            Bukkit.getPlayer(uuid).sendMessage("You got eliminated by " + killer);
        } else {
            reload();
            Bukkit.getPlayer(uuid).kickPlayer("You got eliminated by " + killer + "\nYou can tell someone to revive you by doing /lifesteal revive " + Bukkit.getPlayer(uuid).getName());
            List<String> eliminatedPlayerList = (List<String>) listConfig.getList("EliminatedList");
            eliminatedPlayerList.add(String.valueOf(uuid));
            listConfig.set(String.valueOf(uuid), killer);
            try {
                listConfig.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void remove(UUID uuid){
        reload();
        listConfig.set(String.valueOf(uuid), null);
        List<String> eliminatedPlayerList = (List<String>) listConfig.getList("EliminatedList");
        if (eliminatedPlayerList == null) return;
        for (Iterator<String> iterator = eliminatedPlayerList.iterator(); iterator.hasNext(); ) {
            String value = iterator.next();
            if (value == null) continue;
            if (value.equals(uuid.toString())) {
                iterator.remove();
            }
        }

        for (String playerid :
                eliminatedPlayerList) {
            if (playerid == null) continue;
            if (playerid.equals(uuid.toString())) eliminatedPlayerList.remove(playerid);
        }
        try {
            listConfig.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
