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
				var backto = $.trim($("#backto").val());
				$(location).attr("href", backto.length==0?"/index":backto);
			} else {
				if(result.status<-3) {
					alert(result.status);
					$("#_vcode").attr("src", "/auth/vcode/25/10/25/110/35?random="+Math.random());
				}
				alert(result.msg);
				$("#"+(result.status==0||result.status==-3)?"pwd":"uname").focus();
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
				var backto = $.trim($("#backto").val());
				$(location).attr("href", backto.length==0?"/index":backto);
			} else {
				alert(result.msg);
				$("#uname").focus();
			}
		}
	});
});