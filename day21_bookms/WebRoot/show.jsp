<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'show.jsp' starting page</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<meta http-equiv="Content-Type" content="text/html;charset=UTF-8">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
<h1 align="center">图书列表</h1>
<table border="1" align="center" width="50%">
  <tr>
      <th>书名</th>
      <th>单价</th>
      <th>分类</th>
  </tr>
<c:forEach items="${bookList}" var = "book">
  <tr>
     <td>${book.bname}</td>
     <td>${book.price}</td>
     
     <c:choose>
        <c:when test="${book.category eq 1}"><td style="color:red">JavaSE</td></c:when>
        <c:when test="${book.category eq 2}"><td style="color:green">JavaEE</td></c:when>
        <c:when test="${book.category eq 3}"><td style="color:blue">JavaFrameWork</td></c:when>
     </c:choose>
    
  </tr>
 </c:forEach> 
</table>

  </body>
</html>
