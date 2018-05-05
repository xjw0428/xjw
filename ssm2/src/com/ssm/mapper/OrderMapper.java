package com.ssm.mapper;

import java.util.List;


import com.ssm.po.*;

public interface OrderMapper {
	public void addOrder(Order order) throws Exception;
	 public void addOrderItemList(List<OrderItem> orderItemList) throws Exception;
	public int getStateByOid(String oid) throws Exception;;
	public void updateState(String oid, int i) throws Exception;
	public List<Order> findByUid(String uid) throws Exception;
	public Order load(String oid) throws Exception;
	
	
	
	public List<OrderItem> getOrderItemList(String oid);
	//public List<String> findOrderItemBidByOid();
	public Item getItemByBid(String bid);
	public List<Order> findAll();
	public User getUserByOrder(Order order);
	public List<Order> getOrderByState(int state);
}
