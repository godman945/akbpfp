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

<div style="clear:both;height:50%"></div>

<form method="post" id="tableForm" name="tableForm" >
<table id="tableView" class="tablesorter" width="100%" border="0" cellpadding="0" cellspacing="1">
<thead>
	<tr>
		<th width="50"><a href="#" onclick="checkAll()">全選</a></th>
		<th height="35" style="width:6%">關鍵字</th>
		<th style="width:6%">狀態</th>
		<th style="min-width:130px;">搜尋廣告出價</th>
		<th style="width:6%">曝光數</th>
		<th style="width:6%">點選率(%)</th>
		<th style="width:6%">點選次數</th>	
		<!--<th style="width:6%">無效點選次數</th>-->	
		<th style="width:8%">平均點選費用</th>
		<th style="min-width:65px;width:9%">費用</th>
		<th style="width:8%">平均廣告排名</th>		
		<th>廣告</th>
		<th style="width:8%">分類</th>
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
		        <td>
		        	<input type="text" id="userPrice_${vo.adKeywordSeq!}" value="${vo.adKeywordSearchPrice!}"  style="width:60px;text-align:right"  maxlength="6" class="required digits" min="3" max="${vo.adActionMax!}" >
                    <input type="button" id="bt_${vo.adKeywordSeq!}" onClick="updateKeywordSuggest('${vo.adKeywordSeq!}')" value="確定">
                    <br>
					建議出價：NT$ ${vo.suggestPrice!}		        
		        </td>
				<td class="td01">${vo.adKeywordPv?string('#,###')!}</td>
				<td class="td01">${vo.adKeywordClkRate?string('#.##')!}</td>
				<td class="td01">${vo.adKeywordClk?string('#,###')!}</td>	
				<!--<td class="td01">${vo.invalidClk?string('#,###')!}</td>-->							
				<td class="td01">${vo.adKeywordClkPriceAvg?string('#.##')!}</td>
				<td class="td01">${vo.adKeywordClkPrice?string('#,###')!}</td>
				<#if vo.adKeywordType == "1">
					<td class="td01">${vo.adKeywordRankAvg?string('#.##')!}</td>
				<#else>
					<td class="td01">-</td>
				</#if>			
				<td>${vo.adActionName!}</td>
				<td>${vo.adGroupName!}</td>
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
		<td colspan="4">總計：${totalSize!}筆</td>
		<td class="td01">${totalPv?string('#,###')!}</td>
		<td class="td01">${totalClkRate?string('#.##')!}</td>
		<td class="td01">${totalClk?string('#,###')!}</td>			
		<!--<td class="td01">${totalInvalidClk?string('#.##')!}</td>-->
		<td class="td01">${totalAvgCost?string('#.##')!}</td>
		<td class="td01">${totalCost?string('#,###')!}</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
<input type="hidden" id="adKeywordSeq" name="adKeywordSeq" />
<input type="hidden" id="status" name="status" />
</form>

<div style="padding:10px;float:right;">
	<a href="#" onClick="adExcludeKeyword('${adGroupSeq!}')" >排除關鍵字</a>
</div>

<span style="padding:10px;display:block">
	<input type="button" name="stop" onClick="modifyAdStatus('9')" value="暫 停" /> &nbsp; 
	<input type="button" name="start" onClick="modifyAdStatus('4')" value="開 啟" /> &nbsp;
	<input type="button" name="close" onClick="modifyAdStatus('10')" value="關 閉" /> &nbsp;          
</span>
