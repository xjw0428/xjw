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
 * ȷ���ջ�
 * @param oid
 * @throws OrderException
 */
public void confirm(String oid) throws OrderException {
	/*
	 * 1. У�鶩��״̬���������3���׳��쳣
	 */
	int state = orderDao.getStateByOid(oid);//��ȡ����״̬
	if(state != 3) throw new OrderException("����ȷ��ʧ�ܣ�");
	
	/*
	 * 2. �޸Ķ���״̬Ϊ4����ʾ���׳ɹ�
	 */
	orderDao.updateState(oid, 4);
}
/**
 * ֧������
 * @param oid
 */
public void pay(String oid) {
	/*
	 * 1. ��ȡ������״̬
	 *   * ���״̬Ϊ1����ôִ���������
	 *   * ���״̬��Ϊ1����ô������ʲô������
	 */
	int state = orderDao.getStateByOid(oid);
	if(state == 1) {
		// �޸Ķ���״̬Ϊ2
		orderDao.updateState(oid, 2);
	}
}
}
