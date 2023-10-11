package me.blancocl.completoscore.listener;

import me.blancocl.completoscore.CompletosCore;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scoreboard.Criteria;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;


public class HealthTagListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();
		Player player = event.getPlayer();

		Objective objective = board.registerNewObjective(
				CompletosCore.getInstance().getName() + "_health", Criteria.HEALTH, ChatColor.of("#b1904c") + "❤");

		objective.setDisplaySlot(DisplaySlot.BELOW_NAME);
		player.setScoreboard(board);
	}
}
