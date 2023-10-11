package me.blancocl.completoscore.command.inv;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import me.blancocl.completoscore.util.Heads;
import me.blancocl.completoscore.util.Hex;
import org.bukkit.Sound;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class HeadsInvCommand extends FastInv {
    private boolean preventClose = false;

    public HeadsInvCommand() {
        super(45, "Cabezas");

        // Just add a random item
        setItem(20, new ItemBuilder(Heads.getSkull("http://textures.minecraft.net/texture/2fc95c4442a1a66322310334024f27b3a3d1f4c291fbf8463f1a56e7e89f9628")).name(Arrays.asList(
                Hex.message("&eAlfabeto"),
                Hex.message(""),
                Hex.message("&aClick para abrir el menu")).toString()).build());
        setItem(22, new ItemBuilder(Heads.getSkull("http://textures.minecraft.net/texture/5454c33e21f2eccf1e29d16a74e9e35b35bc0b3cf14d2eb472da90daf098be02")).name(Arrays.asList(
                Hex.message("&eBloques"),
                Hex.message(""),
                Hex.message("&aClick para abrir el menu")).toString()).build());
        setItem(24, new ItemBuilder(Heads.getSkull("http://textures.minecraft.net/texture/abfde2a1086c8e4f963d34872e687e90ac4202ad041234f7f3d2a01bfb40477a")).name(Arrays.asList(
                Hex.message("&eSkins"),
                Hex.message(""),
                Hex.message("&aClick para abrir el menu")).toString()).build());
        setItem(31, new ItemBuilder(Heads.getSkull("http://textures.minecraft.net/texture/eec5574603f3048f21ad5a3c94d97115706011fe6ba67781091b8a9ac10af54f")).name(Arrays.asList(
                Hex.message("&eMobs"),
                Hex.message(""),
                Hex.message("&aClick para abrir el menu")).toString()).build());
        // Add some blocks to the borders
        //setItems(getBorders(), new ItemBuilder(Material.LAPIS_BLOCK).name(" ").build());

        // Add a simple item to prevent closing the inventory
        /*setItem(34, new ItemBuilder(Material.BARRIER).name(ChatColor.RED + "Prevent close").build(), e -> {
            this.preventClose = !this.preventClose;
        });*/

        // Prevent from closing when preventClose is t true
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

    }
}
