package com.ssm.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssm.service.*;

import com.ssm.mapper.CategoryMapper;
import com.ssm.mapper.ItemMapper;
import com.ssm.po.Category;

@Service
public class CategoryServiceImpl implements CategoryService{
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private  ItemMapper  itemMapper;
	
	public List<Category> findAll() throws Exception{
		List<Category> categoryList = categoryMapper.findAll();
		return categoryList;
	}

	@Override
	public void delete(String cid) throws CategoryException {
		int count =itemMapper.getCountByCid(cid);
		if(count > 0) throw new CategoryException("该分类下还有图书，不能删除！");
		categoryMapper.delete(cid);
		
	}

	@Override
	public void add(Category category) {
		categoryMapper.add(category);
		
	}

	@Override
	public Category load(String cid) {
		return categoryMapper.load(cid);
	}

	@Override
	public void edit(Category category) {
		categoryMapper.edit(category);
		
	}
	
}
