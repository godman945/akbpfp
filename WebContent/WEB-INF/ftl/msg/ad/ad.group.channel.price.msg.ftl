<#assign s=JspTaglibs["/struts-tags"]>

<form method="post" id="priceForm" name="priceForm" >

<div id="channelPrice" class="noticepop" style="width:520px">
	<h4><strong>設定分類出價</strong></h4>
	<div class="popcont">
		<em>»</em>聯播網廣告出價設定<em>›</em><b>分類：${adGroup.adGroupName!}</b><br/>	
		<p style="border:0;border-top:1px dashed #ccc;margin-top:5px;">
 			出價金額會決定廣告播出率。系統會依每次廣告的競價結果分析出最佳的播出率，實際支付的廣告點擊費用，會小於或等於您的出價金額。
		</p><br/>
		<b>要獲得廣告更高的播出率，系統建議出價</b><br/>
			NT$&nbsp;<input type="text" id="userPrice" name="userPrice" style="width:100px;" maxlength="6" value="${sysprice!}">
                    	系統預估播出率：<label name="adAsideRate">${adAsideRate?string('#.##')!}</label>%</b><br/>
	</div>
	<center>
		<input class="popbtn" type="button" name="yes" value="儲存" />　
		<input class="popbtn" type="button" name="no" value="取消" />
	</center>
	<input type="hidden" class="popbtn" name="adActionMax" value="${adGroup.pfpAdAction.adActionMax!}"/>
</div>

</form>