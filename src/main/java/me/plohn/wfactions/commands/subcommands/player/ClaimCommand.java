package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;

import java.util.Optional;

// Way of sending messages to offline players
//                Player sender = result.get().getSender();
//                        Player receiver = result.get().getReceiver();
//                        String factionName = result.get().getFaction().getName();
//
//                        Logger.sendMessage(sender,receiver.getName() + "Declined your invitation");
//                        Logger.sendMessage(receiver,"Declined "+factionName+"'s invitation");
public class ClaimCommand extends SubCommand { // /f show
    @Override
    public String getName() {
        return "claim";
    }

    @Override
    public String getDescription() {
        return "claim for your faction";
    }

    @Override
    public String getSyntax() {
        return "/f claim";
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
        /* Try to claim land */
        Chunk location = player.getLocation().getChunk();
        if (FactionManager.factionAttemptClaim(playerFaction,location)){
            player.sendMessage("Claimed this chunk");
        }
        //
        player.sendMessage("You cannot claim this chunk");
    }
}
