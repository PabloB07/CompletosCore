package me.blancocl.completoscore.command;

import fr.mrmicky.fastboard.FastBoard;
import me.blancocl.completoscore.util.Hex;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public final class BoardToggle implements CommandExecutor {
    private final Map<UUID, FastBoard> boards = new HashMap<>();


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }
        Player player = (Player) sender;
        if (boards.containsKey(player.getUniqueId())) {
            FastBoard board = this.boards.remove(player.getUniqueId());

            if (board != null) {
                board.delete();
            }
            player.sendMessage(Hex.message(Hex.prefix(" &cScoreboard desactivado!")));
        } else {
            FastBoard board = new FastBoard(player);
            boards.put(player.getUniqueId(), board);
            player.sendMessage(Hex.message(Hex.prefix(" &aScoreboard activado!")));
        }
        return true;
    }
}
