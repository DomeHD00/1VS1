package eu.dieytberlegion.Commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class domeMode implements CommandExecutor {

	
	private OneVsOne plugin;

	public domeMode(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("domemode")) {
			
			if (!(sender instanceof Player)) {
				sender.sendMessage("§cNur Spieler dürfen /domemode nutzen");
				return true;
			}
			

				Player p = (Player) sender;

				if(p.hasPermission("1vs1.system.domemode")){
					if(OneVsOne.domeMode.contains(p.getUniqueId())){
						OneVsOne.domeMode.remove(p.getUniqueId());
						p.sendMessage(OneVsOne.prefix + "§aDu hast den DomeMode Verlassen!");
					}else {
						OneVsOne.domeMode.add(p.getUniqueId());
						p.sendMessage(OneVsOne.prefix + "§aDu hast den DomeMode betretten!");
					}
				}else{
					p.sendMessage(OneVsOne.prefix + "§cDu bist Kein Dome !");
				}
				

				
				
			
		}
		return true;
	}
}
