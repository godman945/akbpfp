<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<#-- 利用 freemaker 的date 屬性進行時間的判斷 -->
<#-- 設定今天的時間 -->
<#assign today = .now>
<#-- 設定判斷時間 -->
<#assign checkDate = ("2014-02-26 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 

<!-- header start -->
<div class="topbar">
    <div class="logo"><a href="http://show.pchome.com.tw/" target=""><img src="<@s.url value="/" />html/img/logo_pchome.png" border="0" /></a></div>
	<#-- 判斷今天超過檢查時間了沒；沒超過，顯示字串；超過，不顯示字串 -->
	<#if today?date < checkDate?date> 
		<@s.if test="%{#uri.indexOf('/apply') > -1 || #uri.indexOf('/clause') > -1}">
	    	　<a href="click.html" target="_blank" style="text-decoration:none">新戶好康送：2014.3.31前儲值花費達$500，加碼送$1500！</a>
		</@s.if>
	</#if>
 	<div class="login">
 		<#--
 		<#if id_pchome?exists && user_id?exists>
 		-->
 		<#if id_pchome?exists>
 			<img src="<@s.url value="/" />html/img/icon_bb.gif" hspace="3" align="absmiddle" />
        	<b>${id_pchome}</b> 您好 ( <a href="<@s.url value="/" />logout.html">登出</a> )
 		<#else>
 			<a href="<@s.url value="/" />login.html">登入</a>
 		</#if>
 	．<a href="http://www.pchome.com.tw" target="">PChome</a>
 	</div>
</div>
<!-- header end -->
