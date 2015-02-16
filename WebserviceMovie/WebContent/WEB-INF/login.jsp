<%@ page import="bean.UserBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

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
				</div>
				
				<div class="userinfo">
					<div class="user">
					
					<ul>
							<li><a href="#"><span>Register</span></a></li>
						</ul>
				
			</div>
				</div>
				<div class="clear"> </div>
			</div>
		</div>
		<!---//End-header---->

<!---start-content---->
<div class="content">
	 <div class="wrap">
	 <div class="single-page">
					<div class="single-page-artical">
	<div class="artical-commentbox">
						 	<h1>Welcome to What's the Movie!</h1>
				  			<div class="table-form">
								<form action="LoginAction.do" method="get" name="post_comment">
									<div>
										<label>User Name</label>
										<input type="text" name="username" >
									</div>
									<div>
										<label>Password</label>
										<input type="text" name ="password">
									</div>
									<input type="submit" value="Login">
								</form>
														
							</div>
							<div class="clear"> </div>
				  		</div>			

	</div> 
	</div>
	</div>
</div>

<!---//End-content---->
<!----wookmark-scripts---->
<script src="js/jquery.imagesloaded.js"></script>
<script src="js/jquery.wookmark.js"></script>
<script type="text/javascript">
	(function($) {
		var $tiles = $('#tiles'), $handler = $('li', $tiles), $main = $('#main'), $window = $(window), $document = $(document), options = {
			autoResize : true, // This will auto-update the layout when the browser window is resized.
			container : $main, // Optional, used for some extra CSS styling
			offset : 20, // Optional, the distance between grid items
			itemWidth : 280
		// Optional, the width of a grid item
		};
		/**
		 * Reinitializes the wookmark handler after all images have loaded
		 */
		function applyLayout() {
			$tiles.imagesLoaded(function() {
				// Destroy the old handler
				if ($handler.wookmarkInstance) {
					$handler.wookmarkInstance.clear();
				}

				// Create a new layout handler.
				$handler = $('li', $tiles);
				$handler.wookmark(options);
			});
		}
		/**
		 * When scrolled all the way to the bottom, add more tiles
		 */
		function onScroll() {
			// Check if we're within 100 pixels of the bottom edge of the broser window.
			var winHeight = window.innerHeight ? window.innerHeight : $window
					.height(), // iphone fix
			closeToBottom = ($window.scrollTop() + winHeight > $document
					.height() - 100);

			if (closeToBottom) {
				// Get the first then items from the grid, clone them, and add them to the bottom of the grid
				var $items = $('li', $tiles), $firstTen = $items.slice(0, 10);
				$tiles.append($firstTen.clone());

				applyLayout();
			}
		}
		;

		// Call the layout function for the first time
		applyLayout();

		// Capture scroll event.
		// $window.bind('scroll.wookmark', onScroll);
	})(jQuery);
</script>
<!----//wookmark-scripts---->
<!----start-footer--->
<div class="footer">

	<!--<p>Design by <a href="http://w3layouts.com/">W3layouts</a></p>-->
</div>
<!----//End-footer--->
<!---//End-wrap---->
</body>
</html>

