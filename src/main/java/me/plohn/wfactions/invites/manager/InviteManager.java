package me.plohn.wfactions.invites.manager;
import me.plohn.wfactions.invites.Invite;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Optional;

public class InviteManager {
    private static final ArrayList<Invite> pendingInvites = new ArrayList<>();
    public static Optional<Invite> getInvite(Player sender,Player receiver){
        return pendingInvites.stream()
                .filter(pendingInvite -> pendingInvite.getSender()
                        .equals(sender)
                        && pendingInvite.getReceiver()
                        .equals(receiver))
                .findFirst();
    }
    public static boolean sentInvitation(Invite inv){
        if (pendingInvites.contains(inv)) return false;
        pendingInvites.add(inv);
        return true;
    }
    public static boolean deleteInvite(Player player, Player option){
        Optional<Invite> result = getInvite(player,option);
        if (result.isPresent()){
            pendingInvites.remove(result.get());
            return true;
        }else {
            return false;
        }
    }
}

