package me.jatinsingh.hns.Listeners;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;
import me.jatinsingh.hns.Util.HidenSeekStart;
import me.jatinsingh.hns.Util.LobbyShop;
import me.jatinsingh.hns.Util.PlayerLeave;

public class LobbyListener implements Listener {
	
	private Main plugin;
	private LobbyShop lobbyShop;
	private HidenSeekStart hnsStart;
	private PlayerLeave playerLeave;
	
	public LobbyListener(Main plugin) {
		this.plugin = plugin;
		playerLeave = new PlayerLeave(this.plugin);
		lobbyShop = new LobbyShop(this.plugin);
		hnsStart = new HidenSeekStart(this.plugin);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Player p = (Player) e.getPlayer();
		if(HnsInfo.getPlayers().contains(p.getName()))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void onItemRightClick(PlayerInteractEvent e) {
		
		Player p = (Player) e.getPlayer();
		
		if(e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
			
			if(HnsInfo.getPlayers().contains(p.getName())) {
				
				if(p.getInventory().getItemInMainHand().getType().equals(Material.EMERALD)) {
					lobbyShop.Shop(p);
					return;
				}
				
				if(p.getInventory().getItemInMainHand().getType().equals(Material.DIAMOND)) {
					if(HnsInfo.getPlayers().contains(p.getName())) {
						
						e.setCancelled(true);
						
						if(HnsInfo.getVoters().contains(p.getName())) {
							p.sendMessage(plugin.prefix + color("&cYou have already voted."));
							return;
						}
						HnsInfo.getVoters().add(p.getName());
						p.sendMessage(plugin.prefix + color("&aYou voted for Hide and Seek to be force started."));
						
						if(HnsInfo.getVoters().size() >= plugin.getConfig().getInt("MinimumPlayers_With_Vote")) {
							if(HnsInfo.isHnSCountdown())
								return;
							hnsStart.hnsStart();
						}
						return;
					}
				}
				
				if(p.getInventory().getItemInMainHand().getType().equals(Material.PAPER)) {
					
					e.setCancelled(true);
					playerLeave.PlayerLeaveCommand(p);
				}
				return;
			}
			else
				return;
		}
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}