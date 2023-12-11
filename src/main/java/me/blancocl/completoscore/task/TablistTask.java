package me.blancocl.completoscore.task;

import lombok.Getter;
import me.blancocl.completoscore.settings.CompletosCoreSettings;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public final class TablistTask implements Runnable {

    @Getter
    private static final TablistTask instance = new TablistTask();

    private final Map<UUID, Integer> headerPositions = new HashMap<>();
    private final Map<UUID, Integer> footerPositions = new HashMap<>();

    private TablistTask() {
    }

    @Override
    public void run() {
        List<String> headerLines = CompletosCoreSettings.getInstance().getHeaderLines();
        List<String> footerLines = CompletosCoreSettings.getInstance().getFooterLines();
        MiniMessage miniMessage = MiniMessage.miniMessage();

        for (Player player : Bukkit.getOnlinePlayers()) {
            int headerPosition = headerPositions.getOrDefault(player.getUniqueId(), 0);
            int footerPosition = footerPositions.getOrDefault(player.getUniqueId(), 0);

            if (headerPosition >= headerLines.size())
                headerPosition = 0;

            if (footerPosition >= footerLines.size())
                footerPosition = 0;

            player.sendPlayerListHeaderAndFooter(
                    miniMessage.deserialize(replaceVariables(player, headerLines.get(headerPosition))),
                    miniMessage.deserialize(replaceVariables(player, footerLines.get(footerPosition))));

            headerPositions.put(player.getUniqueId(), headerPosition + 1);
            footerPositions.put(player.getUniqueId(), footerPosition + 1);
        }
    }

    private String replaceVariables(Player player, String message) {
        return message.replace("{player}", player.getName());
    }

}