$("#logoutA").click(function() {
	$.ajaxProxy({
		url: "/auth/signout",
		type: "GET",
		dataType: "JSON",
		handle: function(data) {
			alert("您已登出");
			$(top.location).attr("href", "/auth/signin");
		}
	});
});