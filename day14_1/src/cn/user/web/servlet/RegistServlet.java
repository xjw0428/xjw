package cn.user.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.commons.CommonUtils;
import cn.user.domain.User;
import cn.user.service.UserException;
import cn.user.service.UserService;

public class RegistServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		UserService userService=new UserService();
		
		// ��װ�����ݵ�User������
		 
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		
		//��У��
		Map<String,String> errors=new HashMap<String,String>();
		String username = form.getUsername();
		if(username==null||username.trim().isEmpty()){
			errors.put("username", "�û�������Ϊ�գ�");
		}else if(username.length()<2||username.length()>10){
			errors.put("username", "�û������ȱ�����2-10֮�䣡");
		}
		
		String password = form.getPassword();
		if(password==null||password.trim().isEmpty()){
			errors.put("password", "���벻��Ϊ�գ�");
		}else if(password.length()<2||password.length()>10){
			errors.put("password", "���볤�ȱ�����2-10֮�䣡");
		}
		if(errors!=null&&errors.size()>0){
			request.setAttribute("errors", errors);
			request.setAttribute("user", form);//�����ݱ��浽request�򣬻���
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return;//��Ҫ������
		}
		
		
		String sessionVerifyCode=(String) request.getSession().getAttribute("session_vcode");
		if(!sessionVerifyCode.equalsIgnoreCase(form.getVerifyCode())){
			request.setAttribute("msg", "��֤�����");
			request.setAttribute("user", form);
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
			return;
		}
		
		
		try {
			userService.regist(form);
			response.getWriter().print("<h1>ע��ɹ���<h1><a href='"+
			request.getContextPath()+"/user/login.jsp"+"'>�����¼</a>");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("user", form);//�����ݱ��浽request�򣬻���
			request.getRequestDispatcher("/user/regist.jsp").forward(request, response);
		}
	}

}
