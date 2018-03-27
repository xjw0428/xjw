package cn.bookstore.cart.domain;

import java.math.BigDecimal;

import cn.bookstore.book.domain.Book;

/*
 * 购物车条目类
 */
public class CartItem {
     private Book book;
     private int count;
     
     public double getSubtotal(){//小计,处理二进制运算误差问题
    	 BigDecimal d1=new BigDecimal(book.getPrice()+"");//参数变成字符串类型
    	 BigDecimal d2=new BigDecimal(count+"");
    	 return d1.multiply(d2).doubleValue();
    	 
     }
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
}
