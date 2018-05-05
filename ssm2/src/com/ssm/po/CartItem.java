package com.ssm.po;


import java.math.BigDecimal;
import com.ssm.po.Item;

	/*
	 * ���ﳵ��Ŀ��
	 */
	public class CartItem {
	     private Item item;
	     private int count;
	     
	     public double getSubtotal(){//С��,��������������������
	    	 BigDecimal d1=new BigDecimal(item.getPrice()+"");//��������ַ�������
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
