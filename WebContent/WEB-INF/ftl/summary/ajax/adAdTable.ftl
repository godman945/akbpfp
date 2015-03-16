<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<span class="pages"><@t.insertAttribute name="page" /></span>

<div style="clear:both;height:10px"></div>
<table id="listTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
  <tr>
	<th height="35">廣告明細</th>
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
		<iframe height="120" width="350" src="adModel.html?adNo=${vo.seq!}&tproNo=${vo.templateNo!}" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe>
		</iframe>
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

