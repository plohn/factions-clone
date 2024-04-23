package me.plohn.wfactions.factions.utils;

import org.bukkit.Chunk;

public class ChunkKey {
    public static String generateChunkKey(Chunk chunk){
        return chunk.getX() + "-" + chunk.getZ() + "-" + chunk.getWorld().getName();
    }
}
