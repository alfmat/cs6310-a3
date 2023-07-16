package edu.gatech.cs6310.Users;

public class Pilot extends Employee {
	
	private String license;
	private int experience;

	public Pilot(String a, String fn, String ln, String ph, String tx, String lic, int ex) {
		super(tx);
		firstName = fn;
		lastName = ln;
		phoneNumber = ph;
		license = lic;
		experience = ex;
	}
	
	public String toString() {
		return "name:" + firstName + "_" + lastName + ",phone:" + phoneNumber + 
				",taxID:" + ssn + ",licenseID:" + license + ",experience:" + experience;
	}
	
	public void gainExperience() {
		experience++;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}
}
