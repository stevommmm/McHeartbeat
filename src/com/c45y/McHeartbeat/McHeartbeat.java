package com.c45y.McHeartbeat;

import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

public class McHeartbeat extends JavaPlugin {
	Logger log = Logger.getLogger("Minecraft");
	DataRunnable d;
	
	public void onEnable() {
		try {
			d = new DataRunnable(this);
			d.start();
			log.info("McHeartbeat enabled.");
		} catch (Exception e) {
			e.printStackTrace();
			this.setEnabled(false);
		}
	}

	/* Used to test lagging out by sleeping the main thread */
//	@Override
//	public boolean onCommand(CommandSender sender, Command command, String name, String[] args) {
//		if (command.getName().equalsIgnoreCase("lag")) {
//			if (sender.hasPermission("McHeartbeat.lag")) {
//				try {
//					TimeUnit.MINUTES.sleep(10);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
//		}
//		return true;
//	}
	
	public void onDisable() {
		d.interrupt();
		log.info("McHeartbeat disabled.");
	}
}
