package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DisbandCommand extends SubCommand { // /team create
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "Deletes a team";
    }

    @Override
    public String getSyntax() {
        return "/team disband <team_name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        Optional<FPlayer> result = FactionManager.getFactionPlayer(player);
        //player has no faction
        if (result.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to have a team to do that"));
            return;
        }
        //if player is not leader
        if (!result.get().isLeader()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be leader in order to do that"));
            return;
        }
        try {
            FactionManager.disbandFaction(player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou deleted your team"));
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
