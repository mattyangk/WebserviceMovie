<%@ page import="bean.UserBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	UserBean user = (UserBean) request.getSession()
			.getAttribute("user");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<head>
		<title>What's the movie</title>
		<link href="css/style.css" rel='stylesheet' type='text/css' />
		<meta name="viewport" content="width=device-width, initial-scale=1">
		<link rel="shortcut icon" type="image/x-icon" href="images/fav-icon.png" />
		<script type="application/x-javascript"> addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } </script>
		</script>
		<!----webfonts---->
		<link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800' rel='stylesheet' type='text/css'>
		<!----//webfonts---->
		<!-- Global CSS for the page and tiles -->
  		<link rel="stylesheet" href="css/main.css">
  		<!-- //Global CSS for the page and tiles -->
		<!---start-click-drop-down-menu----->
		<script src="js/jquery.min.js"></script>
        <!----start-dropdown--->
         <script type="text/javascript">
			var $ = jQuery.noConflict();
				$(function() {
					$('#activator').click(function(){
						$('#box').animate({'top':'0px'},500);
					});
					$('#boxclose').click(function(){
					$('#box').animate({'top':'-700px'},500);
					});
				});
				$(document).ready(function(){
				//Hide (Collapse) the toggle containers on load
				$(".toggle_container").hide(); 
				//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
				$(".trigger").click(function(){
					$(this).toggleClass("active").next().slideToggle("slow");
						return false; //Prevent the browser jump to the link anchor
				});
									
			});
		</script>
        <!----//End-dropdown--->
		<!---//End-click-drop-down-menu----->
	</head>
	<body>
		<!---start-wrap---->
			<!---start-header---->
			<div class="header">
				<div class="wrap">
				<div class="logo">
					<h1><a href="index.do"><img src="./images/logo.png"/></a></h1>
				</div>
				<div class="top-searchbar">
					<form action="search.do" method="get">
						<input type="text" name="movie_name"/>
						<input type="submit" value="" />
					</form>
				</div>
				
				<div class="userinfo">
					<div class="user">
					<%
				if (user == null) {
				%>
					<ul>
							<li><a href="#"><span>Register</span></a></li>
						</ul>
				
				<%
				} else {	
			%>
			<ul>  <li><a href="allPostsPage.do"><span>Home</span></a></li>       
			<li><a href="homePage.do"><span>My Page</span></a></li>
							<li><a href="#"><span>Logout</span></a></li>
						</ul>

			<%
				}
			%>
			</div>
				</div>
				<div class="clear"> </div>
			</div>
		</div>
		<!---//End-header---->