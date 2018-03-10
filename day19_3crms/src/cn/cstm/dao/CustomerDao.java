package cn.cstm.dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import cn.cstm.domain.Customer;
import cn.cstm.domain.PageBean;
import cn.itcast.jdbc.TxQueryRunner;

public class CustomerDao {
    QueryRunner qr = new TxQueryRunner();
    
    /*
     * 添加客户
     */
    public void add(Customer c){
    	String sql = "insert into t_customer  values(?,?,?,?,?,?,?)";
    	Object[] params = {c.getCid(),c.getCname(),c.getGender(),c.getBirthday(),c.getCellphone(),c.getEmail(),c.getDescription()};
    try {
		qr.update(sql, params);
	} catch (SQLException e) {
	     throw new RuntimeException(e);
	}
        
    }
    
    /*
     * 查询所有
     */
//    public  List<Customer> findAll(){
//    	String sql = "select * from t_customer";
//    try {
//		return qr.query(sql, new BeanListHandler<Customer>(Customer.class));
//	} catch (SQLException e) {
//	     throw new RuntimeException(e);
//	}
//        
//    }

	public PageBean<Customer> findAll(int pc, int ps) {
	 
	    try {
	    	PageBean<Customer> pb = new PageBean<Customer>();
	    	pb.setPc(pc);
	    	pb.setPs(ps);
	    	
	    	String sql = "select count(*) from t_customer";
	    	Number num=(Number) qr.query(sql, new ScalarHandler());
	    	int tr=num.intValue();
	    	pb.setTr(tr);
	    	
	        sql = "select * from t_customer order by cname limit ?,?";
	        List<Customer> beanList = qr.query(sql,
	        		new BeanListHandler<Customer>(Customer.class),(pc-1)*ps,ps);
	        pb.setBeanList(beanList);
	        return pb;
		} catch (SQLException e) {
		     throw new RuntimeException(e);
		}
	}
    /*
     * 加载客户
     */
	public Customer load(String cid) {
		String sql = "select * from t_customer where cid =?";
		  try {
				return qr.query(sql, new BeanHandler<Customer>(Customer.class),cid);
			} catch (SQLException e) {
			     throw new RuntimeException(e);
			}
	}

	public void edit(Customer c) {
		try {
			String sql = "update t_customer set cname=?,gender=?,birthday=?," +
					"cellphone=?,email=?,description=? where cid =?";
			Object[] params = {c.getCname(),c.getGender(),c.getBirthday(),
					c.getCellphone(),c.getEmail(),c.getDescription(),c.getCid()};
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void delete(String cid) {
		String sql = "delete from t_customer where cid =? ";
		Object[] params ={cid};
		try {
			qr.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	/*
	 * 多条件组合查询
	 */
//	public List<Customer> query(Customer criteria) {
//		StringBuilder sql = new StringBuilder("select * from t_customer where 1=1");
//		List<Object> params = new ArrayList<Object>();
//		String cname = criteria.getCname();
//		if(cname != null && !cname.trim().isEmpty()) {
//			sql.append(" and cname like ?");
//			params.add("%" + cname + "%");
//		}
//		
//		String gender = criteria.getGender();
//		if(gender != null && !gender.trim().isEmpty()) {
//			sql.append(" and gender=?");
//			params.add(gender);
//		}
//		
//		String cellphone = criteria.getCellphone();
//		if(cellphone != null && !cellphone.trim().isEmpty()) {
//			sql.append(" and cellphone like ?");
//			params.add("%" + cellphone + "%");
//		}
//		
//		String email = criteria.getEmail();
//		if(email != null && !email.trim().isEmpty()) {
//			sql.append(" and email like ?");
//			params.add("%" + email + "%");
//		}
//		
//		try {
//			return qr.query(sql.toString(), new BeanListHandler<Customer>(Customer.class), params.toArray());
//		} catch (SQLException e) {
//			throw new RuntimeException(e);
//		}
//	}
	public PageBean<Customer> query(Customer criteria,int pc, int ps){
		PageBean<Customer> pb = new PageBean<Customer>();
    	pb.setPc(pc);
    	pb.setPs(ps);
    	try {
    	StringBuilder cntSql = new StringBuilder("select count(*) from t_customer");
    	StringBuilder whereSql = new StringBuilder(" where 1=1");
		List<Object> params = new ArrayList<Object>();
		String cname = criteria.getCname();
		if(cname != null && !cname.trim().isEmpty()) {
			whereSql.append(" and cname like ?");
			params.add("%" + cname + "%");
		}
		
		String gender = criteria.getGender();
		if(gender != null && !gender.trim().isEmpty()) {
			whereSql.append(" and gender=?");
			params.add(gender);
		}
		
		String cellphone = criteria.getCellphone();
		if(cellphone != null && !cellphone.trim().isEmpty()) {
			whereSql.append(" and cellphone like ?");
			params.add("%" + cellphone + "%");
		}
		
		String email = criteria.getEmail();
		if(email != null && !email.trim().isEmpty()) {
			whereSql.append(" and email like ?");
			params.add("%" + email + "%");
		}
			Number num =(Number)qr.query(cntSql.append(whereSql).toString(), new ScalarHandler(),params.toArray());
			int tr = num.intValue();
			pb.setTr(tr);
			
			/*
			 * 得到beanList
			 */
			StringBuilder sql = new StringBuilder("select * from t_customer");
			StringBuilder limitSql = new StringBuilder("limit ?,?");
			params.add((pc-1)*ps);
			params.add(ps);
			 List<Customer> beanList=qr.query(sql.append(whereSql).append(limitSql).toString(), 
					 new BeanListHandler<Customer>(Customer.class), params.toArray());
			 pb.setBeanList(beanList);
			 
			 return pb;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	} 
}
