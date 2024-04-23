package me.plohn.wfactions.factions;

import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.invites.Invite;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.util.*;

public class Faction {
    private UUID uuid;
    private String name;
    private final List<Claim> claims;
    private final List<Invite> invites;
    private ArrayList<UUID> members;

    public Faction(String faction_name) {
        this.claims = new ArrayList<>();
        this.members = new ArrayList<>();
        this.invites = new ArrayList<>();

        this.uuid = UUID.randomUUID();
        this.name = faction_name;
    }

    public String getName() {
        return this.name;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public List<Claim> getClaims() {
        return this.claims;
    }

    public ArrayList<FPlayer> getPlayers() {
        return FactionManager.getPlayersMembers(this.members);
    }

    public void addMember(UUID uuid) {
        this.members.add(uuid);
    }

    public void removeMember(UUID uuid) {
        this.members.remove(uuid);
    }

    public void addClaim(Chunk chunk) {

        this.claims.add(new Claim(chunk.getX(), chunk.getZ(), chunk.getWorld().getName(), this.getUUID()));
    }

    public void removeClaim(Chunk chunk) {

        Optional<Claim> selected = this.claims.stream().filter(claim -> claim.getChunk().equals(chunk)).findFirst();
        this.claims.remove(selected.get());
    }

    public boolean hasClaim(Chunk chunk) {

        Optional<Claim> result = claims.stream().filter(chunkLocation -> chunkLocation.getChunk().equals(chunk)).findFirst();
        return result.isPresent();
    }

    public boolean hasPlayer(OfflinePlayer player) {
        return members.contains(player.getUniqueId());
    }
}
