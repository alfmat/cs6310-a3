package edu.gatech.cs6310.Users;

import edu.gatech.cs6310.Components.Role;

public class Administrator extends User {
	
	private String name;
	
	public Administrator(String n) {
		name = n;
		role = Role.Admin;
	}
	
	public String getName() {
		return name;
	}
}
