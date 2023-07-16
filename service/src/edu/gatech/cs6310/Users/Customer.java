package edu.gatech.cs6310.Users;

import edu.gatech.cs6310.Components.Address;
import edu.gatech.cs6310.Components.Order;
import edu.gatech.cs6310.Components.Role;

public class Customer extends User {
	private int rating;
	private int credits;
	private Address address;
	
	public Customer(String fn, String ln, String ph, int r, int c) {
		firstName = fn;
		lastName = ln;
		phoneNumber = ph;
		rating = r;
		credits = c;
		role = Role.Customer;
	}

	public void setAddress(int x, int y) {
		address = new Address(x,y);
	}

	public Address getAddress(){

		return address;
	}

	public String toString() {
		return "name:" + firstName + "_" + lastName + ",phone:" + phoneNumber
				+ ",rating:" + rating + ",credit:" + credits + ",address:" + address;
	}
	
	public boolean canAfford(int cost) {
		return credits - cost > 0;
	}
	
	public void purchase(Order order) {
		deductCredit(order.getCost());
	}
	
	public void deductCredit(int amount) {
		credits -= amount;
	}
}
