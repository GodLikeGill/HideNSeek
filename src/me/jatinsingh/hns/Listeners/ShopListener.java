package me.jatinsingh.hns.Listeners;

import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jatinsingh.hns.HnsInfo;
import me.jatinsingh.hns.Main;
import net.milkbowl.vault.economy.Economy;

public class ShopListener implements Listener {
	
	private Main plugin;
	private Economy economy = Main.economy;
	
	public ShopListener(Main plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent e) {
		
		if (e.getClickedInventory() == null)
			return;
		
		if(!e.getView().getTitle().equalsIgnoreCase(color("&4&lHide N Seek Shop")))
			return;
		
		if(e.getCurrentItem().getType() == Material.AIR)
			return;
		
		e.setCancelled(true);
		
		if(e.getCurrentItem().getType().equals(Material.getMaterial(plugin.getConfig().getString("Shop.Fill_Empty_Slot.Item"))))
			return;
		
		Player p = (Player) e.getWhoClicked();
		ItemStack item = e.getCurrentItem();
		
		HnsInfo.getShopItems().putIfAbsent(p.getName(), new ArrayList<ItemStack>());
		
		if(HnsInfo.getShopItems().get(p.getName()).contains(item)) {
			p.sendMessage(plugin.prefix + color("&cYou have already purchased this item."));
			return;
		}
		
		String line1 = item.getItemMeta().getLore().get(0);
		String stringprice = line1.substring(9);
		
		double price = Double.parseDouble(stringprice);
		
		if(economy.has(p, price)) {
			HnsInfo.getShopItems().get(p.getName()).add(item);
			economy.withdrawPlayer(p, price);
		}
		else {
			p.sendMessage(plugin.prefix + color("&cYou don't have enough balance to purchase that."));
			p.closeInventory();
			return;
		}
	}
	
	private String color(String s) {
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
}