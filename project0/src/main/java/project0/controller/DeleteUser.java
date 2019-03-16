package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import project0.service.UserService;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class DeleteUser implements Screen{
	
	private static Logger log = Logger.getLogger(DeleteUser.class);

	String username;
	String password;
	UserService uServer = new UserService();
	
	@Override
	public Screen start(BufferedReader br) {
		
		System.out.println("Deleting User");
				
					
			if(uServer.removeUser(username, password)) {
				System.out.println("User " + AppState.getCurrentUser().getUserName() + " is deleted.");
				AppState.setCurrentUser(null);
				return new Home().start(br);
			}else {
				log.warn("Couldn't delete for some reason.");
				log.info("Navigating back to Dashboard...");
				return new Dashboard().start(br);
			}
		
	}

/**
 * 
 * @param br
 * @return	to the delete user screen otherwise return back to dashboard screen.
 */
public Screen verify(BufferedReader br) {
		
		System.out.println("First could you re-enter your credentials");
		
		
		try {
			
			System.out.print("Username: ");
			username = br.readLine();
			System.out.print("Password: ");
			password = br.readLine();
			if(uServer.verifyUser(username, password)) {
				this.start(br);
			}
			else {
				System.out.println("Invalid credentials.");
				return new Dashboard().start(br);
				
			}
		} catch (IOException e) {
//			e.printStackTrace();
			System.out.println(e.getMessage());
			System.out.println("[ERROR] from DeleteUser Screen");
			return new Dashboard().start(br);
		}catch (NumberFormatException nfe) {
			System.out.println("Invalid numeric value entered.");
			return new Dashboard().start(br);
		}
		
		
		return new Dashboard().start(br);
	}

}
