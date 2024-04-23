package me.plohn.wfactions.factions;

import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.factions.utils.ChunkKey;
import org.bukkit.Bukkit;
import org.bukkit.Chunk;

import java.util.UUID;

public class Claim {
    private final int x;
    private final int z;
    private String worldName;
    private UUID factionUUID;


    public Claim(int x, int z, String worldName,UUID factionUUID){
        this.x = x;
        this.z = z;
        this.worldName = worldName;
        this.factionUUID = factionUUID;
    }

    public int getX() {
        return this.x;
    }

    public int getZ() {
        return this.z;
    }

    public String getWorldName() {
        return this.worldName;
    }
    public Faction getFaction(){
        return FactionManager.getFaction(this.factionUUID);
    }
    public UUID getFactionUUID() {return this.factionUUID;}
    public Chunk getChunk(){
        return Bukkit.getWorld(this.worldName).getChunkAt(this.x,this.z);
    }
    public String getClaimKey() {
        return ChunkKey.generateChunkKey(this.getChunk());
    }


}
