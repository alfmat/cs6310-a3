package edu.gatech.cs6310.Components;

import edu.gatech.cs6310.Users.Pilot;

public class Drone {
	
	private String id;
	private int totalCapacity;
	private int remainingCapacity;
	private int fuel;
	private int numOrders;
	private Pilot flownBy;
	
	public Drone(String i, int wc, int f) {
		id = i;
		totalCapacity = wc;
		remainingCapacity = wc;
		fuel = f;
		flownBy = null;
	}
	
	public boolean hasFuel() {
		return fuel >= 1;
	}
	
	public boolean hasPilot() {
		return flownBy != null;
	}
	
	public void setFlownBy(Pilot p) {
		flownBy = p;
	}
	
	public void addOrder() {
		numOrders++;
	}
	
	public void addLineItem(LineItem lineItem) {
		remainingCapacity -= lineItem.getWeight();
	}
	
	public void removeOrder(Order order) {
		remainingCapacity += order.getWeight();
		numOrders--;
	}
	
	public void addOrder(Order order) {
		remainingCapacity -= order.getWeight();
		numOrders++;
	}
	
	public boolean canAddLineItem(LineItem lineItem) {
		return remainingCapacity - lineItem.getWeight() > 0;
	}
	
	public boolean canAddOrder(Order order) {
		return remainingCapacity - order.getWeight() >= 0;
	}
	
	public void deliver(Order order) {
		removeOrder(order);
		fuel--;
		flownBy.gainExperience();
	}
	
	public String toString() {
		if(flownBy == null) {
			return "droneID:" + id + ",total_cap:" + totalCapacity + 
					",num_orders:" + numOrders + ",remaining_cap:"
					+ remainingCapacity + ",trips_left:" + fuel;
		} else {
			return "droneID:" + id + ",total_cap:" + totalCapacity + 
					",num_orders:" + numOrders + ",remaining_cap:"
					+ remainingCapacity + ",trips_left:" + fuel + ",flown_by:" 
					+ flownBy.getFirstName() + "_" + flownBy.getLastName();
		}
	}
	
	public String getId() {
		return id;
	}

	public int getNumOrders() {
		return numOrders;
	}

	public Pilot getFlownBy() {
		return flownBy;
	}
}
