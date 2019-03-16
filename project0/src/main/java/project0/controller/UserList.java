package project0.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import project0.pojo.Account;
import project0.pojo.User;
import project0.service.AccountService;
import project0.service.UserService;
import project0.util.AppState;

public class UserList implements Screen{

	private static Logger log = Logger.getLogger(UserList.class);
	
	@Override
	public Screen start(BufferedReader br) {
		if(AppState.getCurrentUser().getRole().equalsIgnoreCase("ADMIN")) {
			UserService uServer = new UserService();
			AccountService accServer = new AccountService();
			Set<User> userSet = uServer.getTopUsers();
			List<Account> accList = new ArrayList<>();
			
			for (User u : userSet) {
				accList = accServer.getAccounts(u.getUserId());
				System.out.println("\nName: " + u.getFirstName() + " " + u.getLastName());
				System.out.println("Username: " + u.getUserName());
				for(Account a : accList) {
					System.out.printf("\n(%s) \tAccount No.: %d \tAccount Balance: $%,.2f\n", a.getAccountType(), a.getAccountNo(), a.getAccountBalance());
				}
			}
			
			System.out.println("Type anything to exit back to dashboard");
			
			try {
				String input = br.readLine();
				if(input.matches(" ")) {
					System.out.println("Assuming you accidentally pressed space bar and then pressed enter accidentally.");
					this.start(br);
				}
			} catch (IOException e) {
//				e.printStackTrace();
				log.error(e.getCause());
				log.error(e.getMessage());
				log.warn("Error in user list Screen");
			}
								
		}
		return new Dashboard().start(br);
	}

}
