<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


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

				<div class="col-md-3">
					<img src="${user.imagePath}" title="banner1" id="moviehomebanner"
						width="412" height="300">
				</div>
				<div class="col-md-9">
					<div class="comment_field" style="display: none">
						<form action="post.do" method="get" name="post_comment">
							<textarea class="form-control" rows="3" name="comment"> </textarea>
							<input type="checkbox" name="isRepost" value="repost">
							Post to Twitter/Flickr <input type="submit" value="submit"
								class="btn btn-default"> <input type="hidden"
								name="ori_poster" value="Matt"> <input type="hidden"
								name="ori_text" value="Something about movie"> <input
								type="hidden" name="category" value="Comedy"> <input
								type="hidden" name="imagePath" value="images/a.jpg">
						</form>
					</div>



				</div>
			</div>
			<div class="container-fluid">
				<div class="row">
					<h1>All Posts</h1>
					<div class="comment-section" style="width: 70%; margin-left: 5%;">


						<c:forEach var="post" items="${allPosts}">
							<div class="comment_text">
								<div class="row">
									<div class="col-md-2">
										<img src="{comment.imagePath}">
									</div>
									<div class="col-md-10">
										<h5 class="username">${user.username}</h5>
										<h5 class="postdate">${comment.postDate}</h5>
										<h2 class="text">${comment.content}</h2>
									</div>
								</div>
							</div>
						</c:forEach>


						<div class="clear"></div>
					</div>



				</div>

			</div>
		</div>
	</div>
</div>
</body>
</html>