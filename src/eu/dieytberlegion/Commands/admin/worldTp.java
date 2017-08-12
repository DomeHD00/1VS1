package eu.dieytberlegion.Commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class worldTp implements CommandExecutor {

	private OneVsOne plugin;

	public worldTp(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("worldtp")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler Dürfen /worldtp nutzen");
				return true;
			}

			if (sender.hasPermission("1vs1.command.worldtp")) {

				Player p = (Player) sender;

				if (args.length == 1) {
					try{
					p.teleport(Bukkit.getWorld(args[0]).getSpawnLocation());
					p.sendMessage(OneVsOne.prefix + "Du bist jetzt auf Der Welt:§a " + args[0]);
					}catch(Exception e){
				    p.sendMessage(OneVsOne.prefix + "§cWelt nicht gefunden !");
					}

				} else {
					p.sendMessage(OneVsOne.prefix + "§c/worldTp <world>");
				}

			}

		}
		return true;
	}

}
