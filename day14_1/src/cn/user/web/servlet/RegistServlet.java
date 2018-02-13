package cn.user.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.user.domain.User;
import cn.user.service.UserException;
import cn.user.service.UserService;

public class RegistServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		UserService userService=new UserService();
		
		// 封装表单数据到User对象中
		 
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		//表单校验
		Map<String,String> errors=new HashMap<String,String>();
		String username = form.getUsername();
		if(username==null||username.trim().isEmpty()){
			errors.put("username", "用户名不能为空！");
		}else if(username.length()<2||username.length()>10){
			errors.put("username", "用户名长度必须在2-10之间！");
		}
		
		String password = form.getPassword();
		if(password==null||password.trim().isEmpty()){
			errors.put("password", "密码不能为空！");
		}else if(password.length()<2||password.length()>10){
			errors.put("password", "密码长度必须在2-10之间！");
		}
		if(errors!=null&&errors.size()>0){
			request.setAttribute("errors", errors);
			request.setAttribute("user", form);//表单数据保存到request域，回显
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return;//不要往下走
		}
		
		
		String sessionVerifyCode=(String) request.getSession().getAttribute("session_vcode");
		if(!sessionVerifyCode.equalsIgnoreCase(form.getVerifyCode())){
			request.setAttribute("msg", "验证码错误！");
			request.setAttribute("user", form);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return;
		}
		
		
		try {
			userService.regist(form);
			response.getWriter().print("<h1>注册成功！<h1><a href='"+
			request.getContextPath()+"/user/login.jsp"+"'>点击登录</a>");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", form);//表单数据保存到request域，回显
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
		}
	}

}
