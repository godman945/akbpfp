<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<#-- 利用 freemaker 的date 屬性進行時間的判斷 -->
<#-- 設定今天的時間 -->
<#assign today = .now>
<#-- 設定判斷時間 -->
<#assign checkDate1 = ("2014-03-03 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 
<#assign checkDate2 = ("2014-03-26 00:00:00")?date("yyyy-MM-dd HH:mm:ss")> 

	<script type="text/javascript" src="<@s.url value="/" />html/main/js/jquery.min.js"></script>
	<script type="text/javascript" src="<@s.url value="/" />html/main/js/base.js"></script>
	<script type="text/javascript" src="http://rmi.pchome.com.tw/rmiservice/pv.html?t=pfp:pv_pfp_click"></script>

	<div id="header">
		<div class="nav" style="display:none;">
			<ul id="topNavi">
				<li><a class="newp" href="<@s.url value="/" />index.html#content"></a></li>
				<li><a class="show" href="<@s.url value="/" />show.html#content"></a></li>
				<li><a class="pay" href="<@s.url value="/" />pay.html#content"></a></li>
				<li><a class="advantage" href="<@s.url value="/" />advantage.html#content"></a></li>
				<li><a class="qa" href="<@s.url value="/" />faq.html" target="_blank"></a></li>	
			</ul>
		</div>
		<div class="cotnav">
			<ul id="gNavi" >
				<li><a class="newp" href="<@s.url value="/" />index.html#content"></a></li>
				<li><a class="show" href="<@s.url value="/" />show.html#content"></a></li>
				<li><a class="pay" href="<@s.url value="/" />pay.html#content"></a></li>
				<li><a class="advantage" href="<@s.url value="/" />advantage.html#content"></a></li>
				<li><a class="qa" href="<@s.url value="/" />faq.html" target="_blank"></a></li>	
			</ul>
		</div>
	</div>
	<div class="container clearfix">
	<#-- 判斷今天超過檢查時間了沒；沒超過，用原圖；超過，顯示新圖 -->
	<#if today?date < checkDate1?date> 
		<div id="content" style="background:url(<@s.url value="/" />html/main/img/click.jpg) top center no-repeat;height:900px">
		</div>
	<#elseif today?date < checkDate2?date>
		<div id="content" style="background:url(<@s.url value="/" />html/main/img/click.png) top center no-repeat;height:900px">
		</div>
	<#else>
		<div id="content" style="background:url(<@s.url value="/" />html/main/img/click_20140611.png) top center no-repeat;height:900px">
		</div>
	</#if>
	</div>
</div>