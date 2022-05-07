package me.jatinsingh.hns.Util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;

public class HidenSeekTimer {
	
	private Main plugin;
	private HidenSeekEnd hnsEnd;
	
	public HidenSeekTimer(Main plugin) {
		this.plugin = plugin;
		hnsEnd = new HidenSeekEnd(this.plugin);
	}
	
	int timer;
	
	public void HnsTimer() {
		
		HnsInfo.setHnS(true);
		
		timer = plugin.getConfig().getInt("Time");
		
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(timer <= 0) {
					Bukkit.broadcast(plugin.prefix + color("&cThe time is Up, and Seekers were unable to find Hiders, therefore &aHiders&c have won."), "hns.playing");
					hnsEnd.HnsEnd("Hiders");
					cancel();
				}
				else {
					
					if(timer == plugin.getConfig().getInt("Time") - plugin.getConfig().getInt("Send_Seeker_after_seconds")) {
						for(String s : HnsInfo.getSeekers()) {
							Player p = (Player) Bukkit.getPlayer(s);
							
							World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps.HideNSeekLobby.world"));
		                    double x = plugin.getConfig().getDouble("Warps.HideNSeekLobby.x");
		                    double y = plugin.getConfig().getDouble("Warps.HideNSeekLobby.y");
		                    double z = plugin.getConfig().getDouble("Warps.HideNSeekLobby.z");
		                    p.teleport(new Location(w, x, y, z));
		                    
		                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, (plugin.getConfig().getInt("Time") - plugin.getConfig().getInt("Send_Seeker_after_seconds"))*20, 1));
		                    
		                    if(HnsInfo.getShopItems().get(p.getName()) != null) {
		                    	for(ItemStack item : HnsInfo.getShopItems().get(p.getName())) {
			                    	p.getInventory().addItem(item);
			                    }
			                    HnsInfo.getShopItems().get(p.getName()).clear();
		                    }
						}
					}
					
					if(HnsInfo.getHiders().size() == 0)
						cancel();
					
					if(timer%60 == 0) {
						Bukkit.broadcast(plugin.prefix + color("&a" + timer/60 + " min(s) left till HideNSeek is over."), "hns.playing");
						for(String s : HnsInfo.getPlayers()) {
							Player p = (Player) Bukkit.getPlayer(s);
							p.setFoodLevel(40);
						}
					}
					
					if(timer == 30) {
						for(String s : HnsInfo.getHiders()) {
							Player p = (Player) Bukkit.getPlayer(s);
							p.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING,30*20,1));
						}
					}
					
					if(timer == 10)
						Bukkit.broadcast(plugin.prefix + color("&a10 second(s) left till HideNSeek is over."), "hns.playing");
					
					if(timer <= 5)
						Bukkit.broadcast(plugin.prefix + color("&a" + timer + " second(s) left!"), "hns.playing");
					
					timer--;
				}
			}
		}.runTaskTimer(plugin, 20, 20);
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}