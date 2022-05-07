package me.jatinsingh.hns.Util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import me.jatinsingh.hns.Main;

public class LobbyShop {
	
	private Main plugin;
	
	public LobbyShop(Main plugin) {
		this.plugin = plugin;
	}
	
	public void Shop(Player p) {
		
		Inventory inv = Bukkit.getServer().createInventory(null, plugin.getConfig().getInt("Shop.Rows")*9 , color("&4&lHide N Seek Shop"));
		
		ItemStack empty = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Shop.Fill_Empty_Slot.Item")));
		ItemMeta emptyMeta = empty.getItemMeta();
		emptyMeta.setDisplayName(color(plugin.getConfig().getString("Shop.Fill_Empty_Slot.display_name")));
		emptyMeta.setLore(color(plugin.getConfig().getStringList("Shop.Fill_Empty_Slot.lore")));
		empty.setItemMeta(emptyMeta);
		
		for(String key : plugin.getConfig().getConfigurationSection("Shop.Items").getKeys(false)) {
			
			if(plugin.getConfig().getBoolean("Shop.Items." + key + ".isPotion")) {
				ItemStack potion = new ItemStack(Material.POTION, 1);
				PotionMeta potionmeta = (PotionMeta) potion.getItemMeta();
				potionmeta.addCustomEffect(new PotionEffect(PotionEffectType.getByName(plugin.getConfig().getString("Shop.Items." + key + ".PotionType")), plugin.getConfig().getInt("Shop.Items." + key + ".PotionLength"), plugin.getConfig().getInt("Shop.Items." + key + ".PotionStrength")), true);
				potionmeta.setLore(Arrays.asList(color("&aPrice: " + plugin.getConfig().getInt("Shop.Items." + key + ".Price"))));
				potion.setItemMeta(potionmeta);
				
				inv.setItem(plugin.getConfig().getInt("Shop.Items." + key + ".Slot"), potion);
			}
			
			if(plugin.getConfig().getBoolean("Shop.Items." + key + ".isItem")) {
				ItemStack item = new ItemStack(Material.getMaterial(plugin.getConfig().getString("Shop.Items." + key + ".ItemType")),plugin.getConfig().getInt("Shop.Items." + key + ".Amount"));
				ItemMeta itemmeta = item.getItemMeta();
				itemmeta.setLore(Arrays.asList(color("&aPrice: " + plugin.getConfig().getInt("Shop.Items." + key + ".Price"))));
				item.setItemMeta(itemmeta);
				inv.setItem(plugin.getConfig().getInt("Shop.Items." + key + ".Slot"), item);
			}
		}
		
		for (int i = 0; i < inv.getSize(); i++) {
			if(inv.getItem(i) == null) {
				inv.setItem(i, empty);
			}
		}
		p.openInventory(inv);
	}
	
	private String color(String s){
	    return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	private List<String> color(List<String> lore){
	    return lore.stream().map(this::color).collect(Collectors.toList());
	}
}