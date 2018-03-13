package cn.web.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class EncodingFilter
 */
public class EncodingFilter implements Filter {

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		//����Post�����������
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest) request;
		/*
		 * ����get�����������
		 */
//		String username = request.getParameter("username");
//		username = new String( username.getBytes("ISO-8859-1"),"UTF-8");
		
		/*
		 * ����request
		 * 1.дһ��requestװ����
		 * 2.����ʱʹ���Լ���request
		 */
		if(req.getMethod().equals("GET")){
		EncodingRequest er = new EncodingRequest(req);
		chain.doFilter(er, response);
		}else if(req.getMethod().equals("POST")){
			chain.doFilter(request, response);
		}
	}
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
