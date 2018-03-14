package cn.book.web.filter;

import java.io.File;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class StaticFilter
 */
public class StaticFilter implements Filter {
	private FilterConfig config;
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		 
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res =(HttpServletResponse) response;
		
		String category = request.getParameter("category");
		String htmlPage = category+".html";//文件名称
		String htmlPath = config.getServletContext().getRealPath("/htmls");//文件目录
		File destFile = new File (htmlPath,htmlPage);
		
		if(destFile.exists()){//文件存在，重定向
			res.sendRedirect(req.getContextPath()+"/htmls/"+htmlPage);
			return;
		}
		//文件不存在，生成并输出到html文件，调包response
		
		StaticResponse sr = new StaticResponse(res,destFile.getAbsolutePath());
		
		chain.doFilter(request, sr);//生成
		res.sendRedirect(req.getContextPath()+ "/htmls/" +htmlPage);
	}
	public void init(FilterConfig fConfig) throws ServletException {
		this.config=fConfig;
	}

}
