package cn.test.dao;

import cn.test.domain.User;

public class UserDao {
	/*
	 * 把xml中数据查询出来之后，封装到user对象中
	 */
	public User find(){
		return new User("lisi","123");
	}
}
