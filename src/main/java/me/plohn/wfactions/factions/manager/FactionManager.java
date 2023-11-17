package me.plohn.wfactions.factions.manager;

import com.google.common.reflect.TypeToken;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.utils.Json;
import me.plohn.wfactions.invites.Invite;
import org.bukkit.Chunk;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.Optional;

import static me.plohn.wfactions.invites.manager.InviteManager.getInvite;

public class FactionManager {
    private static final ArrayList<Faction> factions = new ArrayList<>();
    public static void createFaction(String name, Player player) throws Exception {
        if (getPlayerFaction(player).isPresent()) throw new Exception(player.getName()+" tried to ");
        if (getFaction(name).isPresent()) throw new Exception("Faction exists");
        factions.add(new Faction(name,player));
    }
    public static void disbandFaction(Player player) throws Exception {
        Optional<Faction> result = getPlayerFaction(player);
        if (result.isEmpty()) throw new Exception("You dont have a faction");
        if (!result.get().isLeader(player)) throw new Exception("You are not the leader of "+result.get().getName());
        factions.remove(result.get());
    }
    public static void playerLeaveFaction(Player player) throws Exception{
        Optional<Faction> result = getPlayerFaction(player);
        if (result.isEmpty()) throw new Exception("You dont have faction.");
        if (result.get().isLeader(player)) throw new Exception("You cannot leave your faction");
        result.get().removeMember(player);
    }
    public static Optional<Faction> getFaction(String name){
        return factions.stream()
                .filter(faction -> faction.getName().equals(name))
                .findFirst();
    }
    public static Optional<Faction> getPlayerFaction(Player player){
        return factions.stream()
                .filter(faction -> faction.hasPlayer(player))
                .findFirst();
    }

    public static boolean factionAttemptClaim(Faction f, Chunk chunk){
        if (getFaction(chunk).isPresent()) return false;
        f.addLand(chunk);
        return true;
    }
    public static boolean factionAttemptUnClaim(Faction f, Chunk chunk){
        Optional<Faction> resultFaction = getFaction(chunk);
        if (resultFaction.isEmpty()) return false;
        if (!resultFaction.get().equals(f)) return false;
        resultFaction.get().removeLand(chunk);
        return true;
    }
    public static Optional<Faction> getFaction(Chunk chunk) {
        return factions.stream()
                .filter(faction -> faction.isLandOwnerOf(chunk))
                .findFirst();
    }
    public static boolean playerJoinFaction(Player player, Player sender){
        Optional<Invite> result = getInvite(sender,player);
        if (result.isPresent()){
            result.get().getFaction().addMember(player);
            return true;
        }
        return false;
    }
    public static boolean playerHasFaction(Player player){
        Optional<Faction> playerFaction = factions.stream()
                .filter(faction -> faction.hasPlayer(player))
                .findFirst();
        return playerFaction.isPresent();
    }
    public static void save(Plugin pl){
        Json.write(pl,"factions-data",factions);
    }
    public static void read(Plugin pl){
        TypeToken<ArrayList<Faction>> typeToken = new TypeToken<ArrayList<Faction>>() {};
        ArrayList<Faction> factions_test = Json.read(pl,"factions-data",typeToken.getType());
        factions.addAll(factions_test);
    }

}
