package me.plohn.wfactions.factions;

import me.plohn.wfactions.factions.Faction;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.util.UUID;

public class FPlayer {
    private String playerUUID;
    private Faction faction;
    public FPlayer (OfflinePlayer p, Faction f){
        this.playerUUID = p.getPlayer().getUniqueId().toString();
        this.faction = f;
    }
    public Faction getFaction(){
        return this.faction;
    }
    public OfflinePlayer getPlayerUUID(){
        return Bukkit.getOfflinePlayer(UUID.fromString(this.playerUUID));
    }
    public boolean isLeader() {
        return this.faction.isLeader(this.getPlayerUUID());
    }

}
