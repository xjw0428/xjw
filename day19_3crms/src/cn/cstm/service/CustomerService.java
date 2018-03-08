package cn.cstm.service;

import java.util.List;

import cn.cstm.dao.CustomerDao;
import cn.cstm.domain.Customer;

public class CustomerService {
   private CustomerDao customerdao = new CustomerDao();
   /*
    * ��ӿͻ�
    */
   public void add(Customer c){
	   customerdao.add(c);
   }
   /*
    * ��ѯ����
    */
   public  List<Customer> findAll(){
	   return customerdao.findAll();
   }
   /*
    * ���ؿͻ�
    */
public Customer load(String cid) {
	return customerdao.load(cid);
}
/*
 * �༭�ͻ�
 */
public void edit(Customer c) {
	customerdao.edit(c);
}
/*
 * ɾ���û�
 */
public void delete(String cid) {
	customerdao.delete(cid);
	
}
/*
 * ��������ϲ�ѯ
 */
public List<Customer> query(Customer criteria) {
	return customerdao.query(criteria);
}
}
