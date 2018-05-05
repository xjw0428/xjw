package com.ssm.po;

import java.util.Date;
import java.util.List;

import com.ssm.po.*;

public class Order {
	 private String oid;
	  private Date ordertime;
	  private double total;
	  private int state;//1.未付款2.已付款未发货3.已发货未确认收货4已确认交易成功
	  private User owner;//订单所有者
	  private String address;
	  
	  private List<OrderItem> orderItemList;
	  
	  private String uid;
	
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	/*@Override
	public String toString() {
		return "Order [oid=" + oid + ", ordertime=" + ordertime + ", total="
				+ total + ", state=" + state + ", owner=" + owner
				+ ", address=" + address + ", orderItemList=" + orderItemList
				+ "]";
	}*/
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
