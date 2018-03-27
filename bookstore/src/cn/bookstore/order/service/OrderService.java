package cn.bookstore.order.service;

import java.sql.SQLException;
import java.util.List;

import cn.bookstore.order.dao.OrderDao;
import cn.bookstore.order.domain.Order;
import cn.itcast.jdbc.JdbcUtils;

public class OrderService {
  private OrderDao orderDao = new OrderDao();
  
  public void add(Order order){
	  try{
		  JdbcUtils.beginTransaction();
		  orderDao.addOrder(order);
		  orderDao.addOrderItemList(order.getOrderItemList());
		  JdbcUtils.commitTransaction();
	  }catch(Exception e){
		  try {
			JdbcUtils.rollbackTransaction();
		} catch (SQLException e1) {
			throw new RuntimeException(e1);
		}
	  }
	  
  }

public List<Order> myOrders(String uid) {
	return orderDao.findByUid(uid);
}

public Order load(String oid) {
	
	return orderDao.load(oid);
}
/**
 * 确认收货
 * @param oid
 * @throws OrderException
 */
public void confirm(String oid) throws OrderException {
	/*
	 * 1. 校验订单状态，如果不是3，抛出异常
	 */
	int state = orderDao.getStateByOid(oid);//获取订单状态
	if(state != 3) throw new OrderException("订单确认失败！");
	
	/*
	 * 2. 修改订单状态为4，表示交易成功
	 */
	orderDao.updateState(oid, 4);
}
/**
 * 支付方法
 * @param oid
 */
public void pay(String oid) {
	/*
	 * 1. 获取订单的状态
	 *   * 如果状态为1，那么执行下面代码
	 *   * 如果状态不为1，那么本方法什么都不做
	 */
	int state = orderDao.getStateByOid(oid);
	if(state == 1) {
		// 修改订单状态为2
		orderDao.updateState(oid, 2);
	}
}
}
