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
		<th height="35">廣告</th>
		<th>播放類型</th>
		<th>廣告樣式</th>
		<th>裝置</th>
		<th>狀態</th>
		<th>廣告走期</th>
		<th width="110">每日花費</th>
		<th style="width:7%">曝光數</th>
		<th><a style="float: left; margin-top: 7px;"><img src="./html/img/question.gif" title="互動數欄位:計算不同廣告樣式所產生的主要動作次數"></a>互動數</th>
		<th>互動率</th>
		<th>單次互動費用</th>
		<th>千次曝光費用</th>	
		<th style="min-width:65px;width:9%">費用</th>
		<th>編輯</th>
	</tr>	
</thead>
<tbody>
	<#if adActionViewVO?exists>
	    <#list adActionViewVO as vo>
			<tr>
				<td>
				<#if vo.adActionStatus != 0>
			        <input type="checkbox" id="chkY_${vo_index!}" name="chkY" value="${vo.adActionSeq!}"/>
				<#else>
			        <input type="checkbox" id="chkN_${vo_index!}" name="chkN" disabled/>
				</#if>	
				
				</td>
		        <td height="35" class="td02">
	        	<#if vo.adActionStatus != 9>
			        <a href="adGroupView.html?adActionSeq=${vo.adActionSeq!}&groupMaxPrice=${vo.adActionMax!}">${vo.adActionName!}</a>
				<#else>
			        ${vo.adActionName!}
				</#if>		        	
		        </td>
		        <td class="td03">${vo.adType!}</td>
		        <td class="td03">${vo.adOperatingRule!}</td>
		        
		        <td class="td03">${vo.adDevice!}</td>
		        <td class="td03">${vo.adActionStatusDesc!}</td>
				<td class="td03">
				<#if vo.adEndDate == "3000-12-31">
				${vo.adStartDate!} ~ 永久
				<#else>
				${vo.adStartDate!} ~ ${vo.adEndDate!}
				</#if>
				</td>
				<td><input type="text" id="max_${vo.adActionSeq!}" style="width:60px;text-align:right" value="${vo.adActionMax?string('###')!}" maxlength="6" class="required digits" min="100"> <input type="button" id="bt_${vo.adActionSeq!}" value="確定" onclick="updAdActionMax('${vo.adActionSeq!}')"></td>
				<td class="td01">${vo.adPv?string('#,###')!}</td>				
				<td class="td01">${vo.adClk?string('#,###')!}</td>
				<td class="td01">${vo.adClkRate?string('#.##')!}%</td>
				<td class="td01">NT$ ${vo.adClkPriceAvg?string('#.##')!}</td>
				<td class="td01">NT$ ${vo.thousandsCost?string('#.##')!}</td>
				<td class="td01">NT$ ${vo.adClkPrice?string('#,###')!}</td>
				<td class="td02">
					<#if vo.adActionStatus != 9>
						<a href="adGroupAdd.html?adActionSeq=${vo.adActionSeq!}">新增分類</a><br>
					</#if>
					<a href="adActionEdit.html?backPage=adGroupAdd&adActionSeq=${vo.adActionSeq!}">編輯</a>&nbsp;
					<#if vo.adActionStatus == 0>
						<a href="#" onclick="closeAdActionStatus('${vo.adActionSeq!}','10')">關閉</a>
					</#if>
				</td>
			</tr>
	    </#list>
	<#else>
	<tr>
		<td colspan="13">
			沒有資料。請按「新增廣告」按鈕，新增廣告。
		</td>
	</tr>
	</#if>
</tbody>
	<tr class="tbg">
		<td colspan="8">總計：${totalSize!}筆</td>
		<td class="td01">${totalPv?string('#,###')!}</td>				
		<td class="td01">${totalClk?string('#,###')!}</td>	
		<td class="td01">${totalClkRate?string('#.##')!}%</td>
		<td class="td01">NT$ ${totalAvgCost?string('#.##')!}</td>
		<td class="td01">NT$ ${thousandsCost?round!}</td>
		<td class="td01">NT$ ${totalCost?string('#,###')!}</td>
		<td></td>
	</tr>
</table>
<input type="hidden" id="adActionSeq" name="adActionSeq" />
<input type="hidden" id="status" name="status" />
<input type="hidden" id="adActionMax" name="adActionMax" />
</form>

<span style="padding:10px;display:block">
	<input type="button" name="stop" onClick="modifyAdStatus('9')" value="暫 停" /> &nbsp; 
	<input type="button" name="start" onClick="modifyAdStatus('4')" value="開 啟" /> &nbsp;
	<input type="button" name="close" onClick="modifyAdStatus('10')" value="關 閉" /> &nbsp;          
</span>
