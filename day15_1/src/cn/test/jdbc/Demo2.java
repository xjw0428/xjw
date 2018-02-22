package cn.test.jdbc;
/*
 * 增删改查
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.Test;

public class Demo2 {
    @Test
	public void fun1() throws ClassNotFoundException, SQLException{
		//四大参数
	   String driverClassName="com.mysql.jdbc.Driver";
       String url="jdbc:mysql://localhost:3308/tf11";
	   String username="root";
	   String password="mysql";
	   
	   //加载驱动类，得到连接
	   Class.forName(driverClassName);
	   Connection con=DriverManager.getConnection(url, username, password);
	   
	   //增删改
	   Statement stmt = con.createStatement();
	   String sql ="INSERT INTO student VALUES(4,'小王',23,3)";
	   int r = stmt.executeUpdate(sql);
	   System.out.println(r);
	   
	}
    
    /*
     * 执行查询
     */
    @Test
    public void fun2() throws ClassNotFoundException, SQLException{
    	
    	String driverClassName="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3308/tf19";
   	   String username="root";
   	   String password="mysql";
   	   
   	   //加载驱动类，得到连接
   	   Class.forName(driverClassName);
   	   Connection con=DriverManager.getConnection(url, username, password);

   	   Statement stmt = con.createStatement();
   	  ResultSet rs= stmt.executeQuery("select * from book");
   	  
   	  
   	  //解析ResultSet
//   	  while(rs.next()){
//   		  int id=rs.getInt(1);
//   		  String bname = rs.getString("bname");
//   		  double bprice =rs.getDouble("bprice");
//   		System.out.println(id+","+bname+","+bprice);
//   	  }
   	  int count=rs.getMetaData().getColumnCount();
   	 while(rs.next()){//遍历行
   		 for(int i=1;i<=count;i++){//遍历列
   			System.out.print(rs.getObject(i));
   			if(i<count) {System.out.print(", ");}
   			
   		 }
   		System.out.println();
   	 }
   	  
   	  //倒关资源
   	  rs.close();
   	  stmt.close();
   	  con.close();
    }
    
}
