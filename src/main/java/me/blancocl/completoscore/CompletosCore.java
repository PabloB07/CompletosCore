package me.blancocl.completoscore;

//import de.slikey.effectlib.EffectLib;
//import de.slikey.effectlib.EffectManager;
import fr.mrmicky.fastboard.FastBoard;
import fr.mrmicky.fastinv.FastInvManager;
import me.blancocl.completoscore.command.*;
import me.blancocl.completoscore.hook.CompletosEconomy;
import me.blancocl.completoscore.hook.PlaceholderAPIHook;
import me.blancocl.completoscore.hook.ProtocolLibHook;
import me.blancocl.completoscore.hook.VaultHook;
import me.blancocl.completoscore.listener.*;
import me.blancocl.completoscore.npc.NPC;
import me.blancocl.completoscore.particles.Particles;
import me.blancocl.completoscore.settings.CompletosCoreSettings;
import me.blancocl.completoscore.task.ButterflyTask;
import me.blancocl.completoscore.task.ItemPickupTask;
import me.blancocl.completoscore.task.TablistTask;
import me.blancocl.completoscore.util.Bungee;
import me.blancocl.completoscore.util.Hex;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import redempt.redlib.region.Region;

import java.text.SimpleDateFormat;
import java.util.*;

public final class CompletosCore extends JavaPlugin implements Listener {
	//private static EffectLib instance;
	private BukkitRunnable particleTask;
	private BukkitTask task3;
	private BukkitTask task4;
	private BukkitTask task5;
	//private static final Map<UUID, String> playerTags = new HashMap<>();
	private final Map<UUID, FastBoard> boards = new HashMap<>();

	private Map<LivingEntity, Player> pets = new HashMap<>();

	@Override
	public void onEnable() {
		new PetListener.PetAIUpdater().runTaskTimer(this, 0, 20); // Actualiza cada segundo

		// Board register and events with AsyncTask
		getServer().getPluginManager().registerEvents(this, this);
		getServer().getScheduler().runTaskTimer(this, () -> {
			for (FastBoard board : this.boards.values()) {
				updateBoard(board);
			}
		}, 0, 20);

		FastInvManager.register(this);

		// Eventos
		getServer().getPluginManager().registerEvents(new PlayerListener(), this);
		getServer().getPluginManager().registerEvents(new SignListener(), this);
		getServer().getPluginManager().registerEvents(new Particles(), this);
		getServer().getPluginManager().registerEvents(new PetListener(this), this);
		getServer().getPluginManager().registerEvents(new GamemodeCommand(), this);
		getServer().getPluginManager().registerEvents(new EntityListener(), this);
		getServer().getPluginManager().registerEvents(new JumpPlates(), this);
		getServer().getPluginManager().registerEvents(new GuiListener(), this);
		getServer().getPluginManager().registerEvents(new LaserPointerListener(), this);
		getServer().getPluginManager().registerEvents(new ChatListener(), this);
		getServer().getPluginManager().registerEvents(new HealthTagListener(), this);
		getServer().getPluginManager().registerEvents(new SpawnListener(this), this);
		

		// Comandos
		Objects.requireNonNull(getCommand("region")).setExecutor(new RegionCommand());
		Objects.requireNonNull(getCommand("cartel")).setExecutor(new SignCommand());
		Objects.requireNonNull(getCommand("board")).setExecutor(new BoardToggle());
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
		Objects.requireNonNull(getCommand("npc")).setExecutor(new NPC());

		// Hooks Registrador
		if (getServer().getPluginManager().getPlugin("ProtocolLib") != null)
			ProtocolLibHook.register();

		if (getServer().getPluginManager().getPlugin("Vault") != null)
			CompletosEconomy.register();

		if (getServer().getPluginManager().getPlugin("PlaceholderAPI") != null)
			PlaceholderAPIHook.registerHook();

		BukkitTask task3 = getServer().getScheduler().runTaskTimer(this, ButterflyTask.getInstance(), 0, 1);
		BukkitTask task4 = getServer().getScheduler().runTaskTimer(this, TablistTask.getInstance(), 0, 20);
		BukkitTask task5 = getServer().getScheduler().runTaskTimer(this, ItemPickupTask.getInstance(), 0, 2);
		//getServer().getScheduler().runTaskTimer(this, CompletoBoardTask.getInstance(), 0, 1);
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new Bungee());

		CompletosCoreSettings.getInstance().load();
		//CompletosCoreSettings.getInstance().set("Tablist.Header", CompletosCoreSettings.getInstance().getHeaderLines());
		//CompletosCoreSettings.getInstance().set("Tablist.Footer", CompletosCoreSettings.getInstance().getFooterLines());
	}


	@Override
	public void onDisable() {
		if (task3 != null) {
			task3.cancel();
			task3 = null;
		}
		if (particleTask != null) {
			particleTask.cancel();
		}
		if (task4 != null) {
			task4.cancel();
			task4 = null;
		}
		if (task5 != null) {
			task5.cancel();
			task5 = null;
		}
		this.boards.clear();
		this.pets.clear();
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
		// Create SimpleDateFormat with UTC-3 time zone for Chile
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC-3"));

		// Format and print the current date
		Date currentDate = new Date();
		String formattedDate = sdf.format(currentDate);

		// Board actualizado X segundos
		board.updateLines(
				Hex.message("#4287f5" + formattedDate),
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
