<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Login</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/login.css">
</head>

<body background="image/backimage1.jpg">
	<div class="container">
		<div class='login logo'>
			<img src="image/white2.png" class="login_logo">
		</div>
		<div class="loginbox">
			<form action="${pageContext.request.contextPath }/user?method=login" method="post">
				<p>Email:</p>
				<input type="text" name="email" placeholder="Enter email">
				<p>Password:</p>
				<input type="password" name="pwd" placeholder="Enter Password">
				<input class="btn btn-primary" type="submit" value="login"> 
				<a href="${pageContext.request.contextPath }/user?method=gotoreset">Forget Password?</a> 
				<a href="${pageContext.request.contextPath }/user?method=registerpage">New User Register!</a>
				<%String message = (String) request.getAttribute("message");%>
				<div class="container w3-center">
					<h3>${message }</h3>
				</div>
			</form>
		</div>

	</div>
</body>
</html>
