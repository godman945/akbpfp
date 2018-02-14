<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="title" content="PChome 聯播網廣告 – 精準網路廣告行銷" />
<meta name="description" content="使用PChome聯播網廣告，最精準的網路廣告行銷工具，廣告曝光不計費，點擊廣告才計費，不僅提高廣告主的廣告曝光量，也提高網站的流量。" />
<meta name="keywords" content="聯播網,google 聯播網,聯播網廣告,聯播網廣告費,聯播網廣告費用,聯播網行銷,聯播網廣告,Google adwords,興趣行銷,再行銷,標籤廣告,網頁廣告,Google 廣告,Yahoo廣告,PChome 廣告,露天廣告,買廣告,廣告曝光,影音廣告,影片廣告,Html5廣告,精準廣告,精準行銷,商品廣告,動態商品廣告,精準TA,圖像廣告,圖文廣告,PChome聯播網,CPC,CPM,CPV,點過廣告,影音聯播網,Html5聯播網,Html廣告,Html聯播網,電商" />
<link rel="shortcut icon" href="http://www.pchome.com.tw/favicon.ico">
<title>PChome 聯播網廣告</title>
    <link href="<@s.url value="/" />html/css/style.css" rel="stylesheet" type="text/css" /> 
    <@t.insertAttribute name="css" />
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-1.8.3.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-common.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.blockUI.js"></script>
	<script language="JavaScript" src="https://code.highcharts.com/highcharts.js"></script>
	<script language="JavaScript" src="http://code.highcharts.com/modules/exporting.js"></script>
	<!--
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.tablesorter.combined.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.tablesorter.widgets.js"></script>
	-->
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

