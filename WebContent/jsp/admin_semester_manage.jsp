<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html lang="en">
<head>
<title>Semester Management</title>
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
				      <a class="navbar-brand"
					href="${pageContext.request.contextPath }/admin">ALT</a>
				   
			</div>
			<button onclick="window.location.href='${pageContext.request.contextPath }/admin'" class="w3-button w3-blue">Return Main Page</button>
			<!-- 
			<button onclick="window.location.href='/edu_system/LessonManageServlet'" class="w3-button w3-green">Edit Lessons</button>
			 -->
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
		<h1>Semester Management</h1>
		<table class="table table-hover">
			<thead>
				<tr>
					<th>Year</th>
					<th>Semester</th>
					<th>Is current</th>
					<th>Set as current</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${list }" var="item" varStatus="counter">
					<tr>
						<td><c:out value="${item.year }" /></td>
						<td><c:out value="${item.semesterName }" /></td>
						<td><c:choose>
								<c:when test="${item.iscurrent == '0'}">
									<c:out value="No" />
								</c:when>
								<c:otherwise>
									<c:out value="Yes" />
								</c:otherwise>
							</c:choose>
						</td>
						<td><a href="${pageContext.request.contextPath }/admin?method=setcurrent&semesterid=${item.id}">Set</a></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>

		<form
			action="${pageContext.request.contextPath }/admin?method=addSemester"
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
			<button type="submit" class="btn btn-default">Add</button>
		</form>
	</div>

</body>
</html>