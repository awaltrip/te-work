package com.techelevator;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class VendingMachineTest {
	
	private VendingMachine machine;
	private ByteArrayOutputStream output;
	
	@Before
	public void setup() { 
		machine = new VendingMachine();
		output = new ByteArrayOutputStream();
		System.setOut(new PrintStream(output));
	}
	
	@Test
	public void gets_inventory() {
		Map<String, Item> inventory = machine.getInventory();
		
		Assert.assertEquals(16, inventory.size());
		Assert.assertNotNull(inventory.get("A1"));
		Assert.assertNotNull(inventory.get("C3"));
	}
	
	@Test
	public void adds_fed_money_to_balance() {
		machine.addPaymentToBalance(5.00);
		
		Assert.assertEquals(5.00, machine.getBalance(), 0.01);
	}
	
	@Test
	public void subtracts_balance() {
		machine.addPaymentToBalance(5.00);
		Map<String, Item> inventory = machine.getInventory();
		Item item = inventory.get("A1");
		machine.subtractPurchaseFromBalance(item);
		
		Assert.assertEquals(1.95, machine.getBalance(), 0.01);
	}
	
	@Test
	public void decrements_quantity() {
		machine.addPaymentToBalance(5.00);
		Map<String, Item> inventory = machine.getInventory();
		Item item = inventory.get("A1");
		machine.decrementQuantity(item);
		
		Assert.assertEquals(4, item.getQuantity(), 0.01);
	}
	
	@Test
	public void makes_change() {
		machine.addPaymentToBalance(1.95);
		machine.makeChange();
		
		String expected = "\nYour change is:\n"+
							"7 Quarters,\n"+
							"2 Dimes,\n"+
							"0 Nickels,\n"+
							"$1.95 total change.\n";
		
		Assert.assertEquals(0, machine.getBalance(), 0.01);
		Assert.assertEquals(expected, output.toString());
	}

}
