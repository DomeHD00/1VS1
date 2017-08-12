package eu.dieytberlegion.MYSQL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import eu.dieytberlegion.OneVsOne.OneVsOne;

public class mysql {

	public static String host;
	public static String port;
	public static String username;
	public static String password;
	public static String database;
	public static Connection con;
	
	public static void connect(String prefix){
		if(!isConnect()){
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database,username,password);
				System.out.println(prefix +"MYSQL Verbunden Augebaut!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
		}
	}
	
	public static void disconnect(){
		if(!isConnect()){
			try {
				con.close();
				System.out.println("[1VS1] MYSQL Verbunden geschlossen!");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isConnect(){
		return (con == null ? false:true);
	}
	
	public static void update(String qry){
		try {
			PreparedStatement ps = con.prepareStatement(qry);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public static ResultSet getResult(String qry){
		try {
			PreparedStatement ps = con.prepareStatement(qry);
			return ps.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
