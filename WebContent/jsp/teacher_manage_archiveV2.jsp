<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Archive Management</title>
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
		<h1>Please select the year and the semester you want to check</h1>
		<form
			action="${pageContext.request.contextPath }/teacher?method=searchclass"
			method="post">
			<input type="text" name="year"
				placeholder="Enter the year (4 digits, such as 2018)"
				style="width: 300px;"> <label> <input type="radio"
				name="semester" id="spring" value="1" checked> Spring
			</label> <label> <input type="radio" name="semester" id="summer"
				value="2"> Summer
			</label> <label> <input type="radio" name="semester" id="fall"
				value="3"> Fall
			</label>
			<button type="submit" class="btn btn-default">Search</button>
		</form>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Class ID</th>
					<th>ClassName</th>
					<th>Create date</th>
					<th>Creator</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${classes }" var="item" varStatus="counter">
					<tr>
						<td><c:out value="${item.lid }" /></td>
						<td><a href="${pageContext.request.contextPath }/teacher?method=details&lid=${item.lid }"><c:out value="${item.name }" /></a></td>
						<td><c:out value="${item.date }" /></td>
						<td><c:out value="${item.uname }" /></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
		
		<h1>Copy an exist class to current semester</h1>
		<form class="form-horizontal"
			action="${pageContext.request.contextPath }/teacher?method=copyclass"
			method="post">
			
			<div class="form-group">
				<label class="control-label col-sm-3" for="lessonName">Class ID</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="name"
						placeholder="Enter the class ID you want to copy" name="id">
				</div>
			</div>
			
			
			<div class="form-group">
				<label class="control-label col-sm-3" for="lessonName">Choose
					the level</label>
				<div class="col-sm-9">
					<select name="level">
						<option value="1">1</option>
						<option value="2">2</option>
						<option value="3">3</option>
						<option value="4">4</option>
					</select>
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="lessonName">New name</label>
				<div class="col-sm-9">
					<input type="text" class="form-control" id="name"
						placeholder="Enter the new name" name="name">
				</div>
			</div>

			<div class="form-group">
				<label class="control-label col-sm-3" for="lessonDescription">New description</label>
				<div class="col-sm-9">
					<input type="text" class="form-control"
						placeholder="Enter the new desciprtion" name="desc">
				</div>
			</div>
			<div class="form-group">
				
				<input class="btn btn-default navbar-btn" type="submit" value="Copy" />
			</div>
		</form>
	</div>
</body>
</html>