package cn.bookstore.book.domain;

import cn.bookstore.category.domain.Category;

public class Book {
   @Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price
				+ ", author=" + author + ", image=" + image + ", category="
				+ category + "]";
	}
private String bid;
   private String bname;
   private Double price;
   private String author;
   private String image;
   private Category category;
   private boolean del;
public boolean isDel() {
	return del;
}
public void setDel(boolean del) {
	this.del = del;
}
public String getBid() {
	return bid;
}
public void setBid(String bid) {
	this.bid = bid;
}
public String getBname() {
	return bname;
}
public void setBname(String bname) {
	this.bname = bname;
}
public Double getPrice() {
	return price;
}
public void setPrice(Double price) {
	this.price = price;
}
public String getAuthor() {
	return author;
}
public void setAuthor(String author) {
	this.author = author;
}
public String getImage() {
	return image;
}
public void setImage(String image) {
	this.image = image;
}
public Category getCategory() {
	return category;
}
public void setCategory(Category category) {
	this.category = category;
}
}
