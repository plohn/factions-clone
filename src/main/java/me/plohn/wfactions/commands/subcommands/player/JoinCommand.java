package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class JoinCommand extends SubCommand {
    @Override
    public String getName() {
        return "join";
    }

    @Override
    public String getDescription() {
        return "Join a faction";
    }

    @Override
    public String getSyntax() {
        return "/f join <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        //Check if command has correct syntax
        if (args.length < 1) {
            player.sendMessage("You need to provide a name!");
            player.sendMessage(this.getDescription());
            return;
        };
        //Check if player has faction
        Optional<Faction> result = FactionManager.getPlayerFaction(player);
        if (result.isPresent()) {
            player.sendMessage("You already have a faction");
            return;
        }
        //Check if faction exists
        if (Bukkit.getPlayer(args[1]) == null) {
            player.sendMessage("Player not found!");
            return;
        }
        Player receiver = Bukkit.getPlayer(args[1]);
        /* Faction join attempt */
        if (FactionManager.playerJoinFaction(player,receiver)){
            player.sendMessage("You joined "+ FactionManager.getPlayerFaction(player).get().getName()
                    .toString());
        } else{
            player.sendMessage("You need an invitation to join this faction.");
        }

    }
}
