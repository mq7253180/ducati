<#assign jslist=["/js/result.js"]/>
<#assign csslist=[]/>
<#assign titlekey="demo"/>
<#include "/layout_p.ftl"/>
<@layout>
	<font color="red">${status}<label id="msg"><#if msg?exists>-${msg}</#if></label><#if data?exists>-${data}</#if></font>
</@layout>