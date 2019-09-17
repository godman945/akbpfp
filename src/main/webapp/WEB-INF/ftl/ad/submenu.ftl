<#assign s=JspTaglibs["/struts-tags"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
<!-- submenu start -->

<div class="submn">
	<@s.if test="%{#uri.indexOf('/adActionAdd') > -1 ||
				   #uri.indexOf('/adActionEdit') > -1 ||
				   #uri.indexOf('/adGroupAdd') > -1 ||
				   #uri.indexOf('/adGroupEdit') > -1 ||
				   #uri.indexOf('/adAdAdd') > -1 ||
				   #uri.indexOf('/adAdEdit') > -1 ||
				   #uri.indexOf('/adKeywordAdd') > -1 ||
				   #uri.indexOf('/adAddFinish') > -1 ||
				   #uri.indexOf('/adAddImg') > -1}">
        <a href="<@s.url value="/" />adActionAdd.html"><b>新增廣告</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />adActionAdd.html" style="color:#db0000;text-decoration:underline">新增廣告</a>
    </@s.else>
</div>





<!-- <div class="submn">
	<@s.if test="%{#uri.indexOf('/adAddImg') > -1}">
        <a href="<@s.url value="/" />adAdAdd.html?adGroupSeq=${adGroupSeq}" style="text-decoration:none"><b>製作廣告</b></a>
    </@s.if>
</div> -->

<div class="submn">
	<@s.if test="%{#uri.indexOf('/adActionView') > -1 ||
				   #uri.indexOf('/searchAdAction') > -1 ||
				   #uri.indexOf('/adGroupView') > -1 ||
				   #uri.indexOf('/searchAdGroup') > -1 ||
				   #uri.indexOf('/adAdView') > -1 ||
				   #uri.indexOf('/searchAd') > -1 ||
				   #uri.indexOf('/adKeywordView') > -1 ||
				   #uri.indexOf('/searchAdKeyword') > -1}">
        <a href="<@s.url value="/" />adActionView.html" style="text-decoration:none"><b>檢視廣告</b></a>
    </@s.if>
    <@s.else>
       	<a href="<@s.url value="/" />adActionView.html">檢視廣告</a>
    </@s.else>
</div>