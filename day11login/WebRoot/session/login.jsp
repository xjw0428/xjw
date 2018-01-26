<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="java.net.*" %>
<%

String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";

 
  
    String message="";
    String msg =(String) request.getAttribute("msg");
    if(msg!=null){
     message=msg;
    }
   

   
    String un = "";
    Cookie[] cs=request.getCookies();
    if(cs!=null){
     /*  for(Cookie c:cs){
         if("uname".equals(c.getName())){
               un=c.getValue();
               break;
               
          }
      } */
      for(int i = 0; i < cs.length; i ++){
      	//if("uname".equals(cs[i].getName())){
      		un = cs[i].getValue();
      		//System.out.println(cs[i].getName() +":"+ un +"  "+ cs.length);  
      	//} 
      }
      
    } 
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<script type="text/javascript">
		alert(un);
	</script>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'login.jsp' starting page</title>
    
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
  
  <form action="/day11login/LoginServlet" method="post">
    <font color="red"><b><%=message %></b></font><br/>
  
     用户名：<input type="text" name="username" value="<%=un%>"><br/>
     密     码：<input type="password" name="password"><br>
         验证码：<input type="text" name="code" size="2">
         <img src="/day11login/VerifyCodeServlet"/><br/>
    <input type="submit" value="提交">
  </form> 
    
  </body>
</html>
