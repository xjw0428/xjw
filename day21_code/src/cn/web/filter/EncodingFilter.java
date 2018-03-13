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
		
		//处理Post请求编码问题
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest) request;
		/*
		 * 处理get请求编码问题
		 */
//		String username = request.getParameter("username");
//		username = new String( username.getBytes("ISO-8859-1"),"UTF-8");
		
		/*
		 * 调包request
		 * 1.写一个request装饰类
		 * 2.放行时使用自己的request
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
