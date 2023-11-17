package me.plohn.wfactions.invites;

import me.plohn.wfactions.factions.Faction;
import org.bukkit.entity.Player;

public class Invite {
    private Player sender;
    private Faction faction;
    private Player receiver;
    public Invite(Player p, Player receiver, Faction faction){
        this.sender = p;
        this.receiver = receiver;
        this.faction = faction;
    }
    public Player getSender(){
        return this.sender;
    }
    public Player getReceiver(){
        return this.receiver;
    }
    public Faction getFaction(){
        return this.faction;
    }
}
