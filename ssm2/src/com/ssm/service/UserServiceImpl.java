package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.service.UserException;

import com.ssm.mapper.UserMapper;
import com.ssm.po.User;
@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Override
	public void regist(User form) throws Exception {
		 User user =userMapper.findByUsername(form.getUsername());
		 if(user!=null)throw new UserException("用户名已存在！");
		 userMapper.add(form);
	}

	@Override
	public User login(User form) throws Exception {
		 User user =userMapper.findByUsername(form.getUsername());
		 if(user == null) throw new UserException("用户名不存在！");
			if(!user.getPassword().equals(form.getPassword()))
				throw new UserException("密码错误！");
			//if(!user.isState()) throw new UserException("尚未激活！");
			
			return user;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}

}
