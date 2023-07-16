package edu.gatech.cs6310.Components;

import edu.gatech.cs6310.Service.DeliveryService;
import edu.gatech.cs6310.Users.Customer;
import edu.gatech.cs6310.Users.Pilot;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class Store {
	
	private String name;
	private int revenue;
	private Map<String, Item> items;
	private Map<String, Order> orders;
	private Map<String, Drone> drones;
	private Address address;

	private int purchases;
	private int overloads;
	private int transfers;

	public void setAddress(int x, int y) {
		address = new Address(x, y);

	}

	public Store(String n, int r) {
		name = n;
		revenue = r;
		items = new TreeMap<>();
		orders = new TreeMap<>();
		purchases = 0;
		overloads = 0;
		transfers = 0;
		drones = new HashMap<>();
	}
	
	public void addItem(String itemName, int itemWeight) {
		Item item = new Item(itemName, itemWeight);
		if(items.containsKey(item.getName())) {
			System.out.print("ERROR:item_identifier_already_exists\n");
		} else {
			items.put(item.getName(), item);
			System.out.print("OK:change_completed\n");
		}
	}
	
	public void displayItems() {
		for(Map.Entry<String, Item> item: items.entrySet()) {
			System.out.print(item.getValue() + "\n");
		}
	}
	
	public boolean hasDrone(String droneId) {
		return drones.containsKey(droneId);
	}
	
	public void addDrone(String id, int weightCapacity, int fuel) {
		drones.put(id, new Drone(id, weightCapacity, fuel));
	}
	
	public void addOrder(String orderId, String droneId, String customerId, DeliveryService service) {
		if(!orders.containsKey(orderId)) {
			if(drones.containsKey(droneId)) {
				Drone drone = drones.get(droneId);
        		if(service.hasCustomer(customerId)) {
        			Order newOrder = new Order(orderId, droneId, customerId);
        			orders.put(orderId, newOrder);
        			drone.addOrder();
        			System.out.print("OK:change_completed\n");
        		} else {
        			System.out.print("ERROR:customer_identifier_does_not_exist\n");
        		}
        	} else {
        		System.out.print("ERROR:drone_identifier_does_not_exist\n");
        	}
		} else {
			System.out.print("ERROR:order_identifier_already_exists\n");
		}
	}
	
	public void requestItem(String orderId, String itemId, int quantity, int price, DeliveryService service) {
		if(orders.containsKey(orderId)) {
			Order order = orders.get(orderId);
			if(items.containsKey(itemId)) {
				Item item = items.get(itemId);
				LineItem lineItem = new LineItem(item, quantity, price);
				if(!order.getLineItems().containsKey(itemId)) {
					Customer customer = service.getCustomer(order.getCustomerId());
					if(customer.canAfford(order.getCost() + lineItem.getCost())) {
						Drone drone = drones.get(order.getDroneId());
						if(drone.canAddLineItem(lineItem)) {
							order.addLineItem(lineItem);
							drone.addLineItem(lineItem);
							System.out.print("OK:change_completed\n");
						} else {
							System.out.print("ERROR:drone_cant_carry_new_item\n");
						}
					} else {
						System.out.print("ERROR:customer_cant_afford_new_item\n");
					}
				} else {
					System.out.print("ERROR:item_already_ordered\n");
				}
			} else {
				System.out.print("ERROR:item_identifier_does_not_exist\n");
			}
		} else {
			System.out.print("ERROR:order_identifier_does_not_exist\n");
		}
	}
	
	public void cancelOrder(String orderId) {
		if(orders.containsKey(orderId)) {
			Order order = orders.get(orderId);
			Drone drone = drones.get(order.getDroneId());
        	drone.removeOrder(order);
        	orders.remove(orderId);
        	System.out.print("OK:change_completed\n");
		} else {
			System.out.print("ERROR:order_identifier_does_not_exist\n");
		}
	}
	
	public void transferOrder(String orderId, String droneId) {
		if(orders.containsKey(orderId)) {
			Order order = orders.get(orderId);
			if(drones.containsKey(droneId)) {
				Drone droneTo = drones.get(droneId);
				Drone droneFrom = drones.get(order.getDroneId());
				if(droneTo == droneFrom) {
					System.out.print("OK:new_drone_is_current_drone_no_change\n");
				} else {
					if(droneTo.canAddOrder(order)) {
						droneFrom.removeOrder(order);
                    	droneTo.addOrder(order);
                    	order.setDroneId(droneTo.getId());
                    	transfers++;
                    	System.out.print("OK:change_completed\n");
					} else {
						System.out.print("ERROR:new_drone_does_not_have_enough_capacity\n");
					}
				}
			} else {
				System.out.print("ERROR:drone_identifier_does_not_exist\n");
			}
		} else {
			System.out.print("ERROR:order_identifier_does_not_exist\n");
		}
	}
	
	public void purchaseOrder(String orderId, DeliveryService service) {
		if(orders.containsKey(orderId)) {
			Order order = orders.get(orderId);
			Customer customer = service.getCustomer(order.getCustomerId());
			Drone drone = drones.get(order.getDroneId());
			if(drone.hasPilot()) {
				if(drone.hasFuel()) {

					for (Storm s: service.storms.values()) {
						if (s.stormLocation.getDistance(customer.getAddress()) < s.size || s.stormLocation.getDistance(address) < s.size)
						{
							if(s.intensity*20 > Math.random()*100){
								//strike the drone
								revenue -= order.getCost();
								System.out.println("ALERT:drone_struck_by_lightning");
								return;
							}
							else
								continue;

						}

					}
					overloads += drone.getNumOrders() - 1;
					purchases++;
					revenue += order.getCost();
					drone.deliver(order);
					customer.purchase(order);
					orders.remove(orderId);
                	System.out.print("OK:change_completed\n");
				} else {
					System.out.print("ERROR:drone_needs_fuel\n");
				}
			} else {
				System.out.print("ERROR:drone_needs_pilot\n");
			}
		} else {
			System.out.print("ERROR:order_identifier_does_not_exist\n");
		}
	}
	
	public void displayOrders() {
		for(Map.Entry<String, Order> order: orders.entrySet()) {
			System.out.print(order.getValue()+"\n");
			order.getValue().displayLineItems();
		}
	}
	
	public void addPilotToDrone(String droneId, Pilot pilot) {
		for(Map.Entry<String, Drone> droneEntry: drones.entrySet()) {
			Drone drone = droneEntry.getValue();
			if(drone.getFlownBy() == pilot) {
				drone.setFlownBy(null);
			}
		}
		drones.get(droneId).setFlownBy(pilot);
	}
	
	public void displayDrones() {
		for(Map.Entry<String, Drone> drone: drones.entrySet()) {
        	System.out.print(drone.getValue()+"\n");
        }
	}
	
	public String getEfficiency() {
		return "name:" + name + ",purchases:" + purchases + ",overloads:" + overloads + ",transfers:" + transfers;
	}
	
	public String toString() {
		return "name:" + name + ",revenue:" + revenue + ",address:" + address;
	}
}
