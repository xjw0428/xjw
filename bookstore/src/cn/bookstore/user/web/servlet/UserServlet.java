package cn.bookstore.user.web.servlet;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.cart.domain.Cart;
import cn.bookstore.user.domain.User;
import cn.bookstore.user.service.UserException;
import cn.bookstore.user.service.UserService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.mail.Mail;
import cn.itcast.mail.MailUtils;
import cn.itcast.servlet.BaseServlet;

/**
 * User������
 */
public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	
	/**
	 * �˳�����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getSession().invalidate();
		return "r:/index.jsp";
	}
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. ��װ�����ݵ�form��
		 * 2. ����У�飨��д�ˣ�
		 * 3. ����service��ɼ���
		 *   > ���������Ϣ��form��request��ת����login.jsp
		 * 4. �����û���Ϣ��session�У�Ȼ���ض���index.jsp
		 */
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		try {
			User user = userService.login(form);
			request.getSession().setAttribute("session_user", user);
			request.getSession().setAttribute("cart", new Cart());
			return "r:/index.jsp";
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/login.jsp";
		}
	}
	
	/**
	 * �����
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String active(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. ��ȡ����������
		 * 2. ����service������ɼ���
		 *   > �����쳣��Ϣ��request��ת����msg.jsp
		 * 3. ����ɹ���Ϣ��request��ת����msg.jsp
		 */
		String code = request.getParameter("code");
		try {
			userService.active(code);
			request.setAttribute("msg", "��ϲ��������ɹ��ˣ������ϵ�¼��");
		} catch (UserException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "f:/jsps/msg.jsp";
	}
	
	/**
	 * ע�Ṧ��
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. ��װ�����ݵ�form������
		 * 2. ��ȫ��uid��code
		 * 3. ����У��
		 *   > ���������Ϣ��form��request��ת����regist.jsp
		 * 4. ����service�������ע��
		 *   > ���������Ϣ��form��request��ת����regist.jsp
		 * 5. ���ʼ�
		 * 6. ����ɹ���Ϣת����msg.jsp
		 */
		// ��װ������
		User form = CommonUtils.toBean(request.getParameterMap(), User.class);
		// ��ȫ
		form.setUid(CommonUtils.uuid());
		form.setCode(CommonUtils.uuid() + CommonUtils.uuid());
		/*
		 * ����У��
		 * 1. ����һ��Map��������װ������Ϣ������keyΪ���ֶ����ƣ�ֵΪ������Ϣ
		 */
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 2. ��ȡform�е�username��password��email����У��
		 */
		String username = form.getUsername();
		if(username == null || username.trim().isEmpty()) {
			errors.put("username", "�û�������Ϊ�գ�");
		} else if(username.length() < 3 || username.length() > 10) {
			errors.put("username", "�û������ȱ�����3~10֮�䣡");
		}
		
		String password = form.getPassword();
		if(password == null || password.trim().isEmpty()) {
			errors.put("password", "���벻��Ϊ�գ�");
		} else if(password.length() < 3 || password.length() > 10) {
			errors.put("password", "���볤�ȱ�����3~10֮�䣡");
		}
		
		String email = form.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email����Ϊ�գ�");
		} else if(!email.matches("\\w+@\\w+\\.\\w+")) {
			errors.put("email", "Email��ʽ����");
		}
		/*
		 * 3. �ж��Ƿ���ڴ�����Ϣ
		 */
		if(errors.size() > 0) {
			// 1. ���������Ϣ
			// 2. ���������
			// 3. ת����regist.jsp
			request.setAttribute("errors", errors);
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		
		/*
		 * ����service��regist()����
		 */
		try {
			userService.regist(form);
		} catch (UserException e) {
			/*
			 * 1. �����쳣��Ϣ
			 * 2. ����form
			 * 3. ת����regist.jsp
			 */
			request.setAttribute("msg", e.getMessage());
			request.setAttribute("form", form);
			return "f:/jsps/user/regist.jsp";
		}
		
		/*
		 * ���ʼ�
		 * ׼�������ļ���
		 */
		// ��ȡ�����ļ�����
		Properties props = new Properties();
		props.load(this.getClass().getClassLoader()
				.getResourceAsStream("email_template.properties"));
		String host = props.getProperty("host");//��ȡ����������
		String uname = props.getProperty("uname");//��ȡ�û���
		String pwd = props.getProperty("pwd");//��ȡ����
		String from = props.getProperty("from");//��ȡ������
		String to = form.getEmail();//��ȡ�ռ���
		String subject = props.getProperty("subject");//��ȡ����
		String content = props.getProperty("content");//��ȡ�ʼ�����
		content = MessageFormat.format(content, form.getCode());//�滻{0}
		
		Session session = MailUtils.createSession(host, uname, pwd);//�õ�session
		Mail mail = new Mail(from, to, subject, content);//�����ʼ�����
		try {
			MailUtils.send(session, mail);//���ʼ���
		} catch (MessagingException e) {
		}
		
		
		/*
		 * 1. ����ɹ���Ϣ
		 * 2. ת����msg.jsp
		 */
		request.setAttribute("msg", "��ϲ��ע��ɹ���");
		return "f:/jsps/msg.jsp";
	}
}


