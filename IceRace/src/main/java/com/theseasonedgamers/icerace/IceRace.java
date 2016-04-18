 package com.theseasonedgamers.icerace;
 
    import org.bukkit.ChatColor;
    import org.bukkit.plugin.java.JavaPlugin;

import org.bukkit.Bukkit;
    import org.bukkit.command.Command;
    import org.bukkit.command.CommandSender;
    import org.bukkit.entity.Player;
 
    public class IceRace extends JavaPlugin {
    	private static GameController gc = new GameController();
 
    @Override
    public void onEnable() {
    //getCommand("startGame").setExecutor(this);
    registerConfig();
    	
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
            } else if (cmd.getName().equalsIgnoreCase("players4")) { //change this to take args
            	if (gc.isRunning() == true) {
            		if(!sender.hasPermission("bc.game")) {
            			sender.sendMessage(ChatColor.DARK_RED +"Sorry, you are not allowed to set the players!");
            			return false;
            		} else {
            			sender.sendMessage(ChatColor.DARK_GREEN +"The number of players has been set to 4");
            			gc.newGame(1);
            		
            		}
            	} else {
            		sender.sendMessage(ChatColor.DARK_RED +"A game is not currently running!");
            	}
            } else if (cmd.getName().equalsIgnoreCase("joinGame")) {
            	if (gc.isRunning() == true && gc.getPlayersIn() < gc.getPlayerSlots()){	
            		gc.playerJoinGame(sender); // adds the player to the game
            		return true;		
            	} else {
            		sender.sendMessage(ChatColor.DARK_RED +"No game running!");
            		return false;
            	}
            } else if (cmd.getName().equalsIgnoreCase("begin")) {
            	if(!sender.hasPermission("bc.game")) {
            		return false;
            	} else {
        			Bukkit.getServer().broadcastMessage(ChatColor.DARK_GREEN +"The race is about to start get ready!");
        			gc.startRace();

            	}
            	
            }
    return false;
    }
}


