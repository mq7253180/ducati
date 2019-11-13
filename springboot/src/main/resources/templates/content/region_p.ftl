<#import "/spring.ftl" as spring>
<!DOCTYPE html>
<html>
	<head>
	    <title>所有国家或地区</title>
	</head>
	<body>
		<table>
			<tr>
				<td>中文名称</td>
				<td>英文名称</td>
			</tr>
		<#list regions as region>
			<tr>
				<td><font color="green">${region.cnName}</font></td>
				<td><font color="blue">${region.enName}</font></td>
			</tr>
		</#list>
		</table>
	</body>
</html>