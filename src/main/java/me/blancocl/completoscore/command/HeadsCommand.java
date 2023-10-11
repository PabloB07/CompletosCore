package me.blancocl.completoscore.command;

import me.blancocl.completoscore.command.inv.HeadsInvCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public final class HeadsCommand implements CommandExecutor {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Solo jugadores pueden usar este comando!");

			return true;
		}

		Player player = (Player) sender;
		/*
		Inventory inventory = Bukkit.createInventory(player, 9 * 3, Hex.message("#4287f5Menu de Cosmeticos"));

		ItemStack beeButton = new ItemStack(Material.POTION);
		ItemMeta beeMeta = beeButton.getItemMeta();
		beeMeta.setDisplayName(Hex.message("#a87b32Particulas"));
		beeMeta.setLore(Arrays.asList(Hex.message(""), Hex.message("#4287f5Particulas."), Hex.message("#51f542(Click para abrir el menu)")));
		beeButton.setItemMeta(beeMeta);

		ItemStack trajesButton = new ItemStack(Material.LEATHER_CHESTPLATE);
		ItemMeta trajesMeta = trajesButton.getItemMeta();
		trajesMeta.setDisplayName(Hex.message("#a87b32Trajes"));
		trajesMeta.setLore(Arrays.asList(Hex.message(""), Hex.message("#4287f5Trajes de colores con efectos."), Hex.message("#51f542(Click para abrir el menu)")));
		trajesButton.setItemMeta(trajesMeta);

		ItemStack crawlButton = new ItemStack(Heads.getSkull("http://textures.minecraft.net/texture/3deff0e11eef14d9349fc48a382fbb62c1bba504e6b4c19c9b6360ea72254f32"));
		ItemMeta crawlMeta = crawlButton.getItemMeta();
		crawlMeta.setDisplayName(Hex.message("#a87b32Cabezas"));
		crawlMeta.setLore(Arrays.asList(Hex.message(""), Hex.message("#4287f5Cabezas"), Hex.message("#51f542(Click para abrir el menu)")));
		crawlButton.setItemMeta(crawlMeta);

		*/

		new HeadsInvCommand().open(player);
		/*
		player.setMetadata("OpenedMenu", new FixedMetadataValue(CompletosCore.getInstance(), "Menu de Cosmeticos"));
		inventory.setItem(11, crawlButton);
		inventory.setItem(13, beeButton);
		inventory.setItem(15, trajesButton);
		*/
		return true;
	}
}