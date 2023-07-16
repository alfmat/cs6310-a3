package edu.gatech.cs6310.Users;

import edu.gatech.cs6310.Components.Role;

public abstract class User {
	String firstName;
	String lastName;
	String phoneNumber;
	Role role;
	
	public Role getRole() {
		return this.role;
	}
}
