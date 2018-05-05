package com.ssm.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.ssm.po.*;;

public interface OrderService {
	 public void add(Order order)  throws Exception;

	public void pay(String oid) throws Exception;

	public List<Order> myOrders(String uid) throws Exception;

	public Order load(String oid) throws Exception;

	public void confirm(String oid)throws Exception;



	public List<Order> findAllOrder() throws Exception;

	public List<Order> findOrderByState(int state) throws Exception;
	
}
