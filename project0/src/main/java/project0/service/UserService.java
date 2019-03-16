package project0.service;



import java.util.Set;

import project0.dao.UserDAO;
import project0.pojo.User;
import project0.util.AppState;

/**
 * 
 * @author Ankit Patel
 *
 */
public class UserService {

	private UserDAO userDao = new UserDAO();
	
	
// gets the user object from DAO ( it is case-sensitive) if the user doesn't exist then
// returns null
	/**
	 * 
	 * @param userName
	 * @param password
	 * @return	the user object that matches the usename and password provided otherwise returns null.
	 */
	public User getUserByCredentials(String userName, String password) {
		
//		System.out.println("[LOG] - in getUserByCredentials \n");
		
		if(!userName.equals("") && !password.equals("")) {
			return userDao.getByCredentials(userName, password);
		}
		
		return null;
	}
	
// create new user object takes in the fields and creates a new user object then passes it 
// to DAO layer for adding the user to the file
	/**
	 * 
	 * @param fName
	 * @param lName
	 * @param uName
	 * @param password
	 * @return	newly created user that has been added successfully to the database otherwise returns null
	 */
	public User createNewUser(String fName, String lName, String uName, String password) {
//		System.out.println("[LOG] - in User Service createNewUser()");
		
		if(!fName.isEmpty() || !fName.equals(" ") && !lName.isEmpty() || !lName.equals(" ") && !uName.isEmpty() || !uName.equals(" ") && !password.isEmpty() || !password.equals(" ")) {
//			System.out.println("Before checkUser()");
			if(!userDao.checkUser(uName)) {
				
				
				User user = new User(fName, lName, uName, password, "CLIENT");
//				System.out.println("\n\n[LOG] - in User Service createNewUser()");
				User newUser = userDao.add(user);
//				System.out.println("returning added user");
				if(newUser != null)return newUser;
			}
		}
		
//		System.out.println("\n\n User Service createNewUser() returning null \n\n\n");
		return null;
	}

//updates the current user object with the valid fields
	/**
	 * 
	 * @param fName
	 * @param lName
	 * @param uName
	 * @param password
	 * @return	current user object with updated fields otherwise return null
	 */
	public User updateUser(String fName, String lName, String uName, String password) {
		
//		System.out.println("[LOG] - User Service updateUser()");
		
		if(fName != "" && lName != "" && uName != "" && password != "") {
			if(userDao.checkUser(AppState.getCurrentUser().getUserName())) {
				
				
				AppState.getCurrentUser().setFirstName(fName);
				AppState.getCurrentUser().setLastName(lName);
				AppState.getCurrentUser().setUserName(uName);
				AppState.getCurrentUser().setPassword(password);
//			System.out.println("\n\n\n" + AppState.getCurrentUser());
				userDao.update(AppState.getCurrentUser());
				
				return AppState.getCurrentUser();
				
			}
		}
		
		return null;
	}

// deletes the user if the username and password given match the current user that is about to be deleted.
	/**
	 * 
	 * @param uName
	 * @param pass
	 * @return true if the user object is deleted otherwise returns false.
	 */
	public boolean removeUser(String uName, String pass) {
		
		if(AppState.getCurrentUser().getUserName().equals(uName) && AppState.getCurrentUser().getPassword().equals(pass)) {
			
			if(userDao.delete(AppState.getCurrentUser())) {
				return true;
			}
			
		}
		
		return false;
	}

// verify user
	/**
	 * 
	 * @param uName
	 * @param pass
	 * @return true if the user input fields are verified to match the credentials of current user
	 */
	public boolean verifyUser(String uName, String pass) {
		
		if(AppState.getCurrentUser().getUserName().equals(uName) && AppState.getCurrentUser().getPassword().equals(pass)) {
			return true;
		}
		return false;
	}
	
	
	public Set<User> getTopUsers(){
		
		if(AppState.getCurrentUser().getRole().equalsIgnoreCase("ADMIN")) return userDao.getAll();
			
		return null;
	}
}
