package eu.dieytberlegion.OneVsOne;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;

import org.bukkit.WorldCreator;
import org.bukkit.WorldType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.plugin.java.JavaPlugin;

import eu.dieytberlegion.Commands.admin.chatRead;
import eu.dieytberlegion.Commands.admin.createArena;
import eu.dieytberlegion.Commands.admin.deaultkitSave;
import eu.dieytberlegion.Commands.admin.domeMode;
import eu.dieytberlegion.Commands.admin.loadSchematic;
import eu.dieytberlegion.Commands.admin.schematicFinish;
import eu.dieytberlegion.Commands.admin.setArena;
import eu.dieytberlegion.Commands.admin.setLocation;
import eu.dieytberlegion.Commands.admin.worldTp;
import eu.dieytberlegion.Commands.moderation.statsRemove;
import eu.dieytberlegion.Commands.user.kitDelete;
import eu.dieytberlegion.Commands.user.kitSave;
import eu.dieytberlegion.Commands.user.kitcanneld;
import eu.dieytberlegion.Commands.user.spectateEnd;
import eu.dieytberlegion.Commands.user.stats;
import eu.dieytberlegion.Events.AsyncPlayerPreLogin;
import eu.dieytberlegion.Events.EntityDamageByEntity;
import eu.dieytberlegion.Events.InventoryClick;
import eu.dieytberlegion.Events.PlayerChat;
import eu.dieytberlegion.Events.PlayerDeath;
import eu.dieytberlegion.Events.PlayerDropItem;
import eu.dieytberlegion.Events.PlayerGameModeChange;
import eu.dieytberlegion.Events.PlayerInteract;
import eu.dieytberlegion.Events.PlayerInteractEntity;
import eu.dieytberlegion.Events.PlayerRespawn;
import eu.dieytberlegion.Events.ProjectilHit;
import eu.dieytberlegion.Events.WeatherChange;
import eu.dieytberlegion.Events.onBlock;
import eu.dieytberlegion.Events.onJoin;
import eu.dieytberlegion.Events.onMove;
import eu.dieytberlegion.Events.onQuit;
import eu.dieytberlegion.MYSQL.mysql;
import eu.dieytberlegion.MYSQL.mysqlFile;
import eu.dieytberlegion.Objecks.Games;
import eu.dieytberlegion.Objecks.Team;
import eu.dieytberlegion.configs.config;
import eu.dieytberlegion.nick.Nick;
import eu.dieytberlegion.status.gameStart;
import eu.dieytberlegion.utils.Cuboid;
import eu.dieytberlegion.utils.SchematicsMenager;
import eu.dieytberlegion.utils.mapCreateHelp;
import eu.dieytberlegion.utils.mapfuctions;
import eu.dieytberlegion.utils.topPlayerFile;
import eu.dieytberlegion.utils.updateTopPlayer;
import net.minecraft.server.v1_8_R3.GameRules;
import net.minecraft.server.v1_8_R3.GameRules.EnumGameRuleType;


public class OneVsOne extends JavaPlugin {
	//-------------Hashmaps
	public static HashMap<UUID, UUID> challenge = new HashMap<>();
	public static HashMap<UUID, Games> onGame = new HashMap<>();
	public static HashMap<UUID, Boolean> noChallenge = new HashMap<>();
	public static HashMap<Games, ArrayList<String>> MapList = new HashMap<>();
	public static HashMap<UUID, String> kit = new HashMap<>();
	public static HashMap<UUID, String> map = new HashMap<>();
	public static HashMap<UUID, Integer> round = new HashMap<>();
	public static HashMap<Player, Team> Teams = new HashMap<>();
	public static HashMap<Player, Player> challengeTeam = new HashMap<>();
	public static ArrayList<UUID> kitmenu = new ArrayList<UUID>();
	public static ArrayList<UUID> fresplayer = new ArrayList<UUID>();
	public static ArrayList<UUID> spectator = new ArrayList<UUID>();
	public static ArrayList<UUID> queue = new ArrayList<UUID>();
	public static ArrayList<Player> Builder = new ArrayList<Player>();
	public static ArrayList<Player> read = new ArrayList<Player>();
	public static ArrayList<UUID> domeMode = new ArrayList<UUID>();
	public static ArrayList<LivingEntity> Top = new ArrayList<LivingEntity>();
	public static ArrayList<Games> Games = new ArrayList<Games>();
	public static ArrayList<eu.dieytberlegion.Objecks.Arena> LoadMaps = new ArrayList<eu.dieytberlegion.Objecks.Arena>();
	public static ArrayList<Player> vipLobby = new ArrayList<Player>();
	public static ArrayList<Player> youtubeLobby = new ArrayList<Player>();
	public static ArrayList<Player> teamLobby = new ArrayList<Player>();
	public static ArrayList<Player> afkLobby = new ArrayList<Player>();	
	public static int Arena = 1;
	public static HashMap<String, Integer> Arenaz = new HashMap<>();
	public static HashMap<String, Integer> Line = new HashMap<>();
	public static HashMap<String, ArrayList<eu.dieytberlegion.Objecks.Arena>> Arenas = new HashMap<>();
	//--------------
	public static topPlayerFile topPlayerFile = new topPlayerFile();
	private static SchematicsMenager sm;
	public static gameStart gs;
	private static OneVsOne instance;
	public static String prefix = "§b1vs1 §8●§7 ";
	public static String nopem = prefix + "§cDu hast Keine Permission dafür!";
	
	public void onEnable(){
		 super.onEnable();
		 /*for(Player all : Bukkit.getOnlinePlayers()){
			 all.kickPlayer("lobby");
		 }*/
		 
		 instance = this;
		 sm = new SchematicsMenager(this);
		 gs = new gameStart();
		 config.setConfig();
		 Mysql();
		 loadWorld();
		 registerCommands();
		 registerEvents();
		 
		 /*
		 topPlayerFile file = new topPlayerFile();
		 file.setStandert();
		 updateTopPlayer.update();
		 */
		 loadStartMap();
	
		 mapfuctions.loadMaps();
		 
	}
	public void onDisable(){
		 super.onDisable();
		 File map = Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")).getWorldFolder();
		 Bukkit.unloadWorld(Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")), true);
		 gameStart.del(map);
		/*	for(LivingEntity v : OneVsOne.Top){
				v.remove();
				OneVsOne.Top.remove(v);
			}
		 }*/
	}
	
	private void Mysql(){
		//mysql connect
		mysqlFile file = new mysqlFile();
		file.setStandert();
		file.readData();
		mysql.connect(prefix);
		
		//mysql create databasesTeam
		mysql.update("CREATE TABLE IF NOT EXISTS kits(UUID VARCHAR(100),playerName VARCHAR(16),KitName VARCHAR(20),Amor TEXT(1000000))");
		mysql.update("CREATE TABLE IF NOT EXISTS setup(Schematic VARCHAR(20),Name VARCHAR(20),pos1X INT(10),pos1Y INT(10),pos1Z INT(10),pos1Yaw FLOAT(10),pos1Pitch FLOAT(10),pos2X INT(10),pos2Y INT(10),pos2Z INT(10),pos2Yaw FLOAT(10),pos2Pitch FLOAT(10),SposX INT(10),SposY INT(10),SposZ INT(10))");
		mysql.update("CREATE TABLE IF NOT EXISTS games(Name VARCHAR(36),Map VARCHAR(30),Multiplikator INT(5),playerName VARCHAR(16))");
		mysql.update("CREATE TABLE IF NOT EXISTS stats(UUID VARCHAR(100),Name VARCHAR(16),kills INT(100),Deaths INT(100),KD INT(100),Games INT(100),Win INT(100),Lost INT(100))");
	}

	private void registerEvents() {
		new onJoin(this);
		new EntityDamageByEntity(this);
		//new PlayerInteractEntity(this);
		new onQuit(this);
		new onMove(this);
		new PlayerDeath(this);
		new PlayerRespawn(this);
		new PlayerGameModeChange(this);
		new PlayerInteract(this);
		new AsyncPlayerPreLogin(this);
		//new PlayerChat(this);
		new InventoryClick(this);
		new onBlock(this);
		new PlayerDropItem(this);
		new WeatherChange(this);
		new ProjectilHit(this);
		//new SignChange(this);
	}

	private void registerCommands() {
		  worldTp exe = new worldTp(this);
		  getCommand("worldtp").setExecutor(exe);
		  
		  setLocation exe1 = new setLocation(this);
		  getCommand("setLoc").setExecutor(exe1);
		  
		  setArena exe2 = new setArena(this);
		  getCommand("schematic").setExecutor(exe2);
		  
		  createArena exe4 = new createArena(this);
		  getCommand("createArena").setExecutor(exe4);
		  
		  eu.dieytberlegion.Commands.admin.loadWorld exe5 = new eu.dieytberlegion.Commands.admin.loadWorld(this);
		  getCommand("loadworld").setExecutor(exe5);
		  
		  loadSchematic exe6 = new loadSchematic(this);
		  getCommand("loadschematic").setExecutor(exe6);
		  
		  schematicFinish exe7 = new schematicFinish(this);
		  getCommand("schematicfinish").setExecutor(exe7);
		  
		  eu.dieytberlegion.Commands.admin.Builder exe11 = new eu.dieytberlegion.Commands.admin.Builder(this);
		  getCommand("builder").setExecutor(exe11);
		  
		  chatRead exe14 = new chatRead(this);
		  getCommand("chatread").setExecutor(exe14);
		  
		  statsRemove exe15 = new statsRemove(this);
		  getCommand("statsremove").setExecutor(exe15);
		  
		  chatRead exe16 = new chatRead(this);
		  getCommand("chatread").setExecutor(exe16);
		  
		  //this.getCommand("nick").setExecutor(new nick());
		  
		  domeMode exe18 = new domeMode(this);
		  getCommand("domemode").setExecutor(exe18);
		  
		  kitcanneld exe19 = new kitcanneld(this);
		  getCommand("kitstop").setExecutor(exe19);
		  
		  deaultkitSave exe20 = new deaultkitSave(this);
		  getCommand("deaultkitSave").setExecutor(exe20);

		  eu.dieytberlegion.Commands.user.spectator exe21 = new eu.dieytberlegion.Commands.user.spectator(this);
		  getCommand("spectate").setExecutor(exe21);

		  
		  spectateEnd exe9 = new spectateEnd(this);
		  getCommand("spectateend").setExecutor(exe9);
		  
		  kitSave exe10 = new kitSave(this);
		  getCommand("kitsave").setExecutor(exe10);
		  
		  kitDelete exe12 = new kitDelete(this);
		  getCommand("kitdelete").setExecutor(exe12);
		  
		  stats exe13 = new stats(this);
		  getCommand("stats").setExecutor(exe13);

	}
	private void loadWorld() {
		WorldCreator creator = new WorldCreator(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1"));
		creator.type(WorldType.FLAT).createWorld();
		
		Bukkit.getWorld("world").setDifficulty(Difficulty.PEACEFUL);
		Bukkit.getWorld("world").setMonsterSpawnLimit(0);
		Bukkit.getWorld("world").setThunderDuration(0);
		Bukkit.getWorld("world").setWeatherDuration(0);
		Bukkit.getWorld("world").setAnimalSpawnLimit(0);
		
		Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")).setDifficulty(Difficulty.NORMAL);
		Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")).setThunderDuration(0);
		Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")).setMonsterSpawnLimit(0);
		Bukkit.getWorld(OneVsOne.getInstance().getConfig().getString("OneVsOne.world.name1")).setAnimalSpawnLimit(0);
		
	}
	
	private void loadStartMap(){
		
		//5 % jeder map werden geladen ....
		int online; 
		int i = 0;		
				
		if(Bukkit.getOnlinePlayers().size() >= 100)
		{
			online = Bukkit.getOnlinePlayers().size();
		}else{
			online = 100;
		}
		
		int worldCreateNumber = online*5/100;  
		
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
	
	
	public static OneVsOne getInstance() {
	     return instance;
	}
	public static SchematicsMenager getSchematicsMenager() {
	     return sm;
	}
	public static gameStart getGameStart() {
	     return gs;
	}
	
	
	public static void selectPosOne(Player p,Location l){
		if(p.hasMetadata("editPosOne")){
			p.removeMetadata("editPosOne", OneVsOne.getInstance());
		}
		p.setMetadata("editPosOne", new FixedMetadataValue(OneVsOne.getInstance(), l));
		p.sendMessage(prefix + "§aPosition Wurde gespeichert (1). X:" + l.getX() + " Y:" + l.getBlockY() + "Z: " + l.getBlockY() );
		
	}
	
	public static void selectPosTwo(Player p,Location l){
		if(p.hasMetadata("editPosTwo")){
			p.removeMetadata("editPosTwo", OneVsOne.getInstance());
		}
		p.setMetadata("editPosTwo", new FixedMetadataValue(OneVsOne.getInstance(), l));
		p.sendMessage(prefix + "§aPosition Wurde gespeichert (2). X:"  + l.getX() + " Y:" + l.getBlockY() + " Z:" + l.getBlockY());
		
	}
	

}
