<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ page import="bean.*"%>
<%
	PostAndCommentsBean[] myPosts = (PostAndCommentsBean[])request.getAttribute("myPosts");
	//System.out.println("haha");
%>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<link
	href="http://netdna.bootstrapcdn.com/bootstrap/3.1.0/css/bootstrap.min.css"
	rel="stylesheet">
<link href="path/to/css/star-rating.min.css" media="all"
	rel="stylesheet" type="text/css" />
<script src="http://libs.baidu.com/jquery/1.10.2/jquery.min.js"></script>
<script src="path/to/js/star-rating.min.js" type="text/javascript"></script>
<link href="css/bootstrap.min.css" rel="stylesheet">
<link rel="shortcut icon" type="image/x-icon"
	href="./images/fav-icon.png" />
<script src="js/jquery.min.js"></script>
<script src="js/showComments.js"></script>

<jsp:include page="header.jsp" />

<div class="content">
	<div id="main" role="main">
		<div class="container-fluid">
			<div class="row">
				<div class="col-md-3" style="border-right: 1px solid black;">
					<span style="padding-left: 140px"><img src="${user.imagePath}"
						alt=""></span> 
					<p style="padding-left: 150px"> <span>User </span><span>${user.username }</span>
					</p>
					
						<span style="padding-left:270px"><form style="padding-left:100px" method="post" action="analysis.do">
							<input type="submit" value="User Trend Analysis">
						</form>
					</span>

				</div>


				<div class="col-md-9">
				   
					<div class="post_new"
						style="width: 70%; padding-bottom: 15px; border-bottom: solid 1px RGB(238, 238, 238);">
						<form action="postNew.do" method="post" name="post_new" enctype="multipart/form-data">
							<textarea class="form-control" rows="2" name="content"
								placeholder="What's happening"></textarea>
							<div class="upload">
								<input type="file" name="upload" />
							</div>
							<input type="checkbox" name="isRepost" value="flickr">
							Send to Flickr <input type="checkbox" name="isRepost"
								style="margin-left: 15%" value="twitter"> Send to
							Twitter <input type="submit" value="submit"
								class="btn btn-default">
						</form>
					</div>
					<h1>My Posts</h1>
							<div class="comment-section" style="width: 70%; margin-left: 5%;">
						<c:forEach var="post" items="${myPosts}">
							<div class="grid1_of_2">
								<div class="grid_img">
									<img src="${post.user_photo_url}">
								</div>
								<div class="grid_text">

									<h4 class="style1 list">
										<a href="#">${post.user_name}</a>
										<h3 class="style">
											<fmt:formatDate pattern="yyyy-MM-dd" value="${post.postDate}" />
										</h3>
									</h4>

									<p class="para top">${post.content}</p>
									<div class="twitter_img" style="height: auto !important">
										<img src="${post.imagePath}">
									</div>
								</div>

								<div class="clear"></div>

								<div class="comment_field" style="display: none">
									<form action="comment.do" method="get" name="post_comment">
										<textarea class="form-control" rows="2" name="content"
											style="width: 75%; float: left"> </textarea>
										<input type="submit" value="submit" style="margin-right: 2%"
											class="btn btn-default"> <input type="hidden"
											name="post_id" value="${post.post_id}">
									</form>
								</div>

								<div class="clear"></div>

								<c:forEach var="comment" items="${post.comments}">
									<div class="comment_text">
										<div class="row">
											<div class="col-md-2">
												<img src="${comment.user_photo_url}">
											</div>
											<div class="col-md-10">
												<h5 class="username">${comment.user_name}</h5>
												<h5 class="postdate" style="margin-left: 55px">
													<fmt:formatDate pattern="yyyy-MM-dd"
														value="${comment.comment_time}" />
												</h5>
												<h5 class="text">${comment.content}</h5>
											</div>
										</div>
									</div>
								</c:forEach>

							</div>

						</c:forEach>

					</div>

				</div>
			</div>
		</div>
	</div>
</div>
</body>
</html>