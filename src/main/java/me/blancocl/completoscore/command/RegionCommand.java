package me.blancocl.completoscore.command;
import me.blancocl.completoscore.CompletosCore;
import me.blancocl.completoscore.hook.WorldEditHook;
import me.blancocl.completoscore.util.Hex;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class RegionCommand implements CommandExecutor {

    private final Map<UUID, Tuple<Location, Location>> selections = new HashMap<>();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Solo jugadores pueden usar esto!");

            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(Hex.message("&aUsa: /region <pos1|pos2|guardar <nombre>|pegar <nombre>>"));

            return true;
        }

        CompletosCore plugin = CompletosCore.getInstance();
        Player player = (Player) sender;
        String param = args[0].toLowerCase();

        Tuple<Location, Location> selection = selections.getOrDefault(player.getUniqueId(), new Tuple<>(null, null));

        if ("pos1".equals(param)) {
            selection.setFirst(player.getLocation());

            sender.sendMessage("§8[§a✔§8] §7Primera ubicación establecida!");
            selections.put(player.getUniqueId(), selection);

        } else if ("pos2".equals(param)) {
            selection.setSecond(player.getLocation());

            sender.sendMessage("§8[§a✔§8] §7Segunda ubicación establecida!");
            selections.put(player.getUniqueId(), selection);

        } else if ("guardar".equals(param)) {
            if (selection.getFirst() == null || selection.getSecond() == null) {
                sender.sendMessage("§8[§c❌§8] §7Utiliza los comandos /region pos1 y /region pos2");

                return true;
            }

            if (args.length != 2) {
                sender.sendMessage("§8[§6🛑§8] §7Usa: /region guardar <nombre>");

                return true;
            }

            File file = new File(plugin.getDataFolder(), "schematic/" + args[1] + ".schem");

            if (!file.getParentFile().exists())
                file.getParentFile().mkdirs();

            WorldEditHook.save(selection.getFirst(), selection.getSecond(), file);
            sender.sendMessage("§8[§a✔§8] §7Schematic guardado!");

        } else if ("pegar".equals(param)) {
            if (args.length != 2) {
                sender.sendMessage("§8[§6🛑§8] §7Usa: /region pegar <nombre>");

                return true;
            }

            File file = new File(plugin.getDataFolder(), "schematic/" + args[1] + ".schem");

            if (!file.exists()) {
                sender.sendMessage("§8[§c❌§8] §7Schematic no encontrado!");

                return true;
            }

            WorldEditHook.paste(player.getLocation(), file);
            sender.sendMessage("§8[§a✔§8] §7Schematic pegado en " + player.getLocation());

        } else
            sender.sendMessage("§8[§6🛑§8] §7Usa: /region <pos1|pos2|guardar <nombre>|pegar <nombre>>");

        return true;
    }

    private static class Tuple<A, B> {
        private A first;
        private B second;

        public Tuple(A first, B second) {
            this.first = first;
            this.second = second;
        }

        public A getFirst() {
            return first;
        }

        public void setFirst(A first) {
            this.first = first;
        }

        public B getSecond() {
            return second;
        }

        public void setSecond(B second) {
            this.second = second;
        }
    }
}