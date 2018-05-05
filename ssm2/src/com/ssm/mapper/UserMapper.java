package com.ssm.mapper;

import java.util.List;

import com.ssm.po.User;


public interface UserMapper {
	public User findByUsername(String username) throws Exception;
	public void add(User user) throws Exception;
	public List<User> findAll();
	
}
