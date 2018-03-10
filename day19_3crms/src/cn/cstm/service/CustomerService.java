package cn.cstm.service;

import java.util.List;

import cn.cstm.dao.CustomerDao;
import cn.cstm.domain.Customer;
import cn.cstm.domain.PageBean;

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
//   public  List<Customer> findAll(){
//	   return customerdao.findAll();
//   }
   public PageBean<Customer> findAll(int pc, int ps) {
		return customerdao.findAll(pc,ps);
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
public PageBean<Customer> query(Customer criteria,int pc,int ps) {
	return customerdao.query(criteria,pc,ps);
}

}
