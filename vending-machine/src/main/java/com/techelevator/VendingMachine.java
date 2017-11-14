package com.techelevator;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class VendingMachine { 
	
	private Map<String, Item> inventory = new HashMap<String, Item>();
	private List<Item> soldList = new ArrayList<Item>();
	private double balance = 0.00;
	private double previousBalance = 0.00;
	
	public VendingMachine() {
		File menuFile = null;
		
		try {
			menuFile = getFileFromPath("vendingmachine.csv");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		try (Scanner fileInput = new Scanner(menuFile)) {
			while (fileInput.hasNextLine()) {
				String line = fileInput.nextLine();
				String[] parts = line.split("\\|");
				
				Item item = new Item();
				item.setSlot(parts[0]);
				item.setItemName(parts[1]);
				item.setItemPrice(Double.parseDouble(parts[2]));
				
				inventory.put(item.getSlot(), item);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public Map<String, Item> getInventory() {
		return inventory;
	}
	
	public double getBalance() {
		return balance;
	}
	
	public void addPaymentToBalance(double fed) {
		previousBalance = balance;
		balance += fed;
		log();
	}
	
	public void subtractPurchaseFromBalance(Item item) {
		previousBalance = balance;
		balance -= item.getItemPrice();
		log();
	}
	
	public void decrementQuantity(Item item) {
		item.setQuantity(item.getQuantity() - 1);
		soldList.add(item);
		log(item);
	}
	
	public void makeChange() {  
		int quarters = 0;
		int dimes = 0;
		int nickels = 0;
		double endingBalance = balance;

		while (balance > 0) {
			if (balance >= 0.25) {
				quarters++;
				balance -= 0.25;
			} else if (Math.round(balance * 100.00) / 100.00 >= 0.10) {
				dimes++;
				balance -= 0.10;
			} else if (Math.round(balance * 100.00) / 100.00 >= 0.05) {
				nickels++;
				balance -= 0.05;
			} else {
				break;
			}
		}
		balance = Math.round(balance * 100.00) / 100.00;
		
		System.out.println("\nYour change is:");
		System.out.println(quarters + " Quarters,");
		System.out.println(dimes + " Dimes,");
		System.out.println(nickels + " Nickels,");
		System.out.println(String.format("$%#.2f total change.", endingBalance));
		
		try (PrintWriter fileOutput = new PrintWriter(new FileOutputStream(new File("log.txt"), true))) {
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
			fileOutput.println(String.format("%-25s%-24s%#.2f   %#.2f", formatter.format(time), "MAKE CHANGE:", endingBalance, balance) + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void consume() { 
		for (Item item : soldList) {
			System.out.println();
			String slot = item.getSlot();
			if (slot.contains("A")) {
				System.out.println("Crunch Crunch, Yum!");
			} else if (slot.contains("B")) {
				System.out.println("Munch Munch, Yum!");
			} else if (slot.contains("C")) {
				System.out.println("Glug Glug, Yum!");
			} else if (slot.contains("D")) {
				System.out.println("Chew Chew, Yum!");
			}
		}
	}
	
	private void log() {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("log.txt"), true))) {
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a");
			pw.println(String.format("%-25s%-24s%#.2f   %#.2f", formatter.format(time), "FEED MONEY:", previousBalance, balance) + "\n");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void log(Item item) {
		try (PrintWriter pw = new PrintWriter(new FileOutputStream(new File("log.txt"), true))) {
			LocalDateTime time = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm:ss a"); 
			pw.println(String.format("%-25s%-4s%-20s%#.2f   %#.2f", formatter.format(time), item.getSlot(), 
					item.getItemName(), previousBalance, balance) + "\n");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	private File getFileFromPath(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
			
		if (!file.exists()) {
			throw new FileNotFoundException(filePath + " does not exist.");
		} else if (!file.isFile()) {
			throw new IllegalArgumentException(filePath + " is not a file.");
		}
		return file; 
	}
	
}
