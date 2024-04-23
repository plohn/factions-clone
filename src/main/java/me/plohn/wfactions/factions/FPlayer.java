package me.plohn.wfactions.factions;

import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.Date;
import java.util.UUID;

public class FPlayer {
    private UUID playerUUID;
    private UUID factionUUID;
    private boolean isLeader;
    //TODO

    public FPlayer (OfflinePlayer p, Faction f,Boolean isLeader){
        this.playerUUID = p.getPlayer().getUniqueId();
        this.factionUUID = f.getUUID();
        this.isLeader = isLeader;
    }
    public OfflinePlayer getOfflinePlayer(){
        return Bukkit.getOfflinePlayer(this.playerUUID);
    }
    public UUID getPlayerUUID(){
        return this.playerUUID;
    }
    public boolean isLeader(){
        return this.isLeader;
    }
    public UUID getFactionUUID(){
        return this.factionUUID;
    }
    public Faction getFaction() {
        return FactionManager.getFaction(this.factionUUID);
    };

}
