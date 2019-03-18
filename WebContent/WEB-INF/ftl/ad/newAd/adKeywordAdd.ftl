<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div class="cont">
    <form method="post" id="modifyForm" name="modifyForm" action="doAdKeywordAdd.html">
		<h1 class="adtitle">廣告：${adActionName!} > 分類：${adGroupName!}</h1>
		<h2>
			<div class="cal">帳戶名稱：${customer_info_title!}</div>
			<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">新增關鍵字
		</h2>
		<div class="steps"><b>關鑑字設定</b> </div>
		<!-- adKeyword start -->
		<script language="JavaScript" src="<@s.url value="/" />html/js/ad/adKeywordAdd.js" ></script>
		<@t.insertAttribute name="adKeyword" />
		<!-- adKeyword end -->
		<span class="t_s01">※※※ 提醒您，您的廣告將在2工作天(周一到周五)審核完成(不含例假日)，並於廣告審核完成後開始播放 ※※※</span>
		<center style="margin-top:10px;">
			<input type="button" id="cancelKW" name="cancelKW" value="取消">
			<input type="button" id="saveKW" name="saveKW" value="儲存群組關鍵字">
			<input type="hidden" id="adGroupSeq" name="adGroupSeq" value="${adGroupSeq!}">
			<input type="hidden" id="backPage" name="backPage" value="${backPage!}">
		</center>
	</form>
</div>
<input type="hidden" id="messageId" value="${message!}">
