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
//		request.setAttribute("cstmList", customerService.findAll());
//		return "f:/list.jsp";
		
		int pc=getPc(request);
		int ps=10;//一页十行记录
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
//		Customer criteria = CommonUtils.toBean(request.getParameterMap(),Customer.class );
//		List<Customer> cstmList = customerService.query(criteria);
//		request.setAttribute("cstmList", cstmList);
//		return "/list.jsp";
		
		Customer criteria = CommonUtils.toBean(request.getParameterMap(),Customer.class );
		/*
		 * 处理GET请求编码问题
		 */
		criteria = encoding(criteria);
		int pc=getPc(request);
		int ps=10;//一页十行记录
		PageBean<Customer> pb= customerService.query(criteria,pc,ps);
		/*
		 * 得到url,保存pb中
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
	 * 截取url
	 * /项目名/Servlet路径?参数字符串
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
