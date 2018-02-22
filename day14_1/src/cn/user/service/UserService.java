package cn.user.service;

import cn.user.dao.DaoFactory;
import cn.user.dao.UserDao;
import cn.user.domain.User;



public class UserService {
	//把具体实现类的创建隐藏到工厂中
		private UserDao userDao=DaoFactory.getUserDao();
		
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
