package cn.bookstore.order.domain;

import java.util.Date;
import java.util.List;

import cn.bookstore.user.domain.User;

public class Order {
  private String oid;
  private Date ordertime;
  private double total;
  private int state;//1.未付款2.已付款未发货3.已发货未确认收货4已确认交易成功
  private User owner;//订单所有者
  private String address;
  
  private List<OrderItem> orderItemList;
public List<OrderItem> getOrderItemList() {
	return orderItemList;
}
public void setOrderItemList(List<OrderItem> orderItemList) {
	this.orderItemList = orderItemList;
}
public String getOid() {
	return oid;
}
public void setOid(String oid) {
	this.oid = oid;
}
public Date getOrdertime() {
	return ordertime;
}
public void setOrdertime(Date ordertime) {
	this.ordertime = ordertime;
}
public double getTotal() {
	return total;
}
public void setTotal(double total) {
	this.total = total;
}
public int getState() {
	return state;
}
public void setState(int state) {
	this.state = state;
}
public User getOwner() {
	return owner;
}
public void setOwner(User owner) {
	this.owner = owner;
}
public String getAddress() {
	return address;
}
public void setAddress(String address) {
	this.address = address;
}
}
