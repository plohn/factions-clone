package me.plohn.wfactions.factions.listeners;

import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import javax.swing.text.html.Option;
import java.util.Optional;

public class BuildListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

        Optional<Faction> result = FactionManager.getFaction(chunk);
        if (result.isEmpty()) return;
        Faction faction = result.get();
        if (faction.hasPlayer(player)) return;

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis land is claimed by " + faction.getName()));
        event.setCancelled(true);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

        Optional<Faction> result = FactionManager.getFaction(chunk);
        if (result.isEmpty()) return;

        Faction faction = result.get();
        if (faction.hasPlayer(player)) return;

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis land is claimed by " + faction.getName()));
        event.setCancelled(true);
    }

}
