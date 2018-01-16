<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<link rel="stylesheet" type="text/css" href="<@s.url value="/" />html/main/css/base.css" />
<link rel="stylesheet" type="text/css" href="<@s.url value="/" />html/main/css/layout.css" />
<script language="JavaScript" src="<@s.url value="/" />html/main/js/faq.js"></script>

<div class="topbar">
	<div class="logo">
		<a href="http://show.pchome.com.tw/" target=""><img src="<@s.url value="/" />html/img/logo_pchome.png" border="0" /></a>
	</div>
	<div class="login">
 		<#if id_pchome?exists>
			<img src="<@s.url value="/" />html/main/img/icon_bb.gif" hspace="3" align="absmiddle">
			<b>${id_pchome}</b> 您好 ( <a href="<@s.url value="/" />logout.html" muse_scanned="true">登出</a> )
		<#else>
			<a href="<@s.url value="/" />login.html">登入</a>
		</#if>
	</div>
</div>

<div id="top" style="width:100%;">
