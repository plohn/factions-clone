package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.invites.Invite;
import me.plohn.wfactions.invites.manager.InviteManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.swing.text.html.Option;
import java.util.Optional;

// Way of sending messages to offline players
//                Player sender = result.get().getSender();
//                        Player receiver = result.get().getReceiver();
//                        String factionName = result.get().getFaction().getName();
//
//                        Logger.sendMessage(sender,receiver.getName() + "Declined your invitation");
//                        Logger.sendMessage(receiver,"Declined "+factionName+"'s invitation");
public class InviteCommand extends SubCommand { // /team show
    @Override
    public String getName() {
        return "invite";
    }

    @Override
    public String getDescription() {
        return "Invites a player to your team";
    }

    @Override
    public String getSyntax() {
        return "/team invite <player>";
    }

    @Override
    public void perform(Player player, String args[]) {
        //Check if command has correct syntax
        if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to provide a player."));
            player.sendMessage(this.getDescription());
            return;
        }
        //Check if player has faction
        Optional<FPlayer> factionPlayer = FactionManager.getFactionPlayer(player);
        if (factionPlayer.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must have a team in order to do that."));
            return;
        }
        //Check if player is the leader of faction
        if (!factionPlayer.get().isLeader()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be a team leader to perform this command"));
            return;
        }
        //Check if player exists
        String target = args[1];
        Optional<Player> player2 = Optional.ofNullable(Bukkit.getPlayer(target));
        if (player2.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cPlayer not found!"));
            return;
        }
        Player receiver = player2.get();
        /* Check if there is already an invitation */
        Invite invitation = new Invite(player, receiver, factionPlayer.get().getFaction());
        if (!InviteManager.sentInvitation(invitation)) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7You have already sent an invitation to &c&o" + receiver.getName()));
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aSent invitation to &a&o" + receiver.getName()));
        receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', "&a" + player.getName() + " invited you to " + factionPlayer.get().getFaction().getName()));
        receiver.sendMessage(ChatColor.translateAlternateColorCodes('&', "&atype /team join " + player.getName() + " to join"));
    }
}
