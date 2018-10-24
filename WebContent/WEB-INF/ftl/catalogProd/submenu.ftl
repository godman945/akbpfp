<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<#-- submenu start -->

<#--商品管理 -->
<div class="submn">
	<@s.if test="%{#uri.indexOf('/catalogProd') > -1 ||
				   #uri.indexOf('/addCatalog') > -1 ||
				   #uri.indexOf('/catalogUpload') > -1 ||
				   #uri.indexOf('/selectUpload') > -1 ||
				   #uri.indexOf('/prodListCardStyleView') > -1 ||
				   #uri.indexOf('/prodListTableStyleView') > -1 ||
				   #uri.indexOf('/queryCatalogGroup') > -1 ||
				   #uri.indexOf('/queryProdGroupFilterProdList') > -1 ||
				   #uri.indexOf('/queryProdGroupFilterItem') > -1 ||
				   #uri.indexOf('/queryProdGroupList') > -1 ||
				   #uri.indexOf('/setup') > -1 }">
        <a href="<@s.url value="/" />catalogProd.html"><b>商品目錄</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />catalogProd.html" style="color:#1d5ed6;text-decoration:underline">商品目錄</a>
    </@s.else>
</div>

<div class="submn">
	<@s.if test="%{#uri.indexOf('/logo') > -1 ||
				   #uri.indexOf('/logo') > -1 ||
				   #uri.indexOf('/logo') > -1}">
        <a href="<@s.url value="/" />logo.html" style="text-decoration:none"><b>LOGO管理</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />logo.html">LOGO管理</a>
    </@s.else>
</div>
<#--商品管理 -->