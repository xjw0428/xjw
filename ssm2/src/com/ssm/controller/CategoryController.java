package com.ssm.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ssm.service.CategoryException;
import cn.itcast.commons.CommonUtils;

import com.ssm.po.Category;
import com.ssm.po.Item;
import com.ssm.service.CategoryService;
@Controller
public class CategoryController {
	@Autowired
	private CategoryService categoryService ;
	
	
	@RequestMapping("/findAllCategory.action")
	public ModelAndView findAllCategory() {
		ModelAndView mav = new ModelAndView();
		try {
			List<Category> categoryList = categoryService.findAll();
			mav.addObject("categoryList",categoryList);
			mav.setViewName("/jsps/left");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	@RequestMapping("/admin/findAllCategory.action")
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 调用service方法，得到所有分类
		 * 2. 保存到request域，转发到/adminjsps/admin/category/list.jsp
		 */
		request.setAttribute("categoryList", categoryService.findAll());
		return "forward:/adminjsps/admin/category/list.jsp";
	}
	
	@RequestMapping("/admin/addCategory.action")
	public String add(HttpServletRequest request, HttpServletResponse response,Category category)
			throws Exception {
		/*
		 * 1. 封装表单数据
		 * 2. 补全：cid
		 * 3. 调用service方法完成添加工作
		 * 4. 调用findAll()
		 */
		//Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.add(category);
		return findAll(request, response);
	}
	@RequestMapping("/admin/delete.action")
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. 获取参数:cid
		 * 2. 调用service方法，传递cid参数
		 *   > 如果抛出异常，保存异常信息，转发到msg.jsp显示
		 * 3. 调用findAll()
		 */
		String cid = request.getParameter("cid");
		try {
			categoryService.delete(cid);
			return findAll(request, response);
		} catch(CategoryException e) {
			request.setAttribute("msg", e.getMessage());
			return "forward:/adminjsps/msg.jsp";
		}
	}
	/**
	 * 修改之前的加载工作
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping("/admin/editPre.action")
	public String editPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("category", categoryService.load(cid));
		return "forward:/adminjsps/admin/category/mod.jsp";
	}
	@RequestMapping("/admin/edit.action")
	public String edit(HttpServletRequest request, HttpServletResponse response,Category category)
			throws Exception {
		/*
		 * 1. 封装表单数据
		 * 2. 调用service方法完成修改工作
		 * 3. 调用findAll
		 */
		categoryService.edit(category);
		return findAll(request, response);
	}
}
