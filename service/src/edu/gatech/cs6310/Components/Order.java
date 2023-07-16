package edu.gatech.cs6310.Components;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
	private String orderId;
	private String droneId; 
	private String customerId;
	private Map<String, LineItem> lineItems;
	
	public Order(String o, String d, String c) {
		orderId = o;
		droneId = d;
		customerId = c;
		lineItems = new LinkedHashMap<>();
	}
	
	public void addLineItem(LineItem lineItem) {
		lineItems.put(lineItem.getItem().getName(), lineItem);
	}
	
	public void displayLineItems() {
		for(Map.Entry<String, LineItem> lineItem: lineItems.entrySet()) {
			System.out.print(lineItem.getValue() + "\n");
		}
	}
	
	public int getCost() {
		int cost = 0;
		for(Map.Entry<String, LineItem> lineItem: lineItems.entrySet()) {
			cost += lineItem.getValue().getCost();
		}
		return cost;
	}
	
	public int getWeight() {
		int weight = 0;
		for(Map.Entry<String, LineItem> lineItem: lineItems.entrySet()) {
			weight += lineItem.getValue().getWeight();
		}
		return weight;
	}
	
	public String toString() {
		return "orderID:" + orderId;
	}

	public String getDroneId() {
		return droneId;
	}

	public void setDroneId(String droneId) {
		this.droneId = droneId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public Map<String, LineItem> getLineItems() {
		return lineItems;
	}
}
