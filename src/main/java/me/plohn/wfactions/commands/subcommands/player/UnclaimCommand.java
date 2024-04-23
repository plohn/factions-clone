package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
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
public class UnclaimCommand extends SubCommand { // /team show
    @Override
    public String getName() {
        return "unclaim";
    }

    @Override
    public String getDescription() {
        return "unclaim the chunk that you standing on";
    }

    @Override
    public String getSyntax() {
        return "/team invite <player>";
    }

    @Override
    public void perform(Player player, String args[]) {
        //Check if player has faction
        Optional<FPlayer> result = FactionManager.getFactionPlayer(player);
        if (result.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&cYou must have a team to do that."));
            return;
        }
        FPlayer playerFaction = result.get();
        //Check if player is the leader of faction
        if (!playerFaction.isLeader()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"You must be team leader to perform this command"));
            return;
        }

        /* Try to un claim land */
        Chunk chunk = player.getLocation().getChunk();
        try {
            FactionManager.factionAttemptUnClaim(playerFaction,chunk);
        } catch (Exception e) {
            Bukkit.getLogger().info(e.getMessage());
        }
    }
}
