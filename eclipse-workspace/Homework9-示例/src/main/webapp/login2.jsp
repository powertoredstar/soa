<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>3100000</title>
</head>
<body>
<%
	// 该变量保存错误信息，如果登录成功，则为null，否则是错误信息。后面代码会根据该变量的值来判断是否成功登录。 
	String errMsg = null;
%>

<%
	//下面是判断逻辑，是来自提交么post？
 if ("POST".equalsIgnoreCase(request.getMethod())
			// 提交数据不空
			&& request.getParameter("submit") !=null
			)
	{
%>
		<!-- id是名字， class 指定一个类 ，说明要使用一个类-->
		<jsp:useBean id="loginBean" class="cshao.LoginBean">
			<!-- 对相应的属性进行赋值，这里使用了依赖注入 -->
			<jsp:setProperty name="loginBean" property="*"/>			
		</jsp:useBean>
<%
		// 判断参数
		if (loginBean.isValidUser())
		{
			session.setAttribute("user", request.getParameter("user"));
			if (session.getAttribute("orign") != null)
				response.sendRedirect(session.getAttribute("orign").toString());
			else
				response.sendRedirect("index.jsp");
		}
		else
		{
			errMsg = "非法用户或口令不对，重试";			
		}
	}
%>

	<h2>Login</h2>

	<!-- 下面根据errMsg的值来决定是否需要显示错误信息 -->
	<% if(errMsg!=null) { %>
	<span style="color: red;">
		<% out.print(errMsg); %>
	</span>
	<%} %>

	<!-- 下面是数据输入表格，使用http post方法，没有指定url，刚发送到自己的rul -->
	<form method="post">
		User Name: <input type="text" name="user"><br> Password:
		<input type="password" name="password"><br>
		<button type="submit" name="submit">Submit</button>
		<button type="reset">Reset</button>
	</form>

</body>
</html>