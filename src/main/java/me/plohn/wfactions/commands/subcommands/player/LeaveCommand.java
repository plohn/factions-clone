package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.swing.text.html.Option;
import java.util.Optional;

public class LeaveCommand extends SubCommand {
    @Override
    public String getName() {
        return "leave";
    }

    @Override
    public String getDescription() {
        return "Leave your team";
    }

    @Override
    public String getSyntax() {
        return "/team leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        Optional<FPlayer> result = FactionManager.getFactionPlayer(player);
        if (result.isEmpty())
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be in a team to do that."));

        try {
            FactionManager.playerLeaveFaction(player);
        } catch (Exception e) {
            return;
        }
    }
}
