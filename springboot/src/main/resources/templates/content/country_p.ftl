<#assign jslist=[]/>
<#assign csslist=[]/>
<#assign titlekey="demo"/>
<#include "/layout_p.ftl"/>
<@layout>
	<table>
		<tr>
			<td>中文名称</td>
			<td>英文名称</td>
		</tr>
	<#list countries as country>
		<tr>
			<td><font color="green">${country.cnName}</font></td>
			<td><font color="blue">${country.enName}</font></td>
		</tr>
	</#list>
	</table>
</@layout>