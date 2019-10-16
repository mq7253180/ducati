{"status": 1, "msg": "<@i18n key="status.success"/>", "data": [
	<#list countries as country>
		{"cnName": "${country.cnName}", "enName": "${country.enName}"}<#if country_index lt countries?size-1>,</#if>
	</#list>
]}