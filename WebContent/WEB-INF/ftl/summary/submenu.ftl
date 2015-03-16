<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />

<div class="submn"><a href="<@s.url value="/" />board.html">
	<@s.if test="%{#uri.indexOf('board.html') > -1}">
        <b>公告訊息</b>
    </@s.if>
    <@s.else>
       	公告訊息
    </@s.else>
</a></div>