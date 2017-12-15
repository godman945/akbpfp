<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<div class="cont">
<!-- 公告 start -->
<div class="anuc">
<h2>
<img src="<@s.url value="/html/img/"/>iconcr.gif" hspace="2" vspace="12" align="absmiddle" />公告訊息

<span style="float:right">
	<table border="0" cellspacing="3" cellpadding="0" class="srchtb">
	    <tr>
	      <td>
	        <select id="boardType">
	        <#list boardType as list>
	        	 <option value="${list.type}" >${list.chName}</option>
	        </#list>
	        </select>
	      </td>
	    </tr>
  	</table>
</span>
</h2>
<div id="latestBoardList" style="height:120px;height:110px\9;overflow:auto"></div>

</div>

<!-- 公告 end -->

<!-- 帳戶概要 start -->
<div class="sum">
<h2><img src="html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />帳戶概要<span>為帳戶目前使用狀況，帳戶餘額結算時間為前一天為止</span></h2>
<div class="ancli">帳戶名稱：${customerInfo.customerInfoTitle!}</div>
<div class="ancli">
	<span style="float:left">帳戶餘額：NT$ ${customerInfo.remain?string('#,###')!}元</span>
	<div class="gbtn"><#if pay_type == payType[0].payType><a href="accountRemain.html">加值去</a></#if></div>
</div>
</div>

<!-- 帳戶概要 end -->
<div style="clear:both;height:10px"></div>
<h2>

<div class="cal">
<#if (adActions?size<=0) >
目前尚無資料，請<a href="<@s.url value="/adActionAdd.html" />">新增廣告活動</a>
</#if>
</div>

<img src="html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />本月廣告成效<span>為本月刊登廣告成效總和 │ 統計時間:${effectSDate!} ~ ${effectEDate!} </span>
</h2>

<table width="100%" border="0" cellpadding="0" cellspacing="5" bgcolor="#f4f2ee">
  <tr>
    <td><table width="100%" height="230" border="0" cellpadding="0" cellspacing="1" class="tb01">
      <tr>
        <th height="30">總曝光數</th>
        <th height="30"><a style="float: left; margin-top: 7px;"><img src="./html/img/question.gif" title="互動數欄位:計算不同廣告樣式所產生的主要動作次數"></a>總互動數</th>
        <th height="30">總費用</th>
      </tr>
      <tr>
        <td height="200">${totalAdPvclkCost[0]?string('#,###')!}</td>
        <td>${totalAdPvclkCost[1]?string('#,###')!}</td>        
        <td>NT$ ${totalAdPvclkCost[2]?string('#,###')!}</td>
      </tr>
    </table></td>
    <td width="618"><@t.insertAttribute name="chartTable" /></td>
  </tr>
</table>

<div style="clear:both;height:10px"></div>

<h2>

<@t.insertAttribute name="dateRangeSelect" />


<script type="text/javascript" src="<@s.url value="/" />html/js/jquery/jquery.cookie.js"></script>
<img src="html/img/iconcr.gif" hspace="2" vspace="12" align="absmiddle" />廣告成效摘要</h2>
<div id="adSearchTable" class="grtba">

<div style="float:left;width:150px;line-height:44px;padding-top:8px;">
    <select id="adLayerType" name="adLayerType">
      <option value="adAction">廣告</option>
      <option value="adGroup">分類</option>
      <option value="adKeyword">關鍵字</option>
      <option value="adAd">廣告明細</option>
    </select>
</div>

<div class="cala01" style="width:190px;line-height:44px;">
    <input id="IT_dateRange" readonly="true"/> 
    <img src="<@s.url value="/" />html/img/icon_cal.gif" border="0" align="absmiddle" id="dateRangeSelect"/>
</div>

<div id="tableList"></div>
</div>

