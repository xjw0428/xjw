package com.ssm.service;

import java.util.List;

import com.ssm.service.UserException;
import com.ssm.po.User;



public interface UserService {
	public void regist(User form) throws Exception;
	public User login(User form) throws Exception ;
	public List<User> findAll();
}
