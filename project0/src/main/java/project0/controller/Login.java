package project0.controller;

import java.io.BufferedReader;

import org.apache.log4j.Logger;

import exception.UserNotFoundException;
import project0.pojo.User;
import project0.service.UserService;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class Login implements Screen {
	
	private static Logger log = Logger.getLogger(Login.class);
	
	private UserService uServer = new UserService();

	@Override
	public Screen start(BufferedReader br) {
		
		String username;
		String password;
		
		try {
			
			System.out.println("\n\n\n\n\n\n\n+-------------------------------+");
			System.out.println("Please provide username and password.");
			System.out.print("Username: ");
			username = br.readLine();
			System.out.print("Password: ");
			password = br.readLine();
			
			User loggedUser = uServer.getUserByCredentials(username, password);
			
			if(!loggedUser.equals(null)) {
				
				System.out.println("Navigating to dashboard...");
				
				AppState.setCurrentUser(loggedUser);
				
				return new Dashboard().start(br);
			} else {
				AppState.setCurrentUser(null);
				
				throw new UserNotFoundException("Invalid credentials!");
			}
			
		} catch(UserNotFoundException unfe) {
			
			log.warn("Login attempt unsuccessful, invalid credentials");
			
		} catch(Exception e) {
			
//			e.printStackTrace();
			log.error(e.getMessage());
			log.warn("Error reading input from console");
			log.info("Restarting application...");
//			AppState.setRestartingApp(true);
//			AppState.setAppRunning(false);
			return new Home().start(br);
			
		}
		
		log.info("Navigating back to home screen...");
		
		
		return new Home().start(br);
	}

}
