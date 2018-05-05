package com.ssm.service;

import java.util.List;

import com.ssm.service.CategoryException;

import com.ssm.po.Category;

public interface CategoryService {
	public List<Category> findAll() throws Exception;

	public void delete(String cid) throws CategoryException ;

	public void add(Category category);

	public Category load(String cid);

	public void edit(Category category);
}
