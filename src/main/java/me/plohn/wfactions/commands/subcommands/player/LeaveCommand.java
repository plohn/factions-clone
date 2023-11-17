package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
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
        return "Leave your faction";
    }

    @Override
    public String getSyntax() {
        return "/f leave";
    }

    @Override
    public void perform(Player player, String[] args) {
        Optional<Faction> result = FactionManager.getPlayerFaction(player);
        if (result.isEmpty()) player.sendMessage("You must be in a faction to do that.");

        try {
            FactionManager.playerLeaveFaction(player);
        } catch (Exception e) {
            return;
        }
    }
}
