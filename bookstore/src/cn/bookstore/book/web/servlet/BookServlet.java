package cn.bookstore.book.web.servlet;


import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.book.service.BookService;
import cn.bookstore.category.domain.Category;
import cn.itcast.servlet.BaseServlet;

public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	
	public String findAll(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setAttribute("bookList",bookService.findAll());
		return "f:/jsps/book/list.jsp";
	}
	

	public String findByCategory(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String cid=req.getParameter("cid");
		req.setAttribute("bookList",bookService.findByCategory(cid));
		return "f:/jsps/book/list.jsp";
	}
	public String load(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String bid=req.getParameter("bid");
		req.setAttribute("book",bookService.load(bid));
		return "f:/jsps/book/desc.jsp";
	}
}
