package cn.bookstore.user.service;

import cn.bookstore.user.dao.UserDao;
import cn.bookstore.user.domain.User;

public class UserService {
   private UserDao userDao = new UserDao();
   
   public void regist(User form) throws UserException{
	   User user = userDao.findByUsername(form.getUsername());
	   if(user!=null)throw new UserException("�û����Ѵ��ڣ�");
	   
	   user = userDao.findByEmail(form.getEmail());
	   if(user!=null)throw new UserException("Email�Ѵ��ڣ�");
	   
	   userDao.add(form);
	   
   }  
	   /**
		 * �����
		 * @throws UserException 
		 */

		   

public void active(String code) throws UserException {
	/*
	 * 1. ʹ��code��ѯ���ݿ⣬�õ�user
	 */
	User user = userDao.findByCode(code);
	/*
	 * 2. ���user�����ڣ�˵�����������
	 */
	if(user == null) throw new UserException("��������Ч��");
	/*
	 * 3. У���û���״̬�Ƿ�Ϊδ����״̬������Ѽ��˵���Ƕ��μ���׳��쳣
	 */
	if(user.isState()) throw new UserException("���Ѿ�������ˣ���Ҫ�ټ����ˣ�");
	
	/*
	 * 4. �޸��û���״̬
	 */
	userDao.updateState(user.getUid(), true);
	
}
/**
 * ��¼����
 * @param form
 * @return
 * @throws UserException 
 */
public User login(User form) throws UserException {
	/*
	 * 1. ʹ��username��ѯ���õ�User
	 * 2. ���userΪnull���׳��쳣���û��������ڣ�
	 * 3. �Ƚ�form��user�����룬����ͬ���׳��쳣���������
	 * 4. �鿴�û���״̬����Ϊfalse���׳��쳣����δ���
	 * 5. ����user
	 */
	User user = userDao.findByUsername(form.getUsername());
	if(user == null) throw new UserException("�û��������ڣ�");
	if(!user.getPassword().equals(form.getPassword()))
		throw new UserException("�������");
	//if(!user.isState()) throw new UserException("��δ���");
	
	return user;
}
}
