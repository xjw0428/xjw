package cn.cstm.service;

import java.util.List;

import cn.cstm.dao.CustomerDao;
import cn.cstm.domain.Customer;

public class CustomerService {
   private CustomerDao customerdao = new CustomerDao();
   /*
    * 添加客户
    */
   public void add(Customer c){
	   customerdao.add(c);
   }
   /*
    * 查询所有
    */
   public  List<Customer> findAll(){
	   return customerdao.findAll();
   }
   /*
    * 加载客户
    */
public Customer load(String cid) {
	return customerdao.load(cid);
}
/*
 * 编辑客户
 */
public void edit(Customer c) {
	customerdao.edit(c);
}
/*
 * 删除用户
 */
public void delete(String cid) {
	customerdao.delete(cid);
	
}
/*
 * 多条件组合查询
 */
public List<Customer> query(Customer criteria) {
	return customerdao.query(criteria);
}
}
