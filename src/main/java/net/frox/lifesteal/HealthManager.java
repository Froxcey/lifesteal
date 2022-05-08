package net.frox.lifesteal;

import net.frox.lifesteal.elimination.EliminationManager;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

import javax.annotation.Nullable;

public class HealthManager {

    // Decrease player's health cap, and eliminate if needed
    public static void decreaseHealth(Player player, @Nullable String killerName){
        AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        if (playerMaxHealth.getBaseValue() <= 2){
            if (Lifesteal.plugin.getConfig().getBoolean("elimination"))
                EliminationManager.add(player.getUniqueId(), killerName);
        } else {
            playerMaxHealth.setBaseValue(playerMaxHealth.getBaseValue() - 2);
        }
    }

    // Increase player's health cap, return true if max is reached
    public static boolean increaseHealth(Player player){
        AttributeInstance playerMaxHealth = player.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double MaxHealth = Lifesteal.plugin.getConfig().getDouble("maxHealth");
        if ( MaxHealth != 0 && playerMaxHealth.getBaseValue() >= MaxHealth) return true;
        playerMaxHealth.setBaseValue(playerMaxHealth.getBaseValue() + 2);
        return false;
    }
}
