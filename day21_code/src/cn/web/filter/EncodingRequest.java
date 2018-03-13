package cn.web.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpSession;

/*
 * ×°ÊÎrequest
 */
public class EncodingRequest extends HttpServletRequestWrapper{
  

	private HttpServletRequest req;
	
      public EncodingRequest(HttpServletRequest request) {
		super(request);
		this.req=request;
	}
    public String getParameter(String name) {
		String value = req.getParameter(name);
     	try {
			value = new String(value .getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException();
		}
     	return value;
	}
}
