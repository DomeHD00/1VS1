package eu.dieytberlegion.Commands.admin;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class deaultkitSave implements CommandExecutor {

	private OneVsOne plugin;

	public deaultkitSave(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@SuppressWarnings({ "unused", "deprecation" })
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("deaultkitSave")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler dürfen /deaultkitSave nutzen!");
				return true;
			}
			Player p = (Player) sender;

			
			if (p.hasPermission("1vs1.kit.defaultkit")) {
				
			
			
			if (!OneVsOne.kitmenu.contains(p.getUniqueId())) {
				p.sendMessage(OneVsOne.prefix + "§cDu bist nicht im Kiteditor!");
				return true;
			}
			if (args.length == 0) {

				Inventory invSave = p.getInventory();
				ItemStack Helmet = p.getInventory().getHelmet();
				ItemStack Chestplate = p.getInventory().getChestplate();
				ItemStack Leggings = p.getInventory().getLeggings();
				ItemStack Boots = p.getInventory().getBoots();
				
				

				Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
					String invNew = "";
					String s = ";";					
					for (int i = 0; i <= 4 * 9; i++) {
						
						try{
							invNew = invNew + invSave.getItem(i).getType().getId() + s;
						}catch (Exception e) {
							invNew = invNew + -1 + s;
						}
						
						
					}

					if(Helmet != null){
						invNew = invNew + "H:" + Helmet.getTypeId() + s;
					}
					if(Chestplate != null){
						invNew = invNew + "C:" + Chestplate.getTypeId() + s;
					}
					if(p.getInventory().getLeggings() != null){
						invNew = invNew + "L:" + p.getInventory().getLeggings().getTypeId() + s;
					}
					if(Boots != null){
						invNew = invNew + "B:" + Boots.getTypeId() + s;
					}
					mysql.update("DELETE FROM kits WHERE kitName = 'Deault-kit' AND UUID = 'System'"); 
					mysql.update("INSERT INTO kits VALUES ('System','S','Deault-kit','" + invNew + "')");
					onJoin.Joininv(p); 
					OneVsOne.kitmenu.remove(p.getUniqueId());
					OneVsOne.fresplayer.remove(p.getUniqueId());
					p.sendMessage(OneVsOne.prefix + "§aDu hast das §eNeu Deaultkit§a gespeichert!");
				}); 
				finish(p);
			} else {
				p.sendMessage(OneVsOne.prefix + "§c/deaultkitSave");
			}

		}else {
			p.sendMessage(OneVsOne.nopem);
		}
		}
		return true;
	
	}
	
  private void finish(Player p){
		OneVsOne.kitmenu.remove(p.getUniqueId());
		OneVsOne.fresplayer.remove(p.getUniqueId());
		
		p.setGameMode(GameMode.SURVIVAL);
	  
  }
	
	
}
