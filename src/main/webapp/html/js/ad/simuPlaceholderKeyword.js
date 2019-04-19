$(document).ready(function(){
	// 設定 input 的預設值
	$("#adKeyword").val($("#adKeyword").attr("placeholder")).css("color", "#aaa");
	$("#adExcludeKeyword").val($("#adExcludeKeyword").attr("placeholder")).css("color", "#aaa");

	$(document).on("focus", ".inputPlaceholderKeyword", function() {
		simuPlaceholder($(this), "focus");
	}).on("keydown", ".inputPlaceholderKeyword", function() {
		simuPlaceholder($(this), "keydown");
	}).on("blur", ".inputPlaceholderKeyword", function() {
		simuPlaceholder($(this), "blur");
	});

	// 模擬 placeholder 動作
	function simuPlaceholder(el, action) {
		if(action == "focus" || action == "keydown") {
			if(el.val() == el.attr("placeholder")) {
				el.css("color", "black");
				el.val("");
			}
		} else if(action == "blur") {
			if(el.val() == "") {
				el.css("color", "#aaa");
				el.val(el.attr("placeholder"));
			}
		}
	}
});
