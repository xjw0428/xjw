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
		 * 1. 封装表单数据到form对象中
		 * 2. 补全：uid、code
		 * 3. 输入校验
		 *   > 保存错误信息、form到request域，转发到regist.jsp
		 * 4. 调用service方法完成注册
		 *   > 保存错误信息、form到request域，转发到regist.jsp
		 * 5. 发邮件
		 * 6. 保存成功信息转发到msg.jsp
		 */
		// 封装表单数据
		//form = CommonUtils.toBean(request.getParameterMap(), User.class);
		// 补全
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		/*
		 * 输入校验
		 * 1. 创建一个Map，用来封装错误信息，其中key为表单字段名称，值为错误信息
		 */
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 2. 获取form中的username、password、email进行校验
		 */
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()) {
			errors.put("username", "用户名不能为空！");
		} else if(username.length() < 3 || username.length() > 10) {
			errors.put("username", "用户名长度必须在3~10之间！");
		}
		
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "密码不能为空！");
		} else if(password.length() < 3 || password.length() > 10) {
			errors.put("password", "密码长度必须在3~10之间！");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "Email格式错误！");
		}
		/*
		 * 3. 判断是否存在错误信息
		 */
		if(errors.size() > 0) {
			// 1. 保存错误信息
			// 2. 保存表单数据
			// 3. 转发到regist.jsp
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "forward:/jsps/user/regist.jsp";
		}
		
		/*
		 * 调用service的regist()方法
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
			 * 1. 封装表单数据到form中
			 * 2. 输入校验（不写了）
			 * 3. 调用service完成激活
			 *   > 保存错误信息、form到request，转发到login.jsp
			 * 4. 保存用户信息到session中，然后重定向到index.jsp
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
