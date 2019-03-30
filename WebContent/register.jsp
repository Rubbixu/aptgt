<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<html>
<head>
<title>Register</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<link rel="stylesheet" type="text/css" href="css/register.css">
</head>

<body background="image/backimage.jpg">
	<div class='login logo'>
		<img src="image/white2.png" class="login_logo">
	</div>
	<div class="registerbox">
		<form action="${pageContext.request.contextPath }/user?method=register" method="post">
			<p>Username:</p>
			<input type="text" name="email" placeholder="Enter email">
			<p>Password:</p>
			<input type="password" name="pwd" placeholder="Enter Password">
			<p>Re-Enter Password:</p>
			<input type="password" name="confirm" placeholder="Enter Password Again" /> 
			<input type="submit" class="btn btn-primary" name="submit" value="Register" /> 
			<a href="${pageContext.request.contextPath }/user?method=login"><u>Have an account, login here.</u></a>
		</form>
		<%String message = (String)request.getAttribute("message"); %>
		<div class="container w3-center">
		<h3>${message }</h3>
		</br> </br> </br>

	</div>
	</div>
</body>
</html>
