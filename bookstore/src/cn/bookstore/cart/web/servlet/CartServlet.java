package cn.bookstore.cart.web.servlet;



import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.book.domain.Book;
import cn.bookstore.book.service.BookService;
import cn.bookstore.cart.domain.Cart;
import cn.bookstore.cart.domain.CartItem;
import cn.itcast.servlet.BaseServlet;

public class CartServlet extends BaseServlet {


	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		//得到车
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//得到条目
		String bid = request.getParameter("bid");
		int count = Integer.parseInt(request.getParameter("count"));
		Book book = new BookService().load(bid);
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		
		cart.add(cartItem);
               return "f:/jsps/cart/list.jsp";
     }
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		cart.clear();
               return "f:/jsps/cart/list.jsp";
     }
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		String bid = request.getParameter("bid");
		cart.delete(bid);
               return "f:/jsps/cart/list.jsp";
     }
}
