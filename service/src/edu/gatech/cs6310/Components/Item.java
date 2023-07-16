package edu.gatech.cs6310.Components;

public class Item {
	private String name;
	private int weight;

	public Item(String n, int w) {
		name = n;
		weight = w;
	}
	
	public String toString() {
		return name + "," + weight;
	}
	
	public String getName() {
		return name;
	}

	public int getWeight() {
		return weight;
	}
}
