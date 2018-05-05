package com.ssm.mapper;

import java.util.List;

import com.ssm.po.Category;


public interface CategoryMapper {
	public List<Category> findAll() throws Exception;
	public void delete(String cid);
	public void add(Category category);
	public Category load(String cid);
	public void edit(Category category);
}
