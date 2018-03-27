package cn.bookstore.category.web.servlet.admin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.bookstore.category.domain.Category;
import cn.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;



public class AdminCategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. ����service�������õ����з���
		 * 2. ���浽request��ת����/adminjsps/admin/category/list.jsp
		 */
		request.setAttribute("categoryList", categoryService.findAll());
		return "f:/adminjsps/admin/category/list.jsp";
	}
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. ��װ������
		 * 2. ��ȫ��cid
		 * 3. ����service���������ӹ���
		 * 4. ����findAll()
		 */
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		categoryService.add(category);
		return findAll(request, response);
	}
	/**
	 * ɾ������
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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
			return "f:/adminjsps/msg.jsp";
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
	public String editPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		request.setAttribute("category", categoryService.load(cid));
		return "f:/adminjsps/admin/category/mod.jsp";
	}
	public String edit(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1. ��װ������
		 * 2. ����service��������޸Ĺ���
		 * 3. ����findAll
		 */
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		categoryService.edit(category);
		return findAll(request, response);
	}
}
