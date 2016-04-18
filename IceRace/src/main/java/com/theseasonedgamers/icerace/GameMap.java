package com.theseasonedgamers.icerace;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class GameMap {
	
	private static boolean isBuilt;
	
	public GameMap() {
		isBuilt = false;
	}
	
	public static boolean build() {
		Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN +"The game map is being built!");
		IceRace.getGc().resetBarriers();
		isBuilt = true;
		return isBuilt;		 
		
		
		
		
		
	}

}