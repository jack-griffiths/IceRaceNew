 package com.theseasonedgamers.icerace;
 
    import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
 
    public class IceRace extends JavaPlugin {
    	private static GameController gc = new GameController();
    	private boolean automateOn;
    @Override
    public void onEnable() {
    //getCommand("startGame").setExecutor(this);
    registerConfig();
    automateOn = false;
    	
    }
 
    @Override
    public void onDisable() {
 
    }
    
    public void registerConfig() {
    	getConfig().options().copyDefaults(true);
    	saveConfig();
    }
    
    public static GameController getGc() {
    	return gc;
    }
 

    public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
    {
    	
      @SuppressWarnings("unused")
	Player p = (Player)sender;
      if (cmd.getName().equalsIgnoreCase("startGame")) { //attempts to start the game
    	  if(gc.isRunning() == false) {
    		  if(!sender.hasPermission("bc.game")) {
    			  sender.sendMessage(ChatColor.DARK_RED +"Sorry, you are not allowed to start a game!");
                
    			  return false;
    		  } else {
    			  sender.sendMessage(ChatColor.DARK_GREEN +"Attempting to start a game...");
    			  gc.setRunning(true);
    		  }    
    		  	return true;
    	  	} else {
    	  		sender.sendMessage(ChatColor.DARK_RED +"Sorry, a game is already running!");
    	  		
    	  	}
                
            } else if (cmd.getName().equalsIgnoreCase("endGame")) {
            	if(gc.isRunning() == true) {
            		if(!sender.hasPermission("bc.game")) {
            			sender.sendMessage(ChatColor.DARK_RED +"Sorry, you are not allowed to end a game!");
            			return false;
            		} else {
            			sender.sendMessage(ChatColor.DARK_GREEN +"Ending the game...");
            			gc.setRunning(false);
            		}    
            			return true;
                    
            		} else {
            			sender.sendMessage(ChatColor.DARK_RED +"Sorry, a game has not been started!");
            		}
            } else if (cmd.getName().equalsIgnoreCase("players")) { //takes from config
            	if (gc.isRunning() == true) {
            		if(!sender.hasPermission("bc.game")) {
            			sender.sendMessage(ChatColor.DARK_RED +"Sorry, you are not allowed to set the players!");
            			return false;
            		} else {
            			sender.sendMessage(ChatColor.DARK_GREEN +"The number of players has been set to 4");
            			gc.newGame(Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Players"));
            		
            		}
            	} else {
            		sender.sendMessage(ChatColor.DARK_RED +"A game is not currently running!");
            	}
            } else if (cmd.getName().equalsIgnoreCase("joinGame")) {
            	if (gc.isRunning() == true && gc.getPlayersIn() < gc.getPlayerSlots()){	
            		gc.playerJoinGame(sender); // adds the player to the game
            		return true;		
            	} else if (gc.isRunning() == false){
            		sender.sendMessage(ChatColor.DARK_RED +"No game running!");
            		return false;
            	} else {
            		sender.sendMessage("The game is full!");
            	}
            } else if (cmd.getName().equalsIgnoreCase("begin")) {
            	if(!sender.hasPermission("bc.game")) {
            		return false;
            	} else {
        			Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN +"The race is about to start get ready!");
        			gc.startRace();

            	}
            	
            } else if(cmd.getName().equalsIgnoreCase("automate")) {
            	if(!sender.hasPermission("bc.game")) {
            		return false;
            	} else {
            		if(automateOn == false) {
            		Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN + "The game has been set to Automatic!");
            		gc.setRunning(true);
            		gc.newGame(Bukkit.getPluginManager().getPlugin("IceRace").getConfig().getInt("Players"));
            		gc.checkPlayersInGame();
            		Bukkit.getServer().broadcastMessage(ChatColor.AQUA + "The game is about to Start!");
            		while(gc.checkPlayersInGame() == false) {
            			//do nothing
            		}
            		gc.teleToStart();
            		gc.startRace();
            		
            		
            		
            		} else {
            			sender.sendMessage("Game is already being automated.");
            			return false;
            		}
            	}
            } else if (cmd.getName().equalsIgnoreCase("joinLobby")) {
            	if(!gc.getPlayersInLobby().contains(sender)) {
            	gc.joinLobby(sender);
            	} else {
            		sender.sendMessage("You are already in the Lobby!");
            	}
            }
    return false;
    }
}


