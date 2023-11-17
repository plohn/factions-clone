package me.plohn.wfactions.factions;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;

public class Claim {
    private int x,z;
    private String worldName;
    public Claim(int x, int z, String worldName){
        this.x = x;
        this.z = z;
        this.worldName = worldName;
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
    public Chunk getChunk(){
        return Bukkit.getWorld(this.worldName).getChunkAt(this.x,this.z);
    }


}
