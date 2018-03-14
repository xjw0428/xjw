package cn.book.web.filter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class StaticResponse extends HttpServletResponseWrapper {
	private HttpServletResponse response;
	private  PrintWriter pw;
	public StaticResponse(HttpServletResponse response,String path) throws FileNotFoundException, UnsupportedEncodingException {
		super(response);
		this.response=response;
		pw=new PrintWriter(path,"utf-8");
	}
   public PrintWriter getWriter(){
	   return pw;
   }
}
