<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Welcome</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>

.btn-sq {
	width: 400px;
	height: 400px;
}

.welcomeMessage{
	border: 1px solid black;
	display: block;
	margin: -10px auto;
	margin-top: -50px;
	margin-left: 20px;
	margin-right: 20px;
	
	border-radius: 20px 20px;
	padding: 20px;
	text-align: left;
}

.btn-primary{
	margin-top: 70px;
	margin-left: 20px;
	margin-right: 40px;
	background-color: #496e9c;
	color: white;
	font-size: 16px;
}

.student_logo {
	margin: 5px auto;
	margin-top: -70px;
	display: block;
}

body {
	background-image: url("gray.jpg");
}
</style>
 
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.0/jquery.min.js"></script>
 
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

	<nav class="navbar navbar-inverse">
		 
		<div class="container-fluid">
			   
			<div class="navbar-header">
				      <a class="navbar-brand"
					href="${pageContext.request.contextPath }/student">ALT</a>    
			</div>
			<ul class="nav navbar-nav navbar-right">
				      
				<li><a href="#"><span class="glyphicon glyphicon-user"></span>
						<%
							if (session == null || session.getAttribute("user") == null) {
						%> <input type="submit" value="LogIn"> <input
						type="submit" value="SignUp"> <%
 	}
 	if (session.getAttribute("user") != null) {
 		out.println("hello, " + session.getAttribute("user"));
 %> <%
 	}
 %> </a></li>   
 <li>
					<button
						onclick="window.location.href='${pageContext.request.contextPath }/logout'"
						class="btn btn-danger navbar-btn">
						<span class="glyphicon glyphicon-log-out">Logout 
					</button>
				</li>   
			</ul>
			 
		</div>
	</nav>
	

	
	<div class="container w3-center">
				<div class="logo">
			<img src="image/logo.png" class="student_logo" height=300px>
			</div>
			
			<div>
				<p class="welcomeMessage">
				Welcome to the Automated Phonetic Transcription Grading Tool (APTgt).  This learning system allows you to complete phonetic transcription assignments online.  Student assignments will be automatically graded based upon the answer key your instructor provided.  Results will be available immediately for practice assignments or after the expired due date.  You will be able to visualize the de-identified results of the entire class. Visual graphic displays allow you to compare your individual performance to the overall class performance.  
				</p>
			</div>
			<!-- class="w3-btn btn-sq w3-lime w3-circle w3-xxxlarge w3-ripple"> -->
				<a href="${pageContext.request.contextPath }/student?method=lessons"
					
					class="btn btn-primary btn-block">
					
					Assignments <br>
				</a>	 
				<!-- 
				<br> <br> <span class="glyphicon glyphicon-edit"></span><br />
				 <a href="${pageContext.request.contextPath }/student?method=comment"
					class="w3-btn btn-sq w3-cyan w3-circle w3-xxxlarge w3-ripple">
						<br> <br> <span class="glyphicon glyphicon-inbox"></span><br />
						Comment <br></a>  
				
				<a></a>
				 -->
		</div>
	
</body>
</html>