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
	 * 添加客户
	 */
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Customer c = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		c.setCid(CommonUtils.uuid());
		customerService.add(c);
		request.setAttribute("msg", "恭喜，添加客户成功！");
		return "f:/msg.jsp";
	}
	 /*
	  * 查询所有
	  */
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("cstmList", customerService.findAll());
		return "f:/list.jsp";
	}	
	 /*
	  * 编辑之前的加载工作
	  */
	public String preEdit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Customer cstm = customerService.load(cid);
		request.setAttribute("cstm", cstm);
		return "f:/edit.jsp";
	}	
	 /*
	  * 编辑方法
	  */
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Customer c = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		customerService.edit(c);
		request.setAttribute("msg", "编辑客户成功！");
		return "f:/msg.jsp";
	}	
	 /*
	  * 删除用户
	  */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		customerService.delete(cid);
		request.setAttribute("msg", "删除客户成功！");
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
