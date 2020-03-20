<!DOCTYPE html>
	<head>
		<title>循环授信再保理</title>
	</head>
	<body>
		<input type="button" id="testBtn" value="Test"/>
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