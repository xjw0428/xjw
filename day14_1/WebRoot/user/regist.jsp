<%@ page language="java" import="java.util.*" pageEncoding="utf-8" isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'regist.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
<script type="text/javascript">
    function _change(){

       var ele =document.getElementById("vCode");
      ele.src="<c:url value='/VerifyCodeServlet'/>?xxx="+new Date().getTime();
     
      
    }

</script>  
  <body>
 <h1>注册</h1>
 <!--${pageContext.request.contextPath}/RegistServlet  -->
 <p style="color:red;font-weight:900">${msg}</p>
 <form action="<c:url value='/RegistServlet'/>" method="post">
 用户名： <input type="text" name="username" value="${user.username }"/>${errors.username }</br>
 密    码: <input type="password" name="password" value="${user.password }"/>${errors.password }</br>
 验证码：<input type="text" name="verifyCode" value="${user.verifyCode }" size="3" />
  <img id="vCode" src="<c:url value='/VerifyCodeServlet'/>" border="2" />
  <a href="javascript:_change()">换一张</a></br>
  <input type="submit" value="注册"/>
  </form>
 
  </body>
</html>
