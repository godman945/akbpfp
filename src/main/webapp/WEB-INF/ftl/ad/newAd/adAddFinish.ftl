<#assign s=JspTaglibs["/struts-tags"]>
<#assign today = .now>

<script type="text/javascript">
function viewAdAction() {
	location.href = "adActionView.html";
}

function newAd() {
	location.href = 'adAdAdd.html?adGroupSeq=${adGroupSeq!}&adOperatingRule=${adOperatingRule!}';
}

</script>

<div class="cont">
 	<!--進度條start-->
        <div class="speedbar grtba">
        <ul>
        <li class="sb01"><i>1</i><b><span class="sbdate">${adCreateDate!}</span>新增廣告</b></li>
        <li class="sb02"><i>2</i><b>審核需3個工作天</b><br/><u>(廣告送審中)</u></li>
        <li class="sb03"><i>3</i><b>預計<span class="sbdate">${adWorkDatee!}</span>廣告審核完成</b><br /><u>審核通過，廣告立即上線!<br><div style="color: red;">(如遇假日廣告審核將順延)</div></u></li>
        </ul>
        <div class="spbarpic"><img src="html/img/speedbar.png" width="888" height="25" /></div>
      </div>
   <!--進度條end-->


	<h1 class="adtitle">廣告：${adActionName!} > 分類：${adGroupName!}</h1>
	<h2>
		<div class="cal">帳戶名稱：${customer_info_title!}</div>
		<img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif"> 新增廣告
	</h2>
	<div class="steps">輸入廣告基本設定 &gt; 建立分類及出價  &gt; 製作廣告及關鍵字設定  &gt; <b>廣告完成</b> </div>
	<div class="grtba">
		<h4>廣告</h4>
		<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
			<tbody>
				<tr>
					<th width="20%" height="35" style="width:20%">廣告名稱</th>
					<td colspan="3">${adActionName!}</td>
				</tr>
				<tr>
					<th style="width:20%" height="35">廣告撥放類型</th>
					<td width="34%">${adTypeName!}</td>
					<th width="12%" style="width:20%">播放裝置</th>
					<td width="34%">${adDeviceName!}</td>
				</tr>
				<tr>
					<th style="width:20%" height="35">廣告走期</th>
					<td width="34%">${adActionStartDate!} ~ ${selAdActionEndDate!}</td>
					<th width="12%" style="width:20%">每日預算</th>
					<td width="34%">${adActionMax!}元</td>
				</tr>
			</tbody>
		</table>
		<h4>分類</h4>
		<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb01">
			<#if adType == "0" >
				<tr>
					<th width="20%" height="48">分類名稱</th>
					<th width="20%" height="48">搜尋廣告出價</th>
					<th width="20%" height="48">關鍵字組數</th>
					<th width="20%" height="48">內容廣告出價</th>
					<th width="20%" height="48">廣告明細</th>
				</tr>
				<tr>
					<td width="20%" height="48">${adGroupName!}</td>
					<td width="20%" height="48">${adGroupSearchPriceType!}<br><a href="adGroupView.html?adActionSeq=${adActionSeq!}">修改出價設定</a></td>
					<td width="20%" height="48">${pfpAdKeywordList?size}組<br><a href="adKeywordView.html?adGroupSeq=${adGroupSeq!}">修改關鍵字出價</a></td>
					<td width="20%" height="48">NT$${adGroupChannelPrice!}<br><a href="adGroupView.html?adActionSeq=${adActionSeq!}">修改出價</a></td>
					<td width="20%" height="48">${pfpAdList?size!}則<br><a href="adAdView.html?adGroupSeq=${adGroupSeq!}">檢視廣告明細</a></td>
				</tr>
			<#elseif adType == "1">
				<tr>
					<th width="25%" height="48">分類名稱</th>
					<th width="25%" height="48">搜尋廣告出價</th>
					<th width="25%" height="48">關鍵字組數</th>
					<th width="25%" height="48">廣告明細</th>
				</tr>
				<tr>
					<td width="25%" height="48">${adGroupName!}</td>
					<td width="25%" height="48">${adGroupSearchPriceType!}<br><a href="adGroupView.html?adActionSeq=${adActionSeq!}">修改出價設定</a></td>
					<td width="25%" height="48">${pfpAdKeywordList?size}組<br><a href="adKeywordView.html?adGroupSeq=${adGroupSeq!}">修改關鍵字出價</a></td>
					<td width="25%" height="48">${pfpAdList?size!}則<br><a href="adAdView.html?adGroupSeq=${adGroupSeq!}">檢視廣告明細</a></td>
				</tr>
			<#elseif adType == "2">
				<tr>
					<th width="34%" height="48">分類名稱</th>
					<th width="33%" height="48">內容廣告出價</th>
					<th width="33%" height="48">廣告明細</th>
				</tr>
				<tr>
					<td width="34%" height="48">${adGroupName!}</td>
					<td width="33%" height="48">NT$${adGroupChannelPrice!}<br><a href="adGroupView.html?adActionSeq=${adActionSeq!}">修改出價</a></td>
					<td width="33%" height="48">${pfpAdList?size!}則<br><a href="adAdView.html?adGroupSeq=${adGroupSeq!}">檢視廣告明細</a></td>
				</tr>
			</#if>
		</table>
	</div>
	<center style="margin-top:10px;">
		<input type="button" id="btnViewAdAction" name="btnViewAdAction" value="檢視廣告" onclick="javascript:viewAdAction();">
		<input type="button" id="saveNew" value="儲存後再新增廣告" onclick="javascript:newAd();">
	</center>
</div>

<input type="hidden" id="adActionSeq" value="${adActionSeq!}">
<input type="hidden" id="adGroupSeq" value="${adGroupSeq!}">
<input type="hidden" id="messageId" value="${message!}">
<input type="hidden" id="messageId" value="${message!}">
<input type="hidden" id="messageId" value="${adOperatingRule!}">

