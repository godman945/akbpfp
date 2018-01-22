<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>
<script src="http://jcrop-cdn.tapmodo.com/v0.9.12/js/jquery.Jcrop.js"></script>
<!-- <link rel="stylesheet" href="http://jcrop-cdn.tapmodo.com/v0.9.9/css/jquery.Jcrop.css" /> -->


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
				<th width="150" height="35" rowspan="2">狀態</th>
				<th colspan="2" rowspan="2">影片明細</th>
				<th width="120" rowspan="2">計價方式</th>
				<th width="50" rowspan="2">裝置</th>
				<th width="80" rowspan="2">廣告尺寸</th>
				<th width="80" rowspan="2">曝光數</th>
				<th width="80" rowspan="2">收視數</th>
				<th width="80" rowspan="2">收視率</th>
				<th width="80" rowspan="2">單次<br>收視費用</th>
				<th width="80" rowspan="2">千次<br>曝光費用</th>
				<th width="90" rowspan="2">費用<b style="float: left; margin-top: 3px;"><img src="./html/img/question.gif" title="廣告費用因小數點進位影響總計費用，實際扣款依帳單管理為主"></b></th>
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
		    <#list adVideoPerformanceReportVOList as adVideoPerformanceReportVO>
		     	<tr height="30">
					<#if adVideoPerformanceReportVO.adStatus == "4" >
						<td><img src="http://show.pchome.com.tw/html/img/icon_adopen.gif" alt="開啟" title="開啟"></td>
						<#else>
						<td><img src="http://show.pchome.com.tw/html/img/icon_adclose.gif" alt="關閉" title="關閉"></td>
					</#if>
		            <td>
		             <iframe src="adVideoModel.html?adPreviewVideoURL=${adVideoPerformanceReportVO.videoUrl!}&adPreviewVideoBgImg=<#if adVideoPerformanceReportVO.adImg = 'img/public/na.gif" style="display:none'><#else>${adVideoPerformanceReportVO.adImg!}</#if>&realUrl=${adVideoPerformanceReportVO.adLinkUrl!}&resize=false" class="akb_iframe" scrolling="no" frameborder="0" marginwidth="0" marginheight="0" vspace="0" hspace="0" id="pchome8044_ad_frame1" width="${adVideoPerformanceReportVO.templateProductWidth!}" height="${adVideoPerformanceReportVO.templateProductHeight!}" allowtransparency="true" allowfullscreen="true"></iframe> 
					</td>
					<td  style="text-align:left; line-height: 20px; padding: 10px;width:50px;word-wrap:break-word;">${adVideoPerformanceReportVO.title!}<br><div class="ad_size">${adVideoPerformanceReportVO.templateProductWidth!} x ${adVideoPerformanceReportVO.templateProductHeight!}</div>00:${adVideoPerformanceReportVO.adVideoSec!}<br>
					  	<a href="${adVideoPerformanceReportVO.adLinkUrl!}" target="_blank">${adVideoPerformanceReportVO.adLinkUrl!}</a><br>
					</td>
					<td><#if adVideoPerformanceReportVO.adPriceType == 'CPM'>千次曝光出價<#else>單次收視出價</#if><br>${adVideoPerformanceReportVO.adPriceType!}</td>
					<td>${adVideoPerformanceReportVO.device!}</td>
					<td align="center">${adVideoPerformanceReportVO.templateProductWidth!}x${adVideoPerformanceReportVO.templateProductHeight!}</td>
					<td align="right">${adVideoPerformanceReportVO.adPvSum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adViewSum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adViewRatings}%</td>
			        <td align="right">NT$ ${adVideoPerformanceReportVO.singleAdViewCost?number?string('#,###.##')!}</td>
			        <td align="right">NT$ ${adVideoPerformanceReportVO.thousandsCost?number?string('#,###.##')!}</td>
			        <td align="right">NT$ ${adVideoPerformanceReportVO.costSum?number?string('#,###.###')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess25Sum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess50Sum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess75Sum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess100Sum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoProcess100Ratings?number?string('#,###.##')!}%</td>
			        <td align="right">${adVideoPerformanceReportVO.adClkSum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoUniqSum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoMusicSum?number?string('#,###.##')!}</td>
			        <td align="right">${adVideoPerformanceReportVO.adVideoReplaySum?number?string('#,###.##')!}</td>
				</tr>
				<#assign totalPV = 0>
				<#assign width = adVideoPerformanceReportVO.templateProductWidth>
				<#assign height = adVideoPerformanceReportVO.templateProductHeight>
				<#assign totalPV = totalPV + adVideoPerformanceReportVO.adPvSum>
			</#list>
	 	</tbody>
	 	
	 	<tfoot>
		 	<tr height="35">
			    <th height="30" colspan="6"  align="">總計-所有影片	<#if adVideoPerformanceReportVOSum ? exists>${adVideoPerformanceReportVOCount.totalSize!}<#else>0</#if></th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adPvSum?number?string('#,###')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adViewSum?number?string('#,###')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adViewRatings?number?string('#,###.##')!}%</th>
			    <th height="30" align="right">NT$ ${adVideoPerformanceReportVOCount.singleAdViewCost?number?string('#,###.##')!}</th>
			    <th height="30" align="right">NT$ ${adVideoPerformanceReportVOCount.thousandsCost?number?string('#,###.##')!}</th>
			    <th height="30" align="right">NT$ ${adVideoPerformanceReportVOCount.costSum?number?string('#,###.###')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoProcess25Sum?number?string('#,###')!}</th>
			   	<th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoProcess50Sum?number?string('#,###')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoProcess75Sum?number?string('#,###')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoProcess100Sum?number?string('#,###')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoProcess100Ratings?number?string('#,###.##')!}%</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adClkSum?number?string('#,###.##')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoUniqSum?number?string('#,###.##')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoMusicSum?number?string('#,###.##')!}</th>
			    <th height="30" align="right">${adVideoPerformanceReportVOCount.adVideoReplaySum?number?string('#,###.##')!}</th>
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