package com.ssm.controller;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.po.*;
import com.ssm.service.OrderService;

import com.ssm.controller.PaymentUtil;


import com.ssm.service.OrderException;
import cn.itcast.commons.CommonUtils;

@Controller
public class OrderController {
	@Autowired
	private OrderService orderService;
	@RequestMapping("/addOrder.action")
	public String addOrder(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		 Cart cart =(Cart) request.getSession().getAttribute("cart");
		 Order order = new Order();
		 order.setOid(CommonUtils.uuid());
		 order.setOrdertime(new Date());
		 order.setState(1);
		 User user = (User) request.getSession().getAttribute("session_user");
		 order.setOwner(user);
		 order.setTotal(cart.getTotal());
		 
		 List<OrderItem> orderItemList = new ArrayList<OrderItem>();

		 for(CartItem cartItem : cart.getCartItems()){
			 OrderItem oi = new OrderItem();
			 oi.setIid(CommonUtils.uuid());
			 oi.setCount(cartItem.getCount());
			 oi.setItem(cartItem.getItem());
			 oi.setSubtotal(cartItem.getSubtotal());
			 oi.setOrder(order);
			 
			 orderItemList.add(oi);
		 }	 
			 order.setOrderItemList(orderItemList);
			 
			 cart.clear();
			 System.out.println(order);
			 orderService.add(order);
			 request.setAttribute("order", order);
			 
		
		 return "forward:/jsps/order/desc.jsp";
}
	@RequestMapping("/myOrders.action")
	public ModelAndView myOrders(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		ModelAndView modelAndView= new ModelAndView();
		User user = (User)request.getSession().getAttribute("session_user");
		List<Order> orderList=orderService.myOrders(user.getUid());
		modelAndView.addObject("orderList", orderList);
		modelAndView.setViewName("/jsps/order/list");
		return modelAndView;
	}
	@RequestMapping("/loadOrder.action")
	public ModelAndView loadOrder(HttpServletRequest request, HttpServletResponse response) throws Exception
			 {
		 String oid = request.getParameter("oid");
		 ModelAndView modelAndView= new ModelAndView();
		 Order order =orderService.load(oid);
		 modelAndView.addObject("order", order);
		 //System.out.println(orderService.load(oid));
		 modelAndView.setViewName("/jsps/order/desc");
		return modelAndView;
	}

	@RequestMapping("/confirm.action")
	public String confirm(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String oid = request.getParameter("oid");
		try {
			orderService.confirm(oid);
			request.setAttribute("msg", "恭喜，交易成功！");
		} catch (OrderException e) {
			request.setAttribute("msg", e.getMessage());
		}
		return "forward:/jsps/msg.jsp";
	}
	
	@RequestMapping("/admin/findAllOrder.action")
	public ModelAndView findAllOrder()
			throws Exception {
		ModelAndView modelAndView= new ModelAndView();
		List<Order> orderList=orderService.findAllOrder();
		modelAndView.addObject("orderList", orderList);
		modelAndView.setViewName("/adminjsps/admin/order/list");
		return modelAndView;
	}
	
	@RequestMapping("/admin/findOrderByState.action")
	public String findOrderByState(int state,HttpServletRequest request) throws Exception{
		List<Order> orderList=orderService.findOrderByState(state);
		request.setAttribute("orderList", orderList);
		return "forward:/adminjsps/admin/order/list.jsp";
		
	}
	@RequestMapping("/pay.action")
	public String pay(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		/*
		 * 准备13参数
		 */
		String p0_Cmd = "Buy";
		String p1_MerId = props.getProperty("p1_MerId");
		String p2_Order = request.getParameter("oid");
		String p3_Amt = "0.01";
		String p4_Cur = "CNY";
		String p5_Pid = "";
		String p6_Pcat = "";
		String p7_Pdesc = "";
		String p8_Url = props.getProperty("p8_Url");
		String p9_SAF = "";
		String pa_MP = "";
		String pd_FrpId = request.getParameter("pd_FrpId");
		String pr_NeedResponse = "1";

		/*
		 * 计算hmac
		 */
		String keyValue = props.getProperty("keyValue");
		String hmac = PaymentUtil.buildHmac(p0_Cmd, p1_MerId, p2_Order, p3_Amt,
				p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc, p8_Url, p9_SAF, pa_MP,
				pd_FrpId, pr_NeedResponse, keyValue);

		/*
		 * 连接易宝的网址和13+1个参数
		 */
		StringBuilder url = new StringBuilder(props.getProperty("url"));
		url.append("?p0_Cmd=").append(p0_Cmd);
		url.append("&p1_MerId=").append(p1_MerId);
		url.append("&p2_Order=").append(p2_Order);
		url.append("&p3_Amt=").append(p3_Amt);
		url.append("&p4_Cur=").append(p4_Cur);
		url.append("&p5_Pid=").append(p5_Pid);
		url.append("&p6_Pcat=").append(p6_Pcat);
		url.append("&p7_Pdesc=").append(p7_Pdesc);
		url.append("&p8_Url=").append(p8_Url);
		url.append("&p9_SAF=").append(p9_SAF);
		url.append("&pa_MP=").append(pa_MP);
		url.append("&pd_FrpId=").append(pd_FrpId);
		url.append("&pr_NeedResponse=").append(pr_NeedResponse);
		url.append("&hmac=").append(hmac);

		System.out.println(url);

		/*
		 * 重定向到易宝
		 */
		response.sendRedirect(url.toString());

		return null;
}
	
	@RequestMapping("/back.action")
	public String back(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 获取11 + 1
		 */
		String p1_MerId = request.getParameter("p1_MerId");
		String r0_Cmd = request.getParameter("r0_Cmd");
		String r1_Code = request.getParameter("r1_Code");
		String r2_TrxId = request.getParameter("r2_TrxId");
		String r3_Amt = request.getParameter("r3_Amt");
		String r4_Cur = request.getParameter("r4_Cur");
		String r5_Pid = request.getParameter("r5_Pid");
		String r6_Order = request.getParameter("r6_Order");
		String r7_Uid = request.getParameter("r7_Uid");
		String r8_MP = request.getParameter("r8_MP");
		String r9_BType = request.getParameter("r9_BType");

		String hmac = request.getParameter("hmac");

		/*
		 * 2. 校验访问者是否为易宝！
		 */
		Properties props = new Properties();
		InputStream input = this.getClass().getClassLoader()
				.getResourceAsStream("merchantInfo.properties");
		props.load(input);
		String keyValue = props.getProperty("keyValue");

		boolean bool = PaymentUtil.verifyCallback(hmac, p1_MerId, r0_Cmd,
				r1_Code, r2_TrxId, r3_Amt, r4_Cur, r5_Pid, r6_Order, r7_Uid,
				r8_MP, r9_BType, keyValue);
		
		if(!bool) {//如果校验失败
			request.setAttribute("msg", "您不是什么好东西！");
			return "forward:/jsps/msg.jsp";
		}
		
		/*
		 * 3. 获取状态订单，确定是否要修改订单状态，以及添加积分等业务操作
		 */
		orderService.pay(r6_Order);//有可能对数据库进行操作，也可能不操作！
		
		/*
		 * 4. 判断当前回调方式
		 *   如果为点对点，需要回馈以success开头的字符串
		 */
		if(r9_BType.equals("2")) {
			response.getWriter().print("success");
		}
		
		/*
		 * 5. 保存成功信息，转发到msg.jsp
		 */
		request.setAttribute("msg", "支付成功！等待卖家发货！");
		return "f:/jsps/msg.jsp";
	}
}