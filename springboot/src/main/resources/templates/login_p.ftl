<!DOCTYPE html>
	<head>
		<title>循环授信再保理</title>
	</head>
	<body>
		<div align="center" valign="top">
			<font color="blue" size="3">循环授信再保理平台</font><br/>
			Username: <input type="input" name="username" id="uname"><br/>
			Password: <input type="password" name="password" id="pwd"><br/>
			<input type="button" id="ajaxLoginBtn" value="Signin"/>
			<input type="button" id="ajaxVCodeLoginBtn" value="Signin"/>
			<input type="button" id="testBtn" value="Test"/>
		</div>
		<input type="hidden" id="locale" value="<@locale/>"/>
		<input type="hidden" id="uri" value="<@attr key="uri_without_first" />"/>
		<input type="hidden" id="resourcePrefix" value="<@property key="prefix.resource"/>"/>
		<input type="hidden" id="backto" value="${backto?if_exists}"/>
		<script src="<@property key="prefix.resource"/>/jquery-3.4.1.min.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.i18n.properties.min.js"></script>
		<script src="<@property key="prefix.resource"/>/core.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.md5.js"></script>
		<script src="<@property key="prefix.resource"/>/jquery.cookie.js"></script>
		<script src="<@property key="prefix.resource"/>/js/login.js"></script>
	</body>
</html>