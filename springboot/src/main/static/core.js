var locale = $("#locale").val();
var resourcePrefix = $("#resourcePrefix").val();
var uri = $("#uri").val();
(function($) {
	String.prototype.startWith = function(s) {
		if(s==null||s==undefined) {
			return false;
		} else {
			var _s = $.trim(s);
			var _this = $.trim(this);
			if(_this.length==0||_s.length>_this.length) {
				return false;
			} else {
				return _this.substr(0, _s.length)==_s?true:false;
			}
		}
	}
	$.i18n.properties({ 
		name: "view",
		path: resourcePrefix+"/i18n",
		mode: "map",//模式：变量或 Map
		language: locale,
		cache: false,
		encoding: "UTF-8",
		callback: function() {
			
		}
	});
	if(navigator.appName=="Microsoft Internet Explorer") {
		var regExp = new RegExp("^.+MSIE\\s*[1-9]\\.+.+$", "g");
		if(regExp.test(navigator.appVersion))
			alert($.i18n.prop("msg.ie"));
	}
	function handleException(xhr) {
		let msg = "未知错误："+xhr.readyState;
		switch(xhr.readyState) {
			case 0: msg = "未初始化，检查网络是否畅通，Nginx、SLB、应用服务是否活着";break;
			case 1: msg = "已经建立连接，准备发送";break;
			case 2: msg = "已经发送，等待响应";break;
			case 3: msg = "已经得到响应，正在接收数据";break;
			case 4: {
				msg = "已完成，";
				switch(xhr.status) {
					case 404: msg += "路径不存在";break;
					case 500: msg += "后端抛异常";break;
					case 502: msg += "Nginx负载节点全挂了";break;
					case 503: msg += "Nginx超负荷";break;
					default: msg += xhr.status;break;
				}
				//msg += "："+JSON.parse(xhr.responseText).path;
				msg += "："+xhr.responseJSON.path;
			};break;
		}
		alert("XMLHttpRequest"+msg);
	}
	$.ajaxProxy = function(s) {
		let handle = s.handle;
		delete s.handle;
		s.xhrFields = {
			withCredentials: true
		};
		s.success = function(data) {
			if(data.status==1) {
				handle(data.data);
			} else {
				alert(data.msg);
				if(data.status==0)
					$(top.location).attr("href", "/auth/signin");
			}
		};
		s.error = function(xhr) {
			handleException(xhr);
		};
		s.complete = function(xhr, status) {
			//alert("complete---Stop Loading---"+status+"---"+JSON.stringify(xhr));
			//Stop Loading
		};
		$.ajax(s);
	};
	$.fn.ajaxUploadFiles = function(s) {
		var formData = new FormData();
		var validationErrorMsg = "";
		var retVal = 1;
		var path = $(this).val();
		if(path==null||path.length==0) {
			validationErrorMsg = $.i18n.prop("upload.error.null");
			retVal = 0;
		} else {
			var files = $(this).prop("files");
			var separator = null;
			var separatorSize = null;
			if(locale.startWith("zh")||locale.startWith("ZH")) {
				separator = "、";
				separatorSize = 1;
			} else {
				separator = ", ";
				separatorSize = 2;
			}
			for(var i=0;i<files.length;i++) {
				var file = files[i];
				var validFileType = false;
				var acceptableTypes = "";
				for(var j=0;j<s.acceptableTypes.length;j++) {
					var type = s.acceptableTypes[j];
					var regExp = new RegExp(".+\."+s.acceptableTypes[j]+"$", "i");
					if(regExp.test(file.name)) {
						validFileType = true;
					}
					acceptableTypes += type;
					acceptableTypes += separator;
				}
				if(!validFileType) {
					validationErrorMsg = $.i18n.prop("upload.error.types", acceptableTypes.substring(0, acceptableTypes.length-separatorSize));
					retVal = -1;
					break;
				}
				if(file.size>s.maxSize) {
					var maxSize = s.maxSize/1024/1024;
					validationErrorMsg = $.i18n.prop("upload.error.max_size", maxSize+"MB");
					retVal = -2;
					break;
				}
				formData.append("files", file);
			}
			if(s.fields!=null) {
				$.each(s.fields, function(name, value) {
					formData.append(name, value);
				});
			}
		}
		if(retVal<1) {
			s.validationFailed(validationErrorMsg);
		} else {
			/*var req = window.XMLHttpRequest?new XMLHttpRequest():new ActiveXObject("Microsoft.XMLHTTP");
			req.onreadystatechange = function() {
				//alert(req.readyState+"---"+req.status+"\r\n"+req.responseText);
				if(req.readyState==4) {
					if(req.status==200) {
						var data = JSON.parse(req.responseText);
						if(data.status==1) {
							s.handle(data.data);
						} else {
							alert(data.msg);
							if(data.status==0)
								$(top.location).attr("href", "/auth/signin");
						}
					} else
						handleException(req.status==0?{
							"readyState": 0,
							"status": 0,
							"statusText": "error"
						}:req);
				}
			};
			req.open("POST", s.url, true);
			req.setRequestHeader("x-requested-with", "XMLHttpRequest");
			req.withCredentials = true;
			req.send(formData);*/
			$.ajaxProxy({
				url: s.url,
				type: "POST",
				data: formData,
				processData: false,
				contentType: false,
				handle: s.handle
			});
		}
		return retVal;
	};
})(jQuery);
var datePickerOptions = {
	dateFormat: "yy-mm-dd",
	showButtonPanel: true,
	currentText: "Now",
	hideIfNoPrevNext: true,
	beforeShow: function(input) {
		setTimeout(function() {
			var buttonPane = $(input).datepicker("widget").find(".ui-datepicker-buttonpane");
			$("<button>", {
				text: "Clear",
				click: function() {
					$.datepicker._clearDate(input);
				}
			}).addClass("ui-state-default ui-priority-primary ui-corner-all").appendTo(buttonPane);
		}, 1);
	}
};