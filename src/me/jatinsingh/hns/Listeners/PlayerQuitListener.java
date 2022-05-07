package me.jatinsingh.hns.Listeners;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.permissions.PermissionAttachment;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;
import me.jatinsingh.hns.Util.HidenSeekEnd;

public class PlayerQuitListener implements Listener {
	
	private Main plugin;
	
	private HidenSeekEnd hnsEnd;
	
	public PlayerQuitListener(Main plugin) {
		this.plugin = plugin;
		hnsEnd = new HidenSeekEnd(this.plugin);
	}
	
	HashMap<UUID, PermissionAttachment> perms = new HashMap<UUID, PermissionAttachment>();
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		String name = e.getPlayer().getName();
		
		Player p = e.getPlayer();
		PermissionAttachment attachment = p.addAttachment(plugin);
		perms.put(p.getUniqueId(), attachment);
		perms.get(p.getUniqueId()).unsetPermission("hns.playing");
		
		PlayerQuit(name);
	}
	
	public void PlayerQuit(String name) {
		if(HnsInfo.getPlayers().contains(name)) {
			HnsInfo.getPlayers().remove(name);
			
			if(HnsInfo.getHiders().contains(name)) {
				HnsInfo.getHiders().remove(name);
				Bukkit.broadcast(plugin.prefix + color("&c1 &7hider has left the game, only &c" + HnsInfo.getHiders().size() + " &7hiders are left!"), "hns.playing");
				if(HnsInfo.getHiders().size() == 0) {
					hnsEnd.HnsEnd("Seekers");
					return;
				}
			}
			if(HnsInfo.getSeekers().contains(name)) {
				HnsInfo.getSeekers().remove(name);
				Bukkit.broadcast(plugin.prefix + color("&7The Seeker has disconnected from the game, Therefore &cHiders win!"), "hns.playing");
				hnsEnd.HnsEnd("Hiders");
			}
		}
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}