package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.Logger;

import project0.pojo.Account;
import project0.service.AccountService;

/**
 * 
 * @author Ankit Patel
 *
 */
public class NewAccount implements Screen{
	
	private static Logger log = Logger.getLogger(NewAccount.class);
	
	AccountService accServer = new AccountService();

	@Override
	public Screen start(BufferedReader br) {
		System.out.println("\n\n\n\n\n\n Thank you for creating New Account");
		String accountSelection;
		String accountType = "";
		String selection;
		double primaryAmt = 0;
		
		try {
			
			System.out.println("What account would you like to create?");
			System.out.println("<1> Checking");
			System.out.println("<2> Saving");
			System.out.print("Type 1 or 2 from two options above: ");
			
			accountSelection = br.readLine();
			
			if(accountSelection.equals("1") || accountSelection.equals("2")) {
				System.out.print("Would you like to put any money at the moment?<y> or <n>: ");
				selection = br.readLine();
				
					if(selection.equalsIgnoreCase("y")) {
						
						System.out.print("How much amount would you like to enter?");
						primaryAmt = Double.parseDouble(br.readLine());
						
					} else if(selection.equalsIgnoreCase("n")) {
						
						primaryAmt = 0.00d;
						
					}else {
						System.out.println("Invalid entry!");
					}
				
				if(accountSelection.equals("1")) accountType = "checking";
				else if(accountSelection.equals("2")) accountType = "saving";
				
				Account newAccount = accServer.createNewAccount(accountType, primaryAmt);
				
				if(!newAccount.equals(null)) {
					
					System.out.println("new account was successfully created.");
					
					return new Dashboard().start(br);	
				}else {
					
					System.out.println("Account could not be created.");
					System.out.println("Navigating back to dashboard");
					
					return new Dashboard().start(br);
					
				}
				
			}else {
				System.out.println("Invalid entry!");
			}
			
			
		} catch (NumberFormatException nfe) {
			log.warn("A non numeric value was entered!");
		} catch (IOException e) {
//			e.printStackTrace();
			log.error(e.getMessage());
			log.warn("error in New Account Screen");
		}
		
		return this.start(br);
	}

}
