package cn.test.jdbc;
/*
 * ��ɾ�Ĳ�
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
		//�Ĵ����
	   String driverClassName="com.mysql.jdbc.Driver";
       String url="jdbc:mysql://localhost:3308/tf11";
	   String username="root";
	   String password="mysql";
	   
	   //���������࣬�õ�����
	   Class.forName(driverClassName);
	   Connection con=DriverManager.getConnection(url, username, password);
	   
	   //��ɾ��
	   Statement stmt = con.createStatement();
	   String sql ="INSERT INTO student VALUES(4,'С��',23,3)";
	   int r = stmt.executeUpdate(sql);
	   System.out.println(r);
	   
	}
    
    /*
     * ִ�в�ѯ
     */
    @Test
    public void fun2() throws ClassNotFoundException, SQLException{
    	
    	String driverClassName="com.mysql.jdbc.Driver";
        String url="jdbc:mysql://localhost:3308/tf19";
   	   String username="root";
   	   String password="mysql";
   	   
   	   //���������࣬�õ�����
   	   Class.forName(driverClassName);
   	   Connection con=DriverManager.getConnection(url, username, password);

   	   Statement stmt = con.createStatement();
   	  ResultSet rs= stmt.executeQuery("select * from book");
   	  
   	  
   	  //����ResultSet
//   	  while(rs.next()){
//   		  int id=rs.getInt(1);
//   		  String bname = rs.getString("bname");
//   		  double bprice =rs.getDouble("bprice");
//   		System.out.println(id+","+bname+","+bprice);
//   	  }
   	  int count=rs.getMetaData().getColumnCount();
   	 while(rs.next()){//������
   		 for(int i=1;i<=count;i++){//������
   			System.out.print(rs.getObject(i));
   			if(i<count) {System.out.print(", ");}
   			
   		 }
   		System.out.println();
   	 }
   	  
   	  //������Դ
   	  rs.close();
   	  stmt.close();
   	  con.close();
    }
    
}
