<#assign jslist=["/js/index.js"]/>
<#assign csslist=[]/>
<#assign titlekey="demo"/>
<#include "/layout_p.ftl"/>
<@layout>
	首页: <input type="button" id="logoutBtn" value="登出"/><br/>
	<@input type="button" id="showAllCountriesBtn" value="显示所有国家和地区(ftl模板返回)" permission="countries"/><@a href="/zone/countries"><font color="blue">显示所有国家和地区</font></@a><input type="button" id="showAllCountriesBtn2" value="显示所有国家和地区(@ResponseBody返回)"/><input type="button" id="showAllCountriesBtn3" value="显示所有国家和地区(AJAX重定向)"/>
</@layout>