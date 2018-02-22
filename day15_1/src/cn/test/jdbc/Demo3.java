package cn.test.jdbc;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

/*
 * PrepareStatement��SQL����
 */
public class Demo3 {
/*
 * ��¼
 * ͨ��username��password��ѯ����
 */
	public boolean login(String username,String password) throws Exception{
		//�Ĵ����
		   String driverClassName="com.mysql.jdbc.Driver";
	       String url="jdbc:mysql://localhost:3308/xjw";
		   String mysqlusername="root";
		   String mysqlpassword="mysql";
		   
		   //���������࣬�õ�����
		   Class.forName(driverClassName);
		   Connection con=DriverManager.getConnection(url, mysqlusername, mysqlpassword);
		   
		
		   Statement stmt = con.createStatement();
		   ResultSet rs= stmt.executeQuery
				   ("select * from t_user where username='"+ username +"' and password='"+ password +"'");
	       return rs.next();
	       
	}
	
	@Test
	public void fun1() throws Exception{
		boolean bool = login("lisi","1qqqq23");
		System.out.println(bool);
	}
	
	
	public boolean login2(String username,String password) throws Exception{
		//�Ĵ����
		   String driverClassName="com.mysql.jdbc.Driver";
	       String url="jdbc:mysql://localhost:3308/xjw";
		   String mysqlusername="root";
		   String mysqlpassword="mysql";
		   
		   //���������࣬�õ�����
		   Class.forName(driverClassName);
		   Connection con=DriverManager.getConnection(url, mysqlusername, mysqlpassword);
		   
		   String sql = "select * from t_user where username=? and password=?";
		   PreparedStatement pstmt = con.prepareStatement(sql);
		   pstmt.setString(1, username);
		   pstmt.setString(2, password);
		   ResultSet rs= pstmt.executeQuery();
					  
	       return rs.next();
		    
	}
	@Test
	public void fun2() throws Exception{
		boolean bool = login2("lisi","123");
		System.out.println(bool);
	}
	
	//����JdbcUtils.getConnection()
	
	@Test
	public void fun3() throws SQLException {
		Connection con = JdbcUtils.getConnection();
		System.out.println(con);
	}
	
}
