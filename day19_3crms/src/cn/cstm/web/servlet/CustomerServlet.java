package cn.cstm.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.cstm.domain.Customer;
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
		request.setAttribute("cstmList", customerService.findAll());
		return "f:/list.jsp";
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
		Customer criteria = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		List<Customer> cstmList = customerService.query(criteria);
		request.setAttribute("cstmList", cstmList);
		return "/list.jsp";
	}	
}
