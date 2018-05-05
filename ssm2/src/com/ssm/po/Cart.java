package com.ssm.po;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

	public class Cart {
	    private Map<String,CartItem> map=new LinkedHashMap<String,CartItem>();

	    public double getTotal(){
	    	//�ϼƵ�������С��֮��
	       BigDecimal total=new BigDecimal("0");
	    	for(CartItem cartItem:map.values()){
	    		BigDecimal subtotal=new BigDecimal(""+cartItem.getSubtotal());
	    		total=total.add(subtotal);
	    	}
	    	return total.doubleValue();
	    }
	   public void add(CartItem cartItem){
		   if(map.containsKey(cartItem.getItem().getBid())){
			   CartItem oldcartItem = map.get(cartItem.getItem().getBid());
			   oldcartItem.setCount(oldcartItem.getCount()+cartItem.getCount());
			   map.put(cartItem.getItem().getBid(), oldcartItem);
		   }else{
			   map.put(cartItem.getItem().getBid(), cartItem);
		   }
	   }
	   public void clear(){
		   map.clear();
	   }
	   public void delete(String bid){
		   map.remove(bid);
	   }
	   public Collection<CartItem> getCartItems(){
		return map.values();
		   
	   }
}
