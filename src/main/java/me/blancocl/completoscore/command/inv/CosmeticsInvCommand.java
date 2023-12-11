package me.blancocl.completoscore.command.inv;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import me.blancocl.completoscore.task.ButterflyTask;
import me.blancocl.completoscore.util.Heads;
import me.blancocl.completoscore.util.Hex;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class CosmeticsInvCommand extends FastInv {
    private boolean preventClose = false;

    public CosmeticsInvCommand() {
        super(45, "Cosmeticos");
        // Just add a bee item
        setItem(20, new ItemBuilder(Heads.getSkull("http://textures.minecraft.net/texture/958394919cd3321883096466888f6311c820b5b0c789a747b79bf909fff1b5b2")).name(Arrays.asList(
                Hex.message("&6Alitas de mariposa"),
                Hex.message(""),
                Hex.message("&aClick para equiparlas")).toString()).build());

        setCloseFilter(p -> this.preventClose);
    }

    @Override
    public void onOpen(InventoryOpenEvent event) {
        event.getPlayer().getLocation().getWorld().playSound(event.getPlayer().getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
    }

    @Override
    public void onClose(InventoryCloseEvent event) {

        //event.getPlayer().sendMessage(ChatColor.GOLD + "You closed the inventory");
    }

    @Override
    public void onClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ButterflyTask instance = (ButterflyTask) ButterflyTask.getInstance();

        if (event.getSlot() == 20) {
            if (instance.hasPlayer(player.getUniqueId())) {
                instance.removePlayer(player.getUniqueId());
                player.sendMessage(Hex.message("#a83240Ya no estás usando el efecto Mariposa!"));

            } else {
                instance.addPlayer(player.getUniqueId());
                player.sendMessage(Hex.message("#a87b32Estás usando el efecto Mariposa!"));
            }
            player.closeInventory();
        }
    }
}