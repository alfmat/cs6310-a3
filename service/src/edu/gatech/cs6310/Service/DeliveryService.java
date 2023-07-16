package edu.gatech.cs6310.Service;

import edu.gatech.cs6310.Components.Address;
import edu.gatech.cs6310.Components.Authenticator;
import edu.gatech.cs6310.Components.Store;
import edu.gatech.cs6310.Components.Storm;
import edu.gatech.cs6310.Components.Constants;
import edu.gatech.cs6310.Users.Customer;
import edu.gatech.cs6310.Users.Pilot;
import edu.gatech.cs6310.Users.User;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;

public class DeliveryService {
	
	Map<String, Store> stores;
	Set<String> pilotLicenseId;
	Map<String, Pilot> pilots;
	Map<String, Customer> customers;
	public Map<String, Storm> storms;
	Authenticator auth;
	User currentUser;
	
	public DeliveryService() {
		stores = new TreeMap<>();
		pilotLicenseId = new HashSet<>();
		pilots = new TreeMap<>();
		customers = new TreeMap<>();
		storms = new TreeMap<>();
		auth = new Authenticator();
		currentUser = null;
	}
	
	public void execAgainstInput(Scanner commandLineInput) {
		System.out.print("Welcome to the Grocery Express Delivery Service!\n");
		System.out.print("You MUST create account/login to proceed:\n");
		System.out.print("1.Type 'login,<username>,<password>' to log into your account.\n");
        System.out.print("2.Type 'create_user,<username>,<password>,<role>' to create a new customer account.\n");
        System.out.print("-- Available roles are: admin, customer\n");
        System.out.print("3.Type 'logout' to logout from your account.\n");
		System.out.print("4.Type 'switch_user,<username>,<password>' to logout of the current account and log into another account.\n");
		if(commandLineInput == null) {
    		commandLineInput = new Scanner(System.in);
    	}
        String wholeInputLine;
        String[] tokens;
        final String DELIMITER = ",";

        while (commandLineInput.hasNextLine()) {
            try {
                // Determine the next command and echo it to the monitor for testing purposes
                wholeInputLine = commandLineInput.nextLine();
                tokens = wholeInputLine.split(DELIMITER);
                System.out.print("> " + wholeInputLine + "\n");

                if (tokens[0].startsWith("//")) {
                	continue;
                } else if(tokens[0].equals("login")) {
					if(currentUser != null) {
						System.out.print("ERROR:already_logged_in\n");
					} else {
						String username = tokens[1];
						String pass = tokens[2];
						currentUser = auth.login(username, pass);
					}
                } else if (tokens[0].equals("create_user")) {
                	String username = tokens[1];
                	String pass = tokens[2];
                	String role = tokens[3];
                	currentUser = auth.createCustomerAccount(username, pass, role);
                } else if (tokens[0].equals("logout")) { 
                	if(currentUser == null) {
                		System.out.print("INFO:already_logged_out\n");
                	} else {
                		currentUser = null;
                    	System.out.print("OK:logout_successful\n");
                	}
                } else if(tokens[0].equals("switch_user")) {
					if(currentUser == null) {
						System.out.print("ERROR:user_not_logged_in\n");
					} else {
						String username = tokens[1];
						String pass = tokens[2];
						currentUser = auth.login(username, pass);
					}
                } else if (tokens[0].equals("stop")) {
                	System.out.print("stop acknowledged\n");
                    break;
                } else if (currentUser == null) { 
                	System.out.print("INFO:user_not_logged_in\n");
                } else if(!auth.allowedCommand(currentUser, tokens[0])) {
                	System.out.print("ERROR:user_permissions_not_enabled\n");
                } else if(tokens[0].equals("make_storm")) {
					// tokens[1] = stormID, tokens[2] = size, tokens[3] = intensity, tokens[4] = x, tokens[5]= y
					int x = Integer.parseInt(tokens[4]);
					int y = Integer.parseInt(tokens[5]);
					if (storms.containsKey(tokens[1])){
						System.out.print("ERROR:stormID_already_exists\n");
					} else if(Integer.parseInt(tokens[2]) < 1 || Integer.parseInt(tokens[2]) > 25) {
						System.out.print("ERROR:storm_size_out_of_range\n");
					} else if(Integer.parseInt(tokens[3]) < 1 || Integer.parseInt(tokens[3]) > 5) {
						System.out.print("ERROR:storm_intensity_out_of_range\n");
					}else if (x < 1 || x > 100 || y < 1 || y > 100 ) {
						System.out.print("ERROR:address_is_out_of_range\n");
					}  else {
						Storm storm = new Storm((tokens[1]), Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), new Address(Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5])));
						storms.put(tokens[1], storm);
						System.out.print("OK:storm_created\n");
					}
				} else if(tokens[0].equals("remove_storm")) {
					//tokens[1] = stormID
					if(storms.containsKey(tokens[1])) {
						storms.remove(tokens[1]);
						System.out.print("OK:storm_removed\n");
					} else {
						System.out.print("ERROR:storm_does_not_exist\n");
					}
				} else if(tokens[0].equals("display_storms")) {
					for(Map.Entry<String, Storm> st: storms.entrySet()) {
                    	System.out.print(st.getValue() + "\n");
                    }
                    System.out.print("OK:display_completed\n");
				} else if (tokens[0].equals("make_store")) {
                	int x = Integer.parseInt(tokens[3]);
                	int y = Integer.parseInt(tokens[4]);
                	if(stores.containsKey(tokens[1])) {
                		System.out.print("ERROR:store_identifier_already_exists\n");
                	} else if (x < 1 || x > 100 || y < 1 || y > 100 ) {
						System.out.print("ERROR: address_is_out_of_range\n");
					}
                	else {
                		Store store = new Store(tokens[1], Integer.parseInt(tokens[2]));
                		store.setAddress(x, y);
                		stores.put(tokens[1], store);
                		System.out.print("OK:change_completed\n");
                	}

                } else if (tokens[0].equals("display_stores")) {
                    
                	for(Map.Entry<String, Store> store: stores.entrySet()) {
                    	System.out.print(store.getValue() + "\n");
                    }
                    System.out.print("OK:display_completed\n");
                
                } else if (tokens[0].equals("sell_item")) {
                	
                	if(stores.containsKey(tokens[1])) {
                		Store store = stores.get(tokens[1]);
                		store.addItem(tokens[2], Integer.parseInt(tokens[3]));
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}

                } else if (tokens[0].equals("display_items")) {
                	
                	if(stores.containsKey(tokens[1])) {
                		Store store = stores.get(tokens[1]);
                		store.displayItems();
                		System.out.print("OK:display_completed\n");
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}

                } else if (tokens[0].equals("make_pilot")) {
                	
                	if(pilots.containsKey(tokens[1])) {
                		System.out.print("ERROR:pilot_identifier_already_exists\n");
                	} else if(pilotLicenseId.contains(tokens[6])) {
                		System.out.print("ERROR:pilot_license_already_exists\n");
                	} else {
                		pilots.put(tokens[1], new Pilot(tokens[1], tokens[2], 
                				tokens[3], tokens[4], tokens[5], tokens[6], 
                				Integer.parseInt(tokens[7])));
                		pilotLicenseId.add(tokens[6]);
                		System.out.print("OK:change_completed\n");
                	}

                } else if (tokens[0].equals("display_pilots")) {
                	
                	for(Map.Entry<String, Pilot> pilot: pilots.entrySet()) {
                    	System.out.print(pilot.getValue()+"\n");
                    }
                    System.out.print("OK:display_completed\n");
                
                } else if (tokens[0].equals("make_drone")) {
                	
                	if(!stores.containsKey(tokens[1])) {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	} else if(stores.get(tokens[1]).hasDrone(tokens[2])) {
                		System.out.print("ERROR:drone_identifier_already_exists\n");
                	} else {
                		stores.get(tokens[1]).addDrone(tokens[2], Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
                		System.out.print("OK:change_completed\n");
                	}

                } else if (tokens[0].equals("display_drones")) {
                    
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).displayDrones();
                    	System.out.print("OK:display_completed\n");
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}
                	
                	
                } else if (tokens[0].equals("fly_drone")) {
                    if(stores.containsKey(tokens[1])) {
                    	if(stores.get(tokens[1]).hasDrone(tokens[2])) {
                    		if(pilots.containsKey(tokens[3])) {
                    			stores.get(tokens[1]).addPilotToDrone(tokens[2], pilots.get(tokens[3]));
                    			System.out.print("OK:change_completed\n");
                    		} else {
                    			System.out.print("ERROR:pilot_identifier_does_not_exist\n");
                    		}
                    	} else {
                    		System.out.print("ERROR:drone_identifier_does_not_exist\n");
                    	}
                    } else {
                    	System.out.print("ERROR:store_identifier_does_not_exist\n");
                    }

                } else if (tokens[0].equals("make_customer")) {
					int x = Integer.parseInt(tokens[7]);
					int y = Integer.parseInt(tokens[8]);
                	if(customers.containsKey(tokens[1])) {
                		System.out.print("ERROR:customer_identifier_already_exists\n");
                	} else if (x < 1 || x > 100 || y < 1 || y > 100 ) {
						System.out.println("ERROR: address_is_out_of_range\n");
					} else {
						Customer cust = new Customer(tokens[2], tokens[3], tokens[4], Integer.parseInt(tokens[5]), Integer.parseInt(tokens[6]));
						cust.setAddress(x,y);
						customers.put(tokens[1], cust);
                		System.out.print("OK:change_completed\n");
                	}

                } else if (tokens[0].equals("display_customers")) {
                	
                	for(Map.Entry<String, Customer> customer : customers.entrySet()) {
                		System.out.print(customer.getValue()+"\n");
                	}
                	System.out.print("OK:display_completed\n");
                	
                } else if (tokens[0].equals("start_order")) {
                	
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).addOrder(tokens[2], tokens[3], tokens[4], this);
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}

                } else if (tokens[0].equals("display_orders")) {
                	
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).displayOrders();
                		System.out.print("OK:display_completed\n");
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}

                } else if (tokens[0].equals("request_item")) {
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).requestItem(tokens[2], tokens[3], Integer.parseInt(tokens[4]), Integer.parseInt(tokens[5]), this);
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}

                } else if (tokens[0].equals("purchase_order")) {
                	
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).purchaseOrder(tokens[2], this);
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}
                } else if (tokens[0].equals("cancel_order")) {
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).cancelOrder(tokens[2]);
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}

                } else if (tokens[0].equals("transfer_order")) {
                	if(stores.containsKey(tokens[1])) {
                		stores.get(tokens[1]).transferOrder(tokens[2], tokens[3]);
                	} else {
                		System.out.print("ERROR:store_identifier_does_not_exist\n");
                	}
                } else if (tokens[0].equals("display_efficiency")) {
                	
                	for(Map.Entry<String, Store> store: stores.entrySet()) {
                		System.out.print(store.getValue().getEfficiency()+"\n");
                	}
                	System.out.print("OK:display_completed\n");

                } else {
                    
                	System.out.print("command " + tokens[0] + " NOT acknowledged\n");
                }
                
            } catch (Exception e) {
                
            	e.printStackTrace();
                System.out.print("\n");
            
            }
        }

        System.out.print("simulation terminated\n");
        commandLineInput.close();
	}
	
	public boolean hasCustomer(String customerId) {
		return customers.containsKey(customerId);
	}
	
	public Customer getCustomer(String customerId) {
		return customers.get(customerId);
	}
	
	public String testCommandLoop(Scanner commandLineInput) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		PrintStream ps = new PrintStream(baos);
		PrintStream old = System.out;
		System.setOut(ps);
		execAgainstInput(commandLineInput);
		System.out.flush();
		System.setOut(old);
		return baos.toString();
	}
	
	public void testDBConnection() {
		boolean success = false;
		System.out.print("Attempting SQL Server DB Connection...");
		while(!success) {
			try {
				Thread.sleep(1000);
				Connection conn = DriverManager.getConnection(Constants.JDBC_URL);
				conn.close();
				System.out.print("Success!\n");
				success = true;
			} catch (Exception e) {
				System.out.print(".");
			}
		}
	}

    public void commandLoop() {
    	testDBConnection();
    	execAgainstInput(null);
    }
}
