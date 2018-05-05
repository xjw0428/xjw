package com.ssm.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.po.*;
import com.ssm.service.UserException;
import cn.itcast.commons.CommonUtils;

import com.ssm.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping("/regist.action")
	public String regist(HttpServletRequest request, User form)
			throws Exception {
		/*
		 * 1. ��װ�����ݵ�form������
		 * 2. ��ȫ��uid��code
		 * 3. ����У��
		 *   > ���������Ϣ��form��request��ת����regist.jsp
		 * 4. ����service�������ע��
		 *   > ���������Ϣ��form��request��ת����regist.jsp
		 * 5. ���ʼ�
		 * 6. ����ɹ���Ϣת����msg.jsp
		 */
		// ��װ������
		//form = CommonUtils.toBean(request.getParameterMap(), User.class);
		// ��ȫ
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		/*
		 * ����У��
		 * 1. ����һ��Map��������װ������Ϣ������keyΪ���ֶ����ƣ�ֵΪ������Ϣ
		 */
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 2. ��ȡform�е�username��password��email����У��
		 */
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()) {
			errors.put("username", "�û�������Ϊ�գ�");
		} else if(username.length() < 3 || username.length() > 10) {
			errors.put("username", "�û������ȱ�����3~10֮�䣡");
		}
		
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "���벻��Ϊ�գ�");
		} else if(password.length() < 3 || password.length() > 10) {
			errors.put("password", "���볤�ȱ�����3~10֮�䣡");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email����Ϊ�գ�");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "Email��ʽ����");
		}
		/*
		 * 3. �ж��Ƿ���ڴ�����Ϣ
		 */
		if(errors.size() > 0) {
			// 1. ���������Ϣ
			// 2. ���������
			// 3. ת����regist.jsp
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "forward:/jsps/user/regist.jsp";
		}
		
		/*
		 * ����service��regist()����
		 */
		
			try {
				userService.regist(form);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/index.jsp";
	}
	   
	
	     @RequestMapping("/quit.action")
		public String quit(HttpServletRequest request, HttpServletResponse response)
				throws ServletException, IOException {
			request.getSession().invalidate();
			return "redirect:/index.jsp";
		}
		@RequestMapping("/login.action")
		public String login(HttpServletRequest request, User form)
				throws ServletException, IOException, UserException {
			/*
			 * 1. ��װ�����ݵ�form��
			 * 2. ����У�飨��д�ˣ�
			 * 3. ����service��ɼ���
			 *   > ���������Ϣ��form��request��ת����login.jsp
			 * 4. �����û���Ϣ��session�У�Ȼ���ض���index.jsp
			 */
			//User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
			try {
				User user = userService.login(form);
			
			request.getSession().setAttribute("session_user", user);
			request.getSession().setAttribute("cart",new Cart());
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return "redirect:/index.jsp";
		}
		@RequestMapping("/admin/findAllUser.action")
		public String findAllUser(HttpServletRequest request){
			List<User> userList= userService.findAll();
			request.setAttribute("userList", userList);
			return "forward:/adminjsps/admin/user/list.jsp";
			
		}
}
