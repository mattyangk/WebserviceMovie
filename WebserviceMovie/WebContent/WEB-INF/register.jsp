<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Create An Account</title>
</head>
<body>
    <h2>Create An Account</h2>
	<form>
		<table class="table">
			<tr>
				<td>Email</td>
				<td><input type="text" name="email" class="form-control"
					value="" /></td>
			</tr>
			<tr>
				<td>Username</td>
				<td><input type="text" name="username" class="form-control"
					value="" /></td>
			</tr>
			<tr>
				<td>Password</td>
				<td><input type="password" name="newPassword"
					class="form-control" value="" /></td>
			</tr>

			<tr>
				<td>Confirm Password</td>
				<td><input type="password" name="rePassword"
					class="form-control" value="" /></td>

			</tr>
			<tr>
				<td><input type="checkbox" name="link" value="twitter"></td>
				<td>Linked with your twitter account</td>
			<tr>
			<tr>
				<td><input type="checkbox" name="link" value="instagram"></td>
				<td>Linked with your instagram account</td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Submit" /></td>
			</tr>
		</table>

	</form>
</body>
</html>