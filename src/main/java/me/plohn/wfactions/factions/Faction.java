package me.plohn.wfactions.factions;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Faction {
    private String name;
    private String leaderUUID ;
    private final List<Claim> claims;
    private final List<String> members;
    public Faction(String faction_name, OfflinePlayer player){
        this.name = faction_name;
        this.leaderUUID = player.getUniqueId().toString();
        this.members = new ArrayList<>();
        this.members.add(player.getUniqueId().toString());
        this.claims = new ArrayList<>();
    }

    public String getName(){
        return  this.name;
    }
    public String getLeaderUUID(){
        return this.leaderUUID;
    }
    public List<Claim> getLand() { return this.claims;}
    public void addLand(Chunk chunk){ this.claims.add(new Claim(chunk.getX(), chunk.getZ(), chunk.getWorld().getName()));}
    public void removeLand(Chunk chunk){
        Optional<Claim> selected = this.claims.stream()
                .filter(claim -> claim.getChunk().equals(chunk))
                .findFirst();
        this.claims.remove(selected.get());
    }
    public OfflinePlayer getLeaderPlayer(){
        return Bukkit.getPlayer(UUID.fromString(this.leaderUUID));
    }
    public List<String> getMembers(){
        return this.members;
    }
    public List<OfflinePlayer> getMemberPlayers(){
        ArrayList<OfflinePlayer> names = new ArrayList<>();
        this.members.forEach(uuid -> {names.add(
                Bukkit.getOfflinePlayer(UUID.fromString(uuid)));
        });
        return names;
    }
    public boolean isLeader(OfflinePlayer player) {
        return Objects.equals(
            Bukkit.getOfflinePlayer(
                    UUID.fromString(this.leaderUUID)), player);}
    public boolean isLandOwnerOf(Chunk chunk){
        Optional<Claim> result = claims.stream().filter(chunkLocation -> chunkLocation.getChunk().equals(chunk)).findFirst();
        return result.isPresent();
    }
    public void addMember(OfflinePlayer player){
        this.members.add(player.getUniqueId().toString());
    }
    public void removeMember(OfflinePlayer player){
        members.remove(player.getUniqueId().toString());
    }
    public void changeOwnership(OfflinePlayer player){
        this.leaderUUID = player.getUniqueId().toString();
    }
    public boolean hasPlayer(OfflinePlayer player){
        return members.contains(player.getUniqueId().toString());
    }
}
