package cn.bookstore.user.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import cn.bookstore.user.domain.User;

public class LoginFilter implements Filter {

   
    public LoginFilter() {
            }

	
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		/*
		 * 1. ��session�л�ȡ�û���Ϣ
		 * 2. �ж�����session�д����û���Ϣ�����У�
		 * 3. ���򣬱��������Ϣ��ת����login.jsp��ʾ
		 */
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		User user = (User)httpRequest.getSession().getAttribute("session_user");
		if(user != null) {
			chain.doFilter(request, response);
		} else {
			httpRequest.setAttribute("msg", "����û�е�¼��");
			httpRequest.getRequestDispatcher("/jsps/user/login.jsp")
					.forward(httpRequest, response);
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
