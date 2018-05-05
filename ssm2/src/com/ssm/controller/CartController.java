package com.ssm.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ssm.service.*;
import com.ssm.po.*;

@Controller
public class CartController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/addCart.action")
	public String addCart(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		//得到车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//得到条目
		String bid = request.getParameter("bid");
		int count = Integer.parseInt(request.getParameter("count"));
		Item item = itemService.load(bid);
		CartItem cartItem = new CartItem();
		cartItem.setItem(item);
		cartItem.setCount(count);
		cart.add(cartItem);
               return "forward:/jsps/cart/list.jsp";
     }
	@RequestMapping("/clearCart.action")
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
               return "forward:/jsps/cart/list.jsp";
     }
	@RequestMapping("/deleteCart.action")
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
               return "forward:/jsps/cart/list.jsp";
     }
}
