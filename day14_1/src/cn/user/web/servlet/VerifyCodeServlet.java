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

	    //创建验证码类 
		VerifyCode vc = new VerifyCode();
		//得到验证吗图片
		BufferedImage image = vc.getImage();
		//将图片上的文本保存到session
		request.getSession().setAttribute("session_vcode", vc.getText());
		//把图片响应给客户端
		VerifyCode.output(image, response.getOutputStream());
	}


}
