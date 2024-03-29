﻿<%@page import="edu.auburn.domain.Lesson"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Edit Course</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
 
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
<style>
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
				      <a class="navbar-brand" href="${pageContext.request.contextPath }/teacher">ALT</a>    
			</div>
			   
			<button
				onclick="window.location.href='${pageContext.request.contextPath }/teacher'"
				class="w3-button w3-pink">Return Main Page</button>
			<!-- <button class="w3-button w3-blue">Manage Exams</button>  -->
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
		<% Lesson lesson = (Lesson) (request.getAttribute("lesson")); %>
		<h1>Current Course</h1>
		<div><%out.print(lesson.getName()); %></div>		
		<div><%out.print(lesson.getDesc()); %></div>		
		<div class="wells">
			<form class="form-horizontal"
				action="${pageContext.request.contextPath }/teacher?method=updatelesson&lid=<%out.print(lesson.getLid()); %>"
				method="post">
				<div class="form-group">
					<label class="control-label col-sm-3" for="lessonName">Course
						Name:</label>
					<div class="col-sm-9">
						<input type="lessonName" class="form-control" id="lessonName"
							placeholder="Enter Lesson Name" name="lname">
					</div>
				</div>
				<div class="form-group">
					<label class="control-label col-sm-3" for="lessonDescription">Course
						Description:</label>
					<div class="col-sm-9">
						<input type="lessonDescription" class="form-control"
							id="lessonDescription" placeholder="Enter Lesson Desciprtion"
							name="ldesc">
					</div>
				</div>
				<div class="w3-center">
					<!-- 					<button class="w3-button w3-circle w3-green">+</button>
 -->
					<input class="w3-button w3-circle w3-green" type="submit" value="update" />
				</div>
			</form>
		</div>
	</div>
</body>
</html>