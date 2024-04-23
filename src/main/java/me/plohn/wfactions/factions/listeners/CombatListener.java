package me.plohn.wfactions.factions.listeners;

import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import javax.swing.text.html.Option;
import java.util.Optional;

public class CombatListener implements Listener {
    @EventHandler
    public void onPlayerAttack(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) && (event.getDamager() instanceof Player)) return;

        Player attacker = (Player) event.getDamager();
        Player target = (Player) event.getEntity();

        Optional<FPlayer> resultFactionPlayer1 = FactionManager.getFactionPlayer(attacker);
        Optional<FPlayer> resultFactionPlayer2 = FactionManager.getFactionPlayer(target);
        if (resultFactionPlayer1.isEmpty()) return;
        if (resultFactionPlayer2.isEmpty()) return;

        if (!resultFactionPlayer1.get()
                .getFaction()
                .equals(resultFactionPlayer2.get()
                        .getFaction()))
            return;

        attacker.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You cannot harm your team members"));
        event.setCancelled(true);
    }
}
