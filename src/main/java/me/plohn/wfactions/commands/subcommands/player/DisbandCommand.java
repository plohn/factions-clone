package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DisbandCommand extends SubCommand { // /f create
    @Override
    public String getName() {
        return "disband";
    }

    @Override
    public String getDescription() {
        return "Deletes a faction";
    }

    @Override
    public String getSyntax() {
        return "/f disband <faction_name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        Optional<Faction> result = FactionManager.getPlayerFaction(player);
        //player has no faction
        if (result.isEmpty()) {
            player.sendMessage("You need to have a faction to do that");
            return;
        }
        //if player is not leader
        if (!result.get().isLeader(player)) {
            player.sendMessage("You must be leader in order to do that");
            return;
        }
        try {
            FactionManager.disbandFaction(player);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
