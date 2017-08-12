package eu.dieytberlegion.status;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.World.Environment;
import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.metadata.FixedMetadataValue;

import com.avaje.ebean.annotation.CreatedTimestamp;
import com.avaje.ebean.enhance.asm.Type;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.Objecks.Arena;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.utils.mapCreateHelp;

public class gameStart {
	//public Variablen
	public  Location start1;
	public  Location start2;
	public  Inventory kit;
	//private Variablen
	String schematic;
	int schedule;
	World w = null;
	Arena arr = null;		

    Location loc1 = null;
	Location loc2 = null;	
	boolean go = true;
	boolean go1 = true;
	String[] amor = null;

	
	
	
	@SuppressWarnings("static-access")
	public static void Start(Player p1,Player p2, String Map, String kit,int round,gameStart gi){

		    	try {
					ResultSet pos =  mysql.getResult("SELECT * FROM setup WHERE Name = '"+ Map +"'");
					if(pos.next()){
						gi.schematic = pos.getString("Schematic");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		    	
		
		
		
		p1.setGameMode(GameMode.SURVIVAL);
		p2.setGameMode(GameMode.SURVIVAL);
		
		if(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1") != null) {
			World w = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
	   }else {
		   World w = Bukkit.getWorld("map");
		   OneVsOne.getInstance().getConfig().set("OneVsOne.world.name1", w);
	   }
		
		
		
		OneVsOne.fresplayer.add(p1.getUniqueId());
		OneVsOne.fresplayer.add(p2.getUniqueId());
		
		if(OneVsOne.Builder.contains(p1.getUniqueId())){
			OneVsOne.Builder.remove(p1.getUniqueId());
		}
		if(OneVsOne.Builder.contains(p2.getUniqueId())){
			OneVsOne.Builder.remove(p2.getUniqueId());
		}
		
		String Gamename = "§b" + p1.getDisplayName()+" VS "+p2.getDisplayName();
		
		for(Arena ar : OneVsOne.Arenas.get(gi.schematic)){
			if(ar.getStatus().equals("free")){
				gi.arr = ar;
				gi.arr.setStatus("Game");
				break;
			}
		}
		while(gi.arr == null){
				 
				 
				 //  mysql.update("INSERT INTO games VALUES ('"+ Gamename +"','"+ Map +"','"+ multiplikator +"', '"+ p1.getName() +"')");
				 
			    	try {
						ResultSet pos =  mysql.getResult("SELECT * FROM setup WHERE Name = '"+ Map +"'");
						if(pos.next()){
							gi.schematic = pos.getString("Schematic");
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				 
				 
		    			try {
		    				ResultSet loc =  mysql.getResult("SELECT * FROM setup WHERE Name = '"+ Map +"'");
		    				if(loc.next()){
		    	                  int pos1X = loc.getInt("pos1X");
		    	                  int pos1Y = loc.getInt("pos1Y");
		    	                  int pos1Z = loc.getInt("pos1Z") +  OneVsOne.Arenaz.get(gi.schematic)*300;
		    	                  float Yaw1 = loc.getFloat("pos1Yaw");
		    	                  float Pitch1 = loc.getFloat("pos1Pitch");
		    	                  
		    	                  Location loc1 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos1X, pos1Y, pos1Z, Yaw1, Pitch1);
		    	                  
		    	                  int pos2X = loc.getInt("pos2X");
		    	                  int pos2Y = loc.getInt("pos2Y");
		    	                  int pos2Z = loc.getInt("pos2Z") +  OneVsOne.Arenaz.get(gi.schematic)*300;
		    	                  float Yaw2 = loc.getFloat("pos2Yaw");
		    	                  float Pitch2 = loc.getFloat("pos2Pitch");
		    	                  Location loc2 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos2X, pos2Y, pos2Z,Yaw2, Pitch2); 
		    	                  
		    	                  int pos3X = loc.getInt("SposX");
		    	                  int pos3Y = loc.getInt("SposY");
		    	                  int pos3Z = loc.getInt("SposZ") + OneVsOne.Arenaz.get(gi.schematic)*300;
		    	                  
		    	                  Location loc3 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos3X, pos3Y, pos3Z); 

		    	                  
		    	                  new mapCreateHelp().loadmap(gi.schematic, -1, loc1, loc2, loc3, 1);
		    	                  
		    	                  }else{
		    	                Location loc1 = null;
		    	                Location loc2 = null;
		    				}
		    			}catch(SQLException e){
		    				System.out.println(e);
		    				Location loc1 = null;
		    				Location loc2 = null;
		    			}
		
		
		    			for(Arena ar : OneVsOne.Arenas.get(gi.schematic)){
		    				if(ar.getStatus().equals("free")){
		    					gi.arr = ar;
		    					gi.arr.setStatus("Game");
		    					break;
		    				}
		    			}
		}
		
		
		Games game = new Games(p1.getUniqueId(),p2.getUniqueId(),Gamename,Map, gi.arr);
		game.setGameInstanc(gi);
        game.setMultiplikator( OneVsOne.Arenaz.get(gi.schematic));
        OneVsOne.Games.add(game);
        if(!kit.equals("-")){
        game.setKit(kit);
        }else{
        game.setKit("-");	
        }
		game.setRound(round);
		
		gi.loc1 = gi.arr.getSpawn1();
		gi.loc2 = gi.arr.getSpawn2();
		
		gi.goStart(game, p1, p2, gi,  OneVsOne.Arenaz.get(gi.schematic));
		
		//mysql abfragen 
		/* Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
			 
			 
			   mysql.update("INSERT INTO games VALUES ('"+ Gamename +"','"+ Map +"','"+ multiplikator +"', '"+ p1.getName() +"')");
			 
		    	try {
					ResultSet pos =  mysql.getResult("SELECT * FROM setup WHERE Name = '"+ Map +"'");
					if(pos.next()){
						gi.schematic = pos.getString("Schematic");
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			 
			 
	    			try {
	    				ResultSet loc =  mysql.getResult("SELECT * FROM setup WHERE Name = '"+ Map +"'");
	    				if(loc.next()){
	    	                  int pos1X = loc.getInt("pos1X");
	    	                  int pos1Y = loc.getInt("pos1Y");
	    	                  int pos1Z = loc.getInt("pos1Z") + multiplikator*300;
	    	                  float Yaw1 = loc.getFloat("pos1Yaw");
	    	                  float Pitch1 = loc.getFloat("pos1Pitch");
	    	                  
	    	                  gi.loc1 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos1X, pos1Y, pos1Z, Yaw1, Pitch1);
	    	                  
	    	                  int pos2X = loc.getInt("pos2X");
	    	                  int pos2Y = loc.getInt("pos2Y");
	    	                  int pos2Z = loc.getInt("pos2Z") + multiplikator*300;
	    	                  float Yaw2 = loc.getFloat("pos2Yaw");
	    	                  float Pitch2 = loc.getFloat("pos2Pitch");
	    	                  gi.loc2 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos2X, pos2Y, pos2Z,Yaw2, Pitch2); 
	    	                  
	    	                  }else{
	    					gi.loc1 = null;
	    					gi.loc2 = null;
	    				}
	    			}catch(SQLException e){
	    				System.out.println(e);
	    				gi.loc1 = null;
	    				gi.loc2 = null;
	    			}
	    			game.setCountdown(10);
	    			gi.goStart(game, p1, p2, gi, multiplikator);

		 } );*/
	    			//continueStart(game, p1, p2, Map);
	      
		 
		


		
		



		
		


		
		
}

	public void goStart(Games game,Player p1,Player p2, gameStart gi, int multiplikator){
		
		
		OneVsOne.onGame.put(p1.getUniqueId(), game);
		OneVsOne.onGame.put(p2.getUniqueId(), game);
		gi.Setinv(p1, gi);
		gi.Setinv(p2, gi);
		
	     gi.schedule = Bukkit.getScheduler().scheduleSyncRepeatingTask(OneVsOne.getInstance(), new Runnable(){
			@Override
			public void run() {
				if(game.getCountdown() > 0){
					// Wenn Der contdown läuft | wird das jede Sekunde aufgrufen !
					int i = game.getCountdown();
					i--;
					game.setCountdown(i);
					p1.setLevel(game.getCountdown());
					p2.setLevel(game.getCountdown());
					
					//spiele Arena Erstellen !
					if(gi.go){
						if(gi.loc2 != null){
					try{
					gi.go = false;
					gi.start1 = gi.loc1;
					gi.start2 = gi.loc2;
				    /*OneVsOne.getGameStart().loadSchematic(gi.schematic, game);
					OneVsOne.getGameStart().pasteSchematic(game, multiplikator);	*/
					p1.teleport(gi.loc1);
					p2.teleport(gi.loc2);
					OneVsOne.challenge.remove(p1.getUniqueId());
					OneVsOne.challenge.remove(p2.getUniqueId());

				    
					}catch (Exception e) {
					}
					}else{
						Bukkit.broadcastMessage("loc NULL");
					}
					}
					
					if(game.getCountdown() == 2 || game.getCountdown() == 3){
						p1.sendMessage(OneVsOne.prefix + "Spiel beginnt in:§e " + game.getCountdown() + " Sekunden");
						p2.sendMessage(OneVsOne.prefix + "Spiel beginnt in:§e " + game.getCountdown() + " Sekunden");
					}
					if(game.getCountdown() == 1){
						p1.sendMessage(OneVsOne.prefix + "Spiel beginnt in:§e " + game.getCountdown() + " Sekunde");
						p2.sendMessage(OneVsOne.prefix + "Spiel beginnt in:§e " + game.getCountdown() + " Sekunde");
					}
					
					if(game.getCountdown() == 0){
					p1.sendMessage(OneVsOne.prefix + "§aDas Spiel beginnt jetzt!");
					p2.sendMessage(OneVsOne.prefix + "§aDas Spiel beginnt jetzt!");
					OneVsOne.fresplayer.remove(p1.getUniqueId());
					OneVsOne.fresplayer.remove(p2.getUniqueId());
					p1.setLevel(0);
					p2.setLevel(0);
					}
				}else{
					Bukkit.getScheduler().cancelTask(gi.schedule);
					if(OneVsOne.fresplayer.contains(p1.getUniqueId())){
						OneVsOne.fresplayer.remove(p1.getUniqueId());
					}
					if(OneVsOne.fresplayer.contains(p2.getUniqueId())){
						OneVsOne.fresplayer.remove(p2.getUniqueId());
					}
					
					game.setStatus("onGame");
				}
			}
	    }, 0, 20); 

	}
	
	public boolean pasteSchematic(Games game,int multiplikator){
		if(!OneVsOne.MapList.containsKey(game)){
			Bukkit.broadcastMessage("return false : pastSchematic");
			return false;
		}
		ArrayList<String> list = OneVsOne.MapList.get(game);
		World w = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
		Location l = new Location(w , 0, 0, 0);
		l.setX(0);
		l.setY(200);
		l.setZ(multiplikator*300);
		for(String str : list){
			String[] array = str.split(";");
			Material material = Material.valueOf(array[0]);
			byte data = Byte.valueOf(array[1]);
			int posX = Integer.parseInt(array[2]);
			int posY = Integer.parseInt(array[3]);
			int posZ = Integer.parseInt(array[4]);
			l.clone().add(posX, posY, posZ).getBlock().setType(material);
			l.clone().add(posX, posY, posZ).getBlock().setData(data);
			//Bukkit.broadcastMessage("return true 2 : pastSchematic");
		}
		Bukkit.getPlayer(game.getPlayer1()).teleport(game.getGameInstanc().loc1);
		Bukkit.getPlayer(game.getPlayer2()).teleport(game.getGameInstanc().loc2);
		return true;
	}
	
	public boolean loadSchematic(String FileName,Games game){
		File file = getFile(FileName);
		if(!file.exists()){
			Bukkit.broadcastMessage("return false : loadSchematic");
			return false;
		}
		
		ArrayList<String> List = getStringListToBlock(FileName);
		if(List != null){
			if(!OneVsOne.MapList.containsKey(game)){
				OneVsOne.MapList.remove(game);
			}
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
		File file = new File(OneVsOne.getInstance().getDataFolder() + "/schematics/" + FileName + ".schem");
		return file;
	}

	public void Setinv(Player p, gameStart gi){
		
	/*	if(OneVsOne.onGame.get(p.getUniqueId()).getkit().equals("Deault-kit")){
			   p.getInventory().clear();
			   p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
			   p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
			   p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
			   p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
			   p.getInventory().setItem(0, new ItemStack(Material.IRON_SWORD));	
			   return;
		} */
		
		 p.getInventory().clear();
		 Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
			
			 if(OneVsOne.onGame.get(p.getUniqueId()).getkit().equals("Deault-kit")){
				 try {
						ResultSet i =  mysql.getResult("SELECT * FROM kits WHERE kitName = 'Deault-kit'");
						if(i.next()){
			                 
						  gi.amor = i.getString("Amor").split(";");
			            }
						
						setInvFinish(p, gi);
					}catch(SQLException e){
						System.err.println(e);
						//gi.amor = null;
					}
				 
			 }else {
			 
			 
		try {
			ResultSet i =  mysql.getResult("SELECT * FROM kits WHERE kitName = '"+ OneVsOne.onGame.get(p.getUniqueId()).getkit() +"' AND UUID = '"+ OneVsOne.onGame.get(p.getUniqueId()).getPlayer2()+"'");
			if(i.next()){
                 
			  gi.amor = i.getString("Amor").split(";");
            }
			
			setInvFinish(p, gi);
		}catch(SQLException e){
			System.err.println(e);
			//gi.amor = null;
		}
		

			 }

          
		
		 });
		 

		
		

	}	
 private void setInvFinish(Player p, gameStart gi){
	 p.getInventory().clear();
	 Inventory inv = p.getInventory();
	 int i = 0;
     for(String str : gi.amor){
    	 
    	  if(gi.amor[i].startsWith("H:")){
    		  String in = gi.amor[i].replaceAll("H:", "");
    		  p.getInventory().setHelmet(CreateIteam(Integer.parseInt(in)));
    	  }else  if(gi.amor[i].startsWith("C:")){
    		  String in = gi.amor[i].replaceAll("C:", "");
    		  p.getInventory().setChestplate(CreateIteam(Integer.parseInt(in)));
    	  }else  if(gi.amor[i].startsWith("L:")){
    		  String in = gi.amor[i].replaceAll("L:", "");
    		  p.getInventory().setLeggings(CreateIteam(Integer.parseInt(in)));
    	  }else  if(gi.amor[i].startsWith("B:")){
    		  String in = gi.amor[i].replaceAll("B:", "");
    		  p.getInventory().setBoots(CreateIteam(Integer.parseInt(in)));
    	  }else {
    		  if(Integer.parseInt(gi.amor[i]) == -1){
    			  
    		  }else{
    			  ItemStack it = CreateIteam(Integer.parseInt(gi.amor[i]));
    			  if(Integer.parseInt(gi.amor[i]) == Material.ARROW.getId() || Integer.parseInt(gi.amor[i]) == Material.SNOW_BALL.getId()){
    				 it.setAmount(16);
    			  }
    		  inv.setItem(i, it);
    		  }
    	  }

   	  i++;
   	  
   	  if(OneVsOne.domeMode.contains(p.getUniqueId())){
   		inv.setItem(4, CreateIteam(Material.STICK, "§aGottes Stab"));
   	  }
   	  
     }
	 
	 
	 
 }
	
    public static boolean del(File dir){
        if (dir.isDirectory()){
                String[] entries = dir.list();
                for (int x=0;x<entries.length;x++){
                    File aktFile = new File(dir.getPath(),entries[x]);
                    del(aktFile);
                }
                if (dir.delete())
                    return true;
                else
                    return false;
            }
            else{
                if (dir.delete())
                    return true;
                else
                    return false;
            }
    }
   
    
	private static ItemStack CreateIteam(Material m, String Name) {

		ItemStack item = new ItemStack(m);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(Name);
		item.setItemMeta(meta);

		return item;
	}
    
	private static ItemStack CreateIteam(int i) {

		//Material item = Material.getMaterial(m);
        //ItemStack is = new ItemStack(item);
		return new ItemStack(i, 1);
	}
}
