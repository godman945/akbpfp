<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<!-- submenu start -->

<div class="submn">
	<@s.if test="%{#uri.indexOf('/catalog') > -1 ||
				   #uri.indexOf('/adAddFinish') > -1 ||
				   #uri.indexOf('/adAddImg') > -1}">
        <a href="<@s.url value="/" />catalog.html"><b>商品目錄</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />catalog.html" style="color:#db0000;text-decoration:underline">商品目錄</a>
    </@s.else>
</div>



<div class="submn">
	<@s.if test="%{#uri.indexOf('/logo') > -1 ||
				   #uri.indexOf('/adKeywordView') > -1 ||
				   #uri.indexOf('/searchAdKeyword') > -1}">
        <a href="<@s.url value="/" />logo.html" style="text-decoration:none"><b>LOGO管理</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />logo.html">LOGO管理</a>
    </@s.else>
</div>
