package cn.test.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.Test;



public class Demo1 {

	@Test
   public void fun1() throws ClassNotFoundException, SQLException{
	   Class.forName("com.mysql.jdbc.Driver");//加载驱动类
	   String url="jdbc:mysql://localhost:3308/xjw";
	   String username="root";
	   String password="mysql";
	   
	   //得到连接对象
	   Connection con=DriverManager.getConnection(url, username, password);
        System.out.println(con);
	}

}
