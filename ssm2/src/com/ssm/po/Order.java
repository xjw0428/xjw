package com.ssm.po;

import java.util.Date;
import java.util.List;

import com.ssm.po.*;

public class Order {
	 private String oid;
	  private Date ordertime;
	  private double total;
	  private int state;//1.δ����2.�Ѹ���δ����3.�ѷ���δȷ���ջ�4��ȷ�Ͻ��׳ɹ�
	  private User owner;//����������
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
