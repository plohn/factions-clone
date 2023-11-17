package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.invites.Invite;
import me.plohn.wfactions.invites.manager.InviteManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DeleteInviteCommand extends SubCommand {
    @Override
    public String getName() {
        return "deinvite";
    }

    @Override
    public String getDescription() {
        return "Deletes a player invitation to your faction";
    }

    @Override
    public String getSyntax() {
        return "/f deinvite <player>";
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
            player.sendMessage("You must have a faction to do that.");
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
        Player receiver = Bukkit.getPlayer(args[1]);
        /* Check if there is already an invitation */
        Invite invitation =new Invite(player,receiver,playerFaction);
        if (InviteManager.sentInvitation(invitation)){
            InviteManager.deleteInvite(player,receiver);
            player.sendMessage("Invitation to "+receiver.getName()+" has been deleted!");
        }
        player.sendMessage("There is no invitation to "+receiver.getName());
    }
}
