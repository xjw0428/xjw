package cn.user.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.user.domain.User;
import cn.user.service.UserException;
import cn.user.service.UserService;

public class LoginServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		UserService userService=new UserService();
		
		// ��װ�����ݵ�User������
		 
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			User loginuser = userService.login(form);
			request.getSession().setAttribute("session_user", loginuser);
			response.sendRedirect(request.getContextPath()+"/user/welcome.jsp");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", form);//�����ݱ��浽request�򣬻���
			request.getRequestDispatcher("/user/login.jsp").forward(request, response);
		
		}
	}

}
