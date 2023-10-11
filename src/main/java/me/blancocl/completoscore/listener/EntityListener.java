package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.CompletosCore;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.ChatColor;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import me.blancocl.completoscore.hook.VaultHook;
import me.blancocl.completoscore.util.Keys;


import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class EntityListener implements Listener {

	private Map<UUID, PermissionAttachment> permissions = new HashMap<>();
	
	@EventHandler
	public void onEntityKill(EntityDeathEvent event) {
		Player killer = event.getEntity().getKiller();

		if (killer != null && event.getEntity() instanceof Sniffer) {
			VaultHook.deposit(killer, 1);

			killer.sendMessage(ChatColor.GOLD + "Has ganado 1 completo!");
		}
	}
}
