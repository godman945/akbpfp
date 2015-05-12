<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#assign today = .now>
<#assign startDate = ("2015-03-18 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 
<#assign endDate = ("2015-05-11 23:59:59")?date("yyyy-MM-dd HH:mm:ss")> 

<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/css/style.css" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/main/css/base.css" />

<#if (today?date >= startDate?date) && (today?date <= endDate?date)>
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/main/css/layout_motherday.css" />
<#else>
    <link type="text/css" rel="stylesheet" href="<@s.url value="/" />html/main/css/layout.css" />
</#if>

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

<#if (today?date >= startDate?date) && (today?date <= endDate?date)>

    <!-- 母親節活動 start -->
	<div id="header">
		<div id="header-top">
			<a href="<@s.url value="/" />redirect.html" class="ad4c"><img src="<@s.url value="/" />html/main/img/topad_150511.png" border="0"  alt="儲三千送一千"></a>
			<a href="<@s.url value="/" />redirect.html"><img src="<@s.url value="/" />html/main/img/logo_tt.gif" border="0" class="logo_tt"></a>
			<a href="<@s.url value="/" />redirect.html" class="btngo" title="立即購買"></a> 
			<a href="http://www.pchome.com.tw/" class="logo" target="_blank"><img src="<@s.url value="/" />html/main/img/top_pchome.png" alt="PChome" border="0" /></a>
			<a href="<@s.url value="/" />faq.html?fid=3&qid=25" target="_blank" class="faq"><img src="<@s.url value="/" />html/main/img/tt_fq.png" border="0"  alt="FAQ"/></a>
			<img src="<@s.url value="/" />html/main/img/icon_note1.png" border="0" class="icon_note1">
            <img src="<@s.url value="/" />html/main/img/icon_3s_motherday.gif" alt="三大優勢" border="0" class="icon_3s">
			<img src="<@s.url value="/" />html/main/img/tt_pp2.png" class="tt_pp">
		</div>
	</div>
    <!-- 母親節活動 end -->

<#else>
    <!-- 原版 start -->
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
    <!-- 原版 end -->

</#if>
