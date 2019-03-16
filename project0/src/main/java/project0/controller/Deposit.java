package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;
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
public class Deposit implements Screen{
	
	private static Logger log = Logger.getLogger(Deposit.class);
	
	AccountService accServer = new AccountService();
	long accountNo;
	double depositAmt;

	@Override
	public Screen start(BufferedReader br) {
		
		List<Account> allAccounts = accServer.getAccounts(AppState.getCurrentUser().getUserId());
		
		
		for(Account a : allAccounts) {
			System.out.printf("\n(%s)\tAccount No.: %d\tAccount Balance: $%,.2f\n", a.getAccountType(), a.getAccountNo(), a.getAccountBalance());
		}
		
		
		try {
			
			System.out.print("\n\n\nType in the account number you want to deposit to: ");
			accountNo = Math.abs(Long.parseLong(br.readLine()));
			
			Account chosenAccount = accServer.getAccount(accountNo);
			
			if(chosenAccount != null) {
			System.out.print("\nHow much would you like to deposit: ");
			depositAmt = Double.parseDouble(br.readLine());
			
				if(depositAmt > 0) {
					if(accServer.depositAmount(chosenAccount, depositAmt)) {
						return new Dashboard().start(br);
						
					}else {
						System.out.println("\n\nYou entered invalid amount.");
						System.out.println("Navigating back to dashboard.");
						return new Dashboard().start(br);
					} 
				}else {
					System.out.println("\n entered negative amount.");
					System.out.println("Navigating back to deposit screen");
					this.start(br);
				}
			
			}else {
				System.out.println("\nWrong account chosen.");
				this.start(br);
			}
			
		} catch (IOException e) {
//			e.printStackTrace();
			log.error(e.getMessage());
			log.warn("error in Deposit Screen");
		} catch (NumberFormatException nfe) {
			log.warn("\nInvalid numeric value inserted");
			log.info("Navigating back to Dashboard...");
			return new Dashboard().start(br);
		}
		
		return this.start(br);
	}

}
