<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'succ2.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
 <h1>succ2</h1>
 <%
       String uname = (String)session.getAttribute("username");
	        if (uname==null){
	           //保存错误信息在request域并转发到登录页面
				request.setAttribute("msg", "用户名或密码错误！");
				request.getRequestDispatcher("/session/login.jsp").forward(request, response);
	        }else{
	                out.print("欢迎"+uname);
	        }
 %>
  </body>
</html>
