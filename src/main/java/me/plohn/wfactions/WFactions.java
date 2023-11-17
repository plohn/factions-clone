package me.plohn.wfactions;

import me.plohn.wfactions.commands.manager.CommandManager;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.listeners.BuildListener;
import me.plohn.wfactions.factions.manager.FactionManager;
import org.bukkit.Chunk;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class WFactions extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getLogger().info("onEnable is called!");
        FactionManager.read(this);
        getServer().getPluginManager().registerEvents(new BuildListener(), this);
        getCommand("f").setExecutor(new CommandManager(this));
    }
    @Override
    public void onDisable() {
        FactionManager.save(this);
        getLogger().info("onDisable is called!");
    }
}
