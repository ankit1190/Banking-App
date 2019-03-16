package project0.controller;

import java.io.BufferedReader;

import java.util.List;

import org.apache.log4j.Logger;

import project0.pojo.Account;
import project0.service.AccountService;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class Dashboard implements Screen{

	private static Logger log = Logger.getLogger(Dashboard.class);
	
	String userSelection;
	String fullName = AppState.getCurrentUser().getFirstName() + " " + AppState.getCurrentUser().getLastName();
	String currentUsername = AppState.getCurrentUser().getUserName();
	int userId = AppState.getCurrentUser().getUserId();
	AccountService accServer = new AccountService();
	@Override
	public Screen start(BufferedReader br) {
		
		List<Account> allAccounts = accServer.getAccounts(userId);
		
		
		System.out.println("\n\n\n\n\n Welcome");
		System.out.println(fullName);
		System.out.println("Your existing account(s): ");
		
		if(!allAccounts.isEmpty()) {
		for(Account a : allAccounts) {
			System.out.printf("\n Account no: %d \n Account Type: %s \n Account Balance: $%,.2f\n\n ", a.getAccountNo(), a.getAccountType(), a.getAccountBalance() );
		}
		} else {
			System.out.println("\n You currently have no accounts.\n");
		}
		
		System.out.println("Please make selection from options below: ");
		if(AppState.getCurrentUser().getRole().equalsIgnoreCase("ADMIN")) {
			System.out.println("<0> View user with accounts");
		}
		System.out.println("<1> Create new account");
		System.out.println("<2> Remove account");
		System.out.println("<3> Withdraw");
		System.out.println("<4> Deposit");
		System.out.println("<5> Edit user information");
		System.out.println("<6> Delete user");
		System.out.println("<7> Exit to Home");
		System.out.print("Please choose from the options: ");
		
		
		try {
			
			userSelection = br.readLine();
			
			switch(userSelection) {
			
			case "0":
				if(AppState.getCurrentUser().getRole().equalsIgnoreCase("ADMIN")) {
					log.info("Navigating to listed user screen...");
					return new UserList().start(br);
				}else {
					this.start(br);
				}
				break;
			
			case "1":
				log.info("Navigating to new account screen...");
				return new NewAccount().start(br);
			
			case "2":
				log.info("Navigating to remove account screen...");
				return new RemoveAccount().verify(br);
				
			case "3":
				if(accServer.checkAccountCount()) {
				log.info("Navigating to withdraw screen...");
				return new Withdraw().start(br);
				} else {
					log.info("No current existing account to withdraw from.");
					this.start(br);
				}
				
			case "4":
				if(accServer.checkAccountCount()) {
				log.info("Navigating to deposit screen...");
				return new Deposit().start(br);
				} else {
					log.info("No current existing account to deposit to.");
					this.start(br);
				}
				
			case "5":
				log.info("Navigating to edit user...");
				return new EditUser().verify(br);
				
			case "6":
				log.info("Navigating to delete user...");
				return new DeleteUser().verify(br);
				
			case "7":
				log.info("Navigating back to home...");
				AppState.setCurrentUser(null);
				return new Home().start(br);
				
			default:
				log.info("Invalid selection");
				this.start(br);
			}
			
		} catch(Exception e) {
			e.printStackTrace();
			
			log.error(e.getMessage());
			log.warn("Error reading input from console");
			log.info("Restarting application...");
//			AppState.setRestartingApp(true);
//			AppState.setAppRunning(false);
			return new Home().start(br);
		}
		
		
		AppState.setCurrentUser(null);
		return new Home().start(br);
	}

	

}
