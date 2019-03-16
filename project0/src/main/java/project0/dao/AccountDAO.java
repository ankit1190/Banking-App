package project0.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import project0.pojo.Account;
import project0.util.ConnectionFactory;

/**
 * 
 * @author Ankit Patel
 *
 */
public class AccountDAO implements DAO<Account> {

	private static Logger log = Logger.getLogger(AccountDAO.class);
// takes in the account object
// then adds to the database and returns the account object back otherwise returns null
	@Override
	public Account add(Account account) {
		
//		System.out.println("[LOG] - in Accounts DAO add()");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO accounts VALUES (0, ?, ?, ?)", new String[] {"account_no"});
			pstmt.setInt(1, account.getUserId());
			pstmt.setString(2, account.getAccountType());
			pstmt.setDouble(3, account.getAccountBalance());
			
			if(pstmt.executeUpdate() != 0) {
				
				// Retrieve the generated primary key for the newly added user
				ResultSet rs = pstmt.getGeneratedKeys();
				
				
				while(rs.next()) {
					account.setAccountNo(rs.getLong(1));
				}
				
				conn.commit();
				
			}
					
		} catch (SQLIntegrityConstraintViolationException sicve) {
			log.error(sicve.getMessage());
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		if(account.getAccountNo() == 0) return null;
		
		return account;
	}

// updates the account objects
// its for updating the checking and savings account that match the accounts in the list
// adds them back in the list and rewrites the file.
	@Override
	public Account update(Account account) {
//		System.out.println("[LOG] - in Accounts DAO update()");
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE accounts SET account_balance = ? WHERE account_no = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setDouble(1, account.getAccountBalance());
			pstmt.setLong(2, account.getAccountNo());
			
			if(pstmt.executeUpdate() != 0) {
				conn.commit();
				return account;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}

// deletes the account with the same account number
// takes the data from the text file and creates a list of it
// looks for matching account id and type and removes it from the list
	@Override
	public boolean delete(Account account) {
//		System.out.println("[LOG] - in Accounts DAO delete()");
		
		
		long accountNo = account.getAccountNo();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
						
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM accounts WHERE account_no = ?");
			pstmt.setLong(1, accountNo);
			
			if (pstmt.executeUpdate() > 0) {
				conn.commit();
				return true;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return false;
	}

	// uses JDBC statement interface instead of prepared statement.
	/**
	 * 
	 * @return	arraylist of accounts in the database by using SQL statement interface.
	 */
	public ArrayList<Account> getAccountsList(){
		
		ArrayList<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			Statement stmt = conn.createStatement();
			
			
			ResultSet rs = stmt.executeQuery("SELECT * FROM accounts ORDER BY account_balance DESC");
			
			while(rs.next()) {
				
				Account account = new Account();
				account.setUserId(rs.getInt("user_id"));
				account.setAccountNo(rs.getLong("account_no"));
				account.setAccountType(rs.getString("account_type"));
				account.setAccountBalance(rs.getDouble("account_balance"));
				

				accounts.add(account);
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return accounts;
	}
// returns an ArrayList of filtered accounts associated with the given userId.
	/**
	 * 
	 * @param userId
	 * @return	arraylist of accounts that are associated with the userID otherwise returns empty arraylist.
	 */
	public ArrayList<Account> getAccounts(int userId){
//		System.out.println("[LOG] - in Accounts DAO getAccounts()");
		
		ArrayList<Account> accounts = new ArrayList<>();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
						
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM accounts WHERE user_id = ?");
			pstmt.setInt(1, userId); 
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				
				Account account = new Account();
				account.setUserId(rs.getInt("user_id"));
				account.setAccountNo(rs.getLong("account_no"));
				account.setAccountType(rs.getString("account_type"));
				account.setAccountBalance(rs.getDouble("account_balance"));
				
//				System.out.println(account);
				accounts.add(account);
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return accounts;
	}

// returns an account by given account number
	/**
	 * 
	 * @param accountNo
	 * @return	account object associated with account number otherwise returns null
	 */
	public Account getAccountById(long accountNo) {
//		System.out.println("[LOG] - in Accounts DAO getAccountById()");
		
		Account account = new Account();;
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM accounts WHERE account_no = ?");
			pstmt.setLong(1, accountNo);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				account.setUserId(rs.getInt("user_id"));
				account.setAccountType(rs.getString("account_type"));
				account.setAccountBalance(rs.getDouble("account_balance"));
				
				return account;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}
}
