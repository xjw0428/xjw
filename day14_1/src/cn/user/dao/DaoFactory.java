package cn.user.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class DaoFactory {
	private static Properties props =null;
	
	static{
		//加载配置文件内容到props对象中
		InputStream in=DaoFactory.class.getClassLoader().getResourceAsStream("dao.properties");
		 props = new Properties();
		try {
			props.load(in);
		} catch (IOException e) {
		   throw new RuntimeException(e);
		}
	}
/*
 * 返回一个具体UserDao实现类对象
 */
	public static UserDao getUserDao(){
		/*
		 * 给出一个配置文件，文件中给出UserDao接口的实现类名称
		 * 从文件中获取类名，通过反射完成创建对象
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
