package cn.cstm.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cstm.domain.Customer;
import cn.cstm.domain.PageBean;
import cn.cstm.service.CustomerService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;

public class CustomerServlet extends BaseServlet {

	private CustomerService customerService = new CustomerService();
	
	/*
	 * ��ӿͻ�
	 */
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Customer c = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		c.setCid(CommonUtils.uuid());
		customerService.add(c);
		request.setAttribute("msg", "��ϲ����ӿͻ��ɹ���");
		return "f:/msg.jsp";
	}
	 /*
	  * ��ѯ����
	  */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		request.setAttribute("cstmList", customerService.findAll());
//		return "f:/list.jsp";
		
		int pc=getPc(request);
		int ps=10;//һҳʮ�м�¼
		PageBean<Customer> pb=customerService.findAll(pc,ps);
		pb.setUrl(getUrl(request));
		request.setAttribute("pb",pb);
		return "f:/list.jsp";
		
	}	
	 private int getPc(HttpServletRequest request) {
		String value = request.getParameter("pc");
		if(value==null||value.trim().isEmpty()){
			return 1;
		}
		return Integer.parseInt(value);
	}
	/*
	  * �༭֮ǰ�ļ��ع���
	  */
	public String preEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Customer cstm = customerService.load(cid);
		request.setAttribute("cstm", cstm);
		return "f:/edit.jsp";
	}	
	 /*
	  * �༭����
	  */
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Customer c = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		customerService.edit(c);
		request.setAttribute("msg", "�༭�ͻ��ɹ���");
		return "f:/msg.jsp";
	}	
	 /*
	  * ɾ���û�
	  */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		customerService.delete(cid);
		request.setAttribute("msg", "ɾ���ͻ��ɹ���");
		return "f:/msg.jsp";
	}	
	public String query(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		Customer criteria = CommonUtils.toBean(request.getParameterMap(),Customer.class );
//		List<Customer> cstmList = customerService.query(criteria);
//		request.setAttribute("cstmList", cstmList);
//		return "/list.jsp";
		
		Customer criteria = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		/*
		 * ����GET�����������
		 */
		criteria = encoding(criteria);
		int pc=getPc(request);
		int ps=10;//һҳʮ�м�¼
		PageBean<Customer> pb= customerService.query(criteria,pc,ps);
		/*
		 * �õ�url,����pb��
		 */
		pb.setUrl(getUrl(request));
		request.setAttribute("pb", pb);
		return "/list.jsp";
	}	
	private Customer encoding(Customer criteria) throws UnsupportedEncodingException {
		String cname = criteria.getCname();
		String gender = criteria.getGender();
		String cellphone = criteria.getCellphone();
		String email = criteria.getEmail();
		
		if(cname != null && !cname.trim().isEmpty()){
			cname =new String(cname.getBytes("ISO-8859-1"),"utf-8");
			criteria.setCname(cname);
		}
		if(gender != null && !gender.trim().isEmpty()){
			gender =new String(gender.getBytes("ISO-8859-1"),"utf-8");
			criteria.setGender(gender);
		}
		if(cellphone != null && !cellphone.trim().isEmpty()){
			cellphone =new String(cellphone.getBytes("ISO-8859-1"),"utf-8");
			criteria.setCellphone(cellphone);
		}
		if(email != null && !email.trim().isEmpty()){
			email =new String(email.getBytes("ISO-8859-1"),"utf-8");
			criteria.setEmail(email);
		}
		return criteria;
	}
	/*
	 * ��ȡurl
	 * /��Ŀ��/Servlet·��?�����ַ���
	 */
	private String getUrl(HttpServletRequest request){
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();
		String queryString = request.getQueryString();
		
		if(queryString.contains("&pc=")){
			int index = queryString.lastIndexOf("&pc=");
			queryString=queryString.substring(0, index);
		}
		return contextPath + servletPath + "?"+ queryString;
	}
}
