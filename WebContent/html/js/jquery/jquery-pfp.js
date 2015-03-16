function alertDialog(num){
	
	switch(num){
	  case 1:
		  $("#dialog").html("系統繁忙，請稍後再試！");
	   break;
	  case 2:
		  $("#dialog").html("網址連結失效，請輸入有效連結網址！");
	   break;
	  default:
	   document.writeln("show others!!");
	   break;
	 }
	
	$("#dialog").dialog({
		title: "錯誤訊息",
		closeOnEscape: false,
		height: 150,
		width: 200,
		modal: true,
		draggable: false,
		resizable: false, 
		buttons: {
            確定: function() {
                $(this).dialog("close");
            }
        },
        open: function(event, ui) {
            //隱藏「x」關閉按鈕
            $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
        }
	});
}
function waitDialog(num){
	
	$("#dialog").html("網址驗證中.....請稍後....");
	$("#dialog").dialog({
		closeOnEscape: false,
		height: 150,
		width: 200,
		modal: true,
		draggable: false,
		resizable: false, 
        open: function(event, ui) {
            //隱藏「x」關閉按鈕
            $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
        }
	});
}

function alertSuccessDialog(num){
	
	$("#dialog").html("修改資料成功");
	$("#dialog").dialog({
		title: "帳戶將態",
		closeOnEscape: false,
		height: 150,
		width: 250,
		modal: true,
		draggable: false,
		resizable: false, 
		buttons: {
            確定: function() {
                $(this).dialog("close");
            },
            取消: function() {
                $(this).dialog("close");
            }
        },
        open: function(event, ui) {
            //隱藏「x」關閉按鈕
            $(this).parent().children().children('.ui-dialog-titlebar-close').hide();
        }
	});
}
