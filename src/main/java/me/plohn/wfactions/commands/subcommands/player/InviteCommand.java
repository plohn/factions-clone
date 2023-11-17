package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.invites.Invite;
import me.plohn.wfactions.invites.manager.InviteManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
// Way of sending messages to offline players
//                Player sender = result.get().getSender();
//                        Player receiver = result.get().getReceiver();
//                        String factionName = result.get().getFaction().getName();
//
//                        Logger.sendMessage(sender,receiver.getName() + "Declined your invitation");
//                        Logger.sendMessage(receiver,"Declined "+factionName+"'s invitation");
public class InviteCommand extends SubCommand { // /f show
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invites player to your faction";
    }

    @Override
    public String getSyntax() {
        return "/f invite <player>";
    }

    @Override
    public void perform(Player player, String args[]) {
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
        Invite invitation = new Invite(player,receiver,playerFaction);
        if (!InviteManager.sentInvitation(invitation)){
            player.sendMessage("You have already sent an invitation to " + receiver.getName());
        }
        player.sendMessage("Sent invitation to "+receiver.getName());
        receiver.sendMessage(player.getName()+" invited you to "+playerFaction.getName());
        receiver.sendMessage("type /f join "+playerFaction.getName()+" to join");
    }
}
