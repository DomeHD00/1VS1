package eu.dieytberlegion.Commands.user;

import java.sql.ResultSet;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class stats implements CommandExecutor {

	private OneVsOne plugin;

	public stats(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("stats")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler Dürfen /stats nutzen");
				return true;
			}
			Player p = (Player) sender;
	
			Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
				try {
					ResultSet pos =  mysql.getResult("SELECT * FROM stats WHERE UUID = '"+p.getUniqueId()+"'");
					if(pos.next()){
			
						p.sendMessage(OneVsOne.prefix + "Deine Kills:§e " + pos.getInt("kills"));
						p.sendMessage(OneVsOne.prefix + "Deine Tode:§e " + pos.getInt("Deaths"));
						 if(pos.getInt("kills") != 0 && pos.getInt("Deaths") != 0){	 
			    				double kd =  (double) pos.getInt("kills") / (double) pos.getInt("Deaths");
			    				kd = kd * 100;
			    				kd = Math.round(kd);
			    				kd = kd / 100;
							 p.sendMessage(OneVsOne.prefix + "Deine KD:§e " + kd);
						 }else{
							 p.sendMessage(OneVsOne.prefix + "Deine KD:§e 0"); 
						 }
						
						p.sendMessage(OneVsOne.prefix + "Deine Gewonnen Spiele:§e " + pos.getInt("Win"));
						p.sendMessage(OneVsOne.prefix + "Deine Verloren Spiele:§e " + pos.getInt("Lost"));
						
						
					}
				}catch (Exception e) {
	
					}
				});
		}
	return true;
	}
	
}
