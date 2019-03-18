<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>


<span class="pages"><@t.insertAttribute name="page" /></span>

<div style="clear:both;height:10px"></div>
<div style="overflow-x:auto;width:100%">
<table id="listTable2" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter tb01">
<thead>
  <tr>
	<th height="35" style="min-width:120px" rowspan="2">關鍵字</th>
	<th height="35" style="min-width:40px"  rowspan="2">狀態</th>
	<th style="min-width:120px" colspan="3">比對方式</th>
    <th class="pvDataTh" style="min-width:100px" colspan="1" >曝光數 <input class="pvDataButton" type="button" onclick="toggleTd('pvData')" value="展開" /></th>
	<th class="clkDataTh" style="min-width:110px" colspan="1">點選次數 <input class="clkDataButton" type="button" onclick="toggleTd('clkData')" value="展開" /></th>
	<th class="clkRateDataTh" style="min-width:100px" colspan="1">點選率 <input class="clkRateDataButton" type="button" onclick="toggleTd('clkRateData')" value="展開" /></th>
    <!--<th style="width:10%">無效點選次數</th>-->
    <th class="clkPriceAvgDataTh" style="min-width:135px" colspan="1">平均點選費用 <input class="clkPriceAvgDataButton" type="button" onclick="toggleTd('clkPriceAvgData')" value="展開" /></th>
	<th class="clkPriceDataTh" style="min-width:90px" colspan="1">費用 <input class="clkPriceDataButton" type="button" onclick="toggleTd('clkPriceData')" value="展開" /></th>
  </tr>
  <tr>
  	<th style="min-width:40px">廣泛<br/>比對</th>
	<th style="min-width:40px">詞組<br/>比對</th>
	<th style="min-width:40px">精準<br/>比對</th>
	<th class="pvData" style="min-width:60px;display:none;">廣泛<br/>比對</th>
	<th class="pvData" style="min-width:60px;display:none;">詞組<br/>比對</th>
	<th class="pvData" style="min-width:60px;display:none;">精準<br/>比對</th>
	<th style="min-width:60px;">總計</th>
	<th class="clkData" style="min-width:50px;display:none;">廣泛<br/>比對</th>
	<th class="clkData" style="min-width:50px;display:none;">詞組<br/>比對</th>
	<th class="clkData" style="min-width:50px;display:none;">精準<br/>比對</th>
	<th style="min-width:50px;">總計</th>
	<th class="clkRateData" style="min-width:40px;display:none;">廣泛<br/>比對</th>
	<th class="clkRateData" style="min-width:40px;display:none;">詞組<br/>比對</th>
	<th class="clkRateData" style="min-width:40px;display:none;">精準<br/>比對</th>
	<th style="min-width:40px;">總計</th>
	<th class="clkPriceAvgData" style="min-width:60px;display:none;">廣泛<br/>比對</th>
	<th class="clkPriceAvgData" style="min-width:60px;display:none;">詞組<br/>比對</th>
	<th class="clkPriceAvgData" style="min-width:60px;display:none;">精準<br/>比對</th>
	<th style="min-width:60px;">總計</th>
	<th class="clkPriceData" style="min-width:70px;display:none;">廣泛<br/>比對</th>
	<th class="clkPriceData" style="min-width:70px;display:none;">詞組<br/>比對</th>
	<th class="clkPriceData" style="min-width:70px;display:none;">精準<br/>比對</th>
	<th style="min-width:80px;">總計</th>
  </tr>
</thead>
<tbody>
<#if adLayerVO?exists>
	<#list adLayerVO as vo>
	  <tr>  	
		<td height="30" class="td02">${vo.name!}</td>
	    <td class="td03">${vo.statusChName!}</td>
	    <td style="display:none;"></td>
        <td style="display:none;"></td>
        <td style="display:none;"></td>
        <td style="display:none;"></td>
        <td style="display:none;"></td>
        <td style="display:none;"></td>
	    <td class="td03">${vo.widOpen!}</td>
	    <td class="td03">${vo.phrOpen!}</td>
	    <td class="td03">${vo.preOpen!}</td>
	    <td class="td01 pvData" style="display:none;" >${vo.widPv?string('#,###')!}</td>
	    <td class="td01 pvData" style="display:none;" >${vo.phrPv?string('#,###')!}</td>
	    <td class="td01 pvData" style="display:none;" >${vo.prePv?string('#,###')!}</td>
	    <td class="td01">${vo.pv?string('#,###')!}</td>
	    <td class="td01 clkData" style="display:none;" >${vo.widClk?string('#,###')!}</td>
	    <td class="td01 clkData" style="display:none;" >${vo.phrClk?string('#,###')!}</td>
	    <td class="td01 clkData" style="display:none;" >${vo.preClk?string('#,###')!}</td>
	    <td class="td01">${vo.clk?string('#,###')!}</td>
	    <td class="td01 clkRateData" style="display:none;" >${vo.widClkRate?string('0.00')!}%</td>	  
	    <td class="td01 clkRateData" style="display:none;" >${vo.phrClkRate?string('0.00')!}%</td>
	    <td class="td01 clkRateData" style="display:none;" >${vo.preClkRate?string('0.00')!}%</td>
	    <td class="td01">${vo.clkRate?string('0.00')!}%</td>
	    <!--<td class="td01">${vo.invalidClk?string('#,###')!}</td>-->
	    <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.widAvgClkCost?string('0.00')!}</td>  
	   	<td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.phrAvgClkCost?string('0.00')!}</td>
	   	<td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.preAvgClkCost?string('0.00')!}</td>
	   	<td class="td01">NT$ ${vo.avgClkCost?string('0.00')!}</td>
	   	<td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.widClkCost?string('#,###')!}</td>
	   	<td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.phrClkCost?string('#,###')!}</td>
	   	<td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.preClkCost?string('#,###')!}</td>
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
</div>
