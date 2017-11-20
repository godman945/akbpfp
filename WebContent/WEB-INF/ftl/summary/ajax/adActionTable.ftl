<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<span class="pages"><@t.insertAttribute name="page" /></span>

<div style="clear:both"></div>
<table id="listTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
  <tr>
	<th height="35">廣告</th>
    <th style="width:10%">狀態</th>
    <th style="width:10%">播放類型</th>
    <th style="width:10%">廣告樣式</th>
    <th style="width:10%">曝光數</th>
    <th style="width:10%"><a style="float: left; margin-top: 7px;"><img src="./html/img/question.gif" title="互動數欄位:計算不同廣告樣式所產生的主要動作次數"></a>互動數</th>
    <th style="width:10%">互動率</th>
    <th style="width:10%">單次互動費用</th>
    <th style="width:10%">千次曝光費用</th>
    <th style="width:10%">費用</th>
  </tr>
</thead>
<tbody>
<#if adLayerVO?exists>
	<#list adLayerVO as vo>
	  <tr>
		<td height="30" class="td02">
		<a href="adGroupView.html?adActionSeq=${vo.seq!}">${vo.name!}</a>
		</td>
	    <td class="td03">${vo.statusChName!}</td>
	    <td class="td03">${vo.adType!}</td>
	    <td class="td03"><#if vo.adOperatingRule == 'VIDEO'>影音廣告<#else>多媒體廣告</#if></td>
	    <td class="td01">${vo.pv?string('#,###')!}</td>
	    <td class="td01">${vo.clk?string('#,###')!}</td>	   
	    <td class="td01">${vo.clkRate?string('#.##')!}%</td>
	    <td class="td01">NT$ ${vo.avgClkCost?string('#.##')!}</td>
	    <td class="td01">NT$ ${vo.thousandsCost?string('#.##')!}</td>
	    <td class="td01">NT$ ${vo.clkCost?string('#,###')!}</td>
	  </tr> 		
	</#list>
<#else>
	<tr>
		<td colspan="8">
			沒有廣告成效資訊
		</td>
	</tr>
</#if>
</tbody>	
</table>

