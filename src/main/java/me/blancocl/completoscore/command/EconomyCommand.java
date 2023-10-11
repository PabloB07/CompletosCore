package me.blancocl.completoscore.command;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import me.blancocl.completoscore.CompletosCore;
import me.blancocl.completoscore.hook.VaultHook;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public final class EconomyCommand implements CommandExecutor, TabCompleter {

	@Override
	public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {

		if (!(sender instanceof Player)) {
			sender.sendMessage("Solo los jugadores pueden usar este comando!");

			return true;
		}

		if (args.length < 2)
			return false;

		String param = args[0];
		String playerName = args[1];
		String amountRaw = args.length == 3 ? args[2] : "";

		new BukkitRunnable() {

			@Override
			public void run() {
				OfflinePlayer target = Bukkit.getOfflinePlayer(playerName);

				if (!target.hasPlayedBefore()) {
					sender.sendMessage(ChatColor.RED + "Jugador" + playerName + " nunca ha jugado en este server.");

					return;
				}

				new BukkitRunnable() {

					@Override
					public void run() {

						if (!VaultHook.hasEconomy()) {
							sender.sendMessage(ChatColor.RED + "Vault no encontrado");

							return;
						}

						if ("ver".equals(param)) {
							double balance = VaultHook.getBalance(target);

							sender.sendMessage(ChatColor.GOLD + target.getName() + " Tus Completos son: " + VaultHook.formatCurrencySymbol(balance));

						} else if ("tomar".equals(param) || "dar".equals(param)) {
							double amount;

							try {
								amount = Double.parseDouble(amountRaw);

							} catch (NumberFormatException e) {
								sender.sendMessage("Debe ser decimal el monto: '" + args[1] + "'.");

								return;
							}

							if ("tomar".equals(param)) {
								String errorMessage = VaultHook.withdraw(target, amount);

								if (errorMessage != null && !errorMessage.isEmpty())
									sender.sendMessage(ChatColor.RED + "Error: " + errorMessage);
								else
									sender.sendMessage(ChatColor.RED + "Toma " + VaultHook.formatCurrencySymbol(amount) + " de " + target.getName());

							} else {
								String errorMessage = VaultHook.deposit(target, amount);

								if (errorMessage != null && !errorMessage.isEmpty())
									sender.sendMessage(ChatColor.RED + "Error: " + errorMessage);
								else {
									sender.sendMessage(ChatColor.GREEN + "Da " + VaultHook.formatCurrencySymbol(amount) + " a " + target.getName());
								}
							}

						} else
							sender.sendMessage(ChatColor.RED + "Parametro inválido: '" + param + "'. Usa: " + command.getUsage());
					}
				}.runTask(CompletosCore.getInstance());
			}
		}.runTaskAsynchronously(CompletosCore.getInstance());

		return true;
	}

	@Override
	public java.util.List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

		if (args.length == 1)
			return Arrays.asList("ver", "tomar", "dar").stream().filter(s -> s.startsWith(args[0])).collect(Collectors.toList());

		else if (args.length == 2)
			return Bukkit.getOnlinePlayers().stream().map(Player::getName).filter(s -> s.startsWith(args[1])).collect(Collectors.toList());

		else if (args.length == 3 && ("tomar".equals(args[0]) || "dar".equals(args[0])))
			return Arrays.asList("1", "100", "1000").stream().filter(s -> s.startsWith(args[2])).collect(Collectors.toList());

		else
			return new ArrayList<>();
	}
}
