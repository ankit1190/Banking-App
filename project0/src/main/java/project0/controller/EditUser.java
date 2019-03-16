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
public class EditUser implements Screen{
	
	private static Logger log = Logger.getLogger(EditUser.class);

	UserService uServer = new UserService();
	String username;
	String password;
	String fname;
	String lname;
	String uname;
	String pass;
	
	@Override
	public Screen start(BufferedReader br) {
		
		System.out.println("Editing User");
		
		try {		
		
			System.out.println("Input new values that you would like to change");
			System.out.println("or type in 'keep same' to keep same values");
			System.out.print("First name: ");
			fname = br.readLine();
			System.out.print("Last name: ");
			lname = br.readLine();
			System.out.print("Username: ");
			uname = br.readLine();
			System.out.print("Password: ");
			pass = br.readLine();
			
			if(pass.trim().isEmpty() || uname.trim().isEmpty() || fname.trim().isEmpty() || lname.trim().isEmpty()) {
				System.out.println("\n\nInvalid entries inserted.");
				return new Dashboard().start(br);
			}
			if(editingUser(fname, lname, uname, pass)) {
				return new Dashboard().start(br);
			}
			
		
			
		} catch (IOException e) {
			
			log.error(e.getMessage());
			log.warn("Error reading input from console");
			log.info("Restarting application...");
//			AppState.setRestartingApp(true);
//			AppState.setAppRunning(false);
			return new Dashboard().start(br);
		}
		
		return new Dashboard().start(br);
//		return this.start(br);
	}
	
	/**
	 * 
	 * @param fname
	 * @param lname
	 * @param uname
	 * @param pass
	 * @return	true if current user object has been updated with new information.
	 */
	public boolean editingUser(String fname, String lname, String uname, String pass) {
		
		
		if(fname.equalsIgnoreCase("keep same")) {
			fname = AppState.getCurrentUser().getFirstName();
		}
		
		if(lname.equalsIgnoreCase("keep same")) {
			lname = AppState.getCurrentUser().getLastName();
		}
		
		if(uname.equalsIgnoreCase("keep same")) {
			uname = AppState.getCurrentUser().getUserName();
		}
		
		if(pass.equalsIgnoreCase("keep same")) {
			pass = AppState.getCurrentUser().getPassword();
		}
		
		if(uServer.updateUser(fname, lname, uname, pass) != null) {
			AppState.setCurrentUser(uServer.updateUser(fname, lname, uname, pass));
			return true;
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param br
	 * @return	to the edit user screen if successful otherwise return back to dashboard screen
	 */
		public Screen verify(BufferedReader br) {
			
			System.out.println("\n\n\n First could you re-enter your credentials");
			
			
			try {
				
				System.out.print("Username: ");
				username = br.readLine();
				System.out.print("Password: ");
				password = br.readLine();
				if(uServer.verifyUser(username, password)) {
					this.start(br);
				}
				else {
					System.out.println("\n\n\n Invalid credentials.");
					
				}
			} catch (IOException e) {
				
				log.error(e.getMessage());
				log.warn("[ERROR] error in Deposit verify Screen");
				
				return new Dashboard().start(br);
			}catch (NumberFormatException nfe) {
				log.warn("Invalid numeric value entered.");
				return new Dashboard().start(br);
			}
			
			
			return new Dashboard().start(br);
		}

	
}
