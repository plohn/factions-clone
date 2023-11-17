package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
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
        return "/f kick <player>";
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
        if (result.isEmpty()) {
            player.sendMessage("You need a faction to do that");
            return;
        }
        Faction playerFaction = result.get();
        //Check if player is the leader of faction
        if (!playerFaction.isLeader(player)) {
            player.sendMessage("You must a faction leader to perm this command");
            return;
        }
        //Check if player exists
        if (Bukkit.getPlayer(args[1]) == null) {
            player.sendMessage("Player not found!");
            return;
        }
        //Check if faction exists
        if (Bukkit.getPlayer(args[1]) == null) {
            player.sendMessage("Player not found!");
            return;
        }
        Player receiver = Bukkit.getPlayer(args[1]);
        //Check if player member of faction
        if (!playerFaction.hasPlayer(receiver)){
            player.sendMessage(receiver.getName()+" is not in your faction.");
            return;
        }
        playerFaction.removeMember(receiver);
        player.sendMessage("You have kicked "+player.getName());
        receiver.sendMessage("You have been kicked from "+playerFaction.getName());
    }
}
