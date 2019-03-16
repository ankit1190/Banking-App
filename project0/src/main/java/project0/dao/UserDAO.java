package project0.dao;



import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashSet;
import java.util.Set;

import org.apache.log4j.Logger;

import oracle.jdbc.internal.OracleTypes;

//import org.apache.log4j.Logger;

import project0.pojo.User;
import project0.util.ConnectionFactory;

/**
 * 
 * @author Ankit Patel
 *
 */
public class UserDAO implements DAO<User> {
	
	private static Logger log = Logger.getLogger(UserDAO.class);
	/**
	 * 
	 * @return returns set of users that have accounts
	 */
	public Set<User> getAll() {
		
		Set<User> userSet = new HashSet<>();
		
		try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

			CallableStatement cstmt = conn.prepareCall("{CALL highest_acct_holder(?)}");
			cstmt.registerOutParameter(1, OracleTypes.CURSOR);
			cstmt.execute();

			ResultSet rs = (ResultSet) cstmt.getObject(1);
			while (rs.next()) {
				User user = new User();
		
				user.setUserId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword("***");
				user.setRole(rs.getString("role"));
				userSet.add(user);
			}

//			books = this.mapResultSet(rs);

		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return userSet;

	}

// inserts the user object passed into the method and returns the newly added user object.
	@Override
	public User add(User user) {
		
//		System.out.println("[LOG] - in add() \n");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("INSERT INTO users VALUES (0, ?, ?, ?, ?, ?)", new String[] {"user_id"});
			pstmt.setString(1, user.getFirstName());
			pstmt.setString(2, user.getLastName());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getPassword());
			pstmt.setString(5, user.getRole());
			
			if(pstmt.executeUpdate() != 0) {
				
				// Retrieve the generated primary key for the newly added user
				ResultSet rs = pstmt.getGeneratedKeys();
				
				
				while(rs.next()) {
					user.setUserId(rs.getInt(1));
//					System.out.println("\n\nAfter getting user id" + user.getUserId());
				}
				
				conn.commit();
//				System.out.println("\n\nAfter getting user id" + user.getUserId());
			}
					
		} catch (SQLIntegrityConstraintViolationException sicve) {
			log.error(sicve.getMessage());
			System.out.println("[WARN] - Username already taken.");
		} catch (SQLException e) {
			log.error(e.getMessage());
			return null;
		}
		
		if(user.getUserId() == 0) return null;
//		System.out.println(user);
		return user;
	}

// gets the user by credentials otherwise it would return null
	/**
	 * 
	 * @param username
	 * @param password
	 * @return user object that matches the username and password provided otherwise returns null.
	 */
	public User getByCredentials(String username, String password) {
			
//		System.out.println("[LOG] - in getByCredentials() \n");
		
		User user = new User();
		
		try(Connection conn  = ConnectionFactory.getInstance().getConnection()){
			
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE user_name = ? AND password = ?");
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				user.setUserId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				
				return user;
			}
			
		} catch (SQLException e) {
			
			log.error(e.getMessage());
		}
					
		return null;
	}

// updating user that is already existing
// take in the user object
// get the list of users from getAll() then cycle through them to look for matching user account
// number. Then set the other details with the input user objects details.
// afterwards call the updateAll() to rewrite the file.
	public User update(User user) {
		
//		System.out.println("[LOG] - in update() \n");
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			conn.setAutoCommit(false);
			
			String sql = "UPDATE users SET password = ?, first_name = ?, last_name = ?, user_name = ? WHERE user_id = ?";
			
			PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, user.getPassword());
			pstmt.setString(2, user.getFirstName());
			pstmt.setString(3, user.getLastName());
			pstmt.setString(4, user.getUserName());
			pstmt.setInt(5, user.getUserId());
			
			if(pstmt.executeUpdate() != 0) {
				conn.commit();
				return user;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}

// removing user 
// it would go through the service layer's validation whether user has right to delete
// guess it would get the list from text file and look for equal objects and remove the object
// from the list. After that calls the updateAll() to rewrite the file.
	public boolean delete(User user) {
		
//		System.out.println("[LOG] - in delete() \n");
		
		int userId = user.getUserId();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
						
			conn.setAutoCommit(false);
			
			PreparedStatement pstmt = conn.prepareStatement("DELETE FROM users WHERE user_id = ?");
			pstmt.setInt(1, userId);
			
			if (pstmt.executeUpdate() > 0) {
				conn.commit();
				return true;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return false;
	}

	/**
	 * 
	 * @param username
	 * @return	true if user exists otherwise returns false
	 */
	public boolean checkUser(String username) {
		
//		System.out.println("[LOG] - in checkUser() \n");
//		System.out.println(username);
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
						
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE user_name = ?");
			pstmt.setString(1, username);
			
			if (pstmt.executeUpdate() > 0) {
				return true;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return false;
	}

// retrieves user object with username
	/**
	 * 
	 * @param username
	 * @return	user object that matches the input username otherwise returns null
	 */
	public User getUserbyUsername(String username) {
		
		User user = new User();
		
		try(Connection conn = ConnectionFactory.getInstance().getConnection()) {
			
			PreparedStatement pstmt = conn.prepareStatement("SELECT * FROM users WHERE username = ?");
			pstmt.setString(1, username);
			
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()) {
				user.setUserId(rs.getInt("user_id"));
				user.setFirstName(rs.getString("first_name"));
				user.setLastName(rs.getString("last_name"));
				user.setUserName(rs.getString("user_name"));
				user.setPassword(rs.getString("password"));
				user.setRole(rs.getString("role"));
				
				return user;
			}
			
		} catch (SQLException e) {
			log.error(e.getMessage());
		}
		
		return null;
	}

}

