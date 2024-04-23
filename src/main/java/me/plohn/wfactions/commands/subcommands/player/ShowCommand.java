package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

public class ShowCommand extends SubCommand { // /team show
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Shows all public information about a team";
    }

    @Override
    public String getSyntax() {
        return "/team show <faction_name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length == 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to provide a player or team name"));
            player.sendMessage(this.getDescription());
            return;
        }
        ;

        String factionName = args[1];
        Optional<Faction> result = FactionManager.getFaction(factionName);
        if (result.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThis team does not exist."));
            return;
        }

        Faction faction = result.get();
        StringBuilder factionMembers = new StringBuilder();
        StringBuilder factionLeader = new StringBuilder();

        faction.getPlayers().forEach(fPlayer -> {
            factionMembers.append(
                            fPlayer.getOfflinePlayer().getName())
                    .append(",");
            if (fPlayer.isLeader()) factionLeader.append(fPlayer.getOfflinePlayer().getName());
        });

        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m===================================="));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l* &eTeam Name: &7" + faction.getName()));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l* &eTeam Leader: &7" + factionLeader));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&e&l* &eTeam Members: &7" + factionMembers));
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&6&m===================================="));
    }
}
