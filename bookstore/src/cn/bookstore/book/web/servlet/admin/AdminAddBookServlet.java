package cn.bookstore.book.web.servlet.admin;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import cn.bookstore.book.domain.Book;
import cn.bookstore.book.service.BookService;
import cn.bookstore.category.domain.Category;
import cn.bookstore.category.service.CategoryService;
import cn.itcast.commons.CommonUtils;

public class AdminAddBookServlet extends HttpServlet {
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
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

			Book book = CommonUtils.toBean(map, Book.class);
			// Ϊbookָ��bid
			book.setBid(CommonUtils.uuid());
			/*
			 * ��Ҫ��Map�е�cid��װ��Category�����У��ٰ�Category����Book
			 */
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			
			/*
			 * 2. �����ϴ����ļ�
			 *   * �����Ŀ¼
			 *   * ������ļ�����
			 */
			// �õ������Ŀ¼
			String savepath = this.getServletContext().getRealPath("/book_img");
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
			book.setImage("book_img/" + filename);
			System.out.println("test5");
			
			
			
			/*
			 * 4. ʹ��BookService��ɱ���
			 */
			
			bookService.add(book);
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
