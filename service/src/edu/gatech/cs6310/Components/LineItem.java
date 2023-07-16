package edu.gatech.cs6310.Components;

public class LineItem {
	private Item item;
	private int quantity;
	private int price;
	
	public LineItem(Item i, int q, int p) {
		item = i;
		quantity = q;
		price = p;
	}
	
	public String toString() {
		return "item_name:" + item.getName() + ",total_quantity:" + quantity + ",total_cost:" + getCost() + ",total_weight:" + getWeight();
	}
	
	public Item getItem() {
		return item;
	}

	public int getCost() {
		return quantity * price;
	}
	
	public int getWeight() {
		return item.getWeight() * quantity;
	}
}
