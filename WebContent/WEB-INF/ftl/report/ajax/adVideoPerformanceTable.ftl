<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<span style="float:left"> 
<table border="0" cellspacing="3" cellpadding="0" class="srchtb"> 
  <tr> 
	  
	   <td>
		    <select id="adPriceType"> 
		        <#list adPriceTypeMap?keys as skey>
		  		    <option value="${skey}">${adPriceTypeMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>
	    
	    <td>
		    <select id="adSize"> 
		        <#list adSizeMap?keys as skey>
		  		    <option value="${skey}">${adSizeMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>
	    
	   <td>
		    <select id="adPvclkDevice"> 
		        <#list adPvclkDeviceMap?keys as skey>
		  		    <option value="${skey}">${adPvclkDeviceMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>
	  
	    <td>
	     	<select id="adSearchWay"> 
		        <#list adSearchWayMap?keys as skey>
		  		    <option value="${skey}">${adSearchWayMap[skey]}</option>
		  	    </#list>
	      	</select>
	    </td>
	    
	    <td>
	    	<input type="text" id="searchText"/>
	    </td>
	   
	    <td>
	    	<img src="<@s.url value="/html/img/"/>srchbtn.gif" border="0" id="btnSearchDo"/>
	    </td>
	    
	    <td>
	    	<a href="#" id="btnSearchReset">重設</a>
	    </td> 
    
    </tr> 
</table> 
</span>




<span class="pages">

<img style="vertical-align:middle" id="fpage" src="<@s.url value="/html/img/"/>page_first.gif" />
<img style="vertical-align:middle" id="ppage" src="<@s.url value="/html/img/"/>page_pre.gif" />
${page}/${totalPage}
<img style="vertical-align:middle" id="npage" src="<@s.url value="/html/img/"/>page_next.gif" />
<img style="vertical-align:middle" id="epage" src="<@s.url value="/html/img/"/>page_end.gif" />

&nbsp&nbsp

顯示 
<select id="pageSizeSelect" style="vertical-align:middle" > 
    <#list pageSizeList as ps>
        <option>${ps}</option>
    </#list>
</select> 
行
</span> 

<br>
<div style="overflow-x:auto;width:100%">
<#if (adVideoPerformanceReportVOList? exists && adVideoPerformanceReportVOList ? size > 0) >
	<table id="excerptTable" border="0" cellpadding="0" cellspacing="1" class="tablesorter" style="width: 2000px"> 
	    <thead>
			<tr>
				<th width="50" height="35" rowspan="2">狀態</th>
				<th width="200" colspan="2" rowspan="2">影片明細</th>
				<th width="100" rowspan="2">計價方式</th>
				<th width="50" rowspan="2">裝置</th>
				<th width="80" rowspan="2">廣告尺寸</th>
				<th width="80" rowspan="2">曝光數</th>
				<th width="80" rowspan="2">收視數</th>
				<th width="80" rowspan="2">收視率</th>
				<th width="80" rowspan="2">單次<br>收視費用</th>
				<th width="80" rowspan="2">千次<br>曝光費用</th>
				<th width="80" rowspan="2">費用</th>
				<th width="200" colspan="4">影片播放進度</th>
				<th width="90" rowspan="2">影片<br>完整播放率</th>
				<th width="80" rowspan="2">點選次數</th>
				<th width="80" rowspan="2">收視人數<br>(不重複)</th>
				<th width="80" rowspan="2">聲音開啟<br>次數</th>
				<th width="80" rowspan="2">重播次數</th>
			</tr>
			<tr>
			  <th style="width:60px;">25%</th>
			  <th style="width:60px;">50%</th>
			  <th style="width:60px;">75%</th>
			  <th style="width:60px;">100%</th>
			</tr>
	
		</thead>
		<tbody>
				<#assign sumPV = 0>
				<#assign sumView = 0>
				<#assign sumCost = 0>
				<#assign sumVideoProvess25 = 0>
				<#assign sumVideoProvess50 = 0>
				<#assign sumVideoProvess75 = 0>
				<#assign sumVideoProvess100 = 0>
				<#assign sumClick = 0>
				<#assign sumVideoUniq = 0>
				<#assign sumMusic = 0>
				<#assign sumReplay = 0>
				<#assign sumViewRatings = 0>
				<#assign sumThousandsCost = 0>
				<#assign sumSingleAdViewCost = 0>
				<#assign sumVideoProcess100Ratings = 0>
		    <#list adVideoPerformanceReportVOList as adVideoPerformanceReportVO>
				<#assign sumPV = sumPV + adVideoPerformanceReportVO.adPvSum ?number>
				<#assign sumView = sumView + adVideoPerformanceReportVO.adViewSum ?number>
				<#assign sumCost = sumCost + adVideoPerformanceReportVO.costSum ?number>
				<#assign sumVideoProvess25 = sumVideoProvess25 + adVideoPerformanceReportVO.adVideoProcess25Sum ?number>
				<#assign sumVideoProvess50 = sumVideoProvess50 + adVideoPerformanceReportVO.adVideoProcess50Sum ?number>
				<#assign sumVideoProvess75 = sumVideoProvess75 + adVideoPerformanceReportVO.adVideoProcess75Sum ?number>
				<#assign sumVideoProvess100 = sumVideoProvess100 + adVideoPerformanceReportVO.adVideoProcess100Sum ?number>
				<#assign sumClick = sumClick + adVideoPerformanceReportVO.adClkSum ?number>
				<#assign sumVideoUniq = sumVideoUniq + adVideoPerformanceReportVO.adVideoUniqSum ?number>
				<#assign sumMusic = sumMusic + adVideoPerformanceReportVO.adVideoMusicSum ?number>
				<#assign sumReplay = sumReplay + adVideoPerformanceReportVO.adVideoReplaySum ?number>
				<#assign sumViewRatings = (sumView / sumPV * 100) ? string("0.##")>
				<#assign sumThousandsCost = (sumCost / sumPV * 1000) ? string("0.##")>
				<#assign sumSingleAdViewCost = (sumCost / sumView) ? string("0.##")>
				<#assign sumVideoProcess100Ratings = (sumVideoProvess100 / sumView * 100) ? string("0.##")>
		     	<tr height="30">
					<#if adVideoPerformanceReportVO.adStatus == "2" >
						<td><img src="http://show.pchome.com.tw/html/img/icon_adopen.gif" alt="開啟" title="開啟"></td>
						<#else>
						<td><img src="http://show.pchome.com.tw/html/img/icon_adclose.gif" alt="關閉" title="關閉"></td>
					</#if>
		            <td>
			  	    <div style="padding: 8px;width:80px;height:auto;margin: 0 auto"><img src="http://showstg.pchome.com.tw/pfp/${adVideoPerformanceReportVO.adImg!}" width="80"></div></td>
					<td style=" text-align: left; line-height: 20px; padding: 10px; ">${adVideoPerformanceReportVO.adGroupName!}<br>${adVideoPerformanceReportVO.templateProductWidth!}x${adVideoPerformanceReportVO.templateProductHeight!}<br>00:30<br>
					  	<a href="#" target="_blank">${adVideoPerformanceReportVO.videoUrl!}</a><br>
						<span class="table_preview_btn"><a href="#">預覽</a></span>
					</td>
					<td><#if adVideoPerformanceReportVO.adPriceType == 'CPM'>千次曝光出價<#else>單次收視出價</#if><br>${adVideoPerformanceReportVO.adPriceType!}</td>
					<td>全部</td>
					<td align="center">${adVideoPerformanceReportVO.templateProductWidth!}x${adVideoPerformanceReportVO.templateProductHeight!}</td>
					<td align="right">${adVideoPerformanceReportVO.adPvSum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adViewSum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adViewRatings!}%</td>
			        <td align="right">NT$ ${adVideoPerformanceReportVO.singleAdViewCost!}</td>
			        <td align="right">NT$ ${adVideoPerformanceReportVO.thousandsCost!}</td>
			        <td align="right">NT$ ${adVideoPerformanceReportVO.costSum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess25Sum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess50Sum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess75Sum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess100Sum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess100Ratings!}%</td>
			        <td align="right">${adVideoPerformanceReportVO.adClkSum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoUniqSum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoMusicSum!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoReplaySum!}</td>
				</tr>
				<#assign totalPV = 0>
				<#assign width = adVideoPerformanceReportVO.templateProductWidth>
				<#assign height = adVideoPerformanceReportVO.templateProductHeight>
				<#assign totalPV = totalPV + adVideoPerformanceReportVO.adPvSum>
			</#list>
	 	</tbody>
	 	
	 	<tfoot>
		 	<tr height="35">
			    <th height="30" colspan="3"  align="">總計-所有影片	<#if adVideoPerformanceReportVOList? exists>${adVideoPerformanceReportVOList ? size}<#else>0</#if></th>
			    <th height="30" align=""><#if adPriceType =="">全部<#else>${adPriceType}</#if></th>
			    <th height="30" align=""><#if adPvclkDevice =="">全部<#else>${adPvclkDevice}</#if></th>
			    <th height="30" align=""><#if adSize =="">全部<#else>${width} x ${height}</#if></th>
			    <th height="30" align="right">${sumPV!}</th>
			    <th height="30" align="right">${sumView!}</th>
			    <th height="30" align="right">${sumViewRatings!}%</th>
			    <th height="30" align="right">NT$ ${sumSingleAdViewCost!}</th>
			    <th height="30" align="right">${sumThousandsCost!}%</th>
			    <th height="30" align="right">NT$ ${sumCost!}</th>
			    <th height="30" align="right">${sumVideoProvess25!}</th>
			   	<th height="30" align="right">${sumVideoProvess50!}</th>
			    <th height="30" align="right">${sumVideoProvess75!}</th>
			    <th height="30" align="right">${sumVideoProvess100!}</th>
			    <th height="30" align="right">${sumVideoProcess100Ratings!}%</th>
			    <th height="30" align="right">${sumClick}</th>
			    <th height="30" align="right">${sumVideoUniq}</th>
			    <th height="30" align="right">${sumMusic}</th>
			    <th height="30" align="right">${sumReplay}</th>
			</tr>    
	   	</tfoot>
	</table> 
<#else>
<div style="clear:both;line-height:30px;text-align:right;font-size:15px"></div> 
<div style="clear:both;line-height:30px;text-align:left;font-size:12px"></div>
<table id="excerptTable" width="100%" border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 
  	<tfoot>
    	<tr height="35"> 
    		
				<td>無此廣告成效</td>
    		
    	</tr> 
		</tfoot>
  
</table> 
</#if>
</div>

<form id="excerptFrom" name="excerptFrom" action="reportVideoPerformanceDownload.html" method="post">
	<input type="hidden" id="adPriceType" name="adPriceType" value="${adPriceType}">
	<input type="hidden" id="adSize" name="adSize" value="${adSize}">
	<input type="hidden" id="formPage" name="page" value="${page}">	
	<input type="hidden" id="fpageSize" name="pageSize" value="${pageSize}">
	<input type="hidden" id="ftotalPage" name="totalPage" value="${totalPage}">
	<input type="hidden" id="foptionSelect" name="optionSelect" value="${optionSelect}">
	<input type="hidden" id="foptionNotSelect" name="optionNotSelect" value="${optionNotSelect}">
	<input type="hidden" id="fstartDate" name="startDate" value="${startDate}">
	<input type="hidden" id="fendDate" name="endDate" value="${endDate}">
	<input type="hidden" id="fadPvclkDevice" name="adPvclkDevice" value="${adPvclkDevice}">
	<input type="hidden" id="fadType" name="adType" value="${adType}">
	<input type="hidden" id="fadSearchWay" name="adSearchWay" value="${adSearchWay}">
	<input type="hidden" id="fsearchText" name="searchText" value="${searchText}">
	<input type="hidden" id="fsearchId" name="searchId" value="${searchId}">
	<input type="hidden" id="downloadFlag" name="downloadFlag" value="yes">
	<input type="hidden" id="contentPath" name="contentPath" value="<@s.url value="/html/img/"/>">
</form>
