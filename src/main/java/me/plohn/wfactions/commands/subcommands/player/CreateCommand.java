package me.plohn.wfactions.commands.subcommands.player;

import me.plohn.wfactions.factions.FPlayer;
import me.plohn.wfactions.factions.Faction;
import me.plohn.wfactions.factions.manager.FactionManager;
import me.plohn.wfactions.commands.SubCommand;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import javax.swing.text.html.Option;
import java.util.Optional;

public class CreateCommand extends SubCommand { // /team create
    @Override
    public String getName() {
        return "create";
    }

    @Override
    public String getDescription() {
        return "Create a team";
    }

    @Override
    public String getSyntax() {
        return "/team create <faction_name>";
    }

    @Override
    public void perform(Player player, String[] args) {
        //player has faction
        Optional<FPlayer> factionPlayer = FactionManager.getFactionPlayer(player);
        if (factionPlayer.isPresent()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou already have a team"));
            return;
        }

        if (args.length < 2){
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cYou need to provide a name!"));
            player.sendMessage(this.getDescription());
            return;
        }
        String factionName = args[1];

        try {
            FactionManager.createFaction(factionName,player);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&aCreated team: &a&o"+factionName));
        } catch (Exception e) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', e.getMessage()));
        }
    };
}
