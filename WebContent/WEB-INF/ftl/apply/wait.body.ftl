<#assign s=JspTaglibs["/struts-tags"]>

<form method="post" id="registerForm" name="registerForm" action="register.html">

<div class="cont" style="width:800px;">
<div style="font-size:18px;color:#000;line-height:50px;font-family:Verdana">

<#if enumBillingStatus[1] == accountVO.orderStatus || 
	enumBillingStatus[2] == accountVO.orderStatus ||
	enumBillingStatus[3] == accountVO.orderStatus ||
	enumBillingStatus[14] == accountVO.orderStatus ||
	enumBillingStatus[15] == accountVO.orderStatus ||
	enumBillingStatus[16] == accountVO.orderStatus ||
	enumBillingStatus[17] == accountVO.orderStatus ||
	enumBillingStatus[18] == accountVO.orderStatus>

	
您尚未完成付款程序！如您已使用信用卡付款或ATM匯款，將於款項確認後為您開通廣告刊登帳戶。
查詢付款訊息請按「付費查詢」按鈕。如要選擇其他付款方式，請按最下方的「付款儲值」按鈕，重新付款。

<#else>

付款失敗，您的廣告刊登帳戶尚未開通！請按最下方的「付款儲值」按鈕，重新付款。

</#if>

</div>
<h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">建立帳戶資料</h2>
<div class="grtba"><span class="t_s01">* </span>為必填欄位

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
		<th height="35"><span class="t_s02">* </span>帳戶類別<br></th>
  		<td>
  			<#if accountVO.category=="1">
			個人戶
	    	<#else>
	    	公司戶
	    	</#if>
		</td>
	</tr>
	<#if accountVO.category=="2">
	<tr>
		<th height="35"><span class="t_s02">* </span>公司名稱<br></th>
  		<td>${accountVO.companyTitle!}</td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>統一編號<br></th>
  		<td>${accountVO.registration!}</td>
	</tr>
	</#if>
	<tr>
		<th height="35"><span class="t_s02">* </span>產業類別</th>
		<td>${accountVO.industry!}</td>
	</tr>
	<tr>
  		<th height="35"><span class="t_s02">* </span>廣告連結位址</th>
  		<td>http://${accountVO.urlAddress!}</td>
	</tr>
</tbody>
</table>
            
</div>

<h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">建立總管理者資料</h2>
<div class="grtba"><span class="t_s01">* </span>為必填欄位

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
		<th height="35"><span class="t_s02">* </span>姓名<br></th>
  		<td>${accountVO.memberVO.memberName!}</td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>性別</th>
		<td>
		<#if accountVO.memberVO.memberSex=="M">
			男
		<#else>
			女	
		</#if>    		    
		</td>
	</tr>
	<tr>
	  <th height="35"><span class="t_s02">* </span>生日</th>
	  <td>${accountVO.memberVO.memberBirthday!}</td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>連絡電話</th>
		<td>${accountVO.memberVO.memberTelephone!}</td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>手機號碼</th>
		<td>${accountVO.memberVO.memberMobile!}</td>
	</tr>
	<tr>
		<th height="35"><span class="t_s02">* </span>聯絡地址</th>
		<td><div id="city"></div></td>
	</tr>
</tbody>
</table>   
        
</div>

<h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">禮金贈送</h2>
<div class="grtba">

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
		<th height="35">禮金序號</th>
  		<td><input type="text" id="giftSno" name="giftSno" value="${accountVO.giftSno!}" autocomplete="off"  style="width:100px" maxlength="10" <#if accountVO.giftSno??>readonly</#if> /><input type="button" id="btnClrGift" name="btnClrGift" value="清除序號"></td>
	</tr>
	<tr>
		<th height="35">禮金金額</th>
  		<td><span id="showMoney" name="showMoney" style="color:red;font-size: 180%"><#if (accountVO.giftMoney > 0) >${accountVO.giftMoney!}</#if></span></td>
	</tr>
</tbody>
</table>   
        
</div>
<div id="addMoneyDiv" >
<h2><img vspace="12" hspace="2" align="absmiddle" src="<@s.url value="/" />html/img/iconcr.gif">廣告帳戶儲值</h2><div class="grtba">
<span class="t_s01">* </span>為必填欄位

<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
	<tr>
        <th height="35"><span class="t_s02">* </span>首次儲值金額<br><span id="first" name="first" class="t_s01"> (首次儲值金額最少為新台幣$500)</span></th>
        <td>NT$
          	<input type="text" id="addMoney" name="addMoney" value="${accountVO.addMoney!}" autocomplete="off" maxlength="6" style="width:50px" <#if accountVO.giftSno??>readonly</#if> />
        </td>
    </tr>
    <tr>
        <th height="35">營業稅</th>
        <td>NT$ <span id="addTax">${accountVO.addTax!}</span></td>
    </tr>
    <tr>
      <th height="35">總金額(含5%營業稅)<br /><span class="t_s01">(實際付款及發票金額)</span></th>
      <td>NT$ <span id="total"/>${(accountVO.addMoney+accountVO.addTax)!}</span></td>
    </tr>
</tbody>
</table>
</div>

<center style="margin:10px">提醒您請先完成儲值，帳戶開通後才能繼續使用關鍵字廣告服務，ATM轉帳後配合金融機構確認需1~24小時的時間。</center>  
</div>
	<center style="margin:10px">
		<input type="checkbox" id="accept" name="accept" />
  		<strong>我同意接受<a href="http://show.pchome.com.tw/faq.html?fid=4&qid=2" target="_blank">約定條款</a>和<a href="http://show.pchome.com.tw/faq.html?fid=4&qid=5" target="_blank">刊登規範</a></strong>
  		<p>
		<input type="button" id="save" value="付款儲值" onclick='goog_report_conversion()' disabled="true" />
	</center>
</div>

<input type="hidden" id="memberZip" value="${accountVO.memberVO.memberZip!}" />
<input type="hidden" id="memberAddress" value="${accountVO.memberVO.memberAddress!}" />
<input type="hidden" id="giftMoney" name="giftMoney" value="${accountVO.giftMoney!}" >

</form>
<!-- Google Code for &#38364;&#37749;&#23383;&#24291;&#21578; Conversion Page
In your html page, add the snippet and call
goog_report_conversion when someone clicks on the
chosen link or button. -->
<script type="text/javascript">
  /* <![CDATA[ */
  goog_snippet_vars = function() {
    var w = window;
    w.google_conversion_id = 1000234696;
    w.google_conversion_label = "uatICJiXggkQyL353AM";
    w.google_remarketing_only = false;
  }
  // DO NOT CHANGE THE CODE BELOW.
  goog_report_conversion = function(url) {
    goog_snippet_vars();
    window.google_conversion_format = "3";
    window.google_is_call = true;
    var opt = new Object();
    opt.onload_callback = function() {
    if (typeof(url) != 'undefined') {
      window.location = url;
    }
  }
  var conv_handler = window['google_trackConversion'];
  if (typeof(conv_handler) == 'function') {
    conv_handler(opt);
  }
}
/* ]]> */
</script>
<script type="text/javascript"
  src="//www.googleadservices.com/pagead/conversion_async.js">
</script>
