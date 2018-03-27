package cn.bookstore.cart.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<String,CartItem> map=new LinkedHashMap<String,CartItem>();

    public double getTotal(){
    	//合计等于所有小计之和
       BigDecimal total=new BigDecimal("0");
    	for(CartItem cartItem:map.values()){
    		BigDecimal subtotal=new BigDecimal(""+cartItem.getSubtotal());
    		total=total.add(subtotal);
    	}
    	return total.doubleValue();
    }
   public void add(CartItem cartItem){
	   if(map.containsKey(cartItem.getBook().getBid())){
		   CartItem oldcartItem = map.get(cartItem.getBook().getBid());
		   oldcartItem.setCount(oldcartItem.getCount()+cartItem.getCount());
		   map.put(cartItem.getBook().getBid(), oldcartItem);
	   }else{
		   map.put(cartItem.getBook().getBid(), cartItem);
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
