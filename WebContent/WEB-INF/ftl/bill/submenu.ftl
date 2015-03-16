<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />

<!-- submenu start -->
<div class="submn"><a href="<@s.url value="/" />transDetail.html">
	<@s.if test="%{#uri.indexOf('transDetail.html') > -1}">
        <b>交易明細</b>
    </@s.if>
    <@s.else>
       	交易明細
    </@s.else>
</a></div>

<div class="submn"><a href="<@s.url value="/" />paySearch.html">
	<@s.if test="%{#uri.indexOf('paySearch.html') > -1}">
        <b>付費查詢</b>
    </@s.if>
    <@s.else>
       	付費查詢
    </@s.else>
</a></div>
<!-- submenu end -->