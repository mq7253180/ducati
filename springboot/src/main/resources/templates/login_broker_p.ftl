<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<title><@i18n key="title"/>-<@i18n key="title.login"/></title>
	</head>
	<body>
		<font color="red"><label id="tip"><@i18n key="auth.timeout.broker"/></label></font>
	</body>
	<input type="hidden" id="locale" value="${locale?if_exists}"/>
	<input type="hidden" id="uri" value="<@attr key="uri_without_first" />"/>
	<input type="hidden" id="resourcePrefix" value="<@property key="prefix.resource"/>"/>
	<input type="hidden" id="redirectTo" value="${redirectTo?if_exists}"/>
	<script src="<@property key="prefix.resource"/>/jquery-3.4.1.min.js"></script>
	<script src="<@property key="prefix.resource"/>/jquery.i18n.properties.min.js"></script>
	<script src="<@property key="prefix.resource"/>/core.js"></script>
	<script src="<@property key="prefix.resource"/>/js/login_broker.js"></script>
</html>