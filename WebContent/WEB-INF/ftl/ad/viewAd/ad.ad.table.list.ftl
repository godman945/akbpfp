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
		<th>廣告明細</th>
		<th style="width:6%">狀態</th>
		<th style="width:6%">曝光數</th>
		<th style="width:6%">點選次數</th>	
		<th style="width:6%">點選率</th>
		<!--<th style="width:6%">無效點選次數</th>-->
		<th style="width:8%">平均點選費用</th>
		<th style="width:6%;min-width:65px">費用</th>
		<th>廣告</th>
		<th>分類</th>
		<th>編輯</th>
	</tr>
</thead>
<tbody>
	<#if adAdViewVO?exists>
	    <#list adAdViewVO as vo>
			<tr>
				<td>
				<#if vo.adStatus == 4 || vo.adStatus == 9>
			        <input type="checkbox" id="chkY_${vo_index!}" name="chkY" value="${vo.adSeq!}"/>
				<#else>
			        <input type="checkbox" id="chkN_${vo_index!}" name="chkN" disabled/>
				</#if>
				</td>
				
		        <td height="35" class="td02" > 
		        	<#if "IMG" == vo.adStyle && "N" == vo.html5Tag>
 						<div class="adreportdv">
							<span class="adboxdvimg"><a href="${vo.realUrl!}" target="_blank"><img src="${vo.originalImg!}" /></a></span>
				        	<span class="adboxdvinf">
						        <span>
						        	<b>${vo.title!}</b><br>
						            <i>尺寸</i><b>${vo.imgWidth!} x ${vo.imgHeight!}</b><br>
						            <span>${vo.showUrl!}</span><br>
						            <a class="fancy" style="cursor:pointer" onclick="preview('${vo.originalImg!}')" alt="預覽">預覽</a>
					            </span>
				        	</span>
			        	</div>
			        <#elseif "IMG" == vo.adStyle && "Y" == vo.html5Tag>
			        	<div class="adreportdv">
			        		<span class="adboxdvimg">${vo.zipTitle!}</span>
				        	<span class="adboxdvinf">
						        <span>
						        	<b>${vo.title!}</b><br>
						            <i>尺寸</i><b>${vo.imgWidth!} x ${vo.imgHeight!}</b><br>
						            <span>${vo.showUrl!}</span><br>
						            <a class="fancy" style="cursor:pointer" onclick="preViewHtml5('${vo.imgWidth!}','${vo.imgHeight!}','${vo.originalImg!}','${vo.realUrl!}')" alt="預覽">預覽</a>
					            </span>
				        	</span>
			        	</div>
					<#else>
						<span><iframe height="120" width="350" src="adModel.html?adNo=${vo.adSeq!}&tproNo=${vo.adTemplateNo!}" marginwidth="0" marginheight="0" scrolling="no" frameborder="0" align="ceneter" class="akb_iframe"></iframe></span>
		        	</#if>
		        </td>
		        <td class="td03">
		        ${vo.adStatusDesc!}
		        <#if vo.adStatus == 3 || vo.adStatus == 6>
		       	 <img src="<@s.url value="/" />html/img/icon_Q.gif" align="absmiddle" title="${vo.adRejectReason!}">
		        </#if>
		        </td>
				<td class="td01">${vo.adPv?string('#,###')!}</td>				
				<td class="td01">${vo.adClk?string('#,###')!}</td>
				<td class="td01">${vo.adClkRate?string('#.##')!}%</td>
				<!--<td class="td01">${vo.invalidClk?string('#,###')!}</td>-->
				<td class="td01">NT$ ${vo.adClkPriceAvg?string('#.##')!}</td>
				<td class="td01">NT$ ${vo.adClkPrice?string('#,###')!}</td>
				<td>${vo.adActionName!}</td>
				<td>${vo.adGroupName!}</td>
				<td class="td02">
					<#if vo.adStatus != 9>
						<a href="adAdAdd.html?adGroupSeq=${vo.adGroupSeq!}">製作新廣告</a><br>
						<#if adType == "0" || adType == "1" >
						<a href="adKeywordAdd.html?adGroupSeq=${vo.adGroupSeq!}">新增關鍵字</a><br>
						</#if>
					</#if>
					<#if vo.adStatus != 2 && vo.adStatus != 13>	
						<#if vo.adStyle == "TXT">			
							<a href="adAdEdit.html?adSeq=${vo.adSeq!}">修改</a>
						<#elseif vo.adStyle == "TMG">
							<a href="adAdEdit.html?adSeq=${vo.adSeq!}">修改</a>
						<#elseif vo.adStyle == "IMG">
							<a href="adAdEditImg.html?adSeq=${vo.adSeq!}">修改</a>
						</#if>
						<#if vo.adStatus == 0 || vo.adStatus == 1 || vo.adStatus == 3 || vo.adStatus == 6 >
							<a href="#" onclick="closeAdAdStatus('${vo.adSeq!}','10')">關閉</a>
						</#if>
					</#if>
					
				</td>
			</tr>
	    </#list>
	<#else>
	<tr>
		<td colspan="14">
			沒有資料。請按「製作新廣告」按鈕，新增廣告明細。
		</td>
	</tr>
	</#if>
</tbody>
	<tr class="tbg">
		<td colspan="3">總計：${totalSize!}筆</td>
		<td class="td01">${totalPv?string('#,###')!}</td>
		<td class="td01">${totalClk?string('#,###')!}</td>		
		<td class="td01">${totalClkRate?string('#.##')!}%</td>
		<!--<td class="td01">${totalInvalidClk?string('#.##')!}</td>-->
		<td class="td01">NT$ ${totalAvgCost?string('#.##')!}</td>
		<td class="td01">NT$ ${totalCost?string('#,###')!}</td>
		<td></td>
		<td></td>
		<td></td>
	</tr>
</table>
<input type="hidden" id="adAdSeq" name="adAdSeq" />
<input type="hidden" id="status" name="status" />
</form>

<span style="padding:10px;display:block">
	<input type="button" name="stop" onClick="modifyAdStatus('9')" value="暫 停" /> &nbsp; 
	<input type="button" name="start" onClick="modifyAdStatus('4')" value="開 啟" /> &nbsp;
	<input type="button" name="close" onClick="modifyAdStatus('10')" value="關 閉" /> &nbsp;          
</span>

