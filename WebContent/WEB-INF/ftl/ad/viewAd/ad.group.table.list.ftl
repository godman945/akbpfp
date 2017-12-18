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
		<!-- MEDIA廣告 -->
		<#if adOperatingRule?exists && adOperatingRule == "MEDIA">
				<th width="50"><a href="#" onclick="checkAll()">全選</a></th>
				<th height="35">分類</th>
				<th>裝置</th>
				<th>狀態</th>
				<th style="width:8%">計價方式</th>
				<#if adType == '0' || adType == '1' >
				<th style="width:12%">搜尋廣告出價</th>
				</#if>
				<#if adType == '0' || adType == '2' >
				<th style="width:13%">聯播網廣告出價</th>
				</#if>
				<th style="width:8%">曝光數</th>
				<th style="width:8%">互動數<a style="float: left; margin-top: 3px;"><img src="./html/img/question.gif" title="互動數欄位:計算不同廣告樣式所產生的主要動作次數"></a></th>	
				<th style="width:8%">互動率</th>
				<th style="width:8%">單次互動費用</th>
				<th style="width:8%">千次曝光費用</th>
				<th style="width:8%">費用</th>
				<th>編輯</th>
			<#else>
			<!-- VIDEO廣告-->
				<th width="50"><a href="#" onclick="checkAll()">全選</a></th>
				<th height="35">分類</th>
				<th>裝置</th>
				<th>狀態</th>
				<th style="width:8%">計價方式</th>
				<th style="width:13%">廣告出價</th>
				<th style="width:8%">曝光數</th>
				<th style="width:8%">互動數<a style="float: left; margin-top: 3px;"><img src="./html/img/question.gif" title="互動數欄位:計算不同廣告樣式所產生的主要動作次數"></a></th>	
				<th style="width:8%">互動率</th>
				<th style="width:8%">單次互動費用</th>
				<th style="width:8%">千次曝光費用</th>
				<th style="width:8%">費用</th>
				<th>編輯  </th>
		</#if>
	</tr>
</thead>
<tbody>
	<#if adGroupViewVO?exists>
	    <#list adGroupViewVO as vo>
		    <tr>
		    	<td>
					<#if vo.adGroupStatus != 0 >
				        <input type="checkbox" id="chkY_${vo_index!}" name="chkY" value="${vo.adGroupSeq!}"/>
					<#else>
				      <input type="checkbox" id="chkN_${vo_index!}" name="chkN" disabled/>
					</#if>	
				</td>
		    	<#if adOperatingRule?exists && adOperatingRule == "MEDIA">
					<td height="35" class="td02">
					    <#if vo.adGroupStatus != 9 >
					    	<a href="adAdView.html?adGroupSeq=${vo.adGroupSeq!}&groupMaxPrice=${groupMaxPrice!}">${vo.adGroupName!}</a>
						<#else>
						       ${vo.adGroupName!}
						</#if>		        	
					</td>
					<td class="td03">${vo.adDevice!}</td>
					<td class="td03">${vo.adGroupStatusDesc!}</td>
					<#if adType == '0' || adType == '1' >
					    <td>
					       <div class="notsbg">
						       <#if vo.adGroupSearchPriceType == adSearchPriceType[0].typeId >
							        ${adSearchPriceType[0].desc!}
							        <img src="<@s.url value="/" />html/img/icon_Q.gif" align="absmiddle" title="在選擇系統建議出價時刻，以此分類中的關鍵字，最高的排名價格設為出價金額範&#13;例：您在2013年9月1日設定「系統建議出價」，你設定的關鍵字「電腦」廣告排名較高出價為$5，系統即為您設定出價為$5">
								<#else>
									NT$ ${vo.adGroupSearchPrice!}<br/>
							        ${adSearchPriceType[1].desc!}			        
							        <img src="<@s.url value="/" />html/img/icon_Q.gif" align="absmiddle" title="您的出價已套用在此分類的每組關鍵字中">       	
								</#if>
								<br><input type="button" value="修改" onClick="modifySearchPrice('${vo.adGroupSeq!}','${vo.adGroupSearchPriceType!}')" />
				                </div>
						</td>
						</#if>
						<td class="td03">${vo.adPriceTypeDesc!}</td>
						<#if adType == '0' || adType == '2' >
					        <td>
						        NT$ ${vo.adGroupChannelPrice?string('#,###')!}
						        <br>預估播出率：${vo.adAsideRate?string('#.##')!}%<br><input type="button" value="修改" onClick="modifyChannelPrice('${vo.adGroupSeq!}','${vo.adGroupChannelPrice!}')" />
					        </td>
					    </#if>
							<td class="td01">${vo.adPv?string('#,###')!}</td>				
							<td class="td01">${vo.adClk?string('#,###')!}</td>
							<td class="td01">${vo.adClkRate?string('#.##')!}%</td>
							<td class="td01">NT$ ${vo.adClkPriceAvg?string('#.##')!}</td>
							<td class="td01">NT$ ${vo.thousandsCost?string('#.##')!}</td>
							<td class="td01">NT$ ${vo.adClkPrice?string('#,###')!}</td>
							<td class="td02">
								<#if vo.adGroupStatus != 9 >
										<a href="adAdAdd.html?adGroupSeq=${vo.adGroupSeq!}&adOperatingRule=${vo.adOperatingRule!}">製作新廣告</a><br>
										<#if adType != '2'>
										<a href="adKeywordAdd.html?adGroupSeq=${vo.adGroupSeq!}">新增關鍵字</a><br>
										</#if>
								</#if>				
										<a href="adGroupEdit.html?backPage=adAdAdd&adGroupSeq=${vo.adGroupSeq!}">修改</a><br>
								<#if vo.adGroupStatus == 0>
										<a href="#" onclick="closeAdGroupStatus('${vo.adGroupSeq!}', '10')">關閉</a>
								</#if>
							</td>
					<#elseif adOperatingRule?exists && adOperatingRule == "VIDEO">
					 <td height="35" class="td02" style="width:100px;">
			        	<#if vo.adGroupStatus != 9 >
							<#if vo.adOperatingRule?exists && vo.adOperatingRule=="VIDEO">
								<a href="adAdVideoView.html?adGroupSeq=${vo.adGroupSeq!}&groupMaxPrice=${groupMaxPrice!}">${vo.adGroupName!}</a>
								<#else>
								<a href="adAdView.html?adGroupSeq=${vo.adGroupSeq!}&groupMaxPrice=${groupMaxPrice!}">${vo.adGroupName!}</a>
							</#if>
							<#else>
					        ${vo.adGroupName!}
						</#if>		        	
			        </td>
			        <td class="td03">${vo.adDevice!}</td>
					<td class="td03">${vo.adGroupStatusDesc!}</td>
					<td class="td03">${vo.adPriceTypeDesc!}</td>
					<#if adType == '0' || adType == '2' >
					        <td style="width:10%;">
							      NT$ ${vo.adGroupChannelPrice?string('#.#')!}<br>
							                 預估播出率：${vo.adAsideRate?string('#.##')!}%<br>
							     <input type="button" value="修改" onClick="modifyChannelPrice('${vo.adGroupSeq!}','${vo.adGroupChannelPrice!}')" />
					        </td>
					 </#if>
					<td class="td01">${vo.adPv?string('#,###')!}</td>
					<td class="td01">${vo.adClk?string('#,###')!}</td>
					<td class="td01">${vo.adClkRate?string('#.##')!}%</td>
					<td class="td01">NT$ ${vo.adClkPriceAvg?string('#.##')!}</td>
					<td class="td01">NT$ ${vo.thousandsCost?string('#.##')!}</td>
					<td class="td01">NT$ ${vo.adClkPrice?string('#.##')!}</td>
					<td class="td02">
						<#if vo.adGroupStatus != 9 >
							<a href="adAdAdd.html?adGroupSeq=${vo.adGroupSeq!}&adOperatingRule=${vo.adOperatingRule!}">製作新廣告</a><br>
						</#if>				
						<a href="adGroupEdit.html?backPage=adAdAdd&adGroupSeq=${vo.adGroupSeq!}">修改</a><br>
						<#if vo.adGroupStatus == 0>
								<a href="#" onclick="closeAdGroupStatus('${vo.adGroupSeq!}', '10')">關閉</a>
						</#if>
					</td>
					
				</#if>
			</tr>
	    </#list>
	<#else>
	<tr>
		<td colspan="14">
			沒有資料。請按「新增分類」按鈕，新增分類。
		</td>
	</tr>
	</#if>
</tbody>
	<tr class="tbg">
		<#if adOperatingRule?exists && adOperatingRule=="MEDIA">
			<#if adType == '0'>
				<td colspan="7">總計：${totalSize!}筆</td>
			<#else>
				<td colspan="6">總計：${totalSize!}筆</td>
			</#if>
				<td class="td01">${totalPv?string('#,###')!}</td>
				<td class="td01">${totalClk?string('#,###')!}</td>			
				<td class="td01">${totalClkRate?string('#.##')!}%</td>
				<td class="td01">NT$ ${totalAvgCost?string('#.##')!}</td>
				<td class="td01">NT$ ${totalThousandsCost?string('#.##')!}</td>
				<td class="td01">NT$ ${totalCost?string('#,###')!}</td>
				<td class="td01"></td>
		<#elseif adOperatingRule?exists && adOperatingRule=="VIDEO">
			<td colspan="6">總計：${totalSize!}筆</td>
			<td class="td01">${totalPv?string('#,###')!}</td>
			<td class="td01">${totalClk?string('#,###')!}</td>			
			<td class="td01">${totalClkRate?string('#.##')!}%</td>
			<td class="td01">NT$ ${totalAvgCost?string('#.##')!}</td>
			<td class="td01">NT$ ${totalThousandsCost?string('#.##')!}</td>
			<td class="td01">NT$ ${totalCost?string('#.##')!}</td>
			<td class="td01"></td>
		</#if>
	</tr>
</table>
<input type="hidden" id="adGroupSeq" name="adGroupSeq" />
<input type="hidden" id="status" name="status" />
<input type="hidden" id="userPrice" name="userPrice" />
<input type="hidden" id="searchPriceType" name="searchPriceType" />
<input type="hidden" id="adGroupSearchPrice" name="adGroupSearchPrice" />
</form>
<span style="padding:10px;display:block">
	<input type="button" name="stop" onClick="modifyAdStatus('9')" value="暫 停" /> &nbsp; 
	<input type="button" name="start" onClick="modifyAdStatus('4')" value="開 啟" /> &nbsp;
	<input type="button" name="close" onClick="modifyAdStatus('10')" value="關 閉" /> &nbsp;          
</span>