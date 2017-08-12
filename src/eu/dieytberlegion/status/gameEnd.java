package eu.dieytberlegion.status;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.OneVsOne.OneVsOne;

public class gameEnd {

	public static void end(Player p, Boolean status){
		 Games Game = OneVsOne.onGame.get(p.getUniqueId());


		 

		
		
		if(!status){
			
			 Player taget; 
				if(Game.getPlayer1() == p.getUniqueId()){
					taget = Bukkit.getPlayer(Game.getPlayer1());
				}else {
					taget = Bukkit.getPlayer(Game.getPlayer2());
				}
		}
		
		 Player p1 = Bukkit.getPlayer(Game.getPlayer1());
		 Player p2 = Bukkit.getPlayer(Game.getPlayer2());
		
		 Bukkit.getPlayer(Game.getPlayer1()).sendMessage(OneVsOne.prefix + "§aDer Spieler§e " + p.getName() + " §ahat gewonnen");
		 Bukkit.getPlayer(Game.getPlayer2()).sendMessage(OneVsOne.prefix + "§aDer Spieler§e " + p.getName() + " §ahat gewonnen");
		
		

		p1.setHealth(20);
		p2.setHealth(20);
		onJoin.Joininv(p1);
		onJoin.Joininv(p2);
		Game.getArena().setStatus("free");
		OneVsOne.Games.remove(Game);
		OneVsOne.onGame.remove(p1.getUniqueId());
		OneVsOne.onGame.remove(p2.getUniqueId());
		p1.teleport((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));
		p2.teleport((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));
		
		
		
		ArrayList<Player> spectator = Game.getSpectatPlayer();
		for(Player sp : spectator){
			sp.setAllowFlight(false);
			OneVsOne.spectator.remove(sp.getUniqueId());	
			for(Player all : Bukkit.getOnlinePlayers()){
			  all.showPlayer(sp);	
			}
			sp.setGameMode(GameMode.SURVIVAL);
			sp.sendMessage(OneVsOne.prefix + "§aDer Spieler:§e " + p.getName() + " §ahat Gewonnen");
			onJoin.Joininv(sp);
			sp.teleport((Location) OneVsOne.getInstance().getConfig().get("OneVsOne.world.lobby"));	
		}
		

		Bukkit.getScheduler().runTaskAsynchronously(OneVsOne.getInstance(), () -> {
			try {
				ResultSet pos =  mysql.getResult("SELECT * FROM stats WHERE UUID = '"+p1.getUniqueId()+"'");
				if(pos.next()){
					 int kills =  Game.getponisPlayer1() + pos.getInt("kills");
					 int Deaths = Game.getdponisPlayer1() + pos.getInt("Deaths");
					 int Lost,Win;
					 double kd= 0;
					 if(kills != 0 && Deaths != 0){
					    kd = (double) pos.getInt("kills") / (double) pos.getInt("Deaths");
	    				kd = kd * 100;
	    				kd = Math.round(kd);
	    				kd = kd / 100;
					 
					// mysql.update("update stats set KD = '"+ kd +"' where UUID = '"+ p1.getUniqueId()+"'");
					 }
					 
					 if(p != p1){
						 Lost = pos.getInt("Lost") + 1;
						 Win =  pos.getInt("Win");
					 }else{
						 Lost = pos.getInt("Lost");
						 Win =  pos.getInt("Win") + 1;
					 }
					 
					 
					 p1.sendMessage(OneVsOne.prefix + "Du hast jetzt§e " + kills + " §7Kills");
					 p1.sendMessage(OneVsOne.prefix + "Du hast jetzt§e " + Deaths + " §7Tode");
					 p1.sendMessage(OneVsOne.prefix + "Deine KD ist§e " + pos.getDouble("KD"));
					 
					 mysql.update("update stats set kills = '"+ kills  +"' where UUID = '"+p1.getUniqueId()+"'");
					 mysql.update("update stats set Deaths = '"+ Deaths +"' where UUID = '"+p1.getUniqueId()+"'");
					 mysql.update("update stats set Lost = '"+ Lost +"' where UUID = '"+p1.getUniqueId()+"'");
					 mysql.update("update stats set Win = '"+ Win +"' where UUID = '"+p1.getUniqueId()+"'");

				}	 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			try {
				ResultSet pos =  mysql.getResult("SELECT * FROM stats WHERE UUID = '"+p2.getUniqueId()+"'");
				if(pos.next()){
					 int kills =  Game.getponisPlayer2() + pos.getInt("kills");
					 int Deaths = Game.getdponisPlayer2() + pos.getInt("Deaths");
					 int Lost,Win;
					 double kd =  0;
					 if(kills != 0 && Deaths != 0){
						    kd = (double) pos.getInt("kills") / (double) pos.getInt("Deaths");
		    				kd = kd * 100;
		    				kd = Math.round(kd);
		    				kd = kd / 100;
				//	 mysql.update("update stats set KD = '"+ kd +"' where UUID = '"+p2.getUniqueId()+"'");
					 }
					 if(p != p2){
						 Lost = pos.getInt("Lost") + 1;
						 Win =  pos.getInt("Win");
					 }else{
						 Lost = pos.getInt("Lost");
						 Win =  pos.getInt("Win") + 1;
					 }
					 
					 
					 
					 
					 p2.sendMessage(OneVsOne.prefix + "Du hast jetzt§e " + kills + " §7Kills");
					 p2.sendMessage(OneVsOne.prefix + "Du hast jetzt§e " + Deaths + " §7Tode");
					 p2.sendMessage(OneVsOne.prefix + "Deine KD ist§e " + kd);
					 
					 mysql.update("update stats set kills = '"+ kills  +"' where UUID = '"+p2.getUniqueId()+"'");
					 mysql.update("update stats set Deaths = '"+ Deaths +"' where UUID = '"+p2.getUniqueId()+"'");
					 mysql.update("update stats set Lost = '"+ Lost +"' where UUID = '"+p2.getUniqueId()+"'");
					 mysql.update("update stats set Win = '"+ Win +"' where UUID = '"+p2.getUniqueId()+"'");
					 
					 //
					 mysql.update("DELETE FROM games WHERE Name = '"+ Game.getName() +"'");
					 
				}	 
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
        } );
		
		
	}
}
			