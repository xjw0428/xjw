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
			request.setAttribute("msg", "未查询到符合要求的结果！");
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
		 * 1. 获取参数bid，通过bid调用service方法得到Book对象
		 * 2. 获取所有分类，保存到request域中
		 * 3. 保存book到request域中，转发到desc.jsp
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
		 * 查询所有分类，保存到request，转发到add.jsp
		 * add.jsp中把所有的分类使用下拉列表显示在表单中
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
		 * 1. 把表单数据封装到Book对象中
		 *   * 上传三步
		 */
		// 创建工厂
		DiskFileItemFactory factory = new DiskFileItemFactory(100 * 1024, new File("E:/e/temp"));
		// 得到解析器
		ServletFileUpload sfu = new ServletFileUpload(factory);
		// 设置单个文件大小为15KB
		sfu.setFileSizeMax(80 * 1024);
		// 使用解析器去解析request对象，得到List<FileItem>
		System.out.println("test1");
		try {
			System.out.println("test2");
			List<FileItem> fileItemList = sfu.parseRequest(request);
			/*
			 * * 把fileItemList中的数据封装到Book对象中
			 *   > 把所有的普通表单字段数据先封装到Map中
			 *   > 再把map中的数据封装到Book对象中
			 */
			Map<String,String> map = new HashMap<String,String>();
			for(FileItem fileItem : fileItemList) {
				if(fileItem.isFormField()) {
					map.put(fileItem.getFieldName(), fileItem.getString("UTF-8"));
				}
			}

			item.setBid(CommonUtils.uuid());
			/*
			 * 需要把Map中的cid封装到Category对象中，再把Category赋给Book
			 */
			item.setCategory(category);
			
			/*
			 * 2. 保存上传的文件
			 *   * 保存的目录
			 *   * 保存的文件名称
			 */
			// 得到保存的目录
			String savepath =  request.getSession().getServletContext().getRealPath("/book_img");
			System.out.println(savepath);
			// 得到文件名称：给原来文件名称添加uuid前缀！避免文件名冲突
			String filename = CommonUtils.uuid() + "_" + fileItemList.get(1).getName().substring(24);
			System.out.println(fileItemList.get(1).getName());
			System.out.println(filename);
			
			/*
			 * 校验文件的扩展名
			 */
			if(!filename.toLowerCase().endsWith("jpg")) {
				request.setAttribute("msg", "您上传的图片不是JPG扩展名！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
			
			
			// 使用目录和文件名称创建目标文件
			File destFile = new File(savepath, filename);
			System.out.println("test3");
			System.out.println(destFile.getPath());
			System.out.println(destFile.getAbsolutePath());
			// 保存上传文件到目标文件位置
			fileItemList.get(1).write(destFile);
			System.out.println("test4");
			
			/*
			 * 3. 设置Book对象的image，即把图片的路径设置给Book的image
			 */
			System.out.println(filename);
			item.setImage("book_img/" + filename);
			System.out.println("test5");
			
			
			
			/*
			 * 4. 使用BookService完成保存
			 */
			
			itemService.add(item);
			System.out.println("book");
			
			/*
			 * 校验图片的尺寸
			 */
			Image image = new ImageIcon(destFile.getAbsolutePath()).getImage();
			if(image.getWidth(null) > 800 || image.getHeight(null) > 800) {
				destFile.delete();//删除这个文件！
				request.setAttribute("msg", "您上传的图片尺寸超出了800 * 800！");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
				return;
			}
			
			
			/*
			 * 5. 返回到图书列表
			 */
			request.getRequestDispatcher("/admin/AdminBookServlet?method=findAll")
					.forward(request, response);
		} catch (Exception e) { 
			if(e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("msg", "您上传的文件超出了80KB");
				request.setAttribute("categoryList", categoryService.findAll());
				request.getRequestDispatcher("/adminjsps/admin/book/add.jsp")
						.forward(request, response);
			}
		}
	}
}