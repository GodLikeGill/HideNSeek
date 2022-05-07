package me.jatinsingh.hns;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

public class HnsInfo {
	
	private static boolean HnS;
	private static boolean HnSCountdown;
	
	private static HashMap<String,ArrayList<ItemStack>> ShopItems = new HashMap<String,ArrayList<ItemStack>>();
	private static ArrayList<String> Players = new ArrayList<String>();
	private static ArrayList<String> Hiders = new ArrayList<String>();
	private static ArrayList<String> Spectators = new ArrayList<String>();
	private static ArrayList<String> Seekers = new ArrayList<String>();
	private static ArrayList<String> Voters = new ArrayList<String>();
	
	public static ArrayList<String> getVoters() {
		return Voters;
	}
	public static HashMap<String, ArrayList<ItemStack>> getShopItems() {
		return ShopItems;
	}
	public static boolean isHnS() {
		return HnS;
	}
	public static ArrayList<String> getPlayers() {
		return Players;
	}
	public static ArrayList<String> getHiders() {
		return Hiders;
	}
	public static ArrayList<String> getSpectators() {
		return Spectators;
	}
	public static ArrayList<String> getSeekers() {
		return Seekers;
	}
	public static void setHnS(boolean hnS) {
		HnS = hnS;
	}
	public static boolean isHnSCountdown() {
		return HnSCountdown;
	}
	public static void setHnSCountdown(boolean hnSCountdown) {
		HnSCountdown = hnSCountdown;
	}
}