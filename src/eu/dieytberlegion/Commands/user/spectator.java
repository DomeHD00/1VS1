package eu.dieytberlegion.Commands.user;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.Objecks.Games;

public class spectator implements CommandExecutor {

	private OneVsOne plugin;

	public spectator(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

		if (command.getName().equalsIgnoreCase("spectate")) {

			if (!(sender instanceof Player)) {
				sender.sendMessage(OneVsOne.prefix + "§cNur Spieler dürfen /spectator nutzen!");
				return true;
			}
			if(args.length == 0){
			Player p = (Player) sender;
			Inventory inv = p.getServer().createInventory(null, 9*6, "§bSpiele");
		 
		   if(OneVsOne.onGame.containsKey(p)){
				p.sendMessage(OneVsOne.prefix + "§cDu must erst dein Spiel beenden!");
			   return true;
			}
			if (OneVsOne.kitmenu.contains(p)) {
				p.sendMessage(OneVsOne.prefix + "§cDu musst erst den Kiteditor verlassen!");
				return true;
			}
			
				 int i = 0;
				 
						for(Games game : OneVsOne.Games){
                            inv.setItem(i, CreateIteam(Material.GOLD_BLOCK, game.getName()));
                            i++;
                            if(i == 9*6){
                            	break;
                            }
						}
						while(!(i == 9*6)){
							inv.setItem(i, CreateGlass());
							i++;
						}
				 
				 
				 p.openInventory(inv);
				 return true;
			}else if(args.length == 1){
				Player p = (Player) sender;
				Player taget = Bukkit.getPlayer(args[0]);
				if(taget == null || OneVsOne.onGame.containsKey(taget)){
					p.sendMessage("§cDer Spieler Spielt garde nicht!");
					return true;
				}
				if(taget == p){
					p.sendMessage(OneVsOne.prefix + "§cDu Kannst dich nicht selbst beobachten!");
					return true;
				}
				if(OneVsOne.onGame.containsKey(p)){
					p.sendMessage(OneVsOne.prefix + "§cDu must erst dein Spiel beenden!");
					return true;
				}

				Games game = OneVsOne.onGame.get(taget.getUniqueId());
				
				
				
				for(Player all : Bukkit.getOnlinePlayers()){
					all.hidePlayer(p);
				}
				OneVsOne.spectator.add(p.getUniqueId());
				game.getSpectatPlayer().add(p);
				p.setAllowFlight(true);
				p.closeInventory();
				p.getInventory().clear();
				p.teleport(game.getArena().getSpctator());
				p.sendMessage(OneVsOne.prefix + "§aDu beobachtes nun das Spiel " + game.getName());
				
				
			}else {
				sender.sendMessage(OneVsOne.prefix + "§c/spectator OR /spectator <Name>");
			}
		}
		
		return true;
	}	

	private static ItemStack CreateGlass() {

		ItemStack item = new ItemStack(Material.STAINED_GLASS_PANE, 1, (byte) 14);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§cKein Spiel");
		item.setItemMeta(meta);

		return item;
	}
	
	private static ItemStack CreateIteam(Material m, String Name) {

		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		item.setItemMeta(meta);

		return item;
	}


	}