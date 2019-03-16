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
public class Withdraw implements Screen{
	
	private static Logger log = Logger.getLogger(Withdraw.class);

	AccountService accServer = new AccountService();
	long accountNo;
	double withdrawAmt;
	List<Account> allAccounts = accServer.getAccounts(AppState.getCurrentUser().getUserId());
	
	@Override
	public Screen start(BufferedReader br) {
				
		
		for(Account a : allAccounts) {
			System.out.printf("\n(%s)\tAccount No.: %d\tAccount Balance: $%,.2f\n", a.getAccountType(), a.getAccountNo(), a.getAccountBalance());
		}
		
		try {
			
			System.out.print("\n\n\nType in the account number you want to withdraw from: ");
			
			accountNo = Long.parseLong(br.readLine());
			
			Account chosenAccount = accServer.getAccount(accountNo);
			
			
			if(!chosenAccount.equals(null)) {
				
				System.out.print("How much would you like to withdraw: ");
				withdrawAmt = Double.parseDouble(br.readLine());
				
					if(withdrawAmt > 0) {
						if(accServer.withdrawAmount(chosenAccount, withdrawAmt)) {
							return new Dashboard().start(br);
							
						}else {
							log.warn("You entered invalid amount.");
							log.warn("Or you tried to overdraft.");
							log.info("Navigating back to dashboard.");
							return new Dashboard().start(br);
						}
					} else {
						log.warn("\n entered negative amount.");
						log.info("Navigating back to deposit screen");
						this.start(br);
					}
				
			}else {
				log.info("Wrong account chosen.");
				this.start(br);
			}
			
		} catch (NumberFormatException e) {
			log.warn("Non-numeric value entered.");	
			log.error(e.getCause());
			log.error(e.getMessage());
			log.warn("Error in Withdraw numberformatexception Screen");
			this.start(br);
		} catch (IOException e) {
			
//			e.printStackTrace();
//			System.out.println(e.getCause());
			log.error(e.getMessage());
			log.warn("Error in Withdraw Screen");
			
		}
		
		
		return this.start(br);
	}

	

	
}
