package cn.test.web.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.test.domain.User;
import cn.test.service.UserService;

public class UserServlet extends HttpServlet {

	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	UserService userService=new UserService();
	User user=userService.find();
	request.setAttribute("user", user);
	request.getRequestDispatcher("/show.jsp").forward(request, response);
	}

}
