<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adPlugInStyle.css" />" />
<link type="text/css" rel="stylesheet" href="<@s.url value="/html/css/ad/adAdAddManyURL.css" />" /> 

<#if adStyle == "TXT">
	<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adAddTxt.js" ></script>
<#elseif adStyle == "TMG">
	<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adAddTmg.js?t=20180221001" ></script>
<#elseif adStyle == "VIDEO">
	<@t.insertAttribute name="includeJs" />
</#if>

<div class="cont">
<#if adStyle == "TXT">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdAdAddTxt.html">
<#elseif adStyle == "TMG">
    <form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddTmg.html">
<#elseif adStyle == "VIDEO">
	<form method="post" id="modifyForm" name="modifyForm" enctype="multipart/form-data" action="doAdAdAddVideo.html">
</#if>
		<h1 class="adtitle">廣告：${adActionName!} > 分類：${adGroupName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增廣告
		</h2>

		<!-- 快速網址刊登 START-->
		<div class="addN-container">
				<div class="addN-card-piece selected-stat">
					<div class="tag fastURLAdAdd">多筆網址刊登</div>
				</div>
				<div class="ultext" style="">僅需提供您的商品賣場網址或單一商品網址，系統自動會幫您載回商品資訊輕鬆上稿</div>
		</div>
		<div class="grtba">
			<#if bookmark??>
				<@t.insertAttribute name="fastPublish"/>
			</#if>
		</div>
		<!-- 快速網址刊登 END-->


		<#if adStyle == "VIDEO">
			<div class="steps" style="background:none;">輸入廣告基本設定 &gt; 建立分類及出價  &gt; <b>製作影音廣告</b>  &gt; 廣告完成 </div>
		<#else>
			<div class="steps" style="background:none;">輸入廣告基本設定 &gt; 建立分類及出價  &gt; <b>製作廣告及關鍵字設定</b>  &gt; 廣告完成 </div>
		</#if>
	
		<ul class="admenuul">
			<#if adStyle == "VIDEO">
				<li class="m03"><a href="adAdAdd.html?adGroupSeq=${adGroupSeq!}&adOperatingRule=VIDEO" class="active" onClick="return(chkLeave())">影音廣告</a></li>
			<#else>
				<li class="m01"><a href="adAddImg.html?adGroupSeq=${adGroupSeq!}&adOperatingRule=MEDIA" onClick="return(chkLeave())">圖像廣告</a></li>
		    	<li class="m02"><a href="adAdAdd.html?adGroupSeq=${adGroupSeq!}&adOperatingRule=MEDIA" class="active" onClick="return(chkLeave())">圖文廣告</a></li>
			</#if>
	    </ul>

		<#if adStyle == "TMG">
			<#-- 只有圖文廣告有刊登廣告頁籤切換 -->
			<div class="addN-container">
				<div class="addN-card-piece">
					<div class="tag adAdd">一般廣告刊登</div>
				</div>
				<div class="addN-card-piece">
					<div class="tag fastURLAdAdd">多筆網址刊登</div>
				</div>
				<div class="ultext" style="display:none;">僅需提供您的商品賣場網址或單一商品網址，系統自動會幫您載回商品資訊輕鬆上稿</div>
			</div>
			<#-- 刊登廣告頁籤切換 end-->
		</#if>

    	<div class="grtba">
		<#-- 判斷adStyle，插入相對應的細部畫面 -->
		<#if adStyle == "TXT">
			<#-- adTxt start -->
			<@t.insertAttribute name="adTxt" />
			<#-- adTxt end -->
		<#elseif adStyle == "TMG">
			<#-- adTmg start -->
			<#if bookmark?exists && bookmark == "fastURLAdAdd">
				<@t.insertAttribute name="adTmgManyURL"/>
			<#else>
				<@t.insertAttribute name="adTmg"/>
			</#if>
			<#-- adTmg end -->
		<#elseif adStyle == "VIDEO">
			<@t.insertAttribute name="adAddVideo" />
		</#if>
 
		<#if adStyle == "TXT" || adStyle == "TMG">
			<#-- adKeyword start -->
			<div id=keywordBody>
				<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adKeywordAdd.js" ></script>
				<@t.insertAttribute name="adKeyword" />
			</div>
			<#-- adKeyword end -->
		</#if>
		
		<!-- 送出區塊 START-->
			<span class="t_s01">※※※ 提醒您，您的廣告將在3工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
	        <#if bookmark??>
		        <center style="margin-top:10px;">
					<input type="button" id="cancel" value="取 消"> 
					<input type="button" id="save" value="下一步" onclick="fastPublishNext();"> 
				</center>
	        	<#else>
	        	<center style="margin-top:10px;">
					<input type="button" id="cancel" value="取 消"> 
					<input type="button" id="save" value="送出審核"> 
				</center>
				<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq!}">
				<input type="hidden" id="saveAndNew" name="saveAndNew" value="${saveAndNew!}">
				<input type="hidden" id="ulTmpName" name="ulTmpName" value="${ulTmpName!}">
				<input type="hidden" id="imgFile" name="imgFile" value="${imgFile!}">
		        <input type="hidden" id="backPage" name="backPage" value="${backPage!}">
		        <input type="hidden" id="adType" name="adType" value="${adType!}">
	        </#if>
        <!-- 送出區塊 END-->
	</form>
	<iframe id="uploadIMG" name="uploadIMG" style="display:none;height:150px;width:600px"></iframe>
	<iframe id="doAdd" name="doAdd" style="display:none;height:150px;width:600px"></iframe>
</div>
<input type="hidden" id="messageId" value="${message!!}">