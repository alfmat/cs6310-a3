package edu.gatech.cs6310.Components;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import edu.gatech.cs6310.Users.Administrator;
import edu.gatech.cs6310.Users.Customer;
import edu.gatech.cs6310.Users.Employee;
import edu.gatech.cs6310.Users.User;

public class Authenticator {

    private User queryUser(String username, String password) {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	User user = null;
    	try {
    		conn = DriverManager.getConnection(Constants.JDBC_URL);
    		String sql = """
    				OPEN SYMMETRIC KEY User_Passwords
    				DECRYPTION BY CERTIFICATE Passwords
    				
    				SELECT id, position, CONVERT(VARCHAR, DecryptByKey(pwd)) AS passwd FROM Users WHERE id LIKE ? AND CONVERT(VARCHAR, DecryptByKey(pwd)) like ?
    				""";
    		ps = conn.prepareStatement(sql);
    		ps.setString(1, username);
    		ps.setString(2, password);
    		ResultSet rs = ps.executeQuery();
    		while(rs.next()) {
    			if(username.equals(rs.getString("id")) && password.equals(rs.getString("passwd"))) {
    				String role = rs.getString("position");
    				switch(role) {
    				case "admin":
    					user = new Administrator(username);
    					break;
    				case "customer":
    					user = new Customer(username, "", "", 0, 0);
    					break;
    				case "employee":
    					user = new Employee(username);
    					break;
    				}
    			}
    		}
    		if(user != null) {
				System.out.print("INFO:Successfully_logged_in_" + username + "_Welcome!\n");
			} else {
				System.out.print("ERROR:unable_to_login_user\n");
			}
    	} catch (Exception e) {
    		System.out.print("ERROR:db_connection_issue\n");
    	} finally {
    		try {
    			if(conn != null) {
    				conn.close();
    			}
    			if(ps != null) {
    				ps.close();
    			}
    		} catch(Exception e) {
    			System.out.print("ERROR:unable_to_close_db_connection\n");
    		}
    	}
    	return user;
    }

    public User login(String username, String password) {
        User userRecord = queryUser(username, password);
        return userRecord;
    }

    public boolean allowedCommand(User user, String command) {
    	if(user == null) return false;
    	boolean result = false;
        switch(command) {
            // admin only commands
            case "make_store":
            case "sell_item":
            case "make_pilot":
            case "make_drone":
            case "make_storm":
            case "remove_storm":
            case "display_efficiency":
                result = user.getRole() == Role.Admin;
                break;

            // admin/employee commands
            case "display_pilots":
            case "display_drones":
            case "fly_drone":
            case "transfer_order":
            	result = (user.getRole() == Role.Admin) || (user.getRole() == Role.Employee);
            	break;

            // all users commands
            case "display_stores":
            case "display_items":
            case "make_customer":
            case "display_customers":
            case "start_order":
            case "display_orders":
            case "request_item":
            case "purchase_order":
            case "cancel_order":
            case "display_storms":
            case "stop":
                result = true;
                break;
        }
        return result;
    }


    public User createCustomerAccount(String username, String password, String userRole) {
        User userRecord = insertUser(username, password, userRole);
        return userRecord;
    }
    
    private User insertUser(String username, String password, String userRole) {
    	Connection conn = null;
    	PreparedStatement ps = null;
    	User user = null;
    	try {
    		conn = DriverManager.getConnection(Constants.JDBC_URL);
    		String sql = "OPEN SYMMETRIC KEY User_Passwords DECRYPTION BY CERTIFICATE Passwords INSERT INTO Users (id, position, pwd) VALUES ('" + username + "', '" + userRole + "', EncryptByKey(Key_GUID('User_Passwords'), '" + password + "'))";
    		ps = conn.prepareStatement(sql);
    		ps.executeUpdate();
    		
			switch(userRole) {
				case "admin":
					user = new Administrator(username);
					break;
				case "customer":
					user = new Customer(username, "", "", 0, 0);
					break;
				case "employee":
					user = new Employee(username);
					break;
			}
			if(user != null) {
				System.out.print("INFO:Successfully_inserted:" + username + "_Welcome!\n");
			} else {
				System.out.print("ERROR:Unable_to_insert_user\n");
			}
    	} catch (Exception e) {
    		e.printStackTrace();
    		System.out.print("ERROR:unable_to_create_user\n");
    	} finally {
    		try {
    			if(conn != null) {
    				conn.close();
    			}
    			if(ps != null) {
    				ps.close();
    			}
    		} catch(Exception e) {
    			System.out.print("ERROR:unable_to_close_db_connection\n");
    		}
    	}
    	return user;
    }
    
}