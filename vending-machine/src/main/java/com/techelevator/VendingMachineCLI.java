package com.techelevator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import com.techelevator.view.Menu;

public class VendingMachineCLI {	

	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS,
													   MAIN_MENU_OPTION_PURCHASE };

	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_PRODUCT = "Select Product";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = { PURCHASE_MENU_OPTION_FEED_MONEY,
						PURCHASE_MENU_OPTION_SELECT_PRODUCT, PURCHASE_MENU_OPTION_FINISH_TRANSACTION };
	
	private static final String FEED_MONEY_MENU_OPTION_ONE = "$1";
	private static final String FEED_MONEY_MENU_OPTION_TWO = "$2";
	private static final String FEED_MONEY_MENU_OPTION_FIVE = "$5";
	private static final String FEED_MONEY_MENU_OPTION_TEN = "$10";
	private static final String[] FEED_MONEY_MENU_OPTIONS = { FEED_MONEY_MENU_OPTION_ONE, FEED_MONEY_MENU_OPTION_TWO,
						FEED_MONEY_MENU_OPTION_FIVE, FEED_MONEY_MENU_OPTION_TEN };
	
	private Menu menu;
	private VendingMachine machine;
	private Scanner userInput;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
		this.machine = new VendingMachine();
	}

	public void run() {
		boolean shopping = true;
		while (shopping) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				printItems();
				
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				System.out.println(String.format("\nCurrent Balance: $%#.2f", machine.getBalance()));
				String choice2 = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);

				if (choice2.equals(PURCHASE_MENU_OPTION_FEED_MONEY)) {
					System.out.println(String.format("\nCurrent balance: $%#.2f", machine.getBalance()));
					String choice3 = (String) menu.getChoiceFromOptions(FEED_MONEY_MENU_OPTIONS);

					if (choice3.equals(FEED_MONEY_MENU_OPTION_ONE)) {
						machine.addPaymentToBalance(1.00);
						System.out.println(String.format("\nCurrent balance: $%#.2f", machine.getBalance()));
						
					} else if (choice3.equals(FEED_MONEY_MENU_OPTION_TWO)) {
						machine.addPaymentToBalance(2.00);
						System.out.println(String.format("\nCurrent balance: $%#.2f", machine.getBalance()));
						
					} else if (choice3.equals(FEED_MONEY_MENU_OPTION_FIVE)) {
						machine.addPaymentToBalance(5.00);
						System.out.println(String.format("\nCurrent balance: $%#.2f", machine.getBalance()));
						
					} else if (choice3.equals(FEED_MONEY_MENU_OPTION_TEN)) {
						machine.addPaymentToBalance(10.00);
						System.out.println(String.format("\nCurrent balance: $%#.2f", machine.getBalance()));
					}
					
				} else if (choice2.equals(PURCHASE_MENU_OPTION_SELECT_PRODUCT)) {
					Item item = selectProduct();
					
					if (machine.getBalance() - item.getItemPrice() < 0) {
						System.out.println("\n*** Please feed more money before making your selection. ***");
						
					} else if (item.getQuantity() == 0) {
						System.out.println("\n*** This item is SOLD OUT. ***");
						
					} else if (item.getQuantity() > 0) {
						System.out.println("\nYou have selected " + item.getItemName());
						machine.subtractPurchaseFromBalance(item);
						machine.decrementQuantity(item);
						System.out.println(String.format("\nCurrent balance updated to $%#.2f", machine.getBalance()));
					}
					
				} else if (choice2.equals(PURCHASE_MENU_OPTION_FINISH_TRANSACTION)) {
					 	machine.makeChange();
						machine.consume();
						shopping = false;
				}
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();
	}
	
	private void printItems() {
		System.out.println(String.format("\n%-6s%-21s%-7s%s","Slot", "Item", "Price", "Quantity"));
		System.out.println("------------------------------------------");
		
		List<String> slots = new ArrayList<String>();
		slots.addAll(machine.getInventory().keySet());
		Collections.sort(slots);				//puts inventory slots in order A1 - D4.
		
		for(String slot : slots) {
			Item item = machine.getInventory().get(slot);
			if (item.getQuantity() == 0) {
				System.out.println(String.format("%-6s%-21s%#.2f   %-4d%s", item.getSlot(), item.getItemName(), 
						item.getItemPrice(), item.getQuantity(), "SOLD OUT"));
			} else {
				System.out.println(String.format("%-6s%-21s%#.2f   %d", item.getSlot(), item.getItemName(), 
						item.getItemPrice(), item.getQuantity()));
			}
		}
	}
	
	private Item selectProduct() { 
		userInput = new Scanner(System.in);
		Item item = null;
		String userChoice;
		while (item == null) {
			System.out.println("\nPlease choose a slot >>>");
			userChoice = userInput.nextLine().toUpperCase();
			item = machine.getInventory().get(userChoice);
			if (item == null) {
				System.out.println("\n*** That is not a valid slot. Please try again. ***");
			} 
		}
		return item;
	}
}
