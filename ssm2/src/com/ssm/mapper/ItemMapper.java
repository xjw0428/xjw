package com.ssm.mapper;

import java.util.List;

import com.ssm.po.Item;

public interface ItemMapper {
      public List<Item> findAll() throws Exception;

	public List<Item> findByCategory(String cid) throws Exception;

	public Item findByBid(String bid) throws Exception;
	public int getCountByCid(String cid);

	public void edit(Item item);

	public void add(Item item);

	public List<Item> findByName(String bname);

}
