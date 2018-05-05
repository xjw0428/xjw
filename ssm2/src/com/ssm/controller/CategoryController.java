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
		 * 1. ����service�������õ����з���
		 * 2. ���浽request��ת����/adminjsps/admin/category/list.jsp
		 */
		request.setAttribute("categoryList", categoryService.findAll());
		return "forward:/adminjsps/admin/category/list.jsp";
	}
	
	@RequestMapping("/admin/addCategory.action")
	public String add(HttpServletRequest request, HttpServletResponse response,Category category)
			throws Exception {
		/*
		 * 1. ��װ������
		 * 2. ��ȫ��cid
		 * 3. ����service���������ӹ���
		 * 4. ����findAll()
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
		 * 1. ��ȡ����:cid
		 * 2. ����service����������cid����
		 *   > ����׳��쳣�������쳣��Ϣ��ת����msg.jsp��ʾ
		 * 3. ����findAll()
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
	 * �޸�֮ǰ�ļ��ع���
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
		 * 1. ��װ������
		 * 2. ����service��������޸Ĺ���
		 * 3. ����findAll
		 */
		categoryService.edit(category);
		return findAll(request, response);
	}
}
