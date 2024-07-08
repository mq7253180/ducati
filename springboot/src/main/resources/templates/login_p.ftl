<!DOCTYPE html>
	<head>
		<title>循环授信再保理</title>
	</head>
	<body>
		<div align="center" valign="top">
			<font color="blue" size="3">循环授信再保理平台</font><br/>
			Username: <input type="input" name="username" id="uname"><br/>
			Password: <input type="password" name="password" id="pwd"><br/>
			Verification Code: <input type="input" name="vcode" id="vcode"><img id="_vcode" src=""/><br/>
			<input type="button" id="ajaxLoginBtn" value="Signin"/>
			<input type="button" id="sendTempPwdBtn" value="发送临时密码"/>
			<input type="button" id="tempPwdLoginBtn" value="登录"/>
			<input type="button" id="testBtn" value="Test"/>
			<a id="pwdResetBtn" href="javacript:void(0);">忘记密码</a>
			<br/><br/>
			<input type="input" name="vcode2" id="vcode2"><img id="_vcode2" src=""/><br/>
			<input type="button" id="test2Btn" value="提交"/><input type="button" id="test2AS" value="提交成功"/><input type="button" id="test2AF" value="提交失败"/>
		</div>
		<input type="hidden" id="locale" value="<@locale/>"/>
		<input type="hidden" id="uri" value="<@attr key="uri_without_first" />"/>
		<input type="hidden" id="resourcePrefix" value="<@property key="prefix.resource"/>"/>
		<input type="hidden" id="redirectTo" value="${redirectTo?if_exists}"/>
		<script src="<@property key="prefix.resource"/>/jquery-3.4.1.min.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.i18n.properties.min.js"></script>
		<script src="<@property key="prefix.resource"/>/core.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.md5.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.cookie.js"></script>
		<script src="<@property key="prefix.resource"/>/js/login.js"></script>
	</body>
</html>