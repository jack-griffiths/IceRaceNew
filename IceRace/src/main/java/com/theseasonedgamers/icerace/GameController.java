package com.theseasonedgamers.icerace;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GameController {
	private boolean isRunning = false;
	private int numPlayers; //slots for players
	private int numPlayersIn; //players in the game
	private Player[] playerList; //list of the players in the game
	private ArrayList<Player> playersInLobby;
    static int interval = 10;//for timer
    private static Timer timer;
	
	public GameController() {
		numPlayers = 0;
		playersInLobby = new ArrayList<Player>();
		
	}
	


	public void newGame(int players) {
		
		this.numPlayers = players;
		Boolean b = GameMap.build();
		
		if(b == true) {
			Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN +"The game was successfully built.");
			
		} else {
			Bukkit.getServer().broadcastMessage(ChatColor.DARK_RED +"The game map build failed!");

		}
		
	}
	
	@SuppressWarnings("deprecation")
	public void dropBarriers() { //drops the barriers on the map to allow the players to go
		List<World> worlds = Bukkit.getWorlds(); //gets the world list
		World w = worlds.get(0); //gets the world 

		int x = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line x");
		int y = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line y");
		int z = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line z");
		
		Location location = new Location (w, x, y, z);

		location.getBlock().setTypeId(0); //sets the barrier to 0 (air)	
		
	}
	
	@SuppressWarnings("deprecation")
	public void resetBarriers() {
		List<World> worlds = Bukkit.getWorlds(); //gets the world list
		World w = worlds.get(0); //gets the world 
		int x = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line x");
		int y = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line y");
		int z = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line z");
		
		Location location = new Location (w, x, y, z);
		location.getBlock().setTypeId(Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Line Block ID"));// re-creates the block
	}
	
	public void countdown() { //fix ????????!!!!!!!!
            	Bukkit.getServer().broadcastMessage(ChatColor.YELLOW +"Place and get into your boats!");
            	Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN + "The game is about to start!");
            	
            	int delay = 1000;
                int period = 1000;
                timer = new Timer();
 
                timer.scheduleAtFixedRate(new TimerTask() {

                    public void run() {
                        Bukkit.getServer().broadcastMessage((setInterval()));

                    }
                }, delay, period);
                
                Bukkit.getServer().broadcastMessage("GO!");
                
            }
	
	private static final String setInterval() { //for count down
	    if (interval == 1)
	        timer.cancel();
	    return Integer.toString(--interval);
	}
	
	public void startRace() { //ran to start the race
		countdown();
		dropBarriers();
	}
	
	public void joinLobby(CommandSender sender) {
		Player player = (Player) sender;
		playersInLobby.add(player);
		
	}

	public void playerJoinGame(CommandSender sender) {
		Player player = (Player) sender;//converts sender to player

		if(playersInLobby.contains(player)) {
		playerList = new Player[numPlayers]; //makes the array with number of players
		
		int openArray = 0;

		for(int i = 0; i<playerList.length; i++) { //finds the next empty place in the list
		    if(playerList[i] == null)
		    {
		        openArray = i;
		        break;
		    }
		}
		
		Bukkit.getServer().broadcastMessage(ChatColor.AQUA +"" + sender + " has been added to the game! " + playersLeft() + " slots left!");
		numPlayersIn++;
		
		playerList[openArray] = player;
		
		
		List<World> worlds = Bukkit.getWorlds(); //gets the world list
		World w = worlds.get(0); //gets the world 
		int x = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Area x");
		int y = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Area y");
		int z = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start Area z");
		
		Location location = new Location(w, x, y, z); //sets the location
		player.teleport(location); //Teleport the player to the start or lobby
		
		ItemStack boat = new ItemStack(Material.BOAT);
		player.getInventory().addItem(boat); //gives the player a boat
		player.sendMessage("You have been given a Boat!");
		} else {
			player.sendMessage("You need to join the lobby first! /joinLobby");
		}
	}
		
	
	
	public void teleToStart() { //teles players to start line
		int x = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start x");
		int y = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start y");
		int z = Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Start z");
		Location location = new Location(Bukkit.getWorlds().get(0), x, y, z);
		
		int i = 0;
		
		while (playerList.length < i)
			playerList[i].teleport(location);
		}
	
	public boolean checkPlayersInGame() { //returns true if the amount of players = the amount of slots
		while(numPlayers != numPlayersIn) {
			return false;
		}
		return true;
	}

	public boolean isRunning() {
		return isRunning;
	}

	public void setRunning(boolean isRunning) {
		this.isRunning = isRunning;
	}
	
	public int getPlayerSlots() {
		return numPlayers;
	}
	
	public int getPlayersIn() {
		return numPlayersIn;
	}

	public CommandSender[] getPlayerList() {
		return playerList;
	}
	
	public int playersLeft() {
		return numPlayers - numPlayersIn;
	}



	public ArrayList<Player> getPlayersInLobby() {
		// TODO Auto-generated method stub
		return playersInLobby;
	}
	
}
