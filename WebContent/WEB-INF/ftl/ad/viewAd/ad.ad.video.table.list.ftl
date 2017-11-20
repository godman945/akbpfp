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
		<th width="200" colspan="2" rowspan="2">影片明細</th>
		<th>狀態</th>
		<th>計價方式</th>
		<th>曝光數</th>	
		<th>互動數</th>
		<th>互動率</th>
		<th>單次互動費用</th>
		<th>千次曝光費用</th>
		<th style=min-width:65px;">費用</th>
		<th style=min-width:70px;">編輯</th>
	</tr>
</thead>
<tbody>
	<#if pfpAdAdVideoViewVOList?exists>
	    <#list pfpAdAdVideoViewVOList as pfpAdAdVideoViewVO>
			<tr>
				<td>
				<#if pfpAdAdVideoViewVO.actionStatus == "4" || pfpAdAdVideoViewVO.actionStatus == "9">
			        <input type="checkbox" id="chkY_${pfpAdAdVideoViewVO_index!}" name="chkY" value="${pfpAdAdVideoViewVO.adSeq!}"/>
				<#else>
			       <input type="checkbox" id="chkN_${pfpAdAdVideoViewVO_index!}" name="chkN" disabled/>
				</#if>
				</td>
				<td>
					<div style="padding: 8px;height:auto;margin: 0 auto">
						<iframe class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="${pfpAdAdVideoViewVO.adWidth!}" height="${pfpAdAdVideoViewVO.adHeight!}" allowtransparency="true" allowfullscreen="true" src="adVideoModel.html?adPreviewVideoURL=${pfpAdAdVideoViewVO.videoUrl!}&adPreviewVideoBgImg=http://showstg.pchome.com.tw/pfp/${pfpAdAdVideoViewVO.imgPath!}&realUrl=${pfpAdAdVideoViewVO.realUrl!}"></iframe>
					</div>
				</td>
				<td style=" text-align: left; line-height: 20px; padding: 10px;">
						${pfpAdAdVideoViewVO.adActionName!}<br>
						尺寸 ${pfpAdAdVideoViewVO.adWidth!} x ${pfpAdAdVideoViewVO.adHeight!}<br>
						時間 00:${pfpAdAdVideoViewVO.videoSeconds!}<br>
					  	<a href="#" target="_blank" src="${pfpAdAdVideoViewVO.realUrl!}">${pfpAdAdVideoViewVO.realUrl!}</a><br>
				</td>
				
		        <td class="td03">
		        ${pfpAdAdVideoViewVO.adStatusDesc!}
		        <#if pfpAdAdVideoViewVO.adStatus == 3 || pfpAdAdVideoViewVO.adStatus == 6>
		       	 <img src="<@s.url value="/" />html/img/icon_Q.gif" align="absmiddle" title="${pfpAdAdVideoViewVO.adRejectReason!}">
		        </#if>
		        </td>
				<td class="td01">${pfpAdAdVideoViewVO.priceTypeDesc}</td>	
				<td class="td01">${pfpAdAdVideoViewVO.adPvSum?string('#,###')!}</td>
				<td class="td01">${pfpAdAdVideoViewVO.sumAdView}</td>
				<td class="td01">${pfpAdAdVideoViewVO.adViewRatings}%</td>		
				<td class="td01">NT$ ${pfpAdAdVideoViewVO.singleAdViewCost}</td>
				<td class="td01">NT$ ${pfpAdAdVideoViewVO.thousandsCost}</td>		
				<td class="td01">NT$ ${pfpAdAdVideoViewVO.costSum}</td>
				<td class="td02">
					<#if pfpAdAdVideoViewVO.actionStatus != "9">
						<a href="adAdAdd.html?adGroupSeq=${pfpAdAdVideoViewVO.adGroupSeq!}&adOperatingRule=VIDEO">製作新廣告</a><br>
					</#if>
					<a href="adAdEditVideo.html?adSeq=${pfpAdAdVideoViewVO.adSeq!}">修改</a><br>
					<#if pfpAdAdVideoViewVO.actionStatus != "2" && pfpAdAdVideoViewVO.actionStatus != "13">	
						<#if pfpAdAdVideoViewVO.actionStatus == "0" || pfpAdAdVideoViewVO.actionStatus == "1" || pfpAdAdVideoViewVO.actionStatus == "3" || pfpAdAdVideoViewVO.actionStatus == "6" >
							<a href="#" onclick="closeAdAdStatus('${pfpAdAdVideoViewVO.adSeq!}','10')">關閉</a>
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
</tbody
	<tr class="tbg">
		<td colspan="5">總計：${totalSize!}筆</td>
		<td class="td01">${pfpAdAdVideoViewSumVO.adPvSum?string('#,###')!}</td>
		<td class="td01">${pfpAdAdVideoViewSumVO.adViewSum?string('#,###')!}</td>		
		<td class="td01">${pfpAdAdVideoViewSumVO.adViewRatings?string('0.##')!}%</td>
		<td class="td01">NT$ ${pfpAdAdVideoViewSumVO.singleAdViewCost?string('0.##')!}</td>
		<td class="td01">NT$ ${pfpAdAdVideoViewSumVO.thousandsCost?string('0.##')!}</td>
		<td class="td01">NT$ ${pfpAdAdVideoViewSumVO.costSum?string('0.##')!}</td>
		<td class="td01"></td>
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


















