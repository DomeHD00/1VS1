package eu.dieytberlegion.Objecks;

import org.bukkit.entity.Player;

public class Team {

	
	Player Owner;

	Player Player1;
	Player Player2;
	
	Player Player3;
	Player Player4;
	
	public Team(Player Owner,Player Player1){
		this.Owner = Owner;
		this.Player1 = Owner;
		this.Player2 = Player1;
	}
	
	public void setOwner(Player p){
		this.Player1 = p;
	}
	public void setPlayer2(Player p){
		this.Player2 = p;
	}
	public void setPlayer3(Player p){
		this.Player3 = p;
	}
	public void setPlayer4(Player p){
		this.Player4 = p;
	}
	
	//geter
	
	public Player getOwner(){
		return this.Owner;
	}
	public Player gettPlayer1(){
		return this.Player1;
	}
	public Player getPlayer2(){
		return this.Player2;
	}
	public Player getPlayer3(){
		return this.Player3;
	}
	public Player getPlayer4(){
		return this.Player4;
	}
	
}
