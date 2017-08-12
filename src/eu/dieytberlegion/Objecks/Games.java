package eu.dieytberlegion.Objecks;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

import eu.dieytberlegion.status.gameStart;

public class Games {
	
	String Name = "default";
	gameStart gi;
	
	UUID Player1;
	UUID Player2;
	ArrayList<Player> spectatPlayer = new ArrayList<Player>();
	int Multiplikator;
	
	int ponisPlayer1 = 0;
	int ponisPlayer2 = 0;
	int dponisPlayer1 = 0;
	int dponisPlayer2 = 0;
	
	String mapName ;
	String status = "Lobby";
	int approximatelyLength;
	int Countdown = 10;
	int round = 0;
	
	
	String kit;
	Arena arean;
	
	public Games(){}
	public Games(String Name){
		this.Name = Name;
	}
	public Games(UUID Player1,UUID Player2){
	    this.Player1 = Player1;
	    this.Player2 = Player2;	
	}
	
	public Games(UUID Player1,UUID Player2,String Name, String Map){
	    this.Name = Name;
	    this.mapName = Map;
	    this.Player1 = Player1;
	    this.Player2 = Player2;	
	}
	
	public Games(UUID Player1,UUID Player2,String Name, String Map, Arena ar){
	    this.Name = Name;
	    this.mapName = Map;
	    this.Player1 = Player1;
	    this.Player2 = Player2;	
	    this.arean = ar;
	}
	
	public Games(UUID Player1,UUID Player2,String Name,int ApproximatelyLength,int Countdown,int countdownWhitStart,String kit){
	    this.Name = Name;
	    this.Player1 = Player1;
	    this.Player2 = Player2;
	    this.approximatelyLength = ApproximatelyLength;
	    this.Countdown = Countdown;
	    this.kit = kit;
	}
	
	public void setRound(int round){
		this.round = round;
	}
	public void setArena(Arena arean){
		this.arean = arean;
	}
	public void setKit(String kit){
		this.kit = kit;
	}
	public void setMultiplikator(int Multiplikator){
		this.Multiplikator = Multiplikator;
	}
	public void setSpectatPlayer(ArrayList<Player> spectatPlayer){
		this.spectatPlayer = spectatPlayer;
	}
	public void setmapName(String mapName){
		this.mapName = mapName;
	}
	public void setponisPlayer1(int ponisPlayer1){
		this.ponisPlayer1 = ponisPlayer1;
	}
	public void setponisPlayer2(int ponisPlayer2){
		this.ponisPlayer2 = ponisPlayer2;
	}
	public void setdponisPlayer1(int dponisPlayer1){
		this.dponisPlayer1 = dponisPlayer1;
	}
	public void setdponisPlayer2(int dponisPlayer2){
		this.dponisPlayer2 = dponisPlayer2;
	}
	public void setStatus(String status){
		this.status = status;
	}
	public void setCountdown(int Countdown){
		this.Countdown = Countdown;
	}
	public void setGameInstanc(gameStart GameInstanc){
		this.gi = GameInstanc;
	}
	public int getCountdown(){
		return Countdown;
	}
	public int getponisPlayer1(){
		return ponisPlayer1;
	}
	public int getponisPlayer2(){
		return ponisPlayer2;
	}
	public int getdponisPlayer1(){
		return dponisPlayer1;
	}
	public int getdponisPlayer2(){
		return dponisPlayer2;
	}
	public UUID getPlayer1(){
		return Player1;
	}
	public UUID getPlayer2(){
		return Player2;
	}
	public String getStatus(){
		return status;
	}
	public String getMapName(){
		return mapName;
	}
	public gameStart getGameInstanc(){
		return gi;
	}
	public String getName(){
		return Name;
	}
	public ArrayList<Player> getSpectatPlayer(){
		return spectatPlayer;
	}
	public int getMultiplikator(){
		return Multiplikator;
	}
	public String getkit(){
		return kit;
	}
	public int getRound(){
		return round;
	}
	public Arena getArena(){
		return arean;
	}
}
