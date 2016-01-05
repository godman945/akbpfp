<#assign s=JspTaglibs["/struts-tags"]>
<#assign t=JspTaglibs["http://tiles.apache.org/tags-tiles"]>

<#if (showSearchBarFlag == "y")>
<span style="float:left"> 
<table border="0" cellspacing="3" cellpadding="0" class="srchtb"> 
  <tr> 
	   <td>
		    <select id="adShowWay"> 
		        <#list adShowWayMap?keys as skey>
		  		    <option value="${skey}">${adShowWayMap[skey]}</option>
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
	    	<select id="adType"> 
		        <#list reportTypeMap?keys as skey>
		  		    <option value="${skey}">${reportTypeMap[skey]}</option>
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

	    <td><input type="text" id="searchText"/></td>

	    <td>
	    	<img src="<@s.url value="/html/img/"/>srchbtn.gif" border="0" id="btnSearchDo"/>
	    </td>

	    <td>
	    	<a href="#" id="btnSearchReset">重設</a>
	    </td> 
    
    </tr> 
</table> 
</span>
<#else>
    <input type="hidden" id="searchText" value="" />
</#if>

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

<div style="clear:both;line-height:30px;text-align:left;font-size:12px">${stepStr}</div>

<#if (AdKeywordReportVOList?size > 0) > 
<div style="overflow-x:auto;width:100%">
<table id="excerptTable2" border="0" cellpadding="0" cellspacing="1" class="tablesorter" > 

	<thead>
		<tr> 
			<th height="35" style="min-width:100px" rowspan="2">關鍵字</th>
			<th height="35" style="min-width:55px" rowspan="2">類型</th>
			<th height="35" style="min-width:35px"  rowspan="2">狀態</th>
			<th height="35" style="min-width:55px" rowspan="2">裝置</th>
			<th colspan="3">比對方式</th>
			<th class="pvDataTh" style="min-width:100px" colspan="1" >曝光數 <input class="pvDataButton" type="button" onclick="toggleTd('pvData')" value="展開" /></th>
			<th class="clkDataTh" style="min-width:110px" colspan="1">點選次數 <input class="clkDataButton" type="button" onclick="toggleTd('clkData')" value="展開" /></th>
			<th class="clkRateDataTh" style="min-width:100px" colspan="1">點選率 <input class="clkRateDataButton" type="button" onclick="toggleTd('clkRateData')" value="展開" /></th>
			<th class="invClickDataTh" style="min-width:135px" colspan="1">無效點選次數 <input class="invClickDataButton" type="button" onclick="toggleTd('invClickData')" value="展開" /></th>
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
			<th class="invClickData" style="min-width:50px;display:none;">廣泛<br/>比對</th>
			<th class="invClickData" style="min-width:50px;display:none;">詞組<br/>比對</th>
			<th class="invClickData" style="min-width:50px;display:none;">精準<br/>比對</th>
			<th style="min-width:50px;">總計</th>
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
	<#list AdKeywordReportVOList as vo>
		<tr height="30">
			<td class="td02">${vo.adKeyword!}</td>
			<td>${vo.kwAdType!}</td>
			<td>${vo.adStatus!}</td>
			<td>${vo.kwDevice!}</td>
			<td style="display:none;"></td>
	        <td style="display:none;"></td>
	        <td style="display:none;"></td>
	        <td style="display:none;"></td>
	        <td style="display:none;"></td>
	        <td style="display:none;"></td>
	        <td style="display:none;"></td>
	        <td>${vo.kwOpen!}</td>
	        <td>${vo.kwPhrOpen!}</td>
	        <td>${vo.kwPreOpen!}</td>
	        <td class="td01 pvData" style="display:none;" >${vo.kwPvSum!}</td>
	        <td class="td01 pvData" style="display:none;" >${vo.kwPhrPvSum!}</td>
	        <td class="td01 pvData" style="display:none;" >${vo.kwPrePvSum!}</td>
	        <td class="td01">${vo.kwPvTotal!}</td>
	        <td class="td01 clkData" style="display:none;" >${vo.kwClkSum!}</td>
	        <td class="td01 clkData" style="display:none;" >${vo.kwPhrClkSum!}</td>
	        <td class="td01 clkData" style="display:none;" >${vo.kwPreClkSum!}</td>
	        <td class="td01">${vo.kwClkTotal!}</td>
	        <td class="td01 clkRateData" style="display:none;" >${vo.kwCtrSum!}%</td>
	        <td class="td01 clkRateData" style="display:none;" >${vo.kwPhrCtrSum!}%</td>
	        <td class="td01 clkRateData" style="display:none;" >${vo.kwPreCtrSum!}%</td>
	        <td class="td01">${vo.kwCtrTotal!}%</td>
	        <td class="td01 invClickData" style="display:none;" >${vo.kwInvClkSum!}</td>
	        <td class="td01 invClickData" style="display:none;" >${vo.kwPhrInvClkSum!}</td>
	        <td class="td01 invClickData" style="display:none;" >${vo.kwPreInvClkSum!}</td>
	        <td class="td01">${vo.kwInvClkTotal!}</td>
	        <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.kwPriceAvgSum!}</td>
	        <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.kwPhrPriceAvgSum!}</td>
	        <td class="td01 clkPriceAvgData" style="display:none;" >NT$ ${vo.kwPrePriceAvgSum!}</td>
	        <td class="td01">NT$ ${vo.kwPriceAvgTotal!}</td>
	        <td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.kwPriceSum!}</td>
	        <td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.kwPhrPriceSum!}</td>
	        <td class="td01 clkPriceData" style="display:none;" >NT$ ${vo.kwPrePriceSum!}</td>
	        <td class="td01">NT$ ${vo.kwPriceTotal!}</td>  
		</tr>
	</#list>
	</tbody>

 	<tfoot>
 	<tr height="35">
	    <th height="30">${AdKeywordReportDataTotal.dataTotal!}</th>
	    <th height="30"></th>
	    <th height="30"></th>
	    <th height="30"></th>
	    <th height="30"></th>
	    <th height="30"></th>
	    <th height="30"></th>
	    <th height="30" class="td01 pvData" style="display:none;" >${AdKeywordReportDataTotal.kwPvSum!}</th>
        <th height="30" class="td01 pvData" style="display:none;" >${AdKeywordReportDataTotal.kwPhrPvSum!}</th>
        <th height="30" class="td01 pvData" style="display:none;" >${AdKeywordReportDataTotal.kwPrePvSum!}</th>
        <th height="30" class="td01">${AdKeywordReportDataTotal.kwPvTotal!}</th>
        <th height="30" class="td01 clkData" style="display:none;" >${AdKeywordReportDataTotal.kwClkSum!}</th>
        <th height="30" class="td01 clkData" style="display:none;" >${AdKeywordReportDataTotal.kwPhrClkSum!}</th>
        <th height="30" class="td01 clkData" style="display:none;" >${AdKeywordReportDataTotal.kwPreClkSum!}</th>
        <th height="30" class="td01">${AdKeywordReportDataTotal.kwClkTotal!}</th>
        <th height="30" class="td01 clkRateData" style="display:none;" >${AdKeywordReportDataTotal.kwCtrSum!}%</th>
        <th height="30" class="td01 clkRateData" style="display:none;" >${AdKeywordReportDataTotal.kwPhrCtrSum!}%</th>
        <th height="30" class="td01 clkRateData" style="display:none;" >${AdKeywordReportDataTotal.kwPreCtrSum!}%</th>
        <th height="30" class="td01">${AdKeywordReportDataTotal.kwCtrTotal!}%</th>
        <th height="30" class="td01 invClickData" style="display:none;" >${AdKeywordReportDataTotal.kwInvClkSum!}</th>
        <th height="30" class="td01 invClickData" style="display:none;" >${AdKeywordReportDataTotal.kwPhrInvClkSum!}</th>
        <th height="30" class="td01 invClickData" style="display:none;" >${AdKeywordReportDataTotal.kwPreInvClkSum!}</th>
        <th height="30" class="td01">${AdKeywordReportDataTotal.kwInvClkTotal!}</th>
        <th height="30" class="td01 clkPriceAvgData" style="display:none;" >NT$ ${AdKeywordReportDataTotal.kwPriceAvgSum!}</th>
        <th height="30" class="td01 clkPriceAvgData" style="display:none;" >NT$ ${AdKeywordReportDataTotal.kwPhrPriceAvgSum!}</th>
        <th height="30" class="td01 clkPriceAvgData" style="display:none;" >NT$ ${AdKeywordReportDataTotal.kwPrePriceAvgSum!}</th>
        <th height="30" class="td01">NT$ ${AdKeywordReportDataTotal.kwPriceAvgTotal!}</th>
        <th height="30" class="td01 clkPriceData" style="display:none;" >NT$ ${AdKeywordReportDataTotal.kwPriceSum!}</th>
        <th height="30" class="td01 clkPriceData" style="display:none;" >NT$ ${AdKeywordReportDataTotal.kwPhrPriceSum!}</th>
        <th height="30" class="td01 clkPriceData" style="display:none;" >NT$ ${AdKeywordReportDataTotal.kwPrePriceSum!}</th>
	    <th height="30" class="td01">NT$ ${AdKeywordReportDataTotal.kwPriceTotal!}</th>
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

<form id="excerptFrom" name="excerptFrom" action="reportExcerptDownload.html" method="post">
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
	<input type="hidden" id="fadShowWay" name="adShowWay" value="${adShowWay}">
	<input type="hidden" id="fsearchText" name="searchText" value="${searchText}">
	<input type="hidden" id="fsearchId" name="searchId" value="${searchId}">
	<input type="hidden" id="downloadFlag" name="downloadFlag" value="yes">
	<input type="hidden" id="contentPath" name="contentPath" value="<@s.url value="/html/img/"/>">
    <input type="hidden" id="showSearchBarFlag" name="showSearchBarFlag" value="${showSearchBarFlag}">
</form>
