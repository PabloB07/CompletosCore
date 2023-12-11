package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.CompletosCore;
import me.blancocl.completoscore.util.Hex;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityTargetLivingEntityEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.Map;

public class PetListener implements Listener {
    private static Map<LivingEntity, Player> pets = new HashMap<>();
    private final CompletosCore plugin;

    public PetListener(CompletosCore plugin) {

        this.plugin = plugin;
    }
    @EventHandler
    public void onPlayerInteractEntity(EntityTargetLivingEntityEvent event) {
        Entity entity = event.getEntity();
        if (entity instanceof LivingEntity) {
            LivingEntity pet = (LivingEntity) entity;
            Player player = pets.get(pet);

            if (player != null) {
                event.setTarget(player);
            }
        }
    }

    public static class PetAIUpdater extends BukkitRunnable {
        @Override
        public void run() {
            for (Player player : Bukkit.getOnlinePlayers()) {
                // Crea una mascota para cada jugador si no tiene una
                if (!pets.containsValue(player)) {
                    Location playerLocation = player.getLocation();
                    // Reemplaza 'EntityType' con el tipo de entidad que deseas usar como mascota
                    LivingEntity pet = (LivingEntity) playerLocation.getWorld().spawnEntity(playerLocation, EntityType.PARROT);
                    pet.setCustomName(Hex.message("#a87b32FIU"));
                    pet.canBreatheUnderwater();
                    pet.setRemoveWhenFarAway(false);
                    pet.setCustomNameVisible(true);
                    pets.put(pet, player);
                }
            }
        }
    }
}
