package me.jatinsingh.hns.Util;

import java.util.Random;

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

public class HidenSeekStart {
	
	private Main plugin;
	private HidenSeekTimer hnsTimer;
	private TagStickItem tagStickItem;
	
	public HidenSeekStart(Main plugin) {
		this.plugin = plugin;
		hnsTimer = new HidenSeekTimer(this.plugin);
		tagStickItem = new TagStickItem(this.plugin);
	}
	
	int seconds;
	
	public void hnsStart() {
		
		if(HnsInfo.isHnSCountdown())
			return;
		
		HnsInfo.setHnSCountdown(true);
		
		seconds = plugin.getConfig().getInt("Auto_Start_Time");
		
		new BukkitRunnable() {

			@Override
			public void run() {
				
				if(seconds >= -1) {
					if(seconds != 0) {
						
						if(HnsInfo.getPlayers().size() < plugin.getConfig().getInt("MinimumPlayers") && HnsInfo.getVoters().size() < plugin.getConfig().getInt("MinimumPlayers_With_Vote")) {
							Bukkit.broadcast(plugin.prefix + color("&cA player has left, causing countdown to stop."), "hns.playing");
							cancel();
							return;
						}
						
						if(seconds%10 == 0)
							Bukkit.broadcast(plugin.prefix + color("&aHide and Seek starting in " + seconds + "."), "hns.playing");
						
						if(seconds<=5)
							Bukkit.broadcast(plugin.prefix + color("&aHide and Seek starting in " + seconds + "."), "hns.playing");
						
						seconds--;
					}
					else {
						
						Random rand = new Random();
				        String seeker = HnsInfo.getPlayers().get(rand.nextInt(HnsInfo.getPlayers().size()));
				        
				        HnsInfo.getSeekers().add(seeker);
				        
				        for(String s1 : HnsInfo.getPlayers()) {
				        	
				        	if(!HnsInfo.getSeekers().contains(s1))
				        		HnsInfo.getHiders().add(s1);
				        	
				        	Player p = (Player) Bukkit.getPlayer(s1);
				        	for(PotionEffect effect : p.getActivePotionEffects()) {
				            	p.removePotionEffect(effect.getType());
				            }
				        }
						
						for(String s2 : HnsInfo.getHiders()) {
							
							Player p = (Player) Bukkit.getPlayer(s2);
							
							World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps.HideNSeekLobby.world"));
		                    double x = plugin.getConfig().getDouble("Warps.HideNSeekLobby.x");
		                    double y = plugin.getConfig().getDouble("Warps.HideNSeekLobby.y");
		                    double z = plugin.getConfig().getDouble("Warps.HideNSeekLobby.z");
		                    p.teleport(new Location(w, x, y, z));
		                    p.getInventory().clear();
		                    
		                    if(HnsInfo.getShopItems().get(p.getName()) != null) {
		                    	for(ItemStack item : HnsInfo.getShopItems().get(p.getName())) {
			                    	p.getInventory().addItem(item);
			                    }
			                    HnsInfo.getShopItems().get(p.getName()).clear();
		                    }
						}
						
						for(String s3 : HnsInfo.getSeekers()) {
							Player p = (Player) Bukkit.getPlayer(s3);
							p.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, plugin.getConfig().getInt("Send_Seeker_after_seconds")*20, 2));
							p.getInventory().clear();
							p.getInventory().addItem(tagStickItem.TagStick());
						}
						
						Bukkit.broadcast(plugin.prefix + color("&aHide N Seek event has started, you have " + plugin.getConfig().getInt("Send_Seeker_after_seconds") + " second(s) to hide, Seeker is blinded."), "hns.playing");
						
						cancel();
						seconds--;
						hnsTimer.HnsTimer();
						return;
					}
				}
				else {
					cancel();
					return;
				}
			}
			
		}.runTaskTimer(plugin, 20, 20);
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}