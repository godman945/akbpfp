<#assign s=JspTaglibs["/struts-tags"]>

<div class="cont">

<form method="post" id="addMoneyForm" name="remainAddForm" action="accountAddMoney.html">

<h2>
<div class="cal">帳戶名稱：${customer_info_title}</div>
<img src="<@s.url value="/" />html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />帳戶加值 
</h2>

<div class="grtba">
            
<table width="100%" cellspacing="1" cellpadding="0" border="0" class="tb02">
<tbody>
    <tr>
      <th height="35">帳戶餘額</th>
      <td>NT$ ${accountVO.remain?string('#,###.##')!}</td>
    </tr>
    <tr>
		<th height="35">禮金序號</th>
  		<td><input type="text" id="giftSno" name="giftSno" value="${accountVO.giftSno!}" autocomplete="off"  style="width:100px" maxlength="10" /><input type="button" id="btnClrGift" name="btnClrGift" value="清除序號"></td>
	</tr>
	<tr>
		<th height="35">禮金金額</th>
  		<td><span id="showMoney" name="showMoney" style="color:red;"></span></td>
	</tr>
    <tr class="addTr" >
    	<th height="35"><span class="t_s02">* </span>本次加值金額<br /> <span class="t_s01">儲值金額最少為新台幣$500</span></th>
    	<td>
    		NT$ <input type="text" id="addMoney" name="addMoney" value="${accountVO.addMoney!}" autocomplete="off" maxlength="6" >
		</td>
    </tr>
 	<tr class="addTr" >
        <th height="35">營業稅</th>
        <td>NT$ <span id="addTax">${accountVO.addTax!}</span></td>
    </tr>
    <tr class="addTr" >
      <th height="35">總金額(含5%營業稅)<br /><span class="t_s01">(實際付款及發票金額)</span></th>
      <td>NT$ <span id="total"/>${(accountVO.addMoney+accountVO.addTax)!}</span></td>
    </tr>
</tbody>
</table>
<#-- 
<center style="margin:10px">
   <input type="button" id="save" value="儲值付款">
</center>
-->
</div>
	<center style="margin:10px">
		<input type="checkbox" id="accept" name="accept" />
  		<strong>我同意接受<a href="http://show.pchome.com.tw/faq.html?fid=4&qid=2" target="_blank">約定條款</a>和<a href="http://show.pchome.com.tw/faq.html?fid=4&qid=5" target="_blank">刊登規範</a></strong>
  		<p>
		<input type="button" id="save" value="付款儲值" onclick='goog_report_conversion()' disabled="true" />
	</center>
</div>
<input type="hidden" id="giftMoney" name="giftMoney" >
</form>

</div> 

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
