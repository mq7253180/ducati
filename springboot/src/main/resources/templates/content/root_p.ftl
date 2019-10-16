<#assign jslist=["/js/root.js"]/>
<#assign csslist=[]/>
<#include "/layout_p.ftl"/>
<@layout>
	<input type="file" id="zipFiles" name="zipFiles" multiple/><@input type="button" id="initBtn" value="初始化白名单" permission="init"/><@input type="button" id="synBtn" value="同步白名单" permission="syn"/><br/>
	<select id="roleSel">
		<#list roles as role>
		<option value="${role.id}">${role.name}</option>
		</#list>
	</select>
	<@input type="button" id="reloadSessionsByRoleBtn" value="根据角色刷新Session" permission="reloadSessionsByRole"/>
	<br/>
	<select id="userSel">
		<#list users as u>
		<option value="${u.id}">${u.name}</option>
		</#list>
	</select>
	<@input type="button" id="reloadSessionByUserIdBtn" value="根据用户ID刷新Session" permission="reloadSessionByUserId"/>
</@layout>