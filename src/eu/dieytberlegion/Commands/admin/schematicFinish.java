package eu.dieytberlegion.Commands.admin;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class schematicFinish implements CommandExecutor {

	private OneVsOne plugin;
	
	public schematicFinish(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
           if (command.getName().equalsIgnoreCase("schematicfinish")) {
			
			if(!(sender instanceof Player)){
				sender.sendMessage("§cNur player Dürfen /schematicfinish nutzen");
				return true;
			}
			
			Player p = (Player) sender;
			
			
			if(p.hasPermission("1vs1.system.schemticFinish")){
				
				if(!p.hasMetadata("schematicLoad")){
					return true;
				}
				
				ArrayList<String> list = (ArrayList<String>) p.getMetadata ("schematicLoad").get(0).value();
				
				World w = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
				Location l = new Location(w , 0, 0, 0);
				l.setX(0);
				l.setY(200);
				l.setZ(0);
				
				for(String str : list){
					String[] array = str.split(";");
					Material material = Material.AIR;
					byte data = Byte.valueOf(array[1]);
					int posX = Integer.parseInt(array[2]);
					int posY = Integer.parseInt(array[3]);
					int posZ = Integer.parseInt(array[4]);
					//Bukkit.broadcastMessage("Block ID" + l.getBlock().getType().getId());
					l.clone().add(posX, posY, posZ).getBlock().setType(material);
					l.clone().add(posX, posY, posZ).getBlock().setData(data);
				}
					
				p.removeMetadata("schematicLoad", OneVsOne.getInstance());
				p.teleport((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));
			}
		
		
		
	}
           return true;
 }
}
