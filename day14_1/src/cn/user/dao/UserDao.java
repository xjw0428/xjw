package cn.user.dao;

import cn.user.domain.User;

public interface UserDao {
	 public User findByUsername(String username);
	 public void add(User user);
}
