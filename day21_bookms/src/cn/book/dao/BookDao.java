package cn.book.dao;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import cn.book.domain.Book;
import cn.itcast.jdbc.TxQueryRunner;

public class BookDao {
   private QueryRunner qr = new TxQueryRunner();
   
   public List<Book> findAll(){
	   String sql = "select * from t_book";
	   try {
		   return qr.query(sql, new BeanListHandler<Book>(Book.class));
	} catch (SQLException e) {
		throw new RuntimeException(e);
	}
   }
   
   public List<Book> findByCategory(int category){
	   String sql = "select * from t_book where category=?";
		try {
			return 	qr.query(sql, new BeanListHandler<Book>(Book.class),category);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	   
   }
}
