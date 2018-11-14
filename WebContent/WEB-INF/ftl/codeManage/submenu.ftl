<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<#-- submenu start -->

<#--再行銷追蹤 -->
<div class="submn">
	<@s.if test="%{#uri.indexOf('/retargetingTracking') > -1 ||
				   #uri.indexOf('/retargetingTracking') > -1 }">
        <a href="<@s.url value="/" />retargetingTracking.html"><b>再行銷追蹤</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />retargetingTracking.html" style="color:#1d5ed6;text-decoration:underline">再行銷追蹤</a>
    </@s.else>
</div>

<#--轉換追蹤 -->
<div class="submn">
	<@s.if test="%{#uri.indexOf('/convertTracking') > -1 ||
				   #uri.indexOf('/convertTracking') > -1}">
        <a href="<@s.url value="/" />convertTracking.html" style="text-decoration:none"><b>轉換追蹤</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />convertTracking.html">轉換追蹤</a>
    </@s.else>
</div>
