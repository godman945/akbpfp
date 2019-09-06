<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <title>PChome 聯播網廣告</title>

	<!-- CSS -->
    <link type="text/css" rel="stylesheet" href="https://code.jquery.com/ui/1.9.2/themes/base/jquery-ui.css">
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/fancybox/jquery.fancybox-1.3.4.css" />
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/floatingscroll/jquery.floatingscroll-3.0.5.css" />
    <!-- 需放最後面，可能有調整前面套件的css -->
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/style2019.min.css?t=20190828001" />
    
    <!-- js -->
	<script language="JavaScript" src="https://code.highcharts.com/modules/exporting.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.swfobject.1-1-1.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery-ui-1.9.2.custom.min.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.datepick-zh-TW.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.fancybox-1.3.4.js"></script>
	<script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.tablesorter.js"></script>
    <script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.ba-dotimeout.js"></script>
    <script language="JavaScript" src="<@s.url value="/" />html/js/jquery/jquery.floatingscroll-3.0.5.js"></script>
    
    <script language="JavaScript" src="<@s.url value="/" />html/js/report/commonReport.js?t=20190906001"></script>
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
	<div class="logo"><a href="http://show.pchome.com.tw/" target=""><img src="<@s.url value="/" />html/img/logo_pchome.png" border="0" /></a></div>
 	<div class="login">
 		<#if id_pchome?exists && user_id?exists>
 			<img src="<@s.url value="/" />html/img/icon_bb.gif" hspace="3" align="absmiddle" />
        	<b><#if bu_id?exists>${bu_id}<#else>${id_pchome}</#if></b> 您好 ( <a href="<@s.url value="/" />logout.html">登出</a> )
 		<#else>
 			<a href="<@s.url value="/" />login.html">登入</a>
 		</#if>
 		．<a href="http://www.pchome.com.tw" target="">PChome</a>
 	</div>
</div>
<!-- header end -->