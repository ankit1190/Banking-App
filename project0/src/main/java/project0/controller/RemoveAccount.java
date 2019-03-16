package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;

import project0.pojo.Account;
import project0.service.AccountService;
import project0.service.UserService;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class RemoveAccount implements Screen{
	
	private static Logger log = Logger.getLogger(RemoveAccount.class);

	AccountService accServer = new AccountService();
	UserService uServer = new UserService();
	String username;
	String password;
	long accountNo;
	
	@Override
	public Screen start(BufferedReader br) {
		
		System.out.println("Removing Account");
		
		
		try {
			
				List<Account> allAccounts = accServer.getAccounts(AppState.getCurrentUser().getUserId());
				
				for(Account a : allAccounts) {
					System.out.println("Account number: " + a.getAccountNo() + " | " + a.getAccountType() + " | $" + a.getAccountBalance());
				}
				
				System.out.println("\n\nPlease type in the Account Number that you would like to delete");
				System.out.print("Account No: ");
				accountNo = Long.parseLong(br.readLine());
				
				if(accServer.removeAccount(username, password, accountNo)) {
					return new Dashboard().start(br);
				} else {
					System.out.println("\n\n\nCouldn't find the account");
					System.out.println("Returning to Dashboard...");
					return new Dashboard().start(br);
				}
				
		} catch (IOException e) {		
//			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
			log.warn("error in Remove Account Screen");
		} catch (NumberFormatException nfe) {
			log.warn("\n\nInvalid numeric value entered.");
		}
			
		return this.start(br);
	}

	/**
	 * 
	 * @param br
	 * @return to the remove account screen otherwise returns back to the dashboard.
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
				log.warn("\n\nInvalid credentials.");
				
			}
		} catch (IOException e) {
			
//			e.printStackTrace();
			log.error(e.getCause());
			log.error(e.getMessage());
			log.warn("error in Remove Account Verify Screen");
		}catch (NumberFormatException nfe) {
			log.warn("\n\nInvalid numeric value entered.");
			return new Dashboard().start(br);
		}
		
		
		return new Dashboard().start(br);
	}
	
}
