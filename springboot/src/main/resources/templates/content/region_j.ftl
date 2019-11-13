{"status": 1, "msg": "<@i18n key="status.success"/>", "data": [
	<#list regions as region>
		{"cnName": "${region.cnName}", "enName": "${region.enName}"}<#if region_index lt regions?size-1>,</#if>
	</#list>
]}