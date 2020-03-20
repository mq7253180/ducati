alert($("#tip").html());
$(top.location).attr("href", "/auth/signin?redirectTo="+encodeURIComponent($("#redirectTo").val()));