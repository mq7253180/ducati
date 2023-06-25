<#import "/spring.ftl" as spring>
<#macro layout>
{
	"status": 1,
	"data": <#nested/>
}
</#macro>