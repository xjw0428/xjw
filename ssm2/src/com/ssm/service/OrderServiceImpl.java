package com.ssm.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.commons.CommonUtils;

import com.ssm.service.OrderException;

import com.ssm.mapper.OrderMapper;
import com.ssm.po.CartItem;
import com.ssm.po.Item;
import com.ssm.po.Order;
import com.ssm.po.OrderItem;
import com.ssm.po.User;
@Service
public class OrderServiceImpl implements OrderService {
	@Autowired
	private OrderMapper orderMapper;
	@Override
	public void add(Order order) throws Exception {
		orderMapper.addOrder(order);
		orderMapper.addOrderItemList(order.getOrderItemList());
	}
	@Override
	public void pay(String oid) throws Exception {
		/*
		 * 1. 获取订单的状态
		 *   * 如果状态为1，那么执行下面代码
		 *   * 如果状态不为1，那么本方法什么都不做
		 */
		int state = orderMapper.getStateByOid(oid);
		if(state == 1) {
			// 修改订单状态为2
			orderMapper.updateState(oid, 2);
		}

	}
	public List<OrderItem> getOrderItem(String oid) throws Exception {
		List<OrderItem> orderItemList = orderMapper.getOrderItemList(oid);
		for(OrderItem orderItem : orderItemList){
			 Item it = new Item();
			 it = orderMapper.getItemByBid(orderItem.getBid());
			 orderItem.setItem(it);
		 }	 
		return orderItemList;
	}
	@Override
	public List<Order> myOrders(String uid) throws Exception {
		List<Order> orderlist = orderMapper.findByUid(uid);
		for(Order order :orderlist){
			
			order.setOrderItemList(this.getOrderItem(order.getOid()));
		}
		return orderlist;
	}
	@Override
	public Order load(String oid) throws Exception {
		Order order =  orderMapper.load(oid);
		List<OrderItem> orderItemList =  this.getOrderItem(oid);
		order.setOrderItemList(orderItemList);
		return  order;
	}
	@Override
	public void confirm(String oid) throws Exception {
		int state = orderMapper.getStateByOid(oid);
		if(state != 3) throw new OrderException("订单确认失败！");
	}
	@Override
	public List<Order> findAllOrder() throws Exception {
		List<Order> orderlist = orderMapper.findAll();
		for(Order order :orderlist){
			order.setOwner(this.getUserByOrder(order));	
	
			order.setOrderItemList(this.getOrderItem(order.getOid()));
		}
		return orderlist;
	}
	
	public User getUserByOrder(Order order) {
		User user=orderMapper.getUserByOrder(order);
		return user;
	}
	@Override
	public List<Order> findOrderByState(int state) throws Exception {
		List<Order> orderlist= orderMapper.getOrderByState(state);
		for(Order order :orderlist){
			order.setOwner(this.getUserByOrder(order));	
	
			order.setOrderItemList(this.getOrderItem(order.getOid()));
		}
		return orderlist;
	}
	
}

