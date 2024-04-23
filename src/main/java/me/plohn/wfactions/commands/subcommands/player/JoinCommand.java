package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.Optional;

public class JoinCommand extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join a team";
    }

    @Override
    public String getSyntax() {
        return "/team join <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        //Check if command has correct syntax
        if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to provide a team name or player!"));
            player.sendMessage(this.getDescription());
            return;
        }
        ;
        //Check if player has faction
        Optional<FPlayer> result = FactionManager.getFactionPlayer(player);
        if (result.isPresent()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou already have a team"));
            return;
        }

        String target = args[1];

        //Check if player exists
        Optional<Player> player2 = Optional.ofNullable(Bukkit.getPlayer(target));
        if (player2.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found!"));
            return;
        }
        //Check if target is faction player
        Optional<FPlayer> factionPlayer = FactionManager.getFactionPlayer(player2.get());
        if (factionPlayer.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player2.get().getName() + " is not in a team!"));
            return;
        }
        /* Faction join attempt */
        if (FactionManager.playerJoinFaction(player, player2.get())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aYou've joined " + factionPlayer.get().getFaction().getName()));
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need an invitation to join this team."));
        }

    }
}
