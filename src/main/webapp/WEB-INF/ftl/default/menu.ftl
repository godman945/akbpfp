<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<@s.set var="uri" value="%{#request['javax.servlet.forward.request_uri']}" />
 
<div class="nvbar">

<!-- menu start -->
<#if user_id?exists>

	<@s.if test="%{#uri.indexOf('/summary') > -1 || #uri.indexOf('/board') > -1 }">
		<div class="tab_on"><a href="<@s.url value="/" />summary.html">帳戶總覽</a></div>
	</@s.if>
	<@s.else>
	    <div class="tab"><a href="<@s.url value="/" />summary.html">帳戶總覽</a></div>
	</@s.else>
	
	<#if user_privilege == "0" || user_privilege == "1"> 
		<@s.if test="%{#uri.indexOf('/accountInfo') > -1 ||
					#uri.indexOf('/accountInfoModify') > -1  ||
					#uri.indexOf('/accountRemain') > -1  ||
					#uri.indexOf('/accountUsers') > -1 ||
					#uri.indexOf('/accountUserInvite') > -1 ||
					#uri.indexOf('/accountAdmModify') > -1 ||
					#uri.indexOf('/accountUserModify') > -1 ||
					#uri.indexOf('/accountUserSerach') > -1 ||
					#uri.indexOf('/accountContent') > -1 }">
			<div class="tab_on"><a href="<@s.url value="/" />accountInfo.html">帳戶管理</a></div>
		</@s.if>
		<@s.else>
	    	<div class="tab"><a href="<@s.url value="/" />accountInfo.html">帳戶管理</a></div>
		</@s.else>
	</#if>
	
	<#if user_privilege == "0" || user_privilege == "1" || user_privilege == "2" >
		<@s.if test="%{#uri.indexOf('/adActionAdd') > -1 ||
					   #uri.indexOf('/adActionView') > -1 ||
					   #uri.indexOf('/adActionEdit') > -1 ||
					   #uri.indexOf('/adGroupAdd') > -1 ||
					   #uri.indexOf('/adGroupView') > -1 ||
					   #uri.indexOf('/adAdAdd') > -1 ||
					   #uri.indexOf('/adAddFinish') > -1 ||
					   #uri.indexOf('/adAdView') > -1 ||
					   #uri.indexOf('/adKeywordView') > -1 ||
					   #uri.indexOf('/adAddImg') > -1}">
			<div class="tab_on"><a href="<@s.url value="/" />adActionView.html">廣告管理</a></div> 
		</@s.if>
		<@s.else>
	    	<div class="tab"><a href="<@s.url value="/" />adActionView.html">廣告管理</a></div> 
		</@s.else>
	</#if>

	<#if user_privilege == "0" || user_privilege == "1" || user_privilege == "2" >
		<@s.if test="%{#uri.indexOf('/catalogProd') > -1 ||
					   #uri.indexOf('/addCatalog') > -1 ||
					   #uri.indexOf('/savePfpCatalog') > -1 ||
					   #uri.indexOf('/catalogUpload') > -1 ||
					   #uri.indexOf('/selectUpload') > -1 ||
					   #uri.indexOf('/prodListCardStyleView') > -1 ||					  
					   #uri.indexOf('/prodListTableStyleView') > -1 ||
					   #uri.indexOf('/queryCatalogGroup') > -1 ||
					   #uri.indexOf('/queryProdGroupFilterProdList') > -1 ||
					   #uri.indexOf('/queryProdGroupFilterItem') > -1 ||
					   #uri.indexOf('/queryProdGroupList') > -1 ||
					   #uri.indexOf('/logo') > -1 ||
					   #uri.indexOf('/setup') > -1 }">
			<div class="tab_on"><a href="<@s.url value="/" />catalogProd.html">商品管理</a></div> 
		</@s.if>
		<@s.else>
			<#if pfd_customer_info_id == "PFDC20150422001" || pfd_customer_info_id == "PFDC20161012001" ||  pfd_customer_info_id == "PFDC20190329001">
				<div class="tab"><a href="<@s.url value="/" />catalogProd.html">商品管理</a></div> 
			</#if>
		</@s.else>
	</#if>


	<#if user_privilege == "0" || user_privilege == "1" || user_privilege == "2" >
		<@s.if test="%{#uri.indexOf('/retargetingTracking') > -1 ||
					   #uri.indexOf('/addRetargetingTrackingView') > -1 ||
					   #uri.indexOf('/editRetargetingTrackingView') > -1 ||
					   #uri.indexOf('/convertTracking') > -1 ||
					   #uri.indexOf('/addConvertTrackingView') > -1 ||
					   #uri.indexOf('/editConvertTrackingView') > -1 }">
			<!-- <div class="tab_on"><a href="<@s.url value="/" />retargetingTracking.html">代碼管理</a></div> --> 
		</@s.if>
		<@s.else>
	    	<!-- <div class="tab"><a href="<@s.url value="/" />retargetingTracking.html">代碼管理</a></div> --> 
		</@s.else>
	</#if>


	<#if user_privilege == "0" || user_privilege == "1" || user_privilege == "2" || user_privilege == "3">
		<@s.if test="%{#uri.indexOf('/report') > -1 }">
			<div class="tab_on"><a href="<@s.url value="/" />reportExcerpt.html">報表管理</a></div>
		</@s.if>
		<@s.else>
	    	<div class="tab"><a href="<@s.url value="/" />reportExcerpt.html">報表管理</a></div>
		</@s.else>
	</#if>
	
	<@s.if test="%{#uri.indexOf('/transDetail.html') > -1 ||
					#uri.indexOf('/paySearch.html') > -1}">
		<div class="tab_on"><a href="<@s.url value="/" />transDetail.html">帳單管理</a></div>
	</@s.if>
	<@s.else>
	    <div class="tab"><a href="<@s.url value="/" />transDetail.html">帳單管理</a></div>
	</@s.else>
	
	
	<div class="rbtn">
		<a href="<@s.url value="/" />faq.html" target="_blank">常見問題</a>
	</div>
	<br clear="both"/>
</#if>

<!-- menu end -->

<!-- submenu start -->
<@t.insertAttribute name="submenu" />
<!-- submenu end -->

</div>