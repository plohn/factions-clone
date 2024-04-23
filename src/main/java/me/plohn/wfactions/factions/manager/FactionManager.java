package me.plohn.wfactions.factions.manager;

import com.google.common.reflect.TypeToken;
import me.plohn.wfactions.factions.Claim;
import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.utils.ChunkKey;
import me.plohn.wfactions.factions.utils.Json;
import me.plohn.wfactions.invites.Invite;
import me.plohn.wfactions.invites.manager.InviteManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.swing.text.html.Option;
import java.util.*;

public class FactionManager {
    private static final HashMap<UUID, Faction> factions = new HashMap<>();
    private static final HashMap<UUID, FPlayer> factionPlayers = new HashMap<>();
    private static final HashMap<String, Claim> claims = new HashMap<>();

    public static Optional<FPlayer> getFactionPlayer(OfflinePlayer player) {
        return Optional.ofNullable(factionPlayers.get(player.getUniqueId()));
    }

    public static Optional<Faction> getFaction(Chunk chunk) {
        Optional<Claim> result = Optional.ofNullable(claims.get(ChunkKey.generateChunkKey(chunk)));
        return result.map(Claim::getFaction);
    }

    public static Optional<Faction> getFaction(String factionName) {

        return factions.values().stream().filter(faction -> faction.getName().equals(factionName)).findFirst();
    }

    public static Faction getFaction(UUID factionUUID) {
        return factions.get(factionUUID);
    }

    public static ArrayList<FPlayer> getPlayersMembers(ArrayList<UUID> membersUUID) {

        ArrayList<FPlayer> fPlayers = new ArrayList<>();
        for (UUID uuid : membersUUID) {
            fPlayers.add(factionPlayers.get(uuid));
        }
        return fPlayers;
    }

    public static void createFaction(String name, Player player) throws Exception {

        if (getFactionPlayer(player).isPresent())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cPlayer has already a team"));
        if (getFaction(name).isPresent())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cTeam with the same name exists"));

        Faction newFaction = new Faction(name);
        newFaction.addMember(player.getUniqueId());
        factions.put(newFaction.getUUID(), newFaction);
        factionPlayers.put(player.getUniqueId(), new FPlayer(player, newFaction, true));
    }

    public static void disbandFaction(Player player) throws Exception {

        Optional<FPlayer> result = getFactionPlayer(player);
        if (result.isEmpty())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cPlayer is not a member of any team"));

        FPlayer factionPlayer = result.get();
        if (!factionPlayer.isLeader())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cPlayer is not the leader of this team"));

        Faction selectedFaction = getFaction(factionPlayer.getFactionUUID());
        //Remove all faction members from the list
        selectedFaction.getPlayers().forEach(fPlayer -> factionPlayers.remove(fPlayer.getOfflinePlayer().getUniqueId()));
        // Delete faction
        factions.remove(selectedFaction.getUUID());
        //Delete all claims
        selectedFaction.getClaims().forEach(claim -> claims.remove(claim.getClaimKey()));
    }

    public static boolean playerJoinFaction(Player player, Player sender) {

        Optional<Invite> result = InviteManager.getInvite(sender, player);
        if (result.isPresent()) {
            Faction faction = result.get().getFaction();
            faction.addMember(result.get().getReceiver().getUniqueId());
            factionPlayers.put(result.get().getReceiver().getUniqueId(), new FPlayer(result.get().getReceiver(), faction, false));
            return true;
        }
        return false;
    }

    public static void playerLeaveFaction(Player player) throws Exception {

        Optional<FPlayer> result = Optional.ofNullable(factionPlayers.get(player.getUniqueId()));
        if (result.isEmpty())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cPlayer is not in a team"));

        FPlayer factionPlayer = result.get();
        if (factionPlayer.isLeader())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cPlayer is the leader of the team"));

        //Remove player from his faction
        factionPlayer.getFaction().removeMember(player.getUniqueId());

        //Delete faction player
        factionPlayers.remove(player.getUniqueId());
    }

    public static void factionAttemptClaim(FPlayer player, Chunk chunk) throws Exception {
        Optional<Faction> result = FactionManager.getFaction(chunk);
        if (result.isPresent())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cLand is already claimed"));
        if (!player.isLeader())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cYou must be team leader in order to do that"));

        Claim newClaim = new Claim(chunk.getX(), chunk.getZ(), chunk.getWorld().getName(), player.getFaction().getUUID());

        claims.put(ChunkKey.generateChunkKey(chunk), newClaim);
        player.getFaction().addClaim(chunk);
    }

    public static void factionAttemptUnClaim(FPlayer player, Chunk chunk) throws Exception {
        Optional<Faction> result = FactionManager.getFaction(chunk);

        if (result.isEmpty())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cChunk is not claimed"));
        if (!result.get().equals(player.getFaction()))
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cThis land belongs to other team"));
        if (!player.isLeader())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cOnly leaders can unclaim land"));

        player.getFaction().removeClaim(chunk);
        claims.remove(ChunkKey.generateChunkKey(chunk));
    }

    public static void factionAttemptKick(OfflinePlayer player) throws Exception {

        Optional<FPlayer> result = Optional.ofNullable(factionPlayers.get(player.getUniqueId()));
        if (result.isEmpty())
            throw new Exception(ChatColor.translateAlternateColorCodes('&', "&cPlayer is not a member of any team"));

        Faction faction = result.get().getFaction();
        faction.removeMember(player.getUniqueId());
        factionPlayers.remove(player.getUniqueId());
    }

    public static void save(Plugin pl) {
        Json.write(pl, "factions", factions.values());
        Json.write(pl, "players", factionPlayers.values());
        Json.write(pl, "claims", claims.values());
    }

    public static void read(Plugin pl) {

        TypeToken<ArrayList<Faction>> typeFactionToken = new TypeToken<ArrayList<Faction>>() {
        };
        TypeToken<ArrayList<Claim>> typeClaimToken = new TypeToken<ArrayList<Claim>>() {
        };
        TypeToken<ArrayList<FPlayer>> typeFPlayerToken = new TypeToken<ArrayList<FPlayer>>() {
        };

        ArrayList<Faction> factionData = Json.read(pl, "factions", typeFactionToken.getType());
        ArrayList<Claim> claimData = Json.read(pl, "claims", typeClaimToken.getType());
        ArrayList<FPlayer> fPlayersData = Json.read(pl, "players", typeFPlayerToken.getType());

        factionData.forEach(faction -> factions.put(faction.getUUID(), faction));
        fPlayersData.forEach(fPlayer -> factionPlayers.put(fPlayer.getPlayerUUID(), fPlayer));
        claimData.forEach(claim -> {
            Bukkit.getLogger().info(claim.getWorldName());
            claims.put(claim.getClaimKey(), claim);
        });

    }

}
