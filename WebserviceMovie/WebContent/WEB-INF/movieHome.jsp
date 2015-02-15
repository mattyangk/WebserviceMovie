<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Pinball Website Template | single-page :: w3layouts</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" type="image/x-icon"
	href="./images/fav-icon.png" />
<script src="js/jquery.min.js"></script>
<script src="js/showComments.js"> </script>
<script type="application/x-javascript">

	addEventListener("load", function() { setTimeout(hideURLbar, 0); }, false); function hideURLbar(){ window.scrollTo(0,1); } 

</script>
<!----webfonts---->
<link
	href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600,700,800'
	rel='stylesheet' type='text/css'>
<!----//webfonts---->
<!---start-click-drop-down-menu----->

<!----start-dropdown--->
<script type="text/javascript">
	var $ = jQuery.noConflict();
	$(function() {
		$('#activator').click(function() {
			$('#box').animate({
				'top' : '0px'
			}, 500);
		});
		$('#boxclose').click(function() {
			$('#box').animate({
				'top' : '-700px'
			}, 500);
		});
	});
	$(document).ready(function() {
		//Hide (Collapse) the toggle containers on load
		$(".toggle_container").hide();
		//Switch the "Open" and "Close" state per click then slide up/down (depending on open/close state)
		$(".trigger").click(function() {
			$(this).toggleClass("active").next().slideToggle("slow");
			return false; //Prevent the browser jump to the link anchor
		});

	});
</script>
<!----//End-dropdown--->
</head>
<body>
	<!---start-wrap---->
	<!---start-header---->
	<jsp:include page="header.jsp" />
	<!---//End-header---->
	<!---start-content---->
	<div class="content">
		<div class="wrap">
			<div id="main" role="main">
				<div class="container-fluid">
					<div class="col-md-3">
						<img src="${movie.imagePath}" title="banner1" id="moviehomebanner">
					</div>
					<div class="col-md-9">
						<span> Title: </span> <span> ${movie.title} </span> <br /> <span>
							Director: </span> <span> ${movie.director.get(0)} </span> <br /> <span>
							Stars: </span>
						<c:forEach var="star" items="${movie.casts}">
							<span> ${star} </span>
						</c:forEach>
						<br /> <span> Date: </span> <span> ${movie.date} </span> <br />
						<span> Rating: </span> <span> ${movie.rate} </span> <br /> <span>
							Category: </span> <span> ${movie.category } </span> <br /> <span>
							Overview: </span> <span> ${movie.description} </span> <br />


					</div>
				</div>

				<hr />
				
				
				<h2 class="section_header">Related Comments</h2>
				<!---start-comments-section--->
				<div class="comment-section" style="width: 70%; margin-left: 15%;">
					<div class="grids_of_2">

						<div class="grid1_of_2">
							<div class="grid_img">
								<a href=""><img src="images/pic10.jpg" alt=""></a>
							</div>
							<div class="grid_text">

								<h4 class="style1 list">
									<a href="#">Uku Mason</a>
									<h3 class="style">march 2, 2013 - 12.50 AM</h3>
								</h4>

								<p class="para top">All the Lorem Ipsum generators on the
									Internet tend to repeat predefined chunks as necessary, making
									this the first true generator on the Internet.</p>
								<div class="twitter_img">
									<img src="images/twitter.jpg" class="twitter_img" alt="">
								</div>
							</div>

							<div class="clear"></div>
						</div>






						<div class="grid1_of_2">
							<div class="grid_img">
								<a href=""><img src="images/pic10.jpg" alt=""></a>
							</div>
							<div class="grid_text">

								<h4 class="style1 list">
									<a href="#">Uku Mason</a>
									<h3 class="style">march 2, 2013 - 12.50 AM</h3>
								</h4>

								<p class="para top">All the Lorem Ipsum generators on the
									Internet tend to repeat predefined chunks as necessary, making
									this the first true generator on the Internet.</p>
								<div class="twitter_img">
									<img src="images/twitter.jpg" class="twitter_img" alt="">
								</div>
							</div>

							<div class="clear"></div>
						</div>




						<div class="grid1_of_2">
							<div class="grid_img">
								<a href=""><img src="images/pic10.jpg" alt=""></a>
							</div>
							<div class="grid_text">

								<h4 class="style1 list">
									<a href="#">Uku Mason</a>
									<h3 class="style">march 2, 2013 - 12.50 AM</h3>
								</h4>

								<p class="para top">All the Lorem Ipsum generators on the
									Internet tend to repeat predefined chunks as necessary, making
									this the first true generator on the Internet.</p>
								<div class="twitter_img">
									<img src="images/twitter.jpg" class="twitter_img" alt="">
								</div>
							</div>

							<div class="clear"></div>
						</div>

						
						
						

						<div class="artical-commentbox">

							<div class="table-form">
								<form action="#" method="post" name="post_comment">

									<div>
										<label>Your Comment<span>*</span></label>
										<textarea> </textarea>
									</div>
								</form>
								<input type="submit" value="submit">

							</div>
							<div class="clear"></div>
						</div>
					</div>
				</div>
			</div>
			<!---//End-comments-section--->
		</div>
	</div>
	</div>

	<!----start-footer--->
	<div class="footer">
		<p>
			Design by <a href="http://w3layouts.com/">W3layouts</a>
		</p>
	</div>
	<!----//End-footer--->
	<!---//End-wrap---->
</body>
</html>

