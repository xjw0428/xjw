package cn.test.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
/*
 * v1.0
 */
public class JdbcUtils {
	private static Properties props =null;
	
	//只执行一次
	static{
		//给props初始化，加载配置文件
		try {
			InputStream in = JdbcUtils.class.getClassLoader()
					.getResourceAsStream("dbconfig.properties");
			props =new Properties();
			props.load(in);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
		//加载驱动类
		
			try {
				Class.forName(props.getProperty("driverClassName"));
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
	
	}
	public static Connection getConnection() throws SQLException{
	
	return DriverManager.getConnection(props.getProperty("url"),
			props.getProperty("username"), props.getProperty("password"));
	}

}
