package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.Optional;

public class CreateCommand extends SubCommand { // /f create
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a faction";
    }

    @Override
    public String getSyntax() {
        return "/f create <faction_name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        //player has faction
        if (FactionManager.playerHasFaction(player)) {
            player.sendMessage("You already have a a faction");
            return;
        }

        if (args.length < 1){
            player.sendMessage("You need to provide a name!");
            player.sendMessage(this.getDescription());
            return;
        }
        String factionName = args[1];

        try {
            FactionManager.createFaction(factionName,player);
            player.sendMessage("Created faction "+factionName);
        } catch (Exception e) {
            player.sendMessage(e.getMessage());
        }
    };
}
