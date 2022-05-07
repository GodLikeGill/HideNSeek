package me.jatinsingh.hns.Listeners;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;
import me.jatinsingh.hns.Util.HidenSeekEnd;
import me.jatinsingh.hns.Util.TagStickItem;

public class TagStickListener implements Listener {
	
	private Main plugin;
	private TagStickItem tagStickItem;
	private HidenSeekEnd hnsEnd;
	
	public TagStickListener(Main plugin) {
		this.plugin = plugin;
		hnsEnd = new HidenSeekEnd(this.plugin);
		tagStickItem = new TagStickItem(this.plugin);
	}
	
	@EventHandler
	public void onTagging(EntityDamageByEntityEvent e) {
		
		if(e.getEntity() instanceof Player) {
			if(e.getDamager() instanceof Player) {
				
				Player damaged = (Player) e.getEntity();
				Player damager = (Player) e.getDamager();
				
				if(tagStickItem.isTagStick(damager.getInventory().getItemInMainHand())) {
					if(HnsInfo.getSeekers().contains(damager.getName()) && HnsInfo.getHiders().contains(damaged.getName())) {
						
						World w = Bukkit.getServer().getWorld(plugin.getConfig().getString("Warps.SpectatorLobby.world"));
	                    double x = plugin.getConfig().getDouble("Warps.SpectatorLobby.x");
	                    double y = plugin.getConfig().getDouble("Warps.SpectatorLobby.y");
	                    double z = plugin.getConfig().getDouble("Warps.SpectatorLobby.z");
	                    damaged.teleport(new Location(w, x, y, z));
						
	                    HnsInfo.getHiders().remove(damaged.getName());
	                    HnsInfo.getSpectators().add(damaged.getName());
	                    
						Bukkit.broadcast(plugin.prefix + color("&c" + damager.getName() + " &7has tagged &c" + damaged.getName() + "&7, &c" + HnsInfo.getHiders().size() + " &7hiders are left."), "hns.playing");
						
						if(HnsInfo.getHiders().size() == 0) {
							Bukkit.broadcast(plugin.prefix + color("&a&lThe Seeker(s) have won the game."), "hns.playing");
							hnsEnd.HnsEnd("Seekers");
						}
					}
				}
			}
		}
	}
	
	private String color(String s){
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}