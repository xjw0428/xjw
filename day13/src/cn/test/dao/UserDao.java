package cn.test.dao;

import cn.test.domain.User;

public class UserDao {
	/*
	 * ��xml�����ݲ�ѯ����֮�󣬷�װ��user������
	 */
	public User find(){
		return new User("lisi","123");
	}
}
