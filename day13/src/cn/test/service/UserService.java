package cn.test.service;

import cn.test.dao.UserDao;
import cn.test.domain.User;

public class UserService {
	//service≤„“¿¿µdao≤„
	private UserDao userDao=new UserDao();
	
   public User find(){
	   return userDao.find();
   }
}
