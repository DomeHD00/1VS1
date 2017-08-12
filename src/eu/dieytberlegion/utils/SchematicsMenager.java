package eu.dieytberlegion.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class SchematicsMenager {
	
	private OneVsOne plugin;

	public SchematicsMenager(OneVsOne plugin) {
		this.plugin = plugin;
		init();
	}

	private void init() {
		File file = new File(plugin.getDataFolder() + "/schematics");
		
		if(!file.exists()){
			file.mkdirs();
		}
	}
	
	@SuppressWarnings("resource")
	public boolean saveSchematic(Player p,String FileName){
		File file = getFile(FileName);
		String nextLine = System.getProperty("line.sepatator");
		
		if(file.exists()){
			p.sendMessage(OneVsOne.prefix + "§cDie Datei Gibt es schon");
			return false;
		}
		
		try {
			file.createNewFile();
			ArrayList<String> formBlockListToString = formBlockListToString(p.getLocation(), getBlocks(p));
			BufferedWriter BufferedWriter = new BufferedWriter(new FileWriter(file, true));
			
			for(String str : formBlockListToString){
				//BufferedWriter.append(str);
				BufferedWriter.append(str);
				//p.sendMessage(nextLine);
				BufferedWriter.newLine();
				//BufferedWriter.append(nextLine);
			}
			BufferedWriter.flush();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public boolean loadSchematic(Player p,String FileName){
		File file = getFile(FileName);
		if(!file.exists()){
			return false;
		}
		
		ArrayList<String> List = getStringListToBlock(FileName);
		
		if(List != null && !List.isEmpty()){
			if(p.hasMetadata("schematic")){
				p.removeMetadata("schematic", OneVsOne.getInstance());
			}
			p.setMetadata("schematic",  new FixedMetadataValue(plugin, List));
			return true;
		}
		
		
		return false;
	}


	public boolean pasteSchematic(Player p){
		if(!p.hasMetadata("schematic")){
			return false;
		}
		ArrayList<String> list = (ArrayList<String>) p.getMetadata("schematic").get(0).value();
		Location l = p.getLocation();
		for(String str : list){
			String[] array = str.split(";");
			Material material = Material.valueOf(array[0]);
			byte data = Byte.valueOf(array[1]);
			int posX = Integer.parseInt(array[2]);
			int posY = Integer.parseInt(array[3]);
			int posZ = Integer.parseInt(array[4]);
			l.clone().add(posX, posY, posZ).getBlock().setType(material);
			l.clone().add(posX, posY, posZ).getBlock().setData(data);
		}
		
		return true;
	}
	public boolean deleteSchematic(String FileName) {
		File file = getFile(FileName);
		if(!file.exists()){
			return false;
		}
		file.delete();
		return true;
	}
	
	private File getFile(String FileName){
		return new File(plugin.getDataFolder() + "/schematics/" + FileName + ".schem");
	}
	private String getBlocktoString(Block b,Location l){
		int diffX = b.getX() - l.getBlockX();
		int diffY = b.getY() - l.getBlockY();
		int diffZ = b.getZ() - l.getBlockZ();
		return b.getType().name() + ";" + b.getData() + ";" + diffX + ";" + diffY + ";" + diffZ + ";" ;
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
	private List<Block> getBlocks(Player p){
		return new Cuboid((Location) p.getMetadata("editPosOne").get(0).value(), (Location) p.getMetadata("editPosTwo").get(0).value()).getBlocks();
	}
	
	private ArrayList<String> formBlockListToString(Location l, List<Block> blocks){
		 ArrayList<String> list = new ArrayList<>();
		 blocks.forEach(Block -> {
			 list.add(getBlocktoString(Block, l));
		 });
		 return list;
	}

}
