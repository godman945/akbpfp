<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-cache, no-store, must-revalidate">
<meta http-equiv="Pragma" content="no-cache">
<meta http-equiv="Expires" content="0">
<meta name="title" content="PChome 關鍵字廣告-精準關鍵字行銷" />
<meta name="description" content="使用PChome關鍵字廣告，最精準的網路廣告行銷工具，關鍵字廣告曝光不計費，點擊廣告才計費，不僅提高廣告主的廣告曝光量，也提高網站的流量。把握每個消費者上網搜尋的動機，及提供使用者最有效的關鍵字廣告資訊。" />
<meta name="keywords" content="關鍵字,google 關鍵字,yahoo 關鍵字,關鍵字廣告,關鍵字排名,關鍵字排行,關鍵字工具,關鍵字廣告費,關鍵字廣告費用,關鍵字搜尋,關鍵字建議工具,關鍵字行銷,關鍵字查詢,關鍵字聯播網,Google adwords,搜尋關鍵字,內容關鍵字,聯播網,興趣行銷,再行銷,標籤廣告,網頁廣告,Google 廣告,Yahoo　廣告,Pchome 廣告,露天廣告,買廣告,廣告曝光" />
<link rel="shortcut icon" href="http://www.pchome.com.tw/favicon.ico">
<title>PChome 關鍵字廣告</title>
    <link href="<@s.url value="/" />html/css/style.css" rel="stylesheet" type="text/css" /> 
    <@t.insertAttribute name="css" />
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-1.8.3.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-common.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.blockUI.js"></script>
    <@t.insertAttribute name="js" />	
    
</head>

<body>
<#if back2PFD?exists && back2PFD!="">
<!-- 返回經銷商 start -->
<div class="" style=" position: fixed; width:226px; height:54px; background: url(<@s.url value="/" />html/img/back.png); bottom:0; left:0; z-index:1000;"><a href="${back2PFD!}" style=" display:block;width:226px; height:54px;color:#FFF; font-size:20px; font-weight:bold; line-height:40px;text-align:left; text-decoration:none; text-indent:-9999px;">返回經銷商</a></div>
<!-- 返回經銷商 end -->
</#if>
<#-- 返回管理者介面 -->
<#if accountType ? exists && '${accountType}' == "PM" >
	<div class="" style=" position: fixed; width:226px; height:54px; background: url(<@s.url value="/" />html/img/back2.png); bottom:0; left:0; z-index:1000;"><a href="accountList.html" style=" display:block;width:226px; height:54px;color:#FFF; font-size:20px; font-weight:bold; line-height:40px;text-align:left; text-decoration:none; text-indent:-9999px;">返回管理者介面</a></div>
	<#elseif "${root_user}" == "PM">
	<div class="" style=" position: fixed; width:226px; height:54px; background: url(<@s.url value="/" />html/img/back2.png); bottom:0; left:0; z-index:1000;"><a href="accountList.html" style=" display:block;width:226px; height:54px;color:#FFF; font-size:20px; font-weight:bold; line-height:40px;text-align:left; text-decoration:none; text-indent:-9999px;">返回管理者介面</a></div>
</#if>

