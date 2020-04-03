<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>登录结果页</title>
	</head>
	<body>
		登录失败: <font color="red">${status}<label id="msg"><#if msg?exists>-${msg}</#if></label></font>
	</body>
	<script src="<@property key="prefix.resource"/>/js/result.js"></script>
</html>