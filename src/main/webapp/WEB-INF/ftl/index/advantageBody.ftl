<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

	<script type="text/javascript" src="<@s.url value="/" />html/main/js/jquery.min.js"></script>
	<script type="text/javascript" src="<@s.url value="/" />html/main/js/base.js"></script>
	<script type="text/javascript" src="http://rmi.pchome.com.tw/rmiservice/pv.html?t=pfp:pv_pfp_advantage"></script>
	
	<div id="header">
		<div class="nav" style="display:none;">
			<ul id="topNavi">
				<li><a class="newp" href="<@s.url value="/" />index.html#content"></a></li>
				<li><a class="show" href="<@s.url value="/" />show.html#content"></a></li>
				<li><a class="pay" href="<@s.url value="/" />pay.html#content"></a></li>
				<li><a class="advantage selected" href="<@s.url value="/" />advantage.html#content"></a></li>
				<li><a class="qa" href="<@s.url value="/" />faq.html" target="_blank"></a></li>	
			</ul>
		</div>
		<div class="cotnav">
			<ul id="gNavi" >
				<li><a class="newp" href="<@s.url value="/" />index.html#content"></a></li>
				<li><a class="show" href="<@s.url value="/" />show.html#content"></a></li>
				<li><a class="pay" href="<@s.url value="/" />pay.html#content"></a></li>
				<li><a class="advantage selected" href="<@s.url value="/" />advantage.html#content"></a></li>
				<li><a class="qa" href="<@s.url value="/" />faq.html" target="_blank"></a></li>	
			</ul>
		</div>
	</div>
	<div class="container clearfix">
		<div id="content" style="background:url(<@s.url value="/" />html/main/img/cont04.png) top center no-repeat;height:750px">
		</div>
	</div>
</div>