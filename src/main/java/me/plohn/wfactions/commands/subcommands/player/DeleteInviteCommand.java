package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.invites.Invite;
import me.plohn.wfactions.invites.manager.InviteManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.Optional;

public class DeleteInviteCommand extends SubCommand {
    @Override
    public String getName() {
        return "deinvite";
    }

    @Override
    public String getDescription() {
        return "Deletes a player invitation to your team";
    }

    @Override
    public String getSyntax() {
        return "/team deinvite <player>";
    }

    @Override
    public void perform(Player player, String[] args) {
        //Check if command has correct syntax
        if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to provide a player!"));
            player.sendMessage(this.getDescription());
            return;
        };
        //Check if player has faction
        Optional<FPlayer> factionPlayer = FactionManager.getFactionPlayer(player);
        if (factionPlayer.isEmpty()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must have a team in order to do that."));
            return;
        }
        //Check if player is the leader of faction
        if (!factionPlayer.get().isLeader()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou must be team leader in order to perform this command"));
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
        Invite invitation =new Invite(player,receiver,factionPlayer.get().getFaction());
        if (InviteManager.sentInvitation(invitation)){
            InviteManager.deleteInvite(player,receiver);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&7Invitation to &c&o"+receiver.getName()+"&7 has been deleted!"));
            return;
        }
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cThere is no invitation sent to "+receiver.getName()));
    }
}
