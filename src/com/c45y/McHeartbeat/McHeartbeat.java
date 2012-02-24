package com.c45y.McHeartbeat;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.entity.Player;

public class McHeartbeat extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");

	public void onEnable(){
		Bukkit.getMessenger().registerOutgoingPluginChannel( this, "McHeartbeat");
            getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                public void run() {
                	Server server = getServer();
                	
                	int count = 0;
				    for(Player serv_players: server.getOnlinePlayers()) {
				        if(serv_players.hasPermission("modtrs.mod")) {
				        	count++;
				        }
				     
				    }
                	
                	String tosend = String.format("{players:%d,staff:%d,ts:%d,}", server.getOnlinePlayers().length,count,System.currentTimeMillis());
                	byte[] bytestosend = tosend.getBytes();
                	for(Player serv_players: server.getOnlinePlayers()) {
                		serv_players.sendPluginMessage( server.getPluginManager().getPlugin("McHeartbeat"), "McHeartbeat", bytestosend );
                	}
                	log.info(tosend);
                }
            }, 1200, 1200);
		log.info("McHeartbeat enabled.");
	}

	public void onDisable(){ 
		getServer().getScheduler().cancelTasks(this);
		log.info("McHeartbeat disabled.");
	}
}
