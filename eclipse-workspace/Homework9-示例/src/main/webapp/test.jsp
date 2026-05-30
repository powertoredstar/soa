<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">

<title>310000 Test</title>
</head>
<body>
<%
	// 这是一个测试是否登录的页面
	// 也可以用 filer过滤器来做
	if (session.getAttribute("user")== null)
	{
		System.out.println("check user");
		session.setAttribute("orign", request.getRequestURL());
		response.sendRedirect("login.jsp");
	}
	else
	{		
		out.println("Welcome: " + session.getAttribute("user"));
		%>
		
		<a id = "logout" href="logout.jsp"> 退出</a>
		
		<%
	}
%>
no no 
</body>
</html>