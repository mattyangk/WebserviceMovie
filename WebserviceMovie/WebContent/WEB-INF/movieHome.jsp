<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE HTML>
<html>
<head>
<title>Pinball Website Template | single-page :: w3layouts</title>

<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" type="image/x-icon"
	href="./images/fav-icon.png" />
<script src="js/jquery.min.js"></script>
<script src="js/showComments.js">
	
</script>
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
						<img src="${movie.imagePath}" title="banner1" id="moviehomebanner"
							width="412" height="300">
					</div>
					<div class="col-md-9">
						<h2>
							<span> ${movie.title} </span><span style="color: #FF3333;">
								(${movie.rate}) </span>
						</h2>
						<span>Date:</span> <span><fmt:formatDate
								pattern="yyyy-MM-dd" value="${movie.date}" /> </span> <br /> <span>Category:</span>
						<span> ${movie.category } </span> <br />
						<h4>Crew</h4>
						<span>Director: </span> <span> ${movie.director.get(0)} </span> <br />

						<c:forEach var="star" items="${movie.casts}">
							<span> ${star} </span>
						</c:forEach>
						<br />
						<h4>Overview</h4>
						<p>${movie.description}</p>
						<br />


					</div>
				</div>

				<hr />


				<h2 class="section_header">Related Comments</h2>
				<!---start-comments-section--->
				<div class="comment-section" style="width: 70%; margin-left: 15%;">
					<c:forEach var="display" items="${displayList}">
						<div class="grid1_of_2">
							<div class="grid_img">
								<a href=""><img src="${display.profile_url}" alt=""></a>
							</div>
							<div class="grid_text">

								<h4 class="style1 list">
									<a href="#">${display.user_name} </a>
									<h3 class="style">
										<fmt:formatDate pattern="yyyy-MM-dd" value="${display.date}" />
									</h3>
									<h3 class="style">--@${display.source}</h3>
								</h4>

								<p class="para top">${display.text}</p>
								<c:choose>
									<c:when test="${display.source.equals('Flickr')}">
										<div class="twitter_img" style="height: auto !important">
											<a href=""><img src="${display.photo_url}"
												style="width:${display.width}px; height:${display.height}px"></a>
										</div>
									</c:when>
									<c:when test="${display.source.equals('Twitter')}">
										<div class="twitter_img" style="height: auto !important">
											<a href=""><img src="${display.photo_url}"></a>
										</div>
									</c:when>
								</c:choose>
							</div>

							<div class="clear"></div>
							<div class="comment_field" style="display: none">
								<form action="post.do" method="get" name="post_comment">
									<textarea class="form-control" rows="3" name="comment"> </textarea>
									<input type="checkbox" name="isRepost" value="repost">
									<c:choose>
										<c:when test="${display.source.equals('Flickr')}">Post to Flickr </c:when>
										<c:when test="${display.source.equals('Twitter')}">Post to Twitter</c:when>
									</c:choose>
									<input type="submit" value="submit" class="btn btn-default">
									<input type="hidden" name="source" value="${display.source}">
									<input type="hidden" name="ori_poster"
										value="${display.user_name}"> <input type="hidden"
										name="ori_text" value="${display.text}"> <input
										type="hidden" name="category" value="${movie.category}">
									<input type="hidden" name="imagePath"
										value="${display.photo_url}"> <input type="hidden"
										name="photoID" value="${display.photoID}">
								</form>
							</div>
						</div>
					</c:forEach>

					<div class="clear"></div>
				</div>
			</div>
		</div>
		<!---//End-comments-section--->
	</div>
	</div>


	<!----start-footer--->
	<div class="footer">
		<p>
			<!--Design by <a href="http://w3layouts.com/">W3layouts</a>-->
		</p>
	</div>
	<!----//End-footer--->
	<!---//End-wrap---->
</body>
</html>

