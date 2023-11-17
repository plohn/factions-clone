package me.plohn.wfactions.commands.manager;

import me.plohn.wfactions.commands.SubCommand;
import me.plohn.wfactions.commands.subcommands.player.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Optional;

public class CommandManager implements CommandExecutor {
    private ArrayList<SubCommand> subCommands = new ArrayList<>();

    private JavaPlugin plugin;
    public CommandManager(JavaPlugin pl){
        this.plugin = pl;
        subCommands.add(new CreateCommand());
        subCommands.add(new ShowCommand());
        subCommands.add(new InviteCommand());
        subCommands.add(new DeleteInviteCommand());
        subCommands.add(new JoinCommand());
        subCommands.add(new KickCommand());
        subCommands.add(new DisbandCommand());
        subCommands.add(new ClaimCommand());
        subCommands.add(new UnclaimCommand());
    }
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String root, String[] args) {
        if (!(commandSender instanceof Player)) {
            this.plugin.getLogger().info("Only players can run faction commands");
            return false;
        }
        Player player = (Player) commandSender;
        if (args.length > 0){
            String option = args[0];
             Optional<SubCommand> subcommand = subCommands.stream()
                     .filter(subCommand -> subCommand.getName()
                             .equals(option))
                     .findFirst();
             if (subcommand.isPresent()) {
                 subcommand.get().perform(player,args);
             }else {
                 player.sendMessage("Command not found");
             }
        }else {
            player.sendMessage("Please use the correct syndax for factions commands");
        }
        return false;
    }

    public ArrayList<SubCommand> getSubCommands(){
        return subCommands;
    }
}
