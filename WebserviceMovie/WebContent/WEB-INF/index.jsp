<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>WebServiceMovie</title>
</head>
<body>
    <h2>Sign In</h2>
	<div class="span3"></div>
					<form class="form-horizontal" method="POST" action="signin.do">
						<div class="span6">
							<table class="table">
								<tr>
									<td><div class="text-right">Username</div></td>
									<td><div class="text-left">
											<input type="text" name = "username" placeholder="Username">
										</div></td>
								</tr>
								<tr>
									<td align="right"><div class="text-right">Password</div></td>
									<td><div class="text-left">
											<input type="password" name = "password"
												placeholder="Password">
										</div></td>
								</tr>
							</table>
							<div>
								<button type="submit" class="btn btn-primary">Sign in</button>
							</div>
						</div>
					</form>
</body>
</html>