<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<div>
    <span style="padding:10px;float:left">                   
	    <input type="button" name="stop" onClick="modifyAdStatus('9')" value="暫 停" /> &nbsp; 
		<input type="button" name="start" onClick="modifyAdStatus('4')" value="開 啟" /> &nbsp;
		<input type="button" name="close" onClick="modifyAdStatus('10')" value="關 閉" /> &nbsp;
	</span>

	<span class="pages"><@t.insertAttribute name="page" /></span>			
</div>

<div style="clear:both;height:50%;"></div>

<form method="post" id="tableForm" name="tableForm" >
<div style="overflow-x:scroll;">
<table id="tableView" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
<thead>
	<tr>
		<th style="min-width:40px;" rowspan="2"><a href="#" onclick="checkAll()">全選</a></th>
		<th height="35" style="min-width:65px" rowspan="2">關鍵字</th>
		<th style="min-width:50px" rowspan="2">狀態</th>
		<th colspan="6">比對方式</th>
		<th class="pvDataTh" style="min-width:100px" colspan="1" >曝光數 <input class="pvDataButton" type="button" onclick="toggleTd('pvData')" value="展開" /></th>
		<th class="clkDataTh" style="min-width:110px" colspan="1">點選次數 <input class="clkDataButton" type="button" onclick="toggleTd('clkData')" value="展開" /></th>
		<th class="clkRateDataTh" style="min-width:100px" colspan="1">點選率 <input class="clkRateDataButton" type="button" onclick="toggleTd('clkRateData')" value="展開" /></th>
		<th class="clkPriceAvgDataTh" style="min-width:135px" colspan="1">平均點選費用 <input class="clkPriceAvgDataButton" type="button" onclick="toggleTd('clkPriceAvgData')" value="展開" /></th>
		<th class="clkPriceDataTh" style="min-width:90px" colspan="1">費用 <input class="clkPriceDataButton" type="button" onclick="toggleTd('clkPriceData')" value="展開" /></th>
		<th colspan="3">平均廣告排名</th>
	</tr>
	<tr>
		<th style="min-width:40px">廣泛比對</th>
		<th class="input" style="min-width:110px;">最高單次<br/>點擊出價</th>
		<th style="min-width:40px">詞組比對</th>
		<th style="min-width:110px;">最高單次<br/>點擊出價</th>
		<th style="min-width:40px">精準比對</th>
		<th style="min-width:110px;">最高單次<br/>點擊出價</th>
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
		<th style="min-width:40px;">廣泛<br/>比對</th>
		<th style="min-width:40px;">詞組<br/>比對</th>
		<th style="min-width:40px;">精準<br/>比對</th>
	</tr>
</thead>
<tbody>
	<#if adKeywordViewVO?exists>
	    <#list adKeywordViewVO as vo>
			<tr>
				<td>
				<#--
				<input type="checkbox" id="chkAdKeyword_${vo_index!}" name="chkAdKeyword" value="${vo.adKeywordSeq!}"/>
				 -->
				<#if vo.adKeywordStatus != 0 >
			        <input type="checkbox" id="chkY_${vo_index!}" name="chkY" value="${vo.adKeywordSeq!}"/>
				<#else>
			        <input type="checkbox" id="chkN_${vo_index!}" name="chkN" disabled/>
				</#if>
				</td>
		        <td height="35" class="td02">
	        	${vo.adKeyword!}		        	
		        </td class="td02">		        
		        <td>${vo.adKeywordStatusDesc!}</td>
		        <td style="display:none;"></td>
		        <td style="display:none;"></td>
		        <td style="display:none;"></td>
		        <td style="display:none;"></td>
		        <td style="display:none;"></td>
		        <td style="display:none;"></td>
		        <td style="display:none;"></td>
		        <td>
		        	<#if vo.adKeywordOpen = 1 >
			        	<input type="checkbox" id="userOpen_widely_${vo.adKeywordSeq!}" onChange="updateKeywordOpen('${vo.adKeywordSeq!}','${vo.suggestPrice!}','widely')" checked />
					<#else>
			        	<input type="checkbox" id="userOpen_widely_${vo.adKeywordSeq!}" onChange="updateKeywordOpen('${vo.adKeywordSeq!}','${vo.suggestPrice!}','widely')" />
					</#if>
		        </td>
		        <td>
		        	<input type="text" id="userPrice_widely_${vo.adKeywordSeq!}" value="${vo.adKeywordSearchPrice!}"  style="width:40px;text-align:right"  maxlength="6" class="required digits" min="3" max="${vo.adActionMax!}" >
                    <input type="button" id="bt_widely_${vo.adKeywordSeq!}" onClick="updateKeywordSuggest('${vo.adKeywordSeq!}','widely')" value="確定">
                    <br>
					建議出價：NT$ ${vo.suggestPrice!}		        
		        </td>
		        <td>
		        	<#if vo.adKeywordPhraseOpen = 1 >
			        	<input type="checkbox" id="userOpen_phrase_${vo.adKeywordSeq!}" onChange="updateKeywordOpen('${vo.adKeywordSeq!}','${vo.suggestPhrasePrice!}','phrase')" checked />
					<#else>
			        	<input type="checkbox" id="userOpen_phrase_${vo.adKeywordSeq!}" onChange="updateKeywordOpen('${vo.adKeywordSeq!}','${vo.suggestPhrasePrice!}','phrase')" />
					</#if>
		        </td>
		        <td>
		        	<input type="text" id="userPrice_phrase_${vo.adKeywordSeq!}" value="${vo.adKeywordSearchPhrasePrice!}"  style="width:40px;text-align:right"  maxlength="6" class="required digits" min="3" max="${vo.adActionMax!}" >
                    <input type="button" id="bt_phrase_${vo.adKeywordSeq!}" onClick="updateKeywordSuggest('${vo.adKeywordSeq!}','phrase')" value="確定">
                    <br>
					建議出價：NT$ ${vo.suggestPhrasePrice!}		        
		        </td>
		        <td>
		        	<#if vo.adKeywordPrecisionOpen = 1 >
			        	<input type="checkbox" id="userOpen_precision_${vo.adKeywordSeq!}" onChange="updateKeywordOpen('${vo.adKeywordSeq!}','${vo.suggestPrecisionPrice!}','precision')" checked />
					<#else>
			        	<input type="checkbox" id="userOpen_precision_${vo.adKeywordSeq!}" onChange="updateKeywordOpen('${vo.adKeywordSeq!}','${vo.suggestPrecisionPrice!}','precision')" />
					</#if>
		        </td>
		        <td>
		        	<input type="text" id="userPrice_precision_${vo.adKeywordSeq!}" value="${vo.adKeywordSearchPrecisionPrice!}"  style="width:40px;text-align:right"  maxlength="6" class="required digits" min="3" max="${vo.adActionMax!}" >
                    <input type="button" id="bt_precision_${vo.adKeywordSeq!}" onClick="updateKeywordSuggest('${vo.adKeywordSeq!}','precision')" value="確定">
                    <br>
					建議出價：NT$ ${vo.suggestPrecisionPrice!}		        
		        </td>
		        <td class="td01 pvData" style="display:none;" >${vo.adKeywordPv?string('#,###')!}</td>
		        <td class="td01 pvData" style="display:none;" >${vo.adKeywordPhrasePv?string('#,###')!}</td>
		        <td class="td01 pvData" style="display:none;" >${vo.adKeywordPrecisionPv?string('#,###')!}</td>
		        <td class="td01">${vo.adKeywordPvSum?string('#,###')!}</td>
		        <td class="td01 clkData" style="display:none;" >${vo.adKeywordClk?string('#,###')!}</td>
		        <td class="td01 clkData" style="display:none;" >${vo.adKeywordPhraseClk?string('#,###')!}</td>
		        <td class="td01 clkData" style="display:none;" >${vo.adKeywordPrecisionClk?string('#,###')!}</td>
		        <td class="td01">${vo.adKeywordClkSum?string('#,###')!}</td>
		        <td class="td01 clkRateData" style="display:none;" >${vo.adKeywordClkRate?string('0.00')!}%</td>
		        <td class="td01 clkRateData" style="display:none;" >${vo.adKeywordPhraseClkRate?string('0.00')!}%</td>
		        <td class="td01 clkRateData" style="display:none;" >${vo.adKeywordPrecisionClkRate?string('0.00')!}%</td>
		        <td class="td01">${vo.adKeywordClkRateSum?string('0.00')!}%</td>
		        <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.adKeywordClkPriceAvg?string('0.00')!}</td>
		        <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.adKeywordPhraseClkPriceAvg?string('0.00')!}</td>
		        <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.adKeywordPrecisionClkPriceAvg?string('0.00')!}</td>
		        <td class="td01">NT$ ${vo.adKeywordClkPriceSumAvg?string('0.00')!}</td>
		        <td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.adKeywordClkPrice?string('#,###')!}</td>
		        <td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.adKeywordPhraseClkPrice?string('#,###')!}</td>
		        <td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.adKeywordPrecisionClkPrice?string('#,###')!}</td>
		        <td class="td01">NT$ ${vo.adKeywordClkPriceSum?string('#,###')!}</td>
		        <#if vo.adKeywordType == "1">
					<td class="td01">${vo.adKeywordRankAvg?string('#.##')!}</td>
				<#else>
					<td class="td01">-</td>
				</#if>
				<#if vo.adKeywordPhraseType == "1">
					<td class="td01">${vo.adKeywordPhraseRankAvg?string('#.##')!}</td>
				<#else>
					<td class="td01">-</td>
				</#if>
				<#if vo.adKeywordPrecisionType == "1">
					<td class="td01">${vo.adKeywordPrecisionRankAvg?string('#.##')!}</td>
				<#else>
					<td class="td01">-</td>
				</#if>
			</tr>
	    </#list>
	<#else>
	<tr>
		<td colspan="13">
			沒有資料。請按「新增關鍵字」按鈕，新增關鍵字。
		</td>
	</tr>
	</#if>
</tbody>
	<tr class="tbg">
		<td colspan="9">總計：${totalSize!}筆</td>
		<td class="td01 pvData" style="display:none;" >${totalPv?string('#,###')!}</td>
		<td class="td01 pvData" style="display:none;" >${totalPhrasePv?string('#,###')!}</td>
		<td class="td01 pvData" style="display:none;" >${totalPrecisionPv?string('#,###')!}</td>
		<td class="td01">${totalPvSum?string('#,###')!}</td>
		<td class="td01 clkData" style="display:none;" >${totalClk?string('#,###')!}</td>
		<td class="td01 clkData" style="display:none;" >${totalPhraseClk?string('#,###')!}</td>
		<td class="td01 clkData" style="display:none;" >${totalPrecisionClk?string('#,###')!}</td>
		<td class="td01">${totalClkSum?string('#,###')!}</td>			
		<td class="td01 clkRateData" style="display:none;" >${totalClkRate?string('0.00')!}%</td>
		<td class="td01 clkRateData" style="display:none;" >${totalPhraseClkRate?string('0.00')!}%</td>
		<td class="td01 clkRateData" style="display:none;" >${totalPrecisionClkRate?string('0.00')!}%</td>
		<td class="td01">${totalClkRateSum?string('0.00')!}%</td>
		<!--<td class="td01">${totalInvalidClk?string('#.##')!}</td>-->
		<td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${totalAvgCost?string('0.00')!}</td>
		<td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${totalPhraseAvgCost?string('0.00')!}</td>
		<td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${totalPrecisionAvgCost?string('0.00')!}</td>
		<td class="td01">NT$ ${totalAvgCostSum?string('0.00')!}</td>
		<td class="td01 clkPriceData" style="display:none;" >NT$ ${totalCost?string('#,###')!}</td>
		<td class="td01 clkPriceData" style="display:none;" >NT$ ${totalPhraseCost?string('#,###')!}</td>
		<td class="td01 clkPriceData" style="display:none;" >NT$ ${totalPrecisionCost?string('#,###')!}</td>
		<td class="td01">NT$ ${totalCostSum?string('#,###')!}</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
</div>
<input type="hidden" id="adKeywordSeq" name="adKeywordSeq" />
<input type="hidden" id="status" name="status" />
</form>

<input type="hidden" id="changTypeSeq" name="changTypeSeq" />
<input type="hidden" id="changTypePrice" name="changTypePrice" />
<input type="hidden" id="changType" name="changType" />

<div style="padding:10px;float:right;">
	<a href="#" onClick="adExcludeKeyword('${adGroupSeq!}')" >排除關鍵字</a>
</div>

<span style="padding:10px;display:block">
	<input type="button" name="stop" onClick="modifyAdStatus('9')" value="暫 停" /> &nbsp; 
	<input type="button" name="start" onClick="modifyAdStatus('4')" value="開 啟" /> &nbsp;
	<input type="button" name="close" onClick="modifyAdStatus('10')" value="關 閉" /> &nbsp;          
</span>
