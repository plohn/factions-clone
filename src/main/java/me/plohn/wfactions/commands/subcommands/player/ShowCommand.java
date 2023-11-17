package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.commands.SubCommand;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ShowCommand extends SubCommand { // /f show
    @Override
    public String getName() {
        return "show";
    }

    @Override
    public String getDescription() {
        return "Shows all public information about a faction";
    }

    @Override
    public String getSyntax() {
        return "/f show <faction_name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        if (args.length > 1){
            String factionName = args[1];
            Optional<Faction> result = FactionManager.getFaction(factionName);
            if (result.isPresent()){
                Faction faction = result.get();
                player.sendMessage("Name:" + faction.getName());
                player.sendMessage("Leader:" + faction.getLeaderUUID());
                player.sendMessage("Members" + faction.getMembers().toString());
            }
            else {
                player.sendMessage("This faction does not exist.");
            }
        }else if (args.length == 1) {
            player.sendMessage("You need to provide a name!");
            player.sendMessage(this.getDescription());
            return;
        };
    }
}
