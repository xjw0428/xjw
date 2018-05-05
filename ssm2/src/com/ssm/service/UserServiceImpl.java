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
		 if(user!=null)throw new UserException("�û����Ѵ��ڣ�");
		 userMapper.add(form);
	}

	@Override
	public User login(User form) throws Exception {
		 User user =userMapper.findByUsername(form.getUsername());
		 if(user == null) throw new UserException("�û��������ڣ�");
			if(!user.getPassword().equals(form.getPassword()))
				throw new UserException("�������");
			//if(!user.isState()) throw new UserException("��δ���");
			
			return user;
	}

	@Override
	public List<User> findAll() {
		// TODO Auto-generated method stub
		return userMapper.findAll();
	}

}
