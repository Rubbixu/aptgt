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
<title>Homepage | Your Site Title Here</title>
<meta name="description" content="A simple static website boilerplate with Metalsmith &amp; Gulp"/>
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/page-animations.css">
<link rel="stylesheet" href="css/style.css">
<link rel="stylesheet" href="css/custom-style.css">
<link rel="stylesheet" href="css/demo-switcher.css">
<link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.2.0/css/all.css">
<link href="https://fonts.googleapis.com/css?family=Roboto:300,400" rel="stylesheet">
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
          <span class="user-name">ALT
            <strong>APT-GT</strong>
          </span>
          <span class="user-role">Auburn University</span>
        </div>
		<%-- <ul class="nav navbar-nav navbar-right">
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
 %> </a></li> --%>
 		</ul>
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
              <div class="sidebar-submenu">
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
      <div id="ap-index.html" class="page ay-current" data-pos="\${pageContext.request.contextPath }/teacher">
        <section class="color-1 " >
    <div class="row">
        <div class="col-sm-12 col-md-12">
            <h1 class="page-title">Welcome to APT!</h1>
            <div class="bio-image">
                <img alt="image" src="img/teaching/APTlogo.png" class="img-fluid">
            </div>
            <div class="content">
                <br><br>
                <h4>Welcome to the Automated Phonetic Transcription Grading Tool (APTgt). This learning system allows you to build phonetic transcription assignments, add audio/video recordings, and create an answer key. Student assignments will be automatically graded based upon the answer key you provide. There are three key boards: beginner (broad transcription), advanced (narrow transcription), and the full IPA keyboard. You are able to set the date and time that the assignments are due and provide immediate or delayed feedback of the performance results. Students are able to visualize the de-identified results of the class and visualize graphic displays of individual and class performance.
                </h4>
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
      <a href="/loading.html" data-ispage="true" class="btn-block">Loading Page</a>

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
<script type="text/javascript" src="js/ay-pages.js"></script>
<script type="text/javascript" src="js/main.js"></script>
<script type="text/javascript" src="js/demo-switcher.js"></script>
</body>
</html>
