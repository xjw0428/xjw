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
		String htmlPage = category+".html";//�ļ�����
		String htmlPath = config.getServletContext().getRealPath("/htmls");//�ļ�Ŀ¼
		File destFile = new File (htmlPath,htmlPage);
		
		if(destFile.exists()){//�ļ����ڣ��ض���
			res.sendRedirect(req.getContextPath()+"/htmls/"+htmlPage);
			return;
		}
		//�ļ������ڣ����ɲ������html�ļ�������response
		
		StaticResponse sr = new StaticResponse(res,destFile.getAbsolutePath());
		
		chain.doFilter(request, sr);//����
		res.sendRedirect(req.getContextPath()+ "/htmls/" +htmlPage);
	}
	public void init(FilterConfig fConfig) throws ServletException {
		this.config=fConfig;
	}

}
