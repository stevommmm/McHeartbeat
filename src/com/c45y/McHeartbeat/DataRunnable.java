package com.c45y.McHeartbeat;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.bukkit.Server;
import org.bukkit.World;

class DataRunnable extends Thread
{
	McHeartbeat p = null;
	String bukhost;
	int bukport;
	int count;
	
	DataRunnable(McHeartbeat p) {
		this.p = p;
		this.bukhost = p.getServer().getIp();
		this.bukport = p.getServer().getPort();
		this.count = 0;
	}
	
	public void run ()
	{
		try {
			while(true) {
				if (this.count != 0) {
					p.log.info("Heartbeat failed ping count: " + this.count);
				}
				if(this.count >= 7) {
					p.log.warning("We lost pings from the server, saving worlds then stopping.");
					Server s = p.getServer();
					s.savePlayers();
					for(World w : s.getWorlds()) {
						w.save();
					}
					s.shutdown();
					break;
					//s.dispatchCommand(s.getConsoleSender(), "save-all");
		            //s.dispatchCommand(s.getConsoleSender(), "stop");
				}
				TimeUnit.MINUTES.sleep(1);
				ping();
			}
		} catch(InterruptedException e) {
			
		}
	}
	
	private void ping() {
		Socket var3 = null;
        DataInputStream var4 = null;
        DataOutputStream var5 = null;
		try {
			var3 = new Socket(bukhost, bukport);
			var3.setSoTimeout(3000);
			var3.setTcpNoDelay(true);
			var3.setTrafficClass(18);
			var4 = new DataInputStream(var3.getInputStream());
			var5 = new DataOutputStream(var3.getOutputStream());
			var5.write(254);

			if (var4.read() != 255) {
				throw new IOException("Bad message");
			}
			String var6 = this.readString(var4, 256);
			if(var6 == null) {
				this.count++;
			} else {
				this.count = 0;
			}
		} catch(Exception e) {
			this.count++;
		} finally {
			try {
				if (var4 != null) {
					var4.close();
				}
				if (var5 != null) {
					var5.close();
				}
				if (var3 != null) {
					var3.close();
				}
			}
			catch (Exception e) {
				
			}
		}
	}
	
	public String readString(DataInputStream par0DataInputStream, int par1) throws IOException
    {
        short var2 = par0DataInputStream.readShort();
        StringBuilder var3 = new StringBuilder();
        for (int var4 = 0; var4 < var2; ++var4) {
            var3.append(par0DataInputStream.readChar());
        }
        return var3.toString();
    }
}