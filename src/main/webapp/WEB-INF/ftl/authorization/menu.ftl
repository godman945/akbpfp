<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<!-- menu start -->
<div class="nvbar">
	<div class="tab_on">
		<a href="<@s.url value="/" />apply.html">帳戶管理</a>
	</div>
	<div class="rbtn">
		<a href="<@s.url value="/" />faq.html" target="_blank">常見問題</a>
	</div>
	<br clear="all">
<!-- menu end -->
<!-- submenu start -->
	<div class="submn"><a href="<@s.url value="/authorization.html" />" >
		<@s.if test="%{#uri.indexOf('authorization.html') > -1 }">
			<b>建立帳戶資料</b>
		</@s.if>
	    <@s.else>
	       	建立帳戶資料
	    </@s.else>
		</a></div>
		
<!-- submenu end -->  
	
</div>