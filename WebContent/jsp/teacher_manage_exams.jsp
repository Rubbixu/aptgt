<!-- jsp header -->
<%@ page language="java"  import="edu.auburn.domain.*" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html lang="en">
<head>
  <meta charset="UTF-8">
<meta name="viewport"
      content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
<meta http-equiv="X-UA-Compatible" content="ie=edge">
<title>Manage Exam | Your Site Title Here</title>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom-style.css">
<link rel="stylesheet" href="css/demo-switcher.css">
<link rel="stylesheet" href="css/page-animations.css">
<link rel="stylesheet" href="css/simplebar.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400" rel="stylesheet">
<link rel="stylesheet" href="css/style2.css">
</head>
<body>
<div class="page-wrapper toggled">
  <nav id="sidebar" class="sidebar-wrapper sidebar-bg">
    <a id="show-sidebar" href="#">
      <i class="fas fa-bars"></i>
    </a>
    <div class="sidebar-content">
      <div class="sidebar-header">
        <div id="close-sidebar">
          <i class="fas fa-bars"></i>
        </div>
        <div class="user-pic">
          <img class="img-responsive img-rounded" src="img/teaching/APTlogo.png" alt="User picture">
        </div>
        <div class="user-info">
          <span class="user-name">
            <strong>APT-GT</strong>
          </span>
          <span class="user-role">Auburn University</span>
        </div>

      </div>
      <!-- sidebar-search  -->
      <div class="sidebar-menu">
        <ul>
          
            
<li>
    
        <!--
            @root === the template scope of the current file object being rendered
            @root.nav_path === currentFileObject.nav_path
            ('nav_path' property can be configured by navConfig.pathProperty = 'nav_path')

            path === currentNavNode.path
            (path property is set to all nav nodes)
        -->

        <a href="${pageContext.request.contextPath }/teacher">
            <i class="fa fa-home"></i>
            <span>Homepage</span>
        </a>
    

</li>

          
            
<li>
    
        <!--
            @root === the template scope of the current file object being rendered
            @root.nav_path === currentFileObject.nav_path
            ('nav_path' property can be configured by navConfig.pathProperty = 'nav_path')

            path === currentNavNode.path
            (path property is set to all nav nodes)
        -->

        <a href="${pageContext.request.contextPath }/teacher?method=lessonlist">
            <i class="fa fa-book-open"></i>
            <span>Current Courses</span>
        </a>
    

</li>

          
            
<li>
    
        <!--
            @root === the template scope of the current file object being rendered
            @root.nav_path === currentFileObject.nav_path
            ('nav_path' property can be configured by navConfig.pathProperty = 'nav_path')

            path === currentNavNode.path
            (path property is set to all nav nodes)
        -->

        <a href="${pageContext.request.contextPath }/teacher?method=archive">
            <i class="fa fa-book"></i>
            <span>Archived Courses</span>
        </a>
    

</li>

          
<%
			Lesson l = (Lesson) (request.getAttribute("lesson"));
			int llid = -1;
			if (l!=null){
				llid=l.getLid();
			}
			
			%> 
        <li class="sidebar-dropdown">
              <a href="#">
                <i class="fa fa-chalkboard-teacher"></i>
                <span>Class Management</span>
              </a>
              <div>
<!--               <div class="sidebar-submenu"> -->
                <ul>
                  <li>
                    <a href="${pageContext.request.contextPath }/teacher?method=examlist&lid=<%out.print(llid); %>"><i class="fa fa-file"></i>
                      <span>Manage Exam</span></a>
                  </li>
                  <li>
                    <a href="${pageContext.request.contextPath }/teacher?method=details&lid=<%out.print(llid); %>"><i class="fa fa-folder-open"></i>
                      <span>Manage Files</span></a>
                  </li>
                  <li>
                    <a href="${pageContext.request.contextPath }/teacher?method=managestudent&lid=<% out.print(llid);%>"><i class="fa fa-user"></i>
                      <span>Manage Users</span></a>
                  </li>
                </ul>
              </div>
            </li>
            <li>
              <a href="${pageContext.request.contextPath }/logout">
                <i class="fas fa-sign-out-alt"></i>
                <!--<i class="fa fa-sign"></i>-->
                <span>Log Out</span>
              </a>
            </li>
        </ul>
      </div>
      <!-- sidebar-menu  -->
    </div>
    <!-- sidebar-content  -->
    <div class="sidebar-footer">
      <div>

        <a href="#" class="" id="dropdownMenuNotification" data-toggle="dropdown" aria-haspopup="true"
           aria-expanded="false">
          <i class="fab fa-facebook-f"></i>

        </a>
      </div>
      <div>
        <a href="#" class="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fab fa-twitter"></i>
        </a>
      </div>
      <div>
        <a href="#" class="" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
          <i class="fab fa-linkedin"></i>
        </a>
      </div>
      <div>
        <a href="#">
          <i class="fa fa-envelope"></i>
        </a>
      </div>
    </div>
  </nav>
  <!-- sidebar-wrapper  -->
  <main class="page-holder" data-animation="54">
    
   <div class="pages ay-spinner2" data-animation="54">
      <div id="ap-/jsp/teacher_manage_exams.jsp" class="page ay-current" data-pos="/jsp/teacher_manage_exams.jsp">
        <section class="color-1 ss-style-doublediagonal" >
  <div class="row">
    <div class="col-sm-12 col-md-12">
      <h1 class="page-title"><%out.println(l.getName()); %></h1>
      <h2 class="page-item">Exams</h2>
    </div>
  </div>
</section>
<section class="color-11 ss-style-doublediagonal" >
  <div class="row">
    <div class="col-sm-12 col-md-12">
        <div class="container-fluid">
            <div class="headings">
              <a>Exam</a>
              <a>Practice?</a>
              <a>Due Date</a>
              <a>Created by</a>
              <a>Delete</a>
              <a>Result</a>
              <a>Edit</a>
            </div><br><br>
            
            <div class="listallco">
              <div class="allcourse">
              <c:forEach items="${list }" var="item" varStatus="counter">
                <div id="view1">
                  <a href="${pageContext.request.contextPath }/teacher?method=examdetails&lid=<%out.print(l.getLid()); %>&eid=${item.eid}"><c:out
									value="${item.name }" /></a>
                  <a>${item.ifPractice}</a>
                  <a>${item.edue }</a>
                  <a>${item.uname }</a>
                  <a href="${pageContext.request.contextPath }/teacher?method=delexam&lid=<%out.print(l.getLid()); %>&eid=${item.eid}">x</a>
                  <a href="${pageContext.request.contextPath }/teacher?method=checke&lid=<%out.print(l.getLid()); %>&eid=${item.eid}">check</a>
                  <a href="${pageContext.request.contextPath }/teacher?method=edite&lid=<%out.print(l.getLid()); %>&eid=${item.eid}">edit</a>
                </div><br>
              </c:forEach>
              </div>
            </div><br><br><br><br>
          <div class="add-test">
            <button class="fas fa-plus" id="add-test" onclick="openTest()"> Add Exam </button>
            <div class="form-create" id="crexam">
              <form action="${pageContext.request.contextPath }/teacher?method=addexam&lid=<%out.print(l.getLid()); %>"
				method="post" class="form-container">
                <h1>Create Test</h1><br>
                <lable for="e-type">Exam Type:</lable>
                <select id="e-type" name="isprac">
                  <option value="0">Practice</option>
                  <option value="1">Exam</option>
                </select><br><br>
                <label for="k-type">Keyboard:</label>
                <select id="k-type" name="etype">
                  <option value="0">Basic</option>
                  <option value="1">Intermediate</option>
                  <option value="2">Advance</option>
                </select><br><br>
                <label for="ddate">Due Date: </label>
                <input type="datetime-local" name="edue" value="2019-05-05T00:00"><br><br>
                <label for="examname">Test Name:</label> <input type="text" placeholder="Enter Exam Name" name="ename" required><br><br>
                <label for="examdesc">Test Description:</label> <input type="text" placeholder="Enter Exam Description" name="edesc" required><br><br><br>
                <button type="submit" class="create">Create</button>
                <button type="button" class="cancel" onclick="closeTest()">Close</button>
              </form>
            </div>
          </div>
          <script>
            function openTest() {
              document.getElementById("crexam").style.display = "block";
            }
            function closeTest() {
              document.getElementById("crexam").style.display = "none";
            }
          </script>
        </div>
    </div>
  </div>
</section>

      </div>
    </div>
    <footer class="fdb-block footer-small bg-dark">
      <div class="container">
        <div class="row align-items-center">
          <div class="col-12 col-md-8">
            <ul class="nav justify-content-center justify-content-md-start">
              <li class="nav-item">
                <a class="nav-link active" href="https://www.froala.com">Home</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="https://www.froala.com">Features</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="https://www.froala.com">Terms</a>
              </li>
              <li class="nav-item">
                <a class="nav-link" href="https://www.froala.com">About</a>
              </li>
            </ul>
          </div>

          <div class="col-12 col-md-4 mt-4 mt-md-0 text-center text-md-right">
            Â© 2019 Tian. All Rights Reserved
          </div>
        </div>
      </div>
    </footer>
  </main>

</div>

<div id="demo-switcher" class="demo-switcher d-none d-sm-block">
  <a id="styleSwitcherOpen" class="demo-switcher-open" href="#"><i class="fas fa-cog fa-spin"></i></a>
  <div class="demo-switcher-wrap">

    <h4>Style Switcher</h4>

    <h5>Sidebar Background Color</h5>

    <a href="#" data-theme="chiller" class="theme chiller-theme selected"></a>
    <a href="#" data-theme="ice" class="theme ice-theme"></a>
    <a href="#" data-theme="cool" class="theme cool-theme"></a>
    <a href="#" data-theme="light" class="theme light-theme"></a>

    <h5>Sidebar Background Image</h5>
    <a href="#" data-bg="1" class="theme theme-bg selected"></a>
    <a href="#" data-bg="2" class="theme theme-bg"></a>
    <a href="#" data-bg="3" class="theme theme-bg"></a>
    <a href="#" data-bg="4" class="theme theme-bg"></a>

    <div class="form-group">
      <div class="custom-control custom-checkbox">
        <input class="custom-control-input" type="checkbox" id="menu-background">
        <label class="custom-control-label" for="menu-background">Menu item background</label>
      </div>
      <div class="custom-control custom-checkbox">
        <input class="custom-control-input" type="checkbox" checked id="toggle-bg">
        <label class="custom-control-label" for="toggle-bg">Background image</label>
      </div>
    </div>
    <h4>Page Loader</h4>

    <h5>Transition Animation</h5>
    <div class="btn-container">
      <button class="btn btn-outline-secondary btn-sm selected" data-loader="1">1</button>
      <button class="btn btn-outline-secondary btn-sm" data-loader="2">2</button>
      <button class="btn btn-outline-secondary btn-sm" data-loader="3">3</button>
      <button class="btn btn-outline-secondary btn-sm" data-loader="4">4</button>
      <button class="btn btn-outline-secondary btn-sm" data-loader="5">5</button>
      <button class="btn btn-outline-secondary btn-sm" data-loader="6">6</button>
    </div>

    <h5>Preloading style</h5>
    <div class="btn-container">
      <button class="btn btn-outline-secondary btn-sm page-loader selected" data-loader="0">0</button>
      <button class="btn btn-outline-secondary btn-sm page-loader " data-loader="1">1</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="2">2</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="3">3</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="4">4</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="5">5</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="6">6</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="7">7</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="8">8</button>
      <button class="btn btn-outline-secondary btn-sm page-loader" data-loader="9">9</button>
    </div>
    <div class="demo-switcher-buttons options-links">
      <a href="loading.html" data-ispage="true" class="btn-block">Loading Page</a>

      <a data-toggle="modal" data-target="#cssModal" class="btn-block getCSS"><i class="fas fa-file-alt"></i> Get Skin
        CSS</a>
    </div>

    <!-- Modal -->
    <div class="modal fade" id="cssModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
         aria-hidden="true">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="exampleModalLabel">Stylesheet for current options</h5>
            <button type="button" class="close" data-dismiss="modal" aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <div class="modal-body">

            <div class="form-group">
              <label for="exampleFormControlTextarea1">Generated CSS</label>
              <textarea class="form-control" id="demoCSS" rows="10">loading...</textarea>
            </div>
            <div class="alert alert-info" style="margin-bottom: 0">You need to update custom-style.css with
              this one to work.
            </div>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-sm btn-secondary" data-dismiss="modal">Close</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>
<script>
    window.paceOptions = {
        ajax: false, // disabled
        document: false, // disabled
        eventLag: false, // disabled
        elements: false,
        restartOnRequestAfter: true,
        restartOnPushState: false
    };</script>
<script type="text/javascript" src="js/bundle.js"></script>
<!-- <script type="text/javascript" src="js/ay-pages.js"></script> -->
<script type="text/javascript" src="js/main.js"></script>
<!-- <script type="text/javascript" src="js/demo-switcher.js"></script> -->
</body>
</html>
