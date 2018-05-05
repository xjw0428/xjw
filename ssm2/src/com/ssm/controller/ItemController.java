package com.ssm.controller;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import cn.itcast.commons.CommonUtils;

import com.ssm.po.*;
import com.ssm.service.CategoryService;
import com.ssm.service.ItemService;


@Controller
public class ItemController {
	
	@Autowired
	private ItemService itemService;
	@Autowired
	private CategoryService categoryService;
	
	@RequestMapping("/findAllItem.action")
	public ModelAndView showGood() {
		ModelAndView mav = new ModelAndView();
		try {
			List<Item> itemList = itemService.findAll();
			mav.addObject("itemList",itemList);
			mav.setViewName("/jsps/item/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	
	@RequestMapping("/findByCategory.action")
	public ModelAndView findByCategory(String cid)
	{   
		ModelAndView mav = new ModelAndView(); 
		//String cid=req.getParameter("cid");
		//System.out.println(cid);
		try {
			mav.addObject("itemList",itemService.findByCategory(cid));
			mav.setViewName("/jsps/item/list");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
}
	@RequestMapping("/itemSearch.action")
	public String itemSearch(String bname,HttpServletRequest request){
		List<Item> itemList = itemService.findByName(bname);
		
		if(itemList.isEmpty()){
			request.setAttribute("msg", "δ��ѯ������Ҫ��Ľ����");
			return  "forward:/jsps/item/search.jsp";
		}else{
			request.setAttribute("itemList", itemList);
			return  "forward:/jsps/item/list.jsp";
		}
		}
		
	
	@RequestMapping("/load.action")
	public ModelAndView load(String bid)
	{   
		ModelAndView mav = new ModelAndView(); 
		try {
			mav.addObject("item",itemService.load(bid));
			mav.setViewName("/jsps/item/desc");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
}
	@RequestMapping("/admin/findAllItem.action")
	public String findAll(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setAttribute("itemList", itemService.findAll());
		return "forward:/adminjsps/admin/item/list.jsp";
	}
	@RequestMapping("/admin/loadItem.action")
	public String load(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * 1. ��ȡ����bid��ͨ��bid����service�����õ�Book����
		 * 2. ��ȡ���з��࣬���浽request����
		 * 3. ����book��request���У�ת����desc.jsp
		 */
	
		request.setAttribute("item", itemService.load(request.getParameter("bid")));
	
		request.setAttribute("categoryList", categoryService.findAll());
		return "forward:/adminjsps/admin/item/desc.jsp";
	}
	@RequestMapping("/admin/editItem.action")
	public String edit(HttpServletRequest request, HttpServletResponse response,Item item,Category category)
			throws Exception {
		//Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		//Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		item.setCategory(category);
		
		itemService.edit(item);
		
			return findAll(request, response);
	
	}
	
	@RequestMapping("/admin/addPreItem.action")
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		/*
		 * ��ѯ���з��࣬���浽request��ת����add.jsp
		 * add.jsp�а����еķ���ʹ�������б���ʾ�ڱ���
		 */
		request.setAttribute("categoryList", categoryService.findAll());
		return "forward:/adminjsps/admin/item/add.jsp";
	}
	
	@RequestMapping("/admin/addItem.action")
	public void doPost(Item item,Category category,HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		/*
		 * 1. �ѱ����ݷ�װ��Book������
		 *   * �ϴ�����
		 */
		// ��������
		DiskFileItemFactory factory = new DiskFileItemFactory(100 * 1024, new File("E:/e/temp"));
		// �õ�������
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// ���õ����ļ���СΪ15KB
		sfu.setFileSizeMax(80 * 1024);
		// ʹ�ý�����ȥ����request���󣬵õ�List<FileItem>
		System.out.println("test1");
		try {
			System.out.println("test2");
			List<FileItem> fileItemList = sfu.parseRequest(request);
			/*
			 * * ��fileItemList�е����ݷ�װ��Book������
			 *   > �����е���ͨ���ֶ������ȷ�װ��Map��
			 *   > �ٰ�map�е����ݷ�װ��Book������
			 */
			Map<String,String> map = new HashMap<String,String>();
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				}
			}

			item.setBid(CommonUtils.uuid());
			/*
			 * ��Ҫ��Map�е�cid��װ��Category�����У��ٰ�Category����Book
			 */
			item.setCategory(category);
			
			/*
			 * 2. �����ϴ����ļ�
			 *   * �����Ŀ¼
			 *   * ������ļ�����
			 */
			// �õ������Ŀ¼
			String savepath =  request.getSession().getServletContext().getRealPath("/book_img");
			System.out.println(savepath);
			// �õ��ļ����ƣ���ԭ���ļ��������uuidǰ׺�������ļ�����ͻ
			String filename = CommonUtils.uuid() + "_" + fileItemList.get(1).getName().substring(24);
			System.out.println(fileItemList.get(1).getName());
			System.out.println(filename);
			
			/*
			 * У���ļ�����չ��
			 */
			if(!filename.toLowerCase().endsWith("jpg")) {
				request.setAttribute("msg", "���ϴ���ͼƬ����JPG��չ����");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
			
			
			// ʹ��Ŀ¼���ļ����ƴ���Ŀ���ļ�
			File destFile = new File(savepath, filename);
			System.out.println("test3");
			System.out.println(destFile.getPath());
			System.out.println(destFile.getAbsolutePath());
			// �����ϴ��ļ���Ŀ���ļ�λ��
			fileItemList.get(1).write(destFile);
			System.out.println("test4");
			
			/*
			 * 3. ����Book�����image������ͼƬ��·�����ø�Book��image
			 */
			System.out.println(filename);
			item.setImage("book_img/" + filename);
			System.out.println("test5");
			
			
			
			/*
			 * 4. ʹ��BookService��ɱ���
			 */
			
			itemService.add(item);
			System.out.println("book");
			
			/*
			 * У��ͼƬ�ĳߴ�
			 */
			Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
			if(image.getWidth(null) > 800 || image.getHeight(null) > 800) {
				destFile.delete();//ɾ������ļ���
				request.setAttribute("msg", "���ϴ���ͼƬ�ߴ糬����800 * 800��");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
			
			
			/*
			 * 5. ���ص�ͼ���б�
			 */
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll")
					.forward(request, response);
		} catch (Exception e) { 
			if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("msg", "���ϴ����ļ�������80KB");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
		}
	}
}