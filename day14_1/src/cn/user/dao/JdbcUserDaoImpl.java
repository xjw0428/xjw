package cn.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import cn.user.domain.User;
/*
 * ������ݿ��ʵ��
 */
public class JdbcUserDaoImpl implements UserDao {
	
	@Override
	public User findByUsername(String username) {
		Connection con =null;
		PreparedStatement pstmt=null;
		ResultSet rs=null;
		//�õ�����
		try {
			con = JdbcUtils.getConnection();
			
		//׼��sqlģ�壬�õ�pstmt
		String sql = "SELECT * FROM t_user WHERE username=?";
		pstmt = con.prepareStatement(sql);
		
		//Ϊ�ʺŸ�ֵ
		pstmt.setString(1, username);
		
		//ִ��
		 rs=pstmt.executeQuery();
		 
		 //��rsתΪUser���ͷ���
		 if(rs==null){
			 return null;
		 }
		 if(rs.next()){
			 //��rsתΪUser���󷵻�
			 //ORM-->�����ϵӳ�� ��������
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
		//�õ�����
		try {
			con = JdbcUtils.getConnection();
			
		//׼��sqlģ�壬�õ�pstmt
		String sql = "INSERT INTO t_user VALUES(?,?)";
		pstmt = con.prepareStatement(sql);
		
		//Ϊ�ʺŸ�ֵ
		pstmt.setString(1, user.getUsername());
		pstmt.setString(2, user.getPassword());
		
		//ִ��
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
