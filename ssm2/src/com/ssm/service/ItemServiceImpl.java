package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.mapper.ItemMapper;
import com.ssm.po.Item;
@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private ItemMapper itemMapper;
	
	public List<Item> findAll() throws Exception{
		List<Item> itemList = itemMapper.findAll();
		return itemList;
	}

	@Override
	public List<Item> findByCategory(String cid) throws Exception {
		return itemMapper.findByCategory(cid);
	}
	
	public Item load(String bid) throws Exception{
		return itemMapper.findByBid(bid);
	}

	@Override
	public void edit(Item item) {
		itemMapper.edit(item);
		
	}

	@Override
	public void add(Item item) {
		itemMapper.add(item);
		
	}

	@Override
	public List<Item> findByName(String bname) {
		return itemMapper.findByName(bname);
	}
}
