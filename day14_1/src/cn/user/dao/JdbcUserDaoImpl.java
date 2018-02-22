package cn.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.user.domain.User;
/*
 * 针对数据库的实现
 */
public class JdbcUserDaoImpl implements UserDao {
	
	@Override
	public User findByUsername(String username) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//得到连接
		try {
			con = JdbcUtils.getConnection();
			
		//准备sql模板，得到pstmt
		String sql = "SELECT * FROM t_user WHERE username=?";
		pstmt = con.prepareStatement(sql);
		
		//为问号赋值
		pstmt.setString(1, username);
		
		//执行
		 rs=pstmt.executeQuery();
		 
		 //把rs转为User类型返回
		 if(rs==null){
			 return null;
		 }
		 if(rs.next()){
			 //把rs转为User对象返回
			 //ORM-->对象关系映射 表到对象中
			 User user =new User();
			 user.setUsername(rs.getString("username"));
			 user.setPassword(rs.getString("password"));
			 return user;
		 }else{
			 return null;
		 }
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
			
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
      
	}

	@Override
	public void add(User user) {
		Connection con =null;
		PreparedStatement pstmt=null;
		//得到连接
		try {
			con = JdbcUtils.getConnection();
			
		//准备sql模板，得到pstmt
		String sql = "INSERT INTO t_user VALUES(?,?)";
		pstmt = con.prepareStatement(sql);
		
		//为问号赋值
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		
		//执行
		pstmt.executeUpdate();
		
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}finally{
			try {
			if(pstmt!=null)pstmt.close();
			if(con!=null)con.close();
			
				} catch (SQLException e) {
					throw new RuntimeException(e);
				}
		}
      
	}

}
