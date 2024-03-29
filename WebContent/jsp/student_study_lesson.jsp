﻿<%@ page language="java" import="edu.auburn.domain.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Lesson Operations</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">

<style>
body {
	background-image: url("gray.jpg");
}
video::-internal-media-controls-download-button {
    display:none;
}

video::-webkit-media-controls-enclosure {
    overflow:hidden;
}

video::-webkit-media-controls-panel {
    width: calc(100% + 30px); /* Adjust as needed */
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
			<%
				Lesson l = (Lesson) (request.getAttribute("lesson"));
			%>
			   
			<button
				onclick="window.location.href='${pageContext.request.contextPath }/student'"
				class="w3-button w3-blue">Main Page</button>
			<button
				onclick="window.location.href='${pageContext.request.contextPath }/student?method=lessons'"
				class="w3-button w3-pink">Assignments</button>
			<button
				onclick="window.location.href='${pageContext.request.contextPath }/student?method=studentexamlist&lid=<% out.print(l.getLid());%>'"
				class="w3-button w3-green">Exam</button>
			<%-- <button
				onclick="window.location.href='${pageContext.request.contextPath }/teacher?method=managestudent&lid=<% out.print(l.getLid());%>'"
				class="w3-button w3-pink">Manage Users</button>

			<button onclick="window.location.href='${pageContext.request.contextPath }/teacher?method=details&lid=<%out.print(l.getLid()); %>'" class="w3-button w3-green">Manage
				Files</button>
				
			<button onclick="window.location.href='${pageContext.request.contextPath }/teacher?method=examlist&lid=<% out.print(l.getLid());%>'" class="w3-button w3-yellow">Manage
				Exams</button> --%>
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
	<div class="container">
		<h1>
			<%
				out.print(l.getName());
			%>
		</h1>

		<p>
			<%
				out.print(l.getDesc());
			%>
		</p>
		
		
		<c:forEach items="${files }" var="item" varStatus="counter">
			<video height="30%" width="30%" controls="controls">
				<source src="${item.path}" type="video/ogg" />
				<source src="${item.path}" type="video/mp4" />
				Your browser does not support the video tag.
			</video>
			<p>File description: <b>${item.fdesc}</b></p>
			<br>
		</c:forEach>
		
		
		
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Other file</th>
					<th>Action</th>
				</tr>
			</thead>
			<tbody>


				<c:forEach items="${dFile }" var="item" varStatus="counter">
					<tr>
						<td>${item.name }</td>
						<td><c:url var="url" value="/student">
								<c:param name="method" value="download"></c:param>
								<c:param name="fid" value="${item.fid }"></c:param>
							</c:url> <a style="color: blue;" href="${url }">download</a></td>
					</tr>
				</c:forEach>
			</tbody>
			
		</table>
	</div>

</body>
</html>