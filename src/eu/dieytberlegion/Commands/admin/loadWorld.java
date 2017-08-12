package eu.dieytberlegion.Commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class loadWorld implements CommandExecutor {

	private OneVsOne plugin;

	public loadWorld(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("loadWorld")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage("§cNur player Dürfen /createArena nutzen");
				return true;
			}

			Player p = (Player) sender;

			if (p.hasPermission("1vs1.system.loadworld")) {

				if (args.length == 1) {

					
					WorldCreator creator = new WorldCreator(args[0]);
					creator.createWorld();
					
					p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
					
				} else {
					sender.sendMessage(OneVsOne.prefix + "§c/loadworld <world>");
				}

			}
		}
		return true;
	}

}
