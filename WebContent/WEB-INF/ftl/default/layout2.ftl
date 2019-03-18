<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<#-- 維護公告
<SCRIPT LANGUAGE="JavaScript">
    window.location="http://help.pchome.com.tw/notice/nid.html?nidx=387&btype=1";
</script>
-->

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>PChome 聯播網廣告</title>
	<script language="JavaScript" src="<@s.url value="/" />html/js/<@t.getAsString name="jsName" />" ></script>  
</head>

<body>

<@t.insertAttribute name="body" />

</body>
</html>