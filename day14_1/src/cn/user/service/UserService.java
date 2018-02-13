package cn.user.service;

import cn.user.dao.UserDao;
import cn.user.domain.User;



public class UserService {
	//service层依赖dao层
		private UserDao userDao=new UserDao();
		
		public void regist(User user) throws UserException{
			User duser =userDao.findByUsername(user.getUsername());
			
			if(duser!=null)throw new UserException("用户名"+user.getUsername()+"已注册！");
			
			userDao.add(user);
		}
		
		public User login(User form) throws UserException{
			User luser =userDao.findByUsername(form.getUsername());
			if(luser==null)throw new UserException("用户名不存在!");
			
			if(luser.getPassword().equals(form.getPassword())){
				return luser;//返回所有用户信息
			}else throw new UserException("密码错误!");
		}
}
