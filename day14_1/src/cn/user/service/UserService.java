package cn.user.service;

import cn.user.dao.DaoFactory;
import cn.user.dao.UserDao;
import cn.user.domain.User;



public class UserService {
	//�Ѿ���ʵ����Ĵ������ص�������
		private UserDao userDao=DaoFactory.getUserDao();
		
		public void regist(User user) throws UserException{
			User duser =userDao.findByUsername(user.getUsername());
			
			if(duser!=null)throw new UserException("�û���"+user.getUsername()+"��ע�ᣡ");
			
			userDao.add(user);
		}
		
		public User login(User form) throws UserException{
			User luser =userDao.findByUsername(form.getUsername());
			if(luser==null)throw new UserException("�û���������!");
			
			if(luser.getPassword().equals(form.getPassword())){
				return luser;//���������û���Ϣ
			}else throw new UserException("�������!");
		}
}
