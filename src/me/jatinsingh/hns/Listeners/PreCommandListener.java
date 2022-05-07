package me.jatinsingh.hns.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;

public class PreCommandListener implements Listener {
	
	private Main plugin;
	
	public PreCommandListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onPreCommand(PlayerCommandPreprocessEvent e) {
		
		Player p = e.getPlayer();
		
		if(HnsInfo.getPlayers().contains(p.getName())) {
			if(!e.getMessage().equals("/hns quit")) {
				p.sendMessage(plugin.prefix + color("You cannot use this command while playing HideNSeek, /hns quit to quit and use command."));
			}
		}
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}