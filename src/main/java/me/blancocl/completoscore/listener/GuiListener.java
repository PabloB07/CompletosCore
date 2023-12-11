package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.task.ButterflyTask;
import me.blancocl.completoscore.util.Hex;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import me.blancocl.completoscore.CompletosCore;

public final class GuiListener implements Listener {

	@EventHandler
	public void onClick(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		ButterflyTask instance = (ButterflyTask) ButterflyTask.getInstance();

		if (player.hasMetadata("OpenedMenu")) {
			event.setCancelled(true);
			if (event.getSlot() == 13) {
				if (instance.hasPlayer(player.getUniqueId())) {
					instance.removePlayer(player.getUniqueId());
					player.sendMessage(Hex.message("#a83240Ya no estás usando el efecto Mariposa!"));

				} else {
					instance.addPlayer(player.getUniqueId());
					player.sendMessage(Hex.message("#a87b32Estás usando el efecto Mariposa!"));
				}
				player.closeInventory();
			}
			if (event.getSlot() == 11) {
				player.sendMessage(Hex.message("#a83240No implementado aún!"));
				player.closeInventory();
			}
		}
			if (event.getSlot() == 15) {
				player.sendMessage(Hex.message("#a83240No implementado aún!"));
				player.closeInventory();
			}
		}

	@EventHandler
	public void onClose(InventoryCloseEvent event) {
		Player player = (Player) event.getPlayer();

		if (player.hasMetadata("OpenedMenu"))
			player.removeMetadata("OpenedMenu", CompletosCore.getInstance());
	}
}
