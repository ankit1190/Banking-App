package project0.service;

import java.util.ArrayList;
import java.util.List;

import project0.dao.AccountDAO;
import project0.pojo.Account;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */

public class AccountService {

	private AccountDAO accountDao = new AccountDAO();
	
	// returns the filtered accounts of the userId.
	/**
	 * 
	 * @param userId
	 * @param accountType
	 * @return list of user's accounts by type otherwise empty ArrayList.
	 */
		public List<Account> getAccountsByType(int userId, String accountType){
			
			ArrayList<Account> accounts = accountDao.getAccounts(userId);
			ArrayList<Account> result = new ArrayList<>();
			
			for(Account a : accounts) {
				if(a.getUserId() == userId && a.getAccountType().equals(accountType)) {
					result.add(a);
				}
			}
			
			return result;
		}

	// gets all the accounts for the user.
		/**
		 * 
		 * @param userId
		 * @return arrayList of user's accounts otherwise returns a null
		 */
		public List<Account> getAccounts(int userId){
//			System.out.println("[LOG] - in Accounts Service getAccounts()");
			ArrayList<Account> accounts = accountDao.getAccounts(userId);
			ArrayList<Account> result = new ArrayList<>();
			
			for(Account a : accounts) {
				if(a.getUserId() == userId) {
					result.add(a);
					
				} 
				
			}
			
			return result;
		}
		
	// get the account from account list with given account number
		/**
		 * 
		 * @param accountNo
		 * @return an account with given account number otherwise null
		 */
		public Account getAccount(long accountNo) {
			
			List<Account> accounts = getAccounts(AppState.getCurrentUser().getUserId());
			
			for(Account a : accounts) {
				if(a.getAccountNo() == accountNo) {
					return a;
				}
			}
			
			return null;
		}
		
	// update the account
		/**
		 * 
		 * @param account
		 * @return returns updated account
		 */
		public Account updateAccount(Account account) {
			
			if(AppState.getCurrentUser().getUserId() == account.getUserId()) {
				
				accountDao.update(account);
				
			}
			
			return account;
		}
		
	// withdraw from account and updates the account info.
		/**
		 * 
		 * @param account
		 * @param withdrawAmt
		 * @return returns true if the account has been updated with new value otherwise returns false
		 */
		public boolean withdrawAmount(Account account, double withdrawAmt) {
			
			double initialAmt = account.getAccountBalance();
			double finalAmt;
			
			if(withdrawAmt > 0) {
				if(AppState.getCurrentUser().getUserId() == account.getUserId()) {
					
					finalAmt = initialAmt - withdrawAmt;
					if(finalAmt > 0) {
						account.setAccountBalance(finalAmt);
						updateAccount(account);
						return true;
					}
				}
			}
					
			return false;
		}

	// deposit to account and updates the account info.
		/**
		 * 
		 * @param account
		 * @param depositAmt
		 * @return returns true if the account has been updated with new value otherwise returns false
		 */
		public boolean depositAmount(Account account, double depositAmt) {
			
			double initialAmt = account.getAccountBalance();
			double finalAmt;
			
			if(depositAmt > 0) {
				if(AppState.getCurrentUser().getUserId() == account.getUserId()) {
					finalAmt = initialAmt + depositAmt;
					
					if(finalAmt > 0) {
						account.setAccountBalance(finalAmt);
						updateAccount(account);
						return true;
					}
				}
			}
			
			return false;
		}
		
	// check if a user has atleast one account
		/**
		 * 
		 * @return true if the current user has accounts otherwise returns false.
		 */
		public boolean checkAccountCount() {
//			System.out.println("[LOG] - in Accounts Service checkAccountCount()");
			int userId = AppState.getCurrentUser().getUserId();
			ArrayList<Account> accounts = accountDao.getAccounts(userId);
			if(accounts.size() > 0) {
				return true;
			}
			
			
			return false;
		}

	// create new account for the present user
		/**
		 * 
		 * @param accountType
		 * @param amount
		 * @return newly created account otherwise returns null
		 */
		public Account createNewAccount(String accountType, double amount) {
//			System.out.println("[LOG] - in Accounts Service createNewAccount()");
			int userId = AppState.getCurrentUser().getUserId();
			if(accountType.equalsIgnoreCase("checking") || accountType.equalsIgnoreCase("saving") && amount > 0) {
					
				Account newAccount = new Account(userId, accountType, amount);
				accountDao.add(newAccount);
				
				return newAccount;
			}
					
			return null;
		}
		
	// deletes the account with given username and password and chosen account no.
		/**
		 * 
		 * @param uName
		 * @param pass
		 * @param AccountNo
		 * @return true if the account has been deleted otherwise returns false.
		 */
		public boolean removeAccount(String uName, String pass, long AccountNo) {
//			System.out.println("[LOG] - in Accounts Service removeAccount()");
			
			List<Account> accounts = getAccounts(AppState.getCurrentUser().getUserId());
			
			if(AppState.getCurrentUser().getUserName().equals(uName) && AppState.getCurrentUser().getPassword().equals(pass)) {
				
				for(Account a : accounts) {
					if(a.getAccountNo() == AccountNo) {
						if(accountDao.delete(a)) {
							return true;
						}
					}
				}
				
			}
			
			return false;
		}
}
