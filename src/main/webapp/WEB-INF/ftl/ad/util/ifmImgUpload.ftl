<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PChome 聯播網廣告</title>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-1.8.3.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-common.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.swfobject.1-1-1.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.datepick-zh-TW.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.blockUI.js"></script>
</head>
<script language="JavaScript">
function initPage() {
	var msg = "${result}";
	
	if(msg == "resizeOK") {
		parent.document.getElementById("imghead").src = "<@s.url value="/" />img/tmp/${ulTmpName}${time}.${imgType}?time=${time}" ;
		parent.document.getElementById("previewImg").src = "<@s.url value="/" />img/tmp/${ulTmpName}${time}.${imgType}?time=${time}";
		parent.document.getElementById("previewImg").style.display = "inline";
		parent.document.getElementById("imgFile").value = "${imgFile}";
		parent.document.getElementById("chkFile").innerText = "";
		parent.document.getElementById("chkFile").textContent = "";
	} else if(msg == "overSize") {
	//	parent.document.getElementById("chkFile").style.color = "red";
	//	parent.document.getElementById("chkFile").innerText = "檔案大小超過 １ＭＢ，請重新選擇！";
	//	parent.document.getElementById("chkFile").textContent = "檔案大小超過 １ＭＢ，請重新選擇！";
	
	
		var imghead  = parent.document.getElementById("imghead");
		var previewImg  = parent.document.getElementById("previewImg");
		var uploadFile  = parent.document.getElementById("uploadFile");
		var chkFile = parent.document.getElementById("chkFile");
		var sizeCheckDiv  = parent.document.getElementById("sizeCheckDiv");
		var uploadCheckDiv  = parent.document.getElementById("uploadCheckDiv");
		$(sizeCheckDiv).css("display","");
		$(uploadCheckDiv).css("display","none");
		$(imghead).attr("src", "./html/img/upl9090.gif");
		$(previewImg).attr("src", "./html/img/upl9090.gif");
		$(uploadFile).replaceWith($(uploadFile).clone());
		$(chkFile).css("display","none");
	} else if(msg == "noFile") {
		parent.document.getElementById("chkFile").style.color = "red";
		parent.document.getElementById("chkFile").innerText = "請選擇圖片檔！";
		parent.document.getElementById("chkFile").textContent = "請選擇圖片檔！";
	} else if(msg == "notSize") {
		var imghead  = parent.document.getElementById("imghead");
		var previewImg  = parent.document.getElementById("previewImg");
		var uploadFile  = parent.document.getElementById("uploadFile");
		var chkFile = parent.document.getElementById("chkFile");
		var sizeCheckDiv  = parent.document.getElementById("sizeCheckDiv");
		var uploadCheckDiv  = parent.document.getElementById("uploadCheckDiv");
		var chkFile = parent.document.getElementById("chkFile");
		var imgFile = parent.document.getElementById("imgFile");

		$(imghead).attr("src", "./html/img/upl9090.gif");
		$(previewImg).attr("src", "./html/img/upl9090.gif");
		//$(uploadFile).replaceWith($(uploadFile).clone());
		$(chkFile).css("display","inline");
		$(chkFile).text("請選擇圖片寬度與高度相同的檔案！");
		$(previewImg).removeAttr("style");
		//$(imgFile).val("");
	} else if(msg == "resizeErr") {
		parent.document.getElementById("chkFile").style.color = "red";
		parent.document.getElementById("chkFile").innerText = "系統問題，請與服務人員聯絡，謝謝您！";
		parent.document.getElementById("chkFile").textContent = "系統問題，請與服務人員聯絡，謝謝您！";
	} else if(msg == "errorSize") {
	var imghead  = parent.document.getElementById("imghead");
		var previewImg  = parent.document.getElementById("previewImg");
		var uploadFile  = parent.document.getElementById("uploadFile");
		var chkFile = parent.document.getElementById("chkFile");
		var sizeCheckDiv  = parent.document.getElementById("sizeCheckDiv");
		var uploadCheckDiv  = parent.document.getElementById("uploadCheckDiv");
		var chkFile = parent.document.getElementById("chkFile");
		var imgFile = parent.document.getElementById("imgFile");

		$(imghead).attr("src", "./html/img/upl9090.gif");
		$(previewImg).attr("src", "./html/img/upl9090.gif");
		//$(uploadFile).replaceWith($(uploadFile).clone());
		$(chkFile).css("display","inline");
		$(chkFile).text("請上傳250 x 250以上的圖片！");
		$(previewImg).removeAttr("style");
	}
}

</script>
<body onLoad="initPage();">
</body>
</html>