package eu.dieytberlegion.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.entity.LivingEntity;

import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class updateTopPlayer {
	

	public static ArrayList<String> names = new ArrayList<String>();
	public static ArrayList<Integer> kills = new ArrayList<Integer>();
	@SuppressWarnings("deprecation")
	public static void update(){

		Bukkit.getScheduler().scheduleSyncRepeatingTask(OneVsOne.getInstance(), new Runnable(){
			@Override
			public void run() {
				for(LivingEntity v : OneVsOne.Top){
					v.remove();
					OneVsOne.Top.remove(v);
				}
				
				ResultSet pos =  mysql.getResult("SELECT * FROM stats ORDER BY kills DESC LIMIT 10");
				try {
					while(pos.next()){
					
						names.add(pos.getString("Name"));
						kills.add(pos.getInt("kills"));

		
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
				
				finish();
			}
		}, 0, 30*60*20); 
	

	
	
	}
	@SuppressWarnings({ "deprecation" })
	private static void finish() {
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player1") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player1");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(0));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(0));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player2") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player2");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(1));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(1));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player3") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player3");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(2));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(2));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player4") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player4");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(3));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(3));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player5") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player5");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(4));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(4));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player6") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player6");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(5));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(5));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player7") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player7");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(6));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(6));;
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player8") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player8");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(7));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(7));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player9") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player9");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(8));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(8));
		}
		if((Location) OneVsOne.getInstance().getConfig().get("Top.Player10") != topPlayerFile.defaultLocation){
			Location l = (Location) OneVsOne.getInstance().getConfig().get("Top.Player10");
			Sign s = (Sign) l.getBlock();
			
		    s.setLine(0, "§c[Top]");
			s.setLine(1, "§a" + names.get(9));
			s.setLine(2, "");
			s.setLine(3, "§7Kills:§e " + kills.get(9));
		}
	}

}
