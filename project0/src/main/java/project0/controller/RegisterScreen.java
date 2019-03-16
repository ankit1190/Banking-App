package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import project0.pojo.User;
import project0.service.UserService;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class RegisterScreen implements Screen{
	
	private static Logger log = Logger.getLogger(RegisterScreen.class);
	
	UserService uServer = new UserService();
	String fname;
	String lname;
	String uname;
	String pass;
	@Override
	public Screen start(BufferedReader br) {
		
		System.out.println("Creating New User");
		
		try {		
					
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
				return new Home().start(br);
			}else if(creatingUser(fname, lname, uname, pass)) {
				System.out.println("Navigating to Dashboard()");
				return new Dashboard().start(br);
			}else {
				System.out.println("\n\nInvalid values added.");
			}
			
		
			
		} catch (IOException e) {
			log.error(e.getCause());
			log.error(e.getMessage());
			log.warn("error in Register Screen");
//			e.printStackTrace();
		}
		
		return new Home().start(br);
	}
	
		/**
		 * 
		 * @param fname
		 * @param lname
		 * @param uname
		 * @param pass
		 * @return	true if the user is created otherwise returns false
		 */
		public boolean creatingUser(String fname, String lname, String uname, String pass) {
			
			
			if(!fname.matches(" ") && !lname.matches(" ") && !uname.matches(" ") && !pass.matches(" ")) {
				User newUser = uServer.createNewUser(fname, lname, uname, pass);
				if(newUser != null) {
					
					AppState.setCurrentUser(newUser);
	
					return true;
				}
				
			}
	
			return false;
		}

}
