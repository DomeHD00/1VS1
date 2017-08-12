package eu.dieytberlegion.Commands.admin;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class createArena implements CommandExecutor {

	private OneVsOne plugin;
	
	public createArena(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		if (command.getName().equalsIgnoreCase("createarena")) {
			
			if(!(sender instanceof Player)){
				sender.sendMessage("§cNur player dürfen /createarena nutzen");
				return true;
			}
			
			Player p = (Player) sender;
			
			
			if(p.hasPermission("1vs1.system.createarena")){
				
				if(args.length == 1){
					
					if(args[0].equalsIgnoreCase("lobby")){
						OneVsOne.getInstance().getConfig().set("OneVsOne.world.lobby", p.getLocation());
						
						OneVsOne.getInstance().saveConfig();
						OneVsOne.getInstance().reloadConfig();
						p.sendMessage(OneVsOne.prefix + "§aDu hast den Lobby gesetzt");
						return true;
					}
					
					
				}
				if(args.length == 2){
					String name = args[0];
					String schmatic = args[1];
					
					if(name.length() >= 20 || schmatic.length() >= 20){
						p.sendMessage(OneVsOne.prefix + "§cName und Schematic dürfen maximal 20 Zeichen haben!");
						return true;
					}
					
					Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
						mysql.update("INSERT INTO setup VALUES ('"+ schmatic +"','"+ name +"','0','0','0','0','0','0','0','0','0','0','0', '0', '0')");
			        } );
					p.sendMessage(OneVsOne.prefix + "§aDie Map wurde erstellt!");
					return true;
				}
					p.sendMessage(OneVsOne.prefix + "§c/createarena <Name> <Schmatic>");
					p.sendMessage(OneVsOne.prefix + "§c/createarena lobby");
				    return true;
				
				
				
				
			}else {
				p.sendMessage(OneVsOne.nopem);
				return true;
			}
			
		}
		
		return true;
	}

}
