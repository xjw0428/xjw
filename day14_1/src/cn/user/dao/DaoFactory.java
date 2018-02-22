package cn.user.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	private static Properties props =null;
	
	static{
		//���������ļ����ݵ�props������
		InputStream in=DaoFactory.class.getClassLoader().getResourceAsStream("dao.properties");
		 props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
		   throw new RuntimeException(e);
		}
	}
/*
 * ����һ������UserDaoʵ�������
 */
	public static UserDao getUserDao(){
		/*
		 * ����һ�������ļ����ļ��и���UserDao�ӿڵ�ʵ��������
		 * ���ļ��л�ȡ������ͨ��������ɴ�������
		 */
		String daoClassName=props.getProperty("cn.user.dao.UserDao");
		
		try{
		Class clazz=Class.forName(daoClassName);
		
		return (UserDao)clazz.newInstance();
		}catch(Exception e){
			  throw new RuntimeException(e);
		}
	}
}
