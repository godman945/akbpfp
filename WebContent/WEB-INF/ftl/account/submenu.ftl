<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />

<!-- submenu start -->
<div class="submn"><a href="<@s.url value="/" />accountInfo.html">
	<@s.if test="%{#uri.indexOf('/accountInfo') > -1 ||
					#uri.indexOf('/accountInfoModify') > -1}">
        <b>帳戶資訊</b>
    </@s.if>
    <@s.else>
       	帳戶資訊
    </@s.else>
</a></div>
<#-- 預付才顯示 1預付 2後付-->
<#if pay_type == "1" > 
<div class="submn"><a href="<@s.url value="/" />accountRemain.html">
	<@s.if test="%{#uri.indexOf('/accountRemain') > -1}"> 
        <b>帳戶儲值</b>
    </@s.if>
    <@s.else>
       	帳戶儲值
    </@s.else>
</a></div>
</#if>


<#if buAccountVO?exists>
	<#else>
		<div class="submn"><a href="<@s.url value="/" />accountUsers.html">
		<@s.if test="%{#uri.indexOf('/accountUsers') > -1 ||
						#uri.indexOf('/accountUserInvite') > -1 ||
						#uri.indexOf('/accountAdmModify') > -1 ||
						#uri.indexOf('/accountUserModify') > -1 ||
						#uri.indexOf('/accountUserSerach') > -1}">
	        <b>帳戶使用者管理</b>
	    </@s.if>
	    <@s.else>
	       	帳戶使用者管理
	    </@s.else>
		</a></div>
</#if>



<#if buAccountVO?exists>
	<#else>
		<div class="submn"><a href="<@s.url value="/" />accountContent.html">
		<@s.if test="%{#uri.indexOf('/accountContent') > -1}">
	        <b>修改個人資料</b>
	    </@s.if>
	    <@s.else>
	       	修改個人資料
	    </@s.else>
	</a>	
</#if>

</div>
<!-- submenu end -->