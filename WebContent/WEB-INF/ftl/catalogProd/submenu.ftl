<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<!-- submenu start -->

<div class="submn">
	<@s.if test="%{#uri.indexOf('/catalogProd') > -1 ||
					#uri.indexOf('/addCatalog') > -1 }">
        <a href="<@s.url value="/" />catalogProd.html"><b>商品目錄</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />catalogProd.html" style="color:#db0000;text-decoration:underline">商品目錄</a>
    </@s.else>
</div>

<div class="submn">
	<!-- 未完成，待修改 -->
	<@s.if test="%{#uri.indexOf('/catalogProd') > -1 ||
				   #uri.indexOf('/searchAdAction') > -1}">
        <a href="<@s.url value="/" />adActionView.html" style="text-decoration:none"><b>LOGO管理</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />adActionView.html">LOGO管理</a>
    </@s.else>
</div>