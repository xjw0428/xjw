package cn.bookstore.user.dao;

import java.sql.SQLException;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import cn.bookstore.user.domain.User;
import cn.itcast.jdbc.TxQueryRunner;

public class UserDao {
   private QueryRunner qr = new TxQueryRunner();
   
   public User findByUsername(String username){
	   String sql="select * from tb_user where username=?";
	   try {
		 return qr.query(sql, new BeanHandler<User>(User.class),username);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
   }
   public User findByEmail(String email){
	   String sql="select * from tb_user where username=?";
	   try {
		 return qr.query(sql, new BeanHandler<User>(User.class),email);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
   }
   public void add(User user){
	   String sql="insert into tb_user values(?,?,?,?,?,?)";
	   Object[] params = {user.getUid(),user.getUsername(),user.getPassword(),user.getEmail(),user.getCode(),user.isState()};
	   try {
		qr.update(sql, params);
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
   }
   /**
	 * ���������ѯ
	 * @param code
	 * @return
	 */
	public User findByCode(String code) {
		try {
			String sql = "select * from tb_user where code=?";
			return qr.query(sql, new BeanHandler<User>(User.class), code);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * �޸�ָ���û���ָ��״̬
	 * @param uid
	 * @param state
	 */
	public void updateState(String uid, boolean state) {
		try {
			String sql = "update tb_user set state=? where uid=?";
			qr.update(sql, state, uid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
