$("#uname").focus();
var login = function() {
//	alert(resourcePrefix+"-"+uri+"-"+$.i18n.prop("demo"));
	$.ajaxProxy({
		url: "/auth/signin/do",
		type: "POST",
		dataType: "JSON",
		data: {
			"username": $("#uname").val(),
			"password": $.md5($("#pwd").val())
		},
		handle: function(result) {
			if(result.status==1) {
//				alert("Auth: "+result.msg+"-------"+result.data.user.name);
//				$.cookie("JSESSIONID_DUCATI", result.data.jsessionid, {expires: 1, path: "/"});
				var backto = $.trim($("#backto").val());
				$(location).attr("href", backto.length==0?"/index":backto);
			} else {
				alert(result.msg);
				$("#"+(result.status==-2||result.status==-4)?"pwd":"uname").focus();
			}
		}
	});
};
$(document).keyup(function(event) {
	if(event.keyCode==13) {
		login();
	}
});
$("#ajaxLoginBtn").click(login);
$("#testBtn").click(function() {
	$.ajaxProxy({
		url: "/xxx/bean",
		type: "GET",
		dataType: "JSON",
		handle: function(result) {
			alert("成功");
		}
	});
});