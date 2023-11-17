package me.plohn.wfactions.factions.listeners;

import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
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
    public void onBlockBreak(BlockBreakEvent event){
        Player p = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

         FactionManager.getFaction(chunk)
              .filter(faction -> !faction.getMembers().contains(p.getUniqueId().toString()))
              .ifPresent(faction -> event.setCancelled(true));
    }
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event){
        Player p = event.getPlayer();
        Chunk chunk = event.getBlock().getChunk();

        FactionManager.getFaction(chunk)
                .filter(faction -> !faction.getMembers().contains(p.getUniqueId().toString()))
                .ifPresent(faction -> event.setCancelled(true));
    }
}
