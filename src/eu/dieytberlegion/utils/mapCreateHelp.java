package eu.dieytberlegion.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import eu.dieytberlegion.Objecks.Arena;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class mapCreateHelp {
	
	ArrayList<String> list = new ArrayList<String>();
	
	public void loadmap(String Name, int line, Location loc1,  Location loc2,  Location loc3,int multi){
		for(int i = 1; i <= multi; i++){
		loadSchematic(Name);
		if(!(line == -1)){
			if(OneVsOne.Line.containsKey(Name)){
				OneVsOne.Line.remove(Name);
			}
			OneVsOne.Line.put(Name, line);
		}else {
			line = OneVsOne.Line.get("Name");
		}
		
		
		if(!OneVsOne.Arenaz.containsKey(Name)){
			OneVsOne.Arenaz.put(Name, 1);
		}
		Arena ar  = new Arena(Name, line, loc1, loc2, loc3);
		
		pasteSchematic(Name, line, ar);
		int o = OneVsOne.Arenaz.get(Name) + 1;
		OneVsOne.Arenaz.remove(Name);
		OneVsOne.Arenaz.put(Name, o);
		
		if(!OneVsOne.Arenas.containsKey(Name)){
			ArrayList<Arena> lar = new ArrayList<>();
			lar.add(ar);
		    OneVsOne.Arenas.put(Name, lar);
		}else{
			ArrayList<Arena> lar = OneVsOne.Arenas.get(Name);
			lar.add(ar);
			OneVsOne.Arenas.remove(Name);
			OneVsOne.Arenas.put(Name, lar);
		}
		}
	
	}
	
	public boolean pasteSchematic(String Name,int line,Arena ar){
		/*if(!OneVsOne.MapList.containsKey(game)){
			Bukkit.broadcastMessage("return false : pastSchematic");
			return false;
		}*/
		
		World w = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
		Location l = new Location(w , 0, 0, 0);
		l.setX(line * 100);
		l.setY(200);
		l.setZ(OneVsOne.Arenaz.get(Name) * 300);
		paste : for(String str : list){
			String[] array = str.split(";");
			Material material = Material.valueOf(array[0]);
			if(material.equals(Material.AIR)){
				continue paste;
			}
			byte data = Byte.valueOf(array[1]);
			int posX = Integer.parseInt(array[2]);
			int posY = Integer.parseInt(array[3]);
			int posZ = Integer.parseInt(array[4]);
			l.clone().add(posX, posY, posZ).getBlock().setType(material);
			l.clone().add(posX, posY, posZ).getBlock().setData(data);
			//Bukkit.broadcastMessage("return true 2 : pastSchematic");
		}
	
		Location loc1 = new Location(w, l.getX() + ar.getSpawn1().getX(), ar.getSpawn1().getY(), l.getZ() + ar.getSpawn1().getZ(),ar.getSpawn1().getYaw(), ar.getSpawn1().getPitch());
		Location loc2 = new Location(w, l.getX() + ar.getSpawn2().getX(), ar.getSpawn2().getY(), l.getZ() + ar.getSpawn2().getZ(),ar.getSpawn2().getYaw(), ar.getSpawn2().getPitch());
		Location loc3 = new Location(w, l.getX() + ar.getSpctator().getX(), ar.getSpctator().getY(), l.getZ() + ar.getSpctator().getZ(),ar.getSpctator().getYaw(), ar.getSpctator().getPitch());
		ar.setSpawn1(loc1);
		ar.setSpawn2(loc2);
		ar.setSpawn3(loc3);
		return true;
	}
	
	public boolean loadSchematic(String FileName){
		File file = getFile(FileName);
		if(!file.exists()){
			Bukkit.broadcastMessage("return false : loadSchematic");
			return false;
		}
		
	   list = getStringListToBlock(FileName);
		if(list != null){
		/*	if(!OneVsOne.MapList.containsKey(game)){
				OneVsOne.MapList.remove(game);
			}*/
			//OneVsOne.MapList.put(game, List);
			
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
		File file = new File(OneVsOne.getInstance().getDataFolder() + "/schematics/" + FileName + ".schem");
		return file;
	}
	
	
}
