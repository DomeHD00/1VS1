package eu.dieytberlegion.Commands.admin;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class chatRead  implements CommandExecutor {

	private OneVsOne plugin;
	
	public chatRead(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		if (command.getName().equalsIgnoreCase("chatread")) {
			
			if(!(sender instanceof Player)){
				sender.sendMessage("§cNur player dürfen /createarena nutzen");
				return true;
			}
			
			Player p = (Player) sender;
			
			
			if(p.hasPermission("1vs1.system.spy")){
				
				if(OneVsOne.read.contains(p)){
					OneVsOne.read.remove(p);
					p.sendMessage(OneVsOne.prefix + "§bDu siehst jetzt nur deine Nachrichten!");
				}else{
				OneVsOne.read.add(p);
				p.sendMessage(OneVsOne.prefix + "§bDu siehst jetzt alle Nachrichten!");
				}
				
			}
		
		}

		return true;	
	}
}
