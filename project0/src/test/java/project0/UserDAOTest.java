package project0;

import org.junit.Test;

import org.junit.Assert;
import project0.dao.UserDAO;

public class UserDAOTest {

	@Test
	public void testCheckUser() {
		
		UserDAO uDao = new UserDAO();
		
		Assert.assertEquals(true, uDao.checkUser("tomcat"));
		
	}
	
	@Test
	public void testCheckUser1() {
		
		UserDAO uDao = new UserDAO();
		
		Assert.assertEquals(true, uDao.checkUser("toMcat"));
		
	}
	
	
	
}
