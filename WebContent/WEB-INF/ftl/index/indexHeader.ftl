<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#-- 利用 freemaker 的date 屬性進行時間的判斷 -->
<#-- 設定今天的時間 -->
<#assign today = .now>
<#-- 設定判斷時間 -->
<#assign checkDate1 = ("2014-03-03 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 
<#assign checkDate2 = ("2014-03-26 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 

<link rel="stylesheet" type="text/css" href="<@s.url value="/" />html/main/css/base.css" />
<link rel="stylesheet" type="text/css" href="<@s.url value="/" />html/main/css/layout.css" />
<div class="topbar">
	<div class="logo"><a href="http://www.pchome.com.tw/" target="_blank"><img src="<@s.url value="/" />html/main/img/logo_pchome.gif" border="0"></a><a href="http://4c.pchome.com.tw/ad/" target="_blank"><img src="<@s.url value="/" />html/main/img/logo_index.gif" border="0"></a><a href="<@s.url value="/" />" border="0"><img src="<@s.url value="/" />html/main/img/logo_pro.gif" alt="付費刊登" border="0"></a></div>
	<div class="login">
 		<#if id_pchome?exists>
			<img src="<@s.url value="/" />html/main/img/icon_bb.gif" hspace="3" align="absmiddle">
			<b>${id_pchome}</b> 您好 ( <a href="<@s.url value="/" />logout.html" muse_scanned="true">登出</a> )
		<#else>
			<a href="<@s.url value="/" />login.html">登入</a>
		</#if>
	．<a href="http://4c.pchome.com.tw/ad/" target="_blank">廣告刊登</a>．<a href="http://www.pchome.com.tw" target="_blank">PChome</a>
	</div>
</div>

<div id="top" style="width:100%;">
	<div id="header">
		<div id="header-top">
			<a href="<@s.url value="/" />redirect.html"><img src="<@s.url value="/" />html/main/img/logo_tt.gif" border="0" style="position:absolute; left: 160px; top: 179px;"></a>
			<a href="<@s.url value="/" />redirect.html" class="btngo" title="立即購買"></a> 
			<a href="http://www.pchome.com.tw/" class="logo" target="_blank"><img src="<@s.url value="/" />html/main/img/top_pchome.png" alt="PChome" border="0" /></a>
			<a href="<@s.url value="/" />faq.html?fid=3&qid=25" target="_blank" class="faq"><img src="<@s.url value="/" />html/main/img/tt_fq.png" border="0"  alt="FAQ"/></a>
			<img src="<@s.url value="/" />html/main/img/icon_note.png" width="246" height="128" border="0" style="position:absolute; left: 208px; top: 484px;">
            <img src="<@s.url value="/" />html/main/img/top_ad_20140301up.png" border="0" />
			<img src="<@s.url value="/" />html/main/img/icon_3s.gif" alt="三大優勢" border="0" style="position:absolute; left: 45px; top: 361px;" />
			<img src="<@s.url value="/" />html/main/img/tt_pp.png" border="0" class="tt_pp">	
		</div>
	</div>