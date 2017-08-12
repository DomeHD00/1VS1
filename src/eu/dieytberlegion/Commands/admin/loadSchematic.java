package eu.dieytberlegion.Commands.admin;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class loadSchematic implements CommandExecutor {

	private OneVsOne plugin;
	
	public loadSchematic(OneVsOne Instance) {
		this.plugin = OneVsOne.getInstance();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		
		if (command.getName().equalsIgnoreCase("loadSchematic")) {
			
			if(!(sender instanceof Player)){
				sender.sendMessage("§cNur player Dürfen /loadSchematic nutzen");
				return true;
			}
			
			Player p = (Player) sender;
			
			if(args.length == 1){
			
			if(p.hasPermission("1vs1.system.loadSchematic")){
			
				String Schematicload = args[0];
				
				/* Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
			        	try {
			    			ResultSet pos =  mysql.getResult("SELECT * FROM setup WHERE Schematic = '"+ Schematicload +"'");
			    			if(pos.next()){
			    				schematic = pos.getString("Schematic");
			    			}
			    		} catch (SQLException e1) {
			    			e1.printStackTrace();
			    		}
			        	
			        } );*/
				
				
				Games game = new Games();
			    Boolean b = loadSchematic2(Schematicload, game, p);
			    pasteSchematic(game);
			    Location l = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), 0, 200, 0);
			    if(b){
			    p.teleport(l);
			    }
				
			}
		
		}else {
			p.sendMessage(OneVsOne.prefix + "§c/loadSchematic [Schematic]");
		}
			
		}
		return true;
	}
	public boolean pasteSchematic(Games game){
		if(!OneVsOne.MapList.containsKey(game)){
			Bukkit.broadcastMessage("return false : pastSchematic");
			return false;
		}
		
		
		
		ArrayList<String> list = OneVsOne.MapList.get(game);
		World w = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
		Location l = new Location(w , 0, 0, 0);
		l.setX(0);
		l.setY(200);
		l.setZ(0);
		Bukkit.broadcastMessage("Tp zu X:" + l.getBlockX() + " Y:" + l.getBlockY() + " Z:" + l.getBlockZ() );
		for(String str : list){
			String[] array = str.split(";");
			Material material = Material.valueOf(array[0]);
			byte data = Byte.valueOf(array[1]);
			int posX = Integer.parseInt(array[2]);
			int posY = Integer.parseInt(array[3]);
			int posZ = Integer.parseInt(array[4]);
			//Bukkit.broadcastMessage("Block ID" + l.getBlock().getType().getId());
			l.clone().add(posX, posY, posZ).getBlock().setType(material);
			l.clone().add(posX, posY, posZ).getBlock().setData(data);
		}
		
		return true;
	}
	
	public boolean loadSchematic2(String FileName,Games game,Player p){
		File file = getFile(FileName);
		if(!file.exists()){
			Bukkit.broadcastMessage("return false : loadSchematic");
			return false;
		}
		
		ArrayList<String> List = getStringListToBlock(FileName);
		if(List != null){
			if(p.hasMetadata("schematicLoad")){
			   p.sendMessage(OneVsOne.prefix + "§cBitte Bende zuerste die ander Schematic");
			   return false;	
		    }
			if(!OneVsOne.MapList.containsKey(game)){
				OneVsOne.MapList.remove(game);
			}
			p.setMetadata("schematicLoad", new FixedMetadataValue(OneVsOne.getInstance(), List));
			OneVsOne.MapList.put(game, List);
			return true;
		}
		
		
		return false;
	}
	
	@SuppressWarnings("resource")
	public ArrayList<String> getStringListToBlock(String FileName) {
		File file = getFile(FileName);
		
		try {
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			ArrayList<String> list = new ArrayList<>();
			String line = null;
			while((line = bufferedReader.readLine()) != null ){
				list.add(line);
			}
			
			return list;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	private File getFile(String FileName){
		return new File(OneVsOne.getInstance().getDataFolder() + "/schematics/" + FileName + ".schem");
	}

	public static void SetKit(Player p,Inventory kit){
		   p.getInventory().clear();
		   p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		   p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		   p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		   p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
		   p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));	
		}	
	
	
}
