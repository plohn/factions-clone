package me.plohn.wfactions.factions.listeners;

import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.Optional;

public class ChunkEnterListener implements Listener {
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Chunk chunk = event.getPlayer().getLocation().getChunk();

        Optional<Faction> result = FactionManager.getFaction(chunk);
        if (result.isEmpty()) return;

    }
}
