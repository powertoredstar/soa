<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Logout</title>
</head>
<body>

<%
	session.invalidate();
%>

<p>你已退出</p>
登录请点击<a href = "index.jsp">登录</a>

</body>
</html>