<#assign s=JspTaglibs["/struts-tags"]>


<form method="post" id="priceForm" name="priceForm" >
<div id="searchPrice" class="noticepop" style="width:520px">
  <h4><strong>設定分類出價</strong></h4>
  <div class="popcont">
  <em>»</em>搜尋廣告出價設定<em>›</em><b>分類：${adGroup.adGroupName!}</b><br />
  <p style="border:0;border-top:1px dashed #ccc;margin-top:5px;"><em>❶</em>設定最高單次點擊出價，來決定廣告的排名、以及廣告被點擊時您所支付的最高金額。
    <br />
    <em>❷</em>在檢視關鍵字列表頁，可個別修改關鍵字的出價金額。
  </p>

 目前選擇：<em style="font-size:15px">${searchPriceTypeName!}</em><br />
<input type="radio" id="sysPrice" name="searchPriceType" value="1"> 
<b>使用系統建議出價，在我的預算內設定最高的排名出價</b>
<div class="exp">你在此分類中所建立的關鍵字，都以當時每組關鍵字最高的排名價格設為出價金額(出價金額會控制在您每日的廣告預算內)
  <br />
  範例：你設定的關鍵字「電腦」，廣告排名較高的出價為$5，系統即為您設定出價為$5 </div>
<input type="radio" id="definePrice" name="searchPriceType" value="2"> 
<b>自行設定分類出價金額NT$&nbsp;<input type="text" style="width:50px" id="searchPrice" name="searchPrice" value="${adGroup.adGroupSearchPrice!}" maxlength="6" ></b>
<div class="exp">此出價會套用在您此分類的每組關鍵字中
</div>
</div>
	<center>
		<input class="popbtn" type="button" name="yes" value="儲存" />
		<input class="popbtn" type="button" name="no" value="取消" />
	</center>
	<input type="hidden" class="popbtn" name="adActionMax" value="${adGroup.pfpAdAction.adActionMax!}"/>
</div>
</form>
