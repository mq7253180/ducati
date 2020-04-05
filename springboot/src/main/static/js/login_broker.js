alert($("#tip").html());
var redirectTo = $("#redirectTo").val();
var locale = $("#locale").val();
var uri = "/auth/signin";
if(locale.length>0) {
	uri += "?locale=";
	uri += locale;
}
if(redirectTo.length>0) {
	uri += uri.indexOf("?")>=0?'&':'?';
	uri += "redirectTo=";
	uri += encodeURIComponent(redirectTo);
}
$(top.location).attr("href", uri);