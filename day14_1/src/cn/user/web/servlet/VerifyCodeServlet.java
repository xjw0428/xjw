package cn.user.web.servlet;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.itcast.vcode.utils.VerifyCode;

public class VerifyCodeServlet extends HttpServlet {


	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	    //������֤���� 
		VerifyCode vc = new VerifyCode();
		//�õ���֤��ͼƬ
		BufferedImage image = vc.getImage();
		//��ͼƬ�ϵ��ı����浽session
		request.getSession().setAttribute("session_vcode", vc.getText());
		//��ͼƬ��Ӧ���ͻ���
		VerifyCode.output(image, response.getOutputStream());
	}


}
