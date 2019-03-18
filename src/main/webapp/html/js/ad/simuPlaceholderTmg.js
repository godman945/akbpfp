$(document).ready(function(){
	// 設定 input 的預設值
	if($("#adTitle").val() == "") {
		$("#adTitle").val($("#adTitle").attr("placeholder")).css("color", "#aaa");
	}
	if($("#adLinkURL").val() == "") {
		$("#adLinkURL").val($("#adLinkURL").attr("placeholder")).css("color", "#aaa");
	}
	if($("#adShowURL").val() == "") {
		$("#adShowURL").val($("#adShowURL").attr("placeholder")).css("color", "#aaa");
	}

	// 設定 textarea 的預設值
	if($("#adContent").text() == "") {
		$("#adContent").text($("#adContent").attr("placeholder")).css("color", "#aaa");
	}

	$(document).on("focus", ".inputPlaceholderTmg", function() {
		simuPlaceholder($(this), "focus");
	}).on("keydown", ".inputPlaceholderTmg", function() {
		simuPlaceholder($(this), "keydown");
	}).on("blur", ".inputPlaceholderTmg", function() {
		simuPlaceholder($(this), "blur");
	}).on("focus", ".inputPlaceholderTmgTextarea", function() {
		simuPlaceholderTextarea($(this), "focus");
	}).on("keydown", ".inputPlaceholderTmgTextarea", function() {
		simuPlaceholderTextarea($(this), "keydown");
	}).on("blur", ".inputPlaceholderTmgTextarea", function() {
		simuPlaceholderTextarea($(this), "blur");
	});

	// 模擬 placeholder 動作(text)
	function simuPlaceholder(el, action) {
		if(action == "focus" || action == "keydown") {
			if(el.val() == el.attr("placeholder")) {
				el.css("color", "black");
				el.val("");
			} else {
				var showElem = $("#" + el.data("value"));
				var length = el.val().length;
				var maxlength = 1024;
				showElem.text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
			}
		} else if(action == "blur") {
			if(el.val() == "") {
				el.css("color", "#aaa");
				el.val(el.attr("placeholder"));
			}
		}
	}

	// 模擬 placeholder 動作(textarea)
	function simuPlaceholderTextarea(el, action) {
		if(action == "focus" || action == "keydown") {
			if(el.val() == el.attr("placeholder")) {
				el.css("color", "black");
				el.text("");
			} else {
				var showElem = $("#" + el.data("value"));
				var length = el.val().length;
				var maxlength = el.attr("maxlength");
				showElem.text("已輸入" + length + "字，剩" + (maxlength - length) + "字");
			}
		} else if(action == "blur") {
			if(el.val() == "") {
				el.css("color", "#aaa");
				el.text(el.attr("placeholder"));
			}
		}
	}
});
