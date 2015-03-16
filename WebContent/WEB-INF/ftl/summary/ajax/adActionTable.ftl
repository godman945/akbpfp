<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<span class="pages"><@t.insertAttribute name="page" /></span>

<div style="clear:both"></div>
<table id="listTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
  <tr>
	<th height="35">廣告</th>
    <th style="width:10%">狀態</th>
    <th style="width:10%">曝光數</th>
    <th style="width:10%">點選率(%)</th>
    <th style="width:10%">點選次數</th>
    <!--<th style="width:10%">無效點選次數</th>-->
    <th style="width:10%">平均點選費用</th>
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
	    <td class="td02">${vo.statusChName!}</td>
	    <td class="td01">${vo.pv?string('#,###')!}</td>
	    <td class="td01">${vo.clkRate?string('#.##')!}%</td>
	    <td class="td01">${vo.clk?string('#,###')!}</td>	   
	    <!--<td class="td01">${vo.invalidClk?string('#,###')!}</td>-->	 
	    <td class="td01">${vo.avgClkCost?string('#.##')!}</td>
	    <td class="td01">${vo.clkCost?string('#,###')!}</td>
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

