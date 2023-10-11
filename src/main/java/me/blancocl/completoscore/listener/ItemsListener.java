package me.blancocl.completoscore.listener;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

public final class ItemsListener implements Listener {

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        PlayerInventory inventory = player.getInventory();

        // Crear un nuevo ItemStack con el material y cantidad deseada
        ItemStack item = new ItemStack(Material.PAPER, 1);

        // Puedes personalizar el ItemStack según tus necesidades
        // Por ejemplo, establecer un nombre personalizado
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName("Mi Papel");
        item.setItemMeta(meta);

        // Agregar el ItemStack al inventario del jugador
        inventory.addItem(item);

        // También puedes enviar un mensaje al jugador para notificarle
        player.sendMessage("¡Bienvenido al servidor! Se te ha dado un papel.");
    }

}
