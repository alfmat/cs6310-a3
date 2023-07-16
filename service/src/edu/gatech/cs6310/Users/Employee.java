package edu.gatech.cs6310.Users;

import edu.gatech.cs6310.Components.Role;

public class Employee extends User {
	String ssn;
	
	public Employee(String s) {
		ssn = s;
		role = Role.Employee;
	}
}
