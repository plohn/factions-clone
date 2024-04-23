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

public class KickCommand extends SubCommand {
    @Override
    public String getName() {
        return "kick";
    }

    @Override
    public String getDescription() {
        return "Kick a faction member";
    }

    @Override
    public String getSyntax() {
        return "/team kick <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        //Check if command has correct syntax
        if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to provide a name!"));
            player.sendMessage(this.getDescription());
            return;
        }
        String playerName = args[1];
        //Check if player has faction
        Optional<FPlayer> result = FactionManager.getFactionPlayer(player);
        if (result.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need a team to do that"));
            return;
        }
        FPlayer factionPlayer = result.get();
        //Check if player is the leader of faction
        if (!factionPlayer.isLeader()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be a team leader to perform this command"));
            return;
        }
        //Check if player exists
        if (Bukkit.getPlayer(playerName) == null) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found!"));
            return;
        }
        //Check if faction exists
        if (Bukkit.getPlayer(playerName) == null) {
            return;
        }
        Faction faction = factionPlayer.getFaction();
        OfflinePlayer player2 = Bukkit.getOfflinePlayer(playerName);
        //Check if player member of faction
        if (!faction.hasPlayer(player2)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&c" + player2.getName() + " is not in your team."));
            return;
        }
        try {
            FactionManager.factionAttemptKick(player2);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have kicked " + player2.getName()));
            player2.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou have been kicked from " + faction.getName())); //TODO
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
