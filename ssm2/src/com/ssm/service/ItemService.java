package com.ssm.service;

import java.util.List;

import com.ssm.po.Item;

public interface ItemService {
	public List<Item> findAll() throws Exception;

	public List<Item> findByCategory(String cid) throws Exception;

	public Item load(String bid) throws Exception;

	public void edit(Item item);

	public void add(Item item);

	public List<Item> findByName(String bname);
}
