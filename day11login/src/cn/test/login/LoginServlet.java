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
			request.setAttribute("msg", "��֤�����!");
			request.getRequestDispatcher("/session/login.jsp").forward(request, response);
		    return;
		}
		
		if(username.equals("itcast")){
			//���������Ϣ��request��ת������¼ҳ��
			request.setAttribute("msg", "�û������������");
			request.getRequestDispatcher("/session/login.jsp").forward(request, response);
		}else{
			
			
			
			
			//��session�б����û���Ϣ�����ض��򵽳ɹ�ҳ��
			HttpSession session = request.getSession();
			session.setAttribute("username", username);
			
			System.out.println("xjw520");
		
			//�����û�Cookie
			Cookie uname =new Cookie("uname",username);
			//Cookie passwd =new Cookie("passwd",passwd);

			uname.setMaxAge(-1);
			response.addCookie(uname);
			
			response.sendRedirect("/day11login/session/succ1.jsp");
			
		}
		
			
	}

}
