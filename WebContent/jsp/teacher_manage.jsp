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


.welcomeMessage {
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

.teacher_logo {
	margin: 5px auto;
	margin-top: -70px;
	display: block;
}


.btn-primary {
	margin-top: 20px;
		margin-left: 20px;
	margin-right: 40px;
}
.btn-secondary {
	margin-top: 10px;
	background-color: yellow;
	margin-left: 20px;
	margin-right: 40px;
	
	
}


.btn-primary {
	margin-top: 70px;
	margin-left: 20px;
	margin-right: 40px;
	background-color: #496e9c;
	color: white;
	font-size: 16px;
}

.btn-secondary {
	margin-top: 20px;
	background-color: yellow;
	margin-left: 20px;
	margin-right: 40px;
	background-color:#f68026;
	color: white;
	font-size: 16px;
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
					href="${pageContext.request.contextPath }/teacher">ALT</a>    
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
				<img src="image/logo.png" class="teacher_logo" height=300px>
			</div>
			
			<div>
				<p class="welcomeMessage">
				Welcome to the Automated Phonetic Transcription Grading Tool (APTgt).  This learning system allows you to build phonetic transcription assignments, add audio/video recordings, and create an answer key.  Student assignments will be automatically graded based upon the answer key you provide. There are three key boards: beginner (broad transcription), advanced (narrow transcription), and the full IPA keyboard. You are able to set the date and time that the assignments are due and provide immediate or delayed feedback of the performance results.  Students are able to visualize the de-identified results of the class and visualize graphic displays of individual and class performance.  
				</p>
			</div>

			
				<a
					href="${pageContext.request.contextPath }/teacher?method=lessonlist"
					class=" btn btn-primary btn-block">

				
					Current Semester 
					

				</a> 
			    <br>
					
				
			<!--	<a href="${pageContext.request.contextPath }/teacher?method=comments"
					class="w3-btn btn-sq w3-cyan w3-circle w3-xxxlarge w3-ripple">
					<br> <br> <span class="glyphicon glyphicon-inbox"></span><br />

					Comments <br>
					
					
					
						<br> <br> <span class="glyphicon glyphicon-edit"></span><br />
						<br> <br> <span class="glyphicon glyphicon-user"></span><br />
 
				</a>
			 -->	
				<a href="${pageContext.request.contextPath }/teacher?method=archive"
					class=" btn btn-secondary btn-block">
					

					Archived Semesters <br>
				</a>
				
			
		</div>
</body>
</html>