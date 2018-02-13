package test.dao;

import org.junit.Test;

import cn.user.dao.UserDao;
import cn.user.domain.User;

public class UserDaoTest {

	
   @Test
   public void testAdd(){
	   UserDao userDao=new UserDao();
	   User user=new User();
	   user.setUsername("ÍõÎå");
	   user.setPassword("234");
	   userDao.add(user);
   } 
   
   
   @Test
   public void testFindByUsername(){
	   UserDao userDao=new UserDao();
	   User user=userDao.findByUsername("ÕÔËÄ");
	   System.out.println(user);
   }
   

	

}
