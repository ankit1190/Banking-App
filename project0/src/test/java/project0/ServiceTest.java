package project0;

import org.junit.Assert;
import org.junit.Test;

import project0.pojo.User;
import project0.service.UserService;

public class ServiceTest {

	
// tests if the user object is retrieved
	@Test
	public void testGetUserByCredentials() {
		
		UserService test = new UserService();
		//1:Ankit:Patel:tomcat:pass:Admin
		User user = new User(1, "Ankit", "Patel", "tomcat", "pass", "Admin");
		User testUser = test.getUserByCredentials("tomcat", "pass");
		
		
		Assert.assertTrue("true they are equal", user.equals(testUser));
		
	}
	
	// tests if the methods returns false with empty string input
		@Test
		public void testGetUserByCredentials1() {
			
			UserService test = new UserService();
			//1:Ankit:Patel:tomcat:pass:Admin
			User user = new User(1, "Ankit", "Patel", "tomcat", "pass", "Admin");
			User testUser = test.getUserByCredentials("", "pass");
			
			
			Assert.assertFalse("true they are equal", user.equals(testUser));
			
			testUser = null;
			
		}
		
	//tests case-sensitivity of the method
		@Test
		public void testGetUserByCredentials2() {
			
			UserService test = new UserService();
			//1:Ankit:Patel:tomcat:pass:Admin
			User user = new User(1, "Ankit", "Patel", "tomcat", "pass", "Admin");
			User testUser = test.getUserByCredentials("tomcat", "Pass");
			
		
			Assert.assertFalse("true they are equal", user.equals(testUser));
			
			testUser = null;
			
		}
		
		
	// this test needs to be worked on
		@Test
		public void testCreateNewUser() {
			
			UserService test = new UserService();
			//1:Ankit:Patel:tomcat:pass:Admin
			User testUser = test.createNewUser("Ankit", "pat28", "tomcat", "pass");
			
			Assert.assertEquals(null, testUser);
			
			testUser = null;
		}
		
		@Test
		public void testCreateNewUser1() {
			
			UserService test = new UserService();
			//1:Ankit:Patel:tomcat:pass:Admin
			User testUser = test.createNewUser("Ankit", "pat28", "mnm", "pass");
			User user = new User(3, "Ankit", "pat28", "mnm", "pass", "client");
			
			Assert.assertTrue(user.equals(testUser));
			
			testUser = null;
		}
		
		@Test
		public void testCreateNewUser2() {
			
			UserService test = new UserService();
			//1:Ankit:Patel:tomcat:pass:Admin
			User testUser = test.createNewUser("", "pat28", "nnn", "pass");
			User user = new User(4, "Ankit", "pat28", "mnm", "pass", "client");
			
			Assert.assertFalse(user.equals(testUser));
			
			testUser = null;
			test = null;
		}
		
		
	
}
