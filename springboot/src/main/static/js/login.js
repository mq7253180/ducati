$("#uname").focus();
var login = function() {
//	alert(resourcePrefix+"-"+uri+"-"+$.i18n.prop("demo"));
	$.ajaxProxy({
		url: "/auth/signin/pwd",
		type: "POST",
		dataType: "JSON",
		data: {
			"username": $("#uname").val(),
			"password": $.md5($("#pwd").val()),
			"vcode": $("#vcode").val()
		},
		handle: function(result) {
			if(result.status==1) {
//				alert("Auth: "+result.msg+"-------"+result.data.user.name);
//				$.cookie("JSESSIONID_DUCATI", result.data.jsessionid, {expires: 1, path: "/"});
				var redirectTo = $.trim($("#redirectTo").val());
				$(location).attr("href", redirectTo.length==0?"/":redirectTo);
			} else {
				alert(result.msg);
				if(result.status<-3)
					$("#_vcode").attr("src", "/auth/vcode/25/10/25/110/35?random="+Math.random());
				var inputKey = null;
				if(result.status==0||result.status==-3||result.status==-4)
					inputKey = "pwd";
				else if(result.status==-1||result.status==-2)
					inputKey = "uname";
				else
					inputKey = "vcode";
				$("#"+inputKey).focus();
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
$("#_vcode").click(function() {
	$(this).attr("src", "/auth/vcode/25/10/25/110/35?random="+Math.random());
});
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
$("#ajaxVCodeLoginBtn").click(function() {
	$.ajaxProxy({
		url: "/auth/signin/vcode",
		type: "POST",
		dataType: "JSON",
		data: {
			"username": $("#uname").val(),
			"vcode": $("#vcode").val()
		},
		handle: function(result) {
			if(result.status==1) {
				var redirectTo = $.trim($("#redirectTo").val());
				$(location).attr("href", redirectTo.length==0?"/index":redirectTo);
			} else {
				alert(result.msg);
				$("#uname").focus();
			}
		}
	});
});
$("#pwdResetBtn").click(function() {
	$.ajaxProxy({
		url: "/auth/vcode/pwdset",
		type: "POST",
		dataType: "JSON",
		data: {
			"email": $("#uname").val()
		},
		handle: function(result) {
			alert(result.status+"======"+result.msg);
		}
	});
	return false;
});