package com.ssm.po;


import java.math.BigDecimal;
import com.ssm.po.Item;

	/*
	 * 购物车条目类
	 */
	public class CartItem {
	     private Item item;
	     private int count;
	     
	     public double getSubtotal(){//小计,处理二进制运算误差问题
	    	 BigDecimal d1=new BigDecimal(item.getPrice()+"");//参数变成字符串类型
	    	 BigDecimal d2=new BigDecimal(count+"");
	    	 return d1.multiply(d2).doubleValue();
	    	 
	     }
		public Item getItem() {
			return item;
		}
		public void setItem(Item item) {
			this.item = item;
		}
		public int getCount() {
			return count;
		}
		public void setCount(int count) {
			this.count = count;
		}
}
