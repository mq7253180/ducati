<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>密码重置超时</title>
	</head>
	<body>
		<input type="hidden" id="uname" value="${username?if_exists}"/>
		<font color="red">链接超时失效，<a id="pwdResetBtn" href="javacript:void(0);">点击</a>重新发送</font>
		<script src="<@property key="prefix.resource"/>/jquery-3.4.1.min.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.i18n.properties.min.js"></script>
		<script src="<@property key="prefix.resource"/>/core.js"></script>
		<script src="<@property key="prefix.resource"/>/js/login.js"></script>
	</body>
</html>