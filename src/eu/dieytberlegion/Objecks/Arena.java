package eu.dieytberlegion.Objecks;

import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import eu.dieytberlegion.utils.Cuboid;

public class Arena {

	String Name;
	String MapName;
	
	int Line;
	Location spawn1;
	Location spawn2;
	Location spctator;
	
	Cuboid Arena;
	Player Player1;
	Player Player2;
	
	String Status = "free";
	
	public Arena(){
	}
	
	public Arena(String Name){
		this.Name = Name;
	}
	public Arena(String Name,int Line){
		this.Name = Name;
		this.Line = Line;
	}
	public Arena(String Name,String MapName,int Line){
		this.MapName = MapName;
		this.Name = Name;
		this.Line = Line;
	}
	public Arena(String Name,String MapName,int Line,Location spawn1,Location spawn2,Location spectator){
		this.MapName = MapName;
		this.Name = Name;
		this.Line = Line;
	    this.spawn1 = spawn1;
	    this.spawn2 = spawn2;
	    this.spctator = spectator;
	}
	public Arena(String Name,int Line,Location spawn1,Location spawn2,Location spectator){
		this.Name = Name;
		this.Line = Line;
	    this.spawn1 = spawn1;
	    this.spawn2 = spawn2;
	    this.spctator = spectator;
	}
	public Arena(String Name,int Line,Location spawn1,Location spawn2,Location spectator,Player Player1,Player Player2){
		this.Name = Name;
		this.Line = Line;
	    this.spawn1 = spawn1;
	    this.spawn2 = spawn2;
	    this.spctator = spectator;
	    this.Player1 = Player1;
	    this.Player2 = Player2;
	}
	//getter
	
	public Location getSpawn1(){
		return this.spawn1;
	}
	public Location getSpawn2(){
		return this.spawn2;
	}
	public Location getSpctator(){
		return this.spctator;
	}
	public Cuboid getArena(){
		return Arena;
	}
	public String getStatus(){
		return Status;
	}
	public String getName(){
		return Name;
	}
	public int getLine(){
		return Line;
	}
	public Player getPlayer1(){
		return Player1;
	}
	public Player getPlayer2(){
		return Player2;
	}
	
	//setter
	public void setStatus(String Status){
		this.Status = Status;
	}
	public void setSpawn1(Location Spawn){
		this.spawn1 = Spawn;
	}
	public void setSpawn2(Location Spawn){
		this.spawn2 = Spawn;
	}
	public void setSpawn3(Location Spawn){
		this.spctator = Spawn;
	}
	public void setLine(int Line){
		this.Line = Line;
	}
}
