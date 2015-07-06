<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<link rel="shortcut icon" href="../favicon.ico">
<style type="text/css">
body {
	height: 100%;
}

td {
	text-align: center;
}

table {
	margin-top: 5%;
	margin-left: auto;
	margin-right: auto;
	position: relative;
	background: url('../PT.jpg');
	background-repeat: no-repeat;
	padding-left: 500px;
	padding-bottom: 240px;
	padding-top: 100px;
	height: 400px;
	width: 900px;
	border: 1px solid black;
}

.error {
	color: red;
}
</style>
</head>
<body>
	<form method="post" action="../j_spring_security_check">
		<table>
			<tbody>
				<tr>
					<td colspan="2" style="text-align: center; font-weight: bold">
						<div>Znalostní báze restaurcí a pivnic</div>
					</td>
				</tr>
				<tr>
					<td>Jméno:</td>
					<td><input type="text" id="j_username" name="j_username"
						size="18"></td>
				</tr>
				<tr>
					<td>Heslo:</td>
					<td><input type="password" id="j_password" name="j_password"
						size="18"></td>
				</tr>
				<tr>
					<td>&nbsp;</td>
					<td><input type="submit" value="Přihlásit"></td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
				<tr>
					<td colspan="2"></td>
				</tr>
			</tbody>
		</table>
	</form>
	<script>
		document.getElementById('j_username').focus();
	</script>
</body>
</html>