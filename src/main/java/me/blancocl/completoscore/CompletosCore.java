package me.blancocl.completoscore;

//import de.slikey.effectlib.EffectLib;
//import de.slikey.effectlib.EffectManager;
import fr.mrmicky.fastboard.FastBoard;
import fr.mrmicky.fastinv.FastInvManager;
import lombok.Getter;
import me.blancocl.completoscore.command.*;
import me.blancocl.completoscore.hook.CompletosEconomy;
import me.blancocl.completoscore.hook.PlaceholderAPIHook;
import me.blancocl.completoscore.hook.ProtocolLibHook;
import me.blancocl.completoscore.hook.VaultHook;
import me.blancocl.completoscore.listener.*;
import me.blancocl.completoscore.npc.NPC;
import me.blancocl.completoscore.task.ButterflyTask;
import me.blancocl.completoscore.util.Hex;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.*;

public final class CompletosCore extends JavaPlugin implements Listener {
	//private static EffectLib instance;

	@Getter
	private static final Map<UUID, String> playerTags = new HashMap<>();
	private BukkitTask task;
	private final Map<UUID, FastBoard> boards = new HashMap<>();

	@Override
	public void onEnable() {
		// Board register and events with AsyncTask
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().runTaskTimer(this, () -> {
			for (FastBoard board : this.boards.values()) {
				updateBoard(board);
			}
		}, 0, 20);

		FastInvManager.register(this);

		// Events
		getServer().getPluginManager().registerEvents(new GamemodeCommand(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new JumpPlates(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new HealthTagListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
		

		// Commands
		Objects.requireNonNull(getCommand("saltar")).setExecutor(new JumpCommand());
		Objects.requireNonNull(getCommand("gm")).setExecutor(new GamemodeCommand());
		Objects.requireNonNull(getCommand("v3")).setExecutor(new JumpPlates());
		Objects.requireNonNull(getCommand("velocidad")).setExecutor(new SpeedCommand());
		Objects.requireNonNull(getCommand("cabezas")).setExecutor(new HeadsCommand());
		Objects.requireNonNull(getCommand("cosmeticos")).setExecutor(new CosmeticsCommand());
		Objects.requireNonNull(getCommand("economia")).setExecutor(new EconomyCommand());
		Objects.requireNonNull(getCommand("setspawn")).setExecutor(new SetSpawnCommand(this));
		Objects.requireNonNull(getCommand("spawn")).setExecutor(new SpawnCommand(this));
		Objects.requireNonNull(getCommand("volar")).setExecutor(new FlyCommand());
		Objects.requireNonNull(getCommand("tag")).setExecutor(new TagCommand());
		Objects.requireNonNull(getCommand("npc")).setExecutor(new NPC());

		// Hooks Register
		if (getServer().getPluginManager().getPlugin("ProtocolLib") != null)
			ProtocolLibHook.register();

		if (getServer().getPluginManager().getPlugin("Vault") != null)
			CompletosEconomy.register();

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
			PlaceholderAPIHook.registerHook();

		task = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
		//getServer().getScheduler().runTaskTimer(this, CompletoBoardTask.getInstance(), 0, 1);
	}


	@Override
	public void onDisable() {
		if (task != null) {
			task.cancel();
			task = null;
		}
		this.getServer().getMessenger().unregisterOutgoingPluginChannel(this);
		this.getServer().getMessenger().unregisterIncomingPluginChannel(this);
	}
	public static CompletosCore getInstance() {
		return getPlugin(CompletosCore.class);
	}

	/*
	public List<EffectManager> getEffectManagers() {
		return EffectManager.getManagers();
	}
	public static EffectLib instance() {
		return instance;
	}
	 */

	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		Player player = e.getPlayer();

		FastBoard board = new FastBoard(player);

		e.setJoinMessage(Hex.message("#dea428") + player.getName() + " entró ✔");

		player.sendMessage(Hex.message("#de7728") + "Bienvenido al servidor " + ChatColor.BOLD + "CompletosCraft");

		board.updateTitle(Hex.message("#fcba03") + ChatColor.BOLD + "CompletosCraft");

		this.boards.put(player.getUniqueId(), board);
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();

		e.setQuitMessage(Hex.message("#dea428") + player.getName() + " se fué ✘");


		FastBoard board = this.boards.remove(player.getUniqueId());

		if (board != null) {
			board.delete();
		}
	}

	private void updateBoard(FastBoard board) {
		// Board updated
		board.updateLines(
				"",
				Hex.message("#fcba03Nombre» " + board.getPlayer().getName()),
				"",
				Hex.message("#fcba03Online» " + getServer().getOnlinePlayers().size()),
				"",
				Hex.message("#fcba03Latencia» " + board.getPlayer().getPing()),
				"",
				Hex.message("#fcba03Completos» " + VaultHook.getBalance(board.getPlayer())),
				"",
				Hex.message("#fcba02mc.completoscraft.live")
		);
	}

}
