package cn.test.login;

import java.io.IOException;
import java.net.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginServlet extends HttpServlet {

	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		
		String username = request.getParameter("username");
		String  paraCode = request.getParameter("code");
		String  sCode =(String) request.getSession().getAttribute("svcode");
		if(!paraCode.equalsIgnoreCase(sCode)){
			request.setAttribute("msg", "验证码错误!");
			request.getRequestDispatcher("/session/login.jsp").forward(request, response);
		    return;
		}
		
		if(username.equals("itcast")){
			//保存错误信息在request域并转发到登录页面
			request.setAttribute("msg", "用户名或密码错误！");
			request.getRequestDispatcher("/session/login.jsp").forward(request, response);
		}else{
			
			
			
			
			//在session中保存用户信息，并重定向到成功页面
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			
			System.out.println("xjw520");
		
			//保存用户Cookie
			Cookie uname =new Cookie("uname",username);
			//Cookie passwd =new Cookie("passwd",passwd);

			uname.setMaxAge(-1);
			response.addCookie(uname);
			
			response.sendRedirect("/day11login/session/succ1.jsp");
			
		}
		
			
	}

}
