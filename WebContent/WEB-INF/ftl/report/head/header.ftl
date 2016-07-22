<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PChome 關鍵字廣告</title>

    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/style.css" />
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/datePicker/jquery-ui-1.9.2.custom.min.css" />
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
        
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.swfobject.1-1-1.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.datepick-zh-TW.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.tablesorter.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.blockUI.js"></script>
	<script language="JavaScript" src="http://cdn.hcharts.cn/highcharts/highcharts.js"></script>
	<script language="JavaScript" src="http://code.highcharts.com/modules/exporting.js"></script>
    <script language="JavaScript" src="<@s.url value="/" />html/js/report/commonReport.js"></script>
    <script language="JavaScript" src="<@s.url value="/" />html/js/report/<@t.getAsString name="jsName" />" ></script>

</head>

<body>

<#if back2PFD?exists && back2PFD!="">
<!-- 返回經銷商 start -->
<div class="" style=" position: fixed; width:226px; height:54px; background: url(<@s.url value="/" />html/img/back.png); bottom:0; left:0;"><a href="${back2PFD!}" style=" display:block;width:226px; height:54px;color:#FFF; font-size:20px; font-weight:bold; line-height:40px;text-align:left; text-decoration:none; text-indent:-9999px;">返回經銷商</a></div>
<!-- 返回經銷商 end -->
</#if>

<!-- header start -->
<div class="topbar">
	<div class="logo"><a href="http://www.pchome.com.tw/" target="_blank"><img src="<@s.url value="/" />html/main/img/logo_pchome.gif" border="0"></a><a href="http://4c.pchome.com.tw/ad/" target="_blank"><img src="<@s.url value="/" />html/main/img/logo_index.gif" border="0"></a><a href="<@s.url value="/" />" border="0"><img src="<@s.url value="/" />html/main/img/logo_pro.gif" alt="付費刊登" border="0"></a></div>
 	<div class="login">
 		<#if id_pchome?exists && user_id?exists>
 			<img src="<@s.url value="/" />html/img/icon_bb.gif" hspace="3" align="absmiddle" />
        	<b>${id_pchome}</b> 您好 ( <a href="<@s.url value="/" />logout.html">登出</a> )
 		<#else>
 			<a href="<@s.url value="/" />login.html">登入</a>
 		</#if>
 		．<a href="http://4c.pchome.com.tw/ad/" target="_blank">廣告刊登</a>．<a href="http://www.pchome.com.tw" target="_blank">PChome</a>
 	</div>
</div>
<!-- header end -->