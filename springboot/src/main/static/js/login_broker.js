alert($("#tip").html());
var redirectTo = $("#redirectTo").val();
$(top.location).attr("href", "/auth/signin"+(redirectTo.length>0?("?redirectTo="+encodeURIComponent(redirectTo)):""));