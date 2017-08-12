package eu.dieytberlegion.Commands.moderation;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class statsRemove implements CommandExecutor {

	private OneVsOne plugin;
	
	public statsRemove(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		if (command.getName().equalsIgnoreCase("statsRemove")) {
			
			if(!(sender instanceof Player)){
				sender.sendMessage("§cNur player dürfen /createarena nutzen");
				return true;
			}
			
			Player p = (Player) sender;
			
			
			if(p.hasPermission("1vs1.system.statsremove")){
				
				if(args.length == 1){
					Player Player = Bukkit.getServer().getPlayer(args[0]);
					if(Player == null){
						p.sendMessage(OneVsOne.prefix + "§cSpieler nicht Online!");
						return true;
					}
					Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
						try{
							 mysql.update("DELETE FROM stats WHERE Name = '"+ Player.getName() +"'");
							 mysql.update("INSERT INTO stats VALUES ('"+ Player.getUniqueId() +"','"+ Player.getName() +"','0','0','0','0','0','0')");
						 
							 p.sendMessage(OneVsOne.prefix + "§aDu stats von Spieler §e" + Player.getPlayer() + " §awurden zurückgesetzt!");
						}catch(Exception e){
							p.sendMessage(OneVsOne.prefix + "§cSpieler nicht registriert");
						}
						
						
						
					});
					
				}else{
					p.sendMessage(OneVsOne.prefix + "§c/sr [Player]");
				}
				
				
			}

		}
	    return true;
	}
}
