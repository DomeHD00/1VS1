package eu.dieytberlegion.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;
import eu.dieytberlegion.nick.Nick;

public class mapfuctions {

@SuppressWarnings("deprecation")
public static void loadMaps(){
		
	
	Bukkit.getScheduler().scheduleAsyncRepeatingTask(OneVsOne.getInstance(), new Runnable(){
		@Override
		public void run() {
			
			for(Player all : Bukkit.getOnlinePlayers()){
				//new InfinityScoreboard(all, ip).update(all, ip);
				
				if (all.hasPermission("1vs1.system.worlds")) {
					all.sendMessage(OneVsOne.prefix + "§aEs werden jetzt neue Welten geladen ...");
				}
			}
			int online; 
			int i = 0;		
			//5 % jeder map werden geladen ....
			if(Bukkit.getOnlinePlayers().size() >= 100)
			{
				online = Bukkit.getOnlinePlayers().size();
			}else{
				online = 100;
			}
			
			int worldCreateNumber = online*5/100;  
			worldCreateNumber = OneVsOne.Arenas.size() - worldCreateNumber;
			if(worldCreateNumber < 0){
				worldCreateNumber = 0;
			}
			
	       	try {
	       		ResultSet pos =  mysql.getResult("SELECT * FROM setup");
				while(pos.next()){
				  Location loc1 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos.getInt("pos1X"), pos.getInt("pos1Y"), pos.getInt("pos1Z"), pos.getFloat("pos1Yaw"), pos.getFloat("pos1Pitch"));
				  Location loc2 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos.getInt("pos2X"), pos.getInt("pos2Y"), pos.getInt("pos2Z"), pos.getFloat("pos2Yaw"), pos.getFloat("pos2Pitch"));
				  Location loc3 = new Location(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), pos.getInt("SposX"), pos.getInt("SposY"), pos.getInt("SposZ"));
					
				  new mapCreateHelp().loadmap(pos.getString("Schematic"), i, loc1, loc2, loc3, worldCreateNumber);
				  i++;
				}
	    		} catch (SQLException e1) {
	    			e1.printStackTrace();
	    		}
		}
	


		
		
	}, 0, 30*60*20); 

	}
	
}
