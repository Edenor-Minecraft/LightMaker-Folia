package net.mov51.lightmaker.util;

import net.kyori.adventure.text.Component;

import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Levelled;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import static net.mov51.lightmaker.util.Lights.*;

public class HandWatcher {

    public static void startWatching(Plugin plugin){
        new BukkitRunnable() {
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {

                    ItemStack hand = p.getInventory().getItemInMainHand();
                    Block b = p.getTargetBlock(null,10);

                    if(lights.contains(hand.asOne()) && b.getType() == Material.LIGHT){
                        int level = ((Levelled) b.getBlockData()).getLevel();

                        TextComponent textComponent = Component.text(level)
                                .color(NamedTextColor.GOLD);



                        int handLevel = getLight(hand.asOne());
                        if(handLevel != 15 & handLevel != 0){
                            int result = rollOverAdd(handLevel + level);
                            textComponent = textComponent.decoration(TextDecoration.BOLD, true)
                                    .append(Component.text(" + ").color(NamedTextColor.WHITE).
                                            decoration(TextDecoration.BOLD, false))
                                    .append(Component.text(handLevel).color(NamedTextColor.GREEN).
                                            decoration(TextDecoration.BOLD, false))
                                    .append(Component.text(" = ").color(NamedTextColor.WHITE).
                                            decoration(TextDecoration.BOLD, false))
                                    .append(Component.text(result).color(NamedTextColor.AQUA).
                                            decoration(TextDecoration.BOLD, true));

                        }
                        p.sendActionBar(textComponent);
                    }
                }
            }
        }.runTaskTimer(plugin, 5L, 5L);
    }
}

