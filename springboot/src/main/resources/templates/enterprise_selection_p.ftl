<!DOCTYPE html>
<html>
	<head>
		<meta charset="UTF-8">
		<title>选择租户</title>
	</head>
	<body>
		<font color="red">
			${status}<label id="msg"><#if msg?exists>-${msg}</#if></label><br/>
			<#if data?exists>
				<#list data as e>
					${e.name}-${e.shardingKey}-${e.id}<br/>
				</#list>
			</#if>
		</font>
	</body>
	<script src="<@property key="prefix.resource"/>/js/result.js"></script>
</html>